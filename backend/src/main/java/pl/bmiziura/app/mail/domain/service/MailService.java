package pl.bmiziura.app.mail.domain.service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import pl.bmiziura.app.mail.domain.model.Mail;

import java.io.IOException;
import java.io.StringWriter;

@Component
@RequiredArgsConstructor
public class MailService {
    private final Configuration configuration;
    private final JavaMailSender mailSender;

    public void sendMail(Mail message) throws MessagingException, IOException, TemplateException {
        var mimeMessage = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("no-reply@restaurant.com");
        helper.setSubject(message.getSubject());
        helper.setTo(message.getRecipients().toArray(new String[0]));

        var emailContent = getContent(message);
        helper.setText(emailContent, true);

        message.getAttachments().forEach((key, value) -> {
            try {
                helper.addInline(key, value);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
        mailSender.send(mimeMessage);
    }

    private String getContent(Mail message) throws IOException, TemplateException {
        var stringWriter = new StringWriter();
        var model = message.getModel();

        configuration.getTemplate(message.getTemplate()).process(model, stringWriter);

        return stringWriter.getBuffer().toString();
    }
}
