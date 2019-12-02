package com.leimingtech.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.ContentTagServiceI;

@Service("contentTagService")
@Transactional
public class ContentTagServiceImpl extends CommonServiceImpl implements ContentTagServiceI {
	
}