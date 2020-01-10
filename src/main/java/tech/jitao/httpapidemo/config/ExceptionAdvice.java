package tech.jitao.httpapidemo.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tech.jitao.httpapidemo.common.ApiResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class ExceptionAdvice {
    private static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    private static final String VALIDATION_FAILED = "VALIDATION_FAILED";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PropertyNamingStrategy.SnakeCaseStrategy snakeCaseStrategy = new PropertyNamingStrategy.SnakeCaseStrategy();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResult handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> map = Maps.newHashMapWithExpectedSize(bindingResult.getFieldErrorCount());
        for (FieldError error : bindingResult.getFieldErrors()) {
            String field = error.getField();

            if (bindingResult.getObjectName().endsWith("Request")) {
                field = snakeCaseStrategy.translate(field);
            }

            map.put(field, error.getDefaultMessage());
        }

        logger.info("{}, 方法参数校验失败：{}。", request.getRequestURI(), map);

        return ApiResult.error(VALIDATION_FAILED, "参数校验失败。", map);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResult handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        logger.info("{}, 缺少必要的参数：{}", request.getRequestURI(), ex.getParameterName());

        return ApiResult.error(VALIDATION_FAILED, String.format("缺少必要的参数：%s。", ex.getParameterName()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResult handleMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        logger.info("{}, 参数类型转换失败：{}", request.getRequestURI(), ex.getName());

        return ApiResult.error(VALIDATION_FAILED, String.format("参数类型转换失败：%s。", ex.getName()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResult handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        Map<String, String> map = Maps.newHashMapWithExpectedSize(violations.size());
        for (ConstraintViolation<?> violation : violations) {
            String[] parts = violation.getPropertyPath().toString().split("\\.");
            if (parts.length > 0) {
                map.put(parts[parts.length - 1], violation.getMessage());
            }
        }

        logger.info("{}, 参数校验失败：{}。", request.getRequestURI(), map);

        return ApiResult.error(VALIDATION_FAILED, "参数校验失败。", map);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResult handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException ex) {
        logger.info("{}, 请求数据非法：", request.getRequestURI(), ex);

        return ApiResult.error(UNKNOWN_ERROR, "请求数据非法。");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResult handleHttpMediaTypeNotSupportedException(HttpServletRequest request, HttpMediaTypeNotSupportedException ex) {
        logger.info("{}, 不支持的媒体类型：{}", request.getRequestURI(), ex.getContentType());

        return ApiResult.error(UNKNOWN_ERROR, String.format("不支持的媒体类型：%s。", ex.getContentType()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ApiResult handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        logger.info("{}, 不支持的请求方式：{}", request.getRequestURI(), ex.getMethod());

        return ApiResult.error(UNKNOWN_ERROR, String.format("不支持的请求方式：%s。", ex.getMethod()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResult handleException(HttpServletRequest request, Exception ex) {
        logger.info("{}, 发生异常：", request.getRequestURI(), ex);

        return ApiResult.error(UNKNOWN_ERROR, "系统发生异常，请联系管理员。");
    }
}
