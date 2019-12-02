package ${bussiPackage}.service.impl.${entityPackage};

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${bussiPackage}.entity.${entityPackage}.${entityName}Entity;
import ${bussiPackage}.service.${entityPackage}.${entityName}ServiceI;

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
		commonService.delete(${entityName?uncap_first});
	}
	
}