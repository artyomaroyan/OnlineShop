package am.online.shop.notification.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 18.05.26
 * Time: 00:06:32
 */
public record EmailResponse(UUID id, String mailTo, String mailFrom, LocalDateTime sendDate) {
}