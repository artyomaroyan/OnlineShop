package am.online.shop.notification.service;

import am.online.shop.notification.model.EmailEntity;
import am.online.shop.notification.model.EmailType;
import am.online.shop.notification.security.OtpGeneratorService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Author: Artyom Aroyan
 * Date: 13.05.26
 * Time: 16:22:52
 */
@Slf4j
@Component
record MailFactory(JavaMailSender javaMailSender) {
    private static final String FROM = "online_shop@gmail.com";

    public Mono<String> send(EmailType type, String... parameter) {
        return sendMail(parameter[0], type);
    }

    private Mono<String> sendMail(String to, EmailType type) {
        return Mono.fromCallable(() -> {
                    MimeMessage message = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8.name());

                    helper.setFrom(FROM);
                    helper.setTo(to);
                    helper.setSubject(createSubject(type));
                    helper.setText(createMessageBody(type));

                    javaMailSender.send(message);
                    return "Email sent successfully to: " + to;
                })
                .doOnError(MessagingException.class, ex ->
                        log.error("Failed to send {} email to {}", type, to, ex))
                .onErrorMap(MessagingException.class, ex ->
                        new RuntimeException("Failed to send email", ex));
    }

    private String createSubject(EmailType type) {
        return switch (type) {
            case OTP -> "OTP verification email";
            case VALIDATED -> "Account validated email";
            case INFORMATIVE -> "General information email";
            case PASSWORD_RESET -> "Password reset email";
        };
    }

    private String createMessageBody(EmailType type) {
        return switch (type) {
            case OTP -> createOtpBody();
            case VALIDATED -> createValidatedBody();
            case INFORMATIVE -> createInformativeBody();
            case PASSWORD_RESET -> createPasswordResetBody();
        };
    }

    private String createPasswordResetBody() {
        return String.format("To reset your password please follow this link: %s", "here should be link!");
    }

    private String createInformativeBody() {
        return "Here should be some informative mail, for example discounts, gifts and so on...";
    }

    private String createValidatedBody() {
        return "Tank you for registration, your account was successfully activated";
    }

    private String createOtpBody() {
        return String.format("To confirm your registration please confirm your account with this OTP: %s",
                OtpGeneratorService.randomOtp());
    }
}