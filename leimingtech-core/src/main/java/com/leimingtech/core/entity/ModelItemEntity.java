package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 拓展字段
 * @author zhangdaihao
 * @date 2014-04-15 14:48:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_model_item", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ModelItemEntity extends IdEntity implements java.io.Serializable {
	
	/**模型id*/
	private java.lang.String modelId;
	/**字段名称*/
	private java.lang.String field;
	/**数据类型（1文本框、2多行文本框、3上传）*/
	private java.lang.Integer dataType;
	/**名称*/
	private java.lang.String itemLabel;
	/**排列顺序*/
	private java.lang.Integer priority;

	/**站点id*/
	private java.lang.String siteid;
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
	 *@return: java.lang.String  模型id
	 */
	@Column(name ="MODEL_ID",nullable=true,length=32)
	public java.lang.String getModelId(){
		return this.modelId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模型id
	 */
	public void setModelId(java.lang.String modelId){
		this.modelId = modelId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  字段名称
	 */
	@Column(name ="FIELD",nullable=true,length=255)
	public java.lang.String getField(){
		return this.field;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  字段名称
	 */
	public void setField(java.lang.String field){
		this.field = field;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  数据类型
	 */
	@Column(name ="DATA_TYPE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getDataType(){
		return this.dataType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  数据类型
	 */
	public void setDataType(java.lang.Integer dataType){
		this.dataType = dataType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="ITEM_LABEL",nullable=true,length=255)
	public java.lang.String getItemLabel(){
		return this.itemLabel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setItemLabel(java.lang.String itemLabel){
		this.itemLabel = itemLabel;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排列顺序
	 */
	@Column(name ="PRIORITY",nullable=true,length=255)
	public java.lang.Integer getPriority(){
		return this.priority;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排列顺序
	 */
	public void setPriority(java.lang.Integer priority){
		this.priority = priority;
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
