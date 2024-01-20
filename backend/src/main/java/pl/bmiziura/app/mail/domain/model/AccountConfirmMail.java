package pl.bmiziura.app.mail.domain.model;

import org.springframework.core.io.Resource;
import pl.bmiziura.app.construction.model.entity.MailTokenEntity;
import pl.bmiziura.app.user.domain.model.UserAccount;

import java.util.HashMap;
import java.util.Map;

public class AccountConfirmMail extends Mail {

    private final UserAccount user;
    private final MailTokenEntity token;

    public AccountConfirmMail(UserAccount user, MailTokenEntity token, Resource logoFile) {
        super("account-created.ftl", "Account Confirmation");

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
