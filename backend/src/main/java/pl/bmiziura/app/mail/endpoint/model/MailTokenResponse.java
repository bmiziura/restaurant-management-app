package pl.bmiziura.app.mail.endpoint.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.bmiziura.app.construction.model.MailTokenStatus;
import pl.bmiziura.app.construction.model.MailTokenType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class MailTokenResponse {
    private Long id;
    private String token;
    private MailTokenType type;
    private LocalDateTime expireTime;
    private MailTokenStatus status;
}
