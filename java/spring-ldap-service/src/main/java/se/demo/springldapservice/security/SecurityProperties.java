package se.demo.springldapservice.security;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    @NotNull
    private Credentials credentials;

    @NotNull
    private Ldap ldap;

    @Data
    static class Credentials {

        @NotEmpty
        private String user;
        @NotEmpty
        private String password;

        @ToString.Include
        private String password() {
            return password != null ? "#####" : null;
        }
    }

    @Data
    static class Ldap {

        private String url;

        private String searchBase;

        private String user;

        private String password;

        @ToString.Include
        private String password() {
            return password != null ? "#####" : null;
        }
    }


}
