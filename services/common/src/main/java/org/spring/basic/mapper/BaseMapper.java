package org.spring.basic.mapper;

import org.spring.basic.exception.MapperException;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 17:06:51
 */
public abstract class BaseMapper<E, RQ, RS> implements GenericMapper<E, RQ, RS> {
    private static final Logger log = Logger.getLogger(BaseMapper.class.getName());

    @Override
    public Mono<E> fromRequestToEntity(RQ request) {
        if (request == null) {
            log.warning(String.format("Attempted to map null request to entity (%s)", getClass().getSimpleName()));
            return Mono.empty();
        }
        try {
            E entity = mapToEntity(request);
            return entity != null ? Mono.just(entity) : Mono.empty();
        } catch (MapperException ex) {
            log.severe(String.format("Error mapping request to entity (%s), %s", getClass().getSimpleName(), ex));
            return Mono.error(new MapperException("Failed to map Request to Entity", ex));
        }
    }

    @Override
    public Mono<RS> fromEntityToResponse(E entity) {
        if (entity == null) {
            log.warning(String.format("Attempted to map null entity to response (%s)", getClass().getSimpleName()));
            return Mono.empty();
        }
        try {
            RS response = mapToResponse(entity);
            return response != null ? Mono.just(response) : Mono.empty();
        } catch (MapperException ex) {
            log.severe(String.format("Error mapping entity to response (%s), %s", getClass().getSimpleName(), ex));
            return Mono.error(new MapperException("Failed to map Entity to Response", ex));
        }
    }

    protected abstract E mapToEntity(RQ request);
    protected abstract RS mapToResponse(E entity);
}