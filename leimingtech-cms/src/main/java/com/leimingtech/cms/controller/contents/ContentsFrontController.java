package com.leimingtech.cms.controller.contents;

import com.leimingtech.cms.service.LuceneServiceI;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.SiteServiceI;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/3/9.
 */
@Controller
@RequestMapping("/front/contentsFrontController")
public class ContentsFrontController {

    @Autowired
    private ContentsServiceI contentsService;
    @Autowired
    private LuceneServiceI luceneServiceI;
    @Autowired
    private SiteServiceI siteService;// 站点管理


    /**
     * 增加赞数
     * @param contentId
     */
    @ResponseBody
    @RequestMapping(params = "addSupport")
    public Integer addSupport(String  contentId) {
        return contentsService.addSupport(contentId);
    }

    /**
     * 增加反对数
     * @param contentId
     */
    @ResponseBody
    @RequestMapping(params = "addOppose")
    public Integer addOppose(String  contentId) {
        return contentsService.addOppose(contentId);
    }
    /**
     * 获取赞同数
     * @param contentId
     */
    @ResponseBody
    @RequestMapping(params = "getSupport")
    public String getSupport(String  contentId) {
        JSONObject j =new  JSONObject();
        ContentsEntity content=contentsService.get(ContentsEntity.class,contentId);
        Integer support = content.getSupport();
        Integer oppose = content.getOppose();
        j.accumulate("support",support);
        j.accumulate("oppose",oppose);
        return  j.toString();
    }
    /**
     * 获取反对数
     * @param contentId
     */
    @ResponseBody
    @RequestMapping(params = "getOppose")
    public Integer getOppose(String  contentId) {
        return contentsService.getOppose(contentId);
    }

    /**
     * 查询 评论数量
     *
     * @param contentId
     */
    @RequestMapping(params = "calculationCommentCount")
    @ResponseBody
    public String calculationCommentCount(String  contentId) {
        JSONObject j = new JSONObject();
        long commentCount = contentsService.calculationCommentCount(contentId);
        j.accumulate("commentCount", commentCount);
        String msg = j.toString();
        return msg;
    }

    /**
     * 增加浏览量
     *
     * @param contentId
     */
    @RequestMapping(params = "addPv")
    @ResponseBody
    public void addPv(String  contentId) {
        contentsService.addPv(contentId);
    }

//    /**
//     * 标签页
//     */
//    @RequestMapping(params = "getTagList")
//    public ModelAndView getTagList(HttpServletRequest request) {
//        String tags=request.getParameter("tags");
//        String siteid = request.getParameter("siteid");
//        int pageSize = org.apache.commons.lang.StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
//        int pageNo = org.apache.commons.lang.StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
//        Map params = new HashMap();
//        params.put("keyword",tags);
//        params.put("pageNo",pageNo);
//        params.put("pageSize",pageSize);
//
//        SiteEntity site = null;
//        if (org.apache.commons.lang.StringUtils.isNotEmpty(siteid)) {
//            site = siteService.getSite(String.valueOf(siteid));
//        }
//        if (site == null) {
//            site = PlatFormUtil.getFrontSessionSite();
//        }
//
//        Map<String, Object> newsMap = luceneServiceI.searchContent(site, params);// 检索后返回的数据
//        List<NewsIndexEntity> newslist = (List<NewsIndexEntity>) newsMap.get("newslist");// 当前页条数
//        int totalSize = (Integer) newsMap.get("totalSize");// 总条数
//
//        int pageCount = (int) Math.ceil((double) totalSize / (double) pageSize);
//        if (pageCount <= 0) {
//            pageCount = 1;
//        }
//        Map<String,Object> m=new HashMap<String,Object>();
//        m.put("site", site);
//        m.put("tags", tags);  //标签
//        m.put("pageList", newslist);
//        m.put("pageNo", pageNo);
//        m.put("pageCount", pageCount);
//        m.put("sitePath", site.getSitePath());
//        m.put("actionUrl", "contentsFrontController.do?getTagList");
//
//        return new ModelAndView("lucene/tags_list",m);
//    }

}
