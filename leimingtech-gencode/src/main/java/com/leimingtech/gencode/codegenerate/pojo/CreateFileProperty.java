package com.leimingtech.gencode.codegenerate.pojo;

/**
 * 生成文件标示
 * @author 00704873
 *
 */
public class CreateFileProperty{
	private boolean actionFlag;
	private boolean serviceIFlag;
	private boolean entityFlag;
	private boolean pageFlag;
	private boolean serviceImplFlag;
	private boolean jspFlag;
	
	
	private String jspMode; //01:页面编辑模板 02：行编辑模板
	
	public boolean isActionFlag() {
		return actionFlag;
	}
	public boolean isServiceIFlag() {
		return serviceIFlag;
	}
	public boolean isEntityFlag() {
		return entityFlag;
	}
	public boolean isPageFlag() {
		return pageFlag;
	}
	public boolean isServiceImplFlag() {
		return serviceImplFlag;
	}
	public void setActionFlag(boolean actionFlag) {
		this.actionFlag = actionFlag;
	}
	public void setServiceIFlag(boolean serviceIFlag) {
		this.serviceIFlag = serviceIFlag;
	}
	public void setEntityFlag(boolean entityFlag) {
		this.entityFlag = entityFlag;
	}
	public void setPageFlag(boolean pageFlag) {
		this.pageFlag = pageFlag;
	}
	public void setServiceImplFlag(boolean serviceImplFlag) {
		this.serviceImplFlag = serviceImplFlag;
	}
	public boolean isJspFlag() {
		return jspFlag;
	}
	public void setJspFlag(boolean jspFlag) {
		this.jspFlag = jspFlag;
	}
	public String getJspMode() {
		return jspMode;
	}
	public void setJspMode(String jspMode) {
		this.jspMode = jspMode;
	}
}
