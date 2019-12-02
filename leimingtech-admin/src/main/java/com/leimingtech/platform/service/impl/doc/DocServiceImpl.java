package com.leimingtech.platform.service.impl.doc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.platform.entity.doc.DocEnRefEntity;
import com.leimingtech.platform.entity.doc.DocEntity;
import com.leimingtech.platform.entity.doc.DocParamEntity;
import com.leimingtech.platform.entity.doc.DocReturnValueEntity;
import com.leimingtech.platform.entity.doc.DocTypeEntity;
import com.leimingtech.platform.service.doc.DocServiceI;



/**
 * 文档管理接口实现
 * 
 * @author liuzhen 2014年6月11日 16:53:11
 * 
 */
@Service("docService")
@Transactional
public class DocServiceImpl implements DocServiceI  {

	
	/**公共dao接口*/
	@Autowired
	private CommonService commonService;
	
	/**
	 * 删除文档（级联删除子集）
	 * 
	 * @param doc
	 */
	@Override
	public void delete(DocEntity doc) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String id = request.getParameter("id");
		
		List<DocParamEntity> docparamList = commonService.findByProperty(DocParamEntity.class, "docid", id);
		commonService.deleteAllEntitie(docparamList);    //删除标签及api输入参数
		List<DocReturnValueEntity> docreturnList = commonService.findByProperty(DocReturnValueEntity.class, "docid", id);
		commonService.deleteAllEntitie(docreturnList);    //删除返回结果
		List<DocEntity> docList = commonService.findByProperty(DocEntity.class, "pid", id);
		
		commonService.deleteAllEntitie(docList);       //删除api列表
		

