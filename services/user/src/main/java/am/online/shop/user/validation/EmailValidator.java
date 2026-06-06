package am.online.shop.user.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 01:48:41
 */
@Slf4j
@Component
public class EmailValidator implements FieldValidator<String> {
    private static final Pattern PATTERN = Pattern.compile("^.+@.+\\..+$");
    private final org.apache.commons.validator.routines.EmailValidator apacheEmailValidator;

    public EmailValidator() {
        apacheEmailValidator = org.apache.commons.validator.routines.EmailValidator.getInstance();
    }

//    public boolean isValid(String email) {
//        if (email == null || email.isBlank()) {
//            log.warn("Email can not be null or empty");
//            return false;
//        }
//
//        String normalizedEmail = email.trim();
//
//        if (normalizedEmail.length() > 254) {// RFC 5321 max length
//            log.warn("Email validation failed: too long ({})", normalizedEmail.length());
//            return false;
//        }
//
//        if (!PATTERN.matcher(normalizedEmail).matches()) {
//            log.warn("Email must contain @ and domain with dot");
//            return false;
//        }
//
//        if (normalizedEmail.contains("..")) {
//            log.warn("Email can not contain consecutive dots");
//            return false;
//        }
//
//        if (!apacheEmailValidator.isValid(normalizedEmail)) {
//            log.warn("Invalid email format");
//            return false;
//        }
//        return true;
//    }

    @Override
    public ValidationResult validate(String email) {
        if (email == null || email.isBlank()) {
            log.warn("Email can not be null or empty");
            return ValidationResult.failed("Email can not be null or empty");
        }

        String normalizedEmail = email.trim();

        if (normalizedEmail.length() > 254) {// RFC 5321 max length
            log.warn("Email validation failed: too long ({})", normalizedEmail.length());
            return ValidationResult.failed("Email validation failed: Email is too long");
        }

        if (!PATTERN.matcher(normalizedEmail).matches()) {
            log.warn("Email must contain @ and domain with dot");
            return ValidationResult.failed("Email must contain @ and domain with dot");
        }

        if (normalizedEmail.contains("..")) {
            log.warn("Email can not contain consecutive dots");
            return ValidationResult.failed("Email can not contain consecutive dots");
        }

        if (!apacheEmailValidator.isValid(normalizedEmail)) {
            log.warn("Invalid email format");
            return ValidationResult.failed("Invalid email format");
        }
        return ValidationResult.ok();
    }

//    public boolean isValid(String email) {
//        if (email == null || email.isBlank()) {
//            throw new ValidationException("Email can not be null or empty");
//        }
//
//        email = email.trim();
//
//        if (email.length() > 254) { // RFC 5321 max length
//            throw new ValidationException("Email is too long (max length 254)");
//        }
//
//        if (!PATTERN.matcher(email).matches()) {
//            throw new ValidationException("Email must contain @ and domain with dot");
//        }
//
//        if (email.contains("..")) {
//            throw new ValidationException("Email can not contain consecutive dots");
//        }
//
//        if (!apacheEmailValidator.isValid(email)) {
//            throw new ValidationException("Invalid email format");
//        }
//        return true;
//    }
}