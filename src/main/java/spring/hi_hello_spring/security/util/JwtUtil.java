package spring.hi_hello_spring.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final CustomUserDetailsService userDetailsService;
    private final Environment env;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final EmployeeRepository employeeRepository;


    public JwtUtil(
            @Value("${TOKEN_SECRET}") String secretKey,
            CustomUserDetailsService userDetailsService,
            Environment env,
            @Qualifier("employeeRepository") EmployeeRepository employeeRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userDetailsService;
        this.env = env;
        this.employeeRepository = employeeRepository;
    }

    /* Token 검증(Bearer 토큰이 넘어왔고, 우리 사이트의 secret key로 만들어 졌는가, 만료되었는지와 내용이 비어있진 않은지) */
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT Token claims empty {}", e);
        }

        return false;
    }

    /* 넘어온 AccessToken으로 인증 객체 추출 */
    public Authentication getAuthentication(String token) {

        /* 토큰을 들고 왔던 들고 오지 않았던(로그인 시) 동일하게 security 가 관리할 UserDetails 타입을 정의 */
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));

        /* 토큰에서 claim들 추출 */
        Claims claims = parseClaims(token);
        log.info("넘어온 AccessToken claims 확인: {}", claims);

        Collection<? extends GrantedAuthority> authorities = null;
        if(claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        } else {
             /* 클레임에서 권한 정보 가져오기 */
             authorities =
                     Arrays.stream(claims.get("auth").toString()
                                     .replace("[", "")
                                     .replace("]", "")
                                     .split(", "))
                             .map(SimpleGrantedAuthority::new)
                             .collect(Collectors.toList());
        }

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    /* Token 에서 Claims 추출 */
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /* Token 에서 사용자의 id(subject 클레임) 추출 */
    public String getUserId(String token) {
        return parseClaims(token).getSubject();
    }

    public String generateAccessToken(String username, Authentication authentication) {

        String accessToken = Jwts.builder()
                .setSubject(username)
                .claim("auth", authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(key)
                .compact();
        return accessToken;
    }

    public String generateRefreshToken(String username) {

        String refreshToken = Jwts.builder()
                .setSubject(username)
                .signWith(key)
                .setExpiration(new java.util.Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("refresh-token.expiration_time")))) // 만료 시간 설정
                .compact();
        return refreshToken;
    }

    // request 에서 리프레시 토큰을 추출
    public Optional<String> getToken(HttpServletRequest request, String tokenType) {

        String authorizationHeader;
        if (tokenType.equals("refresh")) authorizationHeader = request.getHeader("Refresh-Token");
        else authorizationHeader = request.getHeader("accessHeader");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return Optional.of(authorizationHeader.substring(7));
        }
        return Optional.empty();
    }

    // authentication 저장
    public void saveAuthentication(String userId) {
        System.out.println("UserId: " + userId);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // 엑세스 토큰 재발급
    public String reIssueAccessToken(String refreshToken) {
        // 리프레시 토큰에서 사용자 ID 추출
        String userId = getUserId(refreshToken);
        Employee findUser = employeeRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));

        // 새로운 액세스 토큰 생성
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        return generateAccessToken(userId, authentication);
    }

}
