package com.joker.mms.common;

public enum ErrorCode {

    SUCCESS(200, "成功"),
    CREATED(201,"已创建"),

    ACCEPTED(202,"已接受"),
    REQUEST_ERROR(400, "请求错误"),
    NOT_LOGIN_ERROR(401, "未登录"),
    FORBIDDEN_ERROR(403, "无权限"),
    NOT_FOUND_ERROR(404, "请求数据不存在"),

    NOT_EXIST_ERROR(410,"请求数据已删除"),
    SYSTEM_ERROR(500, "服务器内部错误"),

    GATEWAY_ERROR(502, "网关错误"),
    SERVICE_ERROR(503, "服务不可用");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
