package com.leimingtech.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 投票
 * @author zhangdaihao
 * @date 2014-04-29 11:01:55
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cm_vote", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class VoteMobileEntity extends IdEntity  implements java.io.Serializable {
	 
	/**内容ID*/
	private java.lang.String contentid;
	/**投票类型*/
	private java.lang.String votetype;
	/**投票模式*/
	private java.lang.String votepattern;
	/**介绍*/
	private java.lang.String voteintroduce;
	/**IP限制*/
	private java.lang.String voteiplimit;
	/**是否省份限制*/
	private java.lang.String voteprovincelimit;
	/**开始时间*/
	private java.util.Date votestarttime;
	/**结束时间*/
	private java.util.Date voteendtime;
	/**总票数*/
	private java.lang.Integer votetotal;
	/**时间间隔*/
	private java.lang.String votemininterval;
	/**最多票数*/
	private java.lang.Integer maxvotes;
	/**站点id*/
	private java.lang.String siteid;
	/**投票ID*/
	private java.lang.String voteid;
	/**选项集合*/
	private List<VoteOptionEntity> voteOptionList = new ArrayList<VoteOptionEntity>();
	
	/**创建时间*/
	private java.util.Date createdtime;
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "voteid")
	public List<VoteOptionEntity> getVoteOptionList() {
		return voteOptionList;
	}

	public void setVoteOptionList(List<VoteOptionEntity> voteOptionList) {
		this.voteOptionList = voteOptionList;
	}

	 
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容ID
	 */
	@Column(name ="CONTENTID",nullable=true,length=32)
	public java.lang.String getContentid(){
		return this.contentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容ID
	 */
	public void setContentid(java.lang.String contentid){
		this.contentid = contentid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  投票类型
	 */
	@Column(name ="VOTETYPE",nullable=true,length=255)
	public java.lang.String getVotetype(){
		return this.votetype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  投票类型
	 */
	public void setVotetype(java.lang.String votetype){
		this.votetype = votetype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  投票模式
	 */
	@Column(name ="VOTEPATTERN",nullable=true,length=255)
	public java.lang.String getVotepattern(){
		return this.votepattern;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  投票模式
	 */
	public void setVotepattern(java.lang.String votepattern){
		this.votepattern = votepattern;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  介绍
	 */
	@Column(name ="VOTEINTRODUCE",nullable=true,length=255)
	public java.lang.String getVoteintroduce(){
		return this.voteintroduce;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  介绍
	 */
	public void setVoteintroduce(java.lang.String voteintroduce){
		this.voteintroduce = voteintroduce;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  IP限制
	 */
	@Column(name ="VOTEIPLIMIT",nullable=true,length=255)
	public java.lang.String getVoteiplimit(){
		return this.voteiplimit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  IP限制
	 */
	public void setVoteiplimit(java.lang.String voteiplimit){
		this.voteiplimit = voteiplimit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否省份限制
	 */
	@Column(name ="VOTEPROVINCELIMIT",nullable=true,length=255)
	public java.lang.String getVoteprovincelimit(){
		return this.voteprovincelimit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否省份限制
	 */
	public void setVoteprovincelimit(java.lang.String voteprovincelimit){
		this.voteprovincelimit = voteprovincelimit;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始时间
	 */
	@Column(name ="VOTESTARTTIME",nullable=true)
	public java.util.Date getVotestarttime(){
		return this.votestarttime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开始时间
	 */
	public void setVotestarttime(java.util.Date votestarttime){
		this.votestarttime = votestarttime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束时间
	 */
	@Column(name ="VOTEENDTIME",nullable=true)
	public java.util.Date getVoteendtime(){
		return this.voteendtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结束时间
	 */
	public void setVoteendtime(java.util.Date voteendtime){
		this.voteendtime = voteendtime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  总票数
	 */
	@Column(name ="VOTETOTAL",nullable=true,precision=10,scale=0)
	public java.lang.Integer getVotetotal(){
		return this.votetotal;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  总票数
	 */
	public void setVotetotal(java.lang.Integer votetotal){
		this.votetotal = votetotal;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  时间间隔
	 */
	@Column(name ="VOTEMININTERVAL",nullable=true,length=255)
	public java.lang.String getVotemininterval(){
		return this.votemininterval;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  时间间隔
	 */
	public void setVotemininterval(java.lang.String votemininterval){
		this.votemininterval = votemininterval;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  最多票数
	 */
	@Column(name ="MAXVOTES",nullable=true,precision=10,scale=0)
	public java.lang.Integer getMaxvotes(){
		return this.maxvotes;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  最多票数
	 */
	public void setMaxvotes(java.lang.Integer maxvotes){
		this.maxvotes = maxvotes;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点id
	 */
	@Column(name ="SITE_ID",nullable=true,length=32)
	public java.lang.String getSiteid() {
		return siteid;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点id
	 */
	public void setSiteid(java.lang.String siteid) {
		this.siteid = siteid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  投票ID
	 */
	@Column(name ="VOTEID",nullable=true,length=32)
	public java.lang.String getVoteid(){
		return this.voteid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  投票ID
	 */
	public void setVoteid(java.lang.String voteid){
		this.voteid = voteid;
	}
	/**
	 *方法: 取得java.lang.Date
	 *@return: java.lang.Date  创建时间
	 */
	@Column(name = "CREATEDTIME", nullable = true)
	public java.util.Date getCreatedtime() {
		return createdtime;
	}
	/**
	 *方法: 设置java.lang.Date
	 *@return: java.lang.Date  创建时间
	 */
	public void setCreatedtime(java.util.Date createdtime) {
		this.createdtime = createdtime;
	}
}
