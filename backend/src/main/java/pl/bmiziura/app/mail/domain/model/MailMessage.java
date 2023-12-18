package pl.bmiziura.app.mail.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class MailMessage {
    private String recipient;
    private String subject;
    private String template;

    public abstract Map<String, Object> getModel();
}
