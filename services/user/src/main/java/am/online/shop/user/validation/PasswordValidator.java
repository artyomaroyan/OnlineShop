package am.online.shop.user.validation;

import org.passay.*;
import org.spring.basic.exception.ValidationException;
import org.spring.basic.validation.GenericValidator;
import org.springframework.stereotype.Component;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 01:31:24
 */
@Component
public class PasswordValidator implements GenericValidator<String> {
    private static final PasswordValidator PASSWORD_VALIDATOR = new PasswordValidator();

    private final org.passay.PasswordValidator validator = new org.passay.PasswordValidator(
            new LengthRule(8, 32),
            new CharacterRule(EnglishCharacterData.UpperCase, 1),
            new CharacterRule(EnglishCharacterData.LowerCase, 1),
            new CharacterRule(EnglishCharacterData.Digit, 1),
            new CharacterRule(EnglishCharacterData.Special, 1),
            new WhitespaceRule()
    );

    @Override
    public String isValid(String password) {
        RuleResult result = validator.validate(new PasswordData(password));
        if (!result.isValid()) {
            throw new ValidationException(String.join(",", validator.getMessages(result)));
        }
        return password;
    }

    public static PasswordValidator getInstance() {
        return PASSWORD_VALIDATOR;
    }
}