package com.leimingtech.core.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.ContentExtractoruleEntity;
import com.leimingtech.core.entity.PfConfigEntity;
import com.leimingtech.core.entity.TSFunction;
import com.leimingtech.core.entity.TSLog;
import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.entity.TSRoleFunction;
import com.leimingtech.core.entity.TSRoleUser;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.entity.WechatConfigEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.IpUtil;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.browser.BrowserUtils;
import com.leimingtech.core.util.date.DataUtils;

@Service("systemService")
@Transactional
public class SystemServiceImpl implements SystemService {
	
	@Autowired
	private CommonService commonService;
	
	public TSUser checkUserExits(TSUser user) {
		return getUserByUserIdAndUserNameExits(user);
	}
	
	/**
	 * 检查用户是否存在
	 * */
	public TSUser getUserByUserIdAndUserNameExits(TSUser user) {
		// String password = PasswordUtil.encrypt(user.getUserName(),
		// user.getPassword(), PasswordUtil.getStaticSalt());
		String query = "from TSUser u where u.userName = :username and u.password=:passowrd";
		Query queryObject = commonService.getSession().createQuery(query);
		queryObject.setParameter("username" , user.getUserName());
		queryObject.setParameter("passowrd" , user.getPassword());
		List<TSUser> users = queryObject.list();
		if(users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}
	
	/**
	 * 添加日志
	 */
	public void addLog(String logcontent , Short loglevel , Short operatetype) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		TSLog log = new TSLog();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		// log.setNote(oConvertUtils.getIp());
		log.setNote(IpUtil.getIpAddr(request));
		log.setBroswer(broswer);
		log.setOperatetime(DataUtils.gettimestamp());
		log.setTSUser(PlatFormUtil.getSessionUser());
		log.setCreatedtime(new Date());// 创建时间
		commonService.save(log);
	}
	
	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * 
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSType getType(String typecode , String typename , TSTypegroup tsTypegroup) {
		TSType actType = commonService.findUniqueByProperty(TSType.class , "typecode" , typecode);
		if(actType == null) {
			actType = new TSType();
			actType.setTypecode(typecode);
			actType.setTypename(typename);
			actType.setTSTypegroup(tsTypegroup);
			actType.setCreatedtime(new Date());// 创建时间
			commonService.save(actType);
		}
		return actType;
		
	}
	
	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * 
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSTypegroup getTypeGroup(String typegroupcode , String typgroupename) {
		TSTypegroup tsTypegroup = commonService.findUniqueByProperty(TSTypegroup.class , "typegroupcode" , typegroupcode);
		if(tsTypegroup == null) {
			tsTypegroup = new TSTypegroup();
			tsTypegroup.setTypegroupcode(typegroupcode);
			tsTypegroup.setTypegroupname(typgroupename);
			tsTypegroup.setCreatedtime(new Date());// 创建时间
			commonService.save(tsTypegroup);
		}
		return tsTypegroup;
	}
	
	public TSTypegroup getTypeGroupByCode(String typegroupCode) {
		TSTypegroup tsTypegroup = commonService.findUniqueByProperty(TSTypegroup.class , "typegroupcode" , typegroupCode);
		return tsTypegroup;
	}
	
