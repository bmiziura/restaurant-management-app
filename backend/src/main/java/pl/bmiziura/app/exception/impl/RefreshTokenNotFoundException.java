package pl.bmiziura.app.exception.impl;


import org.springframework.http.HttpStatus;
import pl.bmiziura.app.exception.ErrorCode;

public class RefreshTokenNotFoundException extends AppException {
    public static final int ERROR_CODE = ErrorCode.EMAIL_TOKEN_NOT_FOUND;
    public static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public RefreshTokenNotFoundException(long id) {
        super(String.format("Refresh Token not found! (id: %d)", id), ERROR_CODE, HTTP_STATUS);
    }

    public RefreshTokenNotFoundException(String token) {
        super(String.format("Refresh Token not found! (token: %s)", token), ERROR_CODE, HTTP_STATUS);
    }
}
