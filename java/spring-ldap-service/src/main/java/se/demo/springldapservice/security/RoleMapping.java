package se.demo.springldapservice.security;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Validated
@ConfigurationProperties(prefix = "app.security.mappings")
@Setter
@Slf4j
@Component
public class RoleMapping {

    private static final String PREFIX = "ROLE_";
    @NotEmpty
    private Map<String, String> roles;

    @NotNull
    private String defaultRole;

    public String doRoleMap(final List<String> roleList) {
        return findRole(roleList)
                .map(roles::get)
                .map(role -> PREFIX + role)
                .orElse(PREFIX + defaultRole)
                .toUpperCase();
    }

    private Optional<String> findRole(final List<String> roleList) {
        return roleList.stream()
                       .map(String::toLowerCase)
                       .filter(roles::containsKey)
                       .peek(role -> log.debug("Found role [{}]", role))
                       .findFirst();
    }


}
