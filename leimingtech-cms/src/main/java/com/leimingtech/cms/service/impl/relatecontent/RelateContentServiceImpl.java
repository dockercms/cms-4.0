package com.leimingtech.cms.service.impl.relatecontent;

import com.leimingtech.cms.service.LuceneServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.*;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.util.oConvertUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.RelateContentServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("relateContentService")
@Transactional
public class RelateContentServiceImpl extends CommonServiceImpl implements RelateContentServiceI {

    @Autowired
    private LuceneServiceI luceneServiceI;

    @Override
    public Object getRalateContentList(Map params) {
        if (MapUtils.isEmpty(params))
            return null;
        String contentId=(String)params.get("contentId");   //内容Id
        int count= oConvertUtils.getInt(params.get("count"),4);//显示数量
        CriteriaQuery cq = new CriteriaQuery(RelateContentEntity.class,count,1,"","");

        DetachedCriteria dc =cq.getDetachedCriteria();
        ProjectionList pList = Projections.projectionList();
        pList.add(Projections.property("relateTitle").as("title"));
        pList.add(Projections.property("relateUrl").as("url"));
        dc.setProjection(pList);
        dc.setResultTransformer(Transformers.aliasToBean(ContentVoEntity.class));

        cq.eq("contentId",contentId);
        cq.addOrder("created", SortDirection.desc);
        cq.add();

        List<ContentVoEntity> ralateContentList =this.getListByCriteriaQuery(cq,true);
        if(ralateContentList.size()==0){
            ContentsEntity contents =this.get(ContentsEntity.class,contentId);
            if(StringUtils.isNotEmpty(contents.getTags())){
                SiteEntity site=this.get(SiteEntity.class,contents.getSiteid());
                Map map = new HashMap();
                map.put("keyword",contents.getTags());
                map.put("pageNo",1);
                map.put("pageSize",count);
                Map<String,Object> newsMap = luceneServiceI.searchContent(site, map);// 检索后返回的数据
                List<NewsIndexEntity>  newslist = (List<NewsIndexEntity>) newsMap.get("newslist");// 当前页条数
                return newslist;
            }
        }
       return ralateContentList;

    }


}