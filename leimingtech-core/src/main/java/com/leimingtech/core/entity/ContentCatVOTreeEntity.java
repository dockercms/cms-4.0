package com.leimingtech.core.entity;

/**
 * 针对ztree数据划分的简单栏目实体
 */
public class ContentCatVOTreeEntity implements java.io.Serializable {

	/**栏目id*/
	private String id;
	/** 栏目名称 */
	private String name;
	/**栏目父id*/
	private String parentid;
	/**栏目所在的层级*/
	private Integer levels;

	private Integer iscatend;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

	public Integer getIscatend() {
		return iscatend;
	}

	public void setIscatend(Integer iscatend) {
		this.iscatend = iscatend;
	}
}
