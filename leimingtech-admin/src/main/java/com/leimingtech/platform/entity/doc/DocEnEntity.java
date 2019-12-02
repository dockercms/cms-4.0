package com.leimingtech.platform.entity.doc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 文档引用实体表
 * @author 
 * @date 2014-06-25 19:34:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_doc_entity", schema = "")
@SuppressWarnings("serial")
public class DocEnEntity extends IdEntity implements java.io.Serializable {
	 
	/**实体名*/
	private java.lang.String name;
	/**实体代码*/
	private java.lang.String code;
	/**排序*/
	private java.lang.Integer sort;
	/**描述*/
	private java.lang.String description;
	/**状态*/
	private java.lang.Integer status;
	/**添加时间*/
	private java.util.Date createdtime;
	/**添加人*/
	private java.lang.String createdby;
	/**修改次数*/
	private java.lang.Integer updatecount;
	/**修改时间*/
	private java.util.Date updatetime;
	/**修改人*/
	private java.lang.String updateby;
	
	 
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  实体名
	 */
	@Column(name ="NAME",nullable=true,length=255)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  实体名
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  实体代码
	 */
	@Column(name ="CODE",nullable=true,length=255)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  实体代码
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="SORT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSort(){
		return this.sort;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setSort(java.lang.Integer sort){
		this.sort = sort;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述
	 */
	@Column(name ="DESCRIPTION",nullable=true,length=255)
	public java.lang.String getDescription(){
		return this.description;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setDescription(java.lang.String description){
		this.description = description;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  状态
	 */
	@Column(name ="STATUS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  状态
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  添加时间
	 */
	@Column(name ="CREATEDTIME",nullable=true)
	public java.util.Date getCreatedtime(){
		return this.createdtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  添加时间
	 */
	public void setCreatedtime(java.util.Date createdtime){
		this.createdtime = createdtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  添加人
	 */
	@Column(name ="CREATEDBY",nullable=true,length=255)
	public java.lang.String getCreatedby(){
		return this.createdby;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  添加人
	 */
	public void setCreatedby(java.lang.String createdby){
		this.createdby = createdby;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  修改次数
	 */
	@Column(name ="UPDATECOUNT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getUpdatecount(){
		return this.updatecount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  修改次数
	 */
	public void setUpdatecount(java.lang.Integer updatecount){
		this.updatecount = updatecount;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="UPDATETIME",nullable=true)
	public java.util.Date getUpdatetime(){
		return this.updatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdatetime(java.util.Date updatetime){
		this.updatetime = updatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */
	@Column(name ="UPDATEBY",nullable=true,length=255)
	public java.lang.String getUpdateby(){
		return this.updateby;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setUpdateby(java.lang.String updateby){
		this.updateby = updateby;
	}
}
