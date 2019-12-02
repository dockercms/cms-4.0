package com.leimingtech.core.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.leimingtech.core.common.HttpHeaderConstants;


/**
 * @author lkang
 * @date 2014-06-24 上午11:30
 * @version V1.0
 */
public class StringResponse implements Response {
	private String content;
	private String contentType;
	private int statusCode;

	public StringResponse() {
		contentType = HttpHeaderConstants.CONTEXT_TYPE_HTML;
	}

	public void finalize() throws Throwable {

	}

	public String getContent() {
		return content;
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
		this.content = content;
	}

	/**
	 * 
	 * @param code
	 */
	public void setStatusCode(int code) {
		statusCode = code;
	}

	/**
	 * 
	 * @param contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public InputStream getInputStream() {
		try {
			InputStream in = new ByteArrayInputStream(this.content.getBytes("UTF-8"));
			return in;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}