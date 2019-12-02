package com.leimingtech.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.SurveyMobileServiceI;

@Service("surveyMobileService")
@Transactional
public class SurveyMobileServiceImpl extends CommonServiceImpl implements SurveyMobileServiceI {
	
}