package com.leimingtech.platform.service.impl.doc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.doc.DocParamServiceI;

@Service("docParamService")
@Transactional
public class DocParamServiceImpl extends CommonServiceImpl implements DocParamServiceI {
	
}