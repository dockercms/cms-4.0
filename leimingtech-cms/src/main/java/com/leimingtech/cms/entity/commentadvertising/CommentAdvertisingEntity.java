package com.leimingtech.cms.entity.commentadvertising;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 评论广告
 * @author 
 * @date 2017-04-26 11:55:55
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_comment_advertising", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class CommentAdvertisingEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -3825351631892105034L;
	/** 站点id */
	private java.lang.String siteId;
	/** 广告名称 */
	private java.lang.String name;
	/** 资源图片路径 */
	private java.lang.String imageUrl;
	/** 图片宽 */
	private java.lang.Double imageWidth;
	/** 图片高 */
	private java.lang.Double imageHeigh;
	/** 链接提示 */
	private java.lang.String linkRemark;
	/** 链接目标(0:新窗口 1:原窗口) */
	private java.lang.Integer linkTarget;
	/** 链接地址 */
	private java.lang.String linkUrl;
	/** 权重 */
	private java.lang.Integer weight;
	/** 开始时间 */
	private java.util.Date startTime;
	/** 结束时间 */
	private java.util.Date endTime;
	/** 是否启用(0:是 1:否 ) */
	private java.lang.Integer isUsing;
	/** 创建时间 */
	private java.util.Date createTime;
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 站点id
	 */
	@Column(name = "SITE_ID", nullable = false, length = 32)
	public java.lang.String getSiteId() {
		return this.siteId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 站点id
	 */
	public void setSiteId(java.lang.String siteId) {
		this.siteId = siteId;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 广告名称
	 */
	@Column(name = "NAME", nullable = true, length = 255)
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 广告名称
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 资源图片路径
	 */
	@Column(name = "IMAGE_URL", nullable = true, length = 255)
	public java.lang.String getImageUrl() {
		return this.imageUrl;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 资源图片路径
	 */
	public void setImageUrl(java.lang.String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	/**
	 * 方法: 取得java.lang.Double
	 *
	 * @return: java.lang.Double 图片宽
	 */
	@Column(name = "IMAGE_WIDTH", nullable = true, precision = 22)
	public java.lang.Double getImageWidth() {
		return this.imageWidth;
	}

	/**
	 * 方法: 设置java.lang.Double
	 *
	 * @param: java.lang.Double 图片宽
	 */
	public void setImageWidth(java.lang.Double imageWidth) {
		this.imageWidth = imageWidth;
	}
	
	/**
	 * 方法: 取得java.lang.Double
	 *
	 * @return: java.lang.Double 图片高
	 */
	@Column(name = "IMAGE_HEIGH", nullable = true, precision = 22)
	public java.lang.Double getImageHeigh() {
		return this.imageHeigh;
	}

	/**
	 * 方法: 设置java.lang.Double
	 *
	 * @param: java.lang.Double 图片高
	 */
	public void setImageHeigh(java.lang.Double imageHeigh) {
		this.imageHeigh = imageHeigh;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 链接提示
	 */
	@Column(name = "LINK_REMARK", nullable = true, length = 1000)
	public java.lang.String getLinkRemark() {
		return this.linkRemark;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 链接提示
	 */
	public void setLinkRemark(java.lang.String linkRemark) {
		this.linkRemark = linkRemark;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 链接目标(0:新窗口 1:原窗口)
	 */
	@Column(name = "LINK_TARGET", nullable = true, precision = 3, scale = 0)
	public java.lang.Integer getLinkTarget() {
		return this.linkTarget;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 链接目标(0:新窗口 1:原窗口)
	 */
	public void setLinkTarget(java.lang.Integer linkTarget) {
		this.linkTarget = linkTarget;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 链接地址
	 */
	@Column(name = "LINK_URL", nullable = true, length = 1000)
	public java.lang.String getLinkUrl() {
		return this.linkUrl;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 链接地址
	 */
	public void setLinkUrl(java.lang.String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 权重
	 */
	@Column(name = "WEIGHT", nullable = true, precision = 22)
	public java.lang.Integer getWeight() {
		return this.weight;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 权重
	 */
	public void setWeight(java.lang.Integer weight) {
		this.weight = weight;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 开始时间
	 */
	@Column(name = "START_TIME", nullable = true)
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 开始时间
	 */
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 结束时间
	 */
	@Column(name = "END_TIME", nullable = true)
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 结束时间
	 */
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否启用(0:是 1:否 )
	 */
	@Column(name = "IS_USING", nullable = true, precision = 3, scale = 0)
	public java.lang.Integer getIsUsing() {
		return this.isUsing;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否启用(0:是 1:否 )
	 */
	public void setIsUsing(java.lang.Integer isUsing) {
		this.isUsing = isUsing;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATE_TIME", nullable = true)
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 创建时间
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
}
