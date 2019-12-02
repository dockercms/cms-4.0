package com.leimingtech.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.InterviewServiceI;

@Service("interviewService")
@Transactional
public class InterviewServiceImpl extends CommonServiceImpl implements InterviewServiceI {
	
}