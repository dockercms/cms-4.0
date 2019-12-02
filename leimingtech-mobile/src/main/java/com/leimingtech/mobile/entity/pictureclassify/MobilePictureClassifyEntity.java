package com.leimingtech.mobile.entity.pictureclassify;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 移动图片分类表
 * @author 
 * @date 2014-07-14 10:28:05
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_picture_classify", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class MobilePictureClassifyEntity extends IdEntity implements java.io.Serializable {
	 
	/**分类名称*/
	private java.lang.String name;
	/**排序*/
	private java.lang.String sort;
	/**标记*/
	private java.lang.String marker;
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
	 *@return: java.lang.String  分类名称
	 */
	@Column(name ="NAME",nullable=true,length=255)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排序
	 */
	@Column(name ="SORT",nullable=true,length=255)
	public java.lang.String getSort(){
		return this.sort;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排序
	 */
	public void setSort(java.lang.String sort){
		this.sort = sort;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标记
	 */
	@Column(name ="MARKER",nullable=true,length=255)
	public java.lang.String getMarker(){
		return this.marker;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标记
	 */
	public void setMarker(java.lang.String marker){
		this.marker = marker;
	}
}
