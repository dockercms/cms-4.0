package com.leimingtech.cms.tag.lmtag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.service.tag.ICatalogTagMng;
import com.leimingtech.core.common.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * Created by gehanyang on 2016/2/26.
 */
@Component
public class DefaultCatTag extends BaseFreeMarkerTag {
    @Autowired
    private ICatalogTagMng catalogMng;

    @Override
    protected Object exec(Map params) throws TemplateModelException {
        return catalogMng.getDefaultCat(params);

    }
}
