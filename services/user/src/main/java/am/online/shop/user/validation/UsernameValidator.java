package am.online.shop.user.validation;

import lombok.extern.slf4j.Slf4j;
import org.spring.basic.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 01:03:38
 */
@Slf4j
@Component
public class UsernameValidator {
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9._]{4,14}$");

    public boolean isValid(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username can not be null or empty!");
        }

        if (!PATTERN.matcher(username).matches()) {
            throw new ValidationException("Username contains invalid characters. (Valid -> lowercase, uppercase, numbers, dot underscore, 5 - 15 length)");
        }
        return true;
    }
}