package com.leimingtech.mobile.service.impl.videoclassify;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.mobile.service.videoclassify.MobileVideoClassifyServiceI;

@Service("mobileVideoClassifyService")
@Transactional
public class MobileVideoClassifyServiceImpl extends CommonServiceImpl implements MobileVideoClassifyServiceI {
	
}