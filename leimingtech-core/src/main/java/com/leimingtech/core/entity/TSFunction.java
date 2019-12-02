package com.leimingtech.core.entity;
import org.hibernate.annotations.OrderBy;

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

/**
 *菜单权限表
 * @author  
 */
@Entity
@Table(name = "cms_function")
public class TSFunction extends IdEntity implements java.io.Serializable {
	private TSFunction TSFunction;//父菜单
	private String functionName;//菜单名称
	private Short functionLevel;//菜单等级
	private String functionUrl;//菜单地址
	private Short functionIframe;//菜单地址打开方式
	private String functionOrder;//菜单排序
	private String privurl;//功能url
	private String iconclass;//图标class样式
	private List<TSFunction> TSFunctions = new ArrayList<TSFunction>();
	/** 所有父节点id,包含当前id */
	private java.lang.String pathids;
	/** 所有父节点id */
	private java.lang.String parentids;

	/**创建时间*/
	private java.util.Date createdtime;
	
	@Column(name = "iconclass", nullable = false, length = 32)
	public String getIconclass() {
		return iconclass;
	}

	public void setIconclass(String iconclass) {
		this.iconclass = iconclass;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentfunctionid")
	public TSFunction getTSFunction() {
		return this.TSFunction;
	}

	public void setTSFunction(TSFunction TSFunction) {
		this.TSFunction = TSFunction;
	}

	@Column(name = "functionname", nullable = false, length = 50)
	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Column(name = "functionlevel")
	public Short getFunctionLevel() {
		return this.functionLevel;
	}

	public void setFunctionLevel(Short functionLevel) {
		this.functionLevel = functionLevel;
	}
	@Column(name = "functionurl", length = 100)
	public String getFunctionUrl() {
		return this.functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}
	@Column(name = "functionorder")
	public String getFunctionOrder() {
		return functionOrder;
	}
    
	public void setFunctionOrder(String functionOrder) {
		this.functionOrder = functionOrder;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSFunction")
//	@Where(clause="DEL_FLAG=1")
	@OrderBy(clause="functionorder desc,CREATEDTIME desc")
	public List<TSFunction> getTSFunctions() {
		return TSFunctions;
	}
	public void setTSFunctions(List<TSFunction> TSFunctions) {
		this.TSFunctions = TSFunctions;
	}
	@Column(name = "functioniframe")
	public Short getFunctionIframe() {
		return functionIframe;
	}
	public void setFunctionIframe(Short functionIframe) {
		this.functionIframe = functionIframe;
	}
	@Column(name = "privurl", length = 1000)
	public String getPrivurl() {
		return privurl;
	}
	public void setPrivurl(String privurl) {
		this.privurl = privurl;
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
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 所有父节点id,包含当前id
	 */
	@Column(name = "PATHIDS", nullable = true, length = 255)
	public java.lang.String getPathids() {
		return this.pathids;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 所有父节点id,包含当前id
	 */
	public void setPathids(java.lang.String pathids) {
		this.pathids = pathids;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 所有父节点id
	 */
	@Column(name = "PARENTIDS", nullable = true, length = 255)
	public java.lang.String getParentids() {
		return this.parentids;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 所有父节点id
	 */
	public void setParentids(java.lang.String parentids) {
		this.parentids = parentids;
	}
}