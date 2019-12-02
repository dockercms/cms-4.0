package com.leimingtech.core.entity.member;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leimingtech.core.entity.IdEntity;

/**
 * @Title: Entity
 * @Description: 前台用户
 * @author
 * @date 2014-05-20 11:42:02
 * @version V1.0
 * 
 */
@Entity
@Table(name = "cms_member", schema = "")
@SuppressWarnings("serial")
public class MemberEntity extends IdEntity implements java.io.Serializable {
	 
	/**会员卡号*/
	private String cardId;
	/**会员卡访问路径*/
	private String cardPath;
	/** 用户名 */
	private java.lang.String username;
	/** 会员级别 */
	private java.lang.String memberlevel;
	/** 真实姓名 */
	private java.lang.String realname;
	/** 密码 */
	private java.lang.String password;
	/** 确认密码 */
	private java.lang.String repassword;
	/** 头像*/
	private java.lang.String faceImg;
	/** 邮箱 */
	private java.lang.String email;
	/** 出生日期 */
	private java.util.Date birthday;
	/** 性别 */
	private java.lang.Integer sex;//值为1是男
	/** 地址 */
	private java.lang.String address;
	/** qq */
	private java.lang.String qq;
	/** 手机 */
	private java.lang.String cellphone;
	/** 电话 */
	private java.lang.String telephone;
	/** 第三登录返回的UID */
	private java.lang.String thirdLogin_uid;
	/** 用户类型：新浪-sina,腾训-qq,本站会员为空 */
	private java.lang.String thirdType;
	/** 获取用户token */
	private java.lang.String thirdToken;
	
	/** 会员级别id */
	private java.lang.String membergroupsid;
	/** 地域id */
	private java.lang.Integer cityid;
	/** 城市 */
	private java.lang.String city;

	/** 昵称 */
	private String name;
	/** 积分 */
	private Integer point;
	/** msn */
	private String msn;
	/** 个人说明 */
	private String remark;
	/** 最后登录 */
	private Date lastlogin;
	/** 登录次数 */
	private Integer logincount;
	/** 消费积分 */
	private Integer mp;
	/** 是否已验证 */
	private Integer is_cheked;
	/** 注册IP */
	private String registerip;
	/**注册时间*/
	private java.util.Date createtime;
	/** 登录类型 */
	private java.lang.String loginType;

