package com.leimingtech.core.service.impl;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.SpecialEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.SpecialServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内容专题接口实现
 */
@Service("specialService")
@Transactional
public class SpecialServiceImpl implements SpecialServiceI {
    /**公共dao接口*/
    @Autowired
    private CommonService commonService;

    /**
     * 保存内容专题
     *
     * @param special
     * @return
     */
    @Override
    public String save(SpecialEntity special) {
        return (String) this.commonService.save(special);
    }

    /**
     * 更新内容专题
     *
     * @param special
     */
    @Override
    public void saveOrUpdate(SpecialEntity special) {
        this.commonService.saveOrUpdate(special);
    }

    /**
     * 通过id获取内容专题
     *
     * @param id 内容专题id
     * @return
     */
    @Override
    public SpecialEntity getEntity(String id) {
        return this.commonService.getEntity(SpecialEntity.class, id);
    }

    /**
     * 获取分页后的内容专题数据集
     *
     * @param special
     * @param param    字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
     * @param pageSize 每页获取数量
     * @param pageNo   当前页码
     * @return specialList内容专题数据集 pageCount总页数
     */
    @Override
    public Map<String, Object> getPageList(SpecialEntity special, Map param, int pageSize, int pageNo) {
        CriteriaQuery cq = new CriteriaQuery(SpecialEntity.class,
                pageSize, pageNo, "", "");
        // 查询条件组装器
        HqlGenerateUtil.installHql(cq, special, param);
        cq.addOrder("sort", SortDirection.asc);
        cq.add();
        PageList pageList = commonService.getPageList(cq, true);
        List<SpecialEntity> resultList = pageList.getResultList();

        int pageCount = (int) Math.ceil((double) pageList.getCount()
                / (double) pageSize);
        if (pageCount <= 0) {
            pageCount = 1;
        }
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("specialList", resultList);
        m.put("pageCount", pageCount);
        return m;
    }

    /**
     * 删除内容专题
     *
     * @param special
     */
    @Override
    public void delete(SpecialEntity special) {
        if(special==null){
            return;
        }
        this.commonService.delete(special);
    }

    /**
     * 获取内容专题
     *
     * @param contentId
     * @return
     */
    @Override
    public SpecialEntity findByContentId(String contentId) {
        return this.commonService.findUniqueByProperty(SpecialEntity.class, "contentid", contentId);
    }
}