package am.online.shop.user.security;

import com.password4j.Argon2Function;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Argon2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 02:37:15
 */
@Component
public record PasswordHashService(
        @Value("${spring.security.argon2-hash.memory}")
        int memory,
        @Value("${spring.security.argon2-hash.iterations}")
        int iterations,
        @Value("${spring.security.argon2-hash.parallelism}")
        int parallelism,
        @Value("${spring.security.argon2-hash.hash-length}")
        int hashLength,
        @Value("${spring.security.argon2-hash.salt-length}")
        int saltLength
) implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        final Hash hash = Password
                .hash(rawPassword)
                .addRandomSalt(saltLength)
                .with(Argon2Function.getInstance(memory, iterations, parallelism, hashLength, Argon2.ID));
        return hash.getResult();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Password.check(rawPassword, encodedPassword)
                .with(Argon2Function.getInstance(memory, iterations, parallelism, hashLength, Argon2.ID));
    }
}