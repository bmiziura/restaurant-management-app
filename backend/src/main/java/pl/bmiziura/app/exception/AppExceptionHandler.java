package pl.bmiziura.app.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.bmiziura.app.exception.impl.AppException;
import pl.bmiziura.app.exception.impl.MailTokenNotFoundException;
import pl.bmiziura.app.exception.impl.UserNotFoundException;

import java.util.Locale;

@RequiredArgsConstructor
@Slf4j
@ControllerAdvice
public class AppExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler({AppException.class})
    public ResponseEntity<ErrorResponse> handleAppException(AppException exception, Locale locale) {
        logError(exception);

        var message = getMessage(exception, locale);

        return ResponseEntity
                .status(exception.getHttpStatus() == null ? HttpStatus.BAD_REQUEST : exception.getHttpStatus())
                .body(new ErrorResponse(message));
    }

    @ExceptionHandler({UserNotFoundException.class, MailTokenNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(AppException exception, Locale locale) {
        logError(exception);

        var message = getMessage(exception, locale);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(message));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception, Locale locale) {
        logError(exception);

        var message = getMessage(exception, locale);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(message));
    }

    private void logError(RuntimeException exception) {
        log.debug("Exception thrown ", exception);
    }

    public String getMessage(RuntimeException exception, Locale locale) {
        var message = "";

        if (exception instanceof AppException appException) {
            if (appException.getErrorCode() != null)
                message = messageSource.getMessage("errorCode.".concat(String.valueOf(appException.getErrorCode())), null, locale);
        }

        if (message.isEmpty()) message = exception.getMessage();

        return message;
    }
}
