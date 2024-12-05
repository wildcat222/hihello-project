package spring.hi_hello_spring.employee.command.application.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.common.util.RedisService;

import static spring.hi_hello_spring.common.util.CustomUserUtils.getCurrentEmployeeSeq;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final RedisService redisService;
    private final CustomUserUtils customUserUtils;

    @Value("${token.secret}")
    private String key;

    public void logout(String accessToken) {

        String EmployeeSeq = String.valueOf(getCurrentEmployeeSeq());
        System.out.println("토큰에서 빼온 시퀀스 = " + EmployeeSeq);

        redisService.deleteToken(EmployeeSeq);

        if (accessToken != null) {

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();

            // 만료 시간 (exp는 Unix timestamp 형식)
            long exp = claims.getExpiration().getTime() / 1000;  // 초 단위로 변환

            // 현재 시간과 만료 시간을 비교하여 TTL 계산
            long ttl = exp - (System.currentTimeMillis() / 1000);

            redisService.saveToken(accessToken, EmployeeSeq, ttl);
        }

    }
}