	@Column(name = "NAME", nullable = true, length = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "POINT", nullable = true, precision = 13, scale = 0)
	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@Column(name = "MSN", nullable = true, length = 255)
	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	@Column(name = "REMARK", nullable = true, length = 255)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "LASTLOGIN", nullable = true)
	public Date getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}

	@Column(name = "LOGINCOUNT", nullable = true, precision = 13, scale = 0)
	public Integer getLogincount() {
		return logincount;
	}

	public void setLogincount(Integer logincount) {
		this.logincount = logincount;
	}

	@Column(name = "MP", nullable = true, precision = 13, scale = 0)
	public Integer getMp() {
		return mp;
	}

	public void setMp(Integer mp) {
		this.mp = mp;
	}

	@Column(name = "IS_CHEKED", nullable = true, precision = 13, scale = 0)
	public Integer getIs_cheked() {
		return is_cheked;
	}

	public void setIs_cheked(Integer is_cheked) {
		this.is_cheked = is_cheked;
	}

	@Column(name = "REGISTERIP", nullable = true, length = 255)
	public String getRegisterip() {
		return registerip;
	}

	public void setRegisterip(String registerip) {
		this.registerip = registerip;
	}

	 

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 用户名
	 */
	@Column(name = "USERNAME", nullable = true, length = 255)
	public java.lang.String getUsername() {
		return this.username;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 用户名
	 */
	public void setUsername(java.lang.String username) {
		this.username = username;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 会员级别
	 */
	@Column(name = "MEMBERLEVEL", nullable = true, length = 255)
	public java.lang.String getMemberlevel() {
		return this.memberlevel;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 会员级别
	 */
	public void setMemberlevel(java.lang.String memberlevel) {
		this.memberlevel = memberlevel;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 真实姓名
	 */
	@Column(name = "REALNAME", nullable = true, length = 255)
	public java.lang.String getRealname() {
		return this.realname;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 真实姓名
	 */
	public void setRealname(java.lang.String realname) {
		this.realname = realname;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 密码
	 */
	@Column(name = "PASSWORD", nullable = true, length = 255)
	public java.lang.String getPassword() {
		return this.password;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 密码
	 */
	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 确认密码
	 */
	@Column(name = "REPASSWORD", nullable = true, length = 255)
	public java.lang.String getRepassword() {
		return this.repassword;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 确认密码
	 */
	public void setRepassword(java.lang.String repassword) {
		this.repassword = repassword;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 头像
	 */
	@Column(name = "FACEIMG", nullable = true, length = 255)
	public java.lang.String getFaceImg() {
		return this.faceImg;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 头像
	 */
	public void setFaceImg(java.lang.String faceImg) {
		this.faceImg = faceImg;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 邮箱
	 */
	@Column(name = "EMAIL", nullable = true, length = 255)
	public java.lang.String getEmail() {
		return this.email;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 邮箱
	 */
	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 出生日期
	 */
	@Column(name = "BIRTHDAY", nullable = true)
	public java.util.Date getBirthday() {
		return this.birthday;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 出生日期
	 */
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 性别
	 */
	@Column(name = "SEX", nullable = true)
	public java.lang.Integer getSex() {
		return this.sex;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 性别
	 */
	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 地址
	 */
	@Column(name = "ADDRESS", nullable = true, length = 255)
	public java.lang.String getAddress() {
		return this.address;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 地址
	 */
	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String qq
	 */
	@Column(name = "QQ", nullable = true, length = 255)
	public java.lang.String getQq() {
		return this.qq;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String qq
	 */
	public void setQq(java.lang.String qq) {
		this.qq = qq;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 手机
	 */
	@Column(name = "CELLPHONE", nullable = true, length = 255)
	public java.lang.String getCellphone() {
		return this.cellphone;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 手机
	 */
	public void setCellphone(java.lang.String cellphone) {
		this.cellphone = cellphone;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 电话
	 */
	@Column(name = "TELEPHONE", nullable = true, length = 255)
	public java.lang.String getTelephone() {
		return this.telephone;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 电话
	 */
	public void setTelephone(java.lang.String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 第三登录返回的UID
	 */
	@Column(name = "THIRDLOGIN_UID", nullable = true, length = 255)
	public java.lang.String getThirdLogin_uid() {
		return this.thirdLogin_uid;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 第三登录返回的UID
	 */
	public void setThirdLogin_uid(java.lang.String thirdLogin_uid) {
		this.thirdLogin_uid = thirdLogin_uid;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String  用户类型：新浪-sina,腾训-qq,本站会员为空 
	 */
	@Column(name = "THIRDTYPE", nullable = true, length = 255)
	public java.lang.String getThirdType() {
		return this.thirdType;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 用户类型：新浪-sina,腾训-qq,本站会员为空
	 */
	public void setThirdType(java.lang.String thirdType) {
		this.thirdType = thirdType;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 获取token
	 */
	@Column(name = "THIRDTOKEN", nullable = true, length = 255)
	public java.lang.String getThirdToken() {
		return this.thirdToken;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 获取用户token
	 */
	public void setThirdToken(java.lang.String thirdToken) {
		this.thirdToken = thirdToken;
	}


	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 会员级别id
	 */
	@Column(name = "MEMBERGROUPSID", nullable = true, precision = 10, scale = 0)
	public java.lang.String getMembergroupsid() {
		return this.membergroupsid;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 会员级别id
	 */
	public void setMembergroupsid(java.lang.String membergroupsid) {
		this.membergroupsid = membergroupsid;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 地域id
	 */
	@Column(name = "CITYID", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getCityid() {
		return this.cityid;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 地域id
	 */
	public void setCityid(java.lang.Integer cityid) {
		this.cityid = cityid;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 城市
	 */
	@Column(name = "CITY", nullable = true, length = 255)
	public java.lang.String getCity() {
		return this.city;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 城市
	 */
	public void setCity(java.lang.String city) {
		this.city = city;
	}

	@Column(name = "createtime", nullable = true)
	public java.util.Date getCreatetime() {
		return createtime;
	}
	
	/**
	 * 方法: 设置java.util.Date
	 * @param: java.lang.String 注册时间
	 */
	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 登录类型
	 */
	@Column(name = "LOGIN_TYPE", nullable = true, length = 255)
	public java.lang.String getLoginType() {
		return this.loginType;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 登录类型
	 */
	public void setLoginType(java.lang.String loginType) {
		this.loginType = loginType;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 会员卡号
	 */
	@Column(name = "CARD_ID", nullable = true, length = 50)
	public String getCardId() {
		return cardId;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 会员卡号
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 会员卡图
	 */
	@Column(name = "card_path", nullable = true, length = 255)
	public String getCardPath() {
		return cardPath;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 会员卡图
	 */
	public void setCardPath(String cardPath) {
		this.cardPath = cardPath;
	}
	
}
