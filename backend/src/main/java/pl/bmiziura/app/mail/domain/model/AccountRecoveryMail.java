package pl.bmiziura.app.mail.domain.model;

import org.springframework.core.io.Resource;
import pl.bmiziura.app.construction.model.entity.MailTokenEntity;
import pl.bmiziura.app.user.domain.model.UserAccount;

import java.util.HashMap;
import java.util.Map;

public class AccountRecoveryMail extends Mail {

    private final UserAccount user;
    private final MailTokenEntity token;

    public AccountRecoveryMail(UserAccount user, MailTokenEntity token, Resource logoFile) {
        super("account-recovery.ftl", "Account Recovery");

        addRecipient(user.getEmail());

        addAttachment("logo.png", logoFile);

        this.user = user;
        this.token = token;
    }

    @Override
    public Map<String, Object> getModel() {
        var model = new HashMap<String, Object>();

        model.put("user", user);
        model.put("token", token);

        return model;
    }
}
