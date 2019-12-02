package com.leimingtech.core.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OrderBy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @Title: Entity
 * @Description: 栏目
 * @author leiming
 * @date 2014-04-21 12:03:26
 * @version V1.0
 *
 */
@Entity
@Table(name = "cms_content_cat", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ContentCatEntity extends IdEntity implements java.io.Serializable {

	/** 栏目名称 */
	private String name;
	/** 别名 */
	private String alias;
	/** 工作流ID */
	private String workflowid;
	/** 模型 */
	private String model;
	/** 模板索引 */
	private String templateIndex;
	/** 模板列表 */
	private String templateList;
	/** 模板日期 */
	private String templateDate;
	/** 栏目生成路径 */
	private String path;
	/** 缩略图 */
	private String thumb;
	/** url */
	private String url;
	/** wapUrl */
	private String wapUrl;

	/** 是否创建索引 */
	private Integer iscreateindex;
	/** URL索引规则 */
	private String urlruleIndex;
	/** URL列表规则 */
	private String urlruleList;
	/** URL显示规则 */
	private String urlruleShow;
	/** enablecontribute */
	private Integer enablecontribute;
	/** 关键字 */
	private String keywords;
	/** 描述 */
	private String description;
	/** posts */
	private Integer posts;
	/** 评论数 */
	private Integer comments;
	/** 浏览量 */
	private Integer pv;
	/** 排序 */
	private Integer sort;
	/** 不可用 */
	private Integer disabled;
	/** 是否已创建HTML */
	private Integer htmlcreated;
	/** 扩展字段JSON */
	private String extendjson;
	/** 拓展字段方案id*/
	private String jsonid;

	/** 文章是否选择(true) */
	private String  isArticleSelected;
	/** 页数 */
	private Integer pagesize;
	/** 创建时间 */
	private java.util.Date created;
	/** 创建人 */
	private String createdby;
	/** 修改时间 */
	private java.util.Date modified;
	/** 修改人 */
	private String modifiedby;
	/** 等级 */
	private Integer levels;
	/** 站点id */
	private String siteid;

	/** 列表页模板 */
	private String listModel;
	/** 栏目首页模板 */
	private String indexTemplate;
	/** rss 模板 */
	private String rssTemplate;

	/** 内容页URL规则 */
	private String urlruleContent;
	/** 列表页每页信息数 */
	private Integer listMessage;
	/** 是否允许用户投稿 */
	private Integer iscontribute;
	/** 是否允许评论 */
	private Integer iscomment;
	/** 标题 */
	private String contentcatTitle;
	/** 水印方案 */
	private String watermark;
	/** 是否显示 */
	private String isshow;
	/** 会员查看等级 */
	private String viplevel;
	/** 栏目拼音 */
	private String contentcatSpell;
	/** 栏目缩写 */
	private String contentcatAbbreviation;
	/** 是否是单页栏目 0不是 1是 */
	private Integer isLeaf;
	/** 是否是根栏目 */
	private Integer iscatend;
	/**是否默认推送*/
	private Integer isSendMobile;
	/**移动频道*/
	private String mobileChannel;
	/** 所有父栏目id */
	private String parentids;

	/** 当前栏目的路径id 包含所有父级栏目id以及当前栏目id */
	private String pathids;

	/**静态地址*/
	private String staticUrl;
	/**动态地址*/
	private String dynamicUrl;
	/**链接地址*/
	private String linkUrl;
	/**是否是链接地址(0:否1:是)*/
	private Integer isLinkUrl;

	@Column(name = "LINK_URL", nullable = false, length = 255)
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	@Column(name = "IS_LINK_URL", nullable = true, precision = 13, scale = 0)
	public Integer getIsLinkUrl() {
		return isLinkUrl;
	}

	public void setIsLinkUrl(Integer isLinkUrl) {
		this.isLinkUrl = isLinkUrl;
	}

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

	/** 当前栏目的路径id 包含所有父级栏目id以及当前栏目id */
	public String getPathids() {
		return pathids;
	}

	/** 当前栏目的路径id 包含所有父级栏目id以及当前栏目id */
	public void setPathids(String pathids) {
		this.pathids = pathids;
	}

	/** 所有父栏目id */
	public String getParentids() {
		return parentids;
	}

	/** 所有父栏目id */
	public void setParentids(String parentids) {
		this.parentids = parentids;
	}

	@Column(name = "iscatend", nullable = true, precision = 13, scale = 0)
	public Integer getIscatend() {
		return iscatend;
	}

	public void setIscatend(Integer iscatend) {
		this.iscatend = iscatend;
	}

	@Column(name = "INDEX_TEMPLATE", nullable = true, length = 50)
	public String getIndexTemplate() {
		return indexTemplate;
	}

	public void setIndexTemplate(String indexTemplate) {
		this.indexTemplate = indexTemplate;
	}

	@Column(name = "IS_LEAF", nullable = true, precision = 3, scale = 0)
	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	private ContentCatEntity contentCat;
	private List<ContentCatEntity> contentCats = new ArrayList<ContentCatEntity>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	public ContentCatEntity getContentCat() {
		return this.contentCat;
	}

	public void setContentCat(ContentCatEntity contentCat) {
		this.contentCat = contentCat;
	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contentCat")
	//	@Where(clause="DEL_FLAG=1")
	@OrderBy(clause="sort desc,created desc")
	public List<ContentCatEntity> getContentCats() {
		return contentCats;
	}

	public void setContentCats(List<ContentCatEntity> contentCats) {
		this.contentCats = contentCats;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 栏目名称
	 */

	@Column(name = "NAME", nullable = true, length = 20)
	public String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 栏目名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 别名
	 */

	@Column(name = "ALIAS", nullable = true, length = 20)
	public String getAlias() {
		return this.alias;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 别名
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 工作流ID
	 */

	@Column(name = "WORKFLOWID", nullable = true, length = 32)
	public String getWorkflowid() {
		return this.workflowid;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 工作流ID
	 */
	public void setWorkflowid(String workflowid) {
		this.workflowid = workflowid;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 模型
	 */

	@Column(name = "MODEL", nullable = true, length = 255)
	public String getModel() {
		return this.model;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 模型
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 模板索引
	 */

	@Column(name = "TEMPLATE_INDEX", nullable = true, length = 2000)
	public String getTemplateIndex() {
		return this.templateIndex;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 模板索引
	 */
	public void setTemplateIndex(String templateIndex) {
		this.templateIndex = templateIndex;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 模板列表
	 */

	@Column(name = "TEMPLATE_LIST", nullable = true, length = 2000)
	public String getTemplateList() {
		return this.templateList;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 模板列表
	 */
	public void setTemplateList(String templateList) {
		this.templateList = templateList;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 模板日期
	 */

	@Column(name = "TEMPLATE_DATE", nullable = true, length = 255)
	public String getTemplateDate() {
		return this.templateDate;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 模板日期
	 */
	public void setTemplateDate(String templateDate) {
		this.templateDate = templateDate;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 栏目生成路径
	 */

	@Column(name = "PATH", nullable = true, length = 100)
	public String getPath() {
		return this.path;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 栏目生成路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 缩略图
	 */

	@Column(name = "THUMB", nullable = true, length = 60)
	public String getThumb() {
		return this.thumb;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 缩略图
	 */
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String url
	 */

	@Column(name = "URL", nullable = true, length = 100)
	public String getUrl() {
		return this.url;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name = "wap_url", nullable = true, length = 100)
	public String getWapUrl() {
		return wapUrl;
	}

	public void setWapUrl(String wapUrl) {
		this.wapUrl = wapUrl;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否创建索引
	 */

	@Column(name = "ISCREATEINDEX", nullable = true, precision = 3, scale = 0)
	public Integer getIscreateindex() {
		return this.iscreateindex;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否创建索引
	 */
	public void setIscreateindex(Integer iscreateindex) {
		this.iscreateindex = iscreateindex;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String URL索引规则
	 */

	@Column(name = "URLRULE_INDEX", nullable = true, length = 255)
	public String getUrlruleIndex() {
		return this.urlruleIndex;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String URL索引规则
	 */
	public void setUrlruleIndex(String urlruleIndex) {
		this.urlruleIndex = urlruleIndex;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String URL列表规则
	 */

	@Column(name = "URLRULE_LIST", nullable = true, length = 255)
	public String getUrlruleList() {
		return this.urlruleList;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String URL列表规则
	 */
	public void setUrlruleList(String urlruleList) {
		this.urlruleList = urlruleList;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String URL显示规则
	 */

	@Column(name = "URLRULE_SHOW", nullable = true, length = 255)
	public String getUrlruleShow() {
		return this.urlruleShow;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String URL显示规则
	 */
	public void setUrlruleShow(String urlruleShow) {
		this.urlruleShow = urlruleShow;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer enablecontribute
	 */

	@Column(name = "ENABLECONTRIBUTE", nullable = true, precision = 3, scale = 0)
	public Integer getEnablecontribute() {
		return this.enablecontribute;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer enablecontribute
	 */
	public void setEnablecontribute(Integer enablecontribute) {
		this.enablecontribute = enablecontribute;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 关键字
	 */

	@Column(name = "KEYWORDS", nullable = true, length = 255)
	public String getKeywords() {
		return this.keywords;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 关键字
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 描述
	 */

	@Column(name = "DESCRIPTION", nullable = true, length = 255)
	public String getDescription() {
		return this.description;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer posts
	 */

	@Column(name = "POSTS", nullable = true, precision = 7, scale = 0)
	public Integer getPosts() {
		return this.posts;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer posts
	 */
	public void setPosts(Integer posts) {
		this.posts = posts;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 评论数
	 */

	@Column(name = "COMMENTS", nullable = true, precision = 7, scale = 0)
	public Integer getComments() {
		return this.comments;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 评论数
	 */
	public void setComments(Integer comments) {
		this.comments = comments;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 浏览量
	 */

	@Column(name = "PV", nullable = true, precision = 10, scale = 0)
	public Integer getPv() {
		return this.pv;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 浏览量
	 */
	public void setPv(Integer pv) {
		this.pv = pv;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 排序
	 */

	@Column(name = "SORT", nullable = true, precision = 3, scale = 0)
	public Integer getSort() {
		return this.sort;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 不可用
	 */

	@Column(name = "DISABLED", nullable = true, precision = 3, scale = 0)
	public Integer getDisabled() {
		return this.disabled;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 不可用
	 */
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否已创建HTML
	 */

	@Column(name = "HTMLCREATED", nullable = true, precision = 3, scale = 0)
	public Integer getHtmlcreated() {
		return this.htmlcreated;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否已创建HTML
	 */
	public void setHtmlcreated(Integer htmlcreated) {
		this.htmlcreated = htmlcreated;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 扩展字段JSON
	 */

	@Column(name = "EXTENDJSON", nullable = true, length = 4000)
	public String getExtendjson() {
		return this.extendjson;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 扩展字段JSON
	 */
	public void setExtendjson(String extendjson) {
		this.extendjson = extendjson;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String JSONID
	 */

	@Column(name = "JSONID", nullable = true, length=32)
	public String getJsonid() {
		return this.jsonid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String JSONID
	 */
	public void setJsonid(String jsonid) {
		this.jsonid = jsonid;
	}






	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 文章是否选择(true)
	 */

	@Column(name = "ISARTICLESELECTED", nullable = true, length = 200)
	public String getIsArticleSelected() {
		return this.isArticleSelected;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 文章是否选择(true)
	 */
	public void setIsArticleSelected(String isArticleSelected) {
		this.isArticleSelected = isArticleSelected;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 页数
	 */

	@Column(name = "PAGESIZE", nullable = true, precision = 10, scale = 0)
	public Integer getPagesize() {
		return this.pagesize;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 页数
	 */
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
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

	@Column(name = "CREATEDBY", nullable = false, length = 50)
	public String getCreatedby() {
		return this.createdby;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 创建人
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
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

	@Column(name = "MODIFIEDBY", nullable = true, length = 50)
	public String getModifiedby() {
		return this.modifiedby;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 修改人
	 */
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 等级
	 */

	@Column(name = "LEVELS", nullable = true, precision = 10, scale = 0)
	public Integer getLevels() {
		return this.levels;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 等级
	 */
	public void setLevels(Integer levels) {
		this.levels = levels;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 站点id
	 */
	@Column(name = "SITE_ID", nullable = true, precision = 32, scale = 0)
	public String getSiteid() {
		return siteid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 站点id
	 */
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 列表页模板
	 */
	@Column(name = "LIST_MODEL", nullable = true)
	public String getListModel() {
		return this.listModel;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 列表页模板
	 */
	public void setListModel(String listModel) {
		this.listModel = listModel;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 内容页URL规则
	 */
	@Column(name = "URLRULE_CONTENT", nullable = true)
	public String getUrlruleContent() {
		return this.urlruleContent;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 内容页URL规则
	 */
	public void setUrlruleContent(String urlruleContent) {
		this.urlruleContent = urlruleContent;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 列表页每页信息数
	 */
	@Column(name = "LIST_MESSAGE", nullable = true, precision = 13, scale = 0)
	public Integer getListMessage() {
		return this.listMessage;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 列表页每页信息数
	 */
	public void setListMessage(Integer listMessage) {
		this.listMessage = listMessage;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否允许用户投稿
	 */
	@Column(name = "ISCONTRIBUTE", nullable = true, precision = 11, scale = 0)
	public Integer getIscontribute() {
		return this.iscontribute;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否允许用户投稿
	 */
	public void setIscontribute(Integer iscontribute) {
		this.iscontribute = iscontribute;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否允许评论
	 */
	@Column(name = "ISCOMMENT", nullable = true, precision = 11, scale = 0)
	public Integer getIscomment() {
		return this.iscomment;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否允许评论
	 */
	public void setIscomment(Integer iscomment) {
		this.iscomment = iscomment;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 标题
	 */
	@Column(name = "CONTENTCAT_TITLE", nullable = true)
	public String getContentcatTitle() {
		return this.contentcatTitle;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 标题
	 */
	public void setContentcatTitle(String contentcatTitle) {
		this.contentcatTitle = contentcatTitle;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 水印方案
	 */
	@Column(name = "WATERMARK", nullable = true)
	public String getWatermark() {
		return this.watermark;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 水印方案
	 */
	public void setWatermark(String watermark) {
		this.watermark = watermark;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 是否显示
	 */
	@Column(name = "ISSHOW", nullable = true, precision = 11, scale = 0)
	public String getIsshow() {
		return this.isshow;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 是否显示
	 */
	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 会员查看等级
	 */
	@Column(name = "VIPLEVEL", nullable = true)
	public String getViplevel() {
		return this.viplevel;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 会员查看等级
	 */
	public void setViplevel(String viplevel) {
		this.viplevel = viplevel;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 栏目拼音
	 */
	@Column(name = "CONTENTCAT_SPELL", nullable = true)
	public String getContentcatSpell() {
		return this.contentcatSpell;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 栏目拼音
	 */
	public void setContentcatSpell(String contentcatSpell) {
		this.contentcatSpell = contentcatSpell;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 栏目缩写
	 */
	@Column(name = "CONTENTCAT_ABBREVIATION", nullable = true)
	public String getContentcatAbbreviation() {
		return this.contentcatAbbreviation;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 栏目缩写
	 */
	public void setContentcatAbbreviation(String contentcatAbbreviation) {
		this.contentcatAbbreviation = contentcatAbbreviation;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String rss模板
	 */
	@Column(name = "RSS_TEMPLATE", nullable = true, length = 255)
	public String getRssTemplate() {
		return rssTemplate;
	}
	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String rss模板
	 */
	public void setRssTemplate(String rssTemplate) {
		this.rssTemplate = rssTemplate;
	}
	/**
	 * 方法: 取得java.lang.Integer 是否默认推送
	 *
	 * @return: java.lang.Integer
	 */
	@Column(name = "ISSENDMOBILE", nullable = true, precision = 11, scale = 0)
	public Integer getIsSendMobile() {
		return isSendMobile;
	}
	/**
	 * 方法: 设置java.lang.Integer 是否默认推送
	 *
	 * @return: java.lang.Integer
	 */
	public void setIsSendMobile(Integer isSendMobile) {
		this.isSendMobile = isSendMobile;
	}
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 移动频道
	 */
	@Column(name = "MOBILECHANNEL", nullable = true, length = 255)
	public String getMobileChannel() {
		return mobileChannel;
	}
	/**
	 * 方法: 设置java.lang.String
	 *
	 * @return: java.lang.String 移动频道
	 */
	public void setMobileChannel(String mobileChannel) {
		this.mobileChannel = mobileChannel;
	}


}
