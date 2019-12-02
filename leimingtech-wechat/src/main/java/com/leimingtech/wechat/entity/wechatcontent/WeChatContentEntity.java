package com.leimingtech.wechat.entity.wechatcontent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 微信内容
 * @author 
 * @date 2015-09-11 14:47:11
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wechat_content", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class WeChatContentEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -6456581668907621596L;
	/** 内容标题 */
	private String weixinTitle;
	/** 内容 */
	private String weixinContent;
	/** 内容地址 */
	private String weixinContentaddress;
	/**内容摘要*/
	private String weixinDigest;


	/** 图片路径 */
	private String weixinPictureurl;
	/** 排序 */
	private String weixinSort;
	/** 是否是头条, 1:是,0:否 */
	private Integer weixinTop;
	/** contentId */
	private String contentId;
	/** 微信id */
	private String weixinId;
	/** 是否显示封面，1为显示，0为不显示 */
	private Integer showCoverPic;
	/** 作者 */
	private String weixinAuthor;
	/**创建时间*/
	private java.util.Date weixinCreateTime;


	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 内容标题
	 */
	@Column(name = "WEIXIN_TITLE", nullable = true, length = 255)
	public String getWeixinTitle() {
		return this.weixinTitle;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 内容标题
	 */
	public void setWeixinTitle(String weixinTitle) {
		this.weixinTitle = weixinTitle;
	}
	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 内容摘要
	 */
	@Column(name = "WEIXIN_DIGEST", nullable = true, length = 255)
	public String getWeixinDigest() {
		return weixinDigest;
	}
	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 内容摘要
	 */
	public void setWeixinDigest(String weixinDigest) {
		this.weixinDigest = weixinDigest;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 内容
	 */
	@Column(name = "WEIXIN_CONTENT", nullable = true)
	public String getWeixinContent() {
		return this.weixinContent;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 内容
	 */
	public void setWeixinContent(String weixinContent) {
		this.weixinContent = weixinContent;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 内容地址
	 */
	@Column(name = "WEIXIN_CONTENTADDRESS", nullable = true, length = 255)
	public String getWeixinContentaddress() {
		return this.weixinContentaddress;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 内容地址
	 */
	public void setWeixinContentaddress(String weixinContentaddress) {
		this.weixinContentaddress = weixinContentaddress;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 图片路径
	 */
	@Column(name = "WEIXIN_PICTUREURL", nullable = true, length = 255)
	public String getWeixinPictureurl() {
		return this.weixinPictureurl;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 图片路径
	 */
	public void setWeixinPictureurl(String weixinPictureurl) {
		this.weixinPictureurl = weixinPictureurl;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 排序
	 */
	@Column(name = "WEIXIN_SORT", nullable = true, length = 255)
	public String getWeixinSort() {
		return this.weixinSort;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 排序
	 */
	public void setWeixinSort(String weixinSort) {
		this.weixinSort = weixinSort;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否是头条, 1:是,0:否
	 */
	@Column(name = "WEIXIN_TOP", nullable = true, precision = 10, scale = 0)
	public Integer getWeixinTop() {
		return this.weixinTop;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否是头条, 1:是,0:否
	 */
	public void setWeixinTop(Integer weixinTop) {
		this.weixinTop = weixinTop;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String contentId
	 */
	@Column(name = "CONTENT_ID", nullable = true, length = 32)
	public String getContentId() {
		return this.contentId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String contentId
	 */
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 微信id
	 */
	@Column(name = "WEIXIN_ID", nullable = true, length = 32)
	public String getWeixinId() {
		return this.weixinId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 微信id
	 */
	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 是否显示封面，1为显示，0为不显示
	 */
	@Column(name = "SHOW_COVER_PIC", nullable = true, precision = 10, scale = 0)
	public Integer getShowCoverPic() {
		return this.showCoverPic;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 是否显示封面，1为显示，0为不显示
	 */
	public void setShowCoverPic(Integer showCoverPic) {
		this.showCoverPic = showCoverPic;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 作者
	 */
	@Column(name = "WEIXIN_AUTHOR", nullable = true, length = 255)
	public String getWeixinAuthor() {
		return this.weixinAuthor;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 作者
	 */
	public void setWeixinAuthor(String weixinAuthor) {
		this.weixinAuthor = weixinAuthor;
	}
	@Column(name = "WEIXIN_CREATETIME", nullable = true)
	public java.util.Date getWeixinCreateTime() {
		return weixinCreateTime;
	}

	public void setWeixinCreateTime(java.util.Date weixinCreateTime) {
		this.weixinCreateTime = weixinCreateTime;
	}
}
