package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: WAP配置项扩展表
 * @author 
 * @date 2014-06-25 18:14:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_wap_expand", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class WapexpandEntity extends IdEntity implements java.io.Serializable {

	/**主配置id*/
	private java.lang.String configid;
	/**模型*/
	private java.lang.String modelids;
	/**栏目*/
	private java.lang.String catalogids;

	


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主配置id
	 */
	@Column(name ="CONFIGID",nullable=true,length=32)
	public java.lang.String getConfigid(){
		return this.configid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主配置id
	 */
	public void setConfigid(java.lang.String configid){
		this.configid = configid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模型
	 */
	@Column(name ="MODELIDS",nullable=true,length=255)
	public java.lang.String getModelids(){
		return this.modelids;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模型
	 */
	public void setModelids(java.lang.String modelids){
		this.modelids = modelids;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  栏目
	 */
	@Column(name ="CATALOGIDS",nullable=true,length=1000)
	public java.lang.String getCatalogids(){
		return this.catalogids;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  栏目
	 */
	public void setCatalogids(java.lang.String catalogids){
		this.catalogids = catalogids;
	}
	
}
