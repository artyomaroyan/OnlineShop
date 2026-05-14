package am.online.shop.notification.service;

import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 13.05.26
 * Time: 16:08:53
 */
public interface MailSenderService {
    Mono<String> sendOtpEmail();
    Mono<String> sendWelcomeEmail();
    Mono<String> sendInformativeEmail();
    Mono<String> sendPasswordResetEmail();
}