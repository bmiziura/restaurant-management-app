package pl.bmiziura.app.exception.impl;

import org.springframework.http.HttpStatus;
import pl.bmiziura.app.exception.ErrorCode;

public class MailTokenLimitException extends AppException {
    public static final int ERROR_CODE = ErrorCode.MAIL_TOKEN_LIMIT;
    public static final HttpStatus HTTP_STATUS = HttpStatus.TOO_MANY_REQUESTS;

    public MailTokenLimitException(Long userId, int count) {
        super(String.format("You reached the limit of email tokens (userId: %d, count: %d)", userId, count), ERROR_CODE, HTTP_STATUS);
    }
}
