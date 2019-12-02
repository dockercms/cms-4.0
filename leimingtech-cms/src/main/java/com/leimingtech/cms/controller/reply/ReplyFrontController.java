package com.leimingtech.cms.controller.reply;

import com.leimingtech.cms.entity.reply.ReplyEntity;
import com.leimingtech.cms.entity.reply.ReplyVoEntity;
import com.leimingtech.cms.service.reply.ReplyFrontServiceI;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.CommentaryFrontEntity;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.service.CommentaryFrontServiceI;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**   
 * @Title: Controller
 * @Description: 跟帖
 * @author 
 * @date 2017-03-27 19:11:04
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/front/replyFrontController")
public class ReplyFrontController extends BaseController {

	private String message;
	/** 跟帖接口 */
	@Autowired
	private ReplyFrontServiceI replyFrontService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;

    @Autowired
    private MemberServiceI memberMng;

    @Autowired
    private CommentaryFrontServiceI commentaryFrontServiceI;

    @Autowired
    private CommonService commonService;



	/**
	 * 跟帖列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "reply")
	public ModelAndView reply(ReplyEntity reply, HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = replyFrontService.getPageList(reply, param,
				pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "replyController.do?reply");
		return new ModelAndView("cms/reply/replyList", m);
	}

	/**
	 * 跟帖添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("reply", new ReplyEntity());
		return new ModelAndView("cms/reply/reply", m);
	}
	
	/**
	 * 跟帖更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		ReplyEntity reply = replyFrontService.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("reply", reply);
		return new ModelAndView("cms/reply/reply", m);
	}

	/**
	 * 跟帖保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ReplyEntity reply, HttpServletRequest request) {
        JSONObject j = new JSONObject();
        String con = request.getParameter("content");
        String commentid = request.getParameter("commentid");
        String memberid = request.getParameter("memberid");

        if (memberMng.getSessionMember() == null || memberMng.getSessionMember().getId()==null) {
            message ="登陆后才可以跟帖";
        } else if(con == ""){
            message="跟帖不能为空";
        }else if(memberMng.getSessionMember().getId().equals(memberid)) {
            message="自己不能对自己的评论进行跟帖";
        }else {
            message = "跟帖成功";
            CommentaryFrontEntity commentaryFrontEntity = commentaryFrontServiceI.get(CommentaryFrontEntity.class, commentid);
            commentaryFrontEntity.setReplycount(commentaryFrontEntity.getReplycount()+1);
            MemberEntity member = memberMng.getSessionMember();
            reply.setCommentId(commentid);
            reply.setReturnperson(member.getUsername());
            reply.setReplyperson(commentaryFrontEntity.getUsername());
            reply.setReplycontent(commentaryFrontEntity.getContent());
            reply.setReturncontent(con);
            reply.setReplytime(new Date());
            reply.setIscheck("0"); //未审核
            reply.setReplyPersonId(memberMng.getSessionMember().getId());
            replyFrontService.save(reply);
            j.accumulate("replyTotalCount",commentaryFrontEntity.getReplycount());
        }
        j.accumulate("isSuccess", true);
        j.accumulate("msg", message);
        String str = j.toString();
        return str;
	}

    /**
     * 回复保存
     *
     * @return
     */
    @RequestMapping(params = "saveReturn")
    @ResponseBody
    public String saveReturn(ReplyEntity reply,HttpServletRequest request) {
        JSONObject j = new JSONObject();
        String con = request.getParameter("content");
        String replyid = request.getParameter("replyid");
        ReplyEntity oldReply = commentaryFrontServiceI.get(ReplyEntity.class, replyid);
        String replyPersonId = oldReply.getReplyPersonId();
        if (memberMng.getSessionMember() == null || memberMng.getSessionMember().getId()==null) {
            message ="登陆后才可以回复";
        } else if(con == ""){
            message="回复不能为空";
        }else if(memberMng.getSessionMember().getId().equals(replyPersonId)) {
            message="自己不能对自己的跟帖进行回复";
        }else {
            message = "回复添加成功";

            oldReply.setReturncount(oldReply.getReturncount()+1);

            String commentId = oldReply.getCommentId();   //获取评论id
            CommentaryFrontEntity commentaryFrontEntity = commentaryFrontServiceI.get(CommentaryFrontEntity.class, commentId);
            commentaryFrontEntity.setReplycount(commentaryFrontEntity.getReplycount()+1);


            String replyPerson = oldReply.getReturnperson();   //获取 之前的跟帖人
            String replyContent = oldReply.getReturncontent();//获取 之前 的跟帖内容
            String oldReplyId = oldReply.getId();//获取 之前的 帖子的id
            MemberEntity member = memberMng.getSessionMember();

            reply.setCommentId(commentId);
            reply.setIscheck("0"); //未审核
            reply.setReturnperson(member.getUsername());
            reply.setReplyperson(replyPerson);
            reply.setReplycontent(replyContent);
            reply.setReplyId(oldReplyId);
            reply.setReturncontent(con);
            reply.setReplytime(new Date());
            reply.setReplyPersonId(memberMng.getSessionMember().getId());
            replyFrontService.save(reply);
        }
        j.accumulate("isSuccess", true);
        j.accumulate("msg", message);
        String str = j.toString();
        return str;
    }
	
