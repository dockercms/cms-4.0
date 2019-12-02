package com.leimingtech.core.entity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 模型
 * @author zhangdaihao
 * @date 2014-04-21 16:32:19
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_model", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ModelsEntity extends IdEntity implements java.io.Serializable {

	/**模型名称*/
	private java.lang.String name;
	/**别名*/
	private java.lang.String alias;
	/**描述*/
	private java.lang.String description;
	/**模板列表*/
	private java.lang.String templateList;
	/**模板显示*/
	private java.lang.String templateShow;
	/**排序*/
	private java.lang.Integer sort;
	/**不可用 (1不可用，0可用)*/
	private java.lang.Integer disabled;
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
	 *@return: java.lang.String  模型名称
	 */
	@Column(name ="NAME",nullable=true,length=20)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模型名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  别名
	 */
	@Column(name ="ALIAS",nullable=true,length=15)
	public java.lang.String getAlias(){
		return this.alias;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  别名
	 */
	public void setAlias(java.lang.String alias){
		this.alias = alias;
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
	 *@return: java.lang.String  模板列表
	 */
	@Column(name ="TEMPLATE_LIST",nullable=true,length=100)
	public java.lang.String getTemplateList(){
		return this.templateList;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模板列表
	 */
	public void setTemplateList(java.lang.String templateList){
		this.templateList = templateList;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模板显示
	 */
	@Column(name ="TEMPLATE_SHOW",nullable=true,length=100)
	public java.lang.String getTemplateShow(){
		return this.templateShow;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模板显示
	 */
	public void setTemplateShow(java.lang.String templateShow){
		this.templateShow = templateShow;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="SORT",nullable=true,precision=3,scale=0)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  不可用
	 */
	@Column(name ="DISABLED",nullable=true,precision=3,scale=0)
	public java.lang.Integer getDisabled(){
		return this.disabled;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  不可用
	 */
	public void setDisabled(java.lang.Integer disabled){
		this.disabled = disabled;
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
