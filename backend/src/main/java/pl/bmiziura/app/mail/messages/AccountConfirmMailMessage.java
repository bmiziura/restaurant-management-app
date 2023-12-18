package pl.bmiziura.app.mail.messages;

import pl.bmiziura.app.user.domain.model.UserAccount;

import java.util.HashMap;
import java.util.Map;

public class AccountConfirmMailMessage extends MailMessage {

    private final UserAccount user;

    public AccountConfirmMailMessage(UserAccount user) {
        super(user.getEmail(), "Potwierdź konto", "account-created.ftl");

        this.user = user;
    }

    @Override
    public Map<String, Object> getModel() {
        var model = new HashMap<String, Object>();

        model.put("user", user);

        return model;
    }
}
