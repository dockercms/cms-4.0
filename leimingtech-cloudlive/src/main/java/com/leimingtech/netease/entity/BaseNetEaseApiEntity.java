package com.leimingtech.netease.entity;

/**
 * 网易云直播 接口实体基类
 * 
 * @author liuzhen
 * 
 */
public class BaseNetEaseApiEntity<T> {

	private int code;// 错误码
	private String msg;// 错误信息

	private T ret;// 结果

	public T getRet() {
		return ret;
	}

	public void setRet(T ret) {
		this.ret = ret;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
