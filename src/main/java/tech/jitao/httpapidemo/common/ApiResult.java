package tech.jitao.httpapidemo.common;

import java.util.Map;

public class ApiResult {
    private static final String CODE_ERROR_WITH_MESSAGE = "ERROR_WITH_MESSAGE";
    private static final ApiResult OK = new ApiResult();

    private String code = "OK";
    private String msg;
    private Object data;
    private Map<String, String> errors;

    private ApiResult() {
    }

    private ApiResult(String code, String msg, Object data, Map<String, String> errors) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.errors = errors;
    }

    public static ApiResult ok() {
        return OK;
    }

    public static ApiResult ok(Object data) {
        ApiResult result = new ApiResult();
        result.setData(data);

        return result;
    }

    public static ApiResult okWithMsg(String msg) {
        ApiResult result = new ApiResult();
        result.setMsg(msg);

        return result;
    }

    public static ApiResult okWithMsgAndData(String msg, Object data) {
        ApiResult result = new ApiResult();
        result.setMsg(msg);
        result.setData(data);

        return result;
    }

    public static ApiResult error(String msg) {
        return new ApiResult(CODE_ERROR_WITH_MESSAGE, msg, null, null);
    }

    public static ApiResult error(String code, String msg) {
        return new ApiResult(code, msg, null, null);
    }

    public static ApiResult error(String code, String msg, Map<String, String> errors) {
        return new ApiResult(code, msg, null, errors);
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", errors=" + errors +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
