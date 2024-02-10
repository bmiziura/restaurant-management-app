package pl.bmiziura.app.exception.impl;


import org.springframework.http.HttpStatus;
import pl.bmiziura.app.exception.ErrorCode;

public class UserNotFoundException extends AppException {
    public static final int ERROR_CODE = ErrorCode.DEFAULT_ERROR;
    public static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public UserNotFoundException(long id) {
        super(String.format("User not found! (id: %d)", id), ERROR_CODE, HTTP_STATUS);
    }

    public UserNotFoundException(String email) {
        super(String.format("User not found! (email: %s)", email), ERROR_CODE, HTTP_STATUS);
    }
}
