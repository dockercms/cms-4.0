package com.leimingtech.cms.page.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.leimingtech.core.entity.WorkFlowStepEntity;

/**   
 * @Title: Entity
 * @Description: 工作流
 * @author zhangdaihao
 * @date 2014-04-22 15:26:55
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_workflow", schema = "")
@SuppressWarnings("serial")
public class WorkFlowPage implements java.io.Serializable {
	/**保存-步骤*/
	private List<WorkFlowStepEntity> workFlowStepList = new ArrayList<WorkFlowStepEntity>();
	public List<WorkFlowStepEntity> getWorkFlowStepList() {
		return workFlowStepList;
	}
	public void setWorkFlowStepList(List<WorkFlowStepEntity> workFlowStepList) {
		this.workFlowStepList = workFlowStepList;
	}


	/**id*/
	private java.lang.Integer id;
	/**称名*/
	private java.lang.String name;
	/**描述*/
	private java.lang.String description;
	/**步骤*/
	private java.lang.Integer steps;
	
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  称名
	 */
	@Column(name ="NAME",nullable=true,length=30)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  称名
	 */
	public void setName(java.lang.String name){
		this.name = name;
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
	 *@return: java.lang.Integer  步骤
	 */
	@Column(name ="STEPS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSteps(){
		return this.steps;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  步骤
	 */
	public void setSteps(java.lang.Integer steps){
		this.steps = steps;
	}
}
