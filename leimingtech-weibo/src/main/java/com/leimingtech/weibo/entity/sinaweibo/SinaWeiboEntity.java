package com.leimingtech.weibo.entity.sinaweibo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 新浪微博
 * @author 
 * @date 2015-12-03 14:22:46
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_weibo", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SinaWeiboEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 2040779594052276940L;
	/** 站点id */
	private java.lang.String siteId;
	/** App Key */
	private java.lang.String clientId;
	/** App Secret */
	private java.lang.String clientSercret;
	/** 回调地址 */
	private java.lang.String redirectUri;
	/** baseurl */
	private java.lang.String baseurl;
	/** accesstokenurl */
	private java.lang.String accesstokenurl;
	/** authorizeurl */
	private java.lang.String authorizeurl;
	/** rmurl */
	private java.lang.String rmurl;
	/** accesstoken */
	private java.lang.String accesstoken;
	/** userid */
	private java.lang.String userId;
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 站点id
	 */
	@Column(name = "SITE_ID", nullable = true, length = 255)
	public java.lang.String getSiteId() {
		return this.siteId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 站点id
	 */
	public void setSiteId(java.lang.String siteId) {
		this.siteId = siteId;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String App Key
	 */
	@Column(name = "CLIENT_ID", nullable = true, length = 255)
	public java.lang.String getClientId() {
		return this.clientId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String App Key
	 */
	public void setClientId(java.lang.String clientId) {
		this.clientId = clientId;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String App Secret
	 */
	@Column(name = "CLIENT_SERCRET", nullable = true, length = 255)
	public java.lang.String getClientSercret() {
		return this.clientSercret;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String App Secret
	 */
	public void setClientSercret(java.lang.String clientSercret) {
		this.clientSercret = clientSercret;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 回调地址
	 */
	@Column(name = "REDIRECT_URI", nullable = true, length = 255)
	public java.lang.String getRedirectUri() {
		return this.redirectUri;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 回调地址
	 */
	public void setRedirectUri(java.lang.String redirectUri) {
		this.redirectUri = redirectUri;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String baseurl
	 */
	@Column(name = "BASEURL", nullable = true, length = 255)
	public java.lang.String getBaseurl() {
		return this.baseurl;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String baseurl
	 */
	public void setBaseurl(java.lang.String baseurl) {
		this.baseurl = baseurl;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String accesstokenurl
	 */
	@Column(name = "ACCESSTOKENURL", nullable = true, length = 255)
	public java.lang.String getAccesstokenurl() {
		return this.accesstokenurl;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String accesstokenurl
	 */
	public void setAccesstokenurl(java.lang.String accesstokenurl) {
		this.accesstokenurl = accesstokenurl;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String authorizeurl
	 */
	@Column(name = "AUTHORIZEURL", nullable = true, length = 255)
	public java.lang.String getAuthorizeurl() {
		return this.authorizeurl;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String authorizeurl
	 */
	public void setAuthorizeurl(java.lang.String authorizeurl) {
		this.authorizeurl = authorizeurl;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String rmurl
	 */
	@Column(name = "RMURL", nullable = true, length = 255)
	public java.lang.String getRmurl() {
		return this.rmurl;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String rmurl
	 */
	public void setRmurl(java.lang.String rmurl) {
		this.rmurl = rmurl;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String accesstoken
	 */
	@Column(name = "ACCESSTOKEN", nullable = true, length = 255)
	public java.lang.String getAccesstoken() {
		return this.accesstoken;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String accesstoken
	 */
	public void setAccesstoken(java.lang.String accesstoken) {
		this.accesstoken = accesstoken;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String uid
	 */
	@Column(name = "USERID", nullable = true, length = 255)
	public java.lang.String getUserId() {
		return this.userId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String uid
	 */
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
}
