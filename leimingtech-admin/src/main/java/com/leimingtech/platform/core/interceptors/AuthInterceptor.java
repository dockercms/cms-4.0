package com.leimingtech.platform.core.interceptors;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TSFunction;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.util.HttpUtil;
import com.leimingtech.core.util.IpUtil;
import com.leimingtech.core.util.ResourceUtil;
import com.leimingtech.core.util.oConvertUtils;
import com.leimingtech.platform.service.MenuInitService;
import com.leimingtech.platform.service.function.FunctionServiceI;
import com.leimingtech.platform.service.operation.OperationServiceI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

/**
 * 权限拦截器
 * 
 * @author
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {

	@Autowired
	private MenuInitService menuInitService;
	/** 站点管理接口I */
	@Autowired
	private SiteServiceI siteService;
	/** 菜单管理接口 */
	@Autowired
	private FunctionServiceI functionService;
	/** 功能管理接口 */
	@Autowired
	private OperationServiceI operationService;

	private List<String> excludeUrls;
	private static List<TSFunction> functionList;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception)
			throws Exception {
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		String requestPath = ResourceUtil.getRequestPath(request);// 用户访问的资源地址
		HttpSession session = ContextHolderUtils.getSession();
		session.setAttribute("contextpath", Globals.CONTEXTPATH);
//		ThreadContextHolder.setHttpRequest(request);
//		ThreadContextHolder.setHttpResponse(response);
//		ThreadContextHolder.getSessionContext().setSession(session);

		ClientManager clientManager = ClientManager.getInstance();

		Client client = setDefaultSite(request, session, clientManager);// 设置默认站点

		if (requestPath.indexOf("member/") != -1) {
			String name = HttpUtil.getCookieValue(request, "loginname");
			if (StringUtils.isEmpty(name)) {
				name = request.getParameter("username");
			}
			request.setAttribute("frontusername", name);
			if (excludeUrls.contains(requestPath)) {
				return true;
			}
			MemberEntity member = null;

			Client Frontclient = clientManager.getClient("front" + session.getId());

			String toFrontSite = request.getParameter("toFrontSite");// 获取url中站点id，通过传递此参数可以切换站点

			if (Frontclient == null) {
				Frontclient = new Client();
				Frontclient.setSite(siteService.getDefaultMainSite());
				Frontclient.setIp(IpUtil.getIpAddr(request));
			}

			SiteEntity site = Frontclient.getSite();

			if (StringUtils.isNotEmpty(toFrontSite)
					&& !toFrontSite.equals(site.getId())) {
				SiteEntity toNewssite = siteService.getSite(toFrontSite);// 即将跳转的新站点
				if (toNewssite != null) {
					site = toNewssite;
					Frontclient.setSite(site);
				}
			}

			clientManager.addClinet("front"+session.getId(), Frontclient);

			if (Frontclient != null) {
				member = Frontclient.getMember();
			}
			if (member == null) {
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + request.getRequestURI()
						+ "?" + request.getQueryString();
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				StringBuilder builder = new StringBuilder();
				builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
				// builder.append("alert(\"本系统需要您登录后才能使用\");");
				builder.append("window.top.location.href=\"");
				builder.append("loginAct.do?login");
				builder.append("&addr=");
				builder.append(basePath);
				builder.append("\";</script>");
				out.print(builder.toString());
				out.close();
				return false;
			}
			return true;
		}else if(requestPath.indexOf("front/") != -1){
			Client Frontclient = clientManager.getClient("front" + session.getId());

			String toFrontSite = request.getParameter("toFrontSite");// 获取url中站点id，通过传递此参数可以切换站点

			if (Frontclient == null) {
				Frontclient = new Client();
				Frontclient.setSite(siteService.getDefaultMainSite());
				Frontclient.setIp(IpUtil.getIpAddr(request));
			}

			SiteEntity site = Frontclient.getSite();

			if (StringUtils.isNotEmpty(toFrontSite)
					&& !toFrontSite.equals(site.getId())) {
				SiteEntity toNewssite = siteService.getSite(toFrontSite);// 即将跳转的新站点
				if (toNewssite != null) {
					site = toNewssite;
					Frontclient.setSite(site);
				}
			}

			clientManager.addClinet("front"+session.getId(), Frontclient);
			
		
			return true;
		}else if (requestPath.indexOf("docs/") != -1
				
				|| excludeUrls.contains(requestPath)) {
			return true;
		} else if (client != null && client.getUser() != null) {
			// 新的权限控制
			Set<String> privurl = (Set<String>) client.getPrivurl();//
			if (privurl == null) {
				return true;
			}
			if (privurl.contains(requestPath)) {
				return true;
			} 
			
			if (!functionService.checkUrlExist(requestPath)
					&& !operationService.checkUrlExist(requestPath)) {
				return true;
			}

			Boolean isalert = Boolean
					.valueOf(request.getParameter("alert"));
			Boolean ajax = Boolean
					.valueOf(request.getParameter("ajax"));
			response.sendRedirect("loginAction.do?noAuth&alert=" + isalert+"&ajax="+ajax);
			return false;

		} else {
			if (!functionService.checkUrlExist(requestPath)
					&& !operationService.checkUrlExist(requestPath)) {
				return true;
			}
			
			forward(request, response);
			return false;
		}

	}

	/**
	 * 设置默认站点
	 * 
	 * @param request
	 * @param session
	 * @param clientManager
	 * @return
	 */
	private Client setDefaultSite(HttpServletRequest request,
			HttpSession session, ClientManager clientManager) {

		Client client = clientManager.getClient(session.getId());

		String toSite = request.getParameter("toSite");// 获取url中站点id，通过传递此参数可以切换站点

		if (client == null) {
			client = new Client();
		}
		if(client.getSite()==null){
			client.setSite(siteService.getDefaultMainSite());
			client.setIp(IpUtil.getIpAddr(request));
		}

		SiteEntity site = client.getSite();

		if (StringUtils.isNotEmpty(toSite) && site!=null
				&& !toSite.equals(site.getId())) {
			SiteEntity toNewssite = siteService.getSite(toSite);// 即将跳转的新站点
			if (toNewssite != null) {
				site = toNewssite;
				client.setSite(site);
			}
		}

		
		if (site != null) {
			session.setAttribute("siteid", site.getId());
		}

		clientManager.addClinet(session.getId(), client);
		return client;
	}

	private boolean hasMenuAuth(HttpServletRequest request) {
		String requestPath = ResourceUtil.getRequestPath(request);// 用户访问的资源地址
		// 是否是功能表中管理的url
		boolean bMgrUrl = false;
		if (functionList == null) {
			functionList = menuInitService.loadAll(TSFunction.class);
		}
		for (TSFunction function : functionList) {
			if (function.getFunctionUrl() != null
					&& function.getFunctionUrl().startsWith(requestPath)) {
				bMgrUrl = true;
				break;
			}
		}
		if (!bMgrUrl) {
			return true;
		}

		String funcid = oConvertUtils.getString(request
				.getParameter("clickFunctionId"));
		// if(!bMgrUrl &&
		// (requestPath.indexOf("loginController.do")!=-1||funcid.length()==0)){
		if (!bMgrUrl
				&& (requestPath.indexOf("loginAction.do") != -1 || funcid
						.length() == 0)) {
			return true;
		}
		String userid = ClientManager.getInstance()
				.getClient(ContextHolderUtils.getSession().getId()).getUser()
				.getId();
		// requestPath=requestPath.substring(0, requestPath.indexOf("?")+1);
		String sql = "SELECT DISTINCT f.id FROM t_s_function f,cms_role_function  rf,cms_role_user ru "
				+ " WHERE f.id=rf.functionid AND rf.roleid=ru.roleid AND "
				+ "ru.userid='"
				+ userid
				+ "' AND f.functionurl like '"
				+ requestPath + "%'";
		List list = this.menuInitService.findListbySql(sql);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 转发
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "forword")
	public ModelAndView forword(HttpServletRequest request) {
		// return new ModelAndView(new
		// RedirectView("loginController.do?login"));
		return new ModelAndView(new RedirectView("loginAction.do?login"));
	}

	private void forward(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = "/lmPage/main/login.html";
		request.getRequestDispatcher(path).forward(request, response);
	}

}
