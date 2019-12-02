package com.leimingtech.mobile.entity.relatecontent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 相关内容
 * @author 
 * @date 2015-06-30 15:11:09
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_relate_content", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class RelateContentMobileEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -3053517561141063969L;
	/** 内容ID */
	private java.lang.String contentId;
	/** 标题 */
	private java.lang.String relateTitle;
	/** URL路径 */
	private java.lang.String relateUrl;
	/** 缩略图 */
	private java.lang.String images;
	/** 类型 */
	private java.lang.String relateType;
	/** 顺序 */
	private java.lang.Integer relateOrder;
	/** 表单新增时间 */
	private java.util.Date newdate;
	/** 创建时间 */
	private java.util.Date created;
	/** 区别内容id */
	private java.lang.String part;
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 内容ID
	 */
	@Column(name = "CONTENT_ID", nullable = false, length = 255)
	public java.lang.String getContentId() {
		return this.contentId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 内容ID
	 */
	public void setContentId(java.lang.String contentId) {
		this.contentId = contentId;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 标题
	 */
	@Column(name = "RELATE_TITLE", nullable = false, length = 80)
	public java.lang.String getRelateTitle() {
		return this.relateTitle;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 标题
	 */
	public void setRelateTitle(java.lang.String relateTitle) {
		this.relateTitle = relateTitle;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String URL路径
	 */
	@Column(name = "RELATE_URL", nullable = true, length = 100)
	public java.lang.String getRelateUrl() {
		return this.relateUrl;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String URL路径
	 */
	public void setRelateUrl(java.lang.String relateUrl) {
		this.relateUrl = relateUrl;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 缩略图
	 */
	@Column(name = "IMAGES", nullable = false, length = 100)
	public java.lang.String getImages() {
		return this.images;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 缩略图
	 */
	public void setImages(java.lang.String images) {
		this.images = images;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 类型
	 */
	@Column(name = "RELATE_TYPE", nullable = false, length = 255)
	public java.lang.String getRelateType() {
		return this.relateType;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 类型
	 */
	public void setRelateType(java.lang.String relateType) {
		this.relateType = relateType;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 顺序
	 */
	@Column(name = "RELATE_ORDER", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getRelateOrder() {
		return this.relateOrder;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 顺序
	 */
	public void setRelateOrder(java.lang.Integer relateOrder) {
		this.relateOrder = relateOrder;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 表单新增时间
	 */
	@Column(name = "NEWDATE", nullable = true)
	public java.util.Date getNewdate() {
		return this.newdate;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 表单新增时间
	 */
	public void setNewdate(java.util.Date newdate) {
		this.newdate = newdate;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATED", nullable = true)
	public java.util.Date getCreated() {
		return this.created;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 创建时间
	 */
	public void setCreated(java.util.Date created) {
		this.created = created;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 区别内容id
	 */
	@Column(name = "PART", nullable = true, length = 255)
	public java.lang.String getPart() {
		return this.part;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 区别内容id
	 */
	public void setPart(java.lang.String part) {
		this.part = part;
	}
}
