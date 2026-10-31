package com.cheems.common.core.exception;

/**
 * 错误码。
 *
 * <p>编码格式遵循《阿里巴巴Java开发手册》附录的 A/B/C 错误码体系:
 * 第一部分为字母(用户端错误 A、系统执行出错 B、第三方服务出错 C),
 * 第二部分为错误级别(0 为系统级,可继续细化),第三部分为 4 位数字。
 *
 * <p>业务项目应定义自己的枚举实现本接口,与平台级错误码区分开。
 */
public interface ErrorCode {

    /** 错误码,如 "A0400" */
    String getCode();

    /** 面向用户的错误描述 */
    String getMessage();
}
