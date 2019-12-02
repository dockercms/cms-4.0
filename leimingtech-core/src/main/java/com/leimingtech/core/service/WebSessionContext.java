package com.leimingtech.core.service;

import java.util.Set;

import javax.servlet.http.HttpSession;

import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.SiteEntity;


/**
 * THREADLOCAL变量本地化
 * @author LKANG
 */
public interface WebSessionContext<T> {
	public static String sessionAttributeKey = "LMSessionKey";
	
	/**
	 * 获取session
	 * @return
	 */
	public abstract HttpSession getSession();
	
	/**
	 * 给session赋值
	 * @param session
	 */
	public abstract void setSession(HttpSession session);
	
	/**
	 * 初始化session
	 */
	public abstract void invalidateSession();
	
	/**
	 * 填充参数
	 * @param name
	 * @param value
	 */
	public abstract void setAttribute(String name, T value);
	
	/**
	 * 获取参数
	 * @param name
	 * @return
	 */
	public abstract T getAttribute(String name);
	
	/**
	 * 获取所有键
	 * @return
	 */
	public abstract Set<T> getAttributeNames();
	
	/**
	 * 移除参数
	 * @param name
	 */
	public abstract void removeAttribute(String name);
	
	/**
	 * 消毁session
	 */
	public abstract void destory();
	
	/**
	 * 保存站点信息
	 * @param site
	 */
	public abstract void setSite(SiteEntity site);
	
	/**
	 * 获取站点信息
	 */
	public abstract SiteEntity getSite();
	
	/**
	 * 保存client
	 */
	public abstract void setClient(Client client);
	
	/**
	 * 获取client
	 * @return
	 */
	public abstract Client getClient();
	
}
