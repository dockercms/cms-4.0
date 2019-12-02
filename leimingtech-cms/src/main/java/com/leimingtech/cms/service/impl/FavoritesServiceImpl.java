package com.leimingtech.cms.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.FavoritesServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

@Service("favoritesService")
@Transactional
public class FavoritesServiceImpl extends CommonServiceImpl implements FavoritesServiceI {
	
}