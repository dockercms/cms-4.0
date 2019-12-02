<#list originalColumns as po>
<#if po.fieldName == lmcms_table_id && lmcms_primary_key_policy == 'uuid'>
<#else>
	<#if po.fieldName?index_of("parentid")!=-1 || po.fieldName?index_of("Parentid")!=-1>
		<#assign parent=po>
	</#if>
	<#if po.fieldName?index_of("level")!=-1 || po.fieldName?index_of("Level")!=-1>
		<#assign level=po>
	</#if>
	<#if po.fieldName?index_of("sort")!=-1 || po.fieldName?index_of("Sort")!=-1>
		<#assign sort=po>
	</#if>
	<#if po.fieldName?index_of("pathids")!=-1 || po.fieldName?index_of("Pathids")!=-1>
		<#assign pathids=po>
	</#if>
</#if>
</#list>
package ${bussiPackage}.service.impl.${entityPackage};

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.base.SortDirection;
import ${bussiPackage}.entity.${entityPackage}.${entityName}Entity;
import ${bussiPackage}.service.${entityPackage}.${entityName}ServiceI;

/**
 * @Title: interface
 * @Description: ${ftl_description}接口实现
 * @author
 * @date ${ftl_create_time}
 * @version V1.0
 * 
 */
@Service("${entityName?uncap_first}Service")
@Transactional
public class ${entityName}ServiceImpl implements ${entityName}ServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存${ftl_description}
	 * 
	 * @param ${entityName?uncap_first}
	 * @return
	 */
	public ${primary_key_type} save(${entityName}Entity ${entityName?uncap_first}) {
		return (${primary_key_type}) commonService.save(${entityName?uncap_first});
	}

	/**
	 * 更新${ftl_description}
	 * 
	 * @param ${entityName?uncap_first}
	 */
	@Override
	public void saveOrUpdate(${entityName}Entity ${entityName?uncap_first}) {
		commonService.saveOrUpdate(${entityName?uncap_first});
	}

	/**
	 * 通过id获取${ftl_description}
	 * 
	 * @param id
	 *            ${ftl_description}id
	 * @return
	 */
	@Override
	public ${entityName}Entity getEntity(${primary_key_type} id) {
		return commonService.getEntity(${entityName}Entity.class, id);
	}

	/**
	 * 获取分页后的${ftl_description}数据集
	 * 
	 * @param ${entityName?uncap_first}
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return ${entityName?uncap_first}List${ftl_description}数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(${entityName}Entity ${entityName?uncap_first}, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(${entityName}Entity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, ${entityName?uncap_first}, param);
		cq.addOrder("${sort.fieldName}", SortDirection.desc);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<${entityName}Entity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("${entityName?uncap_first}List", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除${ftl_description}
	 * 
	 * @param ${entityName?uncap_first}
	 */
	@Override
	public void delete(${entityName}Entity ${entityName?uncap_first}) {
		if (${entityName?uncap_first} == null)
			return;

		List<${entityName}Entity> ${entityName?uncap_first}List = getListByPid(${entityName?uncap_first}.getId());
		if (${entityName?uncap_first}List != null && ${entityName?uncap_first}List.size() > 0) {
			int ${entityName?uncap_first}MaxIndex = ${entityName?uncap_first}List.size() - 1;
			for (int i = ${entityName?uncap_first}MaxIndex; i >= 0; i--) {
				delete(${entityName?uncap_first}List.get(i));
			}
		}
		commonService.delete(${entityName?uncap_first});
	}
	
	/***
	 * 获取全部一级数据
	 * 
	 * @return
	 */
	@Override
	public List<${entityName}Entity> getAllRoot() {
		CriteriaQuery cq = new CriteriaQuery(${entityName}Entity.class);
		cq.eq("${level.fieldName}", 0);
		cq.addOrder("${sort.fieldName}", SortDirection.desc);
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
	public List<${entityName}Entity> getListByPid(${primary_key_type} id) {
		CriteriaQuery cq = new CriteriaQuery(${entityName}Entity.class);
		cq.eq("${entityName?uncap_first}.id", id);
		cq.addOrder("${sort.fieldName}", SortDirection.desc);
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
	private JSONArray getChildTreeJson(JSONArray json, List<${entityName}Entity> list,
			String openNodes, String selectNode) {
		for (${entityName}Entity ${entityName?uncap_first} : list) {

			JSONObject jChildObject = new JSONObject();

			jChildObject.accumulate("id", ${entityName?uncap_first}.getId());
			jChildObject.accumulate("text", ${entityName?uncap_first}.getName());

			JSONObject stateJson = new JSONObject();
			if (StringUtils.isNotEmpty(selectNode)
					&& selectNode.equals(${entityName?uncap_first}.getId() + "")) {
				stateJson.accumulate("selected", true);
			}
			List<${entityName}Entity> childlist = getListByPid(tree.getId());

			if (childlist != null && childlist.size() > 0) {

				if (StringUtils.isNotEmpty(openNodes)
						&& openNodes.contains(${entityName?uncap_first}.getId() + "")) {
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
		jstreeData.accumulate("text", "测试树");
		JSONObject stateJson = new JSONObject();
		stateJson.accumulate("opened", true);

		${entityName}Entity ${entityName?uncap_first} = new ${entityName}Entity();
		if (StringUtils.isNotEmpty(id)) {
			${entityName?uncap_first} = getEntity(String.valueOf(id));
		} else {
			stateJson.accumulate("selected", true);
		}
		jstreeData.accumulate("state", stateJson);

		List<${entityName}Entity> list = getAllRoot();
		if (list != null && list.size() > 0) {
			JSONArray jChildArray = new JSONArray();
			getChildTreeJson(jChildArray, list, ${entityName?uncap_first}.get${pathids.fieldName?cap_first}(), id);
			jstreeData.accumulate("children", jChildArray);
		}

		json.add(jstreeData);

		return json;
	}
	
}