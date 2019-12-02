package com.leimingtech.platform.service.impl.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.test.CustomServiceI;

@Service("customService")
@Transactional
public class CustomServiceImpl extends CommonServiceImpl implements CustomServiceI {
	
}