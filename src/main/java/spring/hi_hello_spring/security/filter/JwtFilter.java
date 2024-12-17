package spring.hi_hello_spring.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.hi_hello_spring.security.util.JwtUtil;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final AntPathRequestMatcher[] SWAGGER_URLS = {
            new AntPathRequestMatcher("/"),
            new AntPathRequestMatcher("/api/v1/login"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/swagger-resources/**")
    };

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        for (AntPathRequestMatcher url : SWAGGER_URLS) {
            if (url.matches(request)) {
                filterChain.doFilter(request, response);
                return;
            }
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
        }

        Optional<String> accessToken = jwtUtil.getToken(request, "access");

        try {
            if (accessToken.isPresent()) {
                if (jwtUtil.validateAccessToken(accessToken.get())) {
                    jwtUtil.saveAuthentication(Long.parseLong(jwtUtil.getEmployeeSeq(accessToken.get())));
//                log.info("accessToken {} ", accessToken.get());
                    filterChain.doFilter(request, response); // 다음 필터로 요청 전달
                    return;
                }
            }
            filterChain.doFilter(request, response); // 다음 필터로 요청 전달
        } catch (ExpiredJwtException e) {
            // 만료된 토큰 처리
            log.error("Expired JWT Token: {}", e.getMessage());
            if (!response.isCommitted()) { // 응답이 커밋되지 않았을 때만 에러 전송
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "엑세스 토큰이 만료되었습니다.");
            }
        }

    }
}
