package com.leimingtech.platform.service.impl.branch;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.branch.BranchServiceI;

@Service("branchService")
@Transactional
public class BranchServiceImpl extends CommonServiceImpl implements BranchServiceI {
	
}