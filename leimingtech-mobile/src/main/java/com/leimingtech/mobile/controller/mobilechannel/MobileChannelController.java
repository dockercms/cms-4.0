package com.leimingtech.mobile.controller.mobilechannel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 频道管理
 * @author
 * @date 2014-06-25 10:16:44
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/mobileChannelController")
public class MobileChannelController extends BaseController {
	
	/**新闻*/
	public static final String CHANNEL_NEWS = "news";
	/**图片*/
	public static final String CHANNEL_PIC = "pic";
	/**视频*/
	public static final String CHANNEL_VIDEO = "video";
	/**跟帖*/
	public static final String CHANNEL_INVI = "invi";
	private String message = null;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	
	/**
	 * 列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "mobileChannel")
	public ModelAndView mobileChannel(HttpServletRequest request) {
		String id = request.getParameter("id");
		MobileChannelEntity mobile = null;
		if (StringUtils.isNotEmpty(id) && !"-1".equals(id)) {
			mobile = mobileChannelService.getEntity(id);
		} else {
			mobile = new MobileChannelEntity();
		}

		Map<String, Object> m = new HashMap<String, Object>();

		m.put("jstreeData", mobileChannelService.getTreeJson(id).toString());
		m.put("mobileChannel", mobile);
		m.put("selectId", id);
		return new ModelAndView("mobile/mobilechannel/mobileChannelList", m);
	}

	/**
	 * 修改
	 * @param test
	 * @param request
	 * @return
	 */
	
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest request){
		String id = request.getParameter("id");
		String selectId = request.getParameter("selectId");
		
		MobileChannelEntity mobileChannel = null;
		if (StringUtil.isNotEmpty(id)) {
			mobileChannel = mobileChannelService.getEntity(id);
		} else {
			mobileChannel = new MobileChannelEntity();
			if (StringUtil.isNotEmpty(selectId) && !"-1".equals(selectId)) {
				MobileChannelEntity parent = mobileChannelService
						.getEntity(selectId);
				mobileChannel.setMobileChannel(parent);
				mobileChannel.setChannelType(parent.getChannelType());
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mobileChannel", mobileChannel);
		m.put("selectId", selectId);
		return new ModelAndView("mobile/mobilechannel/mobileChanneleditModel", m);
	}
	
	@RequestMapping(params = "update")
	@ResponseBody
	public String update(MobileChannelEntity mobileChannel, HttpServletRequest request) {
		String id = request.getParameter("id");
		String channelTop = request.getParameter("channelTop");
		
		JSONObject j = new JSONObject();
		if(mobileChannel.getMobileChannel() != null && mobileChannel.getMobileChannel().getId()==null){
			mobileChannel.setMobileChannel(null);
		}
		String logInfo = "移动端频道【" + mobileChannel.getName() + "】";
		if (StringUtil.isNotEmpty(mobileChannel.getId())) {
			message = "更新成功";
			MobileChannelEntity t = mobileChannelService.getEntity(mobileChannel.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(mobileChannel, t);
				mobileChannel.setModified(new Date());
				// 头条只能为一条
				if (mobileChannel.getMobileChannel() != null) {
					if (StringUtils.isNotEmpty(channelTop)&& 1 == Integer.parseInt(channelTop)
							&& t.getMobileChannel().getId() != null) {
						mobileChannelService.updateToNotTop(id, t.getMobileChannel().getId());
					}else{
						boolean isOneTop = mobileChannelService.isOneTop(t.getMobileChannel().getId());// 同级栏目中是头条的是否只有一条
						if(!isOneTop){
							//当没有一个头条频道时，当前修改后还将默认为头条频道
							t.setChannelTop(1);
						}
					}
				}
				mobileChannelService.saveOrUpdate(t);
				
			} catch (Exception e) {
				e.printStackTrace();
				message = "更新失败";
			}
			systemService.addLog(logInfo + message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_UPDATE);
			j.accumulate("toUrl",
					"mobileChannelController.do?mobileChannel&id=" + t.getId());
		} else {
			message = "添加成功";
			MobileChannelEntity pm = mobileChannel.getMobileChannel();
			if(mobileChannel.getMobileChannel() == null || StringUtils.isEmpty(mobileChannel.getMobileChannel().getId())){
				mobileChannel.setLevels(0);
				mobileChannel.setParentids("0,");
				mobileChannel.setPathids("0,");
			}else{
				
				pm = mobileChannelService.getEntity(pm.getId());
				mobileChannel
						.setParentids(pm.getParentids() + pm.getId() + ",");
				mobileChannel.setPathids(pm.getParentids() + pm.getId() + ",");
				mobileChannel.setLevels(1);
				mobileChannel.setChannelType(pm.getChannelType());
				
				if(pm.getId() != null){
					if (StringUtils.isNotEmpty(channelTop) && 1 == Integer.parseInt(channelTop)) {
						mobileChannelService.updateToNotTop(id,pm.getId());//将指定的频道更改为非头条
					}else{
						boolean isOneTop = mobileChannelService.isOneTop(pm.getId());// 同级栏目中是头条的是否只有一条
						if(!isOneTop){
							//当没有一个头条频道时，当前将默认为头条频道
							mobileChannel.setChannelTop(1);
						}
					}
				}
			}
			
			mobileChannel.setCreated(new Date());
			mobileChannel.setSiteid(PlatFormUtil.getSessionSiteId());
			
			mobileChannelService.save(mobileChannel);
			 
			mobileChannel.setPathids(mobileChannel.getPathids() + mobileChannel.getId());
			mobileChannelService.saveOrUpdate(mobileChannel);
			
			systemService.addLog(logInfo+message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
			message += "<br/><span style='color:red'>注意：新增频道如需在移动内容中查看以及操作，需要在角色管理中配置权限后才能看到<span>";
			j.accumulate("toUrl", "mobileChannelController.do?mobileChannel&id="+mobileChannel.getId());
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", logInfo + message);
	
		String str = j.toString();
		return str;
	}
	
	/**
	 * 加载下级
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(HttpServletRequest request) {
		String id = request.getParameter("id");
		MobileChannelEntity parent = null;
		if(StringUtils.isEmpty(id)|| "-1".equals(id)){
			//点击顶级节点
			parent = new MobileChannelEntity ();
			parent.setId(null);
			parent.setName("顶级节点");
		}else{
			parent =mobileChannelService.getEntity(id);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mobileChannel", parent);
		m.put("selectId", id);
		//三级级联节点第一级
		return new ModelAndView("mobile/mobilechannel/mobileChannel", m);
	}
	

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		MobileChannelEntity mobileChannel = mobileChannelService.getEntity(id);
		String logInfo = "移动端频道【" + mobileChannel.getName() + "】";
		try {
			if(mobileChannel.getMobileChannel()!=null && StringUtils.isNotEmpty(mobileChannel.getMobileChannel().getId())){
				j.accumulate("toUrl", "mobileChannelController.do?mobileChannel&id=" + mobileChannel.getMobileChannel().getId());
			}else{
				j.accumulate("toUrl", "mobileChannelController.do?mobileChannel");
			}
			
			message = "删除成功";
			mobileChannelService.delete(mobileChannel);
		} catch (Exception e) {
			message = "删除失败";
		}
		systemService.addLog(logInfo+message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
	
}
