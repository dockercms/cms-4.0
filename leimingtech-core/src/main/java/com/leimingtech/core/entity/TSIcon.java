package com.leimingtech.core.entity;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leimingtech.core.entity.IdEntity;


/**
 * TIcon entity.
 *  @author  
 */
@Entity
@Table(name = "cms_icon")
public class TSIcon extends IdEntity implements java.io.Serializable {
	private String iconName;
	private Short iconType;
	private String iconPath;
	private byte[] iconContent;
	private String iconClas;
	private String extend;
	/**创建时间*/
	private java.util.Date createdtime;
	@Column(name = "name", nullable = false, length = 100)
	public String getIconName() {
		return this.iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	@Column(name = "type")
	public Short getIconType() {
		return this.iconType;
	}

	public void setIconType(Short iconType) {
		this.iconType = iconType;
	}

	@Column(name = "path", length = 300,precision =300)
	public String getIconPath() {
		return this.iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	@Column(name = "iconclas", length = 200)
	public String getIconClas() {
		return iconClas;
	}
	public void setIconClas(String iconClas) {
		this.iconClas = iconClas;
	}

	public void setIconContent(byte[] iconContent) {
		this.iconContent = iconContent;
	}
	@Column(name = "content",length = 1000,precision =3000)
	public byte[] getIconContent() {
		return iconContent;
	}
	@Column(name = "extend")
	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
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