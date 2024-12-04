package spring.hi_hello_spring.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.util.RedisService;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.security.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;
    private final EmployeeRepository employeeRepository;
    private final RedisService redisService;

    // Access Token 생성
    public String createAccessToken(String username, Authentication authentication) {

        Employee loginUser = employeeRepository.findByEmployeeNum(username)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND)); // 예외 처리 어찌할건지

        Long EmployeeSeq = loginUser.getEmployeeSeq();
        return jwtUtil.generateAccessToken(EmployeeSeq, authentication);
    }

    // Refresh Token 생성
    public String createRefreshToken(String username) {

        Employee loginUser = employeeRepository.findByEmployeeNum(username)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND)); // 예외 처리 어찌할건지

        return jwtUtil.generateRefreshToken(loginUser.getEmployeeSeq());
    }

    // RefreshToken 을 Redis에 저장
    public void saveRefreshToken(String refreshToken) {
        jwtUtil.saveRefreshToken(refreshToken);
    }

//    // 엑세스 토큰 검증
//    public boolean verifyAccessToken(String token) {
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(ACCESS_TOKEN_SECRET)
//                    .parseClaimsJws(token)
//                    .getBody();
//            return claims.getExpiration().after(new Date()); // 만료 체크
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    // 리프레시 토큰 검증
//    public boolean verifyRefreshToken(String refreshToken) {
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(REFRESH_TOKEN_SECRET)
//                    .parseClaimsJws(refreshToken)
//                    .getBody();
//            return claims.getExpiration().after(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    // 리프레시 토큰 로테이션 (새 엑세스 토큰과 리프레시 토큰 발급)
//    public TokenPair rotateTokens(String userId) {
//        String newAccessToken = createAccessToken(userId);
//        String newRefreshToken = createRefreshToken(userId);
//
//        redisService.saveRefreshToken(userId, newRefreshToken);
//
//        return new TokenPair(newAccessToken, newRefreshToken);
//    }

}
