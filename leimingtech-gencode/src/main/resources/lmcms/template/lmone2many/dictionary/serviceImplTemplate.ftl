package ${bussiPackage}.service.impl.${entityPackage};

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${bussiPackage}.entity.${entityPackage}.${entityName}Entity;
import ${bussiPackage}.service.${entityPackage}.${entityName}ServiceI;
<#list subTab as sub>
import ${bussiPackage}.entity.${sub.entityPackage}.${sub.entityName}Entity;
</#list>

import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;

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

		<#list subTab as sub>
		List<${sub.entityName}Entity> ${sub.entityName?uncap_first}List = getListByPid(${entityName?uncap_first}.getId());
		if (${sub.entityName?uncap_first}List != null && ${sub.entityName?uncap_first}List.size() > 0) {
			int ${sub.entityName?uncap_first}MaxIndex = ${sub.entityName?uncap_first}List.size() - 1;
			for (int i = ${sub.entityName?uncap_first}MaxIndex; i >= 0; i--) {
				delete(${sub.entityName?uncap_first}List.get(i));
			}
		}
		</#list>
		commonService.delete(${entityName?uncap_first});
	}
	
	/***
	 * 获取全部数据
	 * 
	 * @return
	 */
	@Override
	public List<${entityName}Entity> getAllData() {
		CriteriaQuery cq = new CriteriaQuery(${entityName}Entity.class);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}
	
	<#list subTab as sub>
	/**
	 * 通过主表id获取关联数据
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<${sub.entityName}Entity> getListByPid(${primary_key_type} id) {
		CriteriaQuery cq = new CriteriaQuery(${sub.entityName}Entity.class);
		cq.eq("${sub.foreignKeys[1]?uncap_first}", id);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}
	
	/**
	 * 获取分页后的${sub.ftlDescription}数据集
	 * 
	 * @param ${sub.entityName?uncap_first}
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @param id
	 *            主表id
	 * @return ${sub.entityName?uncap_first}List${sub.ftlDescription}数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getListByPid(${sub.entityName}Entity ${sub.entityName?uncap_first}, Map param,
			int pageSize, int pageNo,${primary_key_type} id) {
		CriteriaQuery cq = new CriteriaQuery(${sub.entityName}Entity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, ${sub.entityName?uncap_first}, param);
		cq.eq("${sub.foreignKeys[1]?uncap_first}", ${primary_key_type}.valueOf(id));
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<${sub.entityName}Entity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("${sub.entityName?uncap_first}List", resultList);
		m.put("pageCount", pageCount);
		return m;
	}
	
	/**
	 * 删除${sub.ftlDescription}
	 * 
	 * @param ${sub.entityName?uncap_first}
	 */
	@Override
	public void delete(${sub.entityName}Entity ${sub.entityName?uncap_first}) {
		commonService.delete(${sub.entityName?uncap_first});
	}
	
	/**
	 * 通过id获取${sub.ftlDescription}
	 * 
	 * @param id
	 *            ${sub.ftlDescription}id
	 * @return
	 */
 	@Override
	public ${sub.entityName}Entity get${sub.entityName}Entity(${primary_key_type} id){
		return commonService.getEntity(${sub.entityName}Entity.class, id);
	}
	
	/**
	 * 保存${sub.ftlDescription}
	 * 
	 * @param ${sub.entityName?uncap_first}
	 * @return
	 */
	public ${primary_key_type} save(${sub.entityName}Entity ${sub.entityName?uncap_first}) {
		return (${primary_key_type}) commonService.save(${sub.entityName?uncap_first});
	}

	/**
	 * 更新${sub.ftlDescription}
	 * 
	 * @param ${sub.entityName?uncap_first}
	 */
	@Override
	public void saveOrUpdate(${sub.entityName}Entity ${sub.entityName?uncap_first}) {
		commonService.saveOrUpdate(${sub.entityName?uncap_first});
	}
	</#list>
	
}