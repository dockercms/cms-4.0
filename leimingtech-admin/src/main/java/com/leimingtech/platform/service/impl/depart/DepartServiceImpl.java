package com.leimingtech.platform.service.impl.depart;

import java.util.*;

import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.service.depart.DepartServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.ContentCatDefault;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.MemberDepart;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TSDepart;
import com.leimingtech.core.entity.TSDepartChannel;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.ContentCatServiceI;
import com.leimingtech.core.service.MemberDepartServiceI;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.service.departchannel.TSDepartChannelServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

@Service("departService")
@Transactional
public class DepartServiceImpl extends CommonServiceImpl implements DepartServiceI {

	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	/**栏目管理接口*/
	@Autowired
	private ContentCatServiceI contentCatService;
	/**站点管理接口*/
	@Autowired
	private SiteServiceI siteService;
	/**会员与部门关联接口*/
	@Autowired
	private MemberDepartServiceI memberDepartService;
	/**部门下关联栏目接口*/
	@Autowired
	private TSDepartChannelServiceI tsDepartChannelService;
	
	@Override
	public List<TSDepart> getListByPid(String id) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
		cq.eq("TSPDepart.id",id);
		cq.add();
		return getListByCriteriaQuery(cq, false);
	}
	/**
	 * 获取tree节点数据
	 * 
	 * @param json
	 *            最终返回的json
	 * @param list
	 * @param openNodes
	 *            打开的节点（多个节点用逗号分隔）
	 * @param selectNode
	 *            选中的节点
	 * @return json
	 */
	private JSONArray getChildTreeJson(JSONArray json, List<TSDepart> list,
			String openNodes, String selectNode) {
		for (TSDepart depart : list) {

			JSONObject jChildObject = new JSONObject();

			jChildObject.accumulate("id", depart.getId());
			jChildObject.accumulate("text", depart.getDepartname());

			JSONObject stateJson = new JSONObject();
			if (StringUtils.isNotEmpty(selectNode)
					&& selectNode.equals(depart.getId() + "")) {
				stateJson.accumulate("selected", true);
			}
			List<TSDepart> childlist = getListByPid(depart.getId());

			if (childlist != null && childlist.size() > 0) {

				if (StringUtils.isNotEmpty(openNodes)
						&& openNodes.contains(depart.getId() + "")) {
					stateJson.accumulate("opened", true);
				}
				jChildObject.accumulate("state", stateJson);

				JSONArray jChildArray = new JSONArray();
				getChildTreeJson(jChildArray, childlist, openNodes, selectNode);
				jChildObject.accumulate("children", jChildArray);
			} else {
				jChildObject.accumulate("state", stateJson);
			}

			json.add(jChildObject);
		}
		return json;
	}

	/**
	 * 获取tree节点数据
	 * 
	 * @param id
	 *            选中的节点
	 * @return
	 */
	@Override
	public JSONArray getTreeJson(String id) {

		JSONArray json = new JSONArray();

		JSONObject jstreeData = new JSONObject();
		jstreeData.accumulate("id", "-1");
		jstreeData.accumulate("text", "组织结构");
		JSONObject stateJson = new JSONObject();
		stateJson.accumulate("opened", true);

		TSDepart depart = new TSDepart();
		if (StringUtils.isNotEmpty(id)) {
			depart = getEntity(TSDepart.class, String.valueOf(id));
		} else {
			stateJson.accumulate("selected", true);
		}
		jstreeData.accumulate("state", stateJson);

		List<TSDepart> list = getAllRoot();
		if (list != null && list.size() > 0) {
			JSONArray jChildArray = new JSONArray();
			getChildTreeJson(jChildArray, list, depart.getId(), id);
			jstreeData.accumulate("children", jChildArray);
		}

		json.add(jstreeData);

		return json;
	}

	@Override
	public void saveDepartMember(String departId, String[] memberIds) {

		Set<String> newVal = new HashSet<String>();
		CollectionUtils.addAll(newVal, memberIds);

		TSDepart depart = commonService.getEntity(TSDepart.class,departId);
		List<MemberDepart> memberDepartList = commonService.findByProperty(MemberDepart.class, "depart.id",departId );
		Set<String> flag = new HashSet<String>();
		// 当前角色没有站点权限
		if (memberDepartList.size() == 0) {
			for (int i = 0; i < memberIds.length; i++) {
				String funValId = memberIds[i];
				if (StringUtils.isBlank(funValId)) {
					continue;
				}
				MemberDepart memberDepart = new MemberDepart();

				memberDepart.setDepart(depart);
				MemberEntity member = null;
				if (StringUtils.isNotEmpty(funValId)) {
					member = getEntity(MemberEntity.class,funValId);
				}
				memberDepart.setMember(member);

				save(memberDepart);
			}
		} else {
			for (int k = 0; k < memberDepartList.size(); k++) {
				MemberDepart memberDepart = memberDepartList.get(k);
				String memberId = memberDepart.getMember().getId();
				flag.add(memberId);
//				// 去掉未勾选的项
//				if (!newVal.contains(memberId)) {
//					delete(memberDepart);
//				}
			}
			for (int i = 0; i < memberIds.length; i++) {
				String funValId = memberIds[i];
				if (StringUtils.isBlank(funValId)) {
					continue;
				}
				if (!flag.contains(funValId)) {
					MemberDepart memberDepart = new MemberDepart();
					memberDepart.setDepart(depart);
					MemberEntity member = null;
					if (StringUtils.isNotEmpty(funValId)) {
						member = getEntity(MemberEntity.class,funValId);
					}
					memberDepart.setMember(member);
					save(memberDepart);
				}
			}
		}
	}
	@Override
	public List<TSDepart> getAllRoot() {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
		cq.isNull("TSPDepart");
		cq.add();
		return  getListByCriteriaQuery(cq, false);
	}
	@Override
	public JSONArray loadChannelTree(String departId) {
		List<TSDepartChannel> contentCatPrivList = findByProperty(TSDepartChannel.class, "depart.id", departId);
		Set<String> set = new HashSet<String>();
		for(TSDepartChannel departChannel:contentCatPrivList){
			if(null!=departChannel.getChannel()){
				set.add(String.valueOf(departChannel.getChannel().getId()));
			}
		}
		List<SiteEntity> sites = siteService.siteList();
		JSONArray sitesJsonArray=new JSONArray();
		for (SiteEntity site : sites) {
			JSONObject sitejsonObject = new JSONObject();
			sitejsonObject.put("id","");
			sitejsonObject.put("name", site.getSiteName());
			sitejsonObject.put("open", true);
			sitejsonObject.put("children",
					this.contentCatTree(set, site.getId()));
			sitejsonObject.put("checked", true);
			sitesJsonArray.add(sitejsonObject);
		}

		return sitesJsonArray;
	}

	public JSONArray contentCatTree(Set<String> set, String siteid) {
		List<ContentCatEntity> contentCatList = contentCatService
				.getRootContentCatBySite(siteid);// 根据站点获取一级栏目列表
		JSONArray jsonArray = new JSONArray();
		for(ContentCatEntity contentCat:contentCatList){
			JSONObject json = new JSONObject();
			//父节点
			if(contentCat.getLevels()==0){
				json.put("id",contentCat.getId());
				json.put("name", contentCat.getName());
				json.put("open", true);
				json.put("children",this.getChildren(set,contentCat));
				if(set.contains(String.valueOf(contentCat.getId()))){
					json.put("checked", true);
				}else{
					json.put("checked", false);
				}
				jsonArray.add(json);
			}
		}
		return jsonArray;
	}
	public JSONArray getChildren(Set<String> set,ContentCatEntity contentCat){
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		if(contentCat.getContentCats()==null||contentCat.getContentCats().size()==0){
			return jsonArray;
		}else{
			for(ContentCatEntity contentCat1:contentCat.getContentCats()){
				json.put("id",contentCat1.getId());
				json.put("name", contentCat1.getName());
				json.put("open", true);
				json.put("children",this.getChildren(set,contentCat1));
				if(set.contains(String.valueOf(contentCat1.getId()))){
					json.put("checked", true);
				}else{
					json.put("checked", false);
				}
				jsonArray.add(json);
			}
		}
		return jsonArray;
	}
	@Override
	public void saveDepartChannel(String departId, String funVal) {
		TSDepartChannel departChannel;
		TSDepart depart = getEntity(TSDepart.class, departId);
		List<TSDepartChannel> contentCatPrivList = findByProperty(TSDepartChannel.class, "depart.id", departId);
		String[] funValArray = funVal.split(",");
		Set<String> flag = new HashSet<String>();

		//当前角色没有栏目权限
		if(contentCatPrivList.size()==0){
			for(int i=0;i<funValArray.length;i++){
				String funValId = funValArray[i];
				if(StringUtils.isEmpty(funValId)){
					continue;
				}
				departChannel = new TSDepartChannel();
				departChannel.setDepart(depart);
				ContentCatEntity contentCat = null;
				if(StringUtils.isNotEmpty(funValId)){
					contentCat = getEntity(ContentCatEntity.class, String.valueOf(funValId));
				}
				departChannel.setChannel(contentCat);
				saveOrUpdate(departChannel);
			}
		}else{
			Set<String> funValSet = new HashSet<String>();
			for(int i=0;i<funValArray.length;i++){
				funValSet.add(funValArray[i]);
			}

			for(int k=0;k<contentCatPrivList.size();k++){
				departChannel = contentCatPrivList.get(k);
				String contentCatId = departChannel.getChannel().getId();
				flag.add(contentCatId);
				//去掉已勾选的项
				if(!funValSet.contains(contentCatId)){
					delete(departChannel);
				}
			}
			for(int i=0;i<funValArray.length;i++){
				String funValId = funValArray[i];
				if(StringUtils.isEmpty(funValId)){
					continue;
				}
				if(!flag.contains(funValId)){
					departChannel = new TSDepartChannel();
					departChannel.setDepart(depart);
					ContentCatEntity contentCat = null;
					if(StringUtils.isNotEmpty(funValId)){
						contentCat = getEntity(ContentCatEntity.class, String.valueOf(funValId));
					}
					departChannel.setChannel(contentCat);
					saveOrUpdate(departChannel);
				}
			}
		}
	}

	@Override
	public void saveDefaultChannel(String funval) {
		ContentCatDefault catDefault;
		String[] funValArray = funval.split(",");
		Set<String> flag = new HashSet<String>();
		List<ContentCatDefault> list =getList(ContentCatDefault.class);
		if(list.size()==0){
			for(int i=0;i<funValArray.length;i++) {
				String funValId = funValArray[i];
				if (StringUtils.isEmpty(funValId)) {
					continue;
				}
				catDefault = new ContentCatDefault();
				catDefault.setChannelId(funValId);
				saveOrUpdate(catDefault);
			}
		}else{
			Set<String> funValSet = new HashSet<String>();
			for(int i=0;i<funValArray.length;i++){
				funValSet.add(funValArray[i]);
			}
			for(int k=0;k<list.size();k++){
				catDefault = list.get(k);
				String contentCatId = catDefault.getChannelId();
				flag.add(contentCatId);
				//去掉已勾选的项
				if(!funValSet.contains(contentCatId)){
					delete(catDefault);
				}
			}
			for(int i=0;i<funValArray.length;i++){
				String funValId = funValArray[i];
				if(StringUtils.isEmpty(funValId)){
					continue;
				}
				if(!flag.contains(funValId)){
					catDefault = new ContentCatDefault();
					catDefault.setChannelId(funValId);
					saveOrUpdate(catDefault);
				}
			}
		}
	}

	@Override
	public void saveDepartChannelSpread(String departId, String funval) {
		TSDepartChannel departChannel;
		TSDepart depart = getEntity(TSDepart.class, departId);
		List<TSDepartChannel> contentCatPrivList = findByProperty(TSDepartChannel.class, "depart.id", departId);
		if(contentCatPrivList.size()!=0){
			for(TSDepartChannel tsDepartChannel: contentCatPrivList){
				funval += tsDepartChannel.getChannel().getId()+",";
			}
		}
		String[] funValArray = funval.split(",");
		Set<String> flag = new HashSet<String>();

		//当前角色没有栏目权限
		if(contentCatPrivList.size()==0){
			for(int i=0;i<funValArray.length;i++){
				String funValId = funValArray[i];
				if(StringUtils.isEmpty(funValId)){
					continue;
				}
				departChannel = new TSDepartChannel();
				departChannel.setDepart(depart);
				ContentCatEntity contentCat = null;
				if(StringUtils.isNotEmpty(funValId)){
					contentCat = getEntity(ContentCatEntity.class, String.valueOf(funValId));
				}
				departChannel.setChannel(contentCat);
				saveOrUpdate(departChannel);
			}
		}else{
			Set<String> funValSet = new HashSet<String>();
			for(int i=0;i<funValArray.length;i++){
				funValSet.add(funValArray[i]);
			}

			for(int k=0;k<contentCatPrivList.size();k++){
				departChannel = contentCatPrivList.get(k);
				String contentCatId = departChannel.getChannel().getId();
				flag.add(contentCatId);
				//去掉已勾选的项
				if(!funValSet.contains(contentCatId)){
					delete(departChannel);
				}
			}
			for(int i=0;i<funValArray.length;i++){
				String funValId = funValArray[i];
				if(StringUtils.isEmpty(funValId)){
					continue;
				}
				if(!flag.contains(funValId)){
					departChannel = new TSDepartChannel();
					departChannel.setDepart(depart);
					ContentCatEntity contentCat = null;
					if(StringUtils.isNotEmpty(funValId)){
						contentCat = getEntity(ContentCatEntity.class, String.valueOf(funValId));
					}
					departChannel.setChannel(contentCat);
					saveOrUpdate(departChannel);
				}
			}
		}
	}
	
	@Override
	public void delete(TSDepart tsDepart) {
		
		memberDepartService.deleteByDepart(tsDepart.getId());//根据部门删除会员跟部门间的关联
		
		tsDepartChannelService.deleteByDepart(tsDepart.getId());//根据部门删除栏目跟部门间的关联
		
		commonService.delete(tsDepart);
	}
	
	/**
	 * 加载部门树
	 * @param checkeds 选中的部门
	 * @return
	 */
	public JSONArray ztreeJson(Set<String> checkeds) {
		
		if (checkeds == null) {
			checkeds = new HashSet<String>();
		}
		
		JSONArray jsonArray = new JSONArray();

		List<TSDepart> departList = loadAll(TSDepart.class);
		for (TSDepart depart : departList) {
			JSONObject json = new JSONObject();
			if (null == depart.getTSPDepart()) {
				json.put("id", depart.getId());
				json.put("name", depart.getDepartname());
				json.put("open", true);
				json.put("children", getDepartChildren(depart, checkeds));
				if (checkeds.contains(depart.getId())) {
					json.put("checked", true);
				}
				jsonArray.add(json);
			}
		}
		return jsonArray;
	}

	public JSONArray getDepartChildren(TSDepart depart, Set<String> checkeds) {
		JSONArray jsonArray = new JSONArray();
		if (null == depart.getTSDeparts() || depart.getTSDeparts().size() == 0) {
			return jsonArray;
		}
		for (TSDepart depart1 : depart.getTSDeparts()) {
			JSONObject json = new JSONObject();
			json.put("id", depart1.getId());
			json.put("name", depart1.getDepartname());
			json.put("open", true);
			json.put("children", getDepartChildren(depart1, checkeds));
			if (checkeds.contains(depart1.getId())) {
				json.put("checked", true);
			}
			jsonArray.add(json);
		}
		return jsonArray;
	}
	
	/**
	 * 获取所有部门信息转为map，key为id，value为部门名称
	 * @return
	 */
	@Override
	public Map<String, String> getAllToMap() {
		String sql = "select id,departname from cms_depart";
		Map<String, String> m = new HashMap<String, String>();
		List<Map<String, Object>> dataList = commonService.findForJdbc(sql);
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> data = dataList.get(i);
			m.put(data.get("id").toString(), data.get("departname").toString());
		}
		return m;
	}
}
