package am.online.shop.user.security;

import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Author: Artyom Aroyan
 * Date: 05.06.26
 * Time: 23:38:27
 */
@ConfigurationProperties("spring.security.argon2-hash")
public record PasswordHashProperties(
        @Positive int memory,
        @Positive int iterations,
        @Positive int parallelism,
        @Positive int hashLength,
        @Positive int saltLength
) {
}