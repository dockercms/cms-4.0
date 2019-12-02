package com.leimingtech.platform.service.impl.company;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.company.CompanyServiceI;

@Service("companyService")
@Transactional
public class CompanyServiceImpl extends CommonServiceImpl implements CompanyServiceI {
	
}