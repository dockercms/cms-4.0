package com.leimingtech.platform.controller.uploadfile;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author  :linjm linjianmao@gmail.com
 * @version :2014-4-25上午09:40:35
 *  description :
 **/
@JsonIgnoreProperties({"bytes"}) 
public class UploadFileMeta {
 
	/**
	 * 文件原名
	 */
    private String fileName;
	/**
	 * 文件大小
	 */
    private String fileSize;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件重命名
     */
    private String reName;
    /**
     * 文件路径
     */
    private String localPath;
 
    private byte[] bytes;

  //setters & getters
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getReName() {
		return reName;
	}

	public void setReName(String reName) {
		this.reName = reName;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}
