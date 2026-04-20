package org.spring.basic.exception;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 01:15:01
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}