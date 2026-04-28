package am.online.shop.user.exception;

/**
 * Author: Artyom Aroyan
 * Date: 26.04.26
 * Time: 21:53:51
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}