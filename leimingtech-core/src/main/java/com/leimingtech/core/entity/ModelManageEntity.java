package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 模型管理
 * @author zhangdaihao
 * @date 2014-04-15 14:47:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_modelmanage", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ModelManageEntity extends IdEntity implements java.io.Serializable {
	
	/**模型名称*/
	private java.lang.String modelName;
	/**排列顺序*/
	private java.lang.Integer priority;
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
	 *@return: java.lang.String  模型名称
	 */
	@Column(name ="MODEL_NAME",nullable=true,length=255)
	public java.lang.String getModelName(){
		return this.modelName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模型名称
	 */
	public void setModelName(java.lang.String modelName){
		this.modelName = modelName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排列顺序
	 */
	@Column(name ="PRIORITY",nullable=true)
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
}
