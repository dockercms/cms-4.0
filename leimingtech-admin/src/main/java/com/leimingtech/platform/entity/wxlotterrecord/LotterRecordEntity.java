package com.leimingtech.platform.entity.wxlotterrecord;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 中奖记录
 * @author zhangdaihao
 * @date 2014-04-10 17:28:51
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_lottery_record", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")

public class LotterRecordEntity extends IdEntity  implements java.io.Serializable {
	 

	/**动活id*/
	private java.lang.String lid;
	/**用户使用次数*/
	private java.lang.Integer usenums;
	/**微信唯一识别码*/
	private java.lang.String wechaId;
	/**token*/
	private java.lang.String token;
	/**是否中奖(1中奖 0未中奖)*/
	private java.lang.Integer islottery;
	/**微信号*/
	private java.lang.String wechaName;
	/**机手号*/
	private java.lang.String phone;
	/**中奖后序列号*/
	private java.lang.String sn;
	/**中奖时间*/
	private Date time;
	/**已中奖项*/
	private java.lang.String prize;
	/**sendstutas*/
	private java.lang.Integer sendstutas;
	/**送发时间*/
	private Date sendtime;
	
	/**创建时间*/
	private java.util.Date createdtime;


	 
	/**
	 *方法: 取得java.lang.String
>>>>>>> .r35
	 *@return: java.lang.String  动活id
	 */
	@Column(name ="LID",nullable=true,length=32)
	public java.lang.String getLid(){
		return this.lid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  动活id
	 */
	public void setLid(java.lang.String lid){
		this.lid = lid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  用户使用次数
	 */
	@Column(name ="USENUMS",nullable=true,precision=3,scale=0)
	public java.lang.Integer getUsenums(){
		return this.usenums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  用户使用次数
	 */
	public void setUsenums(java.lang.Integer usenums){
		this.usenums = usenums;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  微信唯一识别码
	 */
	@Column(name ="WECHA_ID",nullable=true,length=60)
	public java.lang.String getWechaId(){
		return this.wechaId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  微信唯一识别码
	 */
	public void setWechaId(java.lang.String wechaId){
		this.wechaId = wechaId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  token
	 */
	@Column(name ="TOKEN",nullable=true,length=30)
	public java.lang.String getToken(){
		return this.token;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  token
	 */
	public void setToken(java.lang.String token){
		this.token = token;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否中奖
	 */
	@Column(name ="ISLOTTERY",nullable=true,precision=10,scale=0)
	public java.lang.Integer getIslottery(){
		return this.islottery;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否中奖
	 */
	public void setIslottery(java.lang.Integer islottery){
		this.islottery = islottery;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  微信号
	 */
	@Column(name ="WECHA_NAME",nullable=true,length=60)
	public java.lang.String getWechaName(){
		return this.wechaName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  微信号
	 */
	public void setWechaName(java.lang.String wechaName){
		this.wechaName = wechaName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机手号
	 */
	@Column(name ="PHONE",nullable=true,length=15)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机手号
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中奖后序列号
	 */
	@Column(name ="SN",nullable=true,length=13)
	public java.lang.String getSn(){
		return this.sn;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中奖后序列号
	 */
	public void setSn(java.lang.String sn){
		this.sn = sn;
	}
	/**
	 *方法: 取得Date
	 *@return: Date  time
	 */
	@Column(name ="TIME",nullable=true)
	public Date getTime(){
		return this.time;
	}

	/**
	 *方法: 设置Date
	 *@param: Date  time
	 */
	public void setTime(Date time){
		this.time = time;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  已中奖项
	 */
	@Column(name ="PRIZE",nullable=true,length=50)
	public java.lang.String getPrize(){
		return this.prize;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  已中奖项
	 */
	public void setPrize(java.lang.String prize){
		this.prize = prize;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  sendstutas
	 */
	@Column(name ="SENDSTUTAS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSendstutas(){
		return this.sendstutas;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  sendstutas
	 */
	public void setSendstutas(java.lang.Integer sendstutas){
		this.sendstutas = sendstutas;
	}
	/**
	 *方法: 取得Date
	 *@return: Date  送发时间
	 */
	@Column(name ="SENDTIME",nullable=true)
	public Date getSendtime(){
		return this.sendtime;
	}

	/**
	 *方法: 设置Date
	 *@param: Date  送发时间
	 */
	public void setSendtime(Date sendtime){
		this.sendtime = sendtime;
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
