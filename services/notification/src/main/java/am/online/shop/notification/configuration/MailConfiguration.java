package am.online.shop.notification.configuration;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Author: Artyom Aroyan
 * Date: 12.05.26
 * Time: 02:24:55
 */
@Validated
@ConfigurationProperties("spring.mail")
public record MailConfiguration(
        @Positive int port,
        @NotBlank String host,
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String protocol,
        @AssertTrue boolean auth,
        @AssertTrue boolean enable,
        @AssertTrue boolean require
) {
}