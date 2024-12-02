package spring.hi_hello_spring.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import spring.hi_hello_spring.security.util.CustomUserDetails;

import java.util.Collection;
import java.util.Optional;

@Component
public class CustomUserUtils {

    // 현재 인증된 사용자의 UserDetails 반환
    public static Optional<CustomUserDetails> getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return Optional.of((CustomUserDetails) authentication.getPrincipal());
        }
        return Optional.empty();
    }

    // 현재 인증된 사용자의 권한 반환
    public static Collection<? extends GrantedAuthority> getCurrentUserAuthorities() {
        return getCurrentUserDetails()
                .map(CustomUserDetails::getAuthorities)
                .orElse(null);
    }

    // 현재 인증된 사용자의 username 반환
    public static String getCurrentEmployeeNum() {
        return getCurrentUserDetails()
                .map(CustomUserDetails::getUsername) // 사원 사번 가져오기
                .map(username -> {
                    try {
                        return username;
                    } catch (NumberFormatException e) {
                        return null; // 변환 실패 시 null 반환
                    }
                })
                .orElse(null); // UserDetails가 없을 경우 null 반환
    }

    // 현재 인증된 사용자의 employeeSeq 반환
    public static Long getCurrentEmployeeSeq() {
        return getCurrentUserDetails()
                .map(CustomUserDetails::getEmployeeSeq)
                .map(employeeSeq -> {
                    try {
                        return employeeSeq;
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .orElse(null);
    }
}
