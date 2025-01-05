package spring.hi_hello_spring.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.security.config.SecurityProperties;
import spring.hi_hello_spring.security.util.JwtUtil;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Swagger UI 경로 체크
        boolean isWhitelisted = securityProperties.getWhitelist().stream()
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, path));

        if (isWhitelisted) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<String> refreshToken = jwtUtil.getToken(request, "refresh");

        try {
            if (refreshToken.isPresent() && jwtUtil.validateRefreshToken(refreshToken.get())) {
                String newAccessToken = jwtUtil.reIssueAccessToken(refreshToken.get());
                String employeeSeq = jwtUtil.getEmployeeSeq(newAccessToken);
                String newRefreshToken = jwtUtil.reIssueRefreshToken(employeeSeq);

                response.setHeader("accesstoken", newAccessToken);
                response.setHeader("refreshtoken", newRefreshToken);

                // 로그용 코드
                log.info("재발급 된 엑세스 토큰 {} ", newAccessToken);
                log.info("재발급 된 리프레시 토큰 {} ", newRefreshToken);

                jwtUtil.saveAuthentication(Long.parseLong(jwtUtil.getEmployeeSeq(newAccessToken)));

                // 재발급된 리프레시 토큰 레디스에 저장 (덮어쓰기)
                jwtUtil.saveToken(newRefreshToken);

                return;
            }
        } catch (Exception e) {
            log.error("Invalid Token: {}", e.getMessage());
            if (!response.isCommitted()) { // 응답이 커밋되지 않았을 때만 에러 전송
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "사용할 수 없는 토큰입니다.");
            }
            return;
        }

        Optional<String> accessToken = jwtUtil.getToken(request, "access");

        try {
            if (accessToken.isPresent()) {
                if (jwtUtil.validateAccessToken(accessToken.get())) {
                    jwtUtil.saveAuthentication(Long.parseLong(jwtUtil.getEmployeeSeq(accessToken.get())));
                    filterChain.doFilter(request, response); // 다음 필터로 요청 전달
                } else {
                    // 토큰 검증 실패 시 처리 추가
                    if (!response.isCommitted()) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write(new ObjectMapper().writeValueAsString(
                                new CustomException(ErrorCodeType.SECURITY_TOKEN_ERROR, "유효하지 않은 토큰입니다.")
                        ));
                    }
                }
            } else {
                // 토큰이 없는 경우 처리
                if (!response.isCommitted()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(
                            new CustomException(ErrorCodeType.SECURITY_TOKEN_ERROR, "토큰이 존재하지 않습니다.")
                    ));
                }
            }
        } catch (ExpiredJwtException e) {
            // 만료된 토큰 처리
            log.error("Expired JWT Token: {}", e.getMessage());
            if (!response.isCommitted()) { // 응답이 커밋되지 않았을 때만 에러 전송
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(
                        new CustomException(ErrorCodeType.SECURITY_TOKEN_ERROR, "엑세스 토큰이 만료되었습니다.")
                ));
            }
        }

    }
}
