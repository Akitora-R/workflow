package com.loctek.workflow.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Resp<T> {
    private Integer code;
    private String msg;
    private OffsetDateTime timestamp;
    private T data;

    private Resp(Integer code, String msg, T data,OffsetDateTime timestamp) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp=timestamp;
    }

    public static <T> Resp<T> success(String msg, T data) {
        return new Resp<>(200, msg, data,OffsetDateTime.now());
    }
    public static <T> Resp<T> fail(String msg, T data) {
        return new Resp<>(500, msg, data,OffsetDateTime.now());
    }
}
