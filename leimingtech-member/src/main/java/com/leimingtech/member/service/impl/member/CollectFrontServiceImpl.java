package com.leimingtech.member.service.impl.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.member.service.member.CollectFrontServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

@Service("collectFrontService")
@Transactional
public class CollectFrontServiceImpl extends CommonServiceImpl implements CollectFrontServiceI {
	
}