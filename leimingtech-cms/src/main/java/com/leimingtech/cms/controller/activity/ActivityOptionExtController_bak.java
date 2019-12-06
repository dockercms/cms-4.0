package com.leimingtech.cms.controller.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.entity.activity.ActivityOptionEntity;
import com.leimingtech.cms.service.activity.ActivityOptionExtServiceI;
import com.leimingtech.cms.service.activity.ActivityPeopleServiceI;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ActivityEntity;
import com.leimingtech.core.entity.ActivityOptionContentEntity;
import com.leimingtech.core.entity.ActivityOptionExtEntity;
import com.leimingtech.core.entity.ActivityPeopleEntity;
import com.leimingtech.core.service.ActivityServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**
 * @Title: Controller
 * @Description: 活动报名表
 * @author
 * @date 2015-08-28 17:59:53
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/activityOptionExtController_bak")
public class ActivityOptionExtController_bak extends BaseController {

	private String message;
	/** 活动报名表接口 */
	@Autowired
	private ActivityOptionExtServiceI activityOptionExtService;
	/** 活动表接口 */
	@Autowired
	private ActivityServiceI activityService;

	/** 活动人员接口 */
	@Autowired
	private ActivityPeopleServiceI activityPeopleService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;

	/**
	 * 活动报名表列表
	 *
	 * @param request
	 */
	@RequestMapping(params = "activityOptionExt")
	public ModelAndView activityOptionExt(
			ActivityOptionExtEntity activityOptionExt,
			HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = activityOptionExtService.getPageList(
				activityOptionExt, param, pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "activityOptionExtController.do?activityOptionExt");
		return new ModelAndView("cms/activity/activityOptionExtList", m);
	}

	/**
	 * 后台活动报名表查看
	 *
	 * @return
	 */
	@RequestMapping(params = "queryLog")
	public ModelAndView queryLog(HttpServletRequest reqeust) {

		String activityId = reqeust.getParameter("activityId");// 活动id
		String activityLogId = reqeust.getParameter("activityLogId");// 报名人id
		String sql = "select  apc.optionids as optionids,"

				+ "ap.optional_value as optionalValue,ap.date_type as dateType,apc.optionids as optionids,"
				+ "ap.is_show as isShow, ap.option_name as optionName "
				+ " from cms_activity_optioncontent  apc,cms_activity_option ap "
				+ "WHERE ap.id=apc.optionids and ap.is_enabled=1 and apc.is_receptionShow=1 "
				+ "and  apc.activityids='" + activityId
				+ "' ORDER BY ap.sort ,ap.createtime DESC";
		List<Map<String, Object>> activityOptionList = this.activityService
				.findForJdbc(sql);
		List<ActivityOptionExtEntity> activityOptionExtList = this.activityService
				.findByProperty(ActivityOptionExtEntity.class, "logids",
						activityLogId);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("activityOptionList", activityOptionList);
		m.put("activityOptionExtList", activityOptionExtList);
		return new ModelAndView("cms/activity/activityOptionExtList", m);
	}

