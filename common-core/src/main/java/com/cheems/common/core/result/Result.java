package com.cheems.common.core.result;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应结果。
 *
 * <p>所有对外的接口都返回本类,前端根据 {@code code} 判断业务是否成功,
 * 失败时直接把 {@code message} 展示给用户。
 *
 * @param <T> 业务数据的类型
 */
public class Result<T> implements Serializable {

    /** 成功状态码,遵循阿里中后台前端约定的 "00000 表示成功" */
    public static final String SUCCESS_CODE = "00000";

    @Serial
    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    private T data;

    private Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, "成功", data);
    }

    public static Result<Void> success() {
        return success(null);
    }

    public static <T> Result<T> failure(String code, String message) {
        return new Result<>(code, message, null);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Result{code='" + code + "', message='" + message + "', data=" + data + '}';
    }
}
