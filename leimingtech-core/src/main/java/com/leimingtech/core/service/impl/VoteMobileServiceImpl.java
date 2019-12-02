package com.leimingtech.core.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.ContentMobileClassify;
import com.leimingtech.core.entity.BeanApi;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ContentsMobileServiceI;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.VoteMobileServiceI;

@Service("voteMobileService")
@Transactional
public class VoteMobileServiceImpl extends CommonServiceImpl implements VoteMobileServiceI {

	/**移动频道管理接口*/
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	
	@Autowired
	private ContentsMobileServiceI contentsMobileService;
	@Override
	public JSONObject getVoteMobileList(int pageSize, int pageNo) {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try {
			CriteriaQuery cq = new CriteriaQuery(ContentsMobileEntity.class,pageSize, pageNo, "", "");
			cq.addOrder("sortDateTime", SortDirection.desc);
			cq.or(Restrictions.eq("classify", ContentMobileClassify.CONTENT_VOTE), Restrictions.eq("classify", ContentMobileClassify.CONTENT_SURVEY));
			cq.add();
			PageList pageList = this.getPageList(cq, true);
			List<ContentsMobileEntity> testList = pageList.getResultList();
			int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
			if(pageCount <= 0){
				pageCount = 1;
			}
			beanApi.setResultCode("1");
			beanApi.setResultMsg("成功");
			beanApi.setPageCount(String.valueOf(pageCount));
			beanApi.setPageSize(String.valueOf(pageSize));
			beanApi.setItems(contentsMobileService.getAllContentsMobileList(testList));
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			
		} catch (Exception e) {
			e.printStackTrace();
			beanApi.setResultCode("0");
			beanApi.setResultMsg("服务器繁忙，稍后重试！");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}
	
}