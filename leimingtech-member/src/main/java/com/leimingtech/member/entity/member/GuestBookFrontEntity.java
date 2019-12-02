package com.leimingtech.member.entity.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 留言
 * @author zhangdaihao
 * @date 2014-04-30 10:32:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_guestbook", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class GuestBookFrontEntity extends IdEntity implements java.io.Serializable {

	/**标题*/
	private java.lang.String title;
	/**留言内容*/
	private java.lang.String content;
	/**留言人*/
	private java.lang.String messageperson;
	/**留言人id*/
	private java.lang.String personid;
	/**留言时间*/
	private java.util.Date messagetime;
	/**qq*/
	private java.lang.String qq;
	/**手机*/
	private java.lang.String cellphone;
	/**电话*/
	private java.lang.String telephone;
	/**创建时间*/
	private java.util.Date createdtime;
	

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=true,length=255)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  留言内容
	 */
	@Column(name ="CONTENT",nullable=true,length=255)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  留言内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  留言人
	 */
	@Column(name ="MESSAGEPERSON",nullable=true,length=255)
	public java.lang.String getMessageperson(){
		return this.messageperson;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  留言人
	 */
	public void setMessageperson(java.lang.String messageperson){
		this.messageperson = messageperson;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  留言人id
	 */
	@Column(name ="PERSONID",nullable=true,length=32)
	public java.lang.String getPersonid(){
		return this.personid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  留言人id
	 */
	public void setPersonid(java.lang.String personid){
		this.personid = personid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  留言时间
	 */
	@Column(name ="MESSAGETIME",nullable=true)
	public java.util.Date getMessagetime(){
		return this.messagetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  留言时间
	 */
	public void setMessagetime(java.util.Date messagetime){
		this.messagetime = messagetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  qq
	 */
	@Column(name ="QQ",nullable=true,length=255)
	public java.lang.String getQq(){
		return this.qq;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  qq
	 */
	public void setQq(java.lang.String qq){
		this.qq = qq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机
	 */
	@Column(name ="CELLPHONE",nullable=true,length=255)
	public java.lang.String getCellphone(){
		return this.cellphone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机
	 */
	public void setCellphone(java.lang.String cellphone){
		this.cellphone = cellphone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="TELEPHONE",nullable=true,length=255)
	public java.lang.String getTelephone(){
		return this.telephone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setTelephone(java.lang.String telephone){
		this.telephone = telephone;
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
