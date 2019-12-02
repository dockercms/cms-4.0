package com.leimingtech.platform.service.impl.territory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.territory.TerritoryServiceI;

@Service("territoryService")
@Transactional
public class TerritoryServiceImpl extends CommonServiceImpl implements
		TerritoryServiceI {

}
