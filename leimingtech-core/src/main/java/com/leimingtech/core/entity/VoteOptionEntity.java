package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 投票选项
 * @author zhangdaihao
 * @date 2014-04-29 11:02:25
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_vote_option", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class VoteOptionEntity extends IdEntity implements java.io.Serializable {
	
	/**投票ID*/
	private java.lang.String voteid; 
	/**选项名称*/
	private java.lang.String optionname;
	/**选项链接*/
	private java.lang.String optionlink;
	/**选项图片*/
	private java.lang.String optionimg;
	/**初始票数*/
	private java.lang.Integer optiontotal;
	/**排序*/
	private java.lang.String optionsort;
	/**站点id*/
	private java.lang.String siteid;
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
	 *@return: java.lang.String  投票ID
	 */
	@Column(name ="VOTEID",nullable=true,length=255)
	public java.lang.String getVoteid(){
		return this.voteid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  投票ID
	 */
	public void setVoteid(java.lang.String voteid){
		this.voteid = voteid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  选项名称
	 */
	@Column(name ="OPTIONNAME",nullable=true,length=255)
	public java.lang.String getOptionname(){
		return this.optionname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选项名称
	 */
	public void setOptionname(java.lang.String optionname){
		this.optionname = optionname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  选项链接
	 */
	@Column(name ="OPTIONLINK",nullable=true,length=255)
	public java.lang.String getOptionlink(){
		return this.optionlink;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选项链接
	 */
	public void setOptionlink(java.lang.String optionlink){
		this.optionlink = optionlink;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  选项图片
	 */
	@Column(name ="OPTIONIMG",nullable=true,length=255)
	public java.lang.String getOptionimg(){
		return this.optionimg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选项图片
	 */
	public void setOptionimg(java.lang.String optionimg){
		this.optionimg = optionimg;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  初始票数
	 */
	@Column(name ="OPTIONTOTAL",nullable=true,precision=10,scale=0)
	public java.lang.Integer getOptiontotal(){
		return this.optiontotal;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  初始票数
	 */
	public void setOptiontotal(java.lang.Integer optiontotal){
		this.optiontotal = optiontotal;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排序
	 */
	@Column(name ="OPTIONSORT",nullable=true,length=255)
	public java.lang.String getOptionsort(){
		return this.optionsort;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排序
	 */
	public void setOptionsort(java.lang.String optionsort){
		this.optionsort = optionsort;
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
}
