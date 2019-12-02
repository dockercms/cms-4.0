package com.leimingtech.platform.service.impl.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.TSFunction;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.platform.service.function.FunctionServiceI;



/**
 * 菜单管理接口实现
 * 
 * @author liuzhen 2015年6月23日 16:40:08
 * 
 */
@Service("functionService")
@Transactional
public class FunctionServiceImpl implements FunctionServiceI {

	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;

	/**
	 * 保存树
	 * 
	 * @param function
	 * @return
	 */
	public java.lang.String save(TSFunction function) {
		return (java.lang.String) commonService.save(function);
	}

	/**
	 * 更新树
	 * 
	 * @param function
	 */
	@Override
	public void saveOrUpdate(TSFunction function) {
		commonService.saveOrUpdate(function);
	}

	/**
	 * 通过id获取树
	 * 
	 * @param id
	 *            树id
	 * @return
	 */
	@Override
	public TSFunction getEntity(java.lang.String id) {
		return commonService.getEntity(TSFunction.class, id);
	}

	/**
	 * 获取分页后的树数据集
	 * 
	 * @param function
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return functionList树数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(TSFunction function, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class, pageSize,
				pageNo, "", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, function, param);
		cq.addOrder("functionOrder", SortDirection.desc);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<TSFunction> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("functionList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除树
	 * 
	 * @param function
	 */
	@Override
	public void delete(TSFunction function) {
		if (function == null)
			return;

		List<TSFunction> functionList = getListByPid(function.getId());
		if (functionList != null && functionList.size() > 0) {
			int functionMaxIndex = functionList.size() - 1;
			for (int i = functionMaxIndex; i >= 0; i--) {
				delete(functionList.get(i));
			}
		}
		commonService.delete(function);
	}

	/***
	 * 获取全部一级数据
	 * 
	 * @return
	 */
	@Override
	public List<TSFunction> getAllRoot() {
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		cq.eq("functionLevel", Short.valueOf(0 + ""));
		cq.addOrder("functionOrder", SortDirection.desc);
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}

	/**
	 * 通过父id获取下一级数据
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<TSFunction> getListByPid(java.lang.String id) {
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		cq.eq("TSFunction.id", id);
		cq.addOrder("functionOrder", SortDirection.desc);
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}

	/**
	 * 获取tree节点数据
	 * 
	 * @param json
	 *            最终返回的json
	 * @param list
	 * @param openNodes
	 *            打开的节点（多个节点用逗号分隔）
	 * @param selectNode
	 *            选中的节点
	 * @return json
	 */
	private JSONArray getChildTreeJson(JSONArray json, List<TSFunction> list,
			String openNodes, String selectNode) {
		for (TSFunction function : list) {

			JSONObject jChildObject = new JSONObject();

			jChildObject.accumulate("id", function.getId());
			jChildObject.accumulate("text", function.getFunctionName());

			JSONObject stateJson = new JSONObject();
			if (StringUtils.isNotEmpty(selectNode)
					&& selectNode.equals(function.getId() + "")) {
				stateJson.accumulate("selected", true);
			}
			List<TSFunction> childlist = getListByPid(function.getId());

			if (childlist != null && childlist.size() > 0) {

				if (StringUtils.isNotEmpty(openNodes)
						&& openNodes.contains(function.getId() + "")) {
					stateJson.accumulate("opened", true);
				}
				jChildObject.accumulate("state", stateJson);

				JSONArray jChildArray = new JSONArray();
				getChildTreeJson(jChildArray, childlist, openNodes, selectNode);
				jChildObject.accumulate("children", jChildArray);
			} else {
				jChildObject.accumulate("state", stateJson);
			}

			json.add(jChildObject);
		}
		return json;
	}

	/**
	 * 获取tree节点数据
	 * 
	 * @param id
	 *            选中的节点
	 * @return
	 */
	@Override
	public JSONArray getTreeJson(String id) {

		JSONArray json = new JSONArray();

		JSONObject jstreeData = new JSONObject();
		jstreeData.accumulate("id", "-1");
		jstreeData.accumulate("text", "菜单");
		JSONObject stateJson = new JSONObject();
		stateJson.accumulate("opened", true);

		TSFunction function = new TSFunction();
		if (StringUtils.isNotEmpty(id)) {
			function = getEntity(String.valueOf(id));
		} else {
			stateJson.accumulate("selected", true);
		}
		jstreeData.accumulate("state", stateJson);

		List<TSFunction> list = getAllRoot();
		if (list != null && list.size() > 0) {
			JSONArray jChildArray = new JSONArray();
			getChildTreeJson(jChildArray, list, function.getPathids(), id);
			jstreeData.accumulate("children", jChildArray);
		}

		json.add(jstreeData);

		return json;
	}

	/**
	 * 验证Url是否在菜单表中存在
	 * 
	 * @param url
	 * @return
	 */
	@Override
	public boolean checkUrlExist(String url) {

		List<TSFunction> functionList = commonService.findByProperty(
				TSFunction.class, "functionUrl", url);
		if (functionList == null || functionList.size() == 0) {
			return false;
		}

		return true;
	}
}
