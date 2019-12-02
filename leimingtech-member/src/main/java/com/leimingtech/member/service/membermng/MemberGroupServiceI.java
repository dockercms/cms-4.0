package com.leimingtech.member.service.membermng;

import com.leimingtech.core.service.CommonService;
import com.leimingtech.member.entity.membermng.MemberGroupEntity;

/**
 * 会员组接口
 * 
 * @author liuzhen 2014年5月27日 18:20:13
 * 
 */
public interface MemberGroupServiceI extends CommonService {
	public void delMain(MemberGroupEntity memberGroup);

	/**
	 * 获取会员默认级别
	 * 
	 * @return
	 */
	public MemberGroupEntity getDefaultLevel();
}
