package pl.bmiziura.app.mail.domain.service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import pl.bmiziura.app.mail.domain.model.MailMessage;

import java.io.IOException;
import java.io.StringWriter;

@Component
@RequiredArgsConstructor
public class MailService {
    private final Configuration configuration;
    private final JavaMailSender mailSender;

    public void sendMail(MailMessage message) throws MessagingException, IOException, TemplateException {
        var mimeMessage = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom("no-reply@restaurant.com");
        helper.setSubject(message.getSubject());
        helper.setTo(message.getRecipient());

        var emailContent = getContent(message);
        helper.setText(emailContent, true);

        mailSender.send(mimeMessage);
    }

    private String getContent(MailMessage message) throws IOException, TemplateException {
        var stringWriter = new StringWriter();
        var model = message.getModel();

        configuration.getTemplate(message.getTemplate()).process(model, stringWriter);

        return stringWriter.getBuffer().toString();
    }
}
