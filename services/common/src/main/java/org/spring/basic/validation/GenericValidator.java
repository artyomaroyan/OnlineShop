package org.spring.basic.validation;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 00:50:37
 */
public interface GenericValidator<T> {
    String isValid(T t);
}