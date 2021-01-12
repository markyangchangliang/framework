package com.markyang.framework.config.exception;

import com.markyang.framework.pojo.common.exception.FrameworkException;
import com.markyang.framework.pojo.web.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Nonnull;
import java.sql.SQLException;

/**
 * 框架错误处理类
 * @author yangchangliang
 */
@RestControllerAdvice
@Slf4j
public class FrameworkExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 捕获框架产生的逻辑异常
     * @param exception 异常（万恶之源）
     * @return 响应实体
     */
    @ExceptionHandler(FrameworkException.class)
    public ResultVo<?> handleFrameworkException(FrameworkException exception) {
        log.error("程序异常[{0}]", exception);
        exception.printStackTrace();
        return ResultVo.error(exception.getMessage());
    }

    /**
     * 请求方法不支持
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("请求方法不支持：", ex);
        }
        log.error("请求方法不支持：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("请求方法不支持"), status);
    }

    /**
     * Path参数不完整异常
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(@NonNull MissingPathVariableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("Path参数不完整：", ex);
        }
        log.error("Path参数不完整：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("Path参数不完整"), status);
    }

    /**
     * 请求参数不完整
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(@NonNull MissingServletRequestParameterException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("请求参数不完整：", ex);
        }
        log.error("请求参数不完整：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("请求参数不完整"), status);
    }

    /**
     * 参数绑定发生错误
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(@NonNull ServletRequestBindingException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("参数绑定错误：", ex);
        }
        log.error("参数绑定错误：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("参数绑定错误"), status);
    }

    /**
     * 参数转换异常
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @Nonnull
    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(@NonNull ConversionNotSupportedException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("参数转换异常：", ex);
        }
        log.error("参数转换异常：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("参数转换异常"), status);
    }

    /**
     * 参数类型不匹配
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(@NonNull TypeMismatchException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("参数类型不匹配：", ex);
        }
        log.error("参数类型不匹配：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("参数类型不匹配"), status);
    }

    /**
     * 参数转换器不可读
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("参数转换器不可读：", ex);
        }
        log.error("参数转换器不可读：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("参数转换器不可读"), status);
    }

    /**
     * 参数转换器不可写
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(@NonNull HttpMessageNotWritableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("参数转换器不可写：", ex);
        }
        log.error("参数转换器不可写：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("参数转换器不可写"), status);
    }

    /**
     * 参数验证不通过
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("参数验证不通过：", ex);
        }
        log.error("参数验证不通过：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("参数验证不通过"), status);
    }

    /**
     * 未发现文件
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(@NonNull MissingServletRequestPartException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("未发现文件：", ex);
        }
        log.error("未发现文件：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("未发现文件"), status);
    }

    /**
     * 参数未能通过验证
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleBindException(@NonNull BindException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("参数未能通过验证：", ex);
        }
        log.error("参数未能通过验证：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("参数未能通过验证"), status);
    }

    /**
     * 请求未找到404
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(@NonNull NoHandlerFoundException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("请求未找到404：", ex);
        }
        log.error("请求未找到404：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("请求未找到404"), status);
    }

    /**
     * 异步请求超时
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param webRequest 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(@NonNull AsyncRequestTimeoutException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest webRequest) {
        if (log.isDebugEnabled()) {
            log.error("异步请求超时：", ex);
        }
        log.error("异步请求超时：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("异步请求超时"), status);
    }

    /**
     * 异步请求超时
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("不支持的MediaType：", ex);
        }
        log.error("不支持的MediaType：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("不支持的MediaType"), status);
    }

    /**
     * 异步请求超时
     * @param ex 异常
     * @param headers 头信息
     * @param status 状态
     * @param request 请求对象
     * @return 响应实体
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(@NonNull HttpMediaTypeNotAcceptableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("不接受的MediaType：", ex);
        }
        log.error("不接受的MediaType：{}", ex.getMessage());
        return new ResponseEntity<>(ResultVo.error("不接受的MediaType"), status);
    }

    /**
     * 捕获整个系统出现的其他异常
     * @param ex 异常
     * @return 响应实体
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        if (log.isDebugEnabled()) {
            log.error("未知异常：", ex);
        }
        if (ex instanceof NullPointerException) {
            // 空指针异常
            log.error("系统内部出现空指针异常[{}]，具体异常信息：{}", ex.getMessage(), ex);
            return new ResponseEntity<>(ResultVo.error("空指针异常"), HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (ex instanceof SQLException) {
            // 数据库执行异常
            log.error("数据库执行异常[{}]，具体异常信息：{}", ex.getMessage(), ex);
            return new ResponseEntity<>(ResultVo.error("数据库执行异常"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.error("系统内部出现未知异常[{}]，具体异常信息：{}", ex.getMessage(), ex);
        return new ResponseEntity<>(ResultVo.error("服务器内部异常"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
