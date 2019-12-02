package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 收藏
 * @author zhangdaihao
 * @date 2014-05-09 15:13:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_collect", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CollectFrontEntity extends IdEntity implements java.io.Serializable {

	/**会员级别*/
	private java.lang.String memberlevel;
	/**主题*/
	private java.lang.String title;
	/**地址*/
	private java.lang.String url;
	/**内容*/
	private java.lang.String content;
	/**收藏人*/
	private java.lang.String collectperson;
	/**收藏人ID*/
	private java.lang.String collectpersonid;
	/**评论时间*/
	private java.util.Date collecttime;
	/**文章id*/
	private java.lang.String contentid;
	/**创建时间*/
	private java.util.Date createdtime;

	

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  会员级别
	 */
	@Column(name ="MEMBERLEVEL",nullable=true,length=255)
	public java.lang.String getMemberlevel(){
		return this.memberlevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  会员级别
	 */
	public void setMemberlevel(java.lang.String memberlevel){
		this.memberlevel = memberlevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主题
	 */
	@Column(name ="TITLE",nullable=true,length=255)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地址
	 */
	@Column(name ="URL",nullable=true,length=255)
	public java.lang.String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地址
	 */
	public void setUrl(java.lang.String url){
		this.url = url;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容
	 */
	@Column(name ="CONTENT",nullable=true,length=255)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收藏人
	 */
	@Column(name ="COLLECTPERSON",nullable=true,length=255)
	public java.lang.String getCollectperson(){
		return this.collectperson;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收藏人
	 */
	public void setCollectperson(java.lang.String collectperson){
		this.collectperson = collectperson;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收藏人ID
	 */
	@Column(name ="COLLECTPERSONID",nullable=true,length=32)
	public java.lang.String getCollectpersonid(){
		return this.collectpersonid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收藏人ID
	 */
	public void setCollectpersonid(java.lang.String collectpersonid){
		this.collectpersonid = collectpersonid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  评论时间
	 */
	@Column(name ="COLLECTTIME",nullable=true)
	public java.util.Date getCollecttime(){
		return this.collecttime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  评论时间
	 */
	public void setCollecttime(java.util.Date collecttime){
		this.collecttime = collecttime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文章id
	 */
	@Column(name ="CONTENTID",nullable=true,length=32)
	public java.lang.String getContentid(){
		return this.contentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文章id
	 */
	public void setContentid(java.lang.String contentid){
		this.contentid = contentid;
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
