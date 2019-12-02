package com.leimingtech.core.entity;

import com.leimingtech.core.entity.member.MemberEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 在线用户对象
 * 
 * @author JueYue
 * @date 2013-9-28
 * @version 1.0
 */
public class Client implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private TSUser user;

	private SiteEntity site;

	private MemberEntity member;

	private Map<String, TSFunction> functions;

	private Set<String> privurl;

	private Map<String, List<TSRoleFunction>> roleFunctionMap = new HashMap<String, List<TSRoleFunction>>();
	/**
	 * 用户IP
	 */
	private java.lang.String ip;
	/**
	 * 登录时间
	 */
	private java.util.Date logindatetime;
	
	/**pc栏目权限*/
	private Set<String> pcCatalogIds;
	/**移动栏目权限*/
	private Set<String> mobileCatalogIds;

	public TSUser getUser() {
		return user;
	}

	public void setUser(TSUser user) {
		this.user = user;
	}

	public Map<String, TSFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(Map<String, TSFunction> functions) {
		this.functions = functions;
	}

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public java.util.Date getLogindatetime() {
		return logindatetime;
	}

	public void setLogindatetime(java.util.Date logindatetime) {
		this.logindatetime = logindatetime;
	}

	public SiteEntity getSite() {
		return site;
	}

	public void setSite(SiteEntity site) {
		this.site = site;
	}

	public MemberEntity getMember() {
		return member;
	}

	public void setMember(MemberEntity member) {
		this.member = member;
	}

	public Set<String> getPrivurl() {
		return privurl;
	}

	public void setPrivurl(Set<String> privurl) {
		this.privurl = privurl;
	}

	public Map<String, List<TSRoleFunction>> getRoleFunctionMap() {
		return roleFunctionMap;
	}

	public void setRoleFunctionMap(Map<String, List<TSRoleFunction>> roleFunctionMap) {
		this.roleFunctionMap = roleFunctionMap;
	}

	public Set<String> getPcCatalogIds() {
		return pcCatalogIds;
	}

	public void setPcCatalogIds(Set<String> pcCatalogIds) {
		this.pcCatalogIds = pcCatalogIds;
	}

	public Set<String> getMobileCatalogIds() {
		return mobileCatalogIds;
	}

	public void setMobileCatalogIds(Set<String> mobileCatalogIds) {
		this.mobileCatalogIds = mobileCatalogIds;
	}
	
	

}
