package com.cheems.common.core.exception;

/**
 * 平台级通用错误码,取值范围参考《阿里巴巴Java开发手册》附录的错误码列表。
 *
 * <p>业务专属错误码(如"优惠券已过期")请在业务项目中另行定义枚举,不要扩充本类。
 */
public enum CommonErrorCode implements ErrorCode {

    INVALID_REQUEST("A0400", "请求参数错误"),
    UNAUTHORIZED("A0300", "访问未授权"),
    FORBIDDEN("A0301", "访问被禁止"),
    RESOURCE_NOT_FOUND("A0440", "请求的资源不存在"),
    SYSTEM_ERROR("B0001", "系统执行出错");

    private final String code;

    private final String message;

    CommonErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
