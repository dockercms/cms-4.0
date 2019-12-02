package com.leimingtech.netease.entity;

import java.util.List;

/**
 * 网易云直播 频道列表
 * 
 * @author liuzhen
 * 
 */
public class ChannelListEntity {

	private List<ChannelItemEntity> list;

	private int pnum;// 当前页码
	private int totalRecords;// 当前页条数
	private int totalPnum;// 总页数
	private int records;// 每页定义条数

	public List<ChannelItemEntity> getList() {
		return list;
	}

	public void setList(List<ChannelItemEntity> list) {
		this.list = list;
	}

	public int getPnum() {
		return pnum;
	}

	public void setPnum(int pnum) {
		this.pnum = pnum;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPnum() {
		return totalPnum;
	}

	public void setTotalPnum(int totalPnum) {
		this.totalPnum = totalPnum;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

}
