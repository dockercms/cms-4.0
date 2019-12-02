package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 视频广告管理
 * @author 
 * @date 2014-07-11 11:05:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_video_adv", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class VideoadvEntity extends IdEntity implements java.io.Serializable {

	/**片头广告状态*/
	private java.lang.Integer headadvstatus;
	/**片头广告资源地址*/
	private java.lang.String headadvresource;
	/**片头广告链接*/
	private java.lang.String headadvlink;
	/**片头广告时长*/
	private java.lang.Integer headadvtime;
	/**暂停广告状态*/
	private java.lang.Integer stopadvstatus;
	/**暂停广告资源地址*/
	private java.lang.String stopadvreource;
	/**暂停广告链接*/
	private java.lang.String stopadvlink;
	/**片尾广告状态*/
	private java.lang.Integer footadvstatus;
	/**片尾广告资源地址*/
	private java.lang.String footadvresource;
	/**片尾广告链接*/
	private java.lang.String footadvlink;
	/**片尾广告时长*/
	private java.lang.Integer footadvtime;
	/**站点id*/
	private java.lang.String siteId;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  片头广告状态
	 */
	@Column(name ="HEADADVSTATUS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getHeadadvstatus(){
		return this.headadvstatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  片头广告状态
	 */
	public void setHeadadvstatus(java.lang.Integer headadvstatus){
		this.headadvstatus = headadvstatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  片头广告资源地址
	 */
	@Column(name ="HEADADVRESOURCE",nullable=true,length=255)
	public java.lang.String getHeadadvresource(){
		return this.headadvresource;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  片头广告资源地址
	 */
	public void setHeadadvresource(java.lang.String headadvresource){
		this.headadvresource = headadvresource;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  片头广告链接
	 */
	@Column(name ="HEADADVLINK",nullable=true,length=255)
	public java.lang.String getHeadadvlink(){
		return this.headadvlink;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  片头广告链接
	 */
	public void setHeadadvlink(java.lang.String headadvlink){
		this.headadvlink = headadvlink;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  片头广告时长
	 */
	@Column(name ="HEADADVTIME",nullable=true,precision=10,scale=0)
	public java.lang.Integer getHeadadvtime(){
		return this.headadvtime;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  片头广告时长
	 */
	public void setHeadadvtime(java.lang.Integer headadvtime){
		this.headadvtime = headadvtime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  暂停广告状态
	 */
	@Column(name ="STOPADVSTATUS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getStopadvstatus(){
		return this.stopadvstatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  暂停广告状态
	 */
	public void setStopadvstatus(java.lang.Integer stopadvstatus){
		this.stopadvstatus = stopadvstatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  暂停广告资源地址
	 */
	@Column(name ="STOPADVREOURCE",nullable=true,length=255)
	public java.lang.String getStopadvreource(){
		return this.stopadvreource;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  暂停广告资源地址
	 */
	public void setStopadvreource(java.lang.String stopadvreource){
		this.stopadvreource = stopadvreource;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  暂停广告链接
	 */
	@Column(name ="STOPADVLINK",nullable=true,length=255)
	public java.lang.String getStopadvlink(){
		return this.stopadvlink;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  暂停广告链接
	 */
	public void setStopadvlink(java.lang.String stopadvlink){
		this.stopadvlink = stopadvlink;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  片尾广告状态
	 */
	@Column(name ="FOOTADVSTATUS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getFootadvstatus(){
		return this.footadvstatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  片尾广告状态
	 */
	public void setFootadvstatus(java.lang.Integer footadvstatus){
		this.footadvstatus = footadvstatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  片尾广告资源地址
	 */
	@Column(name ="FOOTADVRESOURCE",nullable=true,length=255)
	public java.lang.String getFootadvresource(){
		return this.footadvresource;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  片尾广告资源地址
	 */
	public void setFootadvresource(java.lang.String footadvresource){
		this.footadvresource = footadvresource;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  片尾广告链接
	 */
	@Column(name ="FOOTADVLINK",nullable=true,length=255)
	public java.lang.String getFootadvlink(){
		return this.footadvlink;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  片尾广告链接
	 */
	public void setFootadvlink(java.lang.String footadvlink){
		this.footadvlink = footadvlink;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  片尾广告时长
	 */
	@Column(name ="FOOTADVTIME",nullable=true,precision=10,scale=0)
	public java.lang.Integer getFootadvtime(){
		return this.footadvtime;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  片尾广告时长
	 */
	public void setFootadvtime(java.lang.Integer footadvtime){
		this.footadvtime = footadvtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点id
	 */
	@Column(name ="SITE_ID",nullable=true,length=32)
	public java.lang.String getSiteId(){
		return this.siteId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点id
	 */
	public void setSiteId(java.lang.String siteId){
		this.siteId = siteId;
	}
}
