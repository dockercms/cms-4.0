package com.leimingtech.core.service.impl;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Hashtable;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.WebSessionContext;

/**
 * THREADLOCAL变量本地化
 * @author LKANG
 */
@SuppressWarnings("rawtypes")
public class WebSessioncontextImpl implements WebSessionContext, Externalizable {

	private HttpSession session;
	
	private Hashtable attributes = new Hashtable();
	
	private SiteEntity site;
	
	private Client client;
	
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		attributes = (Hashtable) in.readObject();
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(attributes);
		
	}

	/**
	 * 获取session
	 * @return
	 */
	public HttpSession getSession() {
		return session;
	}

	/**
	 * 给session赋值
	 * @param session
	 */
	public void setSession(HttpSession session) {
		this.session = session;
		this.attributes = (Hashtable)this.session.getAttribute(sessionAttributeKey);
		if(attributes == null){
			attributes = new Hashtable();
			this.onSaveSessionAttribute();
		}
	}
	
	private void onSaveSessionAttribute(){
		this.session.setAttribute(sessionAttributeKey, attributes);
	}

	/**
	 * 初始化session
	 */
	public void invalidateSession() {
		this.session.invalidate();
	}

	/**
	 * 填充参数
	 * @param name
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public void setAttribute(String name, Object value) {
		if(attributes != null){
			attributes.put(name, value);
			onSaveSessionAttribute();
		}
	}

	/**
	 * 获取参数
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name) {
		if(attributes != null){
			return attributes.get(name);
		}
		return null;
	}

	/**
	 * 获取所有键
	 * @return
	 */
	public Set getAttributeNames() {
		return attributes.keySet();
	}

	/**
	 * 移除参数
	 * @param name
	 */
	public void removeAttribute(String name) {
		attributes.remove(name);
		onSaveSessionAttribute();
	}

	/**
	 * 消毁session
	 */
	public void destory() {
		this.attributes = null;
		this.session = null;
	}

	/**
	 * 保存站点信息
	 * @param site
	 */
	public void setSite(SiteEntity site) {
		this.site = site;
	}

	/**
	 * 获取站点信息
	 */
	public SiteEntity getSite() {
		return site;
	}

	/**
	 * 保存client
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * 获取client
	 * @return
	 */
	public Client getClient() {
		return client;
	}
}
