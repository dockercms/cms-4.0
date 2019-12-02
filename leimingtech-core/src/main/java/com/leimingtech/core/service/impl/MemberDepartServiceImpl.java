package com.leimingtech.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.MemberDepart;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.MemberDepartServiceI;

/**
 * @Title: interface
 * @Description: 会员与部门关联接口实现
 * @author
 * @date 2016-04-22 16:14:34
 * @version V1.0
 * 
 */
@Service("memberDepartService")
@Transactional
public class MemberDepartServiceImpl implements MemberDepartServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存会员与部门关联
	 * 
	 * @param memberDepart
	 * @return
	 */
	public java.lang.String save(MemberDepart memberDepart) {
		return (java.lang.String) commonService.save(memberDepart);
	}

	/**
	 * 更新会员与部门关联
	 * 
	 * @param memberDepart
	 */
	@Override
	public void saveOrUpdate(MemberDepart memberDepart) {
		commonService.saveOrUpdate(memberDepart);
	}

	/**
	 * 通过id获取会员与部门关联
	 * 
	 * @param id
	 *            会员与部门关联id
	 * @return
	 */
	@Override
	public MemberDepart getEntity(java.lang.String id) {
		return commonService.getEntity(MemberDepart.class, id);
	}

	/**
	 * 获取分页后的会员与部门关联数据集
	 * 
	 * @param memberDepart
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return memberDepartList会员与部门关联数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(MemberDepart memberDepart, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(MemberDepart.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, memberDepart, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<MemberDepart> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("memberDepartList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除会员与部门关联
	 * 
	 * @param memberDepart
	 */
	@Override
	public void delete(MemberDepart memberDepart) {
		commonService.delete(memberDepart);
	}

	/**
	 * 通过会员删除与部门之间的关联
	 */
	@Override
	public void deleteByMember(String memberId) {
		List<MemberDepart> memberDepartList= this.findbyMemberId(memberId);
		if(memberDepartList!=null && memberDepartList.size()>0){
			for (MemberDepart memberDepart : memberDepartList) {
				this.delete(memberDepart);
			}
		}
	}

	/**
	 * 根据会员id获取会员与部门的关联
	 * @param memberId
	 */
	private List<MemberDepart> findbyMemberId(String memberId) {
		return commonService.findByProperty(MemberDepart.class, "member.id", memberId);
	}
	
	/**
	 * 根据部门id获取会员与部门的关联
	 * @param memberId
	 */
	private List<MemberDepart> findbyDepartId(String departId) {
		return commonService.findByProperty(MemberDepart.class, "depart.id", departId);
	}

	/**
	 * 根据部门删除会员跟部门间的关联
	 * @param departId
	 */
	@Override
	public void deleteByDepart(String departId) {
		List<MemberDepart> memberDepartList=findbyDepartId(departId);
		if(memberDepartList!=null && memberDepartList.size()>0){
			for (MemberDepart memberDepart : memberDepartList) {
				this.delete(memberDepart);
			}
		}
	}

	/**
	 * 获取会员的所有部门
	 * @param memberId
	 * @return
	 */
	@Override
	public String[] getMemberDeparts(String memberId) {

		List<String> resultList = getMemberDeparIdtList(memberId);
		String[] departs = null;
		if (resultList != null && resultList.size() > 0) {
			departs = new String[resultList.size()];
			for (int i = 0; i < resultList.size(); i++) {
				String departId = resultList.get(i);
				departs[i] = departId;
			}
		} else {
			departs = new String[] {};
		}

		return departs;
	}

	private List<String> getMemberDeparIdtList(String memberId) {
		String sql = "select departId from cms_member_depart where memberId = ?";

		return commonService.findForList(sql, String.class, memberId);
	}
	
}