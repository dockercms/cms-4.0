package com.leimingtech.cms.entity.acquisition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 采集历史记录
 * @author zhangdaihao
 * @date 2014-04-15 17:13:09
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_acquisition_history", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AcquisitionHistoryEntity extends IdEntity implements java.io.Serializable {
	
	/**栏目地址*/
	private java.lang.String channelUrl;
	/**内容地址*/
	private java.lang.String contentUrl;
	/**标题*/
	private java.lang.String title;
	/**描述*/
	private java.lang.String description;
	/**采集源*/
	private java.lang.String acquisitionId;
	/**内容*/
	private java.lang.String contentId;
	
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
	 * 内容
	 */
	private AcquisitionContentEntity acqc;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTENT_ID")
	public AcquisitionContentEntity getContent() {
		return acqc;
	}

	public void setContent(AcquisitionContentEntity acqc) {
		this.acqc = acqc;
	}
	
	/**
	 * 采集源
	 */
	private AcquisitionEntity acqu;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACQUISITION_ID")
	public AcquisitionEntity getAcquisition() {
		return acqu;
	}

	public void setAcquisition(AcquisitionEntity acqu) {
		this.acqu = acqu;
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
//	/**
//	 *方法: 取得java.lang.Integer
//	 *@return: java.lang.Integer  采集源
//	 */
//	@Column(name ="ACQUISITION_ID",nullable=true,precision=10,scale=0)
//	public java.lang.Integer getAcquisitionId(){
//		return this.acquisitionId;
//	}
//
//	/**
//	 *方法: 设置java.lang.Integer
//	 *@param: java.lang.Integer  采集源
//	 */
//	public void setAcquisitionId(java.lang.Integer acquisitionId){
//		this.acquisitionId = acquisitionId;
//	}
//	/**
//	 *方法: 取得java.lang.Integer
//	 *@return: java.lang.Integer  内容
//	 */
//	@Column(name ="CONTENT_ID",nullable=true,precision=10,scale=0)
//	public java.lang.Integer getContentId(){
//		return this.contentId;
//	}
//
//	/**
//	 *方法: 设置java.lang.Integer
//	 *@param: java.lang.Integer  内容
//	 */
//	public void setContentId(java.lang.Integer contentId){
//		this.contentId = contentId;
//	}
}
