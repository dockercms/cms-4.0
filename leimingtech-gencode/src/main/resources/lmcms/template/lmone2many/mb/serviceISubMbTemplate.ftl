package ${bussiPackage}.service.${entityPackage};

import java.util.List;
import java.util.Map;

import ${bussiPackage}.entity.${entityPackage}.${entityName}Entity;

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
	
	/**
	 * 通过主表id获取关联数据
	 * 
	 * @param id
	 * @return
	 */
	List<${entityName}Entity> getListByPid(${primary_key_type} id);

}