		commonService.delete(doc);   //删除文档
	}

	/**
	 * 获取文档列表(标签调用)
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public Object getDocListByTag(Map params) {
		if (MapUtils.isEmpty(params)) {
			return null;
		}

		String typeid = (String) params.get("typeid");
		if (StringUtils.isNotEmpty(typeid)) {
			String platform = (String) params.get("platform");
			CriteriaQuery cq = new CriteriaQuery(DocEntity.class);
			cq.eq("status", 1);
			cq.eq("typeid", typeid);
			cq.eq("pid", "0");
			if(StringUtils.isNotEmpty(platform)){
				cq.eq("platform", platform);
			}
			cq.addOrder("sort", SortDirection.desc);
			cq.addOrder("createdtime", SortDirection.desc);
			cq.add();

			List<DocEntity> docList = commonService.getListByCriteriaQuery(cq, false);
			if (docList != null && docList.size() > 0) {
				for (int i = 0; i < docList.size(); i++) {
					DocTypeEntity doctype = commonService.getEntity(DocTypeEntity.class, docList.get(i).getTypeid());
					docList.get(i).setType(doctype.getName());
				}
			}

			return docList;
		}
		return null;
	}

	@Override
	public Map<String, Object> getPageList(String name, String typeid, String pid,String platform ,int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(DocEntity.class, pageSize, pageNo, "", "");
		// 排序条件
		// 查询条件组装器
		
		cq.addOrder("createdtime",SortDirection.desc);
		cq.addOrder("sort", SortDirection.desc);
		if (StringUtils.isNotEmpty(name)) {
			cq.like("name", "%" + name + "%");
		}

		if (StringUtils.isNotEmpty(pid)) {
				cq.eq("pid", pid);
			}else{
				if(StringUtils.isEmpty(pid)){
					cq.eq("pid", "0");
					pid="0";
				}
			
			}	
		
		if (StringUtils.isNotEmpty(typeid)) {
			cq.eq("typeid", typeid);
		}
		
		if (StringUtils.isNotEmpty(platform)) {
			cq.eq("platform", platform);
		}
		cq.add();

		PageList pageList = commonService.getPageList(cq, true);
		List<DocEntity> resultList = pageList.getResultList();
		if (resultList != null && resultList.size() > 0) {
			// 文档类型赋值
			for (DocEntity docEntity : resultList) {
				DocTypeEntity doctype = commonService.getEntity(DocTypeEntity.class, docEntity.getTypeid());
				if (doctype != null) {
					docEntity.setType(doctype.getName());
				}
			}
		}
	
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		Map<String, Object> m = new HashMap<String, Object>();

		if (StringUtils.isEmpty(pid) ||  pid.equals("0")) {
			// 查询文档类型，添加文档类型查询条件
			CriteriaQuery cqDocType = new CriteriaQuery(DocTypeEntity.class);
			cqDocType.eq("status", 1);
			cqDocType.addOrder("sort", SortDirection.desc);
			cqDocType.add();

			List<DocTypeEntity> docTypeList = commonService.getListByCriteriaQuery(cqDocType, false);
			m.put("docTypeList", docTypeList);
		}
		
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("actionUrl", "docController.do?doc&pid=" + pid);
		return m;
	}

	@Override
	public void saveOrUpdate(DocEntity doc) {
		commonService.saveOrUpdate(doc);
	}

	@Override
	public String save(DocEntity doc) {
		return (String) commonService.save(doc);
	}

	@Override
	public DocEntity getDoc(String id) {
		DocEntity doc=commonService.getEntity(DocEntity.class, id);
		return doc;
	}

	@Override
	public List<DocEntity> getListRedDoc(String id) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String platform = request.getParameter("platform");

		CriteriaQuery cq = new CriteriaQuery(DocEntity.class);
		cq.eq("status", 1);
		cq.eq("typeid", id);
		if (StringUtils.isNotEmpty(platform)) {
			cq.eq("platform", platform);
		}
		cq.eq("pid", "0");
		cq.addOrder("sort", SortDirection.desc);
		cq.add();

		List<DocEntity> docList = commonService.getListByCriteriaQuery(cq, false);
		
		return docList;
	}

	@Override
	public List<DocEntity> getListRedDocTag(String id) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String platform = request.getParameter("platform");

		DocEntity doc = getDoc(id);

		CriteriaQuery cq = new CriteriaQuery(DocEntity.class);
		cq.eq("status", 1);
		cq.eq("typeid", doc.getTypeid());
		if (StringUtils.isNotEmpty(platform)) {
			cq.eq("platform", platform);
		}
		cq.addOrder("sort", SortDirection.desc);
		cq.add();
		List<DocEntity> docList = commonService.getListByCriteriaQuery(cq, false);
		return docList;
	}

	@Override
	public List<DocEntity> getListRedDocApiList(String id) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String platform = request.getParameter("platform");
		DocEntity doc = getDoc(id);

		CriteriaQuery cq = new CriteriaQuery(DocEntity.class);
		cq.eq("status", 1);
		cq.eq("pid", id);
		if (StringUtils.isNotEmpty(platform)) {
			cq.eq("platform", platform);
		}
		cq.addOrder("sort", SortDirection.desc);
		cq.add();
		List<DocEntity> docList = commonService.getListByCriteriaQuery(cq, false);
		return docList;
	}

	@Override
	public List<DocEnRefEntity> findByProperty(String id) {
		List<DocEnRefEntity> docEnRefList = commonService.findByProperty(DocEnRefEntity.class, "doc.id", id);

		return docEnRefList;
	}

	@Override
	public List<DocEntity> getListRedDocApi(String id) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String platform = request.getParameter("platform");
		DocEntity doc =getDoc(id);
		CriteriaQuery cq = new CriteriaQuery(DocEntity.class);
		cq.eq("status", 1);
		cq.eq("typeid", doc.getTypeid());
		if (StringUtils.isNotEmpty(platform)) {
			cq.eq("platform", platform);
		}
		cq.eq("pid", doc.getPid());
		cq.addOrder("sort", SortDirection.desc);
		cq.add();
		List<DocEntity> docList = commonService.getListByCriteriaQuery(cq, false);
		return docList;
	}

	
}