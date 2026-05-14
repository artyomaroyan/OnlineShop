package am.online.shop.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
@Table(name = "notification", schema = "notification")
public class EmailEntity implements Persistable<UUID> {
    @Id
    private UUID id;
    private String to;
    private String from;
    private EmailType emailType;
    private LocalDateTime sendDate;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }
}