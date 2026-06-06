package am.online.shop.user.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Artyom Aroyan
 * Date: 06.06.26
 * Time: 00:02:36
 */
@Configuration
@EnableConfigurationProperties({
        JwtTokenProperties.class,
        PasswordHashProperties.class
})
public class PropertiesConfiguration {
}