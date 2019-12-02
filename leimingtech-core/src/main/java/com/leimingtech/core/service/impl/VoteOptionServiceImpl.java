package com.leimingtech.core.service.impl;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.VoteOptionEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.VoteOptionServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("voteOptionService")
@Transactional
public class VoteOptionServiceImpl extends CommonServiceImpl implements VoteOptionServiceI {
    /**
     * 公共dao接口
     */
    @Autowired
    private CommonService commonService;

    /**
     * 获取投票下的所有选项
     *
     * @param voteId 投票id
     * @return 选项
     */
    @Override
    public List<VoteOptionEntity> getAllByVoteId(String voteId) {
        CriteriaQuery cq = new CriteriaQuery(VoteOptionEntity.class);
        cq.eq("voteid", voteId);
        cq.addOrder("optionsort", SortDirection.desc);
        cq.addOrder("createdtime", SortDirection.asc);
        cq.add();
        return this.commonService.getListByCriteriaQuery(cq, false);
    }
}