	/**
	 * 跟帖删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		ReplyEntity reply = replyFrontService.getEntity(java.lang.String.valueOf(id));
		message = "跟帖【" + reply.getId() + "】删除成功";
        replyFrontService.delete(reply);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "replyController.do?reply");
		String str = j.toString();
		return str;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    /**
     * 评论列表展现
     *
     * @return
     */
    @RequestMapping(params = "showList")
    public ModelAndView commentaryList(HttpServletRequest request) {
        String commentid = request.getParameter("commentid");

        Map<String, Object> m = new HashMap<String, Object>();

        CommentaryFrontEntity comment = commentaryFrontServiceI.get(CommentaryFrontEntity.class,commentid);
        m.put("comment",comment);
        List<ReplyEntity> replyList = commonService.findByProperty(ReplyEntity.class, "commentId", commentid);
        m.put("replyList",replyList);
        m.put("commentid",commentid);
        return new ModelAndView("member/comment/reply", m);
    }

    /**
     * 增加赞数
     * @param replyId
     */
    @ResponseBody
    @RequestMapping(params = "addReplySupport")
    public Integer addReplySupport(String replyId) {
        return replyFrontService.addReplySupport(replyId);
    }
    /**
     * 增加贬赞数
     * @param replyId
     */
    @ResponseBody
    @RequestMapping(params = "deleteReplySupport")
    public Integer deleteReplySupport(String replyId) {
        return replyFrontService.deleteReplySupport(replyId);
    }

    /**
     * 跟帖列表分页展现
     *
     * @return
     */
    @RequestMapping(params = "replyPageList")
    public  ModelAndView replyPageList(HttpServletRequest request){
        String commentId = request.getParameter("commentId");
        String replyTime=request.getParameter("replyTime");  //跟帖时间

        int replyPageNo = StringUtils.isEmpty(request.getParameter("replyPageNo")) ? 1 : Integer.valueOf(request.getParameter("replyPageNo"));
        List<ReplyVoEntity> replyVoEntityList=new ArrayList<ReplyVoEntity>();
        if(StringUtils.isNotEmpty(replyTime)){
            replyVoEntityList=commonService.findObjForJdbc("SELECT c.id replyId,m.id memberId,c.replyContent replyContent,c.returnContent returnContent,c.replyTime replyTime,m.userName memberName,c.supportcount supportCount,c.opposecount opposeCount,c.returncount returnCount,c.replyPerson commentName," +
                            "m.faceImg memberPic from cms_reply c,cms_member m where c.replyPersonId=m.id and c.commentId=?  and c.replyTime<? and c.ischeck!='2'   ORDER BY c.replyTime desc ",
                    1,10,ReplyVoEntity.class,commentId,replyTime);
        }else{
            replyVoEntityList=commonService.findObjForJdbc("SELECT c.id replyId,m.id memberId,c.replyContent replyContent,c.returnContent returnContent,c.replyTime replyTime,m.userName memberName,c.supportcount supportCount,c.opposecount opposeCount,c.returncount returnCount,c.replyPerson commentName," +
                            "m.faceImg memberPic from cms_reply c,cms_member m where c.replyPersonId=m.id and c.commentId=? and c.ischeck!='2'  ORDER BY c.replyTime desc ",
                    1,10,ReplyVoEntity.class,commentId);
        }
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("replyVoEntityList",replyVoEntityList);
        m.put("replyVoEntityCount",replyVoEntityList.size());
        m.put("replyPageNo",replyPageNo);
        return new ModelAndView("member/comment/replyPageList", m);
    }
}
