package com.leimingtech.cms.entity.acquisition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 数据采集关联表 内容替换
 * @author 
 * @date 2015-04-07 15:52:11
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_acquisition_replace", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AcquisitionreplaceEntity extends IdEntity implements java.io.Serializable {
	
	/**内容替换为*/
	private java.lang.String replaceNew;
	/**将什么内容替换*/
	private java.lang.String replaceOld;
	/**创建日期*/
	private java.util.Date createtime;
	/**采集id*/
	private java.lang.String acquisitionId;
	

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容替换为
	 */
	@Column(name ="REPLACE_NEW",nullable=true,length=1000)
	public java.lang.String getReplaceNew(){
		return this.replaceNew;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容替换为
	 */
	public void setReplaceNew(java.lang.String replaceNew){
		this.replaceNew = replaceNew;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  将什么内容替换
	 */
	@Column(name ="REPLACE_OLD",nullable=true,length=1000)
	public java.lang.String getReplaceOld(){
		return this.replaceOld;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  将什么内容替换
	 */
	public void setReplaceOld(java.lang.String replaceOld){
		this.replaceOld = replaceOld;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATETIME",nullable=true)
	public java.util.Date getCreatetime(){
		return this.createtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreatetime(java.util.Date createtime){
		this.createtime = createtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  采集id
	 */
	@Column(name ="ACQUISITION_ID",nullable=true,length=32)
	public java.lang.String getAcquisitionId(){
		return this.acquisitionId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  采集id
	 */
	public void setAcquisitionId(java.lang.String acquisitionId){
		this.acquisitionId = acquisitionId;
	}
}
