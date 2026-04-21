package am.online.shop.user.validation;

import lombok.extern.slf4j.Slf4j;
import org.spring.basic.exception.ValidationException;
import org.spring.basic.validation.GenericValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 01:48:41
 */
@Slf4j
@Component
public class EmailValidator implements GenericValidator<String> {
    private static final EmailValidator EMAIL_VALIDATOR = new EmailValidator();

    private static final Pattern PATTERN = Pattern.compile("^.+@.+\\..+$");

    @Override
    public String isValid(String email) {
        if (email == null || email.isBlank()) {
            throw new ValidationException("Email can not be null or empty");
        }

        email = email.trim();

        if (email.length() > 254) { // RFC 5321 max length
            throw new ValidationException("Email is too long (max length 254)");
        }

        if (!PATTERN.matcher(email).matches()) {
            throw new ValidationException("Email must contain @ and domain with dot");
        }

        if (email.contains("..")) {
            throw new ValidationException("Email can not contain consecutive dots");
        }

        if (org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email)) {
            throw new ValidationException("Invalid email format");
        }
        return email;
    }

    public static EmailValidator getInstance() {
        return EMAIL_VALIDATOR;
    }
}