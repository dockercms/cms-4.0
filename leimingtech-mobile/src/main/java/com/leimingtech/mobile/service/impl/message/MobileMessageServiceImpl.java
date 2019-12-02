package com.leimingtech.mobile.service.impl.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.BeanApi;
import com.leimingtech.core.entity.BeanListApi;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.entity.message.MobileMessageEntity;
import com.leimingtech.mobile.service.message.MobileMessageServiceI;

@Service("mobileMessageService")
@Transactional
public class MobileMessageServiceImpl extends CommonServiceImpl implements MobileMessageServiceI {

	/**移动频道管理接口*/
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	
	/**
	 * 加载通知
	 * @return
	 */
	@Override
	public JSONObject loadMessage(String contentsMobileId) {
		JSONObject json = new JSONObject();
		BeanListApi beanListApi = new BeanListApi();
		if(StringUtil.isNotEmpty(contentsMobileId)){
			ContentsMobileEntity contents = get(ContentsMobileEntity.class, String.valueOf(contentsMobileId));
			MobileMessageEntity message = new MobileMessageEntity();
			if(contents!=null){
				message.setInformContent(contents.getDigest());
				message.setInformDate(new Date());
				message.setInformDetail(contents.getUrl());
				message.setCreatedtime(new Date());//创建时间
				save(message);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				beanListApi.setPubDate(sdf.format(new Date()));
				beanListApi.setContent(contents.getDigest());
				beanListApi.setListUrl("wwwroot/www/newsDeatil.htm?size=14");
				json = json.fromObject(beanListApi,mobileChannelService.getJsonConfig());
			}
		}
		
		return json;
	}
	/**
	 * 通知列表
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@Override
	public JSONObject messageList(int pageSize,int pageNo) {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try{
			CriteriaQuery cq = new CriteriaQuery(MobileMessageEntity.class, pageSize, pageNo, "", "");
			cq.addOrder("informDate", SortDirection.desc);
			cq.add();
			PageList pageList = this.getPageList(cq, true);
			List<MobileMessageEntity> testList = pageList.getResultList();
			int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
			if(pageCount <= 0){
				pageCount = 1;
			}
			beanApi.setResultCode("1");
			beanApi.setResultMsg("成功");
			beanApi.setPageCount(String.valueOf(pageCount));
			beanApi.setPageSize(String.valueOf(pageSize));
			beanApi.setItems(getmobileMessageList(testList));
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}catch(Exception e1){
			e1.printStackTrace();
			beanApi.setResultCode("0");
			beanApi.setResultMsg("服务器繁忙，稍后重试！");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}
	/**
	 * 所有通知内容
	 * @param mobileMessageList
	 * @return
	 */
	public List<BeanListApi> getmobileMessageList(List<MobileMessageEntity> mobileMessageList){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<BeanListApi> beanList = new ArrayList<BeanListApi>();
		for(MobileMessageEntity mobileMessage:mobileMessageList){
			BeanListApi beanListApi = new BeanListApi();
			beanListApi.setPubDate(sdf.format(mobileMessage.getInformDate()));
			beanListApi.setContent(mobileMessage.getInformContent());
			beanListApi.setListUrl(mobileMessage.getInformDetail());
			beanList.add(beanListApi);
		}
		return beanList;
	}
	
}