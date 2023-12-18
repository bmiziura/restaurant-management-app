package pl.bmiziura.app.construction.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.bmiziura.app.construction.model.MailTokenType;

import java.time.LocalDateTime;

@Entity
@Table(name = "mail_tokens")
public class MailTokenEntity {
    private static final String SEQUENCE_NAME = "mail_tokens_id_seq";

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MailTokenType type;

    @Column(name = "create_time")
    @CreationTimestamp
    private LocalDateTime createTime;

    @Column(name = "expire_time")
    private LocalDateTime expireTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", referencedColumnName = "id")
    private UserAccountEntity user;
}
