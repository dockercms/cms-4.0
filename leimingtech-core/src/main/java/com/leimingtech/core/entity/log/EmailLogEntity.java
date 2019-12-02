package com.leimingtech.core.entity.log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 邮件日志
 * @author 
 * @date 2014-05-13 17:30:18
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_email_log", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class EmailLogEntity extends IdEntity implements java.io.Serializable {
	 
	/**标题*/
	private java.lang.String title;
	/**发邮件箱*/
	private java.lang.String frommail;
	/**收邮箱*/
	private java.lang.String tomail;
	/**内容*/
	private java.lang.String content;
	/**时间*/
	private java.util.Date sendtime;
	/**操作人*/
	private java.lang.String op;
	/**状态*/
	private java.lang.Integer status;
	/**是否重发*/
	private java.lang.Integer isresend;
	/**备注*/
	private java.lang.String remark;
	

	/**创建时间*/
	private java.util.Date createdtime;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=true,length=255)
	public java.lang.String getTitle() {
		return title;
	}
	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发邮件箱
	 */
	@Column(name ="FROMMAIL",nullable=true,length=255)
	public java.lang.String getFrommail(){
		return this.frommail;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发邮件箱
	 */
	public void setFrommail(java.lang.String frommail){
		this.frommail = frommail;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收邮箱
	 */
	@Column(name ="TOMAIL",nullable=true,length=255)
	public java.lang.String getTomail(){
		return this.tomail;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收邮箱
	 */
	public void setTomail(java.lang.String tomail){
		this.tomail = tomail;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容
	 */
	@Column(name ="CONTENT",nullable=true,length=1000)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  时间
	 */
	@Column(name ="SENDTIME",nullable=true)
	public java.util.Date getSendtime(){
		return this.sendtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  时间
	 */
	public void setSendtime(java.util.Date sendtime){
		this.sendtime = sendtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作人
	 */
	@Column(name ="OP",nullable=true,length=100)
	public java.lang.String getOp(){
		return this.op;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作人
	 */
	public void setOp(java.lang.String op){
		this.op = op;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否重发
	 */
	@Column(name ="ISRESEND",nullable=true,precision=10,scale=0)
	public java.lang.Integer getIsresend(){
		return this.isresend;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否重发
	 */
	public void setIsresend(java.lang.Integer isresend){
		this.isresend = isresend;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=500)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
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
