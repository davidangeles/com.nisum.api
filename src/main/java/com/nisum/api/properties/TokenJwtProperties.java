package com.nisum.api.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class TokenJwtProperties {

    private String issuer;

    private Map<TokenType, TokenProperties> types;

    public enum TokenType {
        USER,
        SECURITY
    }

    @Getter
    @Setter
    public static class TokenProperties {

        private String secret;
        private Long expiredTime;
    }
}
