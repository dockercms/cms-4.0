package com.leimingtech.cms.service.impl.simplespecial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.simplespecial.SimplespecialServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.BeanApi;
import com.leimingtech.core.entity.BeanListApi;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.SimplespecialContentEntity;
import com.leimingtech.core.entity.SimplespecialEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ContentsMobileServiceI;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.util.StringUtil;

@Service("simplespecialService")
@Transactional
public class SimplespecialServiceImpl extends CommonServiceImpl implements SimplespecialServiceI {

	@Autowired
	private ContentsMobileServiceI contentsMobileService;
	/**移动频道管理接口*/
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	
	@Override
	public JSONObject loadSimpleSpecial(int pageSize,int pageNo) {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		BeanListApi item = new BeanListApi();
		try {
			//排序条件
			CriteriaQuery cq = new CriteriaQuery(SimplespecialEntity.class, pageSize, pageNo, "", "");
			cq.addOrder("specialCreate", SortDirection.desc);
			cq.add();
			PageList pageList = this.getPageList(cq, true);
			List<SimplespecialEntity> testList = pageList.getResultList();
			int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
			if(pageCount <= 0){
				pageCount = 1;
			}
			beanApi.setPageCount(String.valueOf(pageCount));
			beanApi.setPageSize(String.valueOf(pageSize));
			beanApi.setResultCode("1");
			beanApi.setResultMsg("成功");
			beanApi.setItems(getList(testList));
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
	/**
	 * 所有专题集合
	 * @param simpleSpecialList
	 * @return
	 */
	public List<BeanListApi> getList(List<SimplespecialEntity> simpleSpecialList){
		List<BeanListApi> beanList = new ArrayList<BeanListApi>();
		for(SimplespecialEntity simpleSpecial:simpleSpecialList){
			BeanListApi item = new BeanListApi();
			item.setId(String.valueOf(simpleSpecial.getId()));
			item.setTitle(simpleSpecial.getSpecialName());
			item.setContent(simpleSpecial.getSpecialContent());
			item.setListImage(simpleSpecial.getSpecialThub());
			item.setListUrl("/front/simpleSpecialApi/loadSimpleSpecialContent.do?simpleSpecialId="+simpleSpecial.getId()+"&pageSize=10&pageNo=1");
			beanList.add(item);
		}
		return beanList;
	}
	@Override
	public JSONObject loadSimpleSpecialContent(int pageSize,int pageNo,String simpleSpecialId,int isTop) {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		
		List<ContentsEntity> contentsList = new ArrayList<ContentsEntity>();
		SimplespecialEntity simpleSpecial = get(SimplespecialEntity.class, simpleSpecialId);
		String contentIds = "";
		try {
			//获取专题和内容关联的集合
			List<SimplespecialContentEntity> simpleSpecialContentList = findByProperty(SimplespecialContentEntity.class, "simplespecialid", simpleSpecialId);
			for(SimplespecialContentEntity simpleSpecialContent:simpleSpecialContentList){
				ContentsEntity contents = get(ContentsEntity.class, simpleSpecialContent.getContentid());
				if(null!=contents){
					contentsList.add(contents);
				}
			}
			//通过获取到的PC内容遍历获取移动内容集合
			if(contentsList.size()!=0){
				for(ContentsEntity contents:contentsList){
					List<ContentsMobileEntity> contentsMoblieList = findByProperty(ContentsMobileEntity.class, "relevanceid", contents.getId());
					if(contentsMoblieList.size()!=0){
						contentIds += contentsMoblieList.get(0).getId()+",";
					}
				}
			}
			json = contentsMobileService.getcontentsMobileList("", "", pageSize, pageNo,isTop,contentIds,simpleSpecial);
		} catch (Exception e) {
			e.printStackTrace();
			beanApi.setResultCode("0");
			beanApi.setResultMsg("服务器繁忙，稍后重试！");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}
	@Override
	public JSONObject unlockContent(String contentId,String simplespecialId) {
		JSONObject json = new JSONObject();
		String message = "";
		try {
			List<SimplespecialContentEntity> simpleSpecialContentList = new ArrayList<SimplespecialContentEntity>();
			//删除所选单个关联内容
			if(StringUtil.isNotEmpty(contentId)){
				simpleSpecialContentList = findByProperty(SimplespecialContentEntity.class, "contentid", Integer.valueOf(contentId));
				if(simpleSpecialContentList.size()!=0){
					SimplespecialContentEntity simpleSpecialContent = simpleSpecialContentList.get(0);
					delete(simpleSpecialContent);
				}
				message = "成功解除 1 条内容关联！";
			}
			//删除所有关联内容
			if(StringUtil.isNotEmpty(simplespecialId)&&StringUtil.isEmpty(contentId)){
				simpleSpecialContentList = findByProperty(SimplespecialContentEntity.class, "simplespecialid", Integer.valueOf(simplespecialId));
				message = "成功解除 "+simpleSpecialContentList.size()+" 条内容关联！";
				for(int i=0;i<simpleSpecialContentList.size();i++){
					SimplespecialContentEntity simpleSpecialContent = simpleSpecialContentList.get(i);
					delete(simpleSpecialContent);
				}
			}
			json.accumulate("isSuccess", true);
		} catch (Exception e) {
			message = "解除内容关联失败！";
			json.accumulate("isSuccess", false);
		}
		json.accumulate("msg", message);
		json.accumulate("toUrl", "contentsMobileController.do?chooseContent&simplespecial="+simplespecialId);
		return json;
	}
	/**
	 * 取消置顶
	 * 
	 * @param contentId
	 *            内容id
	 * @return 是否操作成功
	 */
	@Override
	public Boolean cancelTop(String simplespecialId) {
		try {
			SimplespecialEntity m = getEntity(SimplespecialEntity.class, String.valueOf(simplespecialId));
			m.setIsTop(0);
			saveOrUpdate(m);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 获取当前移动内容中最大置顶值
	 * 
	 * @return
	 */
	@Override
	public Integer getCurrentMaxTop() {
		String sql = " select max(is_top) max_top from cm_simplespecial";
		List<Map<String, Object>> data = findForJdbc(sql, null);
		if (data != null && data.size() > 0) {
			if(data.get(0).get("top")!=null){
				return Integer.valueOf(data.get(0).get("max_top").toString());
			}
		}
		return 0;
	}
	/**
	 * 设置置顶内容置顶
	 * 
	 * @param contentId
	 *            内容id
	 * @param setTopValue
	 *            置顶值
	 * @return 是否操作成功
	 */
	@Override
	public Boolean setTop(String simplespecialId, Integer setTopValue) {
		try {
			SimplespecialEntity m = getEntity(SimplespecialEntity.class, String.valueOf(simplespecialId));
			m.setIsTop(setTopValue);
			saveOrUpdate(m);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}