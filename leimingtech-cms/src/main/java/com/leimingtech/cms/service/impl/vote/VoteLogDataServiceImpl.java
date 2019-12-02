package com.leimingtech.cms.service.impl.vote;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.vote.VoteLogDataServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

@Service("voteLogDataService")
@Transactional
public class VoteLogDataServiceImpl extends CommonServiceImpl implements VoteLogDataServiceI {
	
}