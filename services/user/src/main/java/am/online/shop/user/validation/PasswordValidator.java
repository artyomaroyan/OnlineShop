package am.online.shop.user.validation;

import lombok.extern.slf4j.Slf4j;
import org.passay.*;
import org.springframework.stereotype.Component;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 01:31:24
 */
@Slf4j
@Component
public class PasswordValidator implements FieldValidator<String> {
    private static final org.passay.PasswordValidator VALIDATOR = new org.passay.PasswordValidator(
            new LengthRule(8, 32),
            new CharacterRule(EnglishCharacterData.UpperCase, 1),
            new CharacterRule(EnglishCharacterData.LowerCase, 1),
            new CharacterRule(EnglishCharacterData.Digit, 1),
            new CharacterRule(EnglishCharacterData.Special, 1),
            new WhitespaceRule()
    );

//    public boolean isValid(String password) {
//        if (password == null || password.trim().isEmpty()) {
//            log.warn("Password can not be null or empty");
//            return false;
//        }
//        RuleResult result = VALIDATOR.validate(new PasswordData(password));
//        if (!result.isValid()) {
//            log.warn(String.join(",", VALIDATOR.getMessages(result)));
//            return false;
//        }
//        return true;
//    }

    @Override
    public ValidationResult validate(String password) {
        if (password == null || password.trim().isEmpty()) {
            log.warn("Password can not be null or empty");
            return ValidationResult.failed("Password can not be null or empty");
        }
        RuleResult result = VALIDATOR.validate(new PasswordData(password));
        if (!result.isValid()) {
            log.warn(String.join(",", VALIDATOR.getMessages(result)));
            return ValidationResult.failed(VALIDATOR.getMessages(result).toString());
        }
        return ValidationResult.ok();
    }
}