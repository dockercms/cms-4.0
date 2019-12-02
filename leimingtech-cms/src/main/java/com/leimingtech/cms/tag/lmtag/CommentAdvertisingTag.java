package com.leimingtech.cms.tag.lmtag;

import com.leimingtech.cms.entity.commentadvertising.CommentAdvertisingEntity;
import com.leimingtech.cms.service.commentadvertising.CommentAdvertisingServiceI;
import com.leimingtech.core.common.BaseFreeMarkerTag;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by hejiping on 2017/4/27.
 */
@Component
public class CommentAdvertisingTag extends BaseFreeMarkerTag {

    @Autowired
    private CommentAdvertisingServiceI commentAdvertisingMng;

    @Override
    protected Object exec(Map params) throws TemplateModelException {
        return commentAdvertisingMng.getCommentAdvertisingByTag(params);
    }
}
