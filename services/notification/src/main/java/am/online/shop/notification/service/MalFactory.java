package am.online.shop.notification.service;

import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 13.05.26
 * Time: 16:22:52
 */
record MalFactory() {

    static Mono<String> send(String... parameter) {
        return null;
    }
}