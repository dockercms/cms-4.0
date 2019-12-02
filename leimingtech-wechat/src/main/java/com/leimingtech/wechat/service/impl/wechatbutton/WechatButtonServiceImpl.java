package com.leimingtech.wechat.service.impl.wechatbutton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import com.leimingtech.core.entity.WechatConfigEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.wechat.entity.wechatbutton.WechatButtonEntity;
import com.leimingtech.wechat.service.wechatbutton.WechatButtonServiceI;
import com.leimingtech.wechat.util.WechatInterfaceRequestUtil;

/**
 * @Title: interface
 * @Description: 自定义菜单管理接口实现
 * @author
 * @date 2015-08-12 18:20:34
 * @version V1.0
 * 
 */
@Service("wechatButtonService")
@Transactional
public class WechatButtonServiceImpl implements WechatButtonServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存自定义菜单管理
	 * 
	 * @param wechatButton
	 * @return
	 */
	public String save(WechatButtonEntity wechatButton) {
		return (String)commonService.save(wechatButton);
	}

	/**
	 * 更新自定义菜单管理
	 *
	 * @param wechatButton
	 */
	@Override
	public void saveOrUpdate(WechatButtonEntity wechatButton) {
		commonService.saveOrUpdate(wechatButton);
	}

	/**
	 * 通过id获取自定义菜单管理
	 *
	 * @param id
	 *            自定义菜单管理id
	 * @return
	 */
	@Override
	public WechatButtonEntity getEntity(String id) {
		return commonService.getEntity(WechatButtonEntity.class , id);
	}
	
	/**
	 * 获取分页后的自定义菜单管理数据集
	 * 
	 * @param wechatButton
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return wechatButtonList自定义菜单管理数据集 pageCount总页数
	 */
	@Override
	public Map<String , Object> getPageList(WechatButtonEntity wechatButton , Map param , int pageSize , int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(WechatButtonEntity.class , pageSize , pageNo , "" , "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq , wechatButton , param);
		cq.add();
		PageList pageList = commonService.getPageList(cq , true);
		List<WechatButtonEntity> resultList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0) {
			pageCount = 1;
		}
		Map<String , Object> m = new HashMap<String , Object>();
		m.put("wechatButtonList" , resultList);
		m.put("pageCount" , pageCount);
		return m;
	}
	
	/**
	 * 删除自定义菜单管理
	 * 
	 * @param wechatButton
	 */
	@Override
	public void delete(WechatButtonEntity wechatButton) {
		commonService.delete(wechatButton);
	}
	
	@Override
	public void autoGetWechatServerAllMenus() {
		// 获取同步菜单接口地址参数
		String url = WechatConfigEntity.getConfig("getWechatServerAllMenus");
		// 获取系统已录入的所有微信号
		String getAllUserSql = "select id,access_token accessToken from wechat_user;";
		List<Map<String , Object>> allWechatUser = this.commonService.findForJdbc(getAllUserSql);
		for(Map<String , Object> map : allWechatUser) {
			Object access_token = map.get("accessToken");
			if(access_token != null) {
				if(StringUtils.isNotEmpty(access_token.toString())) {
					url = String.format(url , access_token);
					JSONObject json = WechatInterfaceRequestUtil.httpsRequestResultAsJson(url , RequestMethod.GET);
					Integer is_menu_open=json.getInt("is_menu_open");
					JSONArray selfmenu_info = json.getJSONArray("selfmenu_info");
					//TODO 从json中获取主要信息，同步到系统中
				}
			}
			
		}
	}
}