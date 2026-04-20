package org.spring.basic.exception;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 17:14:37
 */
public class MapperException extends RuntimeException {
    public MapperException(String message, MapperException ex) {
        super(message, ex);
    }
}