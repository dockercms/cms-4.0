package com.leimingtech.platform.service.impl.doc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.entity.doc.DocEntity;
import com.leimingtech.platform.entity.doc.DocTypeEntity;
import com.leimingtech.platform.service.doc.DocServiceI;
import com.leimingtech.platform.service.doc.DocTypeServiceI;

@Service("docTypeService")
@Transactional
public class DocTypeServiceImpl extends CommonServiceImpl implements DocTypeServiceI {
	
	@Autowired
	private DocServiceI docService;

	/**
	 * 删除文档类别（级联删除子集）
	 * 
	 * @param docType
	 */
	@Override
	public void delete(DocTypeEntity docType) {
		CriteriaQuery cq = new CriteriaQuery(DocEntity.class);
		cq.eq("typeid", docType.getId());
		cq.add();

		List<DocEntity> docList = getListByCriteriaQuery(cq, false);
		if (docList != null && docList.size() > 0) {
			for (DocEntity docEntity : docList) {
				docService.delete(docEntity);
			}
		}
		delete(docType);
	}
}