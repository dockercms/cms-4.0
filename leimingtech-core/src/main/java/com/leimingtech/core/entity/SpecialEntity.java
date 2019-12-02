package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 专题
 * @author zhangdaihao
 * @date 2014-05-07 12:20:16
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_special", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SpecialEntity extends IdEntity implements java.io.Serializable {
	
	/**内容id*/
	private String contentid;
	/**目录名称*/
	private java.lang.String catalogname;
	/**专题列表页模板*/
	private java.lang.String specialListmodel;
	/**专题列表页信息数*/
	private java.lang.String specialListnews;
	/**专题列表显示页数*/
	private java.lang.String specialListpages;
	/**更新截止日期*/
	private java.util.Date specialEndtime;
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
	 *@return: java.lang.String  内容id
	 */
	@Column(name ="CONTENTID",nullable=true,precision=7,scale=0)
	public java.lang.String getContentid(){
		return this.contentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容id
	 */
	public void setContentid(java.lang.String contentid){
		this.contentid = contentid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  目录名称
	 */
	@Column(name ="CATALOGNAME",nullable=true,length=255)
	public java.lang.String getCatalogname(){
		return this.catalogname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  目录名称
	 */
	public void setCatalogname(java.lang.String catalogname){
		this.catalogname = catalogname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专题列表页模板
	 */
	@Column(name ="SPECIAL_LISTMODEL",nullable=true,length=255)
	public java.lang.String getSpecialListmodel(){
		return this.specialListmodel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专题列表页模板
	 */
	public void setSpecialListmodel(java.lang.String specialListmodel){
		this.specialListmodel = specialListmodel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专题列表页信息数
	 */
	@Column(name ="SPECIAL_LISTNEWS",nullable=true,length=255)
	public java.lang.String getSpecialListnews(){
		return this.specialListnews;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专题列表页信息数
	 */
	public void setSpecialListnews(java.lang.String specialListnews){
		this.specialListnews = specialListnews;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专题列表显示页数
	 */
	@Column(name ="SPECIAL_LISTPAGES",nullable=true,length=255)
	public java.lang.String getSpecialListpages(){
		return this.specialListpages;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专题列表显示页数
	 */
	public void setSpecialListpages(java.lang.String specialListpages){
		this.specialListpages = specialListpages;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新截止日期
	 */
	@Column(name ="SPECIAL_ENDTIME",nullable=true)
	public java.util.Date getSpecialEndtime(){
		return this.specialEndtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新截止日期
	 */
	public void setSpecialEndtime(java.util.Date specialEndtime){
		this.specialEndtime = specialEndtime;
	}
}
