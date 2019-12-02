package com.leimingtech.platform.service;

import java.util.List;

import com.leimingtech.core.service.CommonService;
import com.leimingtech.platform.entity.TSAttachment;

/**
 * 
 * @author  
 *
 */
public interface DeclareService extends CommonService{
	
	public List<TSAttachment> getAttachmentsByCode(String businessKey,String description);
	
}
