package com.leimingtech.cms.service.impl.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.leimingtech.core.util.oConvertUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import com.leimingtech.cms.service.tag.ICatalogTagMng;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.TagConstants;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.impl.CommonServiceImpl;

/**
 * 栏目标签管理
 * 
 * @author liuzhen 2014年4月28日 09:53:57
 * 
 */
@Service("catalogTagMng")
@Transactional
public class CatalogTagMng extends CommonServiceImpl implements ICatalogTagMng {

	/**
	 * 获取栏目（此方法由标签调用）
	 * 
	 * @param params
	 *            栏目参数
	 * @return 根据参数不同既可以获取单个栏目也可以获取栏目列表 Catalog or List<Catalog>
	 */
	@Override
	public Object getCatalogByTag(Map params) {
		if (MapUtils.isEmpty(params))
			return null;

		Boolean getSelf = false;

		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);
		String name = (String) params.get("name");
		


		String levels = (String) params.get("levels");// 栏目获取级别
		String siteid = (String) params.get("siteid");// 站点id 非必填值
		
		if (StringUtils.isNotEmpty(levels)) {
			if (levels.equalsIgnoreCase(TagConstants.LEVEL_ROOT)&&org.apache.commons.lang3.StringUtils.isNotBlank(siteid) && StringUtils.isEmpty(name)) {
				// 获取所有栏目 当name被指定则无效
				cq.eq("siteid", siteid);
				cq.eq("levels", 0);
			} else {
				if (StringUtils.isEmpty(name))
					return null;

				if (levels.equalsIgnoreCase(TagConstants.LEVEL_SELF)) {// 获取栏目本身
					if (StringUtils.isEmpty(name))
						return null;

					getSelf = true;
					
					ContentCatEntity contentcat = getEntity(
							ContentCatEntity.class, name);
					if (contentcat == null) {
						cq.eq("name", name);
					}else{
						cq.eq("id", name);
					}
					
				} else if (levels.equalsIgnoreCase(TagConstants.LEVEL_CURRENT)) {// 获取同级
					ContentCatEntity catalog = null;
					
					catalog = getEntity(ContentCatEntity.class, name);
					if (catalog == null) {
						
						catalog = findUniqueByProperty(ContentCatEntity.class,
								"name", name);
						if (catalog == null) {
							cq.eq("id", -1+"");
						}
					} 
					
					if (catalog != null) {
						if (catalog.getContentCat() == null) {
							cq.isNull("contentCat.id");
						} else {
							cq.eq("contentCat.id", catalog.getContentCat().getId());
						}
					}
				} else {// 获取子集
					
					ContentCatEntity catalog = null;
					
					catalog = getEntity(ContentCatEntity.class, name);
					if (catalog == null) {
						cq.eq("name", name);
						catalog = findUniqueByProperty(ContentCatEntity.class,
								"name", name);
						if (catalog != null) {
							cq.eq("contentCat.id", catalog.getId());
						} else {
							cq.eq("contentCat.id", -1+"");
						}
					} else {
						cq.eq("contentCat.id", name);
					}
					
				}
			}
		} else {// 获取子集
			if (StringUtils.isEmpty(name))
				return null;

			ContentCatEntity catalog = null;
			
			catalog = getEntity(ContentCatEntity.class, name);
			if (catalog == null) {
				cq.eq("name", name);
				catalog = findUniqueByProperty(ContentCatEntity.class,
						"name", name);
				if (catalog != null) {
					cq.eq("contentCat.id", catalog.getId());
				} else {
					cq.eq("contentCat.id", -1+"");
				}
			} else {
				cq.eq("contentCat.id", name);
			}
		}

		cq.eq("disabled", 0);// 栏目可用
	
		cq.addOrder("sort", SortDirection.desc);
		cq.add();
		List<ContentCatEntity> catalogList = getListByCriteriaQuery(cq, false);

		int count=oConvertUtils.getInt(params.get("count"),0);
		if (count>0 && catalogList != null) {

			if (catalogList.size() > count) {
				catalogList = catalogList.subList(0, count);
			}
		}

		//只获取当前栏目
		if(getSelf){
			
			if (catalogList != null && catalogList.size() > 0) {
				return catalogList.get(0);
			}else{
				ContentCatEntity contentCat=new ContentCatEntity();
				contentCat.setName(name);
				
				return contentCat;
			}
			
		}
		
		return catalogList;
	}

	@Override
	public Object getDefaultCat(Map params) {
		if(RequestContextHolder.getRequestAttributes()==null){
			return new ArrayList();
		}
		
		HttpSession session = ContextHolderUtils.getSession();
		if(session!=null){
			List<String> list = (List<String>) session.getAttribute("channel");
			if(list==null){
				return new ArrayList<>();
			}else{
				return list;
			}
		}else{
			return null;

		}
	}
}
