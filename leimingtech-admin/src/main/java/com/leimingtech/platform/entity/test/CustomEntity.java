package com.leimingtech.platform.entity.test;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;


/**   
 * @Title: Entity
 * @Description: test
 * @author zhangdaihao
 * @date 2014-04-08 10:52:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_custom", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CustomEntity  extends IdEntity  implements java.io.Serializable {
				/**sustomlevel*/
				private java.lang.Integer sustomlevel;
				/**customname*/
				private java.lang.String customname;
				/**sustomsort*/
				private java.lang.String sustomsort;
				/**description*/
				private java.lang.String description;
				/**创建时间*/
				private java.util.Date createdtime;
				
	private  CustomEntity  custom ;
	private  List<CustomEntity> customs=new ArrayList<CustomEntity>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sustomparentid")
	public CustomEntity getCustom() {
		return this.custom;
	}

	public void setCustom(CustomEntity custom) {
		this.custom = custom;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "custom")
	public List<CustomEntity> getCustoms() {
		return customs;
	}
	public void setCustoms(List<CustomEntity> customs) {
		this.customs = customs;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  sustomlevel
	 */
	
	
	@Column(name ="SUSTOMLEVEL",nullable=false,precision=19,scale=0)
	public java.lang.Integer getSustomlevel(){
		return this.sustomlevel;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  sustomlevel
	 */
	public void setSustomlevel(java.lang.Integer sustomlevel){
		this.sustomlevel = sustomlevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  customname
	 */
	
	
	@Column(name ="CUSTOMNAME",nullable=false,length=50)
	public java.lang.String getCustomname(){
		return this.customname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  customname
	 */
	public void setCustomname(java.lang.String customname){
		this.customname = customname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  sustomsort
	 */
	
	
	@Column(name ="SUSTOMSORT",nullable=true,length=3)
	public java.lang.String getSustomsort(){
		return this.sustomsort;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  sustomsort
	 */
	public void setSustomsort(java.lang.String sustomsort){
		this.sustomsort = sustomsort;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  description
	 */
	
	
	@Column(name ="DESCRIPTION",nullable=true,length=256)
	public java.lang.String getDescription(){
		return this.description;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  description
	 */
	public void setDescription(java.lang.String description){
		this.description = description;
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
