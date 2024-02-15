package pl.bmiziura.app.exception.impl;

import org.springframework.http.HttpStatus;
import pl.bmiziura.app.exception.ErrorCode;

public class MailSenderException extends AppException {
    public static final int ERROR_CODE = ErrorCode.EMAIL_SENDER_ERROR;
    public static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public MailSenderException(String email, Exception ex) {
        super(String.format("Something went wrong while sending an email (email: %s): %s", email, ex.getMessage()), ERROR_CODE, HTTP_STATUS);
    }

    public MailSenderException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }
}
