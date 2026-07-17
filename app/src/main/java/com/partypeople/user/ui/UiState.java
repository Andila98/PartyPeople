package com.partypeople.user.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

/**
 * What a screen should render right now: LOADING, SUCCESS (with data),
 * EMPTY, or ERROR. One observable state instead of separate loading/error
 * flags that can contradict each other.
 */
public final class UiState<T> {

    public enum Status {
        LOADING,
        SUCCESS,
        EMPTY,
        ERROR
    }

    @NonNull
    private final Status status;
    @Nullable
    private final T data;
    @Nullable
    private final Throwable error;

    private UiState(@NonNull Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    @NonNull
    public static <T> UiState<T> loading() {
        return new UiState<>(Status.LOADING, null, null);
    }

    @NonNull
    public static <T> UiState<T> success(@NonNull T data) {
        return new UiState<>(Status.SUCCESS, Objects.requireNonNull(data), null);
    }

    @NonNull
    public static <T> UiState<T> empty() {
        return new UiState<>(Status.EMPTY, null, null);
    }

    @NonNull
    public static <T> UiState<T> error(@Nullable Throwable error) {
        return new UiState<>(Status.ERROR, null, error);
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    /** Non-null only when status is SUCCESS. */
    @Nullable
    public T getData() {
        return data;
    }

    /** Usually non-null when status is ERROR. */
    @Nullable
    public Throwable getError() {
        return error;
    }

    @Override
    @NonNull
    public String toString() {
        return "UiState." + status;
    }
}
