package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 图片
 * @author 
 * @date 2014-05-13 13:31:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_picturealone", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class PictureAloneMobileEntity extends IdEntity implements java.io.Serializable {
	
	/**组图ID*/
	private java.lang.String picturegroupid;
	/**图片地址*/
	private java.lang.String pictureUrl;
	/**图片信息*/
	private java.lang.String pictureMessage;
	/**宽度*/
	private java.lang.String pictureWidth;
	/**长度*/
	private java.lang.String pictureHeight;
	/**排序*/
	private java.lang.String pictureSort;
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
	 *@return: java.lang.String  组图ID
	 */
	@Column(name ="PICTUREGROUPID",nullable=true,length=32)
	public java.lang.String getPicturegroupid(){
		return this.picturegroupid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组图ID
	 */
	public void setPicturegroupid(java.lang.String picturegroupid){
		this.picturegroupid = picturegroupid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片地址
	 */
	@Column(name ="PICTURE_URL",nullable=true,length=255)
	public java.lang.String getPictureUrl(){
		return this.pictureUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片地址
	 */
	public void setPictureUrl(java.lang.String pictureUrl){
		this.pictureUrl = pictureUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片信息
	 */
	@Column(name ="PICTURE_MESSAGE",nullable=true,length=255)
	public java.lang.String getPictureMessage(){
		return this.pictureMessage;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片信息
	 */
	public void setPictureMessage(java.lang.String pictureMessage){
		this.pictureMessage = pictureMessage;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  宽度
	 */
	@Column(name ="PICTURE_WIDTH",nullable=true,length=255)
	public java.lang.String getPictureWidth(){
		return this.pictureWidth;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  宽度
	 */
	public void setPictureWidth(java.lang.String pictureWidth){
		this.pictureWidth = pictureWidth;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  长度
	 */
	@Column(name ="PICTURE_HEIGHT",nullable=true,length=255)
	public java.lang.String getPictureHeight(){
		return this.pictureHeight;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  长度
	 */
	public void setPictureHeight(java.lang.String pictureHeight){
		this.pictureHeight = pictureHeight;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排序
	 */
	@Column(name ="PICTURE_SORT",nullable=true,length=255)
	public java.lang.String getPictureSort(){
		return this.pictureSort;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排序
	 */
	public void setPictureSort(java.lang.String pictureSort){
		this.pictureSort = pictureSort;
	}
}
