package spring.hi_hello_spring.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import spring.hi_hello_spring.security.util.JwtUtil;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
//        log.info("로그인 성공 후 security 가 관리하는 principal 객체 : {}", authentication);

        // Access Token 생성
        String accessToken = jwtUtil.generateAccessToken(authentication);

        // Refresh Token 생성
        String refreshToken = jwtUtil.generateRefreshToken(authentication);

        // 응답 헤더에 토큰 추가
        response.setHeader("accessToken", accessToken);
        response.setHeader("refreshToken", refreshToken);

        // 레디스에 리프레시 토큰 저장
        jwtUtil.saveToken(refreshToken);
    }
}
