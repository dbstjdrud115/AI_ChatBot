package com.ll.chat_ai.common.rsData;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class resultData<T> {
    private String resultCode;
    private String msg;
    private T data;

    public static <T> resultData<T> of(String resultCode, String msg, T data) {
        return new resultData<>(resultCode, msg, data);
    }

    public static <T> resultData<T> of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

   /* public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }

    public boolean isFail() {
        return !isSuccess();
    }*/

    public Optional<resultData<T>> optional() {
        return Optional.of(this);
    }

    public <T> resultData<T> newDataOf(T data) {
        return new resultData<T>(getResultCode(), getMsg(), data);
    }
}