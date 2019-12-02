package com.leimingtech.core.entity;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by gehanyang on 2016/2/24.
 */
@Entity
@Table(name = "cms_content_cat_default", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ContentCatDefault extends IdEntity implements java.io.Serializable{
    private String channelId;

    @Column(name = "channelId")
    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
