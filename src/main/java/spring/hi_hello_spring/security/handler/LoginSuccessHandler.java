package spring.hi_hello_spring.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import spring.hi_hello_spring.security.util.TokenService;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("로그인 성공 후 security 가 관리하는 principal 객체 : {}", authentication);

        // 사용자 이름 가져오기
        String username = authentication.getName();

        // Access Token 생성
        String accessToken = tokenService.createAccessToken(username, authentication);

        // Refresh Token 생성
        String refreshToken = tokenService.createRefreshToken(username);

        // 응답 헤더에 토큰 추가
        response.setHeader("Authorization", accessToken);
        response.setHeader("Refresh-Token", refreshToken);

    }
}
