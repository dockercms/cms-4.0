package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 简单专题内容关联表
 * @author 
 * @date 2015-01-19 11:13:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_simplespecial_content", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SimplespecialContentEntity extends IdEntity implements java.io.Serializable {
	
	/**简单专题id*/
	private java.lang.String simplespecialid;
	/**内容id*/
	private java.lang.String contentid;
	
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
	 *@return: java.lang.String  简单专题id
	 */
	@Column(name ="SIMPLESPECIALID",nullable=true,length=32)
	public java.lang.String getSimplespecialid(){
		return this.simplespecialid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  简单专题id
	 */
	public void setSimplespecialid(java.lang.String simplespecialid){
		this.simplespecialid = simplespecialid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容id
	 */
	@Column(name ="CONTENTID",nullable=true,length=32)
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
}
