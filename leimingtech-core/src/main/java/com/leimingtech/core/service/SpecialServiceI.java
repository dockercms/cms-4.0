package com.leimingtech.core.service;


import com.leimingtech.core.entity.SpecialEntity;

import java.util.Map;

/**
 * 内容专题接口
 */
public interface SpecialServiceI {
    /**
     * 保存内容专题
     *
     * @param special
     * @return
     */
    String save(SpecialEntity special);

    /**
     * 更新内容专题
     *
     * @param special
     */
    void saveOrUpdate(SpecialEntity special);

    /**
     * 通过id获取内容专题
     *
     * @param id
     *            内容专题id
     * @return
     */
    SpecialEntity getEntity(String id);

    /**
     * 获取分页后的内容专题数据集
     *
     * @param special
     * @param param
     *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
     * @param pageSize
     *            每页获取数量
     * @param pageNo
     *            当前页码
     * @return specialList内容专题数据集 pageCount总页数
     */
    Map<String, Object> getPageList(SpecialEntity special, Map param, int pageSize,
                                    int pageNo);

    /**
     * 删除内容专题
     *
     * @param special
     */
    void delete(SpecialEntity special);

    /**
     * 获取内容专题
     * @param contentId
     * @return
     */
    SpecialEntity findByContentId(String contentId);
}
