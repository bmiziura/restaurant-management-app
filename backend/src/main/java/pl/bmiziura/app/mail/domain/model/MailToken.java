package pl.bmiziura.app.mail.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.bmiziura.app.construction.model.MailTokenStatus;
import pl.bmiziura.app.construction.model.MailTokenType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MailToken {
    private Long id;
    private String token;
    private MailTokenType type;
    private LocalDateTime expireTime;
    private MailTokenStatus status;
}
