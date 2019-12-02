package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 选项内容
 * @author zhangdaihao
 * @date 2014-04-16 09:57:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_model_ext", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ModelExtEntity extends IdEntity implements java.io.Serializable {
	
	/**模型id*/
	private java.lang.String modelId;
	/**栏目id*/
	private java.lang.String channelId;
	/**内容id*/
	private java.lang.String contentId;
	/**选项项目id*/
	private java.lang.String modelItemId;
	/**类型名称*/
	private java.lang.String attrName;
	/**类型值*/
	private java.lang.String attrValue;
	/**标记*/
	private java.lang.String attrToken;
	/**数据类型*/
	private java.lang.Integer dataType;
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
	 *@return: java.lang.String  栏目id
	 */
	@Column(name ="CHANNEL_ID",nullable=true,length=32)
	public java.lang.String getChannelId(){
		return this.channelId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  栏目id
	 */
	public void setChannelId(java.lang.String channelId){
		this.channelId = channelId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容id
	 */
	@Column(name ="CONTENT_ID",nullable=true,length=32)
	public java.lang.String getContentId(){
		return this.contentId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容id
	 */
	public void setContentId(java.lang.String contentId){
		this.contentId = contentId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  选项项目id
	 */
	@Column(name ="MODELITEM_ID",nullable=true,length=32)
	public java.lang.String getModelItemId(){
		return this.modelItemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选项项目id
	 */
	public void setModelItemId(java.lang.String modelItemId){
		this.modelItemId = modelItemId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型名称
	 */
	@Column(name ="ATTR_NAME",nullable=true,length=250)
	public java.lang.String getAttrName(){
		return this.attrName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型名称
	 */
	public void setAttrName(java.lang.String attrName){
		this.attrName = attrName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型值
	 */
	@Column(name ="ATTR_VALUE",nullable=true,length=250)
	public java.lang.String getAttrValue(){
		return this.attrValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型值
	 */
	public void setAttrValue(java.lang.String attrValue){
		this.attrValue = attrValue;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标记
	 */
	@Column(name ="ATTR_TOKEN",nullable=true,length=250)
	public java.lang.String getAttrToken(){
		return this.attrToken;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标记
	 */
	public void setAttrToken(java.lang.String attrToken){
		this.attrToken = attrToken;
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
	
}
