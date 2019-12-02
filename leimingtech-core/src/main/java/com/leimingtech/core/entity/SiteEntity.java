package com.leimingtech.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**   
 * @Title: Entity
 * @Description: 站点管理
 * @author zhangdaihao
 * @date 2014-04-18 10:06:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cms_site", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SiteEntity extends IdEntity  implements java.io.Serializable {
 
	/**配置ID*/
	private java.lang.String configId;
	/**上传ftp*/
	private java.lang.String ftpUploadId;
	/**域名*/
	private java.lang.String domain;
	/**路径*/
	private java.lang.String sitePath;
	/**网站名称*/
	private java.lang.String siteName;
	/**简短名称*/
	private java.lang.String shortName;
	/**协议*/
	private java.lang.String protocol;
	/**动态页后缀*/
	private java.lang.String dynamicSuffix;
	/**静态页后缀*/
	private java.lang.String staticSuffix;
	/**静态页存放目录*/
	private java.lang.String staticDir;
	/**是否使用将首页放在根目录下*/
	private java.lang.String isIndexToRoot;
	/**是否静态化首页*/
	private java.lang.String isStaticIndex;
	/**后台本地化*/
	private java.lang.String localeAdmin;
	/**前台本地化*/
	private java.lang.String localeFront;
	/**模板方案*/
	private java.lang.String tplSolution;
	/**终审级别*/
	private java.lang.Integer finalStep;
	/**审核后(1:不能修改删除;2:修改后退回;3:修改后不变)*/
	private java.lang.Integer afterCheck;
	/**是否使用相对路径*/
	private java.lang.String isRelativePath;
	/**是否开启回收站*/
	private java.lang.String isRecycleOn;
	/**域名别名*/
	private java.lang.String domainAlias;
	/**域名重定向*/
	private java.lang.String domainRedirect;
	/**是否主站*/
	private java.lang.Integer isMaster;
	/**首页模板*/
	private String indexTemplate;
	/**创建时间*/
	private java.util.Date createdtime;
	/**会员连接ucenter是否开启*/
	private Integer ucenterisOpen;
	
	/**是否切换动静态（0代表静态1代表动态）*/
	private String isSwitch;
	/**
	 * 是否切换动静态（0代表静态1代表动态）
	 * @return
	 */
	@Column(name = "is_switch", nullable = false, length = 10)
	public String getIsSwitch() {
		return isSwitch;
	}

	public void setIsSwitch(String isSwitch) {
		this.isSwitch = isSwitch;
	}
	
	/**首页模板*/
	@Column(name ="INDEX_TEMPLATE",nullable=true,length=255)
	public String getIndexTemplate() {
		return indexTemplate;
	}

	/**首页模板*/
	public void setIndexTemplate(String indexTemplate) {
		this.indexTemplate = indexTemplate;
	}

	 
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  配置ID
	 */
	@Column(name ="CONFIG_ID",nullable=true,precision=10,scale=0)
	public java.lang.String getConfigId(){
		return this.configId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  配置ID
	 */
	public void setConfigId(java.lang.String configId){
		this.configId = configId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  上传ftp
	 */
	@Column(name ="FTP_UPLOAD_ID",nullable=true,precision=10,scale=0)
	public java.lang.String getFtpUploadId(){
		return this.ftpUploadId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  上传ftp
	 */
	public void setFtpUploadId(java.lang.String ftpUploadId){
		this.ftpUploadId = ftpUploadId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  域名
	 */
	@Column(name ="DOMAIN",nullable=false,length=50)
	public java.lang.String getDomain(){
		return this.domain;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  域名
	 */
	public void setDomain(java.lang.String domain){
		this.domain = domain;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  路径
	 */
	@Column(name ="SITE_PATH",nullable=false,length=20)
	public java.lang.String getSitePath(){
		return this.sitePath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  路径
	 */
	public void setSitePath(java.lang.String sitePath){
		this.sitePath = sitePath;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  网站名称
	 */
	@Column(name ="SITE_NAME",nullable=false,length=100)
	public java.lang.String getSiteName(){
		return this.siteName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  网站名称
	 */
	public void setSiteName(java.lang.String siteName){
		this.siteName = siteName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  简短名称
	 */
	@Column(name ="SHORT_NAME",nullable=false,length=100)
	public java.lang.String getShortName(){
		return this.shortName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  简短名称
	 */
	public void setShortName(java.lang.String shortName){
		this.shortName = shortName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  协议
	 */
	@Column(name ="PROTOCOL",nullable=false,length=20)
	public java.lang.String getProtocol(){
		return this.protocol;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  协议
	 */
	public void setProtocol(java.lang.String protocol){
		this.protocol = protocol;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  动态页后缀
	 */
	@Column(name ="DYNAMIC_SUFFIX",nullable=true,length=10)
	public java.lang.String getDynamicSuffix(){
		return this.dynamicSuffix;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  动态页后缀
	 */
	public void setDynamicSuffix(java.lang.String dynamicSuffix){
		this.dynamicSuffix = dynamicSuffix;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  静态页后缀
	 */
	@Column(name ="STATIC_SUFFIX",nullable=false,length=10)
	public java.lang.String getStaticSuffix(){
		return this.staticSuffix;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  静态页后缀
	 */
	public void setStaticSuffix(java.lang.String staticSuffix){
		this.staticSuffix = staticSuffix;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  静态页存放目录
	 */
	@Column(name ="STATIC_DIR",nullable=true,length=50)
	public java.lang.String getStaticDir(){
		return this.staticDir;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  静态页存放目录
	 */
	public void setStaticDir(java.lang.String staticDir){
		this.staticDir = staticDir;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否使用将首页放在根目录下
	 */
	@Column(name ="IS_INDEX_TO_ROOT",nullable=true,length=1)
	public java.lang.String getIsIndexToRoot(){
		return this.isIndexToRoot;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否使用将首页放在根目录下
	 */
	public void setIsIndexToRoot(java.lang.String isIndexToRoot){
		this.isIndexToRoot = isIndexToRoot;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否静态化首页
	 */
	@Column(name ="IS_STATIC_INDEX",nullable=true,length=1)
	public java.lang.String getIsStaticIndex(){
		return this.isStaticIndex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否静态化首页
	 */
	public void setIsStaticIndex(java.lang.String isStaticIndex){
		this.isStaticIndex = isStaticIndex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  后台本地化
	 */
	@Column(name ="LOCALE_ADMIN",nullable=true,length=10)
	public java.lang.String getLocaleAdmin(){
		return this.localeAdmin;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  后台本地化
	 */
	public void setLocaleAdmin(java.lang.String localeAdmin){
		this.localeAdmin = localeAdmin;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  前台本地化
	 */
	@Column(name ="LOCALE_FRONT",nullable=true,length=10)
	public java.lang.String getLocaleFront(){
		return this.localeFront;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  前台本地化
	 */
	public void setLocaleFront(java.lang.String localeFront){
		this.localeFront = localeFront;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模板方案
	 */
	@Column(name ="TPL_SOLUTION",nullable=true,length=50)
	public java.lang.String getTplSolution(){
		return this.tplSolution;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模板方案
	 */
	public void setTplSolution(java.lang.String tplSolution){
		this.tplSolution = tplSolution;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  终审级别
	 */
	@Column(name ="FINAL_STEP",nullable=false,precision=3,scale=0)
	public java.lang.Integer getFinalStep(){
		return this.finalStep;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  终审级别
	 */
	public void setFinalStep(java.lang.Integer finalStep){
		this.finalStep = finalStep;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审核后(1:不能修改删除;2:修改后退回;3:修改后不变)
	 */
	@Column(name ="AFTER_CHECK",nullable=false,precision=3,scale=0)
	public java.lang.Integer getAfterCheck(){
		return this.afterCheck;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核后(1:不能修改删除;2:修改后退回;3:修改后不变)
	 */
	public void setAfterCheck(java.lang.Integer afterCheck){
		this.afterCheck = afterCheck;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否使用相对路径
	 */
	@Column(name ="IS_RELATIVE_PATH",nullable=true,length=1)
	public java.lang.String getIsRelativePath(){
		return this.isRelativePath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否使用相对路径
	 */
	public void setIsRelativePath(java.lang.String isRelativePath){
		this.isRelativePath = isRelativePath;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否开启回收站
	 */
	@Column(name ="IS_RECYCLE_ON",nullable=true,length=1)
	public java.lang.String getIsRecycleOn(){
		return this.isRecycleOn;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否开启回收站
	 */
	public void setIsRecycleOn(java.lang.String isRecycleOn){
		this.isRecycleOn = isRecycleOn;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  域名别名
	 */
	@Column(name ="DOMAIN_ALIAS",nullable=true,length=255)
	public java.lang.String getDomainAlias(){
		return this.domainAlias;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  域名别名
	 */
	public void setDomainAlias(java.lang.String domainAlias){
		this.domainAlias = domainAlias;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  域名重定向
	 */
	@Column(name ="DOMAIN_REDIRECT",nullable=true,length=255)
	public java.lang.String getDomainRedirect(){
		return this.domainRedirect;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  域名重定向
	 */
	public void setDomainRedirect(java.lang.String domainRedirect){
		this.domainRedirect = domainRedirect;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否主站
	 */
	@Column(name ="IS_MASTER",nullable=true,precision=3,scale=0)
	public java.lang.Integer getIsMaster(){
		return this.isMaster;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否主站
	 */
	public void setIsMaster(java.lang.Integer isMaster){
		this.isMaster = isMaster;
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

	/**会员连接ucenter是否开启*/
	@Column(name = "ucenterisOpen", nullable = true, precision = 10, scale = 0)
	public Integer getUcenterisOpen() {
		return ucenterisOpen;
	}

	/**会员连接ucenter是否开启*/
	public void setUcenterisOpen(Integer ucenterisOpen) {
		this.ucenterisOpen = ucenterisOpen;
	}
	
	
}
