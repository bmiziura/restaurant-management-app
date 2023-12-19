package pl.bmiziura.app.exception.impl;


import pl.bmiziura.app.exception.ErrorCode;

public class UserNotFoundException extends AppException {
    public static final int ERROR_CODE = ErrorCode.DEFAULT_ERROR;

    public UserNotFoundException(long id) {
        super(String.format("User not found! (id: %d)", id), ERROR_CODE);
    }

    public UserNotFoundException(String email) {
        super(String.format("User not found! (email: %s)", email), ERROR_CODE);
    }
}
