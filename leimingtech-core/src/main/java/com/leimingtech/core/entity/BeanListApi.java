package com.leimingtech.core.entity;

import java.util.List;
import java.util.Map;

public class BeanListApi implements java.io.Serializable{

	/**id*/
	private java.lang.String id;
	/**标题*/
	private java.lang.String title;
	/***/
	private java.lang.String type;
	/***/
	private java.lang.String content;
	/***/
	private java.lang.String topUrl;
	/***/
	private java.lang.String listImage;
	/***/
	private java.lang.String listUrl;
	/***/
	private java.lang.String pubDate;
	/***/
	private java.lang.String star;
	/***/
	private java.lang.String source;
	/***/
	private java.lang.String pageLink;
	/**组图中所有展示的图片 红河接口中使用，Android1.1版本以后被废弃<br/> 推荐使用allDetialPicture 包含图片描述信息*/
	@Deprecated
	private java.lang.String pictureAll;//
	/***/
	private java.lang.String commentCount;
	/***/
	private java.lang.String invitationCount;
	/***/
	private java.lang.String contentMark;
	/***/
	private java.lang.String contentType;
	/***/
	private java.lang.String userName;
	/***/
	private java.lang.String isposter;
	/**专题头图*/
	private String topImage;
	
	/**组图中所有展示的图片、包含图片描述 2015年4月15日 11:42:02新增 */
	private List<Map<String,Object>> allDetailPicture;
	
	/**头条*/
	private java.lang.Integer channelTop;
	
	/**热门*/
	private java.lang.Integer channelHot;
	/*创建时间*/
	private java.util.Date created;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getTitle() {
		return title;
	}
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	public java.lang.String getType() {
		return type;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	public java.lang.String getContent() {
		return content;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	public java.lang.String getTopUrl() {
		return topUrl;
	}
	public void setTopUrl(java.lang.String topUrl) {
		this.topUrl = topUrl;
	}
	
	public java.lang.String getListImage() {
		return listImage;
	}
	public void setListImage(java.lang.String listImage) {
		this.listImage = listImage;
	}
	public java.lang.String getListUrl() {
		return listUrl;
	}
	public void setListUrl(java.lang.String listUrl) {
		this.listUrl = listUrl;
	}
	public java.lang.String getPubDate() {
		return pubDate;
	}
	public void setPubDate(java.lang.String pubDate) {
		this.pubDate = pubDate;
	}
	public java.lang.String getStar() {
		return star;
	}
	public void setStar(java.lang.String star) {
		this.star = star;
	}
	public java.lang.String getSource() {
		return source;
	}
	public void setSource(java.lang.String source) {
		this.source = source;
	}
	public java.lang.String getPageLink() {
		return pageLink;
	}
	public void setPageLink(java.lang.String pageLink) {
		this.pageLink = pageLink;
	}
	public java.lang.String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(java.lang.String commentCount) {
		this.commentCount = commentCount;
	}
	/**组图中所有展示的图片 红河接口中使用，Android1.1版本以后被废弃<br/> 推荐使用allDetialPicture 包含图片描述信息*/
	@Deprecated
	public java.lang.String getPictureAll() {
		return pictureAll;
	}
	/**组图中所有展示的图片 红河接口中使用，Android1.1版本以后被废弃<br/> 推荐使用allDetialPicture 包含图片描述信息*/
	@Deprecated
	public void setPictureAll(java.lang.String pictureAll) {
		this.pictureAll = pictureAll;
	}
	public java.lang.String getInvitationCount() {
		return invitationCount;
	}
	public void setInvitationCount(java.lang.String invitationCount) {
		this.invitationCount = invitationCount;
	}
	public java.lang.String getContentMark() {
		return contentMark;
	}
	public void setContentMark(java.lang.String contentMark) {
		this.contentMark = contentMark;
	}
	public java.lang.String getUserName() {
		return userName;
	}
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	public java.lang.String getContentType() {
		return contentType;
	}
	public void setContentType(java.lang.String contentType) {
		this.contentType = contentType;
	}
	public java.lang.String getIsposter() {
		return isposter;
	}
	public void setIsposter(java.lang.String isposter) {
		this.isposter = isposter;
	}
	/**组图中所有展示的图片、包含图片描述 2015年4月15日 11:42:02新增 */
	public List<Map<String, Object>> getAllDetailPicture() {
		return allDetailPicture;
	}
	/**组图中所有展示的图片、包含图片描述 2015年4月15日 11:42:02新增 */
	public void setAllDetailPicture(List<Map<String, Object>> allDetailPicture) {
		this.allDetailPicture = allDetailPicture;
	}
	/**专题头图*/
	public String getTopImage() {
		return topImage;
	}
	/**专题头图*/
	public void setTopImage(String topImage) {
		this.topImage = topImage;
	}
	
	public java.lang.Integer getChannelTop() {
		return channelTop;
	}
	public void setChannelTop(java.lang.Integer channelTop) {
		this.channelTop = channelTop;
	}
	public java.lang.Integer getChannelHot() {
		return channelHot;
	}
	public void setChannelHot(java.lang.Integer channelHot) {
		this.channelHot = channelHot;
	}
	public java.util.Date getCreated() {
		return created;
	}
	public void setCreated(java.util.Date created) {
		this.created = created;
	}
}
