package com.leimingtech.mobile.controller.contents;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.entity.WorkflowAuditingEntity;
import com.leimingtech.core.service.ContentsMobileServiceI;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.WorkFlowServiceI;
import com.leimingtech.core.util.PlatFormUtil;



/**   
 * @Title: Controller
 * @Description: 内容基类
 * @author zhangdaihao modify by wanghao 20140429
 * @date 2014-04-21 15:05:15
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/contentsMobilebaseController")
public class ContentsMobilebaseController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ContentsMobilebaseController.class);

	@Autowired
	private WorkFlowServiceI workFlowService;
	@Autowired
	private ContentsMobileServiceI contentsMobileService;
	/**移动频道管理接口*/
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
	protected void enterworkflow(ContentsMobileEntity contents,String contentCatId){
		//工作流开始
		if("10".equals(contents.getStatus())){
			//10为草稿，暂时不进入审核流程
		}else{
			MobileChannelEntity concat = mobileChannelService.getEntity(contentCatId);
			if(concat.getWorkflowid()!=null){
				WorkflowAuditingEntity audit = workFlowService.enterWorkFlow(concat.getWorkflowid()+"", contents.getId(), 1);//type=1是内容
				if(audit!=null){
					contents.setWorkflowRoleid(audit.getAuditingrole());
					contents.setWorkflowStep(audit.getStep());
					contents.setStatus("20");//20为待审
				}else{
					contents.setStatus("30");//30为已审
				}
			}else{
				contents.setStatus("30");//30为已审
			}
			
			contentsMobileService.saveOrUpdate(contents);
		}
		//工作流结束
	}
	
	/**
	 * 内容审核
	 * 
	 * @return
	 */
	@RequestMapping(params = "songshen")
	@ResponseBody
	public String songshen(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String ids = request.getParameter("ids");
		String[] contentsids = ids.split(",");
		message = "审核成功";
		for (String contentsid : contentsids) {
			ContentsMobileEntity contents = contentsMobileService.getEntity(contentsid);
			if(!"10".equals(contents.getStatus())){
				continue;
			}
			MobileChannelEntity concat = mobileChannelService.getEntity(contents.getCatid());
			if(concat.getWorkflowid()!=null){
				WorkflowAuditingEntity audit = workFlowService.enterWorkFlow(concat.getWorkflowid()+"", contents.getId(), 1);//type=1是内容
				if(audit!=null){
					contents.setWorkflowRoleid(audit.getAuditingrole());
					contents.setWorkflowStep(audit.getStep());
					contents.setStatus("20");//20为待审
				}else{
					contents.setStatus("30");//30为已审
				}
			}else{
				contents.setStatus("30");//30为已审
			}
			
			contentsMobileService.saveOrUpdate(contents);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 内容审核
	 * 
	 * @return
	 */
	@RequestMapping(params = "contentsAudit")
	@ResponseBody
	public String contentsAudit(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		TSUser user = PlatFormUtil.getSessionUser();
		String ids = request.getParameter("ids");
		String result = request.getParameter("result");
		String[] contentsids = ids.split(",");
		message = "审核成功";
		for (String contentsid : contentsids) {
			boolean flag = workFlowService.Audit(user, Integer.valueOf(result), null, String.valueOf(contentsid), 1);
			if(flag){
				ContentsMobileEntity cont = contentsMobileService.getEntity(contentsid );
				if(!"0".equals(result)){
					WorkflowAuditingEntity audit = workFlowService.getCurrAudit(String.valueOf(contentsid), 1);
					if(audit!=null){
						cont.setWorkflowRoleid(audit.getAuditingrole());
						cont.setWorkflowStep(audit.getStep());
						cont.setStatus("20");
					}else{
						cont.setStatus("30");
					}
				}else{
					cont.setStatus("40");
				}
			
				contentsMobileService.saveOrUpdate(cont);
			}
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
	/**
	 * 当前人
	 * @return String
	 */
	public String markpeople(){
		String markpeople = "";
		if(PlatFormUtil.getSessionUser()!=null){
			markpeople= PlatFormUtil.getSessionUser().getUserName();
			if(StringUtils.isEmpty(markpeople)){
				markpeople=PlatFormUtil.getSessionUser().getRealName();
			}
		}
		return markpeople;
	}
	/**
	 * 生成内容二维码
	 * @param request
	 */
	@RequestMapping(params = "enCode")
	@ResponseBody
	public String enCode(HttpServletRequest request){
		String contentId = request.getParameter("contentId");
		String msg = "";
		JSONObject j = new JSONObject();
		try {
			contentsMobileService.encode(String.valueOf(contentId));
			msg = "已生成二维码";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "生成失败！";
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", msg);
		String jsonStr = j.toString();
		return jsonStr;
	}
}
