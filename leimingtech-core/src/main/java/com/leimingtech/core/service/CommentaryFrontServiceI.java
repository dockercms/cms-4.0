package com.leimingtech.core.service;

import java.util.List;

public interface CommentaryFrontServiceI extends CommonService{


    List showCommentaryList(String contentId);

    /**
     *
     * 增加赞同数
     *
     * @param commentId
     */
    Integer addSupport(String commentId);

    /**
     *
     * 增加反对数
     *
     * @param commentId
     */
    Integer addOppose(String commentId);
}
