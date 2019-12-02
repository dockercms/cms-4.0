package com.leimingtech.platform.service.impl.swfdemo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.swfdemo.SwfdemoServiceI;

@Service("swfdemoService")
@Transactional
public class SwfdemoServiceImpl extends CommonServiceImpl implements SwfdemoServiceI {
	
}