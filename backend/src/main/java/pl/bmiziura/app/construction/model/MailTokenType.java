package pl.bmiziura.app.construction.model;

import lombok.Getter;

@Getter
public enum MailTokenType {
    ACCOUNT_CONFIRMATION(3),
    PASSWORD_CHANGE(2);

    int limit;

    MailTokenType(int limit) {
        this.limit = limit;
    }

}
