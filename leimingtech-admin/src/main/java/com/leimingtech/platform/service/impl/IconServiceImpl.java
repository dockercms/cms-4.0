package com.leimingtech.platform.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.IconService;

@Service("iconService")
@Transactional
public class IconServiceImpl extends CommonServiceImpl implements IconService {

}
