package com.leimingtech.cms.service.impl.vote;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.vote.VoteLogServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

@Service("voteLogService")
@Transactional
public class VoteLogServiceImpl extends CommonServiceImpl implements VoteLogServiceI {
	
}