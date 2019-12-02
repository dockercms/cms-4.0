package com.leimingtech.platform.service.impl.doc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.entity.doc.DocEnEntity;
import com.leimingtech.platform.service.doc.DocEnServiceI;
@Service("docEnService")
@Transactional
public class DocEnServiceImpl extends CommonServiceImpl implements DocEnServiceI {
	public void delMain(DocEnEntity docEn) {
		//删除主表信息
		this.delete(docEn);
		//获取参数
		Object id0 = docEn.getId();	
		//删除-子表
		this.executeSql("delete from cms_doc_entity_property where 1 = 1 AND entityid = ? ", id0);
	}
}