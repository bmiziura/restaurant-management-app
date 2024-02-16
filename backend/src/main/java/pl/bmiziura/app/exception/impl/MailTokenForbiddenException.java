package pl.bmiziura.app.exception.impl;

import org.springframework.http.HttpStatus;
import pl.bmiziura.app.exception.ErrorCode;

public class MailTokenForbiddenException extends AppException {
    public static final int ERROR_CODE = ErrorCode.EMAIL_TOKEN_FORBIDDEN;
    public static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    public MailTokenForbiddenException(String email, String token) {
        super(String.format("Email Token Forbidden (email: %s, token: %s)", email, token), ERROR_CODE, HTTP_STATUS);
    }

    public MailTokenForbiddenException(String email) {
        super(String.format("Email Token Forbidden (email: %s)", email), ERROR_CODE, HTTP_STATUS);
    }

    public MailTokenForbiddenException(Long id) {
        super(String.format("Email Token Forbidden (id: %d)", id), ERROR_CODE, HTTP_STATUS);
    }
}
