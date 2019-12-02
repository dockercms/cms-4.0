package com.leimingtech.core.entity;


/**   
 * @Title: Entity
 * @Description: 教育局
 * @author linjm
 * @date 2014-04-15
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class NewsIndexEntity implements java.io.Serializable {
	
	/**文章标题*/
	private java.lang.String title;
	/**文章描述*/
	private java.lang.String description;
	/**文章路径*/
	private java.lang.String url;
	/**文章路径*/
	private java.lang.String wapurl;
	/**发布日期*/
	private java.lang.String publishdate;
	
	/**是否发布*/
	private java.lang.String published;
	/**文章内容*/
	private java.lang.String content;
	/**文章作者*/
	private java.lang.String author;
	/**文章标签*/
	private java.lang.String tags;



	public NewsIndexEntity() {
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	public java.lang.String getPublishdate() {
		return publishdate;
	}

	public void setPublishdate(java.lang.String publishdate) {
		this.publishdate = publishdate;
	}

	public java.lang.String getPublished() {
		return published;
	}

	public void setPublished(java.lang.String published) {
		this.published = published;
	}

	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public java.lang.String getAuthor() {
		return author;
	}

	public void setAuthor(java.lang.String author) {
		this.author = author;
	}

	public java.lang.String getWapurl() {
		return wapurl;
	}

	public void setWapurl(java.lang.String wapurl) {
		this.wapurl = wapurl;
	}
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "NewsIndexEntity [title=" + title + ", description=" + description + ", url=" + url + ", wapurl=" + wapurl + ", publishdate=" + publishdate + ", published=" + published + ", content=" + content + ", author=" + author + ",tags=" + tags + "]";
	}

 
}
