package com.leimingtech.cms.entity.acquisition;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**
 * @Title: Entity
 * @Description: 数据采集
 * @author zhangdaihao
 * @date 2014-04-15 17:06:01
 * @version V1.0
 * 
 */
@Entity
@Table(name = "cms_acquisition", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AcquisitionEntity extends IdEntity implements java.io.Serializable {
	
	/** siteId */
	private java.lang.String siteId;
	/** channelId */
	private java.lang.String channelId;
	/** typeId */
	private java.lang.String typeId;
	/** userId */
	private java.lang.String userId;
	/** 采集名称 */
	private java.lang.String acqName;
	/** 开始时间 */
	private java.util.Date startTime;
	/** 停止时间 */
	private java.util.Date endTime;
	/** 当前状态(0:静止;1:采集;2:暂停) */
	private java.lang.Integer status;
	/** 当前号码 */
	private java.lang.Integer currNum;
	/** 当前条数 */
	private java.lang.Integer currItem;
	/** 每页总条数 */
	private java.lang.Integer totalItem;
	/** 暂停时间(毫秒) */
	private java.lang.Integer pauseTime;
	/** 页面编码 */
	private java.lang.String pageEncoding;
	/** 采集列表 */
	private java.lang.String planList;
	/** 动态地址 */
	private java.lang.String dynamicAddr;
	/** 页码开始 */
	private java.lang.Integer dynamicStart;
	/** 页码结束 */
	private java.lang.Integer dynamicEnd;
	/** 内容链接区开始 */
	private java.lang.String linksetStart;
	/** 内容链接区结束 */
	private java.lang.String linksetEnd;
	/** 内容链接开始 */
	private java.lang.String linkStart;
	/** 内容链接结束 */
	private java.lang.String linkEnd;
	/** 标题开始 */
	private java.lang.String titleStart;
	/** 标题结束 */
	private java.lang.String titleEnd;
	/** 关键字开始 */
	private java.lang.String keywordsStart;
	/** 关键字结束 */
	private java.lang.String keywordsEnd;
	/** 描述开始 */
	private java.lang.String descriptionStart;
	/** 描述结束 */
	private java.lang.String descriptionEnd;
	/** 内容开始 */
	private java.lang.String contentStart;
	/** 内容结束 */
	private java.lang.String contentEnd;
	/** 内容分页开始 */
	private java.lang.String paginationStart;
	/** 内容分页结束 */
	private java.lang.String paginationEnd;
	/** 队列 */
	private java.lang.Integer queue;
	/** 重复类型 */
	private java.lang.String repeatCheckType;
	/** 是否采集图片 */
	private java.lang.Integer imgAcqu;
	/** 内容地址补全url */
	private java.lang.String contentPrefix;
	/** 图片地址补全url */
	private java.lang.String imgPrefix;
	/** 浏览量开始 */
	private java.lang.String viewStart;
	/** 浏览量结束 */
	private java.lang.String viewEnd;
	/** id前缀 */
	private java.lang.String viewIdStart;
	/** id后缀 */
	private java.lang.String viewIdEnd;
	/** 浏览量动态访问地址 */
	private java.lang.String viewLink;
	/** 发布时间开始 */
	private java.lang.String releasetimeStart;
	/** 发布时间结束 */
	private java.lang.String releasetimeEnd;
	/** 作者开始 */
	private java.lang.String authorStart;
	/** 作者结束 */
	private java.lang.String authorEnd;
	/** 来源开始 */
	private java.lang.String originStart;
	/** 来源结束 */
	private java.lang.String originEnd;
	/** 发布时间格式 */
	private java.lang.String releasetimeFormat;

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
		if(createdtime==null){
			this.createdtime=new Date();
		}
		this.createdtime = createdtime;
	}
	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String siteId
	 */
	@Column(name = "SITE_ID", nullable = true, length=32)
	public java.lang.String getSiteId() {
		return this.siteId;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String siteId
	 */
	public void setSiteId(java.lang.String siteId) {
		this.siteId = siteId;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String channelId
	 */
	@Column(name = "CHANNEL_ID", nullable = true, length=32)
	public java.lang.String getChannelId() {
		return this.channelId;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String channelId
	 */
	public void setChannelId(java.lang.String channelId) {
		this.channelId = channelId;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String typeId
	 */
	@Column(name = "TYPE_ID", nullable = true, length=32)
	public java.lang.String getTypeId() {
		return this.typeId;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String typeId
	 */
	public void setTypeId(java.lang.String typeId) {
		this.typeId = typeId;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String userId
	 */
	@Column(name = "USER_ID", nullable = true, length=32)
	public java.lang.String getUserId() {
		return this.userId;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String userId
	 */
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 采集名称
	 */
	@Column(name = "ACQ_NAME", nullable = true, length = 50)
	public java.lang.String getAcqName() {
		return this.acqName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 采集名称
	 */
	public void setAcqName(java.lang.String acqName) {
		this.acqName = acqName;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 开始时间
	 */
	@Column(name = "START_TIME", nullable = true)
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 开始时间
	 */
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 停止时间
	 */
	@Column(name = "END_TIME", nullable = true)
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 停止时间
	 */
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 当前状态(0:静止;1:采集;2:暂停)
	 */
	@Column(name = "STATUS", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getStatus() {
		return this.status;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 当前状态(0:静止;1:采集;2:暂停)
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 当前号码
	 */
	@Column(name = "CURR_NUM", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getCurrNum() {
		return this.currNum;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 当前号码
	 */
	public void setCurrNum(java.lang.Integer currNum) {
		this.currNum = currNum;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 当前条数
	 */
	@Column(name = "CURR_ITEM", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getCurrItem() {
		return this.currItem;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 当前条数
	 */
	public void setCurrItem(java.lang.Integer currItem) {
		this.currItem = currItem;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 每页总条数
	 */
	@Column(name = "TOTAL_ITEM", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getTotalItem() {
		return this.totalItem;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 每页总条数
	 */
	public void setTotalItem(java.lang.Integer totalItem) {
		this.totalItem = totalItem;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 暂停时间(毫秒)
	 */
	@Column(name = "PAUSE_TIME", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getPauseTime() {
		return this.pauseTime;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 暂停时间(毫秒)
	 */
	public void setPauseTime(java.lang.Integer pauseTime) {
		this.pauseTime = pauseTime;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 页面编码
	 */
	@Column(name = "PAGE_ENCODING", nullable = true, length = 20)
	public java.lang.String getPageEncoding() {
		return this.pageEncoding;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 页面编码
	 */
	public void setPageEncoding(java.lang.String pageEncoding) {
		this.pageEncoding = pageEncoding;
	}

	/**
	 * 方法: 取得java.lang.Object
	 * 
	 * @return: java.lang.Object 采集列表
	 */
	@Column(name = "PLAN_LIST", nullable = true)
	public java.lang.String getPlanList() {
		return this.planList;
	}

	/**
	 * 方法: 设置java.lang.Object
	 * 
	 * @param: java.lang.Object 采集列表
	 */
	public void setPlanList(java.lang.String planList) {
		this.planList = planList;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 动态地址
	 */
	@Column(name = "DYNAMIC_ADDR", nullable = true, length = 255)
	public java.lang.String getDynamicAddr() {
		return this.dynamicAddr;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 动态地址
	 */
	public void setDynamicAddr(java.lang.String dynamicAddr) {
		this.dynamicAddr = dynamicAddr;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 页码开始
	 */
	@Column(name = "DYNAMIC_START", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getDynamicStart() {
		return this.dynamicStart;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 页码开始
	 */
	public void setDynamicStart(java.lang.Integer dynamicStart) {
		this.dynamicStart = dynamicStart;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 页码结束
	 */
	@Column(name = "DYNAMIC_END", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getDynamicEnd() {
		return this.dynamicEnd;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 页码结束
	 */
	public void setDynamicEnd(java.lang.Integer dynamicEnd) {
		this.dynamicEnd = dynamicEnd;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 内容链接区开始
	 */
	@Column(name = "LINKSET_START", nullable = true, length = 255)
	public java.lang.String getLinksetStart() {
		return this.linksetStart;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 内容链接区开始
	 */
	public void setLinksetStart(java.lang.String linksetStart) {
		this.linksetStart = linksetStart;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 内容链接区结束
	 */
	@Column(name = "LINKSET_END", nullable = true, length = 255)
	public java.lang.String getLinksetEnd() {
		return this.linksetEnd;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 内容链接区结束
	 */
	public void setLinksetEnd(java.lang.String linksetEnd) {
		this.linksetEnd = linksetEnd;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 内容链接开始
	 */
	@Column(name = "LINK_START", nullable = true, length = 255)
	public java.lang.String getLinkStart() {
		return this.linkStart;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 内容链接开始
	 */
	public void setLinkStart(java.lang.String linkStart) {
		this.linkStart = linkStart;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 内容链接结束
	 */
	@Column(name = "LINK_END", nullable = true, length = 255)
	public java.lang.String getLinkEnd() {
		return this.linkEnd;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 内容链接结束
	 */
	public void setLinkEnd(java.lang.String linkEnd) {
		this.linkEnd = linkEnd;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 标题开始
	 */
	@Column(name = "TITLE_START", nullable = true, length = 255)
	public java.lang.String getTitleStart() {
		return this.titleStart;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 标题开始
	 */
	public void setTitleStart(java.lang.String titleStart) {
		this.titleStart = titleStart;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 标题结束
	 */
	@Column(name = "TITLE_END", nullable = true, length = 255)
	public java.lang.String getTitleEnd() {
		return this.titleEnd;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 标题结束
	 */
	public void setTitleEnd(java.lang.String titleEnd) {
		this.titleEnd = titleEnd;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 关键字开始
	 */
	@Column(name = "KEYWORDS_START", nullable = true, length = 255)
	public java.lang.String getKeywordsStart() {
		return this.keywordsStart;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 关键字开始
	 */
	public void setKeywordsStart(java.lang.String keywordsStart) {
		this.keywordsStart = keywordsStart;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 关键字结束
	 */
	@Column(name = "KEYWORDS_END", nullable = true, length = 255)
	public java.lang.String getKeywordsEnd() {
		return this.keywordsEnd;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 关键字结束
	 */
	public void setKeywordsEnd(java.lang.String keywordsEnd) {
		this.keywordsEnd = keywordsEnd;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 描述开始
	 */
	@Column(name = "DESCRIPTION_START", nullable = true, length = 255)
	public java.lang.String getDescriptionStart() {
		return this.descriptionStart;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 描述开始
	 */
	public void setDescriptionStart(java.lang.String descriptionStart) {
		this.descriptionStart = descriptionStart;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 描述结束
	 */
	@Column(name = "DESCRIPTION_END", nullable = true, length = 255)
	public java.lang.String getDescriptionEnd() {
		return this.descriptionEnd;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 描述结束
	 */
	public void setDescriptionEnd(java.lang.String descriptionEnd) {
		this.descriptionEnd = descriptionEnd;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 内容开始
	 */
	@Column(name = "CONTENT_START", nullable = true, length = 255)
	public java.lang.String getContentStart() {
		return this.contentStart;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 内容开始
	 */
	public void setContentStart(java.lang.String contentStart) {
		this.contentStart = contentStart;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 内容结束
	 */
	@Column(name = "CONTENT_END", nullable = true, length = 255)
	public java.lang.String getContentEnd() {
		return this.contentEnd;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 内容结束
	 */
	public void setContentEnd(java.lang.String contentEnd) {
		this.contentEnd = contentEnd;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 内容分页开始
	 */
	@Column(name = "PAGINATION_START", nullable = true, length = 255)
	public java.lang.String getPaginationStart() {
		return this.paginationStart;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 内容分页开始
	 */
	public void setPaginationStart(java.lang.String paginationStart) {
		this.paginationStart = paginationStart;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 内容分页结束
	 */
	@Column(name = "PAGINATION_END", nullable = true, length = 255)
	public java.lang.String getPaginationEnd() {
		return this.paginationEnd;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 内容分页结束
	 */
	public void setPaginationEnd(java.lang.String paginationEnd) {
		this.paginationEnd = paginationEnd;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 队列
	 */
	@Column(name = "QUEUE", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getQueue() {
		return this.queue;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 队列
	 */
	public void setQueue(java.lang.Integer queue) {
		this.queue = queue;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 重复类型
	 */
	@Column(name = "REPEAT_CHECK_TYPE", nullable = true, length = 20)
	public java.lang.String getRepeatCheckType() {
		return this.repeatCheckType;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 重复类型
	 */
	public void setRepeatCheckType(java.lang.String repeatCheckType) {
		this.repeatCheckType = repeatCheckType;
	}

	/**
	 * 方法: 取得java.lang.Boolean
	 * 
	 * @return: java.lang.Boolean 是否采集图片
	 */
	@Column(name = "IMG_ACQU", nullable = true)
	public java.lang.Integer getImgAcqu() {
		return this.imgAcqu;
	}

	/**
	 * 方法: 设置java.lang.Boolean
	 * 
	 * @param: java.lang.Boolean 是否采集图片
	 */
	public void setImgAcqu(java.lang.Integer imgAcqu) {
		this.imgAcqu = imgAcqu;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 内容地址补全url
	 */
	@Column(name = "CONTENT_PREFIX", nullable = true, length = 255)
	public java.lang.String getContentPrefix() {
		return this.contentPrefix;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 内容地址补全url
	 */
	public void setContentPrefix(java.lang.String contentPrefix) {
		this.contentPrefix = contentPrefix;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 图片地址补全url
	 */
	@Column(name = "IMG_PREFIX", nullable = true, length = 255)
	public java.lang.String getImgPrefix() {
		return this.imgPrefix;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 图片地址补全url
	 */
	public void setImgPrefix(java.lang.String imgPrefix) {
		this.imgPrefix = imgPrefix;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 浏览量开始
	 */
	@Column(name = "VIEW_START", nullable = true, length = 255)
	public java.lang.String getViewStart() {
		return this.viewStart;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 浏览量开始
	 */
	public void setViewStart(java.lang.String viewStart) {
		this.viewStart = viewStart;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 浏览量结束
	 */
	@Column(name = "VIEW_END", nullable = true, length = 255)
	public java.lang.String getViewEnd() {
		return this.viewEnd;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 浏览量结束
	 */
	public void setViewEnd(java.lang.String viewEnd) {
		this.viewEnd = viewEnd;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String id前缀
	 */
	@Column(name = "VIEW_ID_START", nullable = true, length = 255)
	public java.lang.String getViewIdStart() {
		return this.viewIdStart;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String id前缀
	 */
	public void setViewIdStart(java.lang.String viewIdStart) {
		this.viewIdStart = viewIdStart;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String id后缀
	 */
	@Column(name = "VIEW_ID_END", nullable = true, length = 255)
	public java.lang.String getViewIdEnd() {
		return this.viewIdEnd;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String id后缀
	 */
	public void setViewIdEnd(java.lang.String viewIdEnd) {
		this.viewIdEnd = viewIdEnd;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 浏览量动态访问地址
	 */
	@Column(name = "VIEW_LINK", nullable = true, length = 255)
	public java.lang.String getViewLink() {
		return this.viewLink;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 浏览量动态访问地址
	 */
	public void setViewLink(java.lang.String viewLink) {
		this.viewLink = viewLink;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 发布时间开始
	 */
	@Column(name = "RELEASETIME_START", nullable = true, length = 255)
	public java.lang.String getReleasetimeStart() {
		return this.releasetimeStart;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 发布时间开始
	 */
	public void setReleasetimeStart(java.lang.String releasetimeStart) {
		this.releasetimeStart = releasetimeStart;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 发布时间结束
	 */
	@Column(name = "RELEASETIME_END", nullable = true, length = 255)
	public java.lang.String getReleasetimeEnd() {
		return this.releasetimeEnd;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 发布时间结束
	 */
	public void setReleasetimeEnd(java.lang.String releasetimeEnd) {
		this.releasetimeEnd = releasetimeEnd;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 作者开始
	 */
	@Column(name = "AUTHOR_START", nullable = true, length = 255)
	public java.lang.String getAuthorStart() {
		return this.authorStart;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 作者开始
	 */
	public void setAuthorStart(java.lang.String authorStart) {
		this.authorStart = authorStart;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 作者结束
	 */
	@Column(name = "AUTHOR_END", nullable = true, length = 255)
	public java.lang.String getAuthorEnd() {
		return this.authorEnd;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 作者结束
	 */
	public void setAuthorEnd(java.lang.String authorEnd) {
		this.authorEnd = authorEnd;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 来源开始
	 */
	@Column(name = "ORIGIN_START", nullable = true, length = 255)
	public java.lang.String getOriginStart() {
		return this.originStart;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 来源开始
	 */
	public void setOriginStart(java.lang.String originStart) {
		this.originStart = originStart;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 来源结束
	 */
	@Column(name = "ORIGIN_END", nullable = true, length = 255)
	public java.lang.String getOriginEnd() {
		return this.originEnd;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 来源结束
	 */
	public void setOriginEnd(java.lang.String originEnd) {
		this.originEnd = originEnd;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 发布时间格式
	 */
	@Column(name = "RELEASETIME_FORMAT", nullable = true, length = 255)
	public java.lang.String getReleasetimeFormat() {
		return this.releasetimeFormat;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 发布时间格式
	 */
	public void setReleasetimeFormat(java.lang.String releasetimeFormat) {
		this.releasetimeFormat = releasetimeFormat;
	}
	
	@Transient
	public int getTotalNum() {
		int count = 0;
		Integer start = getDynamicStart();
		Integer end = getDynamicEnd();
		if (!StringUtils.isBlank(getDynamicAddr()) && start != null
				&& end != null) {
			count = end - start + 1;
		}
		if (!StringUtils.isBlank(getPlanList())) {
			count += getPlans().length;
		}
		return count;
	}
	
	@Transient
	public String[] getPlans() {
		String plan = getPlanList();
		if (!StringUtils.isBlank(plan)) {
			return StringUtils.split(plan);
		} else {
			return new String[0];
		}
	}

}