	public void initAllTypeGroups() {
		List<TSTypegroup> typeGroups = this.commonService.loadAll(TSTypegroup.class);
		for(TSTypegroup tsTypegroup : typeGroups) {
			TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase() , tsTypegroup);
			List<TSType> types = this.commonService.findByProperty(TSType.class , "TSTypegroup.id" , tsTypegroup.getId());
			TSTypegroup.allTypes.put(tsTypegroup.getTypegroupcode().toLowerCase() , types);
		}
	}
	
	public void refleshTypesCach(TSType type) {
		TSTypegroup tsTypegroup = type.getTSTypegroup();
		TSTypegroup typeGroupEntity = this.commonService.get(TSTypegroup.class , tsTypegroup.getId());
		List<TSType> types = this.commonService.findByProperty(TSType.class , "TSTypegroup.id" , tsTypegroup.getId());
		TSTypegroup.allTypes.put(typeGroupEntity.getTypegroupcode().toLowerCase() , types);
	}
	
	public void refleshTypeGroupCach() {
		TSTypegroup.allTypeGroups.clear();
		List<TSTypegroup> typeGroups = this.commonService.loadAll(TSTypegroup.class);
		for(TSTypegroup tsTypegroup : typeGroups) {
			TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase() , tsTypegroup);
		}
	}
	
	// ----------------------------------------------------------------
	// ----------------------------------------------------------------
	
	public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId , String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TSRole role = commonService.get(TSRole.class , roleId);
		CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id" , role.getId());
		cq1.eq("TSFunction.id" , functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions = commonService.getListByCriteriaQuery(cq1 , false);
		if(null != rFunctions && rFunctions.size() > 0) {
			TSRoleFunction tsRoleFunction = rFunctions.get(0);
			if(null != tsRoleFunction.getOperation()) {
				String [] operationArry = tsRoleFunction.getOperation().split(",");
				for(int i = 0 ; i < operationArry.length ; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}
	
	public Set<String> getOperationCodesByUserIdAndFunctionId(String userId , String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		List<TSRoleUser> rUsers = commonService.findByProperty(TSRoleUser.class , "TSUser.id" , userId);
		for(TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
			cq1.eq("TSRole.id" , role.getId());
			cq1.eq("TSFunction.id" , functionId);
			cq1.add();
			List<TSRoleFunction> rFunctions = commonService.getListByCriteriaQuery(cq1 , false);
			if(null != rFunctions && rFunctions.size() > 0) {
				TSRoleFunction tsRoleFunction = rFunctions.get(0);
				if(null != tsRoleFunction.getOperation()) {
					String [] operationArry = tsRoleFunction.getOperation().split(",");
					for(int i = 0 ; i < operationArry.length ; i++) {
						operationCodes.add(operationArry[i]);
					}
				}
			}
		}
		return operationCodes;
	}
	
	// ----------------------------------------------------------------
	// ----------------------------------------------------------------
	
	public void flushRoleFunciton(String id , TSFunction newFunction) {
		TSFunction functionEntity = this.commonService.getEntity(TSFunction.class , id);
		if(StringUtils.isEmpty(functionEntity.getIconclass())) {
			return;
		}
		if(!functionEntity.getIconclass().equals(newFunction.getIconclass())) {
			// 刷新缓存
			HttpSession session = ContextHolderUtils.getSession();
			TSUser user = PlatFormUtil.getSessionUser();
			List<TSRoleUser> rUsers = this.commonService.findByProperty(TSRoleUser.class , "TSUser.id" , user.getId());
			for(TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				session.removeAttribute(role.getId());
			}
		}
	}
	
	public void initAllPfConfig() {
		List<PfConfigEntity> pfconfigs = this.commonService.loadAll(PfConfigEntity.class);
		for(PfConfigEntity pfconfig : pfconfigs) {
			PfConfigEntity.pfconfigCache.put(pfconfig.getConfigkey() , pfconfig.getConfigvalue());
		}
	}
	
	public void refleshPfConfig() {
		PfConfigEntity.pfconfigCache.clear();
		List<PfConfigEntity> pfconfigs = this.commonService.loadAll(PfConfigEntity.class);
		for(PfConfigEntity pfconfig : pfconfigs) {
			PfConfigEntity.pfconfigCache.put(pfconfig.getConfigkey() , pfconfig.getConfigvalue());
		}
	}
	
	@Override
	public void loadContentExtractorule() {
		List<ContentExtractoruleEntity> list = this.commonService.loadAll(ContentExtractoruleEntity.class);
		ContentExtractoruleEntity.allContentExtractorule.clear();
		ContentExtractoruleEntity.allContentExtractorule.addAll(list);
	}
	
	@Override
	public void reloadContentExtractorule() {
		this.loadContentExtractorule();
	}
	
	@Override
	public void loadWechatConfig() {
		List<WechatConfigEntity> list = this.commonService.loadAll(WechatConfigEntity.class);
		WechatConfigEntity.allWechatConfig.clear();
		WechatConfigEntity.allWechatConfig.addAll(list);
	}
	
	@Override
	public void reloadWechatConfig() {
		this.loadWechatConfig();
	}
	
}
