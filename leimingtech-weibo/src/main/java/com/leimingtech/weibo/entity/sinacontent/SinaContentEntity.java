package com.leimingtech.weibo.entity.sinacontent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 新浪微博内容
 * @author 
 * @date 2015-12-05 13:56:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sina_content", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SinaContentEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -2173686425512447896L;
	/** 标题 */
	private java.lang.String sinaTitle;
	/** 摘要 */
	private java.lang.String sinaDigest;
	/** 图片路径 */
	private java.lang.String sinaThumb;
	/** sinaUrl */
	private java.lang.String sinaUrl;
	/** 站点id */
	private java.lang.String siteId;
	/** 创建时间 */
	private java.util.Date createtime;
	/** 内容id */
	private java.lang.String contentid;
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 标题
	 */
	@Column(name = "SINA_TITLE", nullable = true, length = 255)
	public java.lang.String getSinaTitle() {
		return this.sinaTitle;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 标题
	 */
	public void setSinaTitle(java.lang.String sinaTitle) {
		this.sinaTitle = sinaTitle;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 摘要
	 */
	@Column(name = "SINA_DIGEST", nullable = true, length = 255)
	public java.lang.String getSinaDigest() {
		return this.sinaDigest;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 摘要
	 */
	public void setSinaDigest(java.lang.String sinaDigest) {
		this.sinaDigest = sinaDigest;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 图片路径
	 */
	@Column(name = "SINA_THUMB", nullable = true, length = 255)
	public java.lang.String getSinaThumb() {
		return this.sinaThumb;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 图片路径
	 */
	public void setSinaThumb(java.lang.String sinaThumb) {
		this.sinaThumb = sinaThumb;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String sinaUrl
	 */
	@Column(name = "SINA_URL", nullable = true, length = 255)
	public java.lang.String getSinaUrl() {
		return this.sinaUrl;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String sinaUrl
	 */
	public void setSinaUrl(java.lang.String sinaUrl) {
		this.sinaUrl = sinaUrl;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 站点id
	 */
	@Column(name = "SITE_ID", nullable = true, length = 32)
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
	 * @return: java.lang.String 内容id
	 */
	@Column(name = "CONTENTID", nullable = true, length = 32)
	public java.lang.String getContentid() {
		return this.contentid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 内容id
	 */
	public void setContentid(java.lang.String contentid) {
		this.contentid = contentid;
	}
}
