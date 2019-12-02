package com.leimingtech.wechat.entity.wechatuser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 微信号管理
 * @author 
 * @date 2015-08-16 15:35:12
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wechat_user", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class WechatUserEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -6449134727882484148L;
	/** 公众号名称 */
	private String name;
	/** 公众号原始id */
	private String originalid;
	/** 微信号 */
	private String wechatid;
	/** AppID */
	private String appid;
	/** AppSecret */
	private String appsecret;
	/** 二维码 */
	private String qrpath;
	/** 微信号类型 */
	private String type;
	/** access_token */
	private String accessToken;
	/** 消息加解密密钥 */
	private String aeskey;
	/** 消息加密方式 */
	private String encode;
	/** 头像 */
	private String headerpic;
	/** token */
	private String token;
	/** QQ */
	private String qq;
	/** 创建时间 */
	private java.util.Date createtime;
	/** 最后一次修改时间 */
	private java.util.Date lastupdatetime;
	/** 最后一次获取accessToken时间 */
	private java.util.Date lastgetaccesstokentime;
	/** 倒数第二次获取accessToken实际到期时间 */
	private java.util.Date lastoverduetime;
	/** 是否已经提前更新 */
	private Boolean isupdate;
	/** 版本 */
	private Integer version;
	/** 公众号邮箱 */
	private String email;
	/** 粉丝数 */
	private Integer fanscount;
	/** 是否启用微信卡券 */
	private Boolean isstartingusingcoupons;
	/** 是否启用附近图文 */
	private Boolean isshownearbynews;
	/** 是否开启聊天（小黄鸡） */
	private Boolean isopenchat;
	/** 省 */
	private String province;
	/** 市 */
	private String city;
	/** 地区 */
	private String region;

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 公众号名称
	 */
	@Column(name = "NAME", nullable = true, length = 255)
	public String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 公众号名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 公众号原始id
	 */
	@Column(name = "ORIGINALID", nullable = true, length = 255)
	public String getOriginalid() {
		return this.originalid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 公众号原始id
	 */
	public void setOriginalid(String originalid) {
		this.originalid = originalid;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 微信号
	 */
	@Column(name = "WECHATID", nullable = true, length = 255)
	public String getWechatid() {
		return this.wechatid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 微信号
	 */
	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String AppID
	 */
	@Column(name = "APPID", nullable = true, length = 255)
	public String getAppid() {
		return this.appid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String AppID
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String AppSecret
	 */
	@Column(name = "APPSECRET", nullable = true, length = 255)
	public String getAppsecret() {
		return this.appsecret;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String AppSecret
	 */
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 二维码
	 */
	@Column(name = "QRPATH", nullable = true, length = 400)
	public String getQrpath() {
		return this.qrpath;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 二维码
	 */
	public void setQrpath(String qrpath) {
		this.qrpath = qrpath;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 微信号类型
	 */
	@Column(name = "TYPE", nullable = true, length = 255)
	public String getType() {
		return this.type;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 微信号类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String access_token
	 */
	@Column(name = "ACCESS_TOKEN", nullable = true, length = 1024)
	public String getAccessToken() {
		return this.accessToken;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String access_token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 消息加解密密钥
	 */
	@Column(name = "AESKEY", nullable = true, length = 255)
	public String getAeskey() {
		return this.aeskey;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 消息加解密密钥
	 */
	public void setAeskey(String aeskey) {
		this.aeskey = aeskey;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 消息加密方式
	 */
	@Column(name = "ENCODE", nullable = true, length = 255)
	public String getEncode() {
		return this.encode;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 消息加密方式
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 头像
	 */
	@Column(name = "HEADERPIC", nullable = true, length = 255)
	public String getHeaderpic() {
		return this.headerpic;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 头像
	 */
	public void setHeaderpic(String headerpic) {
		this.headerpic = headerpic;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String token
	 */
	@Column(name = "TOKEN", nullable = true, length = 255)
	public String getToken() {
		return this.token;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String QQ
	 */
	@Column(name = "QQ", nullable = true, length = 255)
	public String getQq() {
		return this.qq;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String QQ
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATETIME", nullable = true)
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

	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 最后一次修改时间
	 */
	@Column(name = "LASTUPDATETIME", nullable = true)
	public java.util.Date getLastupdatetime() {
		return this.lastupdatetime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 最后一次修改时间
	 */
	public void setLastupdatetime(java.util.Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}

	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 最后一次获取accessToken时间
	 */
	@Column(name = "LASTGETACCESSTOKENTIME", nullable = true)
	public java.util.Date getLastgetaccesstokentime() {
		return this.lastgetaccesstokentime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 最后一次获取accessToken时间
	 */
	public void setLastgetaccesstokentime(java.util.Date lastgetaccesstokentime) {
		this.lastgetaccesstokentime = lastgetaccesstokentime;
	}

	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 倒数第二次获取accessToken实际到期时间
	 */
	@Column(name = "LASTOVERDUETIME", nullable = true)
	public java.util.Date getLastoverduetime() {
		return this.lastoverduetime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 倒数第二次获取accessToken实际到期时间
	 */
	public void setLastoverduetime(java.util.Date lastoverduetime) {
		this.lastoverduetime = lastoverduetime;
	}

	/**
	 * 方法: 取得java.lang.Boolean
	 *
	 * @return: java.lang.Boolean 是否已经提前更新
	 */
	@Column(name = "ISUPDATE", nullable = true, precision = 1)
	public Boolean getIsupdate() {
		return this.isupdate;
	}

	/**
	 * 方法: 设置java.lang.Boolean
	 *
	 * @param: java.lang.Boolean 是否已经提前更新
	 */
	public void setIsupdate(Boolean isupdate) {
		this.isupdate = isupdate;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 版本
	 */
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

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 公众号邮箱
	 */
	@Column(name = "EMAIL", nullable = true, length = 100)
	public String getEmail() {
		return this.email;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 公众号邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 粉丝数
	 */
	@Column(name = "FANSCOUNT", nullable = true, length = 255)
	public Integer getFanscount() {
		return this.fanscount;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 粉丝数
	 */
	public void setFanscount(Integer fanscount) {
		this.fanscount = fanscount;
	}

	/**
	 * 方法: 取得java.lang.Boolean
	 *
	 * @return: java.lang.Boolean 是否启用微信卡券
	 */
	@Column(name = "ISSTARTINGUSINGCOUPONS", nullable = true, precision = 1)
	public Boolean getIsstartingusingcoupons() {
		return this.isstartingusingcoupons;
	}

	/**
	 * 方法: 设置java.lang.Boolean
	 *
	 * @param: java.lang.Boolean 是否启用微信卡券
	 */
	public void setIsstartingusingcoupons(Boolean isstartingusingcoupons) {
		this.isstartingusingcoupons = isstartingusingcoupons;
	}

	/**
	 * 方法: 取得java.lang.Boolean
	 *
	 * @return: java.lang.Boolean 是否启用附近图文
	 */
	@Column(name = "ISSHOWNEARBYNEWS", nullable = true, precision = 1)
	public Boolean getIsshownearbynews() {
		return this.isshownearbynews;
	}

	/**
	 * 方法: 设置java.lang.Boolean
	 *
	 * @param: java.lang.Boolean 是否启用附近图文
	 */
	public void setIsshownearbynews(Boolean isshownearbynews) {
		this.isshownearbynews = isshownearbynews;
	}

	/**
	 * 方法: 取得java.lang.Boolean
	 *
	 * @return: java.lang.Boolean 是否开启聊天（小黄鸡）
	 */
	@Column(name = "ISOPENCHAT", nullable = true, precision = 1)
	public Boolean getIsopenchat() {
		return this.isopenchat;
	}

	/**
	 * 方法: 设置java.lang.Boolean
	 *
	 * @param: java.lang.Boolean 是否开启聊天（小黄鸡）
	 */
	public void setIsopenchat(Boolean isopenchat) {
		this.isopenchat = isopenchat;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 省
	 */
	@Column(name = "PROVINCE", nullable = true, length = 50)
	public String getProvince() {
		return this.province;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 省
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 市
	 */
	@Column(name = "CITY", nullable = true, length = 100)
	public String getCity() {
		return this.city;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 市
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 地区
	 */
	@Column(name = "REGION", nullable = true, length = 150)
	public String getRegion() {
		return this.region;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 地区
	 */
	public void setRegion(String region) {
		this.region = region;
	}
}
