package com.leimingtech.cms.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.service.UserService;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

/**
 * Created by gehanyang on 2016/1/18.
 */
public class MyRealm  extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    private String name = "MyRealm";

    public String getName() {
        return name;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        Principal principal = (Principal) getAvailablePrincipal(principalCollection);
        TSUser user = userService.getEntity(TSUser.class, principal.getId());
        if(user!=null){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
           if(StringUtils.isNotBlank(user.getUserKey())){
               for(String role:StringUtils.split(user.getUserKey(),",")){
                    info.addRole(role);
               }
           }
            info.addStringPermission("user");
            return  info;
        }
     return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        List<TSUser> userList = userService.findByProperty(TSUser.class,
                "userName", token.getUsername());
        if(userList.size()!=0){
            TSUser user = userList.get(0);
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(new Principal(user),user.getPassword(),getName());
              return info;
        }else{
            return null;
        }
    }

    @PostConstruct
    public void initCredentialsMatcher() {
        //该句作用是重写shiro的密码验证，让shiro用我自己的验证
        setCredentialsMatcher(new CustomCredentialsMatcher());

    }
    /**
     * 授权用户信息
     */
    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id; // 编号
        private String loginName; // 登录名
        private String name; // 姓名

        public Principal(TSUser user) {
            this.id = user.getId();
            this.loginName = user.getUserName();
            this.name = user.getRealName();
        }
        public String getId() {
            return id;
        }
        public String getLoginName() {
            return loginName;
        }
        public String getName() {
            return name;
        }
        /**
         * 获取SESSIONID
         */
        public String getSessionid() {
            try{
                return (String) ContextHolderUtils.getSession().getId();
            }catch (Exception e) {
                return "";
            }
        }
        public String toString() {
            return id;
        }
    }
}
