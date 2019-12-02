package com.leimingtech.mobile.entity.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 移动通知表
 * @author 
 * @date 2014-08-04 12:18:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_message", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class MobileMessageEntity extends IdEntity implements java.io.Serializable {
	 
	/**通知时间*/
	private java.util.Date informDate;
	/**通知标题*/
	private java.lang.String title;
	/**通知内容*/
	private java.lang.String informContent;
	/**通知页面*/
	private java.lang.String informDetail;
	/**通知类型*/
	private java.lang.String informType;
	/**运行环境类型*/
	private java.lang.String entironmentType;
	/**运行环境名称*/
	private java.lang.String entironmentName;
	/**推送目标（1苹果,2安卓,3苹果+安卓）*/
	private java.lang.Integer target;
	/**连接的url*/
	private java.lang.String url;
	/**图片的连接 URL*/
	private java.lang.String imgUrl;
	 
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  通知时间
	 */
	@Column(name ="INFORM_DATE",nullable=true)
	public java.util.Date getInformDate(){
		return this.informDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  通知时间
	 */
	public void setInformDate(java.util.Date informDate){
		this.informDate = informDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通知标题
	 */
	@Column(name ="title",nullable=true)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知内容
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通知内容
	 */
	@Column(name ="INFORM_CONTENT",nullable=true)
	public java.lang.String getInformContent(){
		return this.informContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知内容
	 */
	public void setInformContent(java.lang.String informContent){
		this.informContent = informContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通知页面
	 */
	@Column(name ="INFORM_DETAIL",nullable=true,length=255)
	public java.lang.String getInformDetail(){
		return this.informDetail;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知页面
	 */
	public void setInformDetail(java.lang.String informDetail){
		this.informDetail = informDetail;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通知类型
	 */
	@Column(name ="INFORM_TYPE",nullable=true,length=11)
	public java.lang.String getInformType(){
		return this.informType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知类型
	 */
	public void setInformType(java.lang.String informType){
		this.informType = informType;
	}
	@Column(name ="entironment_type",nullable=true,length=30)
	public java.lang.String getEntironmentType() {
		return entironmentType;
	}

	public void setEntironmentType(java.lang.String entironment) {
		this.entironmentType = entironment;
	}
	@Column(name ="entironment_name",nullable=true,length=30)
	public java.lang.String getEntironmentName() {
		return entironmentName;
	}

	public void setEntironmentName(java.lang.String entironmentName) {
		this.entironmentName = entironmentName;
	}
	@Column(name ="target",nullable=true,precision=10,scale=0)
	public java.lang.Integer getTarget() {
		return target;
	}

	public void setTarget(java.lang.Integer target) {
		this.target = target;
	}
	@Column(name ="url",nullable=true)
	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	@Column(name ="img_url",nullable=true)
	public java.lang.String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(java.lang.String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
