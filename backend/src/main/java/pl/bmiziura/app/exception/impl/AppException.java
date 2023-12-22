package pl.bmiziura.app.exception.impl;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private Integer errorCode;

    public AppException(String message, Integer errorCode) {
        super(message);

        this.errorCode = errorCode;
    }
}
