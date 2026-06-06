package org.spring.basic.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.basic.exception.MapperException;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 17:06:51
 */
public abstract class BaseMapper<E, RS> implements GenericMapper<E, RS> {
//    private static final Logger log = Logger.getLogger(BaseMapper.class.getName());
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMapper.class);

//    @Override
//    public Mono<E> fromRequestToEntity(RQ request) {
//        if (request == null) {
//            log.warning(String.format("Attempted to map null request to entity (%s)", getClass().getSimpleName()));
//            return Mono.error(new MapperException("Can not null request to entity"));
//        }
//        try {
//            E entity = mapToEntity(request);
//            return entity != null ? Mono.just(entity) : Mono.error(new MapperException("fromRequestToEntity returned null"));
//        } catch (MapperException ex) {
//            log.severe(String.format("Error mapping request to entity (%s), %s", getClass().getSimpleName(), ex));
//            return Mono.error(new MapperException("Failed to map Request to Entity", ex));
//        }
//    }

    @Override
    public Mono<RS> fromEntityToResponse(E entity) {
        if (entity == null) {
            LOGGER.warn("Attempted to map null entity to response ({})", getClass().getSimpleName());
            return Mono.error(new MapperException("Can not null entity to response"));
        }
        try {
            RS response = mapToResponse(entity);
            return response != null ? Mono.just(response) : Mono.error(new MapperException("mapToResponse returned null"));
        } catch (Exception ex) {
            LOGGER.error("Error mapping entity to response ({})", getClass().getSimpleName(), ex);
            return Mono.error(new MapperException("Failed to map Entity to Response", ex));
        }
    }

//    protected abstract E mapToEntity(RQ request);
    protected abstract RS mapToResponse(E entity);
}