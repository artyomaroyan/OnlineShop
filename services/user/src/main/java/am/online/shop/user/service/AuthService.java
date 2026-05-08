package am.online.shop.user.service;

import am.online.shop.user.model.AuthRequest;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 07.05.26
 * Time: 15:59:16
 */
public interface AuthService {
    Mono<String> login(AuthRequest request);
}