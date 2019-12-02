package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TSRole;



/**
 * @Title: Entity
 * @Description: 站点权限
 * @author
 * @date 2015-10-20 11:16:57
 * @version V1.0
 * 
 */
@Entity
@Table(name = "cms_role_site", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class RoleSiteEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3974444746833838578L;

	private SiteEntity site;

	private TSRole role;

	/** 创建时间 */
	private java.util.Date createdTime;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SITE_ID")
	public SiteEntity getSite() {
		return site;
	}

	public void setSite(SiteEntity site) {
		this.site = site;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID")
	public TSRole getRole() {
		return role;
	}

	public void setRole(TSRole role) {
		this.role = role;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATED_TIME", nullable = true)
	public java.util.Date getCreatedTime() {
		return this.createdTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 创建时间
	 */
	public void setCreatedTime(java.util.Date createdTime) {
		this.createdTime = createdTime;
	}
}
