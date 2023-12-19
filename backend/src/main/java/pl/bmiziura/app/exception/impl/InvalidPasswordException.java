package pl.bmiziura.app.exception.impl;


import pl.bmiziura.app.exception.ErrorCode;

public class InvalidPasswordException extends AppException {
    public static final int ERROR_CODE = ErrorCode.INVALID_PASSWORD_ERROR;

    public InvalidPasswordException(String email) {
        super(String.format("Password doesn't match (email: %s)", email), ERROR_CODE);
    }
}
