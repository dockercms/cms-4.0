package com.leimingtech.cms.tag.lmtag;

import com.leimingtech.core.common.BaseFreeMarkerTag;
import com.leimingtech.core.service.ICommentTagMng;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/28.
 */

@Component
public class CommentTag extends BaseFreeMarkerTag {

    @Autowired
    private ICommentTagMng CommentMng;

    @Override
    protected Object exec(Map params) throws TemplateModelException {
        return CommentMng.getCommentByTag(params);
    }
}
