package com.leimingtech.cms.service.impl.acquisition;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.acquisition.AcquisitionContentServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

@Service("acquisitionContentService")
@Transactional
public class AcquisitionContentServiceImpl extends CommonServiceImpl implements AcquisitionContentServiceI {
	
}