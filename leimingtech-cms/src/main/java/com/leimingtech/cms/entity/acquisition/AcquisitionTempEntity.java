package com.leimingtech.cms.entity.acquisition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 采集进度
 * @author zhangdaihao
 * @date 2014-04-15 17:14:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_acquisition_temp", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AcquisitionTempEntity extends IdEntity implements java.io.Serializable {

	/**siteId*/
	private java.lang.String siteId;
	/**栏目地址*/
	private java.lang.String channelUrl;
	/**内容地址*/
	private java.lang.String contentUrl;
	/**标题*/
	private java.lang.String title;
	/**百分比*/
	private java.lang.Integer percents;
	/**描述*/
	private java.lang.String description;
	/**顺序*/
	private java.lang.Integer seq;
	
	/**创建时间*/
	private java.util.Date createdtime;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建时间
	 */
	@Column(name = "CREATEDTIME", nullable = true)
	public java.util.Date getCreatedtime() {
		return createdtime;
	}
	/**
	 *方法: 设置java.lang.String
	 *@return: java.lang.String  创建时间
	 */
	public void setCreatedtime(java.util.Date createdtime) {
		this.createdtime = createdtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  siteId
	 */
	@Column(name ="SITE_ID",nullable=true,length=32)
	public java.lang.String getSiteId(){
		return this.siteId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  siteId
	 */
	public void setSiteId(java.lang.String siteId){
		this.siteId = siteId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  栏目地址
	 */
	@Column(name ="CHANNEL_URL",nullable=false,length=255)
	public java.lang.String getChannelUrl(){
		return this.channelUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  栏目地址
	 */
	public void setChannelUrl(java.lang.String channelUrl){
		this.channelUrl = channelUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容地址
	 */
	@Column(name ="CONTENT_URL",nullable=false,length=255)
	public java.lang.String getContentUrl(){
		return this.contentUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容地址
	 */
	public void setContentUrl(java.lang.String contentUrl){
		this.contentUrl = contentUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=true,length=255)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  百分比
	 */
	@Column(name ="PERCENTS",nullable=false,precision=10,scale=0)
	public java.lang.Integer getPercents(){
		return this.percents;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  百分比
	 */
	public void setPercents(java.lang.Integer percents){
		this.percents = percents;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述
	 */
	@Column(name ="DESCRIPTION",nullable=false,length=20)
	public java.lang.String getDescription(){
		return this.description;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setDescription(java.lang.String description){
		this.description = description;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  顺序
	 */
	@Column(name ="SEQ",nullable=false,precision=10,scale=0)
	public java.lang.Integer getSeq(){
		return this.seq;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  顺序
	 */
	public void setSeq(java.lang.Integer seq){
		this.seq = seq;
	}
}
