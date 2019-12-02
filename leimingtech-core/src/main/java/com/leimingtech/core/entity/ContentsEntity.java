package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


/**
 * @Title: Entity
 * @Description: 内容
 * @author zhangdaihao
 * @date 2014-04-21 15:05:15
 * @version V1.0
 * 
 */
@Entity
@Table(name = "cms_content", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ContentsEntity extends IdEntity implements java.io.Serializable {
	// ------------------------------属性-------------------------------------------
	/** 模型ID */
	private java.lang.String modelid;
	/** 分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题) */
	private java.lang.String classify;
	/** 标题 */
	private java.lang.String title;
	/** 短标题 */
	private java.lang.String subtitle;
	/***/
	private java.lang.String citetitle;
	/** 颜色 */
	private java.lang.String color;
	/** 缩略图 */
	private java.lang.String thumb;
	/** 标签 */
	private java.lang.String tags;
	/** 作者 */
	private java.lang.String author;
	/** 编辑人 */
	private java.lang.String editor;
	/** 来源 名称 */
	private java.lang.String sourceid;
	/** URL路径 */
	private java.lang.String url;
	/** 宽度 */
	private java.lang.Integer weight;
	/** 工作流状态 */
	private java.lang.String status;
	/** 文章状态 */
	private java.lang.String constants;
	/** 创建时间 */
	private java.util.Date created;
	/** 创建人 */
	private java.lang.String createdby;
	/** 发布时间 */
	private java.util.Date published;
	/** 发布人 */
	private java.lang.String publishedby;
	/** unpublished */
	private java.lang.Integer unpublished;
	/** unpublishedby */
	private java.lang.Integer unpublishedby;
	/** 修改时间 */
	private java.util.Date modified;
	/** 修改人 */
	private java.lang.String modifiedby;
	/** 检查时间 */
	private java.util.Date checked;
	/** 检查人 */
	private java.lang.String checkedby;
	/** 锁定时间 */
	private java.util.Date locked;
	/** 锁定人 */
	private java.lang.String lockedby;
	/** 下线时间 */
	private java.util.Date noted;
	/** 下线人 */
	private java.lang.String notedby;
	/** 注解 */
	private java.lang.Integer note;
	/** 工作流步骤 */
	private java.lang.Integer workflowStep;
	/** 工作流角色ID */
	private java.lang.String workflowRoleid;
	/** 是否投稿 */
	private java.lang.String iscontribute;
	/** 专栏ID */
	private java.lang.String spaceid;
	/** related */
	private java.lang.Integer related;
	/** 浏览量 */
	private java.lang.Integer pv;
	/** 是否允许评论 */
	private java.lang.String allowcomment;
	/** 扩展字段json */
	private java.lang.String docextendjson;
	/** 扩张字段数 */
	private java.lang.Integer extendcount;
	/** 描述 */
	private java.lang.String description;
	/** 摘要 */
	private java.lang.String digest;
	/** 属性 */
	private java.lang.String attribute;
	/** 相关 */
	private java.lang.String correlation;

	/** 栏目名 */
	private java.lang.String catName;
	/** 栏目ID */
	private String catid;
	/** 评分 */
	private java.lang.Integer grade;
	/** 发布人 id */
	private java.lang.String publishedbyid;

	/** 站点id */
	private java.lang.String siteid;
	/** 会员id */
	private java.lang.String memberid;
	/** 二维码 */
	private java.lang.String twoCode;
	/** 内容标记 */
	private java.lang.String contentMark;
	/** 是否广告(0否1是) */
	private java.lang.Integer isposter;
	/** pathids */
	private java.lang.String pathids;

	/** wap页面访问路径 */
	private String wapurl;
	/** 同时发布栏目id */
	private String synCatid;

	/** 是否可以审核，非数据库字段 */
	private boolean isOperate;
	/**
	 * 是否为头条：“1”表示是头条，“0”表示普通新闻，默认是“0”
	 */
	private int isHeadline;

	/**稿件是否锁定（true：锁定，false:不锁定）*/
	private String lockContent;
	/**静态地址*/
	private String staticUrl;
	/**动态地址*/
	private String dynamicUrl;

	/**赞同数*/
	private Integer support;

	/**反对数*/
	private Integer oppose;







	/**
	 * 静态地址
	 * @return
	 */
	@Column(name = "static_url", nullable = false, length = 255)
	public String getStaticUrl() {
		return staticUrl;
	}

	public void setStaticUrl(String staticUrl) {
		this.staticUrl = staticUrl;
	}
	/***
	 * 动态地址
	 * @return
	 */
	@Column(name = "dynamic_url", nullable = false, length = 255)
	public String getDynamicUrl() {
		return dynamicUrl;
	}

	public void setDynamicUrl(String dynamicUrl) {
		this.dynamicUrl = dynamicUrl;
	}
	
	@Column(name = "lock_content", nullable = false, length = 32)
	public String getLockContent() {
		return lockContent;
	}

	public void setLockContent(String lockContent) {
		this.lockContent = lockContent;
	}

	// ----------------------------------------------get,set方法------------------------------------------------------
	/** 置顶 */
	private Integer isTop;
	/** 新加字段表示文章内容字段 */
	private String atriles;

	@Transient
	public String getAtriles() {
		return atriles;
	}

	public void setAtriles(String atriles) {
		this.atriles = atriles;
	}

	/** wap页面访问路径 */
	@Column(name = "WAPURL", nullable = true, length = 255)
	public String getWapurl() {
		return wapurl;
	}

	/** wap页面访问路径 */
	public void setWapurl(String wapurl) {
		this.wapurl = wapurl;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 栏目ID
	 */
	@Column(name = "CATID", nullable = false, length = 32)
	public String getCatid() {
		return this.catid;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 栏目ID
	 */
	public void setCatid(java.lang.String catid) {
		this.catid = catid;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 模型ID
	 */
	@Column(name = "MODELID", nullable = false, length = 32)
	public java.lang.String getModelid() {
		return this.modelid;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 模型ID
	 */
	public void setModelid(java.lang.String modelid) {
		this.modelid = modelid;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 分类(1文章2链接3组图4视频5活动6投票7访谈8调查9专题)
	 */
	@Column(name = "CLASSIFY", nullable = true, length = 255)
	public java.lang.String getClassify() {
		return this.classify;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 分类(1文章2链接3组图4视频5活动6投票7访谈8调查9专题)
	 */
	public void setClassify(java.lang.String classify) {
		this.classify = classify;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 标题
	 */
	@Column(name = "TITLE", nullable = false, length = 255)
	public java.lang.String getTitle() {
		return this.title;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 标题
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 短标题
	 */
	@Column(name = "SUBTITLE", nullable = true, length = 255)
	public java.lang.String getSubtitle() {
		return this.subtitle;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 短标题
	 */
	public void setSubtitle(java.lang.String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 引题
	 */
	@Column(name = "CITETITLE", nullable = true, length = 250)
	public java.lang.String getCitetitle() {
		return this.citetitle;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 引题
	 */
	public void setCitetitle(java.lang.String citetitle) {
		this.citetitle = citetitle;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 颜色
	 */
	@Column(name = "COLOR", nullable = true)
	public java.lang.String getColor() {
		return this.color;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 颜色
	 */
	public void setColor(java.lang.String color) {
		this.color = color;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 缩略图
	 */
	@Column(name = "THUMB", nullable = true, length = 255)
	public java.lang.String getThumb() {
		return this.thumb;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 缩略图
	 */
	public void setThumb(java.lang.String thumb) {
		this.thumb = thumb;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 标签
	 */
	@Column(name = "TAGS", nullable = true, length = 60)
	public java.lang.String getTags() {
		return this.tags;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 标签
	 */
	public void setTags(java.lang.String tags) {
		this.tags = tags;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 作者
	 */
	@Column(name = "AUTHOR", nullable = true, length = 100)
	public java.lang.String getAuthor() {
		return this.author;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 作者
	 */
	public void setAuthor(java.lang.String author) {
		this.author = author;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 编辑人
	 */
	@Column(name = "EDITOR", nullable = true, length = 15)
	public java.lang.String getEditor() {
		return this.editor;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 编辑人
	 */
	public void setEditor(java.lang.String editor) {
		this.editor = editor;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 来源名称
	 */
	@Column(name = "SOURCEID", nullable = true, length = 32)
	public java.lang.String getSourceid() {
		return this.sourceid;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 来源名称
	 */
	public void setSourceid(java.lang.String sourceid) {
		this.sourceid = sourceid;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String URL路径
	 */
	@Column(name = "URL", nullable = true, length = 255)
	public java.lang.String getUrl() {
		return this.url;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String URL路径
	 */
	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 宽度
	 */
	@Column(name = "WEIGHT", nullable = true, precision = 3, scale = 0)
	public java.lang.Integer getWeight() {
		return this.weight;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 宽度
	 */
	public void setWeight(java.lang.Integer weight) {
		this.weight = weight;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 工作流状态
	 */
	@Column(name = "STATUS", nullable = true, length = 255)
	public java.lang.String getStatus() {
		return this.status;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 工作流状态
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 文章状态
	 */
	@Column(name = "CONSTANTS", nullable = true, length = 255)
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

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATED", nullable = true)
	public java.util.Date getCreated() {
		return this.created;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 创建时间
	 */
	public void setCreated(java.util.Date created) {
		this.created = created;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 创建人
	 */
	@Column(name = "CREATEDBY", nullable = true, length = 255)
	public java.lang.String getCreatedby() {
		return this.createdby;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 创建人
	 */
	public void setCreatedby(java.lang.String createdby) {
		this.createdby = createdby;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 发布时间
	 */
	@Column(name = "PUBLISHED", nullable = true)
	public java.util.Date getPublished() {
		return this.published;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 发布时间
	 */
	public void setPublished(java.util.Date published) {
		this.published = published;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 发布人
	 */
	@Column(name = "PUBLISHEDBY", nullable = true, length = 255)
	public java.lang.String getPublishedby() {
		return this.publishedby;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 发布人
	 */
	public void setPublishedby(java.lang.String publishedby) {
		this.publishedby = publishedby;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer unpublished
	 */
	@Column(name = "UNPUBLISHED", nullable = true, precision = 19, scale = 0)
	public java.lang.Integer getUnpublished() {
		return this.unpublished;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer unpublished
	 */
	public void setUnpublished(java.lang.Integer unpublished) {
		this.unpublished = unpublished;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer unpublishedby
	 */
	@Column(name = "UNPUBLISHEDBY", nullable = true, precision = 7, scale = 0)
	public java.lang.Integer getUnpublishedby() {
		return this.unpublishedby;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer unpublishedby
	 */
	public void setUnpublishedby(java.lang.Integer unpublishedby) {
		this.unpublishedby = unpublishedby;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 修改时间
	 */
	@Column(name = "MODIFIED", nullable = true)
	public java.util.Date getModified() {
		return this.modified;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 修改时间
	 */
	public void setModified(java.util.Date modified) {
		this.modified = modified;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 修改人
	 */
	@Column(name = "MODIFIEDBY", nullable = true, length = 255)
	public java.lang.String getModifiedby() {
		return this.modifiedby;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 修改人
	 */
	public void setModifiedby(java.lang.String modifiedby) {
		this.modifiedby = modifiedby;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 检查时间
	 */
	@Column(name = "CHECKED", nullable = true)
	public java.util.Date getChecked() {
		return this.checked;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 检查时间
	 */
	public void setChecked(java.util.Date checked) {
		this.checked = checked;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 检查人
	 */
	@Column(name = "CHECKEDBY", nullable = true, length = 255)
	public java.lang.String getCheckedby() {
		return this.checkedby;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 检查人
	 */
	public void setCheckedby(java.lang.String checkedby) {
		this.checkedby = checkedby;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 锁定时间
	 */
	@Column(name = "LOCKED", nullable = true)
	public java.util.Date getLocked() {
		return this.locked;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 锁定时间
	 */
	public void setLocked(java.util.Date locked) {
		this.locked = locked;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 锁定人
	 */
	@Column(name = "LOCKEDBY", nullable = true, length = 255)
	public java.lang.String getLockedby() {
		return this.lockedby;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 锁定人
	 */
	public void setLockedby(java.lang.String lockedby) {
		this.lockedby = lockedby;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 注解时间
	 */
	@Column(name = "NOTED", nullable = true)
	public java.util.Date getNoted() {
		return this.noted;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 注解时间
	 */
	public void setNoted(java.util.Date noted) {
		this.noted = noted;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 注解人
	 */
	@Column(name = "NOTEDBY", nullable = true, length = 255)
	public java.lang.String getNotedby() {
		return this.notedby;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 注解人
	 */
	public void setNotedby(java.lang.String notedby) {
		this.notedby = notedby;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 注解
	 */
	@Column(name = "NOTE", nullable = true, precision = 3, scale = 0)
	public java.lang.Integer getNote() {
		return this.note;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 注解
	 */
	public void setNote(java.lang.Integer note) {
		this.note = note;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 工作流步骤
	 */
	@Column(name = "WORKFLOW_STEP", nullable = true, precision = 3, scale = 0)
	public java.lang.Integer getWorkflowStep() {
		return this.workflowStep;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 工作流步骤
	 */
	public void setWorkflowStep(java.lang.Integer workflowStep) {
		this.workflowStep = workflowStep;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 工作流角色ID
	 */
	@Column(name = "WORKFLOW_ROLEID", nullable = true, length = 32)
	public java.lang.String getWorkflowRoleid() {
		return this.workflowRoleid;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 工作流角色ID
	 */
	public void setWorkflowRoleid(java.lang.String workflowRoleid) {
		this.workflowRoleid = workflowRoleid;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 是否投稿
	 */
	@Column(name = "ISCONTRIBUTE", nullable = true, length = 255)
	public java.lang.String getIscontribute() {
		return this.iscontribute;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 是否投稿
	 */
	public void setIscontribute(java.lang.String iscontribute) {
		this.iscontribute = iscontribute;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 专栏ID
	 */
	@Column(name = "SPACEID", nullable = true, length = 32)
	public java.lang.String getSpaceid() {
		return this.spaceid;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 专栏ID
	 */
	public void setSpaceid(java.lang.String spaceid) {
		this.spaceid = spaceid;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer related
	 */
	@Column(name = "RELATED", nullable = true, precision = 3, scale = 0)
	public java.lang.Integer getRelated() {
		return this.related;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer related
	 */
	public void setRelated(java.lang.Integer related) {
		this.related = related;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 浏览量
	 */
	@Column(name = "PV", nullable = true, precision = 7, scale = 0)
	public java.lang.Integer getPv() {
		return this.pv;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 浏览量
	 */
	public void setPv(java.lang.Integer pv) {
		this.pv = pv;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 是否允许评论
	 */
	@Column(name = "ALLOWCOMMENT", nullable = true, length = 255)
	public java.lang.String getAllowcomment() {
		return this.allowcomment;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 是否允许评论
	 */
	public void setAllowcomment(java.lang.String allowcomment) {
		this.allowcomment = allowcomment;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 扩展字段json
	 */
	@Column(name = "DOCEXTENDJSON", nullable = true, length = 4000)
	public java.lang.String getDocextendjson() {
		return this.docextendjson;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 扩展字段json
	 */
	public void setDocextendjson(java.lang.String docextendjson) {
		this.docextendjson = docextendjson;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 扩张字段数
	 */
	@Column(name = "EXTENDCOUNT", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getExtendcount() {
		return this.extendcount;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 扩张字段数
	 */
	public void setExtendcount(java.lang.Integer extendcount) {
		this.extendcount = extendcount;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 描述
	 */
	@Column(name = "DESCRIPTION", nullable = true, length = 50)
	public java.lang.String getDescription() {
		return this.description;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 描述
	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 摘要
	 */
	@Column(name = "DIGEST", nullable = true, length = 4000)
	public java.lang.String getDigest() {
		return this.digest;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 摘要
	 */
	public void setDigest(java.lang.String digest) {
		this.digest = digest;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 属性
	 */
	@Column(name = "ATTRIBUTE", nullable = true, length = 255)
	public java.lang.String getAttribute() {
		return this.attribute;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 属性
	 */
	public void setAttribute(java.lang.String attribute) {
		this.attribute = attribute;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 相关
	 */
	@Column(name = "CORRELATION", nullable = true, length = 255)
	public java.lang.String getCorrelation() {
		return this.correlation;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 相关
	 */
	public void setCorrelation(java.lang.String correlation) {
		this.correlation = correlation;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 站点id
	 */
	@Column(name = "SITE_ID", nullable = true, precision = 32, scale = 0)
	public java.lang.String getSiteid() {
		return siteid;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 站点id
	 */
	public void setSiteid(java.lang.String siteid) {
		this.siteid = siteid;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 栏目名
	 */
	@Column(name = "CATNAME", nullable = true, length = 50)
	public java.lang.String getCatName() {
		return this.catName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 栏目名
	 */
	public void setCatName(java.lang.String catName) {
		this.catName = catName;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String pathids
	 */
	@Column(name = "PATHIDS", nullable = true, length = 1000)
	public java.lang.String getPathids() {
		return pathids;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String pathids
	 */
	public void setPathids(java.lang.String pathids) {
		this.pathids = pathids;
	}

	/**
	 * 方法：取得java.lang.Integer
	 * 
	 * @return java.lang.Integer grade
	 */
	@Column(name = "GRADE", nullable = true, precision = 11, scale = 0)
	public java.lang.Integer getGrade() {
		return grade;
	}

	/**
	 * 方法：设置java.lang.Integer
	 * 
	 * @param java
	 *            .lang.Integer grade
	 */
	public void setGrade(java.lang.Integer grade) {
		this.grade = grade;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 发布人id
	 */
	@Column(name = "PUBLISHEDBYID", nullable = true, length = 32)
	public java.lang.String getPublishedbyid() {
		return this.publishedbyid;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 发布人id
	 */
	public void setPublishedbyid(java.lang.String publishedbyid) {
		this.publishedbyid = publishedbyid;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 同时发布栏目id
	 */
	@Column(name = "SYN_CATID", length = 32)
	public java.lang.String getSynCatid() {
		return this.synCatid;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 同时发布栏目id
	 */
	public void setSynCatid(java.lang.String synCatid) {
		this.synCatid = synCatid;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 会员id
	 */
	@Column(name = "MEMBERID", nullable = true, length = 32)
	public java.lang.String getMemberid() {
		return memberid;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 会员id
	 */
	public void setMemberid(java.lang.String memberid) {
		this.memberid = memberid;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 二维码
	 */
	@Column(name = "TWO_CODE", nullable = true, length = 255)
	public java.lang.String getTwoCode() {
		return this.twoCode;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 二维码
	 */
	public void setTwoCode(java.lang.String twoCode) {
		this.twoCode = twoCode;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 内容标记
	 */
	@Column(name = "CONTENT_MARK", nullable = true, length = 255)
	public java.lang.String getContentMark() {
		return this.contentMark;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 内容标记
	 */
	public void setContentMark(java.lang.String contentMark) {
		this.contentMark = contentMark;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 是否广告(0否1是)
	 */
	@Column(name = "ISPOSTER", nullable = true, precision = 2, scale = 0)
	public java.lang.Integer getIsposter() {
		return isposter;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 是否广告(0否1是)
	 */
	public void setIsposter(java.lang.Integer isposter) {
		this.isposter = isposter;
	}

	/** 是否可以审核，非数据库字段 */
	@Transient
	public boolean getIsOperate() {
		return isOperate;
	}

	/** 是否可以审核，非数据库字段 */
	public void setIsOperate(boolean isOperate) {
		this.isOperate = isOperate;
	}

	/**
	 * 
	 * @return
	 */
	@Column(name = "is_headline", nullable = true, precision = 2, scale = 0)
	public int getIsHeadline() {
		return isHeadline;
	}

	public void setIsHeadline(int isHeadline) {
		this.isHeadline = isHeadline;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 置顶
	 */
	@Column(name = "IS_TOP", nullable = true, precision = 11, scale = 0)
	public Integer getIsTop() {
		return isTop;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @param: java.lang.Integer 置顶
	 * 
	 */
	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	/**
	 * 方法：获取赞同数目
	 *
	 * @return
     */
	@Column(name = "supportcount", nullable = true, precision = 11, scale = 0)
	public Integer getSupport() {
		return support;
	}

	/**
	 * 方法：获取反对数目
	 *
	 * @return
	 */
	@Column(name = "opposecount", nullable = true, precision = 11, scale = 0)
	public Integer getOppose() {
		return oppose;
	}

	/**
	 * 方法：设置赞同数目
	 *
	 * @param support
     */
	public void setSupport(Integer support) {this.support = support;}

	/**
	 * 方法：设置反对数目
	 *
	 * @param oppose
     */
	public void setOppose(Integer oppose) {this.oppose = oppose;}
}
