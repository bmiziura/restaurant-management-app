package pl.bmiziura.app.mail.domain.model;

import pl.bmiziura.app.construction.model.entity.MailTokenEntity;
import pl.bmiziura.app.user.domain.model.UserAccount;

import java.util.HashMap;
import java.util.Map;

public class AccountConfirmMailMessage extends MailMessage {

    private final UserAccount user;
    private final MailTokenEntity token;

    public AccountConfirmMailMessage(UserAccount user, MailTokenEntity token) {
        super(user.getEmail(), "Potwierdź konto", "account-created.ftl");

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
