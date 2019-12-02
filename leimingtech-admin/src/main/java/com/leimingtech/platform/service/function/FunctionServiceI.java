package com.leimingtech.platform.service.function;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.leimingtech.core.entity.TSFunction;


/**
 * 菜单管理接口
 * 
 * @author liuzhen 2015年6月23日 16:39:08
 * 
 */
public interface FunctionServiceI {
	/**
	 * 保存菜单
	 * 
	 * @param function
	 * @return
	 */
	java.lang.String save(TSFunction function);

	/**
	 * 更新菜单
	 * 
	 * @param function
	 */
	void saveOrUpdate(TSFunction function);

	/**
	 * 通过id获取菜单
	 * 
	 * @param id
	 *            菜单id
	 * @return
	 */
	TSFunction getEntity(java.lang.String id);

	/**
	 * 获取分页后的菜单数据集
	 * 
	 * @param function
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return functionList菜单数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(TSFunction function, Map param,
			int pageSize, int pageNo);

	/**
	 * 删除菜单
	 * 
	 * @param function
	 */
	void delete(TSFunction function);

	/***
	 * 获取全部一级数据
	 * 
	 * @return
	 */
	List<TSFunction> getAllRoot();

	/**
	 * 通过父id获取下一级数据
	 * 
	 * @param id
	 * @return
	 */
	List<TSFunction> getListByPid(java.lang.String id);

	/**
	 * 获取tree节点数据
	 * 
	 * @param id
	 *            选中的节点
	 * @return
	 */
	JSONArray getTreeJson(String id);

	/**
	 * 验证Url是否在菜单表中存在
	 * 
	 * @param url
	 * @return
	 */
	boolean checkUrlExist(String url);

}
