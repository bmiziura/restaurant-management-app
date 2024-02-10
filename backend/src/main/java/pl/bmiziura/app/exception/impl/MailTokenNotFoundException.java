package pl.bmiziura.app.exception.impl;

import org.springframework.http.HttpStatus;
import pl.bmiziura.app.exception.ErrorCode;

public class MailTokenNotFoundException extends AppException {
    public static final int ERROR_CODE = ErrorCode.EMAIL_TOKEN_NOT_FOUND;
    public static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public MailTokenNotFoundException(String email, String token) {
        super(String.format("Email Token not found (email: %s, token: %s)", email, token), ERROR_CODE, HTTP_STATUS);
    }

    public MailTokenNotFoundException(Long id) {
        super(String.format("Email Token not found (id: %d)", id), ERROR_CODE, HTTP_STATUS);
    }
}
