package ${bussiPackage}.service.${entityPackage};

import java.util.List;
import java.util.Map;

import ${bussiPackage}.entity.${entityPackage}.${entityName}Entity;
<#list subTab as sub>
import ${bussiPackage}.entity.${sub.entityPackage}.${sub.entityName}Entity;
</#list>

/**
 * @Title: interface
 * @Description: ${ftl_description}接口
 * @author
 * @date ${ftl_create_time}
 * @version V1.0
 * 
 */
public interface ${entityName}ServiceI {

	/**
	 * 保存${ftl_description}
	 * 
	 * @param ${entityName?uncap_first}
	 * @return
	 */
	${primary_key_type} save(${entityName}Entity ${entityName?uncap_first});

	/**
	 * 更新${ftl_description}
	 * 
	 * @param ${entityName?uncap_first}
	 */
	void saveOrUpdate(${entityName}Entity ${entityName?uncap_first});

	/**
	 * 通过id获取${ftl_description}
	 * 
	 * @param id
	 *            ${ftl_description}id
	 * @return
	 */
	${entityName}Entity getEntity(${primary_key_type} id);

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
	Map<String, Object> getPageList(${entityName}Entity ${entityName?uncap_first}, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除${ftl_description}
	 * 
	 * @param ${entityName?uncap_first}
	 */
	void delete(${entityName}Entity ${entityName?uncap_first});
	
	/***
	 * 获取全部数据
	 * 
	 * @return
	 */
	List<${entityName}Entity> getAllData();
	
	<#list subTab as sub>
	/**
	 * 通过主表id获取关联数据
	 * 
	 * @param id
	 * @return
	 */
	List<${sub.entityName}Entity> getListByPid(${primary_key_type} id);
	
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
	Map<String, Object> getListByPid(${sub.entityName}Entity ${sub.entityName?uncap_first}, Map param, int pageSize,
			int pageNo,${primary_key_type} id);
	
	/**
	 * 删除${sub.ftlDescription}
	 * 
	 * @param ${sub.entityName?uncap_first}
	 */
	void delete(${sub.entityName}Entity ${sub.entityName?uncap_first});
	
	/**
	 * 通过id获取${sub.ftlDescription}
	 * 
	 * @param id
	 *            ${sub.ftlDescription}id
	 * @return
	 */
	${sub.entityName}Entity get${sub.entityName}Entity(${primary_key_type} id);
	
	/**
	 * 保存${sub.ftlDescription}
	 * 
	 * @param ${sub.entityName?uncap_first}
	 * @return
	 */
	${primary_key_type} save(${sub.entityName}Entity ${sub.entityName?uncap_first});

	/**
	 * 更新${sub.ftlDescription}
	 * 
	 * @param ${sub.entityName?uncap_first}
	 */
	void saveOrUpdate(${sub.entityName}Entity ${sub.entityName?uncap_first});
	</#list>
	
}
