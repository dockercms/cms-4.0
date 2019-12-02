package com.leimingtech.cms.entity.advertising;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 广告管理
 * @author zhangdaihao
 * @date 2014-04-16 17:27:47
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_advertising", schema = "")
@SuppressWarnings("serial")
public class AdvertisingEntity extends IdEntity implements java.io.Serializable {
	
	/**所属网站id*/
	private java.lang.String siteId;
	/**广告名称*/
	private java.lang.String adName;
	/**广告类型*/
	private java.lang.String category;
	/**广告代码*/
	private java.lang.String adCode;
	/**广告权重*/
	private java.lang.Integer adWeight;
	/**展现次数*/
	private java.lang.Integer displayCount;
	/**点击次数*/
	private java.lang.Integer clickCount;
	/**开始时间*/
	private java.util.Date startTime;
	/**结束时间*/
	private java.util.Date endTime;
	/**是否启用*/
	private java.lang.String isEnabled;
	/**图片上传地址*/
	private java.lang.String imgUrl;
	/**度高*/
	private java.lang.String iheight;
	/**度宽*/
	private java.lang.String iweight;
	/**链接地址*/
	private java.lang.String urladress;
	/**接链提示*/
	private java.lang.String urlpoint;
	/**接链目标(0新窗口、1原窗口)*/
	private java.lang.Integer urltarget;
	/**文字内容*/
	private java.lang.String wordcontent;
	/**字文大小*/
	private java.lang.String wordsize;
	/**文字颜色*/
	private java.lang.String wordcolor;
	
	/**
	 * 广告分组
	 */
	private AdvertisingSpaceEntity advertisingSpace;
	/**创建时间*/
	private java.util.Date createdtime;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建时间
	 */
	@Column(name = "CREATEDTIME", nullable = true)
	public java.util.Date getCreatedtime() {
		return createdtime;
	}
	/**
	 *方法: 设置java.lang.String
	 *@return: java.lang.String  创建时间
	 */
	public void setCreatedtime(java.util.Date createdtime) {
		this.createdtime = createdtime;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GID")
	public AdvertisingSpaceEntity getAdvertisingSpace() {
		return advertisingSpace;
	}

	public void setAdvertisingSpace(AdvertisingSpaceEntity advertisingSpace) {
		this.advertisingSpace = advertisingSpace;
	}
	
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属网站id
	 */
	@Column(name ="SITE_ID",nullable=true,precision=10,scale=0)
	public java.lang.String getSiteId(){
		return this.siteId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属网站id
	 */
	public void setSiteId(java.lang.String siteId){
		this.siteId = siteId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  广告名称
	 */
	@Column(name ="AD_NAME",nullable=true,length=100)
	public java.lang.String getAdName(){
		return this.adName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  广告名称
	 */
	public void setAdName(java.lang.String adName){
		this.adName = adName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  广告类型
	 */
	@Column(name ="CATEGORY",nullable=true,length=50)
	public java.lang.String getCategory(){
		return this.category;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  广告类型
	 */
	public void setCategory(java.lang.String category){
		this.category = category;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  广告代码
	 */
	@Column(name ="AD_CODE",nullable=true,length=1000)
	public java.lang.String getAdCode(){
		return this.adCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  广告代码
	 */
	public void setAdCode(java.lang.String adCode){
		this.adCode = adCode;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  广告权重
	 */
	@Column(name ="AD_WEIGHT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getAdWeight(){
		return this.adWeight;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  广告权重
	 */
	public void setAdWeight(java.lang.Integer adWeight){
		this.adWeight = adWeight;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  展现次数
	 */
	@Column(name ="DISPLAY_COUNT",nullable=true,precision=19,scale=0)
	public java.lang.Integer getDisplayCount(){
		return this.displayCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  展现次数
	 */
	public void setDisplayCount(java.lang.Integer displayCount){
		this.displayCount = displayCount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  点击次数
	 */
	@Column(name ="CLICK_COUNT",nullable=true,precision=19,scale=0)
	public java.lang.Integer getClickCount(){
		return this.clickCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  点击次数
	 */
	public void setClickCount(java.lang.Integer clickCount){
		this.clickCount = clickCount;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始时间
	 */
	@Column(name ="START_TIME",nullable=true)
	public java.util.Date getStartTime(){
		return this.startTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开始时间
	 */
	public void setStartTime(java.util.Date startTime){
		this.startTime = startTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束时间
	 */
	@Column(name ="END_TIME",nullable=true)
	public java.util.Date getEndTime(){
		return this.endTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结束时间
	 */
	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否启用
	 */
	@Column(name ="IS_ENABLED",nullable=true,length=1)
	public java.lang.String getIsEnabled(){
		return this.isEnabled;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否启用
	 */
	public void setIsEnabled(java.lang.String isEnabled){
		this.isEnabled = isEnabled;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片上传地址
	 */
	@Column(name ="IMG_URL",nullable=true,length=255)
	public java.lang.String getImgUrl(){
		return this.imgUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片上传地址
	 */
	public void setImgUrl(java.lang.String imgUrl){
		this.imgUrl = imgUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  度高
	 */
	@Column(name ="IHEIGHT",nullable=true,length=30)
	public java.lang.String getIheight(){
		return this.iheight;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  度高
	 */
	public void setIheight(java.lang.String iheight){
		this.iheight = iheight;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  度宽
	 */
	@Column(name ="IWEIGHT",nullable=true,length=30)
	public java.lang.String getIweight(){
		return this.iweight;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  度宽
	 */
	public void setIweight(java.lang.String iweight){
		this.iweight = iweight;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  链接地址
	 */
	@Column(name ="URLADRESS",nullable=true,length=50)
	public java.lang.String getUrladress(){
		return this.urladress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  链接地址
	 */
	public void setUrladress(java.lang.String urladress){
		this.urladress = urladress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接链提示
	 */
	@Column(name ="URLPOINT",nullable=true,length=50)
	public java.lang.String getUrlpoint(){
		return this.urlpoint;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接链提示
	 */
	public void setUrlpoint(java.lang.String urlpoint){
		this.urlpoint = urlpoint;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  (0新窗口、1原窗口)
	 */
	@Column(name ="URLTARGET",nullable=true,precision=10,scale=0)
	public java.lang.Integer getUrltarget(){
		return this.urltarget;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  (0新窗口、1原窗口)
	 */
	public void setUrltarget(java.lang.Integer urltarget){
		this.urltarget = urltarget;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文字内容
	 */
	@Column(name ="WORDCONTENT",nullable=true,length=50)
	public java.lang.String getWordcontent(){
		return this.wordcontent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文字内容
	 */
	public void setWordcontent(java.lang.String wordcontent){
		this.wordcontent = wordcontent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  字文大小
	 */
	@Column(name ="WORDSIZE",nullable=true,length=30)
	public java.lang.String getWordsize(){
		return this.wordsize;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  字文大小
	 */
	public void setWordsize(java.lang.String wordsize){
		this.wordsize = wordsize;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文字颜色
	 */
	@Column(name ="WORDCOLOR",nullable=true,length=50)
	public java.lang.String getWordcolor(){
		return this.wordcolor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文字颜色
	 */
	public void setWordcolor(java.lang.String wordcolor){
		this.wordcolor = wordcolor;
	}
}
