package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 移动栏目权限表
 * @author 
 * @date 2015-01-06 16:37:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_mobile_channel_priv", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class MobileChannelPrivEntity extends IdEntity implements java.io.Serializable {
	 
	/**移动栏目ID*/
	private MobileChannelEntity mobileChannel;
	/**角色ID*/
	private TSRole TSRole;
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
	 * 方法：取得栏目
	 * @return MobileChannelEntity  栏目
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHANNELID")
	public MobileChannelEntity getMobileChannelEntity(){
		return this.mobileChannel;
	}
	/**
	 * 方法: 设置栏目
	 * @param contentCat  栏目
	 */
	public void setMobileChannelEntity(MobileChannelEntity mobileChannel){
		this.mobileChannel = mobileChannel;
	}
	/**
	 *方法: 取得角色
	 *@return: TSRole   角色
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLEID")
	public TSRole getTSRole() {
		return this.TSRole;
	}

	/**
	 *方法: 设置角色
	 *@param: TSRole   角色
	 */
	public void setTSRole(TSRole TSRole) {
		this.TSRole = TSRole;
	}
}
