package com.cheems.common.web.advice;

import com.cheems.common.core.exception.BizException;
import com.cheems.common.core.exception.CommonErrorCode;
import com.cheems.common.core.result.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

/**
 * 全局异常处理器:把异常统一翻译成 {@link Result},业务控制器里不再出现 try-catch。
 *
 * <p>业务异常是预期内的,只记录摘要;未知异常是"案发现场",必须带完整堆栈记录。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String MESSAGE_SEPARATOR = "; ";

    @ExceptionHandler(BizException.class)
    public ResponseEntity<Result<Void>> handleBizException(BizException exception) {
        log.warn("业务异常: code={}, message={}", exception.getErrorCode().getCode(), exception.getMessage());
        return failure(HttpStatus.BAD_REQUEST, exception.getErrorCode().getCode(), exception.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Result<Void>> handleBindException(BindException exception) {
        String message = extractFieldMessages(exception);
        log.warn("参数绑定失败: {}", message);
        return failure(HttpStatus.BAD_REQUEST, CommonErrorCode.INVALID_REQUEST.getCode(), message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result<Void>> handleConstraintViolation(ConstraintViolationException exception) {
        String message = extractViolationMessages(exception);
        log.warn("参数校验失败: {}", message);
        return failure(HttpStatus.BAD_REQUEST, CommonErrorCode.INVALID_REQUEST.getCode(), message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Result<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        String message = "参数 " + exception.getName() + " 的类型不正确";
        log.warn("参数类型错误: {}", message);
        return failure(HttpStatus.BAD_REQUEST, CommonErrorCode.INVALID_REQUEST.getCode(), message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleUnexpectedException(Exception exception) {
        log.error("未预期的异常", exception);
        return failure(HttpStatus.INTERNAL_SERVER_ERROR,
                CommonErrorCode.SYSTEM_ERROR.getCode(), CommonErrorCode.SYSTEM_ERROR.getMessage());
    }

    private String extractFieldMessages(BindException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .map(this::describeFieldError)
                .collect(Collectors.joining(MESSAGE_SEPARATOR));
    }

    private String describeFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }

    private String extractViolationMessages(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream()
                .map(this::describeViolation)
                .collect(Collectors.joining(MESSAGE_SEPARATOR));
    }

    private String describeViolation(ConstraintViolation<?> violation) {
        return violation.getPropertyPath() + ": " + violation.getMessage();
    }

    private ResponseEntity<Result<Void>> failure(HttpStatus status, String code, String message) {
        return ResponseEntity.status(status).body(Result.failure(code, message));
    }
}
