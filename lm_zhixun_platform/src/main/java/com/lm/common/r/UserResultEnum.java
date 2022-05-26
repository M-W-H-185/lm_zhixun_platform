package com.lm.common.r;

/**
 * 可以理解为子模块的异常枚举
 * 用户异常处理枚举
 */
public enum UserResultEnum implements GlobalResultEnumInterface{

    USER_INPUT_NULL_ERROR(100100,"用户输入有误！"),
    USER_NO_LOGIN(100101,"NO_LOGIN"),
    USER_LOGIN_NO_EXIST(100102,"登录失败，账号不存在/密码错误！"),
    USER_LOGIN_ACCOUNT_STATE_STOP_USE(100103,"登录失败，账户已被禁用！"),
    USER_TOKEN_ERROR(100104,"token异常"),
    USER_TOKEN_NOT_FOUND(100105,"token not found"),
    USER_NULL_ERROR(100106, "用户不存在"),

    ;

    private Integer code; //编码

    private String msg; //错误信息

    UserResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
