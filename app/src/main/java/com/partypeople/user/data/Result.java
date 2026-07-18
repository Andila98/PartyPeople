package com.partypeople.user.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

/**
 * Outcome of a data operation: either a value or an error, never both.
 * Lets LiveData streams carry failures to the UI instead of swallowing them.
 */
public final class Result<T> {

    @Nullable
    private final T data;
    @Nullable
    private final Throwable error;

    private Result(@Nullable T data, @Nullable Throwable error) {
        this.data = data;
        this.error = error;
    }

    @NonNull
    public static <T> Result<T> success(@NonNull T data) {
        return new Result<>(Objects.requireNonNull(data), null);
    }

    @NonNull
    public static <T> Result<T> error(@NonNull Throwable error) {
        return new Result<>(null, Objects.requireNonNull(error));
    }

    public boolean isSuccess() {
        return error == null;
    }

    /** The value; null when {@link #isSuccess()} is false. */
    @Nullable
    public T getData() {
        return data;
    }

    /** The failure; null when {@link #isSuccess()} is true. */
    @Nullable
    public Throwable getError() {
        return error;
    }

    @Override
    @NonNull
    public String toString() {
        return isSuccess() ? "Result.Success(" + data + ")" : "Result.Error(" + error + ")";
    }
}
