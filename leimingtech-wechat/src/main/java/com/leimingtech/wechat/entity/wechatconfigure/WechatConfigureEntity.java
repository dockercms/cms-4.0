package com.leimingtech.wechat.entity.wechatconfigure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 微信公众账号配置
 * @author 
 * @date 2015-12-02 15:53:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "weChat_configure", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class WechatConfigureEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -2569240757486423828L;
	/** 微信公众账号appid */
	private String appid;
	/** 微信公众号的秘钥 */
	private String secret;
	/** 微信公众号的token */
	private String token;
	/** 微信公众号的EncodingAESKey */
	private String aeskey;
	/** 站点id */
	private String siteid;
	/** 创建时间 */
	private java.util.Date createtime;

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 微信公众账号appid
	 */
	@Column(name = "APPID", nullable = false, length = 255)
	public String getAppid() {
		return this.appid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 微信公众账号appid
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 微信公众号的秘钥
	 */
	@Column(name = "SECRET", nullable = false, length = 255)
	public String getSecret() {
		return this.secret;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 微信公众号的秘钥
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 微信公众号的token
	 */
	@Column(name = "TOKEN", nullable = false, length = 255)
	public String getToken() {
		return this.token;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 微信公众号的token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 微信公众号的EncodingAESKey
	 */
	@Column(name = "AESKEY", nullable = false, length = 255)
	public String getAeskey() {
		return this.aeskey;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 微信公众号的EncodingAESKey
	 */
	public void setAeskey(String aeskey) {
		this.aeskey = aeskey;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 站点id
	 */
	@Column(name = "SITEID", nullable = false, length = 32)
	public String getSiteid() {
		return this.siteid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 站点id
	 */
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "createtime", nullable = true)
	public java.util.Date getCreatetime() {
		return this.createtime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 创建时间
	 */
	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}
}
