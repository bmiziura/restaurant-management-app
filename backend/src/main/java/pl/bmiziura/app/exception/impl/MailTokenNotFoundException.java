package pl.bmiziura.app.exception.impl;

import pl.bmiziura.app.exception.ErrorCode;

public class MailTokenNotFoundException extends AppException {
    public static final int ERROR_CODE = ErrorCode.REGISTER_EMAIL_ALREADY_USED_ERROR;

    public MailTokenNotFoundException(Long id, String token) {
        super(String.format("Email Token not found (userId: %s, token: %s)", id, token), ERROR_CODE);
    }
}
