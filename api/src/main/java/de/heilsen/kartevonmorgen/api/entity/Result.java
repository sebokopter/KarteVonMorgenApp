package de.heilsen.kartevonmorgen.api.entity;

import javax.annotation.Nullable;

public class Result<T> {
    private final T success;
    private final Error error;

    private Result(@Nullable T success, @Nullable Error error) {
        this.success = success;
        this.error = error;
    }

    @Nullable
    public T success() {
        return success;
    }

    @Nullable
    public Error error() {
        return error;
    }

    public boolean isError() {
        return error != null;
    }
}