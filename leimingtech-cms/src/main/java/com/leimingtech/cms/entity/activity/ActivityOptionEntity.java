package com.leimingtech.cms.entity.activity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 活动报名选项内容
 * @author 
 * @date 2015-08-07 17:56:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_activity_option", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ActivityOptionEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1407815602294211619L;
	/** 选项名称 */
	private java.lang.String optionName;
	/** 字段标识 */
	private java.lang.String optionMark;
	/** 数据类型 */
	private java.lang.String dateType;
	/** 文本大小限制 */
	private java.lang.String textsizeLimit;
	/** 选项大小限制 */
	private java.lang.String selectsizeLimit;
	/** 文件大小限制 */
	private java.lang.String filesizeLimit;
	/** 可选值 */
	private java.lang.String optionalValue;
	/** 创建时间 */
	private java.util.Date createtime;
	/** 验证规则 */
	private java.lang.String validation;
	/** 是否显示删除(1:否,0：是) */
	private java.lang.Integer isShowdelete;
	/** 是否启用(1:是，0：否) */
	private java.lang.Integer isEnabled;
	/** 排序 */
	private java.lang.String sort;
	/** 是否默认显示 */
	private java.lang.Integer isShow;
	
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 选项名称
	 */
	@Column(name = "OPTION_NAME", nullable = true, length = 255)
	public java.lang.String getOptionName() {
		return this.optionName;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 选项名称
	 */
	public void setOptionName(java.lang.String optionName) {
		this.optionName = optionName;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 字段标识
	 */
	@Column(name = "OPTION_MARK", nullable = true, length = 255)
	public java.lang.String getOptionMark() {
		return this.optionMark;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 字段标识
	 */
	public void setOptionMark(java.lang.String optionMark) {
		this.optionMark = optionMark;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 数据类型
	 */
	@Column(name = "DATE_TYPE", nullable = true, length = 255)
	public java.lang.String getDateType() {
		return this.dateType;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 数据类型
	 */
	public void setDateType(java.lang.String dateType) {
		this.dateType = dateType;
	}
	
	/**
	 * 方法: 取得java.lang.Double
	 *
	 * @return: java.lang.Double 文本大小限制
	 */
	@Column(name = "TEXTSIZE_LIMIT", nullable = true, length = 255)
	public java.lang.String getTextsizeLimit() {
		return this.textsizeLimit;
	}

	/**
	 * 方法: 设置java.lang.Double
	 *
	 * @param: java.lang.Double 文本大小限制
	 */
	public void setTextsizeLimit(java.lang.String textsizeLimit) {
		this.textsizeLimit = textsizeLimit;
	}
	
	
	/**
	 * 方法: 取得java.lang.Double
	 *
	 * @return: java.lang.Double 选项大小限制
	 */
	@Column(name = "SELECTSIZE_LIMIT", nullable = true, length = 255)
	public java.lang.String getSelectsizeLimit() {
		return this.selectsizeLimit;
	}

	/**
	 * 方法: 设置java.lang.Double
	 *
	 * @param: java.lang.Double 选项大小限制
	 */
	public void setSelectsizeLimit(java.lang.String  selectsizeLimit) {
		this.selectsizeLimit = selectsizeLimit;
	}
	
	
	
	
	
	
	/**
	 * 方法: 取得java.lang.Double
	 *
	 * @return: java.lang.Double 文件大小限制
	 */
	@Column(name = "FILESIZE_LIMIT", nullable = true, length = 255)
	public java.lang.String getFilesizeLimit() {
		return this.filesizeLimit;
	}

	/**
	 * 方法: 设置java.lang.Double
	 *
	 * @param: java.lang.Double 文件大小限制
	 */
	public void setFilesizeLimit(java.lang.String filesizeLimit) {
		this.filesizeLimit = filesizeLimit;
	}
	
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 可选值
	 */
	@Column(name = "OPTIONAL_VALUE", nullable = true, length = 255)
	public java.lang.String getOptionalValue() {
		return this.optionalValue;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 可选值
	 */
	public void setOptionalValue(java.lang.String optionalValue) {
		this.optionalValue = optionalValue;
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
	 * @return: java.lang.String 验证规则
	 */
	@Column(name = "VALIDATION", nullable = true, length = 255)
	public java.lang.String getValidation() {
		return this.validation;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 验证规则
	 */
	public void setValidation(java.lang.String validation) {
		this.validation = validation;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否显示删除(1:否,0：是)
	 */
	@Column(name = "IS_SHOWDELETE", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getIsShowdelete() {
		return this.isShowdelete;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否显示删除(1:否,0：是)
	 */
	public void setIsShowdelete(java.lang.Integer isShowdelete) {
		this.isShowdelete = isShowdelete;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否启用(1:是，0：否)
	 */
	@Column(name = "IS_ENABLED", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getIsEnabled() {
		return this.isEnabled;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否启用(1:是，0：否)
	 */
	public void setIsEnabled(java.lang.Integer isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 排序
	 */
	@Column(name = "SORT", nullable = true, length = 11)
	public java.lang.String getSort() {
		return this.sort;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 排序
	 */
	public void setSort(java.lang.String sort) {
		this.sort = sort;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否默认显示
	 */
	@Column(name = "IS_SHOW", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getIsShow() {
		return this.isShow;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否默认显示
	 */
	public void setIsShow(java.lang.Integer isShow) {
		this.isShow = isShow;
	}
	
	
	
}
