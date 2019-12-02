package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;


/**   
 * @Title: Entity
 * @Description: 视频库
 * @author linjm
 * @date 2014-04-30 10:35:08
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_video_sources", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class VideoSourcesEntity extends IdEntity  implements java.io.Serializable {
	 
	/**视频原名*/
	private java.lang.String videoname;
	/**上传后的名字*/
	private java.lang.String realname;
	/**转化后本地路径*/
	private java.lang.String transpath;
	/**访问路径*/
	private java.lang.String localpath;
	/**默认图片*/
	private java.lang.String defaultimage;
	/**分类*/
	private java.lang.String type;
	/**时长*/
	private Integer timesize;
	/**创建时间*/
	private java.util.Date createdate;
	/**备注*/
	private java.lang.String mark;
	/**大小*/
	private java.lang.Long videosize;
	/**站点id*/
	private java.lang.String siteid;
 
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  视频原名
	 */
	@Column(name ="VIDEONAME",nullable=true,length=225)
	public java.lang.String getVideoname(){
		return this.videoname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  视频原名
	 */
	public void setVideoname(java.lang.String videoname){
		this.videoname = videoname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上传后的名字
	 */
	@Column(name ="REALNAME",nullable=true,length=225)
	public java.lang.String getRealname(){
		return this.realname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上传后的名字
	 */
	public void setRealname(java.lang.String realname){
		this.realname = realname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  转化后本地路径
	 */
	@Column(name ="TRANSPATH",nullable=true,length=300)
	public java.lang.String getTranspath(){
		return this.transpath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  转化后本地路径
	 */
	public void setTranspath(java.lang.String transpath){
		this.transpath = transpath;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  访问路径
	 */
	@Column(name ="LOCALPATH",nullable=true,length=300)
	public java.lang.String getLocalpath(){
		return this.localpath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  访问路径
	 */
	public void setLocalpath(java.lang.String localpath){
		this.localpath = localpath;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  默认图片
	 */
	@Column(name ="defaultimage",nullable=true,length=300)
	public java.lang.String getDefaultimage() {
		return defaultimage;
	}

	public void setDefaultimage(java.lang.String defaultimage) {
		this.defaultimage = defaultimage;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类
	 */
	@Column(name ="TYPE",nullable=true,length=10)
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	/**
	 *方法: 取得int
	 *@return: int  时长
	 */
	@Column(name ="TIMESIZE",nullable=true,precision=32,scale=0)
	public Integer getTimesize(){
		return this.timesize;
	}

	/**
	 *方法: 设置int
	 *@param: int  时长
	 */
	public void setTimesize(Integer timesize){
		this.timesize = timesize;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATEDATE",nullable=true)
	public java.util.Date getCreatedate(){
		return this.createdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreatedate(java.util.Date createdate){
		this.createdate = createdate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="MARK",nullable=true,length=500)
	public java.lang.String getMark(){
		return this.mark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setMark(java.lang.String mark){
		this.mark = mark;
	}

	/**
	 *方法: 取得java.lang.Long
	 *@return: java.lang.Long  大小
	 */
	@Column(name ="videosize",nullable=true)
	public java.lang.Long getVideosize() {
		return videosize;
	}

	public void setVideosize(java.lang.Long videosize) {
		this.videosize = videosize;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  站点id
	 */
	@Column(name ="SITE_ID",nullable=true,length=32)
	public java.lang.String getSiteid() {
		return siteid;
	}
	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  站点id
	 */
	public void setSiteid(java.lang.String siteid) {
		this.siteid = siteid;
	}
}
