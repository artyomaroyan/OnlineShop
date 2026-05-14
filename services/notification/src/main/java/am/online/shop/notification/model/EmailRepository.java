package am.online.shop.notification.model;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 13.05.26
 * Time: 16:53:58
 */
@Repository("emailRepository")
public interface EmailRepository extends R2dbcRepository<EmailEntity, UUID> {
}