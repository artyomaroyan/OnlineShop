package am.online.shop.user.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Author: Artyom Aroyan
 * Date: 23.04.26
 * Time: 11:38:16
 */
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Bean
    protected SecurityWebFilterChain filterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/webjars/**", "/webjars/swagger-ui/**", "/v2/api-docs", "/v3/api-docs/",
                                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources",
                                "/swagger-resources/**", "/configuration/ui", "/configuration/security")
                            .permitAll()
                        .pathMatchers("/api/v1/users/onboarding", "/api/v1/users/get/by/**")
                            .permitAll()
                        .anyExchange()
                            .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}