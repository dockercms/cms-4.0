package com.leimingtech.platform.service.impl.memo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.memo.MemoServiceI;

@Service("memoService")
@Transactional
public class MemoServiceImpl extends CommonServiceImpl implements MemoServiceI {
	
}