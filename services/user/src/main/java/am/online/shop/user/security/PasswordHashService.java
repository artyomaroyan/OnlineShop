package am.online.shop.user.security;

import com.password4j.Hash;
import com.password4j.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 02:37:15
 */
@Component
public record PasswordHashService() implements PasswordEncoder {
    private static final PasswordHashService PASSWORD_HASHING = new PasswordHashService();

    @Override
    public String encode(CharSequence rawPassword) {
        final Hash hash = Password.hash(rawPassword).addPepper().addRandomSalt().withArgon2();
        return hash.getResult();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Password.check(rawPassword, encodedPassword).withArgon2();
    }

    public static PasswordHashService getInstance() {
        return PASSWORD_HASHING;
    }
}