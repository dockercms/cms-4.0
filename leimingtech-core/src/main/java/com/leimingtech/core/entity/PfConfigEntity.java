package com.leimingtech.core.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**   
 * @Title: Entity
 * @Description: 配置项
 * @author 
 * @date 2014-05-15 13:27:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_pf_config", schema = "")
public class PfConfigEntity extends IdEntity implements java.io.Serializable {
	
	/**平台配置存放的缓存*/
	public static Map<String ,String> pfconfigCache = new HashMap<String ,String>();
	

	/**配置键*/
	private java.lang.String configkey;
	/**配置值*/
	private java.lang.String configvalue;
	/**备注*/
	private java.lang.String remark;
	/**创建时间*/
	private java.util.Date createdtime;
	
	 
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  配置键
	 */
	@Column(name ="CONFIGKEY",nullable=true,length=100)
	public java.lang.String getConfigkey(){
		return this.configkey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配置键
	 */
	public void setConfigkey(java.lang.String configkey){
		this.configkey = configkey;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  配置值
	 */
	@Column(name ="CONFIGVALUE",nullable=true,length=100)
	public java.lang.String getConfigvalue(){
		return this.configvalue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配置值
	 */
	public void setConfigvalue(java.lang.String configvalue){
		this.configvalue = configvalue;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=255)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
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
