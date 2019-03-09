package de.heilsen.kartevonmorgen.api.entity;

import java.io.IOException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Data
public class Error {
    @EqualsAndHashCode(callSuper = true)
    @Value
    public static class HttpError extends Error {
        int statusCode;
        String message;
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    public static class IoError extends Error {
        public IOException ioException;
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    public static class UnknownError extends Error {
        public Throwable throwable;
    }
}
