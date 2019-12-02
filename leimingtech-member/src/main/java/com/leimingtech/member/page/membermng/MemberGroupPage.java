package com.leimingtech.member.page.membermng;

import com.leimingtech.core.entity.IdEntity;
import com.leimingtech.core.entity.member.MemberEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**   
 * @Title: Entity
 * @Description: 前台用户组
 * @author zhangdaihao
 * @date 2014-05-20 11:42:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_membergroups", schema = "")
@SuppressWarnings("serial")
public class MemberGroupPage extends IdEntity implements java.io.Serializable {
	/**保存-前台用户*/
	private List<MemberEntity> memberList = new ArrayList<MemberEntity>();
	public List<MemberEntity> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<MemberEntity> memberList) {
		this.memberList = memberList;
	}



	/**用户主名称*/
	private java.lang.String usergroupsname;
	/**个数*/
	private java.lang.Integer number;
	/**状态*/
	private java.lang.String state;
	/**备注*/
	private java.lang.String remark;
	
	/**默认等级 0不是默认 1为默认*/
	private java.lang.Integer defalutLv;
	


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
	@Column(name ="NUMBER",nullable=true,precision=10,scale=0)
	public java.lang.Integer getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  个数
	 */
	public void setNumber(java.lang.Integer number){
		this.number = number;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="STATE",nullable=true,length=255)
	public java.lang.String getState(){
		return this.state;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setState(java.lang.String state){
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
}
