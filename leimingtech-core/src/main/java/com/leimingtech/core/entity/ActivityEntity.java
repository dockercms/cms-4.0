package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
/**   
 * @Title: Entity
 * @Description: 活动
 * @author zhangdaihao
 * @date 2014-05-06 16:05:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_activity", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ActivityEntity extends IdEntity implements java.io.Serializable {
	
	/**内容ID*/
	private java.lang.String contentid;
	
	/**活动开始时间*/
	private java.util.Date activitystarttime;
	/**活动结束时间*/
	private java.util.Date activityendtime;
	/**活动报名开始时间*/
	private java.util.Date activityapplystarttime;
	/**活动报名截止时间*/
	private java.util.Date activityapplyendtime;
	/**人数限制*/
	private java.lang.String activityplenum;
	/**IP限制*/
	private java.lang.String activityiplimit;
	/**性别*/
	private java.lang.String activitysex;
	/**页面背景图*/
	private java.lang.String activitybackground;
	/**活动类型*/
	private java.lang.String activitydatatype;
	/**活动地址*/
	private java.lang.String activityaddress;
	/**活动内容*/
	private java.lang.String activitycontent;
	/**备注*/
	private java.lang.String activityremark;
	/**创建时间*/
	private java.util.Date createdtime;
	
	/**坐标点*/
	private java.lang.String activityaddressPoint;
	/**站点id*/
	private java.lang.String siteid;
	
	/**发起人*/
	private java.lang.String activitypeople;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容ID
	 */
	@Column(name ="CONTENTID",nullable=true,length=32)
	public java.lang.String getContentid(){
		return this.contentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容ID
	 */
	public void setContentid(java.lang.String contentid){
		this.contentid = contentid;
	}
	
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  活动开始时间
	 */
	@Column(name ="ACTIVITYSTARTTIME",nullable=true)
	public java.util.Date getActivitystarttime(){
		return this.activitystarttime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  活动开始时间
	 */
	public void setActivitystarttime(java.util.Date activitystarttime){
		this.activitystarttime = activitystarttime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  活动结束时间
	 */
	@Column(name ="ACTIVITYENDTIME",nullable=true)
	public java.util.Date getActivityendtime(){
		return this.activityendtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  活动结束时间
	 */
	public void setActivityendtime(java.util.Date activityendtime){
		this.activityendtime = activityendtime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  活动报名开始时间
	 */
	@Column(name ="ACTIVITYAPPLYSTARTTIME",nullable=true)
	public java.util.Date getActivityapplystarttime(){
		return this.activityapplystarttime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  活动报名开始时间
	 */
	public void setActivityapplystarttime(java.util.Date activityapplystarttime){
		this.activityapplystarttime = activityapplystarttime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  活动报名截止时间
	 */
	@Column(name ="ACTIVITYAPPLYENDTIME",nullable=true)
	public java.util.Date getActivityapplyendtime(){
		return this.activityapplyendtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  活动报名截止时间
	 */
	public void setActivityapplyendtime(java.util.Date activityapplyendtime){
		this.activityapplyendtime = activityapplyendtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  人数限制
	 */
	@Column(name ="ACTIVITYPLENUM",nullable=true,length=255)
	public java.lang.String getActivityplenum(){
		return this.activityplenum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  人数限制
	 */
	public void setActivityplenum(java.lang.String activityplenum){
		this.activityplenum = activityplenum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  IP限制
	 */
	@Column(name ="ACTIVITYIPLIMIT",nullable=true,length=11)
	public java.lang.String getActivityiplimit(){
		return this.activityiplimit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  IP限制
	 */
	public void setActivityiplimit(java.lang.String activityiplimit){
		this.activityiplimit = activityiplimit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */
	@Column(name ="ACTIVITYSEX",nullable=true,length=11)
	public java.lang.String getActivitysex(){
		return this.activitysex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setActivitysex(java.lang.String activitysex){
		this.activitysex = activitysex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  页面背景图
	 */
	@Column(name ="ACTIVITYBACKGROUND",nullable=true,length=255)
	public java.lang.String getActivitybackground(){
		return this.activitybackground;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  页面背景图
	 */
	public void setActivitybackground(java.lang.String activitybackground){
		this.activitybackground = activitybackground;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动类型
	 */
	@Column(name ="ACTIVITYDATATYPE",nullable=true,length=255)
	public java.lang.String getActivitydatatype(){
		return this.activitydatatype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动类型
	 */
	public void setActivitydatatype(java.lang.String activitydatatype){
		this.activitydatatype = activitydatatype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动地址
	 */
	@Column(name ="ACTIVITYADDRESS",nullable=true,length=255)
	public java.lang.String getActivityaddress(){
		return this.activityaddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动地址
	 */
	public void setActivityaddress(java.lang.String activityaddress){
		this.activityaddress = activityaddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动内容
	 */
	@Column(name ="ACTIVITYCONTENT",nullable=true,length=1000)
	public java.lang.String getActivitycontent(){
		return this.activitycontent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动内容
	 */
	public void setActivitycontent(java.lang.String activitycontent){
		this.activitycontent = activitycontent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="ACTIVITYREMARK",nullable=true,length=255)
	public java.lang.String getActivityremark(){
		return this.activityremark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setActivityremark(java.lang.String activityremark){
		this.activityremark = activityremark;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发起人
	 */
	@Column(name ="ACTIVITYPEOPLE",nullable=true,length=255)
	public java.lang.String getActivitypeople(){
		return this.activitypeople;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发起人
	 */
	public void setActivitypeople(java.lang.String activitypeople){
		this.activitypeople = activitypeople;
	}
	
	@Column(name ="ACTIVITYADDRESSPOINT",nullable=true,length=255)
	public java.lang.String getActivityaddressPoint() {
		return activityaddressPoint;
	}

	public void setActivityaddressPoint(java.lang.String activityaddressPoint) {
		this.activityaddressPoint = activityaddressPoint;
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
