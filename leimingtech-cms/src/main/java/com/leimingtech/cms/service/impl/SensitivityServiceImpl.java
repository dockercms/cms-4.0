package com.leimingtech.cms.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.SensitivityServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

@Service("sensitivityService")
@Transactional
public class SensitivityServiceImpl extends CommonServiceImpl implements SensitivityServiceI {
	
}