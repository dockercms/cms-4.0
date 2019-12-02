package com.leimingtech.mobile.controller.vote;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.common.ContentMobileClassify;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.VoteEntity;
import com.leimingtech.core.entity.VoteLogDataEntity;
import com.leimingtech.core.entity.VoteMobileEntity;
import com.leimingtech.core.entity.VoteOptionEntity;
import com.leimingtech.core.service.ContentsMobileServiceI;
import com.leimingtech.core.service.IStatic;
import com.leimingtech.core.service.StaticMobileHtmlServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.VoteMobileServiceI;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.controller.contents.ContentsMobilebaseController;

/**   
 * @Title: Controller
 * @Description: 移动投票
 * @author zhangxiaoqiang
 * @date 2014年7月1日16:19:30
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/voteMobileController")
public class VoteMobileController extends ContentsMobilebaseController {
	private static final Logger logger = Logger.getLogger(VoteMobileController.class);
	@Autowired
	private VoteMobileServiceI voteService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private IStatic staticImpl;
	@Autowired
	private StaticMobileHtmlServiceI staticMobileImpl;
	@Autowired
	private ContentsMobileServiceI contentsMobileService;
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 投票添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		//频道id
		String mobileChannelId = reqeust.getParameter("mobileChannelId");
		//内容id
		String contentId = reqeust.getParameter("contentId");
		ContentsEntity contents = new ContentsEntity();
		VoteMobileEntity vote= new VoteMobileEntity();
		if(StringUtil.isNotEmpty(contentId)){
			contents = voteService.get(ContentsEntity.class, String.valueOf(contentId));
			List<VoteEntity> votePCList=voteService.findByProperty(VoteEntity.class, "contentid",  contentId );
			if(votePCList!=null&& votePCList.size()>0){
				VoteEntity pcVote=votePCList.get(0);
				vote.setVotepattern(pcVote.getVotepattern());
				vote.setVotetype(pcVote.getVotetype());
			}
		}
		MobileChannelEntity mobileChannel = voteService.get(MobileChannelEntity.class,  mobileChannelId );
		//分类
		String classify = ContentMobileClassify.CONTENT_VOTE;
		List<VoteOptionEntity> voteOptionList = new ArrayList<VoteOptionEntity>();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page",vote);
		m.put("contentsMobile", new ContentsMobileEntity());
		m.put("contents", contents);
		m.put("mobileChannel", mobileChannel);
		m.put("voteOptionList", voteOptionList);
		m.put("markpeople", markpeople());
		m.put("classify", classify);
		m.put("sessionId", reqeust.getSession().getId());
		m.put("specialids", reqeust.getParameter("specialids"));
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		return new ModelAndView("mobile/vote/vote", m);
	}

	/**
	 * 投票保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(VoteMobileEntity voteMobile,ContentsMobileEntity contentsMobile, HttpServletRequest request) {
		//频道id
		String mobileChannelId = request.getParameter("mobileChannelId");
		//获取内容id
		String contentsMobileId = request.getParameter("contentsMobileId");
		//PC投票id
		String voteId = request.getParameter("voteId");
		//选择的专题
		String specialids = request.getParameter("specialids");
		JSONObject j = new JSONObject();
		MobileChannelEntity mobileChannel = voteService.getEntity(MobileChannelEntity.class, mobileChannelId );
		if (StringUtil.isNotEmpty(voteMobile.getId())) {
			VoteMobileEntity t = voteService.get(VoteMobileEntity.class, voteMobile.getId());
			ContentsMobileEntity c = voteService.get(ContentsMobileEntity.class,  contentsMobileId );
			contentsMobile.setId(null);
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contentsMobile, c);
				MyBeanUtils.copyBeanNotNull2Bean(voteMobile, t);
				
				if(voteMobile.getMaxvotes()==null){
					t.setMaxvotes(null);
				}
				
				c.setModified(new Date());//修改时间
				c.setModifiedby(markpeople());//修改人
				voteService.saveOrUpdate(c);
//				staticImpl.staticArticleActOnPreview(c);
				voteService.saveOrUpdate(t);
				try {
					staticMobileImpl.staticMobileArticle(PlatFormUtil.getSessionSite(), c, mobileChannel);
				} catch (Exception e) {
					e.printStackTrace();
					LogUtil.error(c.getTitle()+"生成页面失败");
				}
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				//保存专题
				if(StringUtil.isNotEmpty(specialids)){
					contentsMobileService.saveOrDel(contentsMobile.getRelevanceid().toString(),specialids);
				}
				message = "投票["+c.getTitle()+"]更新成功";
			} catch (Exception e) {
				e.printStackTrace();
				message = "投票["+c.getTitle()+"]更新失败";
			}
		} else {
			message = "投票["+contentsMobile.getTitle()+"]添加成功";
			contentsMobile.setPathids(mobileChannel.getPathids());
			contentsMobile.setCatid( mobileChannelId );
			//设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
			contentsMobile.setClassify(ContentMobileClassify.CONTENT_VOTE);
			//当前人id
			String userId = "";
			if(PlatFormUtil.getSessionUser()!=null){
				userId= PlatFormUtil.getSessionUser().getId();
			}
			contentsMobile.setCreated(new Date());//创建时间
			contentsMobile.setCreatedby(markpeople());//创建人
			contentsMobile.setPublishedbyid(userId);//发布人id
			contentsMobile.setPublished(new Date());//发布时间
			SiteEntity site=PlatFormUtil.getSessionSite();
			contentsMobile.setSiteid(site.getId()+"");
			voteService.save(contentsMobile);
//			staticImpl.staticArticleActOnPreview(contents);
			
			//进入工作流，判断是否需要审核，如果需要设为待审，如果不需要审核设为已审
			enterworkflow(contentsMobile,  mobileChannelId );
			
			voteMobile.setContentid(contentsMobile.getId() );
			voteMobile.setVoteid( voteId );
			voteMobile.setSiteid(site.getId()+"");
			voteMobile.setCreatedtime(new Date());//创建时间
			voteService.save(voteMobile);
			try {
				staticMobileImpl.staticMobileArticle(PlatFormUtil.getSessionSite(), contentsMobile, mobileChannel);
			} catch (Exception e) {
				e.printStackTrace();
				LogUtil.error(contentsMobile.getTitle() + "生成页面失败");
			}
			systemService.addLog(message, Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
			//保存专题
			if(StringUtil.isNotEmpty(specialids)){
				contentsMobileService.saveOrDel(contentsMobile.getRelevanceid().toString(),specialids);
			}
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentsMobileController.do?table&tab=alone&mobileChannelId="+mobileChannelId);
		String str = j.toString();
		return str;
	}
	/**
	 * 投票结果显示
	 * @param reqeust
	 * @return
	 */
	@RequestMapping(params = "voteOptionView")
	public ModelAndView voteOptionView(HttpServletRequest reqeust){
		String voteId = reqeust.getParameter("voteId");
		List<VoteOptionEntity> voteOptionList = voteService.findByProperty(VoteOptionEntity.class, "voteid", String.valueOf(voteId));
		List<Map<String,Object>> mapList = this.getMapList(String.valueOf(voteId));
		//获取路径前缀
		String conpath = ContextHolderUtils.getRequest().getContextPath();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("voteOptionList", voteOptionList);
		m.put("mapList", mapList);
		m.put("conpath", conpath);
		return new ModelAndView("mobile/vote/voteResult", m);
	}
	/**
	 * 投票结果计算
	 * @param voteId
	 * @return
	 */
	public List<Map<String,Object>> getMapList(String voteId){
		List<VoteLogDataEntity> voteLogDataList = voteService.loadAll(VoteLogDataEntity.class);
		int num = voteLogDataList.size();
		List<VoteOptionEntity> voteOptionList = voteService.findByProperty(VoteOptionEntity.class, "voteid", voteId);
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		for(VoteOptionEntity voteOption:voteOptionList){
			List<VoteLogDataEntity> voteLogDataList1 = voteService.findByProperty(VoteLogDataEntity.class, "optionid", voteOption.getId());
			double count = voteLogDataList1.size()+.0;
			double f1 = 0;
			if(num!=0){
				double f = (count/num);
				BigDecimal bg = new BigDecimal(f);
				f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", voteOption.getId());
			map.put("count", f1);
			map.put("poll", count);
			mapList.add(map);
		}
		return mapList;
	}
}
