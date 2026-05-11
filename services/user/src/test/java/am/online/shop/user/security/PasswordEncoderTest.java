package am.online.shop.user.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Author: Artyom Aroyan
 * Date: 08.05.26
 * Time: 21:14:18
 */
@SpringBootTest
class PasswordEncoderTest {
    @Autowired
    private PasswordHashService passwordHashService;

    @Test
    void passwordShouldMatch() {
        String raw = "user1";
        String encoded = passwordHashService.encode(raw);
        System.out.println("Encoded hash: " + encoded);
        assertTrue(passwordHashService.matches(raw, encoded));
    }

    @Test
    void wrongPasswordShouldNotMatch() {
        String encoded = passwordHashService.encode("correctPassword");
        assertFalse(passwordHashService.matches("wrongPassword", encoded));
    }
}