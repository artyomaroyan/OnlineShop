package am.online.shop.user.validation;

/**
 * Author: Artyom Aroyan
 * Date: 05.06.26
 * Time: 22:57:07
 */
public record ValidationResult(boolean isValid, String reason) {
    public static ValidationResult ok() {
        return new ValidationResult(true, null);
    }

    public static ValidationResult failed(String reason) {
        return new ValidationResult(false, reason);
    }
}