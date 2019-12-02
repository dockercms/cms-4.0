package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 留言板
 * @author 
 * @date 2016-04-26 15:41:54
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_message_board", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class MessageBoardEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -7974289869920103000L;
	/** 留言板名称 */
	private java.lang.String nameBoard;
	/** 别名 */
	private java.lang.String code;
	/** 留言板描述 */
	private java.lang.String description;
	/** 是否开放留言(0:否1:是) */
	private java.lang.String isOpen;
	/** 留言是否登录(0:否1:是) */
	private java.lang.String messageLogin;
	/** 条数 */
	private java.lang.String numbers;
	/** 时间 */
	private java.lang.String time;
	/** 时间选择 */
	private java.lang.String timeSelect;
	/** 是否替换(敏感词0:否1:是) */
	private java.lang.String isReplace;
	/** 替换词 */
	private java.lang.String replaceWord;
	/** 创建时间 */
	private java.util.Date createtime;
	/** 站点Id */
	private java.lang.String siteId;
	/**排序*/
	private java.lang.String sort;
	/**限制时间*/
	private java.util.Date timeLimit;
	
	@Column(name = "SORT", nullable = true, length = 10)
	public java.lang.String getSort() {
		return sort;
	}

	public void setSort(java.lang.String sort) {
		this.sort = sort;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 留言板名称
	 */
	@Column(name = "NAME_BOARD", nullable = true, length = 50)
	public java.lang.String getNameBoard() {
		return this.nameBoard;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 留言板名称
	 */
	public void setNameBoard(java.lang.String nameBoard) {
		this.nameBoard = nameBoard;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 别名
	 */
	@Column(name = "CODE", nullable = true, length = 50)
	public java.lang.String getCode() {
		return this.code;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 别名
	 */
	public void setCode(java.lang.String code) {
		this.code = code;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 留言板描述
	 */
	@Column(name = "DESCRIPTION", nullable = true, length = 300)
	public java.lang.String getDescription() {
		return this.description;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 留言板描述
	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 是否开放留言(0:否1:是)
	 */
	@Column(name = "IS_OPEN", nullable = true, length = 10)
	public java.lang.String getIsOpen() {
		return this.isOpen;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 是否开放留言(0:否1:是)
	 */
	public void setIsOpen(java.lang.String isOpen) {
		this.isOpen = isOpen;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 留言是否登录(0:否1:是)
	 */
	@Column(name = "MESSAGE_LOGIN", nullable = true, length = 10)
	public java.lang.String getMessageLogin() {
		return this.messageLogin;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 留言是否登录(0:否1:是)
	 */
	public void setMessageLogin(java.lang.String messageLogin) {
		this.messageLogin = messageLogin;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 条数
	 */
	@Column(name = "NUMBERS", nullable = true, length = 50)
	public java.lang.String getNumbers() {
		return this.numbers;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 条数
	 */
	public void setNumbers(java.lang.String numbers) {
		this.numbers = numbers;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 时间
	 */
	@Column(name = "TIME", nullable = true, length = 50)
	public java.lang.String getTime() {
		return this.time;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 时间
	 */
	public void setTime(java.lang.String time) {
		this.time = time;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 时间选择
	 */
	@Column(name = "TIME_SELECT", nullable = true, length = 50)
	public java.lang.String getTimeSelect() {
		return this.timeSelect;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 时间选择
	 */
	public void setTimeSelect(java.lang.String timeSelect) {
		this.timeSelect = timeSelect;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 是否替换(敏感词0:否1:是)
	 */
	@Column(name = "IS_REPLACE", nullable = true, length = 10)
	public java.lang.String getIsReplace() {
		return this.isReplace;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 是否替换(敏感词0:否1:是)
	 */
	public void setIsReplace(java.lang.String isReplace) {
		this.isReplace = isReplace;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 替换词
	 */
	@Column(name = "REPLACE_WORD", nullable = true, length = 50)
	public java.lang.String getReplaceWord() {
		return this.replaceWord;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 替换词
	 */
	public void setReplaceWord(java.lang.String replaceWord) {
		this.replaceWord = replaceWord;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATETIME", nullable = true)
	public java.util.Date getCreatetime() {
		return this.createtime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 创建时间
	 */
	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 站点Id
	 */
	@Column(name = "SITE_ID", nullable = true, length = 32)
	public java.lang.String getSiteId() {
		return this.siteId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 站点Id
	 */
	public void setSiteId(java.lang.String siteId) {
		this.siteId = siteId;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 限制时间
	 */
	@Column(name = "TIME_LIMIT", nullable = true)
	public java.util.Date getTimeLimit() {
		return this.timeLimit;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 限制时间
	 */
	public void setTimeLimit(java.util.Date timeLimit) {
		this.timeLimit = timeLimit;
	}
}
