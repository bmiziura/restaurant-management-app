package pl.bmiziura.app.exception.impl;

import pl.bmiziura.app.exception.ErrorCode;

public class MailTokenNotFoundException extends AppException {
    public static final int ERROR_CODE = ErrorCode.REGISTER_EMAIL_ALREADY_USED_ERROR;

    public MailTokenNotFoundException(String email, String token) {
        super(String.format("Email Token not found (email: %s, token: %s)", email, token), ERROR_CODE);
    }
}
