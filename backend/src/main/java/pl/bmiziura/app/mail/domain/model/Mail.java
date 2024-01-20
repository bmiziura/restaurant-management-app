package pl.bmiziura.app.mail.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Mail {
    private final String template;
    private final String subject;

    private Set<String> recipients = new HashSet<>();

    private HashMap<String, Resource> attachments = new HashMap<>();

    public void addRecipient(String email) {
        recipients.add(email);
    }

    public void addAttachment(String fileName, Resource file) {
        attachments.put(fileName, file);
    }

    public abstract Map<String, Object> getModel();
}
