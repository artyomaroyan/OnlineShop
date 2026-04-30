package am.online.shop.user.exception;

/**
 * Author: Artyom Aroyan
 * Date: 01.05.26
 * Time: 00:30:25
 */
public class RSAKeyException extends RuntimeException {
    public RSAKeyException(String message, Exception ex) {
        super(message, ex);
    }
}