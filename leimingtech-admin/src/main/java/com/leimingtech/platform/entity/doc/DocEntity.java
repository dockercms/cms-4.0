package com.leimingtech.platform.entity.doc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;


/**
 * @Title: Entity
 * @Description: 文档
 * @author
 * @date 2014-06-11 16:04:27
 * @version V1.0
 * 
 */
@Entity
@Table(name = "cms_doc", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class DocEntity extends IdEntity implements java.io.Serializable {
	 
	/** name */
	private java.lang.String name;
	/** 标签 */
	private String tag;
	/** 分类名称 */
	private java.lang.String typeid;
	/** 排序 */
	private java.lang.Integer sort;
	/** 描述 */
	private java.lang.String description;
	/** 状态 */
	private java.lang.Integer status;
	/** 添加时间 */
	private java.util.Date createdtime;
	/** 添加人 */
	private java.lang.String createdby;
	/** 修改次数 */
	private java.lang.Integer updatecount;
	/** 修改时间 */
	private java.util.Date updatetime;
	/** 修改人 */
	private java.lang.String updateby;
	/** 标签试一试demo */
	private String tagDemo;

	private String pid;
	/** 返回示例 */
	private String returnExampleValue;
	
	/**文档所属平台*/
	private String platform;
	
	
	@Column(name = "PLATFORM", nullable = true, length = 255)
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/** 标签试一试demo */
	@Column(name = "TAG_DEMO", nullable = true, length = 1000)
	public String getTagDemo() {
		return tagDemo;
	}

	/** 标签试一试demo */
	public void setTagDemo(String tagDemo) {
		this.tagDemo = tagDemo;
	}

	/** 返回示例 */
	@Column(name = "RETURN_EXAMPLE_VALUE", nullable = true, length = 255)
	public String getReturnExampleValue() {
		return returnExampleValue;
	}

	/** 返回示例 */
	public void setReturnExampleValue(String returnExampleValue) {
		this.returnExampleValue = returnExampleValue;
	}

	@Column(name = "PID", nullable = true,length=32)
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	private String type;

	/** api地址 */
	private String apiAddress;

	@Column(name = "API_ADDRESS", nullable = true, length = 255)
	public String getApiAddress() {
		return apiAddress;
	}

	public void setApiAddress(String apiAddress) {
		this.apiAddress = apiAddress;
	}

	@Transient
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "TAG", nullable = true, length = 255)
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	 
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String name
	 */
	@Column(name = "NAME", nullable = true, length = 255)
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 分类名称
	 */
	@Column(name = "TYPEID", nullable = true, length = 32)
	public java.lang.String getTypeid() {
		return this.typeid;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 分类名称
	 */
	public void setTypeid(java.lang.String typeid) {
		this.typeid = typeid;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 排序
	 */
	@Column(name = "SORT", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getSort() {
		return this.sort;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 排序
	 */
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 描述
	 */
	@Column(name = "DESCRIPTION", nullable = true, length = 255)
	public java.lang.String getDescription() {
		return this.description;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 描述
	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 状态
	 */
	@Column(name = "STATUS", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getStatus() {
		return this.status;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 状态
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 添加时间
	 */
	@Column(name = "CREATEDTIME", nullable = true)
	public java.util.Date getCreatedtime() {
		return this.createdtime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 添加时间
	 */
	public void setCreatedtime(java.util.Date createdtime) {
		this.createdtime = createdtime;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 添加人
	 */
	@Column(name = "CREATEDBY", nullable = true, length = 255)
	public java.lang.String getCreatedby() {
		return this.createdby;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 添加人
	 */
	public void setCreatedby(java.lang.String createdby) {
		this.createdby = createdby;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 修改次数
	 */
	@Column(name = "UPDATECOUNT", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getUpdatecount() {
		return this.updatecount;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 修改次数
	 */
	public void setUpdatecount(java.lang.Integer updatecount) {
		this.updatecount = updatecount;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 修改时间
	 */
	@Column(name = "UPDATETIME", nullable = true)
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 修改时间
	 */
	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 修改人
	 */
	@Column(name = "UPDATEBY", nullable = true, length = 255)
	public java.lang.String getUpdateby() {
		return this.updateby;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 修改人
	 */
	public void setUpdateby(java.lang.String updateby) {
		this.updateby = updateby;
	}
}
