package com.leimingtech.core.service;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/28.
 */
public interface ICommentTagMng extends CommonService{
    /**
     * 获取评论（此方法由标签调用）
     *
     * @param params
     *
     * @return 根据参数不同既可以获取两条热门评论 也可以获取评论列表 comment or List<comment>
     */
    Object getCommentByTag(Map params);
}
