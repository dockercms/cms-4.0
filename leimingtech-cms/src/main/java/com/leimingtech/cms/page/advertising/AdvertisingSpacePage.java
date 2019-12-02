package com.leimingtech.cms.page.advertising;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.leimingtech.cms.entity.advertising.AdvertisingEntity;

import javax.persistence.SequenceGenerator;


/**   
 * @Title: Entity
 * @Description: 广告栏位
 * @author zhangdaihao
 * @date 2014-04-16 17:27:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_advertising_space", schema = "")
@SuppressWarnings("serial")
public class AdvertisingSpacePage implements java.io.Serializable {
	/**保存-广告管理*/
	private List<AdvertisingEntity> advertisingList = new ArrayList<AdvertisingEntity>();
	public List<AdvertisingEntity> getAdvertisingList() {
		return advertisingList;
	}
	public void setAdvertisingList(List<AdvertisingEntity> advertisingList) {
		this.advertisingList = advertisingList;
	}


	/**id*/
	private java.lang.Integer id;
	/**属所网站id*/
	private java.lang.Integer siteId;
	/**名称*/
	private java.lang.String adName;
	/**描述*/
	private java.lang.String description;
	/**是否启用*/
	private java.lang.String isEnabled;
	
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
	 *@return: java.lang.Integer  属所网站id
	 */
	@Column(name ="SITE_ID",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSiteId(){
		return this.siteId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  属所网站id
	 */
	public void setSiteId(java.lang.Integer siteId){
		this.siteId = siteId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="AD_NAME",nullable=true,length=100)
	public java.lang.String getAdName(){
		return this.adName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setAdName(java.lang.String adName){
		this.adName = adName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述
	 */
	@Column(name ="DESCRIPTION",nullable=true,length=255)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否启用
	 */
	@Column(name ="IS_ENABLED",nullable=true,length=1)
	public java.lang.String getIsEnabled(){
		return this.isEnabled;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否启用
	 */
	public void setIsEnabled(java.lang.String isEnabled){
		this.isEnabled = isEnabled;
	}
}
