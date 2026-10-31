package com.cheems.common.core.exception;

/**
 * 业务断言工具:前置校验失败时抛出 {@link BizException},
 * 让"校验 → 抛异常"从两行 if 写法收敛为一行有业务语义的调用。
 *
 * <p>用法示例:{@code BusinessAssert.notNull(user, CommonErrorCode.RESOURCE_NOT_FOUND);}
 */
public final class BusinessAssert {

    private BusinessAssert() {
    }

    public static void isTrue(boolean condition, ErrorCode errorCode) {
        if (!condition) {
            throw new BizException(errorCode);
        }
    }

    public static void isTrue(boolean condition, ErrorCode errorCode, String detailedMessage) {
        if (!condition) {
            throw new BizException(errorCode, detailedMessage);
        }
    }

    public static void notNull(Object value, ErrorCode errorCode) {
        isTrue(value != null, errorCode);
    }
}
