package com.example.model.pojo;

import com.example.exception.ExceptionEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data

@SuppressWarnings("unchecked")
@Slf4j
public class R<T> {

	private Boolean success;

	private Integer code;

	private String message;

	private T data;
	
	
	public static <T> R<T> error() {
		
		R<T> r = new R<T>();
		r.setSuccess(false);	
		r.setCode(ExceptionEnum.ERROR.getCode());
		r.setMessage(ExceptionEnum.ERROR.getMessage());
		return r;
	}
	
	public static <T> R<T> error(String message) {
		R<T> r = new R<T>();
		r.setSuccess(false);
		r.setCode(ExceptionEnum.ERROR.getCode());
		r.setMessage(message);
		return r;
	} 
    
    public static <T> R<T> error(ExceptionEnum e) {
		
		R<T> r = new R<T>();
		r.setSuccess(false);
		r.setCode(e.getCode());
		r.setMessage(e.getMessage());
		
		return r;
	}
    
	public static <T> R<T> ok() {

		R<T> r = new R<T>();
		r.setSuccess(true);
		r.setCode(ExceptionEnum.SUCCESS.getCode());
		r.setMessage(ExceptionEnum.SUCCESS.getMessage());
	
		return r;
	}
	
	public static <T> R<T> ok(T data) {

		R<T> r = new R<T>();
		r.setSuccess(true);
		r.setCode(ExceptionEnum.SUCCESS.getCode());
		r.setMessage(ExceptionEnum.SUCCESS.getMessage());
		r.setData(data);
		
		return r;
	}
	
	public R<T> message(String message) {
		this.setMessage(message);		
		return this;
	}
	
	
	public R<T> code(Integer code) {
		this.setCode(code);
		return this;
	}
}
