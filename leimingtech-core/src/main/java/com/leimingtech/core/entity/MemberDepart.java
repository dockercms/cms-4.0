package com.leimingtech.core.entity;


import com.leimingtech.core.entity.member.MemberEntity;

import javax.persistence.*;

/**
 * Created by gehanyang on 2016/1/7.
 */
@Entity
@Table(name = "cms_member_depart")
public class MemberDepart extends IdEntity implements java.io.Serializable{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departId")
    public TSDepart getDepart() {
        return depart;
    }

    public void setDepart(TSDepart depart) {
        this.depart = depart;
    }
    private MemberEntity member;
    private TSDepart depart;


}
