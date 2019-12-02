package com.leimingtech.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

public class BeanApi implements java.io.Serializable{

	/**id*/
	private java.lang.String id;
	/***/
	private java.lang.String title;
	/***/
	private java.lang.String type;
	/***/
	private java.lang.String resultCode;
	/***/
	private java.lang.String resultMsg;
	/***/
	private java.lang.String pageCount;
	/***/
	private java.lang.String pageIndex;
	/***/
	private java.lang.String pageSize;
	/***/
	private java.lang.String pageItemsCount;
	/***/
	private java.lang.String listUrl;
	/***/
	private java.lang.String userId;
	/***/
	private java.lang.String userName;
	/***/
	private java.lang.String faceImg;
	private java.lang.String cardPath;
	/***/
	private java.lang.String email;
	/***/
	private java.lang.String tel;
	/***/
	private java.lang.String content;
	/**UCenter同步登陆代码*/
	private String ucsynlogin;
	/***/
	private List<BeanListApi> items = new ArrayList<BeanListApi>();
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	@Column(nullable=true)
	public java.lang.String getTitle() {
		return title;
	}
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	@Column(nullable=true)
	public java.lang.String getType() {
		return type;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	@Column(nullable=true)
	public java.lang.String getResultCode() {
		return resultCode;
	}
	public void setResultCode(java.lang.String resultCode) {
		this.resultCode = resultCode;
	}
	@Column(nullable=true)
	public java.lang.String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(java.lang.String resultMsg) {
		this.resultMsg = resultMsg;
	}
	@Column(nullable=true)
	public java.lang.String getPageCount() {
		return pageCount;
	}
	public void setPageCount(java.lang.String pageCount) {
		this.pageCount = pageCount;
	}
	@Column(nullable=true)
	public java.lang.String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(java.lang.String pageIndex) {
		this.pageIndex = pageIndex;
	}
	@Column(nullable=true)
	public java.lang.String getPageSize() {
		return pageSize;
	}
	public void setPageSize(java.lang.String pageSize) {
		this.pageSize = pageSize;
	}
	@Column(nullable=true)
	public java.lang.String getPageItemsCount() {
		return pageItemsCount;
	}
	public void setPageItemsCount(java.lang.String pageItemsCount) {
		this.pageItemsCount = pageItemsCount;
	}
	
	public List<BeanListApi> getItems() {
		return items;
	}
	public void setItems(List<BeanListApi> items) {
		this.items = items;
	}
	@Column(nullable=true)
	public java.lang.String getListUrl() {
		return listUrl;
	}
	public void setListUrl(java.lang.String listUrl) {
		this.listUrl = listUrl;
	}
	@Column(nullable=true)
	public java.lang.String getUserId() {
		return userId;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	@Column(nullable=true)
	public java.lang.String getUserName() {
		return userName;
	}
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	@Column(nullable=true)
	public java.lang.String getFaceImg() {
		return faceImg;
	}
	public void setFaceImg(java.lang.String faceImg) {
		this.faceImg = faceImg;
	}
	@Column(nullable=true)
	public java.lang.String getEmail() {
		return email;
	}
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	@Column(nullable=true)
	public java.lang.String getTel() {
		return tel;
	}
	public void setTel(java.lang.String tel) {
		this.tel = tel;
	}
	public String getUcsynlogin() {
		return ucsynlogin;
	}
	public void setUcsynlogin(String ucsynlogin) {
		this.ucsynlogin = ucsynlogin;
	}
	public java.lang.String getContent() {
		return content;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	public java.lang.String getCardPath() {
		return cardPath;
	}
	public void setCardPath(java.lang.String cardPath) {
		this.cardPath = cardPath;
	}
	
}
