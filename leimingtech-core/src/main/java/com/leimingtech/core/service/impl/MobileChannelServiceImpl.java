package com.leimingtech.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.ContentMobileClassify;
import com.leimingtech.core.entity.BeanApi;
import com.leimingtech.core.entity.BeanListApi;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.ContentsMobileServiceI;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.util.MobileUtil;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;

/**
 * 移动频道管理接口实现
 * 
 * @author liuzhen 2015年9月1日 09:46:02
 * 
 */
@Service("mobileChannelService")
@Transactional
public class MobileChannelServiceImpl implements MobileChannelServiceI {

	/**移动稿件管理接口*/
	@Autowired
	private ContentsMobileServiceI contentsMobileService;
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;

	/**
	 * 频道列表
	 * 
	 * @param all
	 * @param mobileChannelId
	 * @return String
	 */
	@Override
	public JSONObject getmobileChannelList(String all, String mobileChannelId) {
		// 当前频道（一级）
		MobileChannelEntity mobileChannel = getEntity(mobileChannelId);
		List<MobileChannelEntity> childlist = getListByPid(mobileChannelId);
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		if (StringUtil.isNotEmpty(all)) {
			beanApi.getItems().add(setValue());
			json = json.fromObject(beanApi, getJsonConfig());
		} else {
			if (mobileChannel == null) {
				beanApi.setResultCode("0");
				beanApi.setResultMsg("频道无数据");
				beanApi.setItems(null);
				return json.fromObject(beanApi, getJsonConfig());
			}
			try {
				if (mobileChannel.getLevels() == 0) {
					beanApi.setId(String.valueOf(mobileChannel.getId()));
					beanApi.setTitle(mobileChannel.getName());
					beanApi.setResultCode("1");
					beanApi.setResultMsg("成功");
					beanApi.setType(mobileChannel.getChannelType());
					beanApi.setItems(getValue(childlist));
					json = json.fromObject(beanApi, getJsonConfig());
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				beanApi.setResultCode("0");
				beanApi.setResultMsg("服务器繁忙，稍后重试！");
				beanApi.setItems(null);
				json = json.fromObject(beanApi, getJsonConfig());
			}
		}
		return json;
	}

	public BeanListApi setValue() {

		BeanListApi item = new BeanListApi();
		item.setId("1");
		item.setContent("一对儿女打开天窗坐上车顶，驾车父亲依然以60公里左右的时速向前行驶，这一幕，前日就发生在了北环大道上。");
		item.setStar("3");
		item.setSource("新华网");
		item.setPubDate("2014-07-17 10:30:00");
		item.setType("news");
		item.setListUrl("/front/contentMobileApi/loadContentList.do?mobileChannelId=&pageSize=&pageNo=&all=y");
		item.setTitle("交通事件");
		item.setTopUrl("/front/slideMobileApi/loadSlideList.do?mobileChannelId=&pageSize=&pageNo=&all=y");
		item.setPageLink("/front/slideMobileApi/loadSlideList.do?mobileChannelId=&pageSize=&pageNo=&all=y");

		return item;
	}

	/**
	 * 一级频道下所有频道
	 * 
	 * @param mobileChannelList1
	 * @return
	 */
	public List<BeanListApi> getValue(
			List<MobileChannelEntity> mobileChannelList1) {
		List<BeanListApi> beanList = new ArrayList<BeanListApi>();
		for (MobileChannelEntity mobileChannel : mobileChannelList1) {
			BeanListApi item = new BeanListApi();

			item.setChannelTop(mobileChannel.getChannelTop());// 头条 0 不是 1是
			item.setChannelHot(mobileChannel.getChannelHot());// 热门 0不是 1 是
			item.setId(String.valueOf(mobileChannel.getId()));
			item.setTitle(mobileChannel.getName());
			item.setType(mobileChannel.getChannelType());
			item.setListUrl("/front/contentMobileApi/loadContentList.do?mobileChannelId="
					+ mobileChannel.getId());
			item.setTopUrl("/front/slideMobileApi/loadSlideList.do?mobileChannelId="
					+ mobileChannel.getId());
			item.setCreated(mobileChannel.getCreated()); // 发布时间

			beanList.add(item);
		}
		return beanList;
	}

	/**
	 * 设置过滤值为空的属性，使得生成的 json 字符串只包含非空的值
	 * 
	 * @return
	 */
	public JsonConfig getJsonConfig() {
		JsonConfig jsonConfig = new JsonConfig();
		// 设置为""的String类型转为null
		// jsonConfig.registerDefaultValueProcessor(String.class, new
		// DefaultValueProcessor(){
		// public Object getDefaultValue(Class type) {
		// return null;
		// }
		// });
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				return value == null;
			}
		});
		return jsonConfig;
	}

	/**
	 * 获取内容类型
	 * 
	 * @param classify
	 * @return
	 */
	@Override
	public String getContentType(String classify) {
		String str = "";
		if (classify.equals(ContentMobileClassify.CONTENT_ARTICLE)
				|| classify.equals(ContentMobileClassify.CONTENT_LINK)
				|| classify.equals(ContentMobileClassify.CONTENT_SURVEY)
				|| classify.equals(ContentMobileClassify.CONTENT_VOTE)) {
			str = "news";
		}
		if (classify.equals(ContentMobileClassify.CONTENT_PICTUREGROUP)) {
			str = "pic";
		}
		if (classify.equals(ContentMobileClassify.CONTENT_VIDEO)) {
			str = "video";
		}
		return str;
	}

	/**
	 * 给栏目子集排序
	 * 
	 * @param list
	 */
	@Override
	public void sortChildCatalog(List<MobileChannelEntity> list) {
		if (list != null && list.size() > 0) {
			Set<String> pcCatalog = MobileUtil.initMobileCatalogPermission();
			if (pcCatalog == null || pcCatalog.size() == 0) {
				list.clear();
				return;
			}
			for (int i = list.size() - 1; i >= 0; i--) {
				if (pcCatalog.contains(list.get(i).getId().toString())) {
					List<MobileChannelEntity> childlist = getListByPid(list
							.get(i).getId());
					sortChildCatalog(childlist);
					list.get(i).setMobileChannels(childlist);
				} else {
					list.remove(i);
				}
			}
		}

	}

	@Override
	public List<MobileChannelEntity> getRootMobileChannel() {
		CriteriaQuery cq = new CriteriaQuery(MobileChannelEntity.class);
		cq.eq("levels", 0);
		cq.eq("siteid", PlatFormUtil.getSessionSiteId());
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		List<MobileChannelEntity> list = commonService.getListByCriteriaQuery(
				cq, false);
		return list;
	}

	@Override
	public List<MobileChannelEntity> getListByPid(String id) {
		CriteriaQuery cq = new CriteriaQuery(MobileChannelEntity.class);
		cq.eq("siteid", PlatFormUtil.getSessionSiteId());
		cq.eq("mobileChannel.id", id);
		cq.addOrder("channelTop", SortDirection.desc);
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		List<MobileChannelEntity> list = commonService.getListByCriteriaQuery(
				cq, false);
		return list;
	}

	/**
	 * 递归栏目子节点数据
	 * 
	 * @param jChildArray
	 * @param list
	 */
	private JSONArray setChildInfo(JSONArray jArray,
			List<MobileChannelEntity> list, String openNodes, String selectNode) {

		for (MobileChannelEntity mobile : list) {

			JSONObject jChildObject = new JSONObject();

			jChildObject.accumulate("id", mobile.getId());
			jChildObject.accumulate("text", mobile.getName());

			JSONObject stateJson = new JSONObject();
			if (StringUtils.isNotEmpty(selectNode)
					&& selectNode.equals(mobile.getId() + "")) {
				stateJson.accumulate("selected", true);
			}
			List<MobileChannelEntity> childlist = getListByPid(mobile.getId());

			if (childlist != null && childlist.size() > 0) {

				if (StringUtils.isNotEmpty(openNodes)
						&& openNodes.contains(mobile.getId() + "")) {
					stateJson.accumulate("opened", true);
				}
				jChildObject.accumulate("state", stateJson);

				JSONArray jChildArray = new JSONArray();
				setChildInfo(jChildArray, childlist, openNodes, selectNode);
				jChildObject.accumulate("children", jChildArray);
			} else {
				jChildObject.accumulate("state", stateJson);
			}

			jArray.add(jChildObject);
		}
		return jArray;
	}

	@Override
	public JSONArray getTreeJson(String id) {
		JSONArray json = new JSONArray();

		JSONObject jstreeData = new JSONObject();
		jstreeData.accumulate("id", "-1");
		jstreeData.accumulate("text", "频道");
		JSONObject stateJson = new JSONObject();
		stateJson.accumulate("opened", true);

		MobileChannelEntity mobile = new MobileChannelEntity();
		if (StringUtils.isNotEmpty(id)) {
			mobile = commonService.getEntity(MobileChannelEntity.class,
					String.valueOf(id));
		} else {
			stateJson.accumulate("selected", true);
		}
		jstreeData.accumulate("state", stateJson);

		List<MobileChannelEntity> list = getRootMobileChannel();
		if (list != null && list.size() > 0) {
			JSONArray jChildArray = new JSONArray();
			setChildInfo(jChildArray, list, mobile.getPathids(), id);
			jstreeData.accumulate("children", jChildArray);
		}
		json.add(jstreeData);
		return json;
	}

	/**
	 * 保存移动稿件
	 * 
	 * @param mobileChannel
	 * @return
	 */
	public java.lang.String save(MobileChannelEntity mobileChannel) {
		return (java.lang.String) commonService.save(mobileChannel);
	}

	/**
	 * 更新移动稿件
	 * 
	 * @param mobileChannel
	 */
	@Override
	public void saveOrUpdate(MobileChannelEntity mobileChannel) {
		commonService.saveOrUpdate(mobileChannel);
	}

	/**
	 * 通过id获取移动稿件
	 * 
	 * @param id
	 *            移动稿件id
	 * @return
	 */
	@Override
	public MobileChannelEntity getEntity(java.lang.String id) {
		return commonService.getEntity(MobileChannelEntity.class, id);
	}

	/**
	 * 获取分页后的移动稿件数据集
	 * 
	 * @param mobileChannel
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return mobileChannelList移动稿件数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(MobileChannelEntity mobileChannel,
			Map param, int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(MobileChannelEntity.class,
				pageSize, pageNo, "", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, mobileChannel, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<ContentsMobileEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mobileChannelList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除移动稿件
	 * 
	 * @param mobileChannel
	 */
	@Override
	public void delete(MobileChannelEntity mobileChannel) {

		List<MobileChannelEntity> childCatalogs = getListByPid(mobileChannel
				.getId());// 删除频道时，需要先删除所有子数据

		if (childCatalogs != null && childCatalogs.size() > 0) {
			for (MobileChannelEntity childMobileChannel : childCatalogs) {
				this.delete(childMobileChannel);
			}
		}

		contentsMobileService.deleteByCat(mobileChannel.getId());// 通过频道删除频道中的稿件
		commonService.delete(mobileChannel);
	}

	/**
	 * 将指定同级目录中的频道更改为非头条
	 * 
	 * @param id
	 * @param pid
	 */
	@Override
	public void updateToNotTop(String id, String pid) {
		commonService
				.updateBySqlString("update cm_mobile_channel set channel_top=0 where parentid = '"
						+ pid + "' and id <> '" + id + "'  ");
	}

	/**
	 * 根据一级频道获取下一级中是否已经存在头条频道
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public boolean isOneTop(String pid) {
		List<Map<String,Object>> result=commonService.findForJdbc("select count(*) num from cm_mobile_channel where channel_top=1 and parentid=? ", pid);
		if(result!=null && result.size()>0){
			int topCount=MapUtils.getIntValue(result.get(0), "num", 0);
			if(topCount!=0){
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据站点获取以及频道列表
	 * @param siteid
	 * @return
	 */
	@Override
	public List<MobileChannelEntity> getRootMobileChannelBySite(String siteid) {
		CriteriaQuery cq = new CriteriaQuery(MobileChannelEntity.class);
		cq.eq("levels", 0);
		cq.eq("siteid", siteid);
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		List<MobileChannelEntity> list = commonService.getListByCriteriaQuery(
				cq, false);
		return list;
	}

}