package com.leimingtech.wechat.controller.wechatAPI;

import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.wechat.entity.wechatconfigure.WechatConfigureEntity;
import com.leimingtech.wechat.entity.wechatcontent.WeChatContentEntity;
import com.leimingtech.wechat.entity.wechatpush.WeChatPushEntity;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

/**
 * @Title: Controller
 * @Description: 微信号管理
 * @author
 * @date 2015-08-12 18:18:12
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/front/wechatAPI")
public class wechatAPI extends BaseController {

	private String message;

	@Autowired
	private SystemService systemService;
	@Autowired
	private CommonService commonService;


	/**
	 * 微信号
	 * 
	 * @return
	 * @return
	 * @throws IOException
	 * @throws WxErrorException
	 */
	@RequestMapping("/api")
	@ResponseBody
	public String api(String siteId, HttpServletRequest request, HttpServletResponse response)
			throws IOException, WxErrorException {

		if(StringUtil.isEmpty(siteId)){
			LogUtil.error("微信通知校验接口未收到站点id，请在微信接口配置地址上传入siteId值，例如：/leimingtech-admin/front/wechatAPI/api.do?siteId=XXXX");
			return "请传入站点id";
		}

		WechatConfigureEntity wechatConfigure = commonService.findUniqueByProperty(WechatConfigureEntity.class, "siteid", siteId);
		if (wechatConfigure == null) {
			String errorInfo = "id为" + siteId + "站点没有做微信配置";
			LogUtil.error(errorInfo);
			return errorInfo;
		}

		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		config.setAppId(wechatConfigure.getAppid()); // 设置微信公众号的appid
		config.setSecret(wechatConfigure.getSecret()); // 设置微信公众号的app
		// corpSecret
		config.setToken(wechatConfigure.getToken()); // 设置微信公众号的token
		config.setAesKey(wechatConfigure.getAeskey()); // 设置微信公众号的EncodingAESKey

		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(config);
		/**
		 * 测试微信接口服务器配置是否配置成功
		 */

		WxMpMessageRouter wxMpMessageRouter = new WxMpMessageRouter(wxMpService);

		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		String signature = request.getParameter("signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");

		if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
			// 消息签名不正确，说明不是公众平台发过来的消息
			LogUtil.error("微信消息签名不正确，非法请求");
			response.getWriter().println("非法请求");
			return null;
		}

		String echostr = request.getParameter("echostr");
		if (StringUtils.isNotBlank(echostr)) {
			// 说明是一个仅仅用来验证的请求，回显echostr
			response.getWriter().println(echostr);
			return null;
		}

		String encryptType = StringUtils.isBlank(request
				.getParameter("encrypt_type")) ? "raw" : request
				.getParameter("encrypt_type");

		if ("raw".equals(encryptType)) {
			// 明文传输的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request
					.getInputStream());
			LogUtil.info(inMessage.toString());
			WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
			response.getWriter().write(outMessage.toXml());
			return null;
		}

		if ("aes".equals(encryptType)) {
			// 是aes加密的消息
			String msgSignature = request.getParameter("msg_signature");
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(
					request.getInputStream(), config, timestamp, nonce,
					msgSignature);
			WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
			response.getWriter().write(outMessage.toEncryptedXml(config));
			return null;
		}

		response.getWriter().println("不可识别的加密类型");
		return null;
			

			

	}
	/**
	 * 推送功能
	 * @param request
	 * @return
	 * @throws WxErrorException
	 * @throws IOException
	 */
	@RequestMapping(params = "tuiSong")
	@ResponseBody
	public String tuiSong(HttpServletRequest request) throws WxErrorException,
			IOException {
		JSONObject j = new JSONObject();
		String weixinid = request.getParameter("weixinId");

		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();

		WechatConfigureEntity wechatConfigure = null;
		SiteEntity site = PlatFormUtil.getSessionSite();
		wechatConfigure = commonService.findUniqueByProperty(WechatConfigureEntity.class, "siteid", site.getId());
		if (wechatConfigure != null) {
			config.setAppId(wechatConfigure.getAppid()); // 设置微信公众号的appid
			config.setSecret(wechatConfigure.getSecret()); // 设置微信公众号的app
			// corpSecret
			config.setToken(wechatConfigure.getToken()); // 设置微信公众号的token
			config.setAesKey(wechatConfigure.getAeskey()); // 设置微信公众号的EncodingAESKey


			String domain = PlatFormUtil.getCurrentSitedomain(); //获取站点域名
			List<WeChatContentEntity> contentlist = commonService.findByProperty(
					WeChatContentEntity.class, "weixinId", weixinid);
			if (contentlist != null) {
				try {
					WxMpService wxMpService = new WxMpServiceImpl();
					wxMpService.setWxMpConfigStorage(config);

					WxMpMassNews news = new WxMpMassNews();

					for (WeChatContentEntity content : contentlist) {
						//没有图片
						if (StringUtils.isEmpty(content.getWeixinPictureurl())) {
							WxMpMassNews.WxMpMassNewsArticle article = new WxMpMassNews.WxMpMassNewsArticle();
							article.setTitle(content.getWeixinTitle());// 标题
							article.setContent(content.getWeixinContent());// 内容
							article.setAuthor(content.getWeixinAuthor());// 作者
							if (!content.getWeixinContentaddress().startsWith("http")) {
								article.setContentSourceUrl("" + domain + "" + content.getWeixinContentaddress());// 链接
							} else {
								article.setContentSourceUrl(content.getWeixinContentaddress());// 链接
							}
							article.setDigest(content.getWeixinDigest());// 摘要
							news.addArticle(article);
							//有图片
						} else {
							WxMediaUploadResult uploadMediaRes = null;
							if (content.getWeixinPictureurl().contains("http")) {
								URL url = new URL(content.getWeixinPictureurl());//filePath是网络的地址
								URLConnection conn = url.openConnection();
								InputStream inStream = conn.getInputStream();
								BufferedInputStream br = new BufferedInputStream(inStream);
								uploadMediaRes = wxMpService.getMaterialService().mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, br);

							} else {

								String uploadFilesPath = CmsConstants.getUploadFilesPath(PlatFormUtil.getSessionSite());//图片本地地址
								File file = new File(uploadFilesPath + content.getWeixinPictureurl());
								InputStream inputStream = new FileInputStream(file);
								uploadMediaRes = wxMpService.getMaterialService().mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
							}


							WxMpMassNews.WxMpMassNewsArticle article = new WxMpMassNews.WxMpMassNewsArticle();
							article.setTitle(content.getWeixinTitle());// 标题

							article.setContent(content.getWeixinContent());// 内容
							article.setThumbMediaId(uploadMediaRes.getMediaId());
							if (content.getShowCoverPic() == 1) {

								article.setShowCoverPic(true); // 封面
							} else {
								article.setShowCoverPic(false);
							}
							article.setAuthor(content.getWeixinAuthor());// 作者
							if (!content.getWeixinContentaddress().startsWith("http")) {
								article.setContentSourceUrl("" + domain + "" + content.getWeixinContentaddress());// 链接
							} else {
								article.setContentSourceUrl(content.getWeixinContentaddress());// 链接
							}
							article.setDigest(content.getWeixinDigest());// 摘要
							news.addArticle(article);

						}
					}
					WxMpMassUploadResult massUploadResult = wxMpService
							.massNewsUpload(news);

					WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
					massMessage.setMsgType(WxConsts.MASS_MSG_NEWS);
					massMessage.setMediaId(massUploadResult.getMediaId());

					List<String> users = massMessage.getToUsers();

					WxMpUserList wxUserList = wxMpService.getUserService().userList(null);

					for (String openids : wxUserList.getOpenIds()) {
						users.add(openids);
					}
					WxMpMassSendResult massResult = wxMpService
							.massOpenIdsMessageSend(massMessage);


					WeChatPushEntity push = commonService.getEntity(
							WeChatPushEntity.class, weixinid);
					push.setIsPush(1);
					push.setIsSucesspush(1);
					push.setPushTime(new Date());
					push.setPushPerson(PlatFormUtil.getSessionUser().getUserName());
					commonService.saveOrUpdate(push);
					message = "微信内容推送成功";
					systemService.addLog(message, Globals.Log_Leavel_INFO,
							Globals.Log_Type_DEL);
					j.accumulate("isSuccess", true);
				} catch (Exception e) {
					e.printStackTrace();
					WeChatPushEntity push = commonService.getEntity(
							WeChatPushEntity.class, weixinid);
					push.setIsPush(1);
					push.setIsSucesspush(0);
					push.setPushTime(new Date());

					push.setPushPerson(PlatFormUtil.getSessionUser().getUserName());
					commonService.saveOrUpdate(push);
					message = "微信内容推送失败,错误信息："+e.getMessage();
					systemService.addLog(message, Globals.Log_Leavel_INFO,
							Globals.Log_Type_DEL);
					j.accumulate("isSuccess", false);
				}
			} else {
				message = "微信内容推送失败";
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_DEL);
				j.accumulate("isSuccess", false);

			}

		} else {
			message = "微信内容推送失败";
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_DEL);
			j.accumulate("isSuccess", false);
		}

		j.accumulate("msg", message);
		j.accumulate("toUrl", "weChatPushController.do?weChatPush");
		String str = j.toString();
		return str;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
