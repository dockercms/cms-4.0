package com.leimingtech.cms.entity.acquisition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 采集信息
 * @author zhangdaihao
 * @date 2014-04-16 11:37:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_acquisition_content", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AcquisitionContentEntity extends IdEntity implements java.io.Serializable {
	
	/**标题*/
	private java.lang.String title;
	/**简短标题*/
	private java.lang.String shortTitle;
	/**作者*/
	private java.lang.String author;
	/**来源*/
	private java.lang.String origin;
	/**来源链接*/
	private java.lang.String originUrl;
	/**描述*/
	private java.lang.String description;
	/**发布日期*/
	private java.util.Date releaseDate;
	/**媒体路径*/
	private java.lang.String mediaPath;
	/**媒体类型*/
	private java.lang.String mediaType;
	/**标题颜色*/
	private java.lang.String titleColor;
	/**是否加粗*/
	private java.lang.Integer isBold;
	/**标题图片*/
	private java.lang.String titleImg;
	/**内容图片*/
	private java.lang.String contentImg;
	/**类型图片*/
	private java.lang.String typeImg;
	/**外部链接*/
	private java.lang.String link;
	/**指定模板*/
	private java.lang.String tplContent;
	/**需要重新生成静态页*/
	private java.lang.Integer needRegenerate;
	/**文章内容*/
	private java.lang.String txt;

	/**创建时间*/
	private java.util.Date createdtime;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文章内容
	 */
	@Column(name ="txt",nullable=true)
	public java.lang.String getTxt(){
		return this.txt;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文章内容
	 */
	public void setTxt(java.lang.String txt){
		this.txt = txt;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=false,length=150)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  简短标题
	 */
	@Column(name ="SHORT_TITLE",nullable=true,length=150)
	public java.lang.String getShortTitle(){
		return this.shortTitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  简短标题
	 */
	public void setShortTitle(java.lang.String shortTitle){
		this.shortTitle = shortTitle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  作者
	 */
	@Column(name ="AUTHOR",nullable=true,length=100)
	public java.lang.String getAuthor(){
		return this.author;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  作者
	 */
	public void setAuthor(java.lang.String author){
		this.author = author;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  来源
	 */
	@Column(name ="ORIGIN",nullable=true,length=100)
	public java.lang.String getOrigin(){
		return this.origin;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  来源
	 */
	public void setOrigin(java.lang.String origin){
		this.origin = origin;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  来源链接
	 */
	@Column(name ="ORIGIN_URL",nullable=true,length=255)
	public java.lang.String getOriginUrl(){
		return this.originUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  来源链接
	 */
	public void setOriginUrl(java.lang.String originUrl){
		this.originUrl = originUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述
	 */
	@Column(name ="DESCRIPTION",nullable=true,length=255)
	public java.lang.String getDescription(){
		return this.description;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setDescription(java.lang.String description){
		this.description = description;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发布日期
	 */
	@Column(name ="RELEASE_DATE",nullable=true)
	public java.util.Date getReleaseDate(){
		return this.releaseDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发布日期
	 */
	public void setReleaseDate(java.util.Date releaseDate){
		this.releaseDate = releaseDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  媒体路径
	 */
	@Column(name ="MEDIA_PATH",nullable=true,length=255)
	public java.lang.String getMediaPath(){
		return this.mediaPath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  媒体路径
	 */
	public void setMediaPath(java.lang.String mediaPath){
		this.mediaPath = mediaPath;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  媒体类型
	 */
	@Column(name ="MEDIA_TYPE",nullable=true,length=20)
	public java.lang.String getMediaType(){
		return this.mediaType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  媒体类型
	 */
	public void setMediaType(java.lang.String mediaType){
		this.mediaType = mediaType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题颜色
	 */
	@Column(name ="TITLE_COLOR",nullable=true,length=10)
	public java.lang.String getTitleColor(){
		return this.titleColor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题颜色
	 */
	public void setTitleColor(java.lang.String titleColor){
		this.titleColor = titleColor;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否加粗
	 */
	@Column(name ="IS_BOLD",nullable=true,precision=3,scale=0)
	public java.lang.Integer getIsBold(){
		return this.isBold;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否加粗
	 */
	public void setIsBold(java.lang.Integer isBold){
		this.isBold = isBold;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题图片
	 */
	@Column(name ="TITLE_IMG",nullable=true,length=100)
	public java.lang.String getTitleImg(){
		return this.titleImg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题图片
	 */
	public void setTitleImg(java.lang.String titleImg){
		this.titleImg = titleImg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容图片
	 */
	@Column(name ="CONTENT_IMG",nullable=true,length=100)
	public java.lang.String getContentImg(){
		return this.contentImg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容图片
	 */
	public void setContentImg(java.lang.String contentImg){
		this.contentImg = contentImg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型图片
	 */
	@Column(name ="TYPE_IMG",nullable=true,length=100)
	public java.lang.String getTypeImg(){
		return this.typeImg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型图片
	 */
	public void setTypeImg(java.lang.String typeImg){
		this.typeImg = typeImg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  外部链接
	 */
	@Column(name ="LINK",nullable=true,length=255)
	public java.lang.String getLink(){
		return this.link;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  外部链接
	 */
	public void setLink(java.lang.String link){
		this.link = link;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  指定模板
	 */
	@Column(name ="TPL_CONTENT",nullable=true,length=100)
	public java.lang.String getTplContent(){
		return this.tplContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  指定模板
	 */
	public void setTplContent(java.lang.String tplContent){
		this.tplContent = tplContent;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  需要重新生成静态页
	 */
	@Column(name ="NEED_REGENERATE",nullable=true,precision=3,scale=0)
	public java.lang.Integer getNeedRegenerate(){
		return this.needRegenerate;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  需要重新生成静态页
	 */
	public void setNeedRegenerate(java.lang.Integer needRegenerate){
		this.needRegenerate = needRegenerate;
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
