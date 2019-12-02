package com.leimingtech.wechat.entity.wechatbutton;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 自定义菜单管理
 * @author 
 * @date 2015-08-12 18:20:34
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wechat_button", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class WechatButtonEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3686041187681714606L;
	/** 公众号Id */
	private String wuid;
	/** 公众号token */
	private String token;
	/** 父Id */
	private String pid;
	/** 菜单名称 */
	private String name;
	/** 响应动作类型 */
	private String type;
	/** 资源Id */
	private String mediaId;
	/** 网页链接 */
	private String url;
	/** 菜单KEY值 */
	private String menukey;
	/** 菜单级别 */
	private Integer level;
	/** 版本 */
	private Integer version;

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 公众号Id
	 */
	@Column(name = "WUID", nullable = true, length = 32)
	public String getWuid() {
		return this.wuid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 公众号Id
	 */
	public void setWuid(String wuid) {
		this.wuid = wuid;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 公众号token
	 */
	@Column(name = "TOKEN", nullable = true, length = 255)
	public String getToken() {
		return this.token;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 公众号token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 父Id
	 */
	@Column(name = "PID", nullable = true, length = 32)
	public String getPid() {
		return this.pid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 父Id
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 菜单名称
	 */
	@Column(name = "NAME", nullable = true, length = 40)
	public String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 菜单名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 响应动作类型
	 */
	@Column(name = "TYPE", nullable = true, length = 32)
	public String getType() {
		return this.type;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 响应动作类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 资源Id
	 */
	@Column(name = "MEDIA_ID", nullable = true, length = 256)
	public String getMediaId() {
		return this.mediaId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 资源Id
	 */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 网页链接
	 */
	@Column(name = "URL", nullable = true, length = 256)
	public String getUrl() {
		return this.url;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 网页链接
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 菜单KEY值
	 */
	@Column(name = "MENUKEY", nullable = true, length = 128)
	public String getMenukey() {
		return this.menukey;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 菜单KEY值
	 */
	public void setMenukey(String menukey) {
		this.menukey = menukey;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 菜单级别
	 */
	@Column(name = "LEVEL", nullable = true, precision = 10, scale = 0)
	public Integer getLevel() {
		return this.level;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 菜单级别
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 版本
	 */
	@Version
	@Column(name = "VERSION", nullable = true, precision = 10, scale = 0)
	public Integer getVersion() {
		return this.version;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 版本
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
}
