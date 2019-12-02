package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 区块所关联数据
 * @author zhangdaihao
 * @date 2014-04-22 11:46:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_section_data", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SectionDataEntity extends IdEntity implements java.io.Serializable {
	
	/**区块id*/
	private java.lang.String sectionid;
	/**关联内容id*/
	private java.lang.String contentid;
	/**标题*/
	private java.lang.String title;
	/**颜色*/
	private java.lang.String color;
	/**内容地址*/
	private java.lang.String url;
	/**缩略图*/
	private java.lang.String thumb;
	/**描述*/
	private java.lang.String description;
	/**排序*/
	private java.lang.Integer sort;
	/**创建时间*/
	private java.util.Date created;
	/**创建人*/
	private java.lang.String createdby;
	/**修改时间*/
	private java.util.Date commended;
	/**修改人*/
	private java.lang.Integer commendedby;
	
	
	private TSUser createUser;//区块内容推荐人
	private TSUser updateUser;//区块内容修改人
	
	@Transient
	public TSUser getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(TSUser updateUser) {
		this.updateUser = updateUser;
	}

	@Transient
	public TSUser getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TSUser createUser) {
		this.createUser = createUser;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  区块id
	 */
	@Column(name ="SECTIONID",nullable=false,length=32)
	public java.lang.String getSectionid(){
		return this.sectionid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  区块id
	 */
	public void setSectionid(java.lang.String sectionid){
		this.sectionid = sectionid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关联内容id
	 */
	@Column(name ="CONTENTID",nullable=true,length=32)
	public java.lang.String getContentid(){
		return this.contentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联内容id
	 */
	public void setContentid(java.lang.String contentid){
		this.contentid = contentid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=false,length=100)
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
	 *@return: java.lang.String  颜色
	 */
	@Column(name ="COLOR",nullable=true,length=7)
	public java.lang.String getColor(){
		return this.color;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  颜色
	 */
	public void setColor(java.lang.String color){
		this.color = color;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容地址
	 */
	@Column(name ="URL",nullable=true,length=100)
	public java.lang.String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容地址
	 */
	public void setUrl(java.lang.String url){
		this.url = url;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  缩略图
	 */
	@Column(name ="THUMB",nullable=true,length=100)
	public java.lang.String getThumb(){
		return this.thumb;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  缩略图
	 */
	public void setThumb(java.lang.String thumb){
		this.thumb = thumb;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述
	 */
	@Column(name ="DESCRIPTION",nullable=true,length=65535)
	public java.lang.String getDescription(){
		return this.description;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setDescription(java.lang.String description){
		this.description = description;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="SORT",nullable=false,precision=10,scale=0)
	public java.lang.Integer getSort(){
		return this.sort;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setSort(java.lang.Integer sort){
		this.sort = sort;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATED",nullable=false)
	public java.util.Date getCreated(){
		return this.created;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreated(java.util.Date created){
		this.created = created;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="CREATEDBY",nullable=false,length=32)
	public java.lang.String getCreatedby(){
		return this.createdby;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  创建人
	 */
	public void setCreatedby(java.lang.String createdby){
		this.createdby = createdby;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="COMMENDED",nullable=true)
	public java.util.Date getCommended(){
		return this.commended;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setCommended(java.util.Date commended){
		this.commended = commended;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  修改人
	 */
	@Column(name ="COMMENDEDBY",nullable=true,precision=7,scale=0)
	public java.lang.Integer getCommendedby(){
		return this.commendedby;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  修改人
	 */
	public void setCommendedby(java.lang.Integer commendedby){
		this.commendedby = commendedby;
	}
}
