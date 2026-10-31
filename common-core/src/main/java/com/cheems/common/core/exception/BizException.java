package com.cheems.common.core.exception;

import java.io.Serial;

/**
 * 业务异常。
 *
 * <p>当业务规则被违反(如"库存不足")时抛出,由全局异常处理器统一转为响应结果。
 * 业务代码只负责抛出,不负责捕获。
 */
public class BizException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final transient ErrorCode errorCode;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /** 用于需要在通用错误描述之外补充具体原因的场景,如"库存不足:仅剩 3 件" */
    public BizException(ErrorCode errorCode, String detailedMessage) {
        super(detailedMessage);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
