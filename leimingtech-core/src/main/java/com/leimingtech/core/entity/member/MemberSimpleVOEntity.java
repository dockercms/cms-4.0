package com.leimingtech.core.entity.member;

/**
 * 简单的会员实体，只有id和username字段
 * 
 * @author liuzhen
 * 
 */
public class MemberSimpleVOEntity {
	private String id;
	private String username;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
