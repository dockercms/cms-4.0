package com.leimingtech.cms.page.friendlink;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.leimingtech.cms.entity.friendlink.FriendLinkEntity;



/**   
 * @Title: Entity
 * @Description: 友情链接类别
 * @author zhangdaihao
 * @date 2014-04-18 13:38:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_friendlink_ctg", schema = "")
@SuppressWarnings("serial")
public class FriendLinkCtgPage implements java.io.Serializable {
	/**保存-友情链接*/
	private List<FriendLinkEntity> friendLinkList = new ArrayList<FriendLinkEntity>();
	public List<FriendLinkEntity> getFriendLinkList() {
		return friendLinkList;
	}
	public void setFriendLinkList(List<FriendLinkEntity> friendLinkList) {
		this.friendLinkList = friendLinkList;
	}


	/**id*/
	private java.lang.Integer id;
	/**点站id*/
	private java.lang.Integer siteId;
	/**名称*/
	private java.lang.String friendlinkctgName;
	/**排列顺序*/
	private java.lang.Integer priority;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,precision=10,scale=0)
	public java.lang.Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  id
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  点站id
	 */
	@Column(name ="SITE_ID",nullable=false,precision=10,scale=0)
	public java.lang.Integer getSiteId(){
		return this.siteId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  点站id
	 */
	public void setSiteId(java.lang.Integer siteId){
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
}
