package pl.bmiziura.app.exception.impl;


import pl.bmiziura.app.exception.ErrorCode;

public class RegisterEmailTakenException extends AppException {
    public static final int ERROR_CODE = ErrorCode.REGISTER_EMAIL_ALREADY_USED_ERROR;

    public RegisterEmailTakenException(String email) {
        super(String.format("Email already taken (email: %s)", email), ERROR_CODE);
    }
}
