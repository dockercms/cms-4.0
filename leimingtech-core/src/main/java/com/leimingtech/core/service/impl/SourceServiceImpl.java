package com.leimingtech.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.SourceServiceI;

@Service("sourceService")
@Transactional
public class SourceServiceImpl extends CommonServiceImpl implements SourceServiceI {
	
}