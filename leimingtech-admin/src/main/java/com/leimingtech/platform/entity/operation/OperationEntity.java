package com.leimingtech.platform.entity.operation;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;


/**   
 * @Title: Entity
 * @Description: 功能按钮管理
 * @author zhangdaihao
 * @date 2014-04-16 13:41:11
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_operation", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class OperationEntity extends IdEntity  implements java.io.Serializable {
	 
	/**operationcode*/
	private java.lang.String operationcode;
	/**operationicon*/
	private java.lang.String operationicon;
	/**operationname*/
	private java.lang.String operationname;
	/**status*/
	private java.lang.Integer status;
	/**functionid*/
	private java.lang.String functionid;
	/**iconid*/
	private java.lang.String iconid;
	/**operationurl*/
	private java.lang.String operationurl;

	/**创建时间*/
	private java.util.Date createdtime;
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	 
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  operationcode
	 */
	@Column(name ="OPERATIONCODE",nullable=true,length=50)
	public java.lang.String getOperationcode(){
		return this.operationcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  operationcode
	 */
	public void setOperationcode(java.lang.String operationcode){
		this.operationcode = operationcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  operationicon
	 */
	@Column(name ="OPERATIONICON",nullable=true,length=100)
	public java.lang.String getOperationicon(){
		return this.operationicon;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  operationicon
	 */
	public void setOperationicon(java.lang.String operationicon){
		this.operationicon = operationicon;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  operationname
	 */
	@Column(name ="OPERATIONNAME",nullable=true,length=50)
	public java.lang.String getOperationname(){
		return this.operationname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  operationname
	 */
	public void setOperationname(java.lang.String operationname){
		this.operationname = operationname;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  status
	 */
	@Column(name ="STATUS",nullable=true,precision=5,scale=0)
	public java.lang.Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  status
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  functionid
	 */
	@Column(name ="FUNCTIONID",nullable=true,length=100)
	public java.lang.String getFunctionid(){
		return this.functionid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  functionid
	 */
	public void setFunctionid(java.lang.String functionid){
		this.functionid = functionid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  iconid
	 */
	@Column(name ="ICONID",nullable=true,length=100)
	public java.lang.String getIconid(){
		return this.iconid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  iconid
	 */
	public void setIconid(java.lang.String iconid){
		this.iconid = iconid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  operationurl
	 */
	@Column(name ="OPERATIONURL",nullable=true,length=300)
	public java.lang.String getOperationurl() {
		return operationurl;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  operationurl
	 */
	public void setOperationurl(java.lang.String operationurl) {
		this.operationurl = operationurl;
	}
	/**
	 *方法: 取得java.lang.Date
	 *@return: java.lang.Date  创建时间
	 */
	@Column(name = "CREATEDTIME", nullable = true)
	public java.util.Date getCreatedtime() {
		return createdtime;
	}
	/**
	 *方法: 设置java.lang.Date
	 *@return: java.lang.Date  创建时间
	 */
	public void setCreatedtime(java.util.Date createdtime) {
		this.createdtime = createdtime;
	}
}
