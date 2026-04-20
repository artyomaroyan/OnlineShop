package org.spring.basic.mapper;

import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 17:02:05
 */
public interface GenericMapper<E, RQ, RS> {
    Mono<E> fromRequestToEntity(RQ request);
    Mono<RS> fromEntityToResponse(E entity);
}