package de.heilsen.kartevonmorgen.app.poi.map.model;

//TODO: use Lombok?
public class ErrorState {
    public static final ErrorState NO_ERROR = new ErrorState(null, false);

    private String errorMessage;
    private boolean isError;

    public ErrorState(String errorMessage, boolean isError) {
        this.errorMessage = errorMessage;
        this.isError = isError;
    }
    public ErrorState(String errorMessage) {
        this(errorMessage, true);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isError() {
        return isError;
    }
}
