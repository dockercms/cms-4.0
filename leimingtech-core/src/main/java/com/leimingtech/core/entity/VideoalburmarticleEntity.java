package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 视频专辑关联文章
 * @author 
 * @date 2014-07-10 17:25:41
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_video_alburm_article", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class VideoalburmarticleEntity extends IdEntity implements java.io.Serializable {
	
	/**视频专辑id*/
	private java.lang.String alburmid;
	/**视频文章id*/
	private java.lang.String articleid;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  视频专辑id
	 */
	@Column(name ="ALBURMID",nullable=true,length=32)
	public java.lang.String getAlburmid(){
		return this.alburmid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  视频专辑id
	 */
	public void setAlburmid(java.lang.String alburmid){
		this.alburmid = alburmid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  视频文章id
	 */
	@Column(name ="ARTICLEID",nullable=true,length=32)
	public java.lang.String getArticleid(){
		return this.articleid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  视频文章id
	 */
	public void setArticleid(java.lang.String articleid){
		this.articleid = articleid;
	}
}
