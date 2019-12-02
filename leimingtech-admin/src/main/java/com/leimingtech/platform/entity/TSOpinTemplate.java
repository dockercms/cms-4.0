package com.leimingtech.platform.entity;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leimingtech.core.entity.IdEntity;


/**
 * TType entity.
 *  @author  
 */
@Entity
@Table(name = "cms_opintemplate")
public class TSOpinTemplate extends IdEntity implements java.io.Serializable {
	private String descript;
	@Column(name = "descript", length = 100)
	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	
}