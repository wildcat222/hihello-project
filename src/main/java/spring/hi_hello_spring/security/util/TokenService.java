package spring.hi_hello_spring.security.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;

    // Access Token 생성
    public String createAccessToken(String username, Authentication authentication) {
        return jwtUtil.generateAccessToken(username, authentication);
    }

    // Refresh Token 생성
    public String createRefreshToken(String username) {
        String refreshToken = jwtUtil.generateRefreshToken(username);
        return refreshToken;
    }

    // Refresh Token 저장

}
