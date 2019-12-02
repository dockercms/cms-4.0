package com.leimingtech.core.service;

import java.util.Set;

import com.leimingtech.core.entity.TSFunction;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.entity.TSUser;

/**
 * 
 * @author
 * 
 */
public interface SystemService {
	
	/**
	 * 登陆用户检查
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public TSUser checkUserExits(TSUser user);
	
	/**
	 * 日志添加
	 * 
	 * @param LogContent
	 *            内容
	 * @param loglevel
	 *            级别
	 * @param operatetype
	 *            类型
	 * @param TUser
	 *            操作人
	 */
	public void addLog(String LogContent , Short loglevel , Short operatetype);
	
	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * 
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSType getType(String typecode , String typename , TSTypegroup tsTypegroup);
	
	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * 
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSTypegroup getTypeGroup(String typegroupcode , String typgroupename);
	
	/**
	 * 根据用户ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * 
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	public Set<String> getOperationCodesByUserIdAndFunctionId(String userId , String functionId);
	
	/**
	 * 根据角色ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * 
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId , String functionId);
	
	/**
	 * 根据编码获取字典组
	 * 
	 * @param typegroupCode
	 * @return
	 */
	public TSTypegroup getTypeGroupByCode(String typegroupCode);
	
	/**
	 * 对数据字典进行缓存
	 */
	public void initAllTypeGroups();
	
	/**
	 * 刷新字典缓存
	 * 
	 * @param type
	 */
	public void refleshTypesCach(TSType type);
	
	/**
	 * 刷新字典分组缓存
	 */
	public void refleshTypeGroupCach();
	
	/**
	 * 刷新菜单
	 * 
	 * @param id
	 */
	public void flushRoleFunciton(String id , TSFunction newFunciton);
	
	/**
	 * 初始化平台配置
	 */
	public void initAllPfConfig();
	
	/**
	 * 更新配置缓存
	 */
	public void refleshPfConfig();
	
	/**
	 * 装载所有内容抓取规则
	 */
	public void loadContentExtractorule();
	
	/**
	 * 重新装载内容抓取规则
	 */
	public void reloadContentExtractorule();
	
	/**
	 * 装载所有微信接口参数
	 */
	public void loadWechatConfig();
	
	/**
	 * 重新装载所有微信接口参数
	 */
	public void reloadWechatConfig();
	
}
