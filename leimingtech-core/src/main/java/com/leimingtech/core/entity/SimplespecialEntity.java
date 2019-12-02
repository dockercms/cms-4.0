package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 简单专题
 * @author 
 * @date 2015-01-19 11:09:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_simplespecial", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SimplespecialEntity extends IdEntity implements java.io.Serializable {
	/**is_top*/
	private java.lang.Integer isTop;
	/** 排序 */
	private java.lang.Integer sort;
	/**专题名称*/
	private java.lang.String specialName;
	/**专题内容*/
	private java.lang.String specialContent;
	/**列表缩略图*/
	private java.lang.String specialThub;
	/**创建时间*/
	private java.util.Date specialCreate;
	/**创建人*/
	private java.lang.String specialCreated;
	/**专题标记*/
	private java.lang.String specialMark;
	/**专题说明*/
	private java.lang.String specialState;
	/**专题分类*/
	private java.lang.String specialType;
	/**专题头图*/
	private java.lang.String specialSlide;
	
	/**
	 *方法: 取得java.lang.Int
	 *@return: java.lang.Int  置顶
	 */
	@Column(name ="IS_TOP",nullable=true,length=11)
	public java.lang.Integer getIsTop(){
		return this.isTop;
	}

	/**
	 *方法: 设置java.lang.Int
	 *@param: java.lang.Int  置顶
	 */
	public void setIsTop(java.lang.Integer isTop){
		this.isTop = isTop;
	}
	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 排序
	 */

	@Column(name = "SORT", nullable = true, precision = 3, scale = 0)
	public java.lang.Integer getSort() {
		return this.sort;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 排序
	 */
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专题名称
	 */
	@Column(name ="SPECIAL_NAME",nullable=true,length=255)
	public java.lang.String getSpecialName(){
		return this.specialName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专题名称
	 */
	public void setSpecialName(java.lang.String specialName){
		this.specialName = specialName;
	}
	/**
	 *方法: 取得java.lang.Object
	 *@return: java.lang.Object  专题内容
	 */
	@Column(name ="SPECIAL_CONTENT",nullable=true,length=65535)
	public java.lang.String getSpecialContent(){
		return this.specialContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.Object  专题内容
	 */
	public void setSpecialContent(java.lang.String specialContent){
		this.specialContent = specialContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  缩略图
	 */
	@Column(name ="SPECIAL_THUB",nullable=true,length=255)
	public java.lang.String getSpecialThub(){
		return this.specialThub;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  缩略图
	 */
	public void setSpecialThub(java.lang.String specialThub){
		this.specialThub = specialThub;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="SPECIAL_CREATE",nullable=true)
	public java.util.Date getSpecialCreate(){
		return this.specialCreate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setSpecialCreate(java.util.Date specialCreate){
		this.specialCreate = specialCreate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="SPECIAL_CREATED",nullable=true,length=255)
	public java.lang.String getSpecialCreated(){
		return this.specialCreated;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setSpecialCreated(java.lang.String specialCreated){
		this.specialCreated = specialCreated;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专题标记
	 */
	@Column(name ="SPECIAL_MARK",nullable=true,length=255)
	public java.lang.String getSpecialMark(){
		return this.specialMark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专题标记
	 */
	public void setSpecialMark(java.lang.String specialMark){
		this.specialMark = specialMark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专题说明
	 */
	@Column(name ="SPECIAL_STATE",nullable=true,length=255)
	public java.lang.String getSpecialState(){
		return this.specialState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专题说明
	 */
	public void setSpecialState(java.lang.String specialState){
		this.specialState = specialState;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专题分类
	 */
	@Column(name ="SPECIAL_TYPE",nullable=true,length=255)
	public java.lang.String getSpecialType(){
		return this.specialType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专题分类
	 */
	public void setSpecialType(java.lang.String specialType){
		this.specialType = specialType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  头图
	 */
	@Column(name ="SPECIAL_SLIDE",nullable=true,length=255)
	public java.lang.String getSpecialSlide() {
		return specialSlide;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  头图
	 */
	public void setSpecialSlide(java.lang.String specialSlide) {
		this.specialSlide = specialSlide;
	}
	
}
