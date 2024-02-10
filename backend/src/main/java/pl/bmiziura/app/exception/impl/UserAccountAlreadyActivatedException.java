package pl.bmiziura.app.exception.impl;

import pl.bmiziura.app.exception.ErrorCode;

public class UserAccountAlreadyActivatedException extends AppException {
    public static final int ERROR_CODE = ErrorCode.ACCOUNT_ALREADY_ACTIVATED;

    public UserAccountAlreadyActivatedException(String email) {
        super(String.format("Account is already activated (email: %s)", email), ERROR_CODE);
    }
}
