package com.leimingtech.mobile.service.message;

import java.util.List;

import net.sf.json.JSONObject;

import com.leimingtech.core.entity.BeanListApi;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.mobile.entity.message.MobileMessageEntity;

public interface MobileMessageServiceI extends CommonService{

	/**
	 * 加载通知
	 * @return
	 */
	public JSONObject loadMessage(String contentsMobileId);
	/**
	 * 通知列表
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public JSONObject messageList(int pageSize,int pageNo);
	/**
	 * 所有通知内容
	 * @param mobileMessageList
	 * @return
	 */
	public List<BeanListApi> getmobileMessageList(List<MobileMessageEntity> mobileMessageList);
}
