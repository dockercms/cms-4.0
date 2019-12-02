package com.leimingtech.mobile.entity.advertisemen;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 启动画面广告
 * @author 
 * @date 2014-08-20 21:04:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_advertisemen_starting", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AdvertisemenStartingEntity  extends IdEntity  implements java.io.Serializable {
	 
	/**图片高度*/
	private java.lang.Integer imgHeight;
	/**图片宽度*/
	private java.lang.Integer imgWidth;
	/**图片地址*/
	private java.lang.String imgUrl;
	
	/**创建时间*/
	private java.util.Date createdtime;
	
	/**是否显示启动图*/
	private java.lang.Integer  display;
	
	/**是否跳转*/
	private java.lang.Integer  jump;
	
	/**显示时间*/
	private java.lang.Double  showtime;
	
	/**链接站点*/
	private java.lang.String   siteId;
	
	/**排序*/
	private java.lang.Integer  sort;
	
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
	 *@return: java.lang.Integer  图片高度
	 */
	@Column(name ="IMG_HEIGHT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getImgHeight(){
		return this.imgHeight;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  图片高度
	 */
	public void setImgHeight(java.lang.Integer imgHeight){
		this.imgHeight = imgHeight;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  图片宽度
	 */
	@Column(name ="IMG_WIDTH",nullable=true,precision=10,scale=0)
	public java.lang.Integer getImgWidth(){
		return this.imgWidth;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  图片宽度
	 */
	public void setImgWidth(java.lang.Integer imgWidth){
		this.imgWidth = imgWidth;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片地址
	 */
	@Column(name ="IMG_URL",nullable=true,length=60)
	public java.lang.String getImgUrl(){
		return this.imgUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片地址
	 */
	public void setImgUrl(java.lang.String imgUrl){
		this.imgUrl = imgUrl;
	}
	
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否显示启动图
	 */
	@Column(name ="DISPLAY",nullable=true,precision=10,scale=0)
	public java.lang.Integer getDisplay() {
		return display;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否显示启动图
	 */
	public void setDisplay(java.lang.Integer display) {
		this.display = display;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否跳转
	 */
	@Column(name ="JUMP",nullable=true,precision=10,scale=0)
	public java.lang.Integer getJump() {
		return jump;
	}
	
	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否跳转
	 */
	public void setJump(java.lang.Integer jump) {
		this.jump = jump;
	}
	
	/**
	 *方法: 取得java.lang.double
	 *@return: java.lang.double  显示时间
	 */
	@Column(name ="SHOWTIME",nullable=true,precision=10)
	public java.lang.Double getShowtime() {
		return showtime;
	}
	/**
	 *方法: 设置java.lang.double
	 *@param: java.lang.double  显示时间
	 */
	public void setShowtime(java.lang.Double showtime) {
		this.showtime = showtime;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  链接站点
	 */
	@Column(name ="SITEID",nullable=true,length=60)
	public java.lang.String getSiteId() {
		return siteId;
	}
	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  链接站点
	 */
	public void setSiteId(java.lang.String siteId) {
		this.siteId = siteId;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="SORT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSort() {
		return sort;
	}
	
	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}
}
