package am.online.shop.user.validation;

/**
 * Author: Artyom Aroyan
 * Date: 05.06.26
 * Time: 22:56:56
 */
public interface FieldValidator<T> {
    ValidationResult validate(T value);
}