	/**
	 * 活动报名表更新
	 *
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		ActivityOptionExtEntity activityOptionExt = activityOptionExtService
				.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("activityOptionExt", activityOptionExt);
		return new ModelAndView("cms/activity/activityOptionExt", m);
	}

	/**
	 * 活动报名表保存
	 *
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ActivityOptionExtEntity activityOptionExt,
			HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;

		if (StringUtils.isNotEmpty(activityOptionExt.getId())) {
			message = "活动报名表【" + activityOptionExt.getId() + "】更新成功";
			ActivityOptionExtEntity t = activityOptionExtService
					.getEntity(activityOptionExt.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(activityOptionExt, t);
				activityOptionExtService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "活动报名表【" + activityOptionExt.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "活动报名表【" + activityOptionExt.getId() + "】添加成功";
			activityOptionExtService.save(activityOptionExt);
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl",
				"activityOptionExtController.do?activityOptionExt");
		String str = j.toString();
		return str;
	}

	/**
	 * 活动报名表删除
	 *
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		ActivityOptionExtEntity activityOptionExt = activityOptionExtService
				.getEntity(java.lang.String.valueOf(id));
		message = "活动报名表【" + activityOptionExt.getId() + "】删除成功";
		activityOptionExtService.delete(activityOptionExt);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl",
				"activityOptionExtController.do?activityOptionExt");
		String str = j.toString();
		return str;
	}

	/**
	 * 前台活动提交保存
	 *
	 * @param request
	 * @throws ParseException
	 */
	@RequestMapping(params = "subActivity")
	@ResponseBody
	public String subActivity(HttpServletRequest request) throws ParseException {
		JSONObject j = new JSONObject();

		String activityId = request.getParameter("activityId");// 活动id
		String activityplenum = request.getParameter("activityplenum");
		int peoplecount = Integer.parseInt(activityplenum);// 活动总人数
		String textsizeLimit = request.getParameter("textsizeLimit");// 文本长度
		HttpSession session = ContextHolderUtils.getSession();

		String isRequired = request.getParameter("isRequired");// 必填
		String randCode = session.getAttribute("randCode").toString();// 验证码
		String code = request.getParameter("valicode");
		String selectsizeLimit = request.getParameter("selectsizeLimit");// 多选个数

		String validation = request.getParameter("validation");
		String activityendtime = request.getParameter("activityendtime");// 活动结束时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endtime = sdf.parse(activityendtime); // 活动结束时间
		Date date = new Date(); // 当前时间

		// 获取客户端IP
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		ActivityEntity activity = activityService.get(ActivityEntity.class,
				String.valueOf(activityId));
		int ipnum = 0;
		if (StringUtil.isNotEmpty(activity.getActivityiplimit())) {
			ipnum = Integer.valueOf(activity.getActivityiplimit());
		}
		int ipLimit = ipnum * 3600;// 小时限制

		String sql = "select * from cms_activity_people where ip='" + ip
				+ "' and UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(createtime)<'"
				+ ipLimit + "'";
		List<ActivityPeopleEntity> activityPeopleList = activityService
				.findListbySql(sql);

		List<ActivityPeopleEntity> peopleList = activityService.findByProperty(
				ActivityPeopleEntity.class, "activityids", activityId);

		int size = activityPeopleList.size();
		// 同IP,ipLimit小时之内没有数据表示可以投票
		if (date.getTime() > endtime.getTime()) {
			j.accumulate("isSuccess", false);
			message = "报名时间已截止";
		} else if (peopleList.size() >= peoplecount) {
			j.accumulate("isSuccess", false);
			message = "活动报名人数已够";
		} else if (StringUtils.isNotEmpty(textsizeLimit)
				|| StringUtils.isNotEmpty(selectsizeLimit)
				|| StringUtils.isNotEmpty(validation)
				|| StringUtils.isNotEmpty(isRequired)) {
			List<ActivityOptionContentEntity> activityoptionLists = activityService
					.findByQueryString(" from ActivityOptionContentEntity where activityids='"
							+ activityId + "' and isReceptionshow=1");
			if (activityoptionLists != null) {
				boolean isHave = false;
				for (ActivityOptionContentEntity aoce : activityoptionLists) {

					String names = request.getParameter(aoce.getId());

					ActivityOptionEntity aoe = activityService.getEntity(
							ActivityOptionEntity.class, aoce.getOptionids());// 根据选项id查询唯一的选项表单实体

					String checkbox[] = request
							.getParameterValues(aoce.getId());

					int textsize = 0;
					int selectsize = 0;
					if (StringUtils.isNotEmpty(aoe.getSelectsizeLimit())) {

						selectsize = Integer.parseInt(aoe.getSelectsizeLimit());// 选项长度
					}
					if (StringUtils.isNotEmpty(aoe.getTextsizeLimit())) {

						textsize = Integer.parseInt(aoe.getTextsizeLimit());// 文本长度
					}

					if (StringUtils.isNotEmpty(names)
							&& aoe.getDateType().equals("checkbox")
							&& StringUtils.isNotEmpty(aoe.getSelectsizeLimit())
							&& (checkbox.length > selectsize)) {// 类型为多选

						message = aoe.getOptionName() + "最多选择" + selectsize
								+ "项";
						isHave = true;

						break;

					} else if (aoe.getDateType().equals("text")
							&& StringUtils.isNotEmpty(aoe.getTextsizeLimit())
							&& (names.length() > textsize)) {// 类型为文本

						message = aoe.getOptionName() + "输入的长度大于" + textsize
								+ "个字符";
						isHave = true;

						break;

					} else if (aoe.getDateType().equals("textarea")
							&& StringUtils.isNotEmpty(aoe.getTextsizeLimit())
							&& (names.length() > textsize)) {// 类型为文本

						message = aoe.getOptionName() + "输入的长度大于" + textsize
								+ "个字符";
						isHave = true;

						break;

					} else if (aoe.getValidation().equals("number")
							&& !names.matches("^[0-9]*$")) { // 数字验证

						message = aoe.getOptionName() + "输入的格式不正确,应为数字";
						isHave = true;

						break;
					} else if (aoe.getValidation().equals("english")
							&& !names.matches("^[A-Za-z]+$")) { // 英文字符验证

						message = aoe.getOptionName() + "输入的格式不正确,应为英文";
						isHave = true;

						break;

					} else if (aoe.getValidation().equals("chinese")
							&& !names.matches("[\u4e00-\u9fa5]")) { // 中文字符验证

						message = aoe.getOptionName() + "输入的格式不正确,应为中文";

						isHave = true;

						break;
					} else if (aoe.getValidation().equals("email")
							&& !names
									.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) { // 邮箱验证

						message = aoe.getOptionName() + "输入的格式不正确";

						isHave = true;

						break;
					} else if (aoe.getValidation().equals("cellphone")
							&& !names
									.matches("^(\\(\\d{3,4}-)|\\d{3.4}-)?\\d{7,8}$")) { // 电话号码验证

						message = aoe.getOptionName() + "输入的格式不正确";

						isHave = true;

						break;
					} else if (aoe.getValidation().equals("telephone")
							&& !names.matches("^[1][3,4,5,8][0-9]{9}$")) { // 手机号码验证

						message = aoe.getOptionName() + "输入的格式不正确";

						isHave = true;

						break;
					} else if (aoe.getValidation().equals("http")
							&& !names
									.matches("^[a-zA-z]+://(w+(-w+)*)(.(w+(-w+)*))*(?S*)?$")) { // 网址验证

						message = aoe.getOptionName() + "输入的格式不正确";

						isHave = true;

						break;
					} else if (aoe.getValidation().equals("cardnumber")
							&& !names.matches("^\\d{15}|\\d{18}$")) { // 身份证号码验证

						message = aoe.getOptionName() + "输入的格式不正确";

						isHave = true;

						break;
					} else if (aoe.getValidation().equals("qq")
							&& !names.matches("^[1-9][0-9]{4,} $")) { // QQ号码验证

						message = aoe.getOptionName() + "输入的格式不正确";

						isHave = true;

						break;
					} else if (aoe.getValidation().equals("postalcode")
							&& !names.matches("[1-9]\\d{5}(?!\\d)")) { // 邮政编码验证

						message = aoe.getOptionName() + "输入的格式不正确";
						isHave = true;

						break;

					} else if (!randCode.equalsIgnoreCase(code)) {// 验证码验证

						message = "验证码错误";
						isHave = true;
						break;
					} else if (aoe.getDateType().equals("checkbox")
							&& StringUtils.isEmpty(names)
							&& aoce.getIsEnableds() == 1) {
						message = "至少选择一个";
						isHave = true;
						break;
					} else if (aoe.getDateType().equals("picture")
							&& StringUtils.isEmpty(names)
							&& aoce.getIsEnableds() == 1) {
						message = "没有选择图片";
						isHave = true;
						break;
					}

				}
				if (!isHave) {
					try {
						message = saveExeList(ip, activityId, request);
						j.accumulate("isSuccess", true);
					} catch (Exception e) {
						e.printStackTrace();
						j.accumulate("isSuccess", false);
						message = "服务器处理失败，请稍后重试";
					}
				}

			}
		}

		else if (size != 0) {
			j.accumulate("isSuccess", false);
			message = "请勿在 " + ipnum + " 小时之内重复提交，详情请咨询管理员";
		} else {
			try {
				message = saveExeList(ip, activityId, request);
				j.accumulate("isSuccess", true);
			} catch (Exception e) {
				e.printStackTrace();
				j.accumulate("isSuccess", false);
				message = "服务器处理失败，请稍后重试";
			}
		}
		j.accumulate("msg", message);
		return j.toString();
	}

