package pl.bmiziura.app.exception.impl;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private Integer errorCode;
    private HttpStatus httpStatus;

    public AppException(String message, Integer errorCode) {
        this(message, errorCode, null);
    }

    public AppException(String message, Integer errorCode, HttpStatus httpStatus) {
        super(message);

        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
