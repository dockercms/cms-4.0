package com.leimingtech.cms.service.impl.guestbook;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.guestbook.GuestBookServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

@Service("guestBookService")
@Transactional
public class GuestBookServiceImpl extends CommonServiceImpl implements GuestBookServiceI {
	
}