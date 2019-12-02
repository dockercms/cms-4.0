package com.leimingtech.wechat.entity.wechatpush;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 微信推送
 * @author 
 * @date 2015-09-11 14:47:12
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wechat_push", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class WeChatPushEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 9132256418109907443L;
	/** 创建人 */
	private String createPerson;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 推送人 */
	private String pushPerson;
	/** 推送时间 */
	private java.util.Date pushTime;
	/** 是否推送,1:是,0:否 */
	private Integer isPush;
	/** 是否推送成功,1:成功,0:失败 */
	private Integer isSucesspush;
	/**站点id*/
	private String siteId;

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 创建人
	 */
	@Column(name = "CREATE_PERSON", nullable = true, length = 255)
	public String getCreatePerson() {
		return this.createPerson;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 创建人
	 */
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATE_TIME", nullable = true)
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 创建时间
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 推送人
	 */
	@Column(name = "PUSH_PERSON", nullable = true, length = 255)
	public String getPushPerson() {
		return this.pushPerson;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 推送人
	 */
	public void setPushPerson(String pushPerson) {
		this.pushPerson = pushPerson;
	}

	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 推送时间
	 */
	@Column(name = "PUSH_TIME", nullable = true)
	public java.util.Date getPushTime() {
		return this.pushTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 推送时间
	 */
	public void setPushTime(java.util.Date pushTime) {
		this.pushTime = pushTime;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否推送,1:是,0:否
	 */
	@Column(name = "IS_PUSH", nullable = true, precision = 10, scale = 0)
	public Integer getIsPush() {
		return this.isPush;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否推送,1:是,0:否
	 */
	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否推送成功,1:成功,0:失败
	 */
	@Column(name = "IS_SUCESSPUSH", nullable = true, precision = 10, scale = 0)
	public Integer getIsSucesspush() {
		return this.isSucesspush;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否推送成功,1:成功,0:失败
	 */
	public void setIsSucesspush(Integer isSucesspush) {
		this.isSucesspush = isSucesspush;
	}


	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 站点ID
	 */
	@Column(name = "site_id", nullable = true, length = 32)
	public String getSiteId() {
		return this.siteId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 站点ID
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
}
