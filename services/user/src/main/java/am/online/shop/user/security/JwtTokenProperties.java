package am.online.shop.user.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Author: Artyom Aroyan
 * Date: 01.05.26
 * Time: 00:19:16
 */
@Validated
@ConfigurationProperties("spring.security.jwt-token")
record JwtTokenProperties(
        @NotBlank String issuer,
        @Positive Long expiration,
        @NotBlank String algorithm,
        @NotBlank String privateKeyPath,
        @NotBlank String publicKeyPath
) {
}