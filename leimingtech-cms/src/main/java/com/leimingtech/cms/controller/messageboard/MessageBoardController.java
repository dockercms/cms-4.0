package com.leimingtech.cms.controller.messageboard;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.MessageBoardEntity;
import com.leimingtech.core.entity.MessageManagementEntity;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.service.MessageBoardServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PinyinUtil;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 留言板
 * @author 
 * @date 2016-04-26 15:41:54
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/messageBoardController")
public class MessageBoardController extends BaseController {

	private String message;
	@Autowired
	private MessageBoardServiceI messageBoardService;
	@Autowired
	private SystemService systemService;
	
	/**
	 * 留言板列表页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "messageBoard")
	public ModelAndView messageBoard(MessageBoardEntity messageBoard,HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = messageBoardService.getPageList(messageBoard, param,
				pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "messageBoardController.do?messageBoard");
		return new ModelAndView("cms/messageboard/messageBoardList",m);
	}

	/**
	 * 留言板添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		MessageBoardEntity messageBoard = null;
		if (StringUtil.isNotEmpty(id)) {
			messageBoard = messageBoardService.getEntity(java.lang.String.valueOf(id));
		} else {
			messageBoard = new MessageBoardEntity();
		}
		List<TSType> timeList = TSTypegroup.allTypes.get("time_select");//时间选择
		m.put("messageBoard", messageBoard);
		m.put("timeList", timeList);
		return new ModelAndView("cms/messageboard/messageBoard", m);
	}
	
	/**
	 * 留言板修改
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		MessageBoardEntity messageBoard = null;
		if (StringUtil.isNotEmpty(id)) {
			messageBoard = messageBoardService.getEntity(java.lang.String.valueOf(id));
		} else {
			messageBoard = new MessageBoardEntity();
		}
		List<TSType> timeList = TSTypegroup.allTypes.get("time_select");//时间选择
		m.put("messageBoard", messageBoard);
		m.put("timeList", timeList);
		return new ModelAndView("cms/messageboard/messageBoard", m);
	}
	
	/**
	 * 留言板保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(MessageBoardEntity messageBoard, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(messageBoard.getId())) {
			message = "留言板更新成功";
			MessageBoardEntity t = messageBoardService.getEntity(messageBoard.getId());
			try {
				
				MyBeanUtils.copyBeanNotNull2Bean(messageBoard, t);
				if(!messageBoard.getNumbers().equals("0") && !messageBoard.getTime().equals("0")){
					Calendar rightNow = Calendar.getInstance();
				     rightNow.setTime(new Date());
					if(messageBoard.getTimeSelect().equals("y")){
						rightNow.add(Calendar.YEAR,Integer.parseInt(messageBoard.getTime()));//日期加年
					}else if(messageBoard.getTimeSelect().equals("M")){
						rightNow.add(Calendar.MONTH,Integer.parseInt(messageBoard.getTime()));//日期加月
					}else if(messageBoard.getTimeSelect().equals("d")){
						rightNow.add(Calendar.DATE,Integer.parseInt(messageBoard.getTime()));//日期加日
					}else if(messageBoard.getTimeSelect().equals("H")){
						rightNow.add(Calendar.HOUR,Integer.parseInt(messageBoard.getTime()));//日期加时
					}else if(messageBoard.getTimeSelect().equals("m")){
						rightNow.add(Calendar.MINUTE,Integer.parseInt(messageBoard.getTime()));//日期加分
					}else if(messageBoard.getTimeSelect().equals("s")){
						rightNow.add(Calendar.SECOND,Integer.parseInt(messageBoard.getTime()));//日期加秒
					}
					t.setTimeLimit(rightNow.getTime());
				}else{
					t.setTimeLimit(null);
				}
				
				messageBoardService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "留言板更新失败";
			}
		} else {
			message = "留言板添加成功";
			
			messageBoard.setCreatetime(new Date());
			if(!messageBoard.getNumbers().equals("0") && !messageBoard.getTime().equals("0")){
				Calendar rightNow = Calendar.getInstance();
			     rightNow.setTime(new Date());
				if(messageBoard.getTimeSelect().equals("y")){
					rightNow.add(Calendar.YEAR,Integer.parseInt(messageBoard.getTime()));//日期加年
				}else if(messageBoard.getTimeSelect().equals("M")){
					rightNow.add(Calendar.MONTH,Integer.parseInt(messageBoard.getTime()));//日期加月
				}else if(messageBoard.getTimeSelect().equals("d")){
					rightNow.add(Calendar.DATE,Integer.parseInt(messageBoard.getTime()));//日期加日
				}else if(messageBoard.getTimeSelect().equals("H")){
					rightNow.add(Calendar.HOUR,Integer.parseInt(messageBoard.getTime()));//日期加时
				}else if(messageBoard.getTimeSelect().equals("m")){
					rightNow.add(Calendar.MINUTE,Integer.parseInt(messageBoard.getTime()));//日期加分
				}else if(messageBoard.getTimeSelect().equals("s")){
					rightNow.add(Calendar.SECOND,Integer.parseInt(messageBoard.getTime()));//日期加秒
				}
				messageBoard.setTimeLimit(rightNow.getTime());
			}else{
				messageBoard.setTimeLimit(null);
			}
			
			messageBoard.setSiteId(PlatFormUtil.getSessionSiteId());
			messageBoardService.save(messageBoard);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "messageBoardController.do?messageBoard");
		String str = j.toString();
		return str;
	}

	/**
	 * 留言板删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(MessageBoardEntity messageBoard,HttpServletRequest request) {
		JSONObject j = new JSONObject();
		messageBoard = messageBoardService.getEntity(messageBoard.getId());
		message = "留言板删除成功";
		messageBoardService.delete(messageBoard);
		systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "messageBoardController.do?messageBoard");
		String str = j.toString();
		return str;
	}
	/**
	 * 留言管理列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "messageManagement")
	public ModelAndView messageManagement(MessageManagementEntity messageManagement,HttpServletRequest request) {
		String boardId = request.getParameter("boardId");
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = messageBoardService.getListByPid(messageManagement, param,
				pageSize, pageNo,java.lang.String.valueOf(boardId));
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("boardId", boardId);
		m.put("actionUrl", "messageBoardController.do?messageManagement");
		return new ModelAndView("cms/messagemanagement/messageManagementList",m);
	}
	
	/**
	 * 留言管理添加
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addMessageManagement")
	public ModelAndView addMessageManagement(HttpServletRequest request) {
		String id = request.getParameter("id");
		String boardId = request.getParameter("boardId");
		Map<String, Object> m = new HashMap<String, Object>();
		MessageManagementEntity messageManagement = null;
		if (StringUtil.isNotEmpty(id)) {
			messageManagement = messageBoardService.getMessageManagementEntity(java.lang.String.valueOf(id));
		} else {
			messageManagement = new MessageManagementEntity();
		}
		request.setAttribute("boardId", boardId);
		m.put("messageManagement", messageManagement);
		return new ModelAndView("cms/messagemanagement/messageManagement", m);
	}
	
	/**
	 * 留言管理修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "editMessageManagement")
	public ModelAndView ditMessageManagement(HttpServletRequest request) {
		String id = request.getParameter("id");
		String boardId = request.getParameter("boardId");
		Map<String, Object> m = new HashMap<String, Object>();
		MessageManagementEntity messageManagement = null;
		if (StringUtil.isNotEmpty(id)) {
			messageManagement = messageBoardService.getMessageManagementEntity(java.lang.String.valueOf(id));
		} else {
			messageManagement = new MessageManagementEntity();
		}
		request.setAttribute("boardId", boardId);
		m.put("messageManagement", messageManagement);
		return new ModelAndView("cms/messagemanagement/messageManagement", m);
	}
	
	/**
	 * 留言管理保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveMessageManagement")
	@ResponseBody
	public String saveMessageManagement(MessageManagementEntity messageManagement, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		
		if (StringUtil.isNotEmpty(messageManagement.getId())) {
			message = "留言管理【"+messageManagement.getId()+"】更新成功";
			MessageManagementEntity t = messageBoardService.getMessageManagementEntity(messageManagement.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(messageManagement, t);
				t.setReplyTime(new Date());
				t.setReplyStatus("1");
				t.setReplyUser(PlatFormUtil.getSessionUser().getUserName());
				messageBoardService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "留言管理【"+messageManagement.getId()+"】更新失败";
			}
		} else {
			message = "留言管理【"+messageManagement.getId()+"】添加成功";
			
			messageBoardService.save(messageManagement);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "messageBoardController.do?messageManagement&boardId="+messageManagement.getBoardId());
		String str = j.toString();
		return str;
	}

	/**
	 * 留言管理删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "delMessageManagement")
	@ResponseBody
	public String delSub(MessageManagementEntity messageManagement,HttpServletRequest request) {
		JSONObject j = new JSONObject();
		messageManagement = messageBoardService.getMessageManagementEntity(messageManagement.getId());
		message = "留言管理【"+messageManagement.getId()+"】删除成功";
		messageBoardService.delete(messageManagement);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "messageBoardController.do?messageManagement&boardId="+messageManagement.getBoardId());
		String str = j.toString();
		return str;
	}
	
	/**
	 * 审核通过
	 * @return
	 */
	@RequestMapping(params = "checkMessages")
	@ResponseBody
	public String checkMessages(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		String ischeck = request.getParameter("ischeck");
		MessageManagementEntity messages = messageBoardService.getMessageManagementEntity(String.valueOf(id));
		messages.setIscheck(ischeck);
		try {
			message = "操作审核["+messages.getContent()+"]成功";
			messageBoardService.saveOrUpdate(messages);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "操作审核["+messages.getContent()+"]失败";
		}
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "messageBoardController.do?messageManagement&boardId="+messages.getBoardId());
		String str = j.toString();
		return str;
	}
	
	
	/**
	 * 将站点名称转化为简拼
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "pyChange")
	@ResponseBody
	public String pyChange(HttpServletRequest request) {
		String nameBoard = request.getParameter("nameBoard");
		try {
			nameBoard = new String(java.net.URLDecoder.decode(nameBoard, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		nameBoard = PinyinUtil.getPinYinHeadChar(nameBoard);// 首字母
		
		
		JSONObject j = new JSONObject();
		j.accumulate("nameBoard", nameBoard);
		return j.toString();
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
