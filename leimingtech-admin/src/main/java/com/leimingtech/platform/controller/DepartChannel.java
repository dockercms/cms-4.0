package com.leimingtech.platform.controller;

import com.leimingtech.cms.json.AjaxJson;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TSDepart;
import com.leimingtech.core.entity.TSDepartChannel;
import com.leimingtech.core.service.ContentCatServiceI;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.contentcatdefault.ContentCatDefaultServiceI;
import com.leimingtech.core.service.depart.DepartServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 部门栏目管理
 * Created by gehanyang on 2016/1/13.
 */
@Controller
@RequestMapping("/departChannelAction")
public class DepartChannel extends BaseController {
    private static final Logger logger = Logger.getLogger(DepartController.class);
    @Autowired
    private SystemService systemService;
    @Autowired
    private DepartServiceI departService;
    /**
     * 站点管理接口
     */
    @Autowired
    private SiteServiceI siteService;
    /**
     * 栏目管理接口
     */
    @Autowired
    private ContentCatServiceI contentCatService;
    /**
     * 会员栏目默认权限管理
     */
    @Autowired
    private ContentCatDefaultServiceI contentCatDefaultService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取部门列表信息
     *
     * @param request
     * @param depart
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request, TSDepart depart) {
        String id = request.getParameter("id");
        TSDepart parentDepart = null;
        if (StringUtils.isEmpty(id) || "-1".equals(id)) {
            //点击顶级菜单
            parentDepart = new TSDepart();
            parentDepart.setId(null);
            parentDepart.setDepartname("顶级菜单");
        } else {
            parentDepart = departService.findUniqueByProperty(TSDepart.class, "id", id);

        }
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("jstreeData", departService.getTreeJson(id).toString());
        m.put("parentDepart", depart);
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(id)) {
            List<TSDepartChannel> contentCatPrivList = departService.findByProperty(TSDepartChannel.class, "depart.id", id);
            Set<String> set = new HashSet<String>();
            for (TSDepartChannel departChannel : contentCatPrivList) {
                if (null != departChannel.getChannel()) {
                    set.add(String.valueOf(departChannel.getChannel().getId()));
                }
            }
            List<SiteEntity> sites = siteService.siteList();
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            for (SiteEntity site : sites) {
                Map<String, Object> mData = new HashMap<String, Object>();
                mData.put("site", site);
                mData.put("channelJson",
                        departService.contentCatTree(set, site.getId()).toString());
                data.add(mData);
            }
            m.put("data", data);
        }
        m.put("selectId", id);
        return new ModelAndView("lmPage/departChannel/depart_list", m);
    }

    /**
     * 加载下级菜单
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "menuTable")
    public ModelAndView menuTable(HttpServletRequest request) {
        String id = request.getParameter("id");
        TSDepart parentDepart = null;
        if (StringUtils.isEmpty(id) || "-1".equals(id)) {
            //点击顶级菜单
            parentDepart = new TSDepart();
            parentDepart.setId(null);
            parentDepart.setDepartname("顶级菜单");
        } else {
            parentDepart = departService.findUniqueByProperty(TSDepart.class, "id", id);
        }
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("parentDepart", parentDepart);
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(id)) {
            List<TSDepartChannel> contentCatPrivList = departService.findByProperty(TSDepartChannel.class, "depart.id", id);
            Set<String> set = new HashSet<String>();
            for (TSDepartChannel departChannel : contentCatPrivList) {
                if (null != departChannel.getChannel()) {
                    set.add(String.valueOf(departChannel.getChannel().getId()));
                }
            }
            List<SiteEntity> sites = siteService.siteList();
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            for (SiteEntity site : sites) {
                Map<String, Object> mData = new HashMap<String, Object>();
                mData.put("site", site);
                mData.put("channelJson", this.contentCatService.contentCatTree(set, site.getId()).toString());
                data.add(mData);
            }
            m.put("data", data);

        }
        m.put("selectId", id);
        return new ModelAndView("lmPage/departChannel/menuTable", m);
    }

    @RequestMapping(params = "setDefault")
    public ModelAndView setDefault(HttpServletRequest request) {

        //查询选中项
        List<String> contentCatids = contentCatDefaultService.getAllContentCatId();
        Set<String> set = new HashSet<String>();
        CollectionUtils.addAll(set, contentCatids.toArray(new String[]{}));

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        List<SiteEntity> sites = siteService.siteList();
        for (SiteEntity site : sites) {
            Map<String, Object> mData = new HashMap<String, Object>();
            mData.put("site", site);
            mData.put("channelJson", this.contentCatService.contentCatTree(set, site.getId()).toString());
            data.add(mData);
        }

        Map<String, Object> m = new HashMap<String, Object>();
        m.put("data", data);
        return new ModelAndView("lmPage/defaultChannel/menuTable", m);
    }

    /**
     * @param request
     * @param funval2
     * @return
     */
    @RequestMapping(params = "savecontentCatDefault")
    @ResponseBody
    public String savecontentCatDefault(HttpServletRequest request, String[] funVal) {
        StringBuilder sb = new StringBuilder();
        if (funVal != null && funVal.length > 0) {
            for (int i = 0; i < funVal.length; i++) {
                if (i == 0) {
                    sb.append(funVal[i]);
                } else {
                    sb.append("," + funVal[i]);
                }
            }
        }

        try {
            message = "会员默认权限更新成功";

            departService.saveDefaultChannel(sb.toString());
            systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
        } catch (Exception e) {
            message = "会员默认权限更新失败，原因：" + e.getMessage();
            e.printStackTrace();
            return error(message).toString();
        }
        return success(message).accumulate("toUrl", "departChannelAction.do?setDefault").toString();
    }

    /**
     * PC栏目权限设置跳转
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "channelTree")
    public ModelAndView channelTree(HttpServletRequest request) {
        String departId = request.getParameter("departId");
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("selectId", departId);
        m.put("channelJson", departService.loadChannelTree(departId).toString());
        return new ModelAndView("lmPage/departChannel/contentCatSet", m);
    }

    /**
     * 保存已勾选PC栏目权限
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "savecontentCatPriv")
    @ResponseBody
    public String savecontentCatPriv(HttpServletRequest request, String[] funVal) {
        String departId = request.getParameter("departId");
        StringBuilder sb = new StringBuilder();
        if (funVal != null && funVal.length > 0) {
            for (int i = 0; i < funVal.length; i++) {
                if (i == 0) {
                    sb.append(funVal[i]);
                } else {
                    sb.append("," + funVal[i]);
                }
            }
        }
        try {
            message = "更新成功";
            departService.saveDepartChannel(departId, sb.toString());
            systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
        } catch (Exception e) {
            message = "权限更新失败，原因：" + e.getMessage();
            e.printStackTrace();
            return error(message).toString();
        }
        return success(message).accumulate("toUrl", "departChannelAction.do?list&id=" + departId).toString();
    }

    /**
     * 部门向下一级传播权限
     *
     * @param depart
     * @param request
     * @return
     */
    @RequestMapping(params = "spread")
    @ResponseBody
    public AjaxJson spread(TSDepart depart, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String funval = "";
        depart = departService.getEntity(TSDepart.class, depart.getId());
        List<TSDepartChannel> contentCatPrivList = departService.findByProperty(TSDepartChannel.class, "depart.id", depart.getId());
        if (contentCatPrivList.size() != 0) {
            List<TSDepart> departList = departService.getListByPid(depart.getId());
            if (departList.size() != 0) {
                for (TSDepartChannel departChannel : contentCatPrivList) {
                    funval += departChannel.getChannel().getId() + ",";
                }
                for (TSDepart tsDepart : departList) {
                    departService.saveDepartChannelSpread(tsDepart.getId(), funval);
                }
                message = "部门: " + depart.getDepartname() + "传播权限 成功";
            } else {
                message = "部门: " + depart.getDepartname() + "没有下级";
            }
        } else {
            message = "部门: " + depart.getDepartname() + "没有权限";
        }
        systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
        j.setSuccess(false);
        j.setMsg(message);
        return j;
    }

    /**
     * 继承上级部门的权限
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(params = "inherit")
    @ResponseBody
    public AjaxJson inherit(String id, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        TSDepart depart = departService.getEntity(TSDepart.class, id);
        String funval = "";
        TSDepart pDepart = depart.getTSPDepart();
        if (pDepart != null) {
            List<TSDepartChannel> contentCatPrivList = departService.findByProperty(TSDepartChannel.class, "depart.id", pDepart.getId());
            if (contentCatPrivList.size() != 0) {
                for (TSDepartChannel departChannel : contentCatPrivList) {
                    funval += departChannel.getChannel().getId() + ",";
                }
                departService.saveDepartChannelSpread(depart.getId(), funval);
                message = "部门: " + depart.getDepartname() + "继承成功";
                j.setObj("departChannelAction.do?list&id=" + id);
                j.setSuccess(true);
            } else {
                message = "部门: " + depart.getDepartname() + "上级无权限";
                j.setSuccess(false);
            }
        } else {
            message = "部门: " + depart.getDepartname() + "上级不存在";
            j.setSuccess(false);
        }

        j.setMsg(message);
        return j;
    }
}
