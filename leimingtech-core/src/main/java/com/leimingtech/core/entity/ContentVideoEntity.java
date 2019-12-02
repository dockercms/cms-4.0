package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 视频
 * @author zhangdaihao
 * @date 2014-04-28 14:07:11
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_video", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ContentVideoEntity extends IdEntity implements java.io.Serializable {
	
	/**内容ID*/
	private String contentid;
	/**视频名称*/
	private java.lang.String videoname;
	/**视频url*/
	private java.lang.String videourl;
	/**分类*/
	private java.lang.String videoclassify;
	/**视频时长*/
	private java.lang.String time;
	/**视频专辑*/
	private java.lang.String special;
	/**备注*/
	private java.lang.String videoremark;
	/**站点id*/
	private java.lang.String siteid;
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
	 *@return: java.lang.Integer  内容ID
	 */
	@Column(name ="CONTENTID",nullable=true,precision=7,scale=0)
	public java.lang.String getContentid(){
		return this.contentid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  内容ID
	 */
	public void setContentid(java.lang.String contentid){
		this.contentid = contentid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  视频名称
	 */
	@Column(name ="VIDEONAME",nullable=true,length=255)
	public java.lang.String getVideoname(){
		return this.videoname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  视频名称
	 */
	public void setVideoname(java.lang.String videoname){
		this.videoname = videoname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  视频url
	 */
	@Column(name ="VIDEOURL",nullable=true,length=4000)
	public java.lang.String getVideourl(){
		return this.videourl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  视频url
	 */
	public void setVideourl(java.lang.String videourl){
		this.videourl = videourl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类
	 */
	@Column(name ="VIDEOCLASSIFY",nullable=true,length=255)
	public java.lang.String getVideoclassify(){
		return this.videoclassify;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类
	 */
	public void setVideoclassify(java.lang.String videoclassify){
		this.videoclassify = videoclassify;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  视频时长
	 */
	@Column(name ="TIME",nullable=true,length=255)
	public java.lang.String getTime(){
		return this.time;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  视频时长
	 */
	public void setTime(java.lang.String time){
		this.time = time;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  视频专辑
	 */
	@Column(name ="SPECIAL",nullable=true,length=255)
	public java.lang.String getSpecial(){
		return this.special;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  视频专辑
	 */
	public void setSpecial(java.lang.String special){
		this.special = special;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="VIDEOREMARK",nullable=true,length=1000)
	public java.lang.String getVideoremark(){
		return this.videoremark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setVideoremark(java.lang.String videoremark){
		this.videoremark = videoremark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点id
	 */
	@Column(name ="SITE_ID",nullable=true,length=32)
	public java.lang.String getSiteid() {
		return siteid;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点id
	 */
	public void setSiteid(java.lang.String siteid) {
		this.siteid = siteid;
	}
}
