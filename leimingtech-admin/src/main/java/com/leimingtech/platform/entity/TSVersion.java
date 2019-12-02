package com.leimingtech.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leimingtech.core.entity.IdEntity;

/**
 * 程序版本控制表
 * @author 
 *
 */
@Entity
@Table(name = "cms_version")
public class TSVersion extends IdEntity implements java.io.Serializable {
	private String versionName;//版本名称
	private String versionCode;//版本编码
	private String loginPage;//登陆入口页面
	private String versionNum;//版本号
	
	/**创建时间*/
	private java.util.Date createdtime;
	
	
	@Column(name = "versionname", length = 30)
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
	@Column(name = "versioncode", length = 50)
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	@Column(name = "loginpage", length = 100)
	public String getLoginPage() {
		return loginPage;
	}
	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
	@Column(name = "versionnum", length = 20)
	public String getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	/**
	 *方法: 取得java.lang.Date
	 *@return: java.lang.Date  创建时间
	 */
	@Column(name = "CREATEDTIME", nullable = true)
	public java.util.Date getCreatedtime() {
		return createdtime;
	}
	/**
	 *方法: 设置java.lang.Date
	 *@return: java.lang.Date  创建时间
	 */
	public void setCreatedtime(java.util.Date createdtime) {
		this.createdtime = createdtime;
	}
}
