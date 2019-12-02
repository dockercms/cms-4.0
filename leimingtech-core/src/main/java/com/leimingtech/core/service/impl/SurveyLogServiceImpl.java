package com.leimingtech.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.SurveyLogServiceI;

@Service("surveyLogService")
@Transactional
public class SurveyLogServiceImpl extends CommonServiceImpl implements SurveyLogServiceI {
	
}