package com.leimingtech.core.entity;



import javax.persistence.*;

/**
 * Created by gehanyang on 2016/1/12.
 */
@Entity
@Table(name = "cms_role_depart")
public class TSRoleDepart extends IdEntity implements java.io.Serializable{
    private TSRole role;
    private TSDepart depart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleid")
    public TSRole getRole() {
        return role;
    }

    public void setRole(TSRole role) {
        this.role = role;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departid")
    public TSDepart getDepart() {
        return depart;
    }

    public void setDepart(TSDepart depart) {
        this.depart = depart;
    }
}
