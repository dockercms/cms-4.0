package com.leimingtech.platform.entity.doc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;


/**   
 * @Title: Entity
 * @Description: 文档和文档引用的实体关联表
 * @author 
 * @date 2014-06-25 19:39:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_doc_entity_ref", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class DocEnRefEntity extends IdEntity implements java.io.Serializable {
	 
	/**文档id*/
	private DocEntity doc;
	/**实体id*/
	private DocEnEntity docEn;
	/**排序*/
	private java.lang.Integer sort;
	/**描述*/
	private java.lang.String description;
	/**状态*/
	private java.lang.Integer status;
	/**添加时间*/
	private java.util.Date createdtime;
	/**添加人*/
	private java.lang.String createdby;
	/**修改次数*/
	private java.lang.Integer updatecount;
	/**修改时间*/
	private java.util.Date updatetime;
	/**修改人*/
	private java.lang.String updateby;
	
	 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCID")
	public DocEntity getDoc() {
		return doc;
	}

	public void setDoc(DocEntity doc) {
		this.doc = doc;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  实体id
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENTITYID")
	public DocEnEntity getDocEn() {
		return docEn;
	}

	public void setDocEn(DocEnEntity docEn) {
		this.docEn = docEn;
	}
	
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="SORT",nullable=true,precision=10,scale=0)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  状态
	 */
	@Column(name ="STATUS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  状态
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  添加时间
	 */
	@Column(name ="CREATEDTIME",nullable=true)
	public java.util.Date getCreatedtime(){
		return this.createdtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  添加时间
	 */
	public void setCreatedtime(java.util.Date createdtime){
		this.createdtime = createdtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  添加人
	 */
	@Column(name ="CREATEDBY",nullable=true,length=255)
	public java.lang.String getCreatedby(){
		return this.createdby;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  添加人
	 */
	public void setCreatedby(java.lang.String createdby){
		this.createdby = createdby;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  修改次数
	 */
	@Column(name ="UPDATECOUNT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getUpdatecount(){
		return this.updatecount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  修改次数
	 */
	public void setUpdatecount(java.lang.Integer updatecount){
		this.updatecount = updatecount;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="UPDATETIME",nullable=true)
	public java.util.Date getUpdatetime(){
		return this.updatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdatetime(java.util.Date updatetime){
		this.updatetime = updatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */
	@Column(name ="UPDATEBY",nullable=true,length=255)
	public java.lang.String getUpdateby(){
		return this.updateby;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setUpdateby(java.lang.String updateby){
		this.updateby = updateby;
	}
}
