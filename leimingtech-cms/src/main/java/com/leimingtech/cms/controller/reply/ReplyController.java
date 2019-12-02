package com.leimingtech.cms.controller.reply;


import com.leimingtech.cms.entity.reply.ReplyEntity;
import com.leimingtech.cms.service.reply.ReplyFrontServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SystemService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 跟帖后台管理
 * Created by Administrator on 2017/4/14.
 */
@Controller
@RequestMapping("/replyController")
public class ReplyController {

    private String message;
    /** 跟帖接口 */
    @Autowired
    private ReplyFrontServiceI replyFrontService;

    @Autowired
    private MemberServiceI memberMng;

    @Autowired
    private CommonService commonService;

    @Autowired
    private SystemService systemService;

    /**
     * 跟帖展现
     *
     * @return
     */
    @RequestMapping(params = "replyList")
    public ModelAndView replyList(ReplyEntity reply,HttpServletRequest request) {
        String commentId = request.getParameter("commentId");
        // 获取评论列表
        int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
        int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

        CriteriaQuery cq = new CriteriaQuery(ReplyEntity.class, pageSize, pageNo, "", "");
        cq.eq("commentId",commentId);
        cq.addOrder("id", SortDirection.desc);
        // 查询条件组装器
        Map param = request.getParameterMap();
        HqlGenerateUtil.installHql(cq, reply, param);
        // 排序条件

        cq.addOrder("replytime", SortDirection.desc);
        cq.add();
        PageList pageList = this.commonService.getPageList(cq, true);
        List<ReplyEntity> replyList = pageList.getResultList();

        int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
        if (pageCount <= 0) {
            pageCount = 1;
        }
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("searchMap", param);
        m.put("pageNo", pageList.getCurPageNO());
        m.put("pageSize", pageSize);
        m.put("pageCount", pageCount);
        m.put("actionUrl", "replyController.do?replyList&commentId="+commentId);
        m.put("replyList",replyList);
        m.put("commentId",commentId);
        return new ModelAndView("cms/commentary/replyModel", m);
    }

    /**
     * 跟帖审核
     * @return
     */
    @RequestMapping(params = "checkReply")
    @ResponseBody
    public String checkReply(HttpServletRequest request) {
        JSONObject j = new JSONObject();
        String id = request.getParameter("id");
        String pageNo = request.getParameter("pageNo");
        String ischeck = request.getParameter("ischeck");
        ReplyEntity reply = replyFrontService.getEntity(id);
        reply.setIscheck(ischeck);
        try {
            message = "操作成功";
            replyFrontService.saveOrUpdate(reply);
            systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
            message = "操作失败";
        }

        j.accumulate("isSuccess", true);
        j.accumulate("msg", message);
//        j.accumulate("toUrl", "replyController.do?replyList&pageNo="+pageNo);
        String str = j.toString();
        return str;
    }
    /**
     * 评论删除
     *
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public String del(HttpServletRequest request) {
        JSONObject j = new JSONObject();
        String commentId = null;

        try {
            String id = request.getParameter("id");
            //删除 跟帖
            commentId = commonService.get(ReplyEntity.class, id).getCommentId();
            commonService.deleteEntityById(ReplyEntity.class,id);
            message = "跟帖删除成功";
            systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
            j.accumulate("isSuccess", true);
            //删除 评论中的 跟帖
            List<ReplyEntity> replyEntityList = commonService.findByProperty(ReplyEntity.class, "replyId", id);
            if (replyEntityList != null && replyEntityList.size() > 0) {
                commonService.deleteAllEntitie(replyEntityList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message="跟帖删除失败";
            j.accumulate("isSuccess", false);
            systemService.addLog(message+"，原因："+e.getMessage(), Globals.Log_Type_DEL, Globals.Log_Leavel_ERROR);
        }
        j.accumulate("toUrl", "replyController.do?replyList&commentId="+commentId);
        j.accumulate("msg", message);
        String str = j.toString();
        return str;
    }
}
