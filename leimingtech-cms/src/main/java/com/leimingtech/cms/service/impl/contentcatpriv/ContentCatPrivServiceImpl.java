package com.leimingtech.cms.service.impl.contentcatpriv;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.leimingtech.core.entity.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.contentcatpriv.ContentCatPrivServiceI;
import com.leimingtech.core.service.ContentCatServiceI;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

/**
 * PC栏目权限管理接口实现
 * 
 * @author liuzhen
 * 
 */
@Service("contentCatPrivService")
@Transactional
public class ContentCatPrivServiceImpl extends CommonServiceImpl implements ContentCatPrivServiceI {
	
	/**栏目管理接口*/
	@Autowired
	private ContentCatServiceI contentCatService;
	
	/**站点管理接口*/
	@Autowired
	private SiteServiceI siteService;

	@Override
	public JSONArray loadContentCatTree(String roleId) {
		List<ContentCatPrivEntity> contentCatPrivList = findByProperty(ContentCatPrivEntity.class, "TSRole.id", roleId);
		Set<String> set = new HashSet<String>();
		for(ContentCatPrivEntity contentCatPriv:contentCatPrivList){
			if(null!=contentCatPriv.getContentCatEntity()){
				set.add(contentCatPriv.getContentCatEntity().getId());
			}
		}
		
		List<SiteEntity> sites = siteService.siteList();
		JSONArray sitesJsonArray=new JSONArray();
		for (SiteEntity site : sites) {
			JSONObject sitejsonObject = new JSONObject();
			sitejsonObject.put("id",site.getId());
			sitejsonObject.put("name", site.getSiteName());
			sitejsonObject.put("open", true);
			sitejsonObject.put("checked", true);
			sitesJsonArray.add(sitejsonObject);
			sitesJsonArray.addAll(this.contentCatTree(set, site.getId()));
		}

		return sitesJsonArray;
	}
	
	/**
	 * 获取指定站点中的栏目
	 * 
	 * @param set
	 * @param siteid
	 * @return
	 */
	private JSONArray contentCatTree(Set<String> set, String siteid) {
		
		List<ContentCatVOTreeEntity> contentCatList=contentCatService.getSimpleAllBySite(siteid);//获取站点下所有栏目数据，返回字段比较简洁，针对ztree使用

		JSONArray jsonArray = new JSONArray();
		for (ContentCatVOTreeEntity contentCat : contentCatList) {
			JSONObject json = new JSONObject();
			json.put("id", contentCat.getId());
			json.put("name", contentCat.getName());
			json.put("open", true);

			if(StringUtils.isBlank(contentCat.getParentid())){
				json.put("pId",siteid);
			}else{
				json.put("pId",contentCat.getParentid());
			}

			if (set.contains(contentCat.getId())) {
				json.put("checked", true);
			} else {
				json.put("checked", false);
			}
			jsonArray.add(json);
		}

		return jsonArray;
	}
	@Override
	public void saveContentCatPriv(String roleId, String funVal) {
		ContentCatPrivEntity contentCatPriv = new ContentCatPrivEntity();
		String[] funValArray = funVal.split(",");
		
		TSRole role = getEntity(TSRole.class, roleId);
		List<ContentCatPrivEntity> contentCatPrivList = findByProperty(ContentCatPrivEntity.class, "TSRole.id", roleId);
		Set<String> flag = new HashSet<String>();
		//当前角色没有栏目权限
		if(contentCatPrivList.size()==0){
			for(int i=0;i<funValArray.length;i++){
				String funValId = funValArray[i];
				if(StringUtils.isEmpty(funValId)){
					continue;
				}
				contentCatPriv = new ContentCatPrivEntity();
				contentCatPriv.setTSRole(role);//添加角色Id
				ContentCatEntity contentCat = null;
				if(StringUtils.isNotEmpty(funValId)){
					contentCat = getEntity(ContentCatEntity.class, String.valueOf(funValId));
				}
				contentCatPriv.setContentCatEntity(contentCat);//添加栏目Id
				contentCatPriv.setCreatedtime(new Date());
				saveOrUpdate(contentCatPriv);
			}
		}else{
			for(int k=0;k<contentCatPrivList.size();k++){
				contentCatPriv = contentCatPrivList.get(k);
				String contentCatId="XXXXXX";
				if(contentCatPriv.getContentCatEntity()!=null){
					contentCatId = contentCatPriv.getContentCatEntity().getId();
				}
				flag.add(contentCatId);
				//去掉已勾选的项
				if(!funVal.contains(","+contentCatId+",")){
					delete(contentCatPriv);
				}
			}
			for(int i=0;i<funValArray.length;i++){
				String funValId = funValArray[i];
				if(StringUtils.isEmpty(funValId)){
					continue;
				}
				if(!flag.contains(funValId)){
					contentCatPriv = new ContentCatPrivEntity();
					contentCatPriv.setTSRole(role);//添加角色Id
					ContentCatEntity contentCat = null;
					if(StringUtils.isNotEmpty(funValId)){
						contentCat = getEntity(ContentCatEntity.class, String.valueOf(funValId));
					}
					if(contentCat!=null){
						contentCatPriv.setContentCatEntity(contentCat);//添加栏目Id
						contentCatPriv.setCreatedtime(new Date());
						saveOrUpdate(contentCatPriv);
					}
				}
			}
		}
		
	}
}