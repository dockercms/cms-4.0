package com.leimingtech.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.SimplespecialContentServiceI;

@Service("simplespecialContentService")
@Transactional
public class SimplespecialContentServiceImpl extends CommonServiceImpl implements SimplespecialContentServiceI {
	
}