package com.leimingtech.core.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.leimingtech.core.entity.IdEntity;


/**   
 * @Title: Entity
 * @Description: 大转盘
 * @author zhangdaihao
 * @date 2014-04-08 18:53:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_lottery", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class LotteryEntity  extends IdEntity  implements java.io.Serializable {
	 
	/**参与人数*/
	private java.lang.Integer joinnum;
	/**click*/
	private java.lang.Integer click;
	/**token*/
	private java.lang.String token;
	/**键关词*/
	private java.lang.String keyword;
	/**填写活动开始图片网址*/
	private java.lang.String starpicurl;
	/**活动名称*/
	private java.lang.String title;
	/**用户输入兑奖时候的显示信息*/
	private java.lang.String txt;
	/**简介*/
	private java.lang.String sttxt;
	/**活动开始时间*/
	private Date statdate;
	/**活动结束时间*/
	private Date enddate;
	/**活动说明*/
	private java.lang.String info;
	/**重复抽奖回复*/
	private java.lang.String aginfo;
	/**活动结束公告主题*/
	private java.lang.String endtite;
	/**endpicurl*/
	private java.lang.String endpicurl;
	/**endinfo*/
	private java.lang.String endinfo;
	/**一等奖奖品设置*/
	private java.lang.String fist;
	/**一等奖奖品数量*/
	private java.lang.Integer fistnums;
	/**一等奖中奖号码*/
	private java.lang.Integer fistlucknums;
	/**二等奖奖品设置*/
	private java.lang.String second;
	/**类型（1代表大转盘、2代表刮刮卡、3代表砸金蛋）*/
	private java.lang.Integer type;
	/**secondnums*/
	private java.lang.Integer secondnums;
	/**secondlucknums*/
	private java.lang.Integer secondlucknums;
	/**third*/
	private java.lang.String third;
	/**thirdnums*/
	private java.lang.Integer thirdnums;
	/**thirdlucknums*/
	private java.lang.Integer thirdlucknums;
	/**allpeople*/
	private java.lang.Integer allpeople;
	/**个人限制抽奖次数*/
	private java.lang.Integer canrqnums;
	/**parssword*/
	private java.lang.Integer parssword;
	/**renamesn*/
	private java.lang.String renamesn;
	/**renametel*/
	private java.lang.String renametel;
	/**抽奖页面是否显示奖品数量 （1显示 0不显示）*/
	private java.lang.Integer displayjpnums;
	/**createdate*/
	private Date createdate;
	/**status*/
	private java.lang.Integer status;
	/**four*/
	private java.lang.String four;
	/**fournums*/
	private java.lang.Integer fournums;
	/**fourlucknums*/
	private java.lang.Integer fourlucknums;
	/**five*/
	private java.lang.String five;
	/**fivenums*/
	private java.lang.Integer fivenums;
	/**fivelucknums*/
	private java.lang.Integer fivelucknums;
	/**六等奖*/
	private java.lang.String six;
	/**sixnums*/
	private java.lang.Integer sixnums;
	/**sixlucknums*/
	private java.lang.Integer sixlucknums;
	/**zjpic*/
	private java.lang.String zjpic;
	/**活动展示页面地址*/
	private String url;
	/**站点id*/
	private String siteId;
	
	 
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  参与人数
	 */
	@Column(name ="JOINNUM",nullable=true,precision=10,scale=0)
	public java.lang.Integer getJoinnum(){
		return this.joinnum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  参与人数
	 */
	public void setJoinnum(java.lang.Integer joinnum){
		this.joinnum = joinnum;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  click
	 */
	@Column(name ="CLICK",nullable=true,precision=10,scale=0)
	public java.lang.Integer getClick(){
		return this.click;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  click
	 */
	public void setClick(java.lang.Integer click){
		this.click = click;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  键关词
	 */
	@Column(name ="KEYWORD",nullable=true,length=10)
	public java.lang.String getKeyword(){
		return this.keyword;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  键关词
	 */
	public void setKeyword(java.lang.String keyword){
		this.keyword = keyword;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  填写活动开始图片网址
	 */
	@Column(name ="STARPICURL",nullable=true,length=255)
	public java.lang.String getStarpicurl(){
		return this.starpicurl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  填写活动开始图片网址
	 */
	public void setStarpicurl(java.lang.String starpicurl){
		this.starpicurl = starpicurl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动名称
	 */
	@Column(name ="TITLE",nullable=true,length=60)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动名称
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户输入兑奖时候的显示信息
	 */
	@Column(name ="TXT",nullable=true,length=60)
	public java.lang.String getTxt(){
		return this.txt;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户输入兑奖时候的显示信息
	 */
	public void setTxt(java.lang.String txt){
		this.txt = txt;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  简介
	 */
	@Column(name ="STTXT",nullable=true,length=200)
	public java.lang.String getSttxt(){
		return this.sttxt;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  简介
	 */
	public void setSttxt(java.lang.String sttxt){
		this.sttxt = sttxt;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  活动开始时间
	 */
	@Column(name ="STATDATE",nullable=true)
	public Date getStatdate(){
		return this.statdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  活动开始时间
	 */
	public void setStatdate(Date statdate){
		this.statdate = statdate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  活动结束时间
	 */
	@Column(name ="ENDDATE",nullable=true)
	public Date getEnddate(){
		return this.enddate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  活动结束时间
	 */
	public void setEnddate(Date enddate){
		this.enddate = enddate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动说明
	 */
	@Column(name ="INFO",nullable=true,length=200)
	public java.lang.String getInfo(){
		return this.info;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动说明
	 */
	public void setInfo(java.lang.String info){
		this.info = info;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  重复抽奖回复
	 */
	@Column(name ="AGINFO",nullable=true,length=200)
	public java.lang.String getAginfo(){
		return this.aginfo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  重复抽奖回复
	 */
	public void setAginfo(java.lang.String aginfo){
		this.aginfo = aginfo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动结束公告主题
	 */
	@Column(name ="ENDTITE",nullable=true,length=60)
	public java.lang.String getEndtite(){
		return this.endtite;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动结束公告主题
	 */
	public void setEndtite(java.lang.String endtite){
		this.endtite = endtite;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  endpicurl
	 */
	@Column(name ="ENDPICURL",nullable=true,length=255)
	public java.lang.String getEndpicurl(){
		return this.endpicurl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  endpicurl
	 */
	public void setEndpicurl(java.lang.String endpicurl){
		this.endpicurl = endpicurl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  endinfo
	 */
	@Column(name ="ENDINFO",nullable=true,length=255)
	public java.lang.String getEndinfo(){
		return this.endinfo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  endinfo
	 */
	public void setEndinfo(java.lang.String endinfo){
		this.endinfo = endinfo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  一等奖奖品设置
	 */
	@Column(name ="FIST",nullable=true,length=50)
	public java.lang.String getFist(){
		return this.fist;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  一等奖奖品设置
	 */
	public void setFist(java.lang.String fist){
		this.fist = fist;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  一等奖奖品数量
	 */
	@Column(name ="FISTNUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getFistnums(){
		return this.fistnums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  一等奖奖品数量
	 */
	public void setFistnums(java.lang.Integer fistnums){
		this.fistnums = fistnums;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  一等奖中奖号码
	 */
	@Column(name ="FISTLUCKNUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getFistlucknums(){
		return this.fistlucknums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  一等奖中奖号码
	 */
	public void setFistlucknums(java.lang.Integer fistlucknums){
		this.fistlucknums = fistlucknums;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  二等奖奖品设置
	 */
	@Column(name ="SECOND",nullable=true,length=50)
	public java.lang.String getSecond(){
		return this.second;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  二等奖奖品设置
	 */
	public void setSecond(java.lang.String second){
		this.second = second;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer 类型（1代表大转盘、2代表刮刮卡、3代表砸金蛋）
	 */
	@Column(name ="TYPE",nullable=true,precision=3,scale=0)
	public java.lang.Integer getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  类型（1代表大转盘、2代表刮刮卡、3代表砸金蛋）
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  secondnums
	 */
	@Column(name ="SECONDNUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSecondnums(){
		return this.secondnums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  secondnums
	 */
	public void setSecondnums(java.lang.Integer secondnums){
		this.secondnums = secondnums;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  secondlucknums
	 */
	@Column(name ="SECONDLUCKNUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSecondlucknums(){
		return this.secondlucknums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  secondlucknums
	 */
	public void setSecondlucknums(java.lang.Integer secondlucknums){
		this.secondlucknums = secondlucknums;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  third
	 */
	@Column(name ="THIRD",nullable=true,length=50)
	public java.lang.String getThird(){
		return this.third;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  third
	 */
	public void setThird(java.lang.String third){
		this.third = third;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  thirdnums
	 */
	@Column(name ="THIRDNUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getThirdnums(){
		return this.thirdnums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  thirdnums
	 */
	public void setThirdnums(java.lang.Integer thirdnums){
		this.thirdnums = thirdnums;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  thirdlucknums
	 */
	@Column(name ="THIRDLUCKNUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getThirdlucknums(){
		return this.thirdlucknums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  thirdlucknums
	 */
	public void setThirdlucknums(java.lang.Integer thirdlucknums){
		this.thirdlucknums = thirdlucknums;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  allpeople
	 */
	@Column(name ="ALLPEOPLE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getAllpeople(){
		return this.allpeople;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  allpeople
	 */
	public void setAllpeople(java.lang.Integer allpeople){
		this.allpeople = allpeople;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  个人限制抽奖次数
	 */
	@Column(name ="CANRQNUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getCanrqnums(){
		return this.canrqnums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  个人限制抽奖次数
	 */
	public void setCanrqnums(java.lang.Integer canrqnums){
		this.canrqnums = canrqnums;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  parssword
	 */
	@Column(name ="PARSSWORD",nullable=true,precision=10,scale=0)
	public java.lang.Integer getParssword(){
		return this.parssword;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  parssword
	 */
	public void setParssword(java.lang.Integer parssword){
		this.parssword = parssword;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  renamesn
	 */
	@Column(name ="RENAMESN",nullable=true,length=50)
	public java.lang.String getRenamesn(){
		return this.renamesn;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  renamesn
	 */
	public void setRenamesn(java.lang.String renamesn){
		this.renamesn = renamesn;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  renametel
	 */
	@Column(name ="RENAMETEL",nullable=true,length=60)
	public java.lang.String getRenametel(){
		return this.renametel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  renametel
	 */
	public void setRenametel(java.lang.String renametel){
		this.renametel = renametel;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  displayjpnums
	 */
	@Column(name ="DISPLAYJPNUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getDisplayjpnums(){
		return this.displayjpnums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  displayjpnums
	 */
	public void setDisplayjpnums(java.lang.Integer displayjpnums){
		this.displayjpnums = displayjpnums;
	}
	/**
	 *方法: 取得Date
	 *@return: Date  createdate
	 */
	@Column(name ="CREATEDATE",nullable=true)
	public Date getCreatedate(){
		return this.createdate;
	}

	/**
	 *方法: 设置Date
	 *@param: Date  createdate
	 */
	public void setCreatedate(Date createdate){
		this.createdate = createdate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  status
	 */
	@Column(name ="STATUS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  status
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  four
	 */
	@Column(name ="FOUR",nullable=true,length=50)
	public java.lang.String getFour(){
		return this.four;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  four
	 */
	public void setFour(java.lang.String four){
		this.four = four;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  fournums
	 */
	@Column(name ="FOURNUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getFournums(){
		return this.fournums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  fournums
	 */
	public void setFournums(java.lang.Integer fournums){
		this.fournums = fournums;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  fourlucknums
	 */
	@Column(name ="FOURLUCKNUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getFourlucknums(){
		return this.fourlucknums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  fourlucknums
	 */
	public void setFourlucknums(java.lang.Integer fourlucknums){
		this.fourlucknums = fourlucknums;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  five
	 */
	@Column(name ="FIVE",nullable=true,length=50)
	public java.lang.String getFive(){
		return this.five;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  five
	 */
	public void setFive(java.lang.String five){
		this.five = five;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  fivenums
	 */
	@Column(name ="FIVENUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getFivenums(){
		return this.fivenums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  fivenums
	 */
	public void setFivenums(java.lang.Integer fivenums){
		this.fivenums = fivenums;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  fivelucknums
	 */
	@Column(name ="FIVELUCKNUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getFivelucknums(){
		return this.fivelucknums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  fivelucknums
	 */
	public void setFivelucknums(java.lang.Integer fivelucknums){
		this.fivelucknums = fivelucknums;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  六等奖
	 */
	@Column(name ="SIX",nullable=true,length=50)
	public java.lang.String getSix(){
		return this.six;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  六等奖
	 */
	public void setSix(java.lang.String six){
		this.six = six;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  sixnums
	 */
	@Column(name ="SIXNUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSixnums(){
		return this.sixnums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  sixnums
	 */
	public void setSixnums(java.lang.Integer sixnums){
		this.sixnums = sixnums;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  sixlucknums
	 */
	@Column(name ="SIXLUCKNUMS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSixlucknums(){
		return this.sixlucknums;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  sixlucknums
	 */
	public void setSixlucknums(java.lang.Integer sixlucknums){
		this.sixlucknums = sixlucknums;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  zjpic
	 */
	@Column(name ="ZJPIC",nullable=true,length=150)
	public java.lang.String getZjpic(){
		return this.zjpic;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  zjpic
	 */
	public void setZjpic(java.lang.String zjpic){
		this.zjpic = zjpic;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  url 活动展示页面地址
	 */
	@Column(name ="URL",nullable=true,length=255)
	public String getUrl() {
		return url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  url 活动展示页面地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  站点id
	 */
	@Column(name ="SITE_ID",nullable=true,length=32)
	public String getSiteId() {
		return siteId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer 站点id
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
}
