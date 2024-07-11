package com.example.exception;

public enum ExceptionEnum {
	SUCCESS(200,"成功"),
	USER_INPUT_ERROR(400,"用户输入错误"),
	SYSTEM_ERROR(500,"系统异常"),
	INNER_ERROR(600,"业务异常"),
	ERROR(999,"失败"),
	USER_NOT_EXIST(701,"用户名不存在"),
	PASSWORD_ERROR(702,"密码错误"),
	USER_NOT_ENABLED(703,"用户被禁用"),
	WITHOUT_LOGIN(704,"未携带授权凭证"),
	USER_EXIST(705,"用户名已存在"),
	VERIFYCODE_ERRO(706,"验证码错误"),
	DATA_FORMAT_ERRO(707,"数据格式错误"),
	USER_NOT_EXAMINE(708,"账号待审核"),
	USER_FAIL_EXAMINE(709,"账号审核失败"),
	POLLING_TIME_OUT(710,"长轮询暂无数据"),
	AUTHORITY_DENIED(401,"无此权限"),
	IP_DENIED(402,"该IP已被禁用，请联系管理员"),
	ACCESS_DENIED(403,"未登录"),
	TOKEN_EXPIRED(405,"登陆已过期");

	private int code;
	private String message;
	
	ExceptionEnum(int code,String message) {
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

