package spring.hi_hello_spring.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private final List<String> whitelist = new ArrayList<>();

    public SecurityProperties() {
        // Swagger UI paths
        whitelist.add("/v3/api-docs/**");
        whitelist.add("/swagger-ui/**");
        whitelist.add("/swagger-ui.html");
        whitelist.add("/swagger-resources/**");
        whitelist.add("/webjars/**");
        whitelist.add("/swagger-ui/index.html");
        whitelist.add("/swagger-ui/");
        whitelist.add("/v3/api-docs.yaml");
        whitelist.add("/");
        whitelist.add("/ws/**");

        // API paths
        whitelist.add("/api/v1/login");
        whitelist.add("/api/v1/test/health");
        whitelist.add("/actuator/prometheus");
    }

    public List<String> getWhitelist() {
        return whitelist;
    }
}
