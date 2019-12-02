package com.leimingtech.platform.service.impl.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.test.TestOneServiceI;

@Service("testOneService")
@Transactional
public class TestOneServiceImpl extends CommonServiceImpl implements TestOneServiceI {
	
}