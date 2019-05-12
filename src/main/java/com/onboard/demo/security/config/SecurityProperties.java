package com.onboard.demo.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityProperties {

    private final Authentication authentication = new Authentication();

    /**
     * Authentication.
     */
    @Data
    public static class Authentication {

        private final Jwt jwt = new Jwt();

        /**
         * Jwt.
         */
        @Data
        public static class Jwt {

            private String secret;

            private String base64Secret;

            private long tokenValidityInSeconds;

            private long tokenValidityInSecondsForRememberMe;
        }
    }
}