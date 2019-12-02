package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 申请公开
 * @author 
 * @date 2016-04-05 16:57:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_apply_public", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ApplyPublicEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -4008525843106617506L;
	/** 申请人类型(0公民1 法人或其他组织) */
	private java.lang.String bjjSel;
	/** 公民姓名 */
	private java.lang.String grName;
	/** 公民工作单位 */
	private java.lang.String grDanwei;
	/** 公民证件名称 */
	private java.lang.String grZj;
	/** grHaoma */
	private java.lang.String grHaoma;
	/** 公民联系电话 */
	private java.lang.String grContact;
	/** 公民传真 */
	private java.lang.String grFax;
	/** 公民联系地址 */
	private java.lang.String grAddress;
	/** 公民邮政编码 */
	private java.lang.String grPost;
	/** 公民电子邮箱 */
	private java.lang.String grEmail;
	/** 法人名称 */
	private java.lang.String frName;
	/** 法人代表 */
	private java.lang.String frDaibiao;
	/** 法人联系人姓名 */
	private java.lang.String frXingming;
	/** 法人联系人电话 */
	private java.lang.String frContact;
	/** 法人邮政编码 */
	private java.lang.String frPost;
	/** 法人联系地址 */
	private java.lang.String frAddress;
	/** 法人传真 */
	private java.lang.String frFax;
	/** 法人电子邮箱 */
	private java.lang.String frEmail;
	/** 申请时间 */
	private java.util.Date datetime;
	/** 所需信息的内容描述 */
	private java.lang.String content;
	/** 所需信息的指定提供载体形式 */
	private java.lang.String xingshi;
	/** 所需信息的名称 */
	private java.lang.String xtName;
	/** 所需信息的索引号 */
	private java.lang.String xtSuyinhao;
	/** 所需信息的用途 */
	private java.lang.String xtYongtu;
	/** 是否申请减免费用 */
	private java.lang.String xtJmfy;
	/** 获取信息的方式（可多选） */
	private java.lang.String xtHuoqufs;
	/** 申请公开状态 */
	private java.lang.String isstat;
	/** 发布者IP */
	private java.lang.String publisherIp;
	/** 创建时间 */
	private java.util.Date createtime;
	/** 站点Id */
	private java.lang.String siteid;
	/** 备注说明 */
	private java.lang.String remarks;
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 申请人类型(0公民1 法人或其他组织)
	 */
	@Column(name = "BJJ_SEL", nullable = true, length = 10)
	public java.lang.String getBjjSel() {
		return this.bjjSel;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 申请人类型(0公民1 法人或其他组织)
	 */
	public void setBjjSel(java.lang.String bjjSel) {
		this.bjjSel = bjjSel;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 公民姓名
	 */
	@Column(name = "GR_NAME", nullable = true, length = 50)
	public java.lang.String getGrName() {
		return this.grName;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 公民姓名
	 */
	public void setGrName(java.lang.String grName) {
		this.grName = grName;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 公民工作单位
	 */
	@Column(name = "GR_DANWEI", nullable = true, length = 100)
	public java.lang.String getGrDanwei() {
		return this.grDanwei;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 公民工作单位
	 */
	public void setGrDanwei(java.lang.String grDanwei) {
		this.grDanwei = grDanwei;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 公民证件名称
	 */
	@Column(name = "GR_ZJ", nullable = true, length = 100)
	public java.lang.String getGrZj() {
		return this.grZj;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 公民证件名称
	 */
	public void setGrZj(java.lang.String grZj) {
		this.grZj = grZj;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String grHaoma
	 */
	@Column(name = "GR_HAOMA", nullable = true, length = 100)
	public java.lang.String getGrHaoma() {
		return this.grHaoma;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String grHaoma
	 */
	public void setGrHaoma(java.lang.String grHaoma) {
		this.grHaoma = grHaoma;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 公民联系电话
	 */
	@Column(name = "GR_CONTACT", nullable = true, length = 100)
	public java.lang.String getGrContact() {
		return this.grContact;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 公民联系电话
	 */
	public void setGrContact(java.lang.String grContact) {
		this.grContact = grContact;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 公民传真
	 */
	@Column(name = "GR_FAX", nullable = true, length = 100)
	public java.lang.String getGrFax() {
		return this.grFax;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 公民传真
	 */
	public void setGrFax(java.lang.String grFax) {
		this.grFax = grFax;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 公民联系地址
	 */
	@Column(name = "GR_ADDRESS", nullable = true, length = 100)
	public java.lang.String getGrAddress() {
		return this.grAddress;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 公民联系地址
	 */
	public void setGrAddress(java.lang.String grAddress) {
		this.grAddress = grAddress;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 公民邮政编码
	 */
	@Column(name = "GR_POST", nullable = true, length = 20)
	public java.lang.String getGrPost() {
		return this.grPost;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 公民邮政编码
	 */
	public void setGrPost(java.lang.String grPost) {
		this.grPost = grPost;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 公民电子邮箱
	 */
	@Column(name = "GR_EMAIL", nullable = true, length = 50)
	public java.lang.String getGrEmail() {
		return this.grEmail;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 公民电子邮箱
	 */
	public void setGrEmail(java.lang.String grEmail) {
		this.grEmail = grEmail;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 法人名称
	 */
	@Column(name = "FR_NAME", nullable = true, length = 50)
	public java.lang.String getFrName() {
		return this.frName;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 法人名称
	 */
	public void setFrName(java.lang.String frName) {
		this.frName = frName;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 法人代表
	 */
	@Column(name = "FR_DAIBIAO", nullable = true, length = 50)
	public java.lang.String getFrDaibiao() {
		return this.frDaibiao;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 法人代表
	 */
	public void setFrDaibiao(java.lang.String frDaibiao) {
		this.frDaibiao = frDaibiao;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 法人联系人姓名
	 */
	@Column(name = "FR_XINGMING", nullable = true, length = 50)
	public java.lang.String getFrXingming() {
		return this.frXingming;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 法人联系人姓名
	 */
	public void setFrXingming(java.lang.String frXingming) {
		this.frXingming = frXingming;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 法人联系人电话
	 */
	@Column(name = "FR_CONTACT", nullable = true, length = 50)
	public java.lang.String getFrContact() {
		return this.frContact;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 法人联系人电话
	 */
	public void setFrContact(java.lang.String frContact) {
		this.frContact = frContact;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 法人邮政编码
	 */
	@Column(name = "FR_POST", nullable = true, length = 50)
	public java.lang.String getFrPost() {
		return this.frPost;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 法人邮政编码
	 */
	public void setFrPost(java.lang.String frPost) {
		this.frPost = frPost;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 法人联系地址
	 */
	@Column(name = "FR_ADDRESS", nullable = true, length = 100)
	public java.lang.String getFrAddress() {
		return this.frAddress;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 法人联系地址
	 */
	public void setFrAddress(java.lang.String frAddress) {
		this.frAddress = frAddress;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 法人传真
	 */
	@Column(name = "FR_FAX", nullable = true, length = 50)
	public java.lang.String getFrFax() {
		return this.frFax;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 法人传真
	 */
	public void setFrFax(java.lang.String frFax) {
		this.frFax = frFax;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 法人电子邮箱
	 */
	@Column(name = "FR_EMAIL", nullable = true, length = 50)
	public java.lang.String getFrEmail() {
		return this.frEmail;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 法人电子邮箱
	 */
	public void setFrEmail(java.lang.String frEmail) {
		this.frEmail = frEmail;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 申请时间
	 */
	@Column(name = "DATETIME", nullable = true)
	public java.util.Date getDatetime() {
		return this.datetime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 申请时间
	 */
	public void setDatetime(java.util.Date datetime) {
		this.datetime = datetime;
	}
	
	/**
	 * 方法: 取得java.lang.Object
	 *
	 * @return: java.lang.Object 所需信息的内容描述
	 */
	@Column(name = "CONTENT", nullable = true)
	public java.lang.String getContent() {
		return this.content;
	}

	

	/**
	 * 方法: 设置java.lang.Object
	 *
	 * @param: java.lang.Object 所需信息的内容描述
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 所需信息的指定提供载体形式
	 */
	@Column(name = "XINGSHI", nullable = true, length = 100)
	public java.lang.String getXingshi() {
		return this.xingshi;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 所需信息的指定提供载体形式
	 */
	public void setXingshi(java.lang.String xingshi) {
		this.xingshi = xingshi;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 所需信息的名称
	 */
	@Column(name = "XT_NAME", nullable = true, length = 100)
	public java.lang.String getXtName() {
		return this.xtName;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 所需信息的名称
	 */
	public void setXtName(java.lang.String xtName) {
		this.xtName = xtName;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 所需信息的索引号
	 */
	@Column(name = "XT_SUYINHAO", nullable = true, length = 100)
	public java.lang.String getXtSuyinhao() {
		return this.xtSuyinhao;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 所需信息的索引号
	 */
	public void setXtSuyinhao(java.lang.String xtSuyinhao) {
		this.xtSuyinhao = xtSuyinhao;
	}
	
	/**
	 * 方法: 取得java.lang.Object
	 *
	 * @return: java.lang.Object 所需信息的用途
	 */
	@Column(name = "XT_YONGTU", nullable = true)
	public java.lang.String getXtYongtu() {
		return this.xtYongtu;
	}

	/**
	 * 方法: 设置java.lang.Object
	 *
	 * @param: java.lang.Object 所需信息的用途
	 */
	public void setXtYongtu(java.lang.String xtYongtu) {
		this.xtYongtu = xtYongtu;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 是否申请减免费用
	 */
	@Column(name = "XT_JMFY", nullable = true, length = 10)
	public java.lang.String getXtJmfy() {
		return this.xtJmfy;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 是否申请减免费用
	 */
	public void setXtJmfy(java.lang.String xtJmfy) {
		this.xtJmfy = xtJmfy;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 获取信息的方式（可多选）
	 */
	@Column(name = "XT_HUOQUFS", nullable = true, length = 50)
	public java.lang.String getXtHuoqufs() {
		return this.xtHuoqufs;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 获取信息的方式（可多选）
	 */
	public void setXtHuoqufs(java.lang.String xtHuoqufs) {
		this.xtHuoqufs = xtHuoqufs;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 申请公开状态
	 */
	@Column(name = "ISSTAT", nullable = true, length = 50)
	public java.lang.String getIsstat() {
		return this.isstat;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 申请公开状态
	 */
	public void setIsstat(java.lang.String isstat) {
		this.isstat = isstat;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 发布者IP
	 */
	@Column(name = "PUBLISHER_IP", nullable = true, length = 50)
	public java.lang.String getPublisherIp() {
		return this.publisherIp;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 发布者IP
	 */
	public void setPublisherIp(java.lang.String publisherIp) {
		this.publisherIp = publisherIp;
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
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 站点Id
	 */
	@Column(name = "SITEID", nullable = true, length = 32)
	public java.lang.String getSiteid() {
		return this.siteid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 站点Id
	 */
	public void setSiteid(java.lang.String siteid) {
		this.siteid = siteid;
	}
	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 备注说明
	 */
	@Column(name = "REMARKS", nullable = true)
	public java.lang.String getRemarks() {
		return remarks;
	}

	public void setRemarks(java.lang.String remarks) {
		this.remarks = remarks;
	}
}
