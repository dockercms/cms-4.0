package com.leimingtech.cms.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

/**
 * Created by gehanyang on 2016/1/25.
 */
public class ShiroUtils {
    /**
     * 获取当前登录者对象
     */
    public static MyRealm.Principal getPrincipal(){
        try{
            Subject subject = SecurityUtils.getSubject();
           MyRealm.Principal principal = (MyRealm.Principal)subject.getPrincipal();
            if (principal != null){
                return principal;
            }
//			subject.logout();
        }catch (UnavailableSecurityManagerException e) {

        }catch (InvalidSessionException e){

        }
        return null;
    }
}
