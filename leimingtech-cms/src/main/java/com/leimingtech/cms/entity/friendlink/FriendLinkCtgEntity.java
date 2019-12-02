package com.leimingtech.cms.entity.friendlink;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 友情链接类别
 * @author zhangdaihao
 * @date 2014-04-18 13:38:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_friendlink_ctg", schema = "")
@SuppressWarnings("serial")
public class FriendLinkCtgEntity extends IdEntity implements java.io.Serializable {

	/**点站id*/
	private java.lang.String siteId;
	/**名称*/
	private java.lang.String friendlinkctgName;
	/**排列顺序*/
	private java.lang.Integer priority;

	/**创建时间*/
	private java.util.Date createdtime;
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  点站id
	 */
	@Column(name ="SITE_ID",nullable=false,length=32)
	public java.lang.String getSiteId(){
		return this.siteId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  点站id
	 */
	public void setSiteId(java.lang.String siteId){
		this.siteId = siteId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="FRIENDLINKCTG_NAME",nullable=false,length=50)
	public java.lang.String getFriendlinkctgName(){
		return this.friendlinkctgName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setFriendlinkctgName(java.lang.String friendlinkctgName){
		this.friendlinkctgName = friendlinkctgName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排列顺序
	 */
	@Column(name ="PRIORITY",nullable=false,precision=10,scale=0)
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
