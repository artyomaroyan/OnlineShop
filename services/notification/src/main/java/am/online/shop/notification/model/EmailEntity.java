package am.online.shop.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 13.05.26
 * Time: 15:30:58
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email", schema = "notifications")
public class EmailEntity implements Persistable<UUID> {
    @Id
    private UUID id;
    private String mailTo;
    private String mailFrom;
    private EmailType emailType;
    private LocalDateTime sendDate;

    @Version
    private Long version;

    @Override
    public boolean isNew() {
        return version == null;
    }
}