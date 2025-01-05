package spring.hi_hello_spring.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import spring.hi_hello_spring.common.util.RedisService;
import spring.hi_hello_spring.security.entity.CustomUserDetails;
import spring.hi_hello_spring.security.service.CustomUserDetailsService;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final CustomUserDetailsService userDetailsService;
    private final RedisService redisService;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();


    public JwtUtil(
            @Value("${TOKEN_SECRET}") String secretKey,
            CustomUserDetailsService userDetailsService, RedisService redisService) {
        this.redisService = redisService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userDetailsService;
    }

    /* accessToken 검증(Bearer 토큰이 넘어왔고, 우리 사이트의 secret key로 만들어 졌는가, 만료되었는지와 내용이 비어있진 않은지) */
    public boolean validateAccessToken(String accessToken) {

        log.info("엑세스 토큰 검증 도중 확인 : " + accessToken);
        String savedToken = "";

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);

            // 레디스 내 블랙리스트 토큰과 비교 검증 로직 추가
            String EmployeeSeqInAccessToken = getEmployeeSeq(accessToken);

            // 레디스에 저장된 해당 사원이 이전에 발급 받았던 엑세스 토큰 조회
            savedToken = redisService.getToken(EmployeeSeqInAccessToken + 'a');
            log.info("레디스에서 조회한 엑세스 토큰 : {}", savedToken);
            if (savedToken != null && savedToken.equals(accessToken)) {
                return false;
            }
            return true;
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            log.info("엑세스 토큰 검증 과정 중 예외 발생 : " + e);
            return false;
        }
    }

    /* refreshToken 검증(Bearer 토큰이 넘어왔고, 우리 사이트의 secret key로 만들어 졌는가, 만료되었는지와 내용이 비어있진 않은가
     *  ,레디스에 저장된 토큰과 동일한가, 내부의 담긴 값은 일치하는가) */
    public boolean validateRefreshToken(String refreshToken) {

        String savedToken = "";

        try {
            // 1차 검증
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);

            // 요청 토큰이 담고있던 사원Seq
            String EmployeeSeqInRefreshToken = getEmployeeSeq(refreshToken);

            // 레디스에 저장된 해당 사원이 이전에 발급 받았던 리프레시 토큰 조회
            savedToken = redisService.getToken(EmployeeSeqInRefreshToken);
            if (savedToken != null) {
                // 저장되어 있던 토큰의 담긴 값
                String EmployeeSeqInRedisToken = getEmployeeSeq(savedToken);
                if (refreshToken.equals(savedToken) && EmployeeSeqInRefreshToken.equals(EmployeeSeqInRedisToken)) {
                    return true;
                }
            }

        } catch (Exception e) {
            log.info("리프레시 토큰 검증 과정 중 예외 발생 : " + e.getMessage());
            throw e;
        }
        return false;
    }

    /* Token 에서 Claims 추출 */
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /* Token 에서 사용자의 seq(subject 클레임) 추출 */
    public String getEmployeeSeq(String token) {
        return parseClaims(token).getSubject();
    }

    // accessToken 생성
    public String generateAccessToken(Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("액세스 토큰 생성 중(generateAccessToken) 객체 확인 : " + customUserDetails.toString());

        Long employeeSeq = customUserDetails.getEmployeeSeq();
        String employeeDepartmentName = customUserDetails.getEmployeeDepartmentName();
        String employeePositionName = customUserDetails.getEmployeePositionName();
//        log.info("employee 정보 : " + employeePositionName + " + " + employeeDepartmentName);

//        long expirationTime = (long) 1000 * 60 * 30; // 30분
//        long expirationTime = (long) 5000; // 토큰 테스트 용
        long expirationTime = (long) 1000 * 60 * 180; // 프론트 개발 용 3시간

        /* 권한을 꺼내 List<String> 으로 변환 */
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Claims claims = Jwts.claims().setSubject(String.valueOf(employeeSeq));
        claims.put("employeeDepartmentName", new String(employeeDepartmentName.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        claims.put("employeePositionName", new String(employeePositionName.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
//        claims.put("employeeDepartmentName", employeeDepartmentName);
//        claims.put("employeePositionName", employeePositionName);
        claims.put("employeeRole", authorities);
//        log.info("클레임 정보 : " + claims.toString());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .setHeaderParam("typ", "JWT") // JWT 타입 지정
                .compact();
    }

    // refreshToken 생성
    public String generateRefreshToken(Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("리프레시 토큰 생성 중(generateAccessToken) 객체 확인 : " + customUserDetails.toString());

        Long employeeSeq = customUserDetails.getEmployeeSeq();

        long expirationTime = (long) 1000 * 60 * 60 * 24 * 7; // 7일
        Claims claims = Jwts.claims().setSubject(String.valueOf(employeeSeq));

        // 만료 시간 설정
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시간
                .signWith(key)
                .compact();
    }



    // request 에서 refresh token or access token 을 추출
    public Optional<String> getToken(HttpServletRequest request, String tokenType) {

        String authorizationHeader;
        if (tokenType.equals("refresh")) authorizationHeader = request.getHeader("RefreshToken");
        else authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return Optional.of(authorizationHeader.substring(7));
        }
        return Optional.empty();
    }

    // authentication 저장
    public void saveAuthentication(Long employeeSeq) {

        log.info("authentication 저장 과정 중 seq 확인 : " + employeeSeq);
        CustomUserDetails customUserDetails = userDetailsService.loadUserBySeq(employeeSeq);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(customUserDetails, null,
                        authoritiesMapper.mapAuthorities(customUserDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // 엑세스 토큰 재발급
    public String reIssueAccessToken(String refreshToken) {
        // 리프레시 토큰에서 사용자 employeeSeq 추출
        Long employeeSeq = Long.parseLong(getEmployeeSeq(refreshToken));

        // 권한 생성
        CustomUserDetails customUserDetails = userDetailsService.loadUserBySeq(employeeSeq);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(customUserDetails, null,
                        authoritiesMapper.mapAuthorities(customUserDetails.getAuthorities()));

        log.info("액세스 토큰 재 발급 도중의 principal : " + authentication);

        return generateAccessToken(authentication);
    }

    // refreshToken 재발급
    public String reIssueRefreshToken(String employeeSeq) {

        long expirationTime = (long) 1000 * 60 * 60 * 24 * 7; // 7일
        Claims claims = Jwts.claims().setSubject(String.valueOf(employeeSeq));

        // 만료 시간 설정
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시간
                .signWith(key)
                .compact();
    }

    // 생성된 토큰 레디스에 저장
    public void saveToken(String Token) {

        String employeeSeq = getEmployeeSeq(Token);
        // JWT 디코딩하여 만료 시간 가져오기
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(Token)
                .getBody();

        // 만료 시간 (exp는 Unix timestamp 형식)
        long exp = claims.getExpiration().getTime() / 1000;  // 초 단위로 변환

        // 현재 시간과 만료 시간을 비교하여 TTL 계산
        long ttl = exp - (System.currentTimeMillis() / 1000);
        redisService.saveToken(employeeSeq, Token, ttl);
    }

}