	// 保存活动人员和填写的表单
	public String saveExeList(String ip, String activityId,
			HttpServletRequest request) {

		// 添加报名人数
		ActivityPeopleEntity activityPeople = new ActivityPeopleEntity();
		activityPeople.setActivityids(activityId);
		activityPeople.setCreatetime(new Date());
		activityPeople.setIp(ip);
		activityPeople.setCreateby("匿名");
		activityPeopleService.save(activityPeople);
		// 根据活动id查询活动表单选项
		List<ActivityOptionContentEntity> activityoptionList = activityService
				.findByQueryString(" from ActivityOptionContentEntity where activityids='"
						+ activityId + "' and isReceptionshow=1");
		if (activityoptionList != null) {
			for (ActivityOptionContentEntity ace : activityoptionList) {

				String name = request.getParameter(ace.getId());
				if (StringUtils.isNotEmpty(name)) {
					ActivityOptionEntity aoe = activityService.getEntity(
							ActivityOptionEntity.class, ace.getOptionids());// 根据选项id查询唯一的选项表单实体

					ActivityOptionExtEntity aoee = new ActivityOptionExtEntity();
					aoee.setIp(ip);
					aoee.setLogids(activityPeople.getId());
					if (aoe.getDateType().equals("radio")) {// 类型为单选
						aoee.setOptiondids(aoe.getId());
						aoee.setExtCheckbox(name);
					} else if (aoe.getDateType().equals("checkbox")) {// 类型为多选
						String checkbox[] = request.getParameterValues(ace
								.getId());
						StringBuffer sb = new StringBuffer();
						for (int i = 0; i < checkbox.length; i++) {
							sb.append(checkbox[i] + ",");
						}
						aoee.setOptiondids(aoe.getId());
						aoee.setExtCheckbox(sb.toString());

					} else if (aoe.getDateType().equals("text")) {// 类型为文本
						aoee.setOptiondids(aoe.getId());
						aoee.setExtText(name);
					} else if (aoe.getDateType().equals("img")) {// 类型为图片
						aoee.setOptiondids(aoe.getId());
						aoee.setExtPicture(name);
					} else if (aoe.getDateType().equals("file")) {// 类型为文件
						aoee.setOptiondids(aoe.getId());
						aoee.setExtFile(name);
					} else if (aoe.getDateType().equals("select")) {// 类型为下拉
						aoee.setOptiondids(aoe.getId());
						aoee.setExtSelect(name);
					} else if (aoe.getDateType().equals("textarea")) {// 类型为文本域
						aoee.setOptiondids(aoe.getId());
						aoee.setExtText(name);
					}
					aoee.setCreatetime(new Date());
					activityOptionExtService.save(aoee);// 添加演示站活动表单
				}
			}
		}
		message = "提交成功，感谢您的参与。";
		return message;

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
