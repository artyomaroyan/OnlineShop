package am.online.shop.user.configuration;

import am.online.shop.user.model.UserResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Author: Artyom Aroyan
 * Date: 26.04.26
 * Time: 23:18:16
 */
@Configuration
public class RedisConfiguration {

    @Bean
    public ReactiveRedisTemplate<String, UserResponse> redisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        JacksonJsonRedisSerializer<UserResponse> valueSerializer = new JacksonJsonRedisSerializer<>(UserResponse.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, UserResponse> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, UserResponse> context = builder.value(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }
}