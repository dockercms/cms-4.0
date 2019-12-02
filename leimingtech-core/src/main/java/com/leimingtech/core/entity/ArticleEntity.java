package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 文章
 * @author zhangdaihao
 * @date 2014-04-22 10:29:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_content_article", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ArticleEntity extends IdEntity implements java.io.Serializable {
	
	/**内容ID*/
	private java.lang.String contentid;
	/**内容*/
	private java.lang.String content;
	/**页数*/
	private java.lang.Integer pagecount;
	/**保存远程图片*/
	private java.lang.Integer saveremoteimage;
	/**字数*/
	private java.lang.Integer wordsCount;
	/**站点id*/
	private java.lang.String siteid;
	/**会员id*/
	private java.lang.String memberid;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  内容ID
	 */
	@Column(name ="CONTENTID",nullable=true,length=32)
	public java.lang.String getContentid(){
		return this.contentid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  内容ID
	 */
	public void setContentid(java.lang.String contentid){
		this.contentid = contentid;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容
	 */
	@Column(name ="CONTENT",nullable=true)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  页数
	 */
	@Column(name ="PAGECOUNT",nullable=false,precision=3,scale=0)
	public java.lang.Integer getPagecount(){
		return this.pagecount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  页数
	 */
	public void setPagecount(java.lang.Integer pagecount){
		this.pagecount = pagecount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  保存远程图片
	 */
	@Column(name ="SAVEREMOTEIMAGE",nullable=false,precision=3,scale=0)
	public java.lang.Integer getSaveremoteimage(){
		return this.saveremoteimage;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  保存远程图片
	 */
	public void setSaveremoteimage(java.lang.Integer saveremoteimage){
		this.saveremoteimage = saveremoteimage;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  字数
	 */
	@Column(name ="WORDS_COUNT",nullable=false,precision=5,scale=0)
	public java.lang.Integer getWordsCount(){
		return this.wordsCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  字数
	 */
	public void setWordsCount(java.lang.Integer wordsCount){
		this.wordsCount = wordsCount;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  会员id
	 */
	@Column(name ="MEMBERID",nullable=true,length=32)
	public java.lang.String getMemberid() {
		return memberid;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  会员id
	 */
	public void setMemberid(java.lang.String memberid) {
		this.memberid = memberid;
	}
}
