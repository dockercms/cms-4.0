package com.leimingtech.core.service.impl;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.CommentaryFrontEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommentaryFrontServiceI;
import com.leimingtech.core.service.ICommentTagMng;
import com.leimingtech.core.util.oConvertUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/28.
 */
@Service("CommentTagMng")
@Transactional
public class CommentTagMng extends CommonServiceImpl implements ICommentTagMng {

    @Override
    public Object getCommentByTag(Map params) {
        if (MapUtils.isEmpty(params))
            return null;

        String name = (String) params.get("name");

        CriteriaQuery cq = new CriteriaQuery(CommentaryFrontEntity.class,2,1,"","");
        cq.eq("contentid",name);
        cq.addOrder("supportcount", SortDirection.desc);
        cq.add();
        PageList pageList=getPageList(cq,true);
        List<CommentaryFrontEntity> commentList = pageList.getResultList();
        return  commentList;
    }
}
