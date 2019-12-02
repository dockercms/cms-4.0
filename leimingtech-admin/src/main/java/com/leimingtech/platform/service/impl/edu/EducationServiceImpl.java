package com.leimingtech.platform.service.impl.edu;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.edu.EducationServiceI;

@Service("educationService")
@Transactional
public class EducationServiceImpl extends CommonServiceImpl implements EducationServiceI {
	
}