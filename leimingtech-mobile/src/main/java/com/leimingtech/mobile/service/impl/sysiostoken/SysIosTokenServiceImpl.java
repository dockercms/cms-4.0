package com.leimingtech.mobile.service.impl.sysiostoken;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.mobile.service.sysiostoken.SysIosTokenServiceI;

@Service("sysIsoTokenService")
@Transactional
public class SysIosTokenServiceImpl extends CommonServiceImpl implements SysIosTokenServiceI {
	
}