package com.leimingtech.cms.service.impl.contribute;

import com.leimingtech.cms.service.ContributeServiceI;
import com.leimingtech.cms.service.accessory.ContentAccessoryServiceI;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.ContentClassify;
import com.leimingtech.core.entity.*;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ArticleServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.util.MapBeanUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import jodd.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 会员中心爆料
 * @author LKANG
 * 2014-08-13 10:55:00
 */
@Service("contributeServiceImpl")
@Transactional
public class ContributeServiceImpl extends CommonServiceImpl implements ContributeServiceI {
	
	@Autowired
	private ArticleServiceI articleService;       // 文章内容实现类
	@Autowired
	private ContentsServiceI contentsService;     // 文章实现类
	@Autowired
	private MemberServiceI memberMng;             // 会员实现类
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	@Autowired
	private ContentAccessoryServiceI contentAccessoryService;

	/**
	 * 保存爆料即保存文章
	 *
	 * @param map
	 *            map中包含request中所有的参数
	 */
	public void saveContribute(Map<String, String> map) {
		String siteid = PlatFormUtil.getFrontSessionSiteId();

		String id = map.get("id");
		MemberEntity member = memberMng.getSessionMember(); // 登录会员实体
		if (member == null && StringUtil.isNotEmpty(map.get("userId"))) {
			member = get(MemberEntity.class, map.get("userId"));
		}
		try {

			ContentsEntity contents = new ContentsEntity();

			String memberName=member.getName();
			if(StringUtils.isBlank(memberName)){
				memberName=member.getUsername();
			}

			ContentsEntity c = null;
			if (StringUtil.isNotEmpty(id)) {
				c = get(ContentsEntity.class, id);
				MyBeanUtils.copyBeanNotNull2Bean(contents, c);
				c.setTitle(map.get("title"));
				c.setModified(new Date());// 修改时间
				c.setModifiedby(member.getId());// 修改人
				c.setId(id);
				saveOrUpdate(c);
			} else {

				ContentCatEntity contentCat = new ContentCatEntity();
				if (StringUtil.isNotEmpty(map.get("contentCatId"))) {
					contentCat = get(ContentCatEntity.class,
							map.get("contentCatId"));
				}

				// 文章实体
				contents.setTitle(StringEscapeUtils.escapeHtml4(map.get("title")));
				//contents.setDigest(map.get("digest"));
				contents.setCatid(map.get("contentCatId"));
				contents.setSourceid("网友爆料");

				contents.setAuthor(memberName);
				contents.setModelid("1");
				contents.setClassify(ContentClassify.CONTENT_ARTICLE);
				contents.setCreated(new Date());
				contents.setMemberid(member.getId());
				contents.setCreatedby(member.getId());
				contents.setConstants("0");
				contents.setAllowcomment("false");
				contents.setIscontribute("1");
				contents.setPathids(contentCat.getPathids());
				
				if(siteid!=null){
					contents.setSiteid(siteid);
				}else{
					contents.setSiteid("1");
				}
				save(contents);
			}

			ArticleEntity article = new ArticleEntity(); // 文章内容实体

			article.setMemberid(member.getId());
			String content = map.get("content");
			
			HttpServletRequest request = ContextHolderUtils.getRequest();

			String[] accessoryIds=request.getParameterValues("accessory_id");
			String[] accessoryTitles=request.getParameterValues("accessory_title");
			String[] accessoryUrls=request.getParameterValues("accessory_url");

			Set<String> accessorySet=new HashSet<String>();
			if(accessoryTitles!=null && accessoryTitles.length>0){
				Date date=new Date();
				for (int i = 0; i < accessoryTitles.length; i++) {
					String accessoryId=accessoryIds[i];
					if(StringUtils.isBlank(accessoryId)){

						String accessoryTitle=accessoryTitles[i];
						String accessoryUrl=accessoryUrls[i];
						ContentAccessoryEntity contentAccessory = new ContentAccessoryEntity();
						contentAccessory.setAccessoryName(accessoryTitle);
						contentAccessory.setAccessoryUrl(accessoryUrl);
						contentAccessory.setContentId(String.valueOf(contents
								.getId()));
						contentAccessory.setCreatedtime(date);
						contentAccessory.setSiteId(siteid);
						save(contentAccessory);
					}else{
						accessorySet.add(accessoryId);
					}
				}
			}

			if(c!=null){
				List<ContentAccessoryEntity> accessoryList=contentAccessoryService.findByContentId(c.getId());
				if(accessoryTitles==null || accessoryTitles.length==0){
					contentAccessoryService.delete(accessoryList);
				}else{
					for (int i = 0; i < accessoryList.size(); i++) {
						ContentAccessoryEntity a=accessoryList.get(i);
						if(!accessorySet.contains(a.getId())){
							contentAccessoryService.delete(a);
						}
					}

				}


			}


			if (StringUtil.isNotEmpty(id)) {
				article.setContentid(id);
			}

			article.setContent(content );
			if (c != null) {
				articleService.saveArticle(c, article);
			} else {
				articleService.saveArticle(contents, article);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 根据文章id获取文章内容
	 * @param id
	 * @return
	 */
	public Map<String, Object> getContentById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		ContentsEntity contents = contentsService.getContensById(id);
		ArticleEntity article = articleService.getArticleById(id);
		map = MapBeanUtil.transBean2Map(contents);
		map.put("content", article.getContent());
		return map;
	}
	
	/**
	 * 根据会员id获取爆料文章列表
	 * @param pagesize
	 * @param pageno
	 * @param map
	 * @param memberid
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public PageList contentsList(Integer pageSize, Integer pageNo,
			Map map, String memberid, ContentsEntity contens) {
		PageList resultList = contentsService.contentsList(pageSize, pageNo, map, memberid, contens);
		return resultList;
	}

	@Override
	public JSONObject getContributeCat() {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try {
			CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);
			cq.eq("iscontribute", 1);
			cq.eq("isArticleSelected", "true");
			cq.addOrder("sort", SortDirection.desc);
			cq.addOrder("id", SortDirection.desc);
			cq.add();
			List<ContentCatEntity> contentCatList = getListByCriteriaQuery(cq, false);
			List<BeanListApi> beanList = new ArrayList<BeanListApi>();
			for(ContentCatEntity contentCat:contentCatList){
				BeanListApi item = new BeanListApi();
				item.setId(String.valueOf(contentCat.getId()));
				item.setTitle(contentCat.getName());
				beanList.add(item);
			}
			beanApi.setResultMsg("成功");
			beanApi.setResultCode("1");
			beanApi.setItems(beanList);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		} catch (Exception e) {
			e.printStackTrace();
			beanApi.setResultMsg("服务器繁忙，稍后重试！");
			beanApi.setResultCode("0");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}

	@Override
	public JSONObject getContentList(List<ContentsEntity> contentList) {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try {
			beanApi.setResultMsg("成功");
			beanApi.setResultCode("1");
			List<BeanListApi> beanList = new ArrayList<BeanListApi>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(ContentsEntity content:contentList){
				BeanListApi item = new BeanListApi();
				item.setId(String.valueOf(content.getId()));
				item.setTitle(content.getTitle());
				item.setPubDate(sdf.format(content.getCreated()));
				beanList.add(item);
			}
			beanApi.setItems(beanList);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		} catch (Exception e) {
			e.printStackTrace();
			beanApi.setResultMsg("服务器繁忙，稍后重试！");
			beanApi.setResultCode("0");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}


	
}
