package com.leimingtech.core.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;



/**   
 * @Title: Entity
 * @Description: 水印方案
 * @author 
 * @date 2014-05-16 13:56:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_watermark", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class WaterMarkEntity extends IdEntity implements java.io.Serializable {
	 
	/**名称*/
	private java.lang.String name;
	/**片图路径*/
	private java.lang.String imagepath;
	/**水印位置*/
	private java.lang.String position;
	/**宽*/
	private java.lang.String width;
	/**高*/
	private java.lang.String height;
	/**水印不透明度*/
	private java.lang.String undiaphaneity;
	/**水印质量*/
	private java.lang.String quality;
	/**状态*/
	private java.lang.Integer state;
	/**默认方案*/
	private java.lang.Integer defaultset;
	/**所属id*/
	private java.lang.String departid;
	/**站点id*/
	private java.lang.String siteId;

	/**创建时间*/
	private java.util.Date createdtime;
	 
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=true,length=32)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  片图路径
	 */
	@Column(name ="IMAGEPATH",nullable=true,length=90)
	public java.lang.String getImagepath(){
		return this.imagepath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  片图路径
	 */
	public void setImagepath(java.lang.String imagepath){
		this.imagepath = imagepath;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  水印位置
	 */
	@Column(name ="POSITION",nullable=true,length=32)
	public java.lang.String getPosition(){
		return this.position;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  水印位置
	 */
	public void setPosition(java.lang.String position){
		this.position = position;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  宽
	 */
	@Column(name ="WIDTH",nullable=true,length=32)
	public java.lang.String getWidth(){
		return this.width;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  宽
	 */
	public void setWidth(java.lang.String width){
		this.width = width;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  高
	 */
	@Column(name ="HEIGHT",nullable=true,length=32)
	public java.lang.String getHeight(){
		return this.height;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  高
	 */
	public void setHeight(java.lang.String height){
		this.height = height;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  水印不透明度
	 */
	@Column(name ="UNDIAPHANEITY",nullable=true,length=32)
	public java.lang.String getUndiaphaneity(){
		return this.undiaphaneity;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  水印不透明度
	 */
	public void setUndiaphaneity(java.lang.String undiaphaneity){
		this.undiaphaneity = undiaphaneity;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  水印质量
	 */
	@Column(name ="QUALITY",nullable=true,length=32)
	public java.lang.String getQuality() {
		return quality;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  水印质量
	 */
	public void setQuality(java.lang.String quality) {
		this.quality = quality;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  状态
	 */
	@Column(name ="STATE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getState(){
		return this.state;
	}


	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  状态
	 */
	public void setState(java.lang.Integer state){
		this.state = state;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  默认方案
	 */
	@Column(name ="DEFAULTSET",nullable=true,precision=10,scale=0)
	public java.lang.Integer getDefaultset(){
		return this.defaultset;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  默认方案
	 */
	public void setDefaultset(java.lang.Integer defaultset){
		this.defaultset = defaultset;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属id
	 */
	@Column(name ="DEPARTID",nullable=true,length=32)
	public java.lang.String getDepartid(){
		return this.departid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属id
	 */
	public void setDepartid(java.lang.String departid){
		this.departid = departid;
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
	/**
	 *方法: 取得java.lang.Date
	 *@return: java.lang.Date  创建时间
	 */
	@Column(name = "CREATEDTIME", nullable = true)
	public java.util.Date getCreatedtime() {
		return createdtime;
	}
	/**
	 *方法: 设置java.lang.Date
	 *@return: java.lang.Date  创建时间
	 */
	public void setCreatedtime(java.util.Date createdtime) {
		this.createdtime = createdtime;
	}
}
