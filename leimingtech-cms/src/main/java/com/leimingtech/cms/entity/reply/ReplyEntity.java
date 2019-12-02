package com.leimingtech.cms.entity.reply;

import com.leimingtech.core.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**   
 * @Title: Entity
 * @Description: 跟帖
 * @author 
 * @date 2017-03-27 19:11:04
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_reply", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ReplyEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -6717650641931484892L;
	/** 跟帖人 */
	private java.lang.String replyperson;
	/** 跟帖时间 */
	private java.util.Date replytime;
	/** 跟帖内容  */
	private java.lang.String replycontent;
	/** 站点Id */
	private java.lang.String siteid;
	/** 点赞数 */
	private java.lang.Integer supportcount;
	/** 贬赞数  */
	private java.lang.Integer opposecount;
	/** 审核状态 */
	private java.lang.String ischeck;
	/** 审核人Id */
	private java.lang.String auditorid;
	/** 审核人 */
	private java.lang.String auditorname;
	/** 审核时间 */
	private java.util.Date auditorcreatetime;
	/** 回复人 */
	private java.lang.String returnperson;
	/** 回复Id */
	private java.lang.String returnid;
	/** 回复内容 */
	private java.lang.String returncontent;
	/** 回复时间 */
	private java.util.Date returntime;

    /**被跟的帖子的id*/
    private String replyId;

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    /**跟帖人ID*/
    private String replyPersonId;

    public String getReplyPersonId() {
        return replyPersonId;
    }

    public void setReplyPersonId(String replyPersonId) {
        this.replyPersonId = replyPersonId;
    }

    /**评论的id*/
    private String commentId;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    /**跟帖中的回复数量*/
    private Integer returncount;

    public Integer getReturncount() { return returncount;}
    public void setReturncount(Integer returncount) {this.returncount = returncount;}

    /**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 跟帖人
	 */
	@Column(name = "REPLYPERSON", nullable = true, length = 255)
	public java.lang.String getReplyperson() {
		return this.replyperson;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 跟帖人
	 */
	public void setReplyperson(java.lang.String replyperson) {
		this.replyperson = replyperson;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 跟帖时间
	 */
	@Column(name = "REPLYTIME", nullable = true)
	public java.util.Date getReplytime() {
		return this.replytime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 跟帖时间
	 */
	public void setReplytime(java.util.Date replytime) {
		this.replytime = replytime;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 跟帖内容 
	 */
	@Column(name = "REPLYCONTENT", nullable = true, length = 255)
	public java.lang.String getReplycontent() {
		return this.replycontent;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 跟帖内容 
	 */
	public void setReplycontent(java.lang.String replycontent) {
		this.replycontent = replycontent;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 站点Id
	 */
	@Column(name = "SITEID", nullable = true, length = 32)
	public java.lang.String getSiteid() {
		return this.siteid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 站点Id
	 */
	public void setSiteid(java.lang.String siteid) {
		this.siteid = siteid;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 点赞数
	 */
	@Column(name = "SUPPORTCOUNT", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getSupportcount() {
		return this.supportcount;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 点赞数
	 */
	public void setSupportcount(java.lang.Integer supportcount) {
		this.supportcount = supportcount;
	}
	
	/**
	 * 方法: 取得java.lang.Integer
	 *
	 * @return: java.lang.Integer 贬赞数 
	 */
	@Column(name = "OPPOSECOUNT", nullable = true, precision = 10, scale = 0)
	public java.lang.Integer getOpposecount() {
		return this.opposecount;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 *
	 * @param: java.lang.Integer 贬赞数 
	 */
	public void setOpposecount(java.lang.Integer opposecount) {
		this.opposecount = opposecount;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 审核状态
	 */
	@Column(name = "ISCHECK", nullable = true, length = 10)
	public java.lang.String getIscheck() {
		return this.ischeck;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 审核状态
	 */
	public void setIscheck(java.lang.String ischeck) {
		this.ischeck = ischeck;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 审核人Id
	 */
	@Column(name = "AUDITORID", nullable = true, length = 32)
	public java.lang.String getAuditorid() {
		return this.auditorid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 审核人Id
	 */
	public void setAuditorid(java.lang.String auditorid) {
		this.auditorid = auditorid;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 审核人
	 */
	@Column(name = "AUDITORNAME", nullable = true, length = 255)
	public java.lang.String getAuditorname() {
		return this.auditorname;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 审核人
	 */
	public void setAuditorname(java.lang.String auditorname) {
		this.auditorname = auditorname;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 审核时间
	 */
	@Column(name = "AUDITORCREATETIME", nullable = true)
	public java.util.Date getAuditorcreatetime() {
		return this.auditorcreatetime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 审核时间
	 */
	public void setAuditorcreatetime(java.util.Date auditorcreatetime) {
		this.auditorcreatetime = auditorcreatetime;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 回复人
	 */
	@Column(name = "RETURNPERSON", nullable = true, length = 255)
	public java.lang.String getReturnperson() {
		return this.returnperson;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 回复人
	 */
	public void setReturnperson(java.lang.String returnperson) {
		this.returnperson = returnperson;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 回复Id
	 */
	@Column(name = "RETURNID", nullable = true, length = 32)
	public java.lang.String getReturnid() {
		return this.returnid;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 回复Id
	 */
	public void setReturnid(java.lang.String returnid) {
		this.returnid = returnid;
	}
	
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 回复内容
	 */
	@Column(name = "RETURNCONTENT", nullable = true, length = 255)
	public java.lang.String getReturncontent() {
		return this.returncontent;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 回复内容
	 */
	public void setReturncontent(java.lang.String returncontent) {
		this.returncontent = returncontent;
	}
	
	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 回复时间
	 */
	@Column(name = "RETURNTIME", nullable = true)
	public java.util.Date getReturntime() {
		return this.returntime;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 回复时间
	 */
	public void setReturntime(java.util.Date returntime) {
		this.returntime = returntime;
	}
}
