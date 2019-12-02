package com.leimingtech.platform.entity.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;


/**   
 * @Title: Entity
 * @Description: 公司信息
 * @author zhangdaihao
 * @date 2014-04-03 17:42:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_company", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CompanyEntity extends IdEntity implements java.io.Serializable {
	 
	/**pid*/
	private java.lang.String pid;
	/**标记*/
	private java.lang.String token;
	/**公司名称*/
	private java.lang.String companyName;
	/**公司简称*/
	private java.lang.String companyShortname;
	/**电话*/
	private java.lang.String companyCell;
	/**手机*/
	private java.lang.String companyPhone;
	/**地址*/
	private java.lang.String companyUrl;
	/**纬度*/
	private java.lang.Double latitude;
	/**经度*/
	private java.lang.Double longitude;
	/**简介*/
	private java.lang.String intro;
	/**排列*/
	private java.lang.String taxis;
	/**分支*/
	private java.lang.String isbranch;
	/**Logo地址*/
	private java.lang.String logourl;
	

	/**创建时间*/
	private java.util.Date createdtime;
	
	
	 
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  pid
	 */
	@Column(name ="PID",nullable=true,length=32)
	public java.lang.String getPid(){
		return this.pid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  pid
	 */
	public void setPid(java.lang.String pid){
		this.pid = pid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标记
	 */
	@Column(name ="TOKEN",nullable=true,length=250)
	public java.lang.String getToken(){
		return this.token;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标记
	 */
	public void setToken(java.lang.String token){
		this.token = token;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公司名称
	 */
	@Column(name ="COMPANY_NAME",nullable=true,length=250)
	public java.lang.String getCompanyName(){
		return this.companyName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公司名称
	 */
	public void setCompanyName(java.lang.String companyName){
		this.companyName = companyName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公司简称
	 */
	@Column(name ="COMPANY_SHORTNAME",nullable=true,length=250)
	public java.lang.String getCompanyShortname(){
		return this.companyShortname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公司简称
	 */
	public void setCompanyShortname(java.lang.String companyShortname){
		this.companyShortname = companyShortname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="COMPANY_CELL",nullable=true,length=250)
	public java.lang.String getCompanyCell(){
		return this.companyCell;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setCompanyCell(java.lang.String companyCell){
		this.companyCell = companyCell;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机
	 */
	@Column(name ="COMPANY_PHONE",nullable=true,length=250)
	public java.lang.String getCompanyPhone(){
		return this.companyPhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机
	 */
	public void setCompanyPhone(java.lang.String companyPhone){
		this.companyPhone = companyPhone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地址
	 */
	@Column(name ="COMPANY_URL",nullable=true,length=250)
	public java.lang.String getCompanyUrl(){
		return this.companyUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地址
	 */
	public void setCompanyUrl(java.lang.String companyUrl){
		this.companyUrl = companyUrl;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  纬度
	 */
	@Column(name ="LATITUDE",nullable=true,precision=22)
	public java.lang.Double getLatitude(){
		return this.latitude;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  纬度
	 */
	public void setLatitude(java.lang.Double latitude){
		this.latitude = latitude;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  经度
	 */
	@Column(name ="LONGITUDE",nullable=true,precision=22)
	public java.lang.Double getLongitude(){
		return this.longitude;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  经度
	 */
	public void setLongitude(java.lang.Double longitude){
		this.longitude = longitude;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  简介
	 */
	@Column(name ="INTRO",nullable=true,length=4000)
	public java.lang.String getIntro(){
		return this.intro;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  简介
	 */
	public void setIntro(java.lang.String intro){
		this.intro = intro;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排列
	 */
	@Column(name ="TAXIS",nullable=true,length=250)
	public java.lang.String getTaxis(){
		return this.taxis;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排列
	 */
	public void setTaxis(java.lang.String taxis){
		this.taxis = taxis;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支
	 */
	@Column(name ="ISBRANCH",nullable=true,length=250)
	public java.lang.String getIsbranch(){
		return this.isbranch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支
	 */
	public void setIsbranch(java.lang.String isbranch){
		this.isbranch = isbranch;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  Logo地址
	 */
	@Column(name ="LOGOURL",nullable=true,length=250)
	public java.lang.String getLogourl(){
		return this.logourl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  Logo地址
	 */
	public void setLogourl(java.lang.String logourl){
		this.logourl = logourl;
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
