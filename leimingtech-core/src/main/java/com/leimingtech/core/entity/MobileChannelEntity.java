package com.leimingtech.core.entity;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 频道管理
 * @author leiming
 * @date 2014-06-25 10:16:45
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_mobile_channel", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class MobileChannelEntity extends IdEntity implements java.io.Serializable {

	/**频道名称*/
	private java.lang.String name;
	/**栏目ID*/
	private java.lang.String catId;
	/**图标*/
	private java.lang.String channelIco;
	/**显示幻灯片(0代表 禁用   1代表 启用)*/
	private java.lang.Integer slideShow;
	/**幻灯片数量*/
	private java.lang.Integer slideNumber;
	/**状态(0代表 禁用   1代表 启用)*/
	private java.lang.Integer channelStates;
	/**头条频道(0代表 否   1代表 是)*/
	private java.lang.Integer channelTop;
	/**浏览量*/
	private java.lang.Integer pv;
	/**评论数*/
	private java.lang.Integer comments;
	/**发稿量*/
	private java.lang.Integer issuedAmount;
	/**创建时间*/
	private java.util.Date created;
	/**创建人*/
	private java.lang.String createdby;
	/**修改时间*/
	private java.util.Date modified;
	/**修改人*/
	private java.lang.String modifiedby;
	/**排序*/
	private java.lang.Integer sort;
	/**级别*/
	private java.lang.Integer levels;
	/**路径*/
	private java.lang.String pathids;
	/**工作流ID*/
	private java.lang.String workflowid;
	/**频道类型*/
	private java.lang.String channelType;
	/** 生成路径 */
	private java.lang.String path;
	/** url */
	private java.lang.String url;
	/** 文章状态 */
	private java.lang.String constants;
	private  MobileChannelEntity  mobileChannel ;
	private  List<MobileChannelEntity> mobileChannels=new ArrayList<MobileChannelEntity>();
	
	/**所有父频道id*/
	private String parentids;
	/**是否是热门频道（0代表否1代表是）**/
	private java.lang.Integer channelHot;
	
	/**站点id*/
	private String siteid;
	
	@Column(name = "SITE_ID", nullable = true, precision = 32, scale = 0)
	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	/**所有父频道id*/
	@Column(name ="parentids",nullable=true,length=255)
	public String getParentids() {
		return parentids;
	}

	/**所有父频道id*/
	public void setParentids(String parentids) {
		this.parentids = parentids;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	public MobileChannelEntity getMobileChannel() {
		return this.mobileChannel;
	}

	public void setMobileChannel(MobileChannelEntity mobileChannel) {
		this.mobileChannel = mobileChannel;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "mobileChannel")
	public List<MobileChannelEntity> getMobileChannels() {
		return mobileChannels;
	}
	public void setMobileChannels(List<MobileChannelEntity> mobileChannels) {
		this.mobileChannels = mobileChannels;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  频道名称
	 */
	
	
	@Column(name ="NAME",nullable=true,length=50)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  频道名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  栏目ID
	 */
	
	
	@Column(name ="CAT_ID",nullable=true,length=255)
	public java.lang.String getCatId(){
		return this.catId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  栏目ID
	 */
	public void setCatId(java.lang.String catId){
		this.catId = catId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图标
	 */
	
	
	@Column(name ="CHANNEL_ICO",nullable=true,length=255)
	public java.lang.String getChannelIco(){
		return this.channelIco;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图标
	 */
	public void setChannelIco(java.lang.String channelIco){
		this.channelIco = channelIco;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  显示幻灯片(0代表 禁用   1代表 启用)
	 */
	
	
	@Column(name ="SLIDE_SHOW",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSlideShow(){
		return this.slideShow;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  显示幻灯片(0代表 禁用   1代表 启用)
	 */
	public void setSlideShow(java.lang.Integer slideShow){
		this.slideShow = slideShow;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  幻灯片数量
	 */
	
	
	@Column(name ="SLIDE_NUMBER",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSlideNumber(){
		return this.slideNumber;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  幻灯片数量
	 */
	public void setSlideNumber(java.lang.Integer slideNumber){
		this.slideNumber = slideNumber;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  状态(0代表 禁用   1代表 启用)
	 */
	
	
	@Column(name ="CHANNEL_STATES",nullable=true,precision=10,scale=0)
	public java.lang.Integer getChannelStates(){
		return this.channelStates;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  状态(0代表 禁用   1代表 启用)
	 */
	public void setChannelStates(java.lang.Integer channelStates){
		this.channelStates = channelStates;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  头条频道(0代表 否   1代表 是)
	 */
	
	
	@Column(name ="CHANNEL_TOP",nullable=true,precision=10,scale=0)
	public java.lang.Integer getChannelTop(){
		return this.channelTop;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  头条频道(0代表 否   1代表 是)
	 */
	public void setChannelTop(java.lang.Integer channelTop){
		this.channelTop = channelTop;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  浏览量
	 */
	
	
	@Column(name ="PV",nullable=true,precision=10,scale=0)
	public java.lang.Integer getPv(){
		return this.pv;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  浏览量
	 */
	public void setPv(java.lang.Integer pv){
		this.pv = pv;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  评论数
	 */
	
	
	@Column(name ="COMMENTS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getComments(){
		return this.comments;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  评论数
	 */
	public void setComments(java.lang.Integer comments){
		this.comments = comments;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  发稿量
	 */
	
	
	@Column(name ="ISSUED_AMOUNT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getIssuedAmount(){
		return this.issuedAmount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  发稿量
	 */
	public void setIssuedAmount(java.lang.Integer issuedAmount){
		this.issuedAmount = issuedAmount;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	
	
	@Column(name ="CREATED",nullable=true)
	public java.util.Date getCreated(){
		return this.created;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreated(java.util.Date created){
		this.created = created;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	
	
	@Column(name ="CREATEDBY",nullable=true,length=50)
	public java.lang.String getCreatedby(){
		return this.createdby;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreatedby(java.lang.String createdby){
		this.createdby = createdby;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	
	
	@Column(name ="MODIFIED",nullable=true)
	public java.util.Date getModified(){
		return this.modified;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setModified(java.util.Date modified){
		this.modified = modified;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */
	
	
	@Column(name ="MODIFIEDBY",nullable=true,length=50)
	public java.lang.String getModifiedby(){
		return this.modifiedby;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setModifiedby(java.lang.String modifiedby){
		this.modifiedby = modifiedby;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	
	
	@Column(name ="SORT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSort(){
		return this.sort;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setSort(java.lang.Integer sort){
		this.sort = sort;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  级别
	 */
	
	
	@Column(name ="LEVELS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getLevels(){
		return this.levels;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  级别
	 */
	public void setLevels(java.lang.Integer levels){
		this.levels = levels;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  路径
	 */
	
	
	@Column(name ="PATHIDS",nullable=true,length=255)
	public java.lang.String getPathids(){
		return this.pathids;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  路径
	 */
	public void setPathids(java.lang.String pathids){
		this.pathids = pathids;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工作流ID
	 */
	
	
	@Column(name ="WORKFLOWID",nullable=true,length=32)
	public java.lang.String getWorkflowid(){
		return this.workflowid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  工作流ID
	 */
	public void setWorkflowid(java.lang.String workflowid){
		this.workflowid = workflowid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  频道类型
	 */
	
	
	@Column(name ="CHANNEL_TYPE",nullable=true,length=255)
	public java.lang.String getChannelType(){
		return this.channelType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  频道类型
	 */
	public void setChannelType(java.lang.String channelType){
		this.channelType = channelType;
	}
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 生成路径
	 */

	@Column(name = "PATH", nullable = true, length = 100)
	public java.lang.String getPath() {
		return this.path;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 生成路径
	 */
	public void setPath(java.lang.String path) {
		this.path = path;
	}
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String url
	 */

	@Column(name = "URL", nullable = true, length = 100)
	public java.lang.String getUrl() {
		return this.url;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String url
	 */
	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 文章状态
	 */
	@Column(name = "CONSTANTS", nullable = true, length = 11)
	public java.lang.String getConstants() {
		return this.constants;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 文章状态
	 */
	public void setConstants(java.lang.String constants) {
		this.constants = constants;
	}
	
	@Column(name ="CHANNELHOT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getChannelHot(){
		return this.channelHot;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  热门频道(0代表 否   1代表 是)
	 */
	public void setChannelHot(java.lang.Integer channelHot){
		this.channelHot = channelHot;
	}
}
