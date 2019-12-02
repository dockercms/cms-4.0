package com.leimingtech.platform.service.impl.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.test.TestServiceI;

@Service("testService")
@Transactional
public class TestServiceImpl extends CommonServiceImpl implements TestServiceI {
	
}