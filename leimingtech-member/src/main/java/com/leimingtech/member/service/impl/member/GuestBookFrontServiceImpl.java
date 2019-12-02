package com.leimingtech.member.service.impl.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.member.service.member.GuestBookFrontServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

@Service("guestBookFrontService")
@Transactional
public class GuestBookFrontServiceImpl extends CommonServiceImpl implements GuestBookFrontServiceI {
	
}