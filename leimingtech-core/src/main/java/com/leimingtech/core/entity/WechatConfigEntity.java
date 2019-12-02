package com.leimingtech.core.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: Entity
 * @Description: 微信参数
 * @author
 * @date 2015-08-13 14:56:06
 * @version V1.0
 * 
 */
@Entity
@Table(name = "wechat_config" , schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class WechatConfigEntity extends IdEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = 1032576088361258993L;
	public static List<WechatConfigEntity> allWechatConfig = new ArrayList<WechatConfigEntity>();
	
	/** 参数名称 */
	private String configname;
	/** 参数值 */
	private String configvalue;
	/** 备注 */
	private String remark;
	/** 版本 */
	private Integer version;

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 参数名称
	 */
	@Column(name = "CONFIGNAME" , nullable = true , length = 255)
	public String getConfigname() {
		return this.configname;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 参数名称
	 */
	public void setConfigname(String configname) {
		this.configname = configname;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 参数值
	 */
	@Column(name = "CONFIGVALUE" , nullable = true , length = 1000)
	public String getConfigvalue() {
		return this.configvalue;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 参数值
	 */
	public void setConfigvalue(String configvalue) {
		this.configvalue = configvalue;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 备注
	 */
	@Column(name = "REMARK" , nullable = true , length = 65535)
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 版本
	 */
	@Version
	@Column(name = "VERSION" , nullable = true , precision = 10 , scale = 0)
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
	
	/**
	 * 根据参数名获取参数值
	 * 
	 * @param configName
	 *            参数名称
	 * @return
	 */
	@Transient
	public static String getConfig(String configName) {
		if(StringUtils.isEmpty(configName)) {
			return null;
		}
		for(WechatConfigEntity wce : WechatConfigEntity.allWechatConfig) {
			if(configName.toLowerCase().equals(wce.getConfigname().toLowerCase())) {
				return wce.getConfigvalue();
			}
		}
		return null;
	}
}
