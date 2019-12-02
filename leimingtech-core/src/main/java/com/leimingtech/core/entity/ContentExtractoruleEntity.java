package com.leimingtech.core.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 一键抓取规则管理
 * @author 
 * @date 2015-08-04 16:53:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_content_extractor_rule", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ContentExtractoruleEntity extends IdEntity implements java.io.Serializable {

	public static List<ContentExtractoruleEntity> allContentExtractorule=new ArrayList<ContentExtractoruleEntity>();
	
	private static final long serialVersionUID = 2621364570250345626L;
	/** 网址名称 */
	private java.lang.String websitename;
	/** 网页网址关键字片段 */
	private java.lang.String urikeywordsfragment;
	/** 网页主题内容标签 */
	private java.lang.String nesbodytag;
	/** 网页主题内容标签Class属性 */
	private java.lang.String newsbodyclass;
	/** 网页主题内容标签Id属性 */
	private java.lang.String newsbodyid;
	/** 开始标签 */
	private java.lang.String begintag;
	private java.lang.String begintagEscape;
	/** 结束标签 */
	private java.lang.String endtag;
	private java.lang.String endtagEscape;
	/** 抓取方案 */
	private java.lang.String scheme;
	/** 自定义属性名 */
	private java.lang.String attrname;
	/** 自定义属性值*/
	private java.lang.String attrvalue;
	/** 版本 */
	private java.lang.Integer version;
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 网址名称
	 */
	@Column(name = "WEBSITENAME", nullable = true, length = 255)
	public java.lang.String getWebsitename() {
		return this.websitename;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 网址名称
	 */
	public void setWebsitename(java.lang.String websitename) {
		this.websitename = websitename;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 网页网址关键字片段
	 */
	@Column(name = "URIKEYWORDSFRAGMENT", nullable = true, length = 255)
	public java.lang.String getUrikeywordsfragment() {
		return this.urikeywordsfragment;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 网页网址关键字片段
	 */
	public void setUrikeywordsfragment(java.lang.String urikeywordsfragment) {
		this.urikeywordsfragment = urikeywordsfragment;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 网页主题内容标签
	 */
	@Column(name = "NESBODYTAG", nullable = true, length = 255)
	public java.lang.String getNesbodytag() {
		return this.nesbodytag;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 网页主题内容标签
	 */
	public void setNesbodytag(java.lang.String nesbodytag) {
		this.nesbodytag = nesbodytag;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 网页主题内容标签Class属性
	 */
	@Column(name = "NEWSBODYCLASS", nullable = true, length = 255)
	public java.lang.String getNewsbodyclass() {
		return this.newsbodyclass;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 网页主题内容标签Class属性
	 */
	public void setNewsbodyclass(java.lang.String newsbodyclass) {
		this.newsbodyclass = newsbodyclass;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 网页主题内容标签Id属性
	 */
	@Column(name = "NEWSBODYID", nullable = true, length = 255)
	public java.lang.String getNewsbodyid() {
		return this.newsbodyid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 网页主题内容标签Id属性
	 */
	public void setNewsbodyid(java.lang.String newsbodyid) {
		this.newsbodyid = newsbodyid;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 开始标签
	 */
	@Column(name = "BEGINTAG", nullable = true, length = 255)
	public java.lang.String getBegintag() {
		return this.begintag;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 开始标签
	 */
	public void setBegintag(java.lang.String begintag) {
		this.begintag = begintag;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 结束标签
	 */
	@Column(name = "ENDTAG", nullable = true, length = 255)
	public java.lang.String getEndtag() {
		return this.endtag;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 结束标签
	 */
	public void setEndtag(java.lang.String endtag) {
		this.endtag = endtag;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 抓取方案
	 */
	@Column(name = "SCHEME", nullable = true, length = 255)
	public java.lang.String getScheme() {
		return this.scheme;
	}
	
	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 抓取方案
	 */
	public void setScheme(java.lang.String scheme) {
		this.scheme = scheme;
	}
	
	@Column(name = "attrname", nullable = true, length = 255)
	public java.lang.String getAttrname() {
		return attrname;
	}
	
	public void setAttrname(java.lang.String attrname) {
		this.attrname = attrname;
	}
	
	@Column(name = "attrvalue", nullable = true, length = 255)
	public java.lang.String getAttrvalue() {
		return attrvalue;
	}

	
	public void setAttrvalue(java.lang.String attrvalue) {
		this.attrvalue = attrvalue;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 版本
	 */
	@Column(name = "VERSION", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getVersion() {
		return this.version;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 版本
	 */
	public void setVersion(java.lang.Integer version) {
		this.version = version;
	}

	@Transient
	public java.lang.String getBegintagEscape() {
		begintagEscape=org.apache.commons.lang.StringEscapeUtils.escapeHtml(this.getBegintag());
		return begintagEscape;
	}
	
	@Transient
	public java.lang.String getEndtagEscape() {
		endtagEscape=org.apache.commons.lang.StringEscapeUtils.escapeHtml(this.getEndtag());
		return endtagEscape;
	}

}
