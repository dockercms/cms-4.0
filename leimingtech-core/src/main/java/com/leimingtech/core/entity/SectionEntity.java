package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @Title: Entity
 * @Description: 区块信息
 * @author zhangdaihao
 * @date 2014-04-22 11:44:30
 * @version V1.0
 * 
 */
@Entity
@Table(name = "cms_section", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SectionEntity extends IdEntity implements java.io.Serializable {
	
	/** 区块名称 */
	private java.lang.String name;
	/** 类型 */
	private java.lang.String type;
	/** 模版路径 */
	private java.lang.String templepath;
	/** 所发布的html */
	private java.lang.String url;
	/** 区块内容 */
	private java.lang.String data;
	/** 更新频率 */
	private java.lang.Integer frequency;
	/** 更新时间 */
	private java.util.Date updatetime;
	/** 排序 */
	private java.lang.Integer sort;
	/** 创建时间 */
	private java.util.Date createdtime;
	/** 创建人 */
	private java.lang.Integer createdby;
	/** 修改时间 */
	private java.util.Date modifiedtime;
	/** 修改人 */
	private java.lang.String modifiedby;
	/** 发布时间 */
	private java.util.Date published;
	/** 发布人 */
	private java.lang.Integer publishedby;
	/** 描述 */
	private java.lang.String memo;
	/** 每页显示数据条数 */
	private java.lang.Integer num;
	/** 区块分类 改为栏目 */
	private String classid;
	/** pathids */
	private java.lang.String pathids;
	/**站点id*/
	private String siteid;
	
	@Column(name = "SITE_ID", nullable = true, precision = 32, scale = 0)
	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	@Column(name = "classid", nullable = false, length=32)
	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 区块名称
	 */
	@Column(name = "NAME", nullable = false, length = 50)
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 区块名称
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 类型
	 */
	@Column(name = "TYPE", nullable = false, length = 7)
	public java.lang.String getType() {
		return this.type;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 类型
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 模版路径
	 */
	@Column(name = "TEMPLEPATH", nullable = true, length = 50)
	public java.lang.String getTemplepath() {
		return this.templepath;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 模版路径
	 */
	public void setTemplepath(java.lang.String templepath) {
		this.templepath = templepath;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 所发布的html
	 */
	@Column(name = "URL", nullable = true, length = 50)
	public java.lang.String getUrl() {
		return this.url;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 所发布的html
	 */
	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 区块内容
	 */
	@Column(name = "DATA", nullable = true, length = 65535)
	public java.lang.String getData() {
		return this.data;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 区块内容
	 */
	public void setData(java.lang.String data) {
		this.data = data;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 更新频率
	 */
	@Column(name = "FREQUENCY", nullable = true, precision = 5, scale = 0)
	public java.lang.Integer getFrequency() {
		return this.frequency;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 更新频率
	 */
	public void setFrequency(java.lang.Integer frequency) {
		this.frequency = frequency;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 更新时间
	 */
	@Column(name = "UPDATETIME", nullable = true)
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 更新时间
	 */
	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 排序
	 */
	@Column(name = "SORT", nullable = false, precision = 10, scale = 0)
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
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATEDTIME", nullable = false)
	public java.util.Date getCreatedtime() {
		return this.createdtime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 创建时间
	 */
	public void setCreatedtime(java.util.Date createdtime) {
		this.createdtime = createdtime;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 创建人
	 */
	@Column(name = "CREATEDBY", nullable = false, precision = 7, scale = 0)
	public java.lang.Integer getCreatedby() {
		return this.createdby;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 创建人
	 */
	public void setCreatedby(java.lang.Integer createdby) {
		this.createdby = createdby;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 修改时间
	 */
	@Column(name = "MODIFIEDTIME", nullable = true)
	public java.util.Date getModifiedtime() {
		return this.modifiedtime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 修改时间
	 */
	public void setModifiedtime(java.util.Date modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 修改人
	 */
	@Column(name = "MODIFIEDBY", nullable = true, precision = 32, scale = 0)
	public java.lang.String getModifiedby() {
		return this.modifiedby;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 修改人
	 */
	public void setModifiedby(java.lang.String modifiedby) {
		this.modifiedby = modifiedby;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 发布时间
	 */
	@Column(name = "PUBLISHED", nullable = true)
	public java.util.Date getPublished() {
		return this.published;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 发布时间
	 */
	public void setPublished(java.util.Date published) {
		this.published = published;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 发布人
	 */
	@Column(name = "PUBLISHEDBY", nullable = true, precision = 7, scale = 0)
	public java.lang.Integer getPublishedby() {
		return this.publishedby;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 发布人
	 */
	public void setPublishedby(java.lang.Integer publishedby) {
		this.publishedby = publishedby;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 描述
	 */
	@Column(name = "MEMO", nullable = true)
	public java.lang.String getMemo() {
		return this.memo;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 描述
	 */
	public void setMemo(java.lang.String memo) {
		this.memo = memo;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 每页显示数据条数
	 */
	@Column(name = "NUM", nullable = true, precision = 5, scale = 0)
	public java.lang.Integer getNum() {
		return this.num;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 每页显示数据条数
	 */
	public void setNum(java.lang.Integer num) {
		this.num = num;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 描述
	 */
	@Column(name = "PATHIDS", nullable = true)
	public java.lang.String getPathids() {
		return pathids;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String pathids
	 */
	public void setPathids(java.lang.String pathids) {
		this.pathids = pathids;
	}

}
