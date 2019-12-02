package com.leimingtech.core.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**   
 * @Title: Entity
 * @Description: 步骤
 * @author zhangdaihao
 * @date 2014-04-22 15:26:51
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_workflow_step", schema = "")
@SuppressWarnings("serial")
public class WorkFlowStepEntity extends IdEntity implements java.io.Serializable {
	
	/**步骤*/
	private java.lang.Integer step;
	/**权限*/
	private java.lang.String roleid;
	/**外键*/
	private java.lang.String gid;
	/**站点id*/
	private java.lang.String siteid;
	
	/**创建时间*/
	private java.util.Date createdtime;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  步骤
	 */
	@Column(name ="STEP",nullable=true,precision=10,scale=0)
	public java.lang.Integer getStep(){
		return this.step;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  步骤
	 */
	public void setStep(java.lang.Integer step){
		this.step = step;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  权限
	 */
	@Column(name ="ROLEID",nullable=true,precision=32,scale=0)
	public String getRoleid(){
		return this.roleid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  权限
	 */
	public void setRoleid(java.lang.String roleid){
		this.roleid = roleid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  外键
	 */
	@Column(name ="GID",nullable=true,length=32)
	public java.lang.String getGid(){
		return this.gid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  外键
	 */
	public void setGid(java.lang.String gid){
		this.gid = gid;
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
