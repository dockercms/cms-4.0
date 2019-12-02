package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;


/**   
 * @Title: Entity
 * @Description: 图片库
 * @author linjm
 * @date 2014-04-24 14:46:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_attach_picture", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AttachPictureEntity extends IdEntity  implements java.io.Serializable {
 
	/**图片原名*/
	private java.lang.String realname;
	/**上传后的名字*/
	private java.lang.String localname;
	/**缩略图路径*/
	private java.lang.String thumbnailpath;
	/**原图路径*/
	private java.lang.String localpath;
	/**创建时间*/
	private java.util.Date createdate;
	/**站点id*/
	private java.lang.String siteid;
	 
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片原名
	 */
	@Column(name ="REALNAME",nullable=true,length=225)
	public java.lang.String getRealname(){
		return this.realname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片原名
	 */
	public void setRealname(java.lang.String realname){
		this.realname = realname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上传后的名字
	 */
	@Column(name ="LOCALNAME",nullable=true,length=225)
	public java.lang.String getLocalname(){
		return this.localname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上传后的名字
	 */
	public void setLocalname(java.lang.String localname){
		this.localname = localname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  缩略图路径
	 */
	@Column(name ="THUMBNAILPATH",nullable=true,length=225)
	public java.lang.String getThumbnailpath(){
		return this.thumbnailpath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  缩略图路径
	 */
	public void setThumbnailpath(java.lang.String thumbnailpath){
		this.thumbnailpath = thumbnailpath;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  原图路径
	 */
	@Column(name ="LOCALPATH",nullable=true,length=255)
	public java.lang.String getLocalpath(){
		return this.localpath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  原图路径
	 */
	public void setLocalpath(java.lang.String localpath){
		this.localpath = localpath;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  创建时间
	 */
	@Column(name ="CREATEDATE",nullable=true,precision=19,scale=0)
	public java.util.Date getCreatedate(){
		return this.createdate;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  创建时间
	 */
	public void setCreatedate(java.util.Date createdate){
		this.createdate = createdate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  站点id
	 */
	@Column(name ="SITE_ID",nullable=true,length=32)
	public java.lang.String getSiteid() {
		return siteid;
	}
	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  站点id
	 */
	public void setSiteid(java.lang.String siteid) {
		this.siteid = siteid;
	}
}
