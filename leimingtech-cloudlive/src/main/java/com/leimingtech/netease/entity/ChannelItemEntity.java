package com.leimingtech.netease.entity;

/**
 * 网易云直播 频道列表项
 * 
 * @author liuzhen
 * 
 */
public class ChannelItemEntity {
	private long ctime;// 创建频道的时间戳
	private String cid;// 频道ID，32位字符串
	private String name;// 频道名称
	private int status;// 频道状态（0：正常；1：直播中；2：暂停）
	private Integer type;// 频道类型 ( 0 : rtmp, 1 : hls, 2 : http)

	private int needRecord;
	private int uid;
	private int duration;
	private String filename;
	private int format;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getNeedRecord() {
		return needRecord;
	}

	public void setNeedRecord(int needRecord) {
		this.needRecord = needRecord;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getFormat() {
		return format;
	}

	public void setFormat(int format) {
		this.format = format;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
