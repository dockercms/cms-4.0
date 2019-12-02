package com.leimingtech.core.service;


import com.leimingtech.core.entity.VoteOptionEntity;

import java.util.List;

public interface VoteOptionServiceI extends CommonService{

    /**
     * 获取投票下的所有选项
     * @param voteId 投票id
     * @return 选项
     */
    List<VoteOptionEntity> getAllByVoteId(String voteId);
}
