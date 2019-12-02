package com.leimingtech.cms.service.impl.acquisition;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.acquisition.AcquisitionTempServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

@Service("acquisitionTempService")
@Transactional
public class AcquisitionTempServiceImpl extends CommonServiceImpl implements AcquisitionTempServiceI {
	
}