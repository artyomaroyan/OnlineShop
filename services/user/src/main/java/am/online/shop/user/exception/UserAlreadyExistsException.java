package am.online.shop.user.exception;

/**
 * Author: Artyom Aroyan
 * Date: 23.04.26
 * Time: 14:46:22
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}