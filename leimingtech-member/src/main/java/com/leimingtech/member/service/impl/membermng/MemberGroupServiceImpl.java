package com.leimingtech.member.service.impl.membermng;

import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.member.entity.membermng.MemberGroupEntity;
import com.leimingtech.member.service.membermng.MemberGroupServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * liuzhen 2014年5月27日 18:20:31
 * 
 * @author Administrator
 * 
 */
@Service("memberGroupService")
@Transactional
public class MemberGroupServiceImpl extends CommonServiceImpl implements MemberGroupServiceI {
	public void delMain(MemberGroupEntity memberGroup) {
		// 删除主表信息
		this.delete(memberGroup);
		// 获取参数
		Object id0 = memberGroup.getId();
		// 删除-动态报表配置明细
		String hql0 = "from MemberEntity where 1 = 1 AND membergroupsid = ? ";
		List<MemberEntity> memberOldList = this.findHql(hql0, id0);
		this.deleteAllEntitie(memberOldList);
	}

	/**
	 * 获取会员默认级别
	 */
	@Override
	public MemberGroupEntity getDefaultLevel() {
		List<MemberGroupEntity> mgList = findByProperty(MemberGroupEntity.class, "defalutLv", 1);
		if (mgList != null && mgList.size() > 0) {
			return mgList.get(0);
		}
		return null;
	}
}