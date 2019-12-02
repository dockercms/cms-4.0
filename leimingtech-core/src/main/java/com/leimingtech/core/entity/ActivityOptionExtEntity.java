package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 活动报名表
 * @author 
 * @date 2015-08-28 17:59:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_activity_option_ext", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ActivityOptionExtEntity extends IdEntity implements java.io.Serializable {

	
	/** 选项id */
	private java.lang.String optiondids;
	/** 文本内容 */
	private java.lang.String extText;
	
	/** 创建时间 */
	private java.util.Date createtime;
	/** IP */
	private java.lang.String ip;
	/** 报名人id */
	private java.lang.String logids;
	
	/** 多选内容 */
	private java.lang.String extCheckbox;
	/** 下拉内容 */
	private java.lang.String extSelect;
	/**上传图片*/
	private java.lang.String extPicture;
	/**上传文件*/
	private java.lang.String extFile;
	

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 选项id
	 */
	@Column(name = "OPTIONDIDS", nullable = true, length = 32)
	public java.lang.String getOptiondids() {
		return this.optiondids;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 选项id
	 */
	public void setOptiondids(java.lang.String optiondids) {
		this.optiondids = optiondids;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 文本内容
	 */
	@Column(name = "EXT_TEXT", nullable = true, length = 255)
	public java.lang.String getExtText() {
		return this.extText;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 文本内容
	 */
	public void setExtText(java.lang.String extText) {
		this.extText = extText;
	}
	
		
	
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATETIME", nullable = true)
	public java.util.Date getCreatetime() {
		return this.createtime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 创建时间
	 */
	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String IP
	 */
	@Column(name = "IP", nullable = true, length = 255)
	public java.lang.String getIp() {
		return this.ip;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String IP
	 */
	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 报名人id
	 */
	@Column(name = "LOGIDS", nullable = true, length = 255)
	public java.lang.String getLogids() {
		return this.logids;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 报名人id
	 */
	public void setLogids(java.lang.String logids) {
		this.logids = logids;
	}
	
	
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 多选内容
	 */
	@Column(name = "EXT_CHECKBOX", nullable = true, length = 255)
	public java.lang.String getExtCheckbox() {
		return this.extCheckbox;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 多选内容
	 */
	public void setExtCheckbox(java.lang.String extCheckbox) {
		this.extCheckbox = extCheckbox;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 下拉内容
	 */
	@Column(name = "EXT_SELECT", nullable = true, length = 255)
	public java.lang.String getExtSelect() {
		return this.extSelect;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 下拉内容
	 */
	public void setExtSelect(java.lang.String extSelect) {
		this.extSelect = extSelect;
	}
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 上传图片
	 */
	@Column(name = "EXT_PICTURE", nullable = true, length = 255)
	public java.lang.String getExtPicture() {
		return extPicture;
	}
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 上传图片
	 */
	public void setExtPicture(java.lang.String extPicture) {
		this.extPicture = extPicture;
	}
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 上传文件
	 */
	@Column(name = "EXT_FILE", nullable = true, length = 255)
	public java.lang.String getExtFile() {
		return extFile;
	}
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 上传文件
	 */
	public void setExtFile(java.lang.String extFile) {
		this.extFile = extFile;
	}
	
}
