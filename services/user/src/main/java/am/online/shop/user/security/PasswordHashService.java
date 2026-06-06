package am.online.shop.user.security;

import com.password4j.Argon2Function;
import com.password4j.Password;
import com.password4j.types.Argon2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 02:37:15
 */
@Service
public final class PasswordHashService implements PasswordEncoder {
    private final PasswordHashProperties properties;
    private final Argon2Function argon2Function;

    private PasswordHashService(PasswordHashProperties properties) {
        this.properties = properties;
        this.argon2Function = Argon2Function.getInstance(
                properties.memory(),
                properties.iterations(),
                properties.parallelism(),
                properties.hashLength(),
                Argon2.ID);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return Password.hash(rawPassword)
                .addRandomSalt(properties.saltLength())
                .with(argon2Function)
                .getResult();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Password.check(rawPassword, encodedPassword)
                .with(argon2Function);
    }
}