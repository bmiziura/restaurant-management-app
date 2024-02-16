package pl.bmiziura.app.exception;

public class ErrorCode {
    public static final int DEFAULT_ERROR = 1000;

    public static final int USER_NOT_FOUND_ERROR = 2000;
    public static final int REGISTER_EMAIL_ALREADY_USED_ERROR = 2001;
    public static final int INVALID_PASSWORD_ERROR = 2002;
    public static final int EMAIL_TOKEN_NOT_FOUND = 2003;
    public static final int ACCOUNT_ALREADY_ACTIVATED = 2004;
    public static final int MAIL_TOKEN_LIMIT = 2005;
    public static final int EMAIL_SENDER_ERROR = 2006;
    public static final int EMAIL_TOKEN_FORBIDDEN = 2007;
}
