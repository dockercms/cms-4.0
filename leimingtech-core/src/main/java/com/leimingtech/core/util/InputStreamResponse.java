package com.leimingtech.core.util;

import java.io.InputStream;


/**
 * @author lkang
 * @date 2014-06-24 上午11:30
 * @version V1.0
 */
public class InputStreamResponse implements Response {

	private String contentType;
	private InputStream inputStream;
	private int statusCode;

	public InputStreamResponse(InputStream in) {
		this.inputStream = in;
	}

	public String getContent() {
		if (inputStream == null)
			return "";
		else
			return StringUtil.inputStream2String(this.inputStream);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getContentType() {
		return this.contentType;
	}

	/**
	 * 
	 * @param content
	 */
	public void setContent(String content) {

	}

	/**
	 * 
	 * @param code
	 */
	public void setStatusCode(int code) {
		this.statusCode = code;
	}

	/**
	 * 
	 * @param contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public InputStream getInputStream() {

		return this.inputStream;
	}

}