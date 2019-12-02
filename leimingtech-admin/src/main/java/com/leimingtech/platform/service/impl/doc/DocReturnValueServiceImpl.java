package com.leimingtech.platform.service.impl.doc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.doc.DocReturnValueServiceI;

@Service("docReturnValueService")
@Transactional
public class DocReturnValueServiceImpl extends CommonServiceImpl implements DocReturnValueServiceI {
	
}