package com.ll.chat_ai.common.rsData;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class resultData<T> {
    private final String resultCode;
    private final String msg;
    private final T data;
    private final int statusCode;

    public static <T> resultData<T> of(String resultCode, String msg, T data) {
        int statusCode = Integer.parseInt(resultCode);

        return new resultData<>(resultCode, msg, data, statusCode);
    }

    public boolean isSuccess() {
        return statusCode >= 200 && statusCode < 400;
    }

    public boolean isFail() {
        return !isSuccess();
    }
}