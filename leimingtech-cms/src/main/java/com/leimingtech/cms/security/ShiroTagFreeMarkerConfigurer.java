package com.leimingtech.cms.security;


import com.leimingtech.cms.security.tag.ShiroTags;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;



/**
 * Created by gehanyang on 2016/1/27.
 */
public class ShiroTagFreeMarkerConfigurer extends FreeMarkerConfigurer {
    public void afterPropertiesSet() throws IOException, TemplateException {
        super.afterPropertiesSet();
        this.getConfiguration().setSharedVariable("shiro", new ShiroTags());

    }
}
