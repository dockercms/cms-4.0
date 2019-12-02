package com.leimingtech.cms.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.MoodServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

@Service("moodService")
@Transactional
public class MoodServiceImpl extends CommonServiceImpl implements MoodServiceI {
	
}