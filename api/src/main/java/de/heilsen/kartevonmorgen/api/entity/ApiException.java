package de.heilsen.kartevonmorgen.api.entity;

import java.io.IOException;

import retrofit2.HttpException;

public class ApiException extends RuntimeException {
    private final Kind kind;

    public ApiException(String message, Kind kind, Throwable exception) {
        super(message, exception);
        this.kind = kind;
    }

    public static ApiException from(Throwable throwable) {
        // 2xy HTTP error
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            return ApiException.httpError(httpException.response().message());
        }
        // network error happened
        if (throwable instanceof IOException) {
            return ApiException.networkError((IOException) throwable);
        }

        // We don't know what happened. We need to simply convert to an unknown error
        return ApiException.unexpectedError(throwable);
    }

    public static ApiException httpError(String message) {
        return new ApiException(message, Kind.HTTP, null);
    }

    public static ApiException networkError(IOException exception) {
        return new ApiException(exception.getMessage(), Kind.NETWORK, exception);
    }

    public static ApiException unexpectedError(Throwable exception) {
        return new ApiException(exception.getMessage(), Kind.UNEXPECTED, exception);
    }

    public Kind getKind() {
        return kind;
    }

    /**
     * Identifies the event kind which triggered a {@link ApiException}.
     */
    public enum Kind {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}