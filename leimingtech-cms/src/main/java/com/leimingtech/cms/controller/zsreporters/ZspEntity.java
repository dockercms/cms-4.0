package com.leimingtech.cms.controller.zsreporters;

public class ZspEntity {
	private String reportid;  //新闻id
	private String papername;//出版报纸名称
	private String channel;  //板块
	private String title; 	 //标题
	private String editon;  //版本号
	private String author;   //作者
	private String pubdate;    //发布时间
	
	public String getReportid() {
		return reportid;
	}
	public void setReportid(String reportid) {
		this.reportid = reportid;
	}
	public String getPapername() {
		return papername;
	}
	public void setPapername(String papername) {
		this.papername = papername;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEditon() {
		return editon;
	}
	public void setEditon(String editon) {
		this.editon = editon;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
}
