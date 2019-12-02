package com.leimingtech.platform.entity.doc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;


/**   
 * @Title: Entity
 * @Description: 标签及api输入参数
 * @author 
 * @date 2014-06-13 17:04:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_doc_param", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")

public class DocParamEntity extends IdEntity  implements java.io.Serializable {
	 

	/**文档id*/
	private java.lang.String docid;
	/**名称*/
	private java.lang.String name;
	/**类型*/
	private java.lang.Integer type;
	/**是否必须*/
	private java.lang.String isrequired;
	/**示例值*/
	private java.lang.String exampleValue;
	/**默认值*/
	private java.lang.String defalutValue;
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
	 *@return: java.lang.String  文档id
	 */
	@Column(name ="DOCID",nullable=true,length=32)
	public java.lang.String getDocid(){

		return this.docid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文档id
	 */
	public void setDocid(java.lang.String docid){
		this.docid = docid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=true,length=255)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  类型
	 */
	@Column(name ="TYPE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  类型
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否必须
	 */
	@Column(name ="ISREQUIRED",nullable=true,length=255)
	public java.lang.String getIsrequired(){
		return this.isrequired;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否必须
	 */
	public void setIsrequired(java.lang.String isrequired){
		this.isrequired = isrequired;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  示例值
	 */
	@Column(name ="EXAMPLE_VALUE",nullable=true,length=255)
	public java.lang.String getExampleValue(){
		return this.exampleValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  示例值
	 */
	public void setExampleValue(java.lang.String exampleValue){
		this.exampleValue = exampleValue;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  默认值
	 */
	@Column(name ="DEFALUT_VALUE",nullable=true,length=255)
	public java.lang.String getDefalutValue(){
		return this.defalutValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  默认值
	 */
	public void setDefalutValue(java.lang.String defalutValue){
		this.defalutValue = defalutValue;
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
