package com.leimingtech.core.entity;



import javax.persistence.*;

/**
 * Created by gehanyang on 2016/1/13.
 */
@Entity
@Table(name = "cms_depart_channel")
public class TSDepartChannel extends IdEntity implements java.io.Serializable {
    private TSDepart depart;
    private ContentCatEntity channel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departId")
    public TSDepart getDepart() {
        return depart;
    }

    public void setDepart(TSDepart depart) {
        this.depart = depart;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "channelId")
    public ContentCatEntity getChannel() {
        return channel;
    }

    public void setChannel(ContentCatEntity channel) {
        this.channel = channel;
    }
}
