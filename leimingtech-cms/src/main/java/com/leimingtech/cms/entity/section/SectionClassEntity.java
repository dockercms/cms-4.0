
package com.leimingtech.cms.entity.section;

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
 * @Description: 区块分类
 * @author leiming
 * @date 2014-04-21 17:26:59
 * @version V1.0
 * 
 */
@Entity
@Table(name = "cms_section_class", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SectionClassEntity extends IdEntity implements java.io.Serializable {
	
	/** 分类名称 */
	private java.lang.String name;
	/** 排序 */
	private java.lang.Integer sort;
	/** 备注 */
	private java.lang.String memo;
	
	/** level */
	
	private java.lang.Integer levels;
	private SectionClassEntity sectionClass;
	private List<SectionClassEntity> sectionClasss = new ArrayList<SectionClassEntity>();
	private java.lang.String siteid;
	
	/**创建时间*/
	private java.util.Date createdtime;
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 站点
	 */

	@Column(name = "SITEID", nullable = false, length = 32)
	public java.lang.String getSiteid() {
		return this.siteid;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 站点
	 */
	public void setSiteid(java.lang.String siteid) {
		this.siteid = siteid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	public SectionClassEntity getSectionClass() {
		return this.sectionClass;
	}

	public void setSectionClass(SectionClassEntity sectionClass) {
		this.sectionClass = sectionClass;
	}

	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sectionClass")
	public List<SectionClassEntity> getSectionClasss() {
		return sectionClasss;
	}

	public void setSectionClasss(List<SectionClassEntity> sectionClasss) {
		this.sectionClasss = sectionClasss;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 分类名称
	 */

	@Column(name = "NAME", nullable = false, length = 50)
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 分类名称
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 排序
	 */

	@Column(name = "SORT", nullable = false, precision = 3, scale = 0)
	public java.lang.Integer getSort() {
		return this.sort;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 排序
	 */
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}

	/**
	 * 方法: 取得java.lang.Object
	 * 
	 * @return: java.lang.Object 备注
	 */

	@Column(name = "MEMO", nullable = true, length = 65535)
	public java.lang.String getMemo() {
		return this.memo;
	}

	/**
	 * 方法: 设置java.lang.Object
	 * 
	 * @param: java.lang.Object 备注
	 */
	public void setMemo(java.lang.String memo) {
		this.memo = memo;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer level
	 */

	@Column(name = "LEVELS", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getLevels() {
		return this.levels;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer level
	 */
	public void setLevels(java.lang.Integer levels) {
		this.levels = levels;
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
