package com.leimingtech.cms.tag.lmtag;

import com.leimingtech.cms.service.RelateContentServiceI;
import com.leimingtech.core.common.BaseFreeMarkerTag;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *相关内容标签
 * Created by sunyaoxu on 2017/4/14.
 */
@Component
public class RelatedContentTag extends BaseFreeMarkerTag {

    @Autowired
    private RelateContentServiceI relateContentServiceI;

    @Override
    protected Object exec(Map params) throws TemplateModelException {
        return  relateContentServiceI.getRalateContentList(params);
    }

}
