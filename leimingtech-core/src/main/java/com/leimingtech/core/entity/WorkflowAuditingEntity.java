package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 工作流评审
 * @author zhangdaihao
 * @date 2014-04-25 14:01:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_workflow_auditing", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class WorkflowAuditingEntity extends IdEntity implements java.io.Serializable {
	/**关联ID*/
	private java.lang.String relationid;
	/**类别*/
	private java.lang.Integer type;
	/**步骤*/
	private java.lang.Integer step;
	/**审核角色*/
	private java.lang.String auditingrole;
	/**审核意见*/
	private java.lang.String opinion;
	/**审核人*/
	private java.lang.String auditor;
	/**审核时间*/
	private java.util.Date auditingdate;
	/**审核结果*/
	private java.lang.Integer auditingresult;
	/**审核人ID*/
	private java.lang.String operatoroid;
	/**删除标记*/
	private java.lang.Integer delflag;
	
	/**站点id*/
	private java.lang.String siteid;
	/**创建时间*/
	private java.util.Date createdtime;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关联ID
	 */
	@Column(name ="RELATIONID",nullable=false,length=32)
	public java.lang.String getRelationid(){
		return this.relationid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联ID
	 */
	public void setRelationid(java.lang.String relationid){
		this.relationid = relationid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  类别
	 */
	@Column(name ="TYPE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  类别
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  类别
	 */
	@Column(name ="STEP",nullable=true,precision=10,scale=0)
	public java.lang.Integer getStep(){
		return this.step;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  类别
	 */
	public void setStep(java.lang.Integer step){
		this.step = step;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核角色
	 */
	@Column(name ="AUDITINGROLE",nullable=true,length=256)
	public java.lang.String getAuditingrole(){
		return this.auditingrole;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核角色
	 */
	public void setAuditingrole(java.lang.String auditingrole){
		this.auditingrole = auditingrole;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核意见
	 */
	@Column(name ="OPINION",nullable=true,length=1024)
	public java.lang.String getOpinion(){
		return this.opinion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核意见
	 */
	public void setOpinion(java.lang.String opinion){
		this.opinion = opinion;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */
	@Column(name ="AUDITOR",nullable=true,length=50)
	public java.lang.String getAuditor(){
		return this.auditor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setAuditor(java.lang.String auditor){
		this.auditor = auditor;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核时间
	 */
	@Column(name ="AUDITINGDATE",nullable=true)
	public java.util.Date getAuditingdate(){
		return this.auditingdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审核时间
	 */
	public void setAuditingdate(java.util.Date auditingdate){
		this.auditingdate = auditingdate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审核结果
	 */
	@Column(name ="AUDITINGRESULT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getAuditingresult(){
		return this.auditingresult;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核结果
	 */
	public void setAuditingresult(java.lang.Integer auditingresult){
		this.auditingresult = auditingresult;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人ID
	 */
	@Column(name ="OPERATOROID",nullable=true,length=100)
	public java.lang.String getOperatoroid(){
		return this.operatoroid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人ID
	 */
	public void setOperatoroid(java.lang.String operatoroid){
		this.operatoroid = operatoroid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  删除标记
	 */
	@Column(name ="DELFLAG",nullable=true,precision=10,scale=0)
	public java.lang.Integer getDelflag(){
		return this.delflag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  删除标记
	 */
	public void setDelflag(java.lang.Integer delflag){
		this.delflag = delflag;
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
