package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 投票日志
 * @author 
 * @date 2014-06-10 19:13:19
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_vote_log", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class VoteLogEntity extends IdEntity implements java.io.Serializable {
	
	/**投票id*/
	private java.lang.String voteid;
	/**投票时间*/
	private java.util.Date created;
	/**投票人*/
	private java.lang.String createdby;
	/**ip地址*/
	private java.lang.String ip;
	/**国家*/
	private java.lang.String country;
	/**省*/
	private java.lang.String province;
	/**市*/
	private java.lang.String city;
	/**区县*/
	private java.lang.String county;
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  投票id
	 */
	@Column(name ="VOTEID",nullable=true,length=32)
	public java.lang.String getVoteid(){
		return this.voteid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  投票id
	 */
	public void setVoteid(java.lang.String voteid){
		this.voteid = voteid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  投票时间
	 */
	@Column(name ="CREATED",nullable=true)
	public java.util.Date getCreated(){
		return this.created;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  投票时间
	 */
	public void setCreated(java.util.Date created){
		this.created = created;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  投票人
	 */
	@Column(name ="CREATEDBY",nullable=true,length=255)
	public java.lang.String getCreatedby(){
		return this.createdby;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  投票人
	 */
	public void setCreatedby(java.lang.String createdby){
		this.createdby = createdby;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ip地址
	 */
	@Column(name ="IP",nullable=true,length=15)
	public java.lang.String getIp(){
		return this.ip;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ip地址
	 */
	public void setIp(java.lang.String ip){
		this.ip = ip;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  国家
	 */
	@Column(name ="COUNTRY",nullable=true,length=255)
	public java.lang.String getCountry(){
		return this.country;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  国家
	 */
	public void setCountry(java.lang.String country){
		this.country = country;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  省
	 */
	@Column(name ="PROVINCE",nullable=true,length=255)
	public java.lang.String getProvince(){
		return this.province;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省
	 */
	public void setProvince(java.lang.String province){
		this.province = province;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  市
	 */
	@Column(name ="CITY",nullable=true,length=255)
	public java.lang.String getCity(){
		return this.city;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  市
	 */
	public void setCity(java.lang.String city){
		this.city = city;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  区县
	 */
	@Column(name ="COUNTY",nullable=true,length=255)
	public java.lang.String getCounty(){
		return this.county;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  区县
	 */
	public void setCounty(java.lang.String county){
		this.county = county;
	}
}
