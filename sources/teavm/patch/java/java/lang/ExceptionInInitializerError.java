package java.lang;

public class ExceptionInInitializerError extends LinkageError {
    private final Throwable exception;

    public ExceptionInInitializerError() {
        super();
        this.exception = null;
    }

    public ExceptionInInitializerError(Throwable cause) {
        super(cause == null ? null : cause.toString());
        this.exception = cause;
    }

    public ExceptionInInitializerError(String s) {
        super(s);
        this.exception = null;
    }

    public Throwable getException() { return exception; }
    @Override public Throwable getCause() { return exception; }
}
