package spring.hi_hello_spring.security.filter;

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
            new AntPathRequestMatcher("/login"),
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

        if (refreshToken.isPresent() && jwtUtil.validateToken(refreshToken.get())) {
            String newAccessToken = jwtUtil.reIssueAccessToken(refreshToken.get());
            String userId = jwtUtil.getUserId(newAccessToken);
            String newRefreshToken = jwtUtil.generateRefreshToken(userId);

            response.setHeader("Authorization", newAccessToken);
            response.setHeader("Refresh-Token", newRefreshToken);

            log.info("Authorization {} ", newAccessToken);
            log.info("Refresh-Token {} ", newRefreshToken);

            jwtUtil.saveAuthentication(jwtUtil.getUserId(newAccessToken));

            filterChain.doFilter(request, response);
        }

        Optional<String> accessToken = jwtUtil.getToken(request, "access");

        if (accessToken.isPresent()) {
            if (jwtUtil.validateToken(accessToken.get())){
                jwtUtil.saveAuthentication(jwtUtil.getUserId(accessToken.get()));
                log.info("Authorization {} ", accessToken.get());
                filterChain.doFilter(request, response); // 다음 필터로 요청 전달
                return;
            }
        }

        // 모든 토큰이 유효하지 않은 경우 401 에러
        if (!response.isCommitted()) { // 응답이 커밋되지 않았을 때만 에러 전송
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid tokens");
        }

    }

}
