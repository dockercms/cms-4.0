package com.leimingtech.core.entity;
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

import org.hibernate.annotations.ForeignKey;

import com.leimingtech.core.entity.IdEntity;

/**
 *自定义管理表
 * @author  
 */
@Entity
@Table(name = "cms_custom")
public class TSCustom extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private TSCustom TSCustom;//父地域
	private String customName;//地域名称
	private Short sustomLevel;//等级
	private String description; //描述
	private String sustomSort;//同区域中的显示顺序
	private List<TSCustom> TSCustoms = new ArrayList<TSCustom>();
	

	/**创建时间*/
	private java.util.Date createdtime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sustomparentid")
	@ForeignKey(name="null")//取消hibernate的外键生成
	public TSCustom getTSCustom() {
		return this.TSCustom;
	}
	public void setTSCustom(TSCustom TSCustom) {
		this.TSCustom = TSCustom;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSCustom")
	public List<TSCustom> getTSCustoms() {
		return TSCustoms;
	}
	public void setTSCustoms(List<TSCustom> TSCustoms) {
		this.TSCustoms = TSCustoms;
	}
	@Column(name = "customname", nullable = false, length = 50)
	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	
	@Column(name = "description", nullable = false, length = 50)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "sustomsort",nullable = false,length = 3)
	public String getSustomSort() {
		return sustomSort;
	}

	public void setSustomSort(String sustomSort) {
		this.sustomSort = sustomSort;
	}
	@Column(name = "sustomlevel",nullable = false,length = 1)
	public Short getSustomLevel() {
		return sustomLevel;
	}
	public void setSustomLevel(Short sustomLevel) {
		this.sustomLevel = sustomLevel;
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