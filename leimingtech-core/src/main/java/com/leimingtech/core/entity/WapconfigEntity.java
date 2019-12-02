package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: WAP配置
 * @author 
 * @date 2014-06-25 16:03:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_wap_config", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class WapconfigEntity extends IdEntity implements java.io.Serializable {
	
	/**站点id*/
	private java.lang.String siteid;
	/**开启状态*/
	private java.lang.String wapstatus;
	/**网站名称*/
	private java.lang.String wapname;
	/**网站LOGO*/
	private java.lang.String waplogo;
	/**首页栏目内容条数*/
	private java.lang.String indexcolumncount;
	/**首页显示最低权重*/
	private java.lang.String indexminweights;
	/**栏目首页子栏目显示条数*/
	private java.lang.String columncount;
	/**列表页条数*/
	private java.lang.String listcount;
	/**栏目显示最低权重*/
	private java.lang.String columnminweights;
	/**评论列表页条数*/
	private java.lang.String evaluatelistcount;
	/**首页模板*/
	private java.lang.String indextemplate;
	/**列表页模板*/
	private java.lang.String listtemplate;
	/**文章内容模板*/
	private java.lang.String contenttemplate;
	/**组图内容模板*/
	private java.lang.String picturestemplate;
	/**评论模板*/
	private java.lang.String evaluatetemplate;
	/**视频模板*/
	private java.lang.String videoTemplate;
	/**调查模板*/
	private java.lang.String surveyTemplate;
	/**投票模板*/
	private java.lang.String voteTemplate;
	
	/**创建时间*/
	private java.util.Date createdtime;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点id
	 */
	@Column(name ="SITEID",nullable=true,length=32)
	public java.lang.String getSiteid(){
		return this.siteid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点id
	 */
	public void setSiteid(java.lang.String siteid){
		this.siteid = siteid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  开启状态
	 */
	@Column(name ="WAPSTATUS",nullable=true,length=100)
	public java.lang.String getWapstatus(){
		return this.wapstatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  开启状态
	 */
	public void setWapstatus(java.lang.String wapstatus){
		this.wapstatus = wapstatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  网站名称
	 */
	@Column(name ="WAPNAME",nullable=true,length=100)
	public java.lang.String getWapname(){
		return this.wapname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  网站名称
	 */
	public void setWapname(java.lang.String wapname){
		this.wapname = wapname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  网站LOGO
	 */
	@Column(name ="WAPLOGO",nullable=true,length=255)
	public java.lang.String getWaplogo(){
		return this.waplogo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  网站LOGO
	 */
	public void setWaplogo(java.lang.String waplogo){
		this.waplogo = waplogo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  首页栏目内容条数
	 */
	@Column(name ="INDEXCOLUMNCOUNT",nullable=true,length=50)
	public java.lang.String getIndexcolumncount(){
		return this.indexcolumncount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  首页栏目内容条数
	 */
	public void setIndexcolumncount(java.lang.String indexcolumncount){
		this.indexcolumncount = indexcolumncount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  首页显示最低权重
	 */
	@Column(name ="INDEXMINWEIGHTS",nullable=true,length=50)
	public java.lang.String getIndexminweights(){
		return this.indexminweights;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  首页显示最低权重
	 */
	public void setIndexminweights(java.lang.String indexminweights){
		this.indexminweights = indexminweights;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  栏目首页子栏目显示条数
	 */
	@Column(name ="COLUMNCOUNT",nullable=true,length=50)
	public java.lang.String getColumncount(){
		return this.columncount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  栏目首页子栏目显示条数
	 */
	public void setColumncount(java.lang.String columncount){
		this.columncount = columncount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  列表页条数
	 */
	@Column(name ="LISTCOUNT",nullable=true,length=50)
	public java.lang.String getListcount(){
		return this.listcount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  列表页条数
	 */
	public void setListcount(java.lang.String listcount){
		this.listcount = listcount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  栏目显示最低权重
	 */
	@Column(name ="COLUMNMINWEIGHTS",nullable=true,length=50)
	public java.lang.String getColumnminweights(){
		return this.columnminweights;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  栏目显示最低权重
	 */
	public void setColumnminweights(java.lang.String columnminweights){
		this.columnminweights = columnminweights;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  评论列表页条数
	 */
	@Column(name ="EVALUATELISTCOUNT",nullable=true,length=50)
	public java.lang.String getEvaluatelistcount(){
		return this.evaluatelistcount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评论列表页条数
	 */
	public void setEvaluatelistcount(java.lang.String evaluatelistcount){
		this.evaluatelistcount = evaluatelistcount;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  首页模板
	 */
	@Column(name ="INDEXTEMPLATE",nullable=true,length=50)
	public java.lang.String getIndextemplate() {
		return indextemplate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  首页模板
	 */
	public void setIndextemplate(java.lang.String indextemplate) {
		this.indextemplate = indextemplate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  列表页模板
	 */
	@Column(name ="LISTTEMPLATE",nullable=true,length=50)
	public java.lang.String getListtemplate() {
		return listtemplate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String 列表页模板
	 */
	public void setListtemplate(java.lang.String listtemplate) {
		this.listtemplate = listtemplate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容页模板
	 */
	@Column(name ="CONTENTTEMPLATE",nullable=true,length=50)
	public java.lang.String getContenttemplate() {
		return contenttemplate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String 内容页模板
	 */
	public void setContenttemplate(java.lang.String contenttemplate) {
		this.contenttemplate = contenttemplate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  组图模板
	 */
	@Column(name ="PICTURESTEMPLATE",nullable=true,length=50)
	public java.lang.String getPicturestemplate() {
		return picturestemplate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String 组图模板
	 */
	public void setPicturestemplate(java.lang.String picturestemplate) {
		this.picturestemplate = picturestemplate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  评价模板
	 */
	@Column(name ="EVALUATETEMPLATE",nullable=true,length=50)
	public java.lang.String getEvaluatetemplate() {
		return evaluatetemplate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String 评价模板
	 */
	public void setEvaluatetemplate(java.lang.String evaluatetemplate) {
		this.evaluatetemplate = evaluatetemplate;
	}
	@Column(name ="video_template",nullable=true,length=50)
	public java.lang.String getVideoTemplate() {
		return videoTemplate;
	}

	public void setVideoTemplate(java.lang.String videoTemplate) {
		this.videoTemplate = videoTemplate;
	}
	@Column(name ="surver_template",nullable=true,length=50)
	public java.lang.String getSurveyTemplate() {
		return surveyTemplate;
	}

	public void setSurveyTemplate(java.lang.String surveyTemplate) {
		this.surveyTemplate = surveyTemplate;
	}
	@Column(name ="vote_template",nullable=true,length=50)
	public java.lang.String getVoteTemplate() {
		return voteTemplate;
	}

	public void setVoteTemplate(java.lang.String voteTemplate) {
		this.voteTemplate = voteTemplate;
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
