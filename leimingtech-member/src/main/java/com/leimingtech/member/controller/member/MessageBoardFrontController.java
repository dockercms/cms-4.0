package com.leimingtech.member.controller.member;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.MessageBoardEntity;
import com.leimingtech.core.entity.MessageManagementEntity;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.MessageBoardServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.PlatFormUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: Controller
 * @Description: 留言
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-30 10:32:39
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/front/messageBoardFrontController")
public class MessageBoardFrontController extends BaseController {

//	@Autowired
//	private GuestBookFrontServiceI guestBookFrontService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private MemberServiceI memberMng;
	
	@Autowired
	private MessageBoardServiceI boardServiceI;
	@Autowired
	private CommonService commonService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	

	
	
	/**
	 * 首页留言
	 * 
	 * @return
	 */
	@RequestMapping(params = "messageBoard")
	@ResponseBody
	public String messageBoard(HttpServletRequest request) {
		
		JSONObject j = new JSONObject();
		String boardId= request.getParameter("boardId"); //留言板Id
		MessageBoardEntity board = boardServiceI.getEntity(boardId);
		
		j.accumulate("isSuccess", true);
		j.accumulate("board", board);
		String str = j.toString();
		return str;
	}

	


	/**
	 * 主页留言页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "newsGuestBook")
	public ModelAndView newsguestBook(HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 5 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		String boardId=request.getParameter("boardId");

		CriteriaQuery cq = new CriteriaQuery(MessageManagementEntity.class, pageSize, pageNo, "", "");
		cq.eq("boardId", boardId);
		cq.eq("replyStatus", "1");
		cq.addOrder("id", SortDirection.desc);
		
		//cq.eq("memberid", memberMng.getSessionMember().getId() + "");
		//cq.addOrder("id", SortDirection.desc);
		/*try {
			if (StringUtils.isNotEmpty(begintime)) {
				cq.gt("addTime", DataUtils.parseDate(begintime + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));

			}
			if (StringUtils.isNotEmpty(endtime)) {
				cq.lt("addTime", DataUtils.parseDate(endtime + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<MessageManagementEntity> guestBookList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		MessageBoardEntity board = commonService.get(MessageBoardEntity.class, boardId);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("board", board);
		m.put("guestBookList", guestBookList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("countNum", pageList.getCount());
		m.put("sitePath", memberMng.getSitePath());
		m.put("searchUrl", "messageBoardFrontController.do?newsGuestBook&boardId="+boardId);
		m.put("site",  PlatFormUtil.getFrontSessionSite());
		return new ModelAndView("member/newsguestbook", m);
	}
	/**
	 * 主页留言保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "messagesave")
	@ResponseBody
	public String messagesave(MessageManagementEntity messages ,HttpServletRequest request) {
		
		JSONObject j = new JSONObject();
		boolean bool = true;
		MemberEntity member = memberMng.getSessionMember();
		String boardId= request.getParameter("boardId"); //留言板Id
		String anonymous= request.getParameter("anonymous"); //留言板Id
		MessageBoardEntity board = boardServiceI.getEntity(boardId);
		Date sysdate =new Date();
		List<MessageManagementEntity> messageList = commonService.findByProperty(MessageManagementEntity.class, "boardId", boardId);
		//限制发布留言次数
		if(messageList!=null && messageList.size()>0 && board.getTimeLimit()!=null){
			if(messageList.size()>=Integer.parseInt(board.getNumbers()) || sysdate.getTime()>board.getTimeLimit().getTime()){
				List<TSType> timeList = TSTypegroup.allTypes.get("time_select");//时间选择
				for(TSType type :timeList){
					if(type.getTypecode().equals(board.getTimeSelect())){
						message="频率:"+board.getNumbers()+"次/"+board.getTime()+type.getTypename();
						bool=false;
					}
				}
				
			}
	        
		}
			if(bool){
				messages.setSiteId(board.getSiteId());
				messages.setAddTime(new Date());
				messages.setReplyStatus("0");
				messages.setBoardId(boardId);
				
				if(StringUtils.isNotEmpty(anonymous) && anonymous.equals("1")){
					messages.setName("XXX");
				}else{
					messages.setName(member.getUsername());
					messages.setMemberid(member.getId());
				}
				commonService.save(messages);
				message="保存成功";
				j.accumulate("isSuccess", true);
			}
		
		j.accumulate("message", message);
		String str = j.toString();
		return str;
	}
	
}
