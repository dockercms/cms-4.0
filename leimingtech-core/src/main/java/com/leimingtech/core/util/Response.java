package com.leimingtech.core.util;

import java.io.InputStream;

/**
 * @author lkang
 * @date 2014-06-24 上午11:30
 * @version V1.0
 */
public interface Response {

	public String getContent();

	public InputStream getInputStream();

	public int getStatusCode();

	public String getContentType();

	/**
	 * 
	 * @param content
	 */
	public void setContent(String content);

	/**
	 * 
	 * @param code
	 */
	public void setStatusCode(int code);

	/**
	 * 
	 * @param contentType
	 */
	public void setContentType(String contentType);

}