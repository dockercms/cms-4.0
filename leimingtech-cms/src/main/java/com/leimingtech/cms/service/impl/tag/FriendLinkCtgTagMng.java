package com.leimingtech.cms.service.impl.tag;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.entity.friendlink.FriendLinkCtgEntity;
import com.leimingtech.cms.service.tag.IFriendLinkCtgTagMng;
import com.leimingtech.core.service.CommonService;

@Service("friendLinkCtgTagMng")
@Transactional
public class FriendLinkCtgTagMng implements IFriendLinkCtgTagMng {


	/**
	 * 友情链接分类
	 */
	@Autowired
	private CommonService commonService ;
	

	@Override
	public Object getFriendLinkCtg(Map params) {
		String name=(String) params.get("name");
	
		
		String siteid = (String) params.get("siteid");// 站点id 非必填值
		
		String count=(String)  params.get("count");
		
		if(StringUtils.isNotEmpty(name)){
			FriendLinkCtgEntity friendLinkCtg=	commonService.get(FriendLinkCtgEntity.class,name);
			return friendLinkCtg;
		
		}else{
			
			List<FriendLinkCtgEntity> friendLinkCtgList=commonService.findByProperty(FriendLinkCtgEntity.class, "siteId", siteid);
			if(StringUtils.isNotEmpty(count) && StringUtils.isNumeric(count) && friendLinkCtgList != null){
				if(friendLinkCtgList.size()>Integer.valueOf(count)){
					friendLinkCtgList = friendLinkCtgList.subList(0, Integer.valueOf(count));
				}
			}
			return friendLinkCtgList;
		}
	}


}
