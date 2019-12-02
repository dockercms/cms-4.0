package com.leimingtech.platform.service.impl.viewmodel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.viewmodel.ViewmodelServiceI;

@Service("viewmodelService")
@Transactional
public class ViewmodelServiceImpl extends CommonServiceImpl implements ViewmodelServiceI {
	
}