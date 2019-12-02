package com.leimingtech.member.entity.membermng;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 前台用户组
 * @author 
 * @date 2014-05-20 11:42:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_membergroups", schema = "")
@SuppressWarnings("serial")
public class MemberGroupEntity extends IdEntity implements java.io.Serializable {

	/**用户主名称*/
	private java.lang.String usergroupsname;
	/**个数*/
	private java.lang.Integer numbers;
	/**状态*/
	private java.lang.Integer state;
	/**备注*/
	private java.lang.String remark;
	
	/**默认等级 0不是默认 1为默认*/
	private java.lang.Integer defalutLv;
	/**创建时间*/
	private java.util.Date createdtime;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户主名称
	 */
	@Column(name ="USERGROUPSNAME",nullable=true,length=255)
	public java.lang.String getUsergroupsname(){
		return this.usergroupsname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户主名称
	 */
	public void setUsergroupsname(java.lang.String usergroupsname){
		this.usergroupsname = usergroupsname;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  个数
	 */
	@Column(name ="NUMBERS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getNumbers(){
		return this.numbers;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  个数
	 */
	public void setNumbers(java.lang.Integer numbers){
		this.numbers = numbers;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  状态
	 */
	@Column(name ="STATE",nullable=true,length=255)
	public java.lang.Integer getState(){
		return this.state;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  状态
	 */
	public void setState(java.lang.Integer state){
		this.state = state;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=255)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  默认等级 0不是默认 1为默认
	 */
	@Column(name ="DEFALUT_LV",nullable=true,precision=10,scale=0)
	public java.lang.Integer getDefalutLv(){
		return this.defalutLv;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  默认等级 0不是默认 1为默认
	 */
	public void setDefalutLv(java.lang.Integer defalutLv){
		this.defalutLv = defalutLv;
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
