package com.leimingtech.core.util;

import com.leimingtech.core.base.ApplicationContextUtil;
import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.entity.*;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.UserService;
import com.leimingtech.core.service.WaterMarkServiceI;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jodd.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

/**
 * 平台工具类
 * 
 * @author liuzhen 2014年7月10日 18:42:31
 * 
 */
public class PlatFormUtil {
	
	/**
	 * 获取当前session中管理员
	 * 
	 * @return
	 */
	public static final TSUser getSessionUser() {
		HttpSession session = ContextHolderUtils.getSession();
		if(ClientManager.getInstance().getClient(session.getId()) != null) {
			return ClientManager.getInstance().getClient(session.getId()).getUser();
		}
		return null;
	}
	
	/**
	 * 获取当前session中站点
	 * 
	 * @return
	 */
	public static final SiteEntity getSessionSite() {
		HttpSession session = ContextHolderUtils.getSession();
		if(ClientManager.getInstance().getClient(session.getId()) != null) {
			return ClientManager.getInstance().getClient(session.getId()).getSite();
		}
		return null;
	}
	
	/**
	 * 获取当前session中站点
	 * 
	 * @param session
	 *            ContextHolderUtils.getSession()
	 * @return
	 */
	public static final SiteEntity getSessionSite(HttpSession session) {
		if(ClientManager.getInstance().getClient(session.getId()) != null) {
			return ClientManager.getInstance().getClient(session.getId()).getSite();
		}
		return null;
	}
	
	/**
	 * 获取当前session中站点id
	 * 
	 * @return
	 */
	public static final String getSessionSiteId() {
		SiteEntity site = getSessionSite();
		if(site != null) {
			return site.getId();
		}
		
		return "0";
		
	}
	
	/**
	 * 获取前端session中的站点
	 * 
	 * @return
	 */
	public static final SiteEntity getFrontSessionSite() {
		HttpSession session = ContextHolderUtils.getSession();
		ClientManager clientMng = ClientManager.getInstance();
		Client client = clientMng.getClient("front" + session.getId());
		SiteEntity site = null;
		if(client != null) {
			site = clientMng.getClient("front" + session.getId()).getSite();
		}
		return site;
	}
	
	/**
	 * 获取前端session中的站点id
	 * 
	 * @return
	 */
	public static final String getFrontSessionSiteId() {
		SiteEntity site = getFrontSessionSite();
		String siteid = null;
		if(site != null) {
			siteid = site.getId();
		}
		return siteid;
	}
	
	/**
	 * 获取站点域名
	 * 
	 * @return
	 */
	public static final String getCurrentSitedomain() {
		SiteEntity site = getSessionSite();
		if(site != null && StringUtils.isNotEmpty(site.getDomain())) {
			String domain=site.getProtocol()+site.getDomain();
			return domain;
		}
		return "";
	}
	
	/**
	 * 是否开启图片打水印
	 * 
	 * @return
	 */
	public static final Boolean isOpenWatermark() {
		Map<String , String> mCache = PfConfigEntity.pfconfigCache;
		String watermark = mCache.get("watermark");
		if(StringUtils.isNotEmpty(watermark) && "1".equals(watermark)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取水印图片路径
	 * 
	 * @return
	 */
	public static final Map<String , Object> findWatermarkImageUrl(HttpSession session) {
		if(isOpenWatermark()) {
			
			WaterMarkServiceI waterMarkService = (WaterMarkServiceI)getInterface("waterMarkService");
			List<WaterMarkEntity> waterMarks = waterMarkService.getList(WaterMarkEntity.class);
			
			if(waterMarks != null && waterMarks.size() > 0) {
				WaterMarkEntity waterMark = waterMarks.get(0);
				
				String separator = System.getProperty("file.separator");
				
				String imagepath = waterMark.getImagepath();
				
				if(StringUtils.isNotEmpty(imagepath)) {
					imagepath = imagepath.replaceAll("/" , separator + separator);
					String sitePath = CmsConstants.getSitePath(session);
					
					if(new File(sitePath + imagepath).exists()) {
						Map<String , Object> m = new HashMap<String , Object>();
						
						m.put("imagePath" , sitePath + imagepath);
						m.put("position" , waterMark.getPosition());
						
						return m;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 从spring容器中获取systemservice接口
	 * 
	 * @return
	 */
	public static SystemService getSystemService() {
		ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext)ApplicationContextUtil.getContext();
		SystemService systemService = (SystemService)applicationContext.getBean("systemService");
		return systemService;
	}
	
	/**
	 * 通过注入的接口名从spring容器中获取接口实例
	 * 
	 * @param InterfaceName
	 *            注入的接口名
	 * @return
	 */
	public static Object getInterface(String InterfaceName) {
		ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext)ApplicationContextUtil.getContext();
		return applicationContext.getBean(InterfaceName);
	}
	
	/**
	 * 获取权限集合
	 * 
	 * @param rUsers
	 *            用户所拥有的角色
	 * 
	 * @return privurl 用户所拥有的权限集合
	 */
	public static void getFunctionSet(List<TSRoleUser> rUsers) {
		HttpSession session = ContextHolderUtils.getSession();
		UserService userService = (UserService)getInterface("userService");
		Client client = ClientManager.getInstance().getClient(session.getId());
		if(client == null) {
			client = ClientManager.getInstance().getClient(ContextHolderUtils.getRequest().getParameter("sessionId"));
		}
		
		Set<String> privurl = null;
		if(client != null && client.getPrivurl() != null) {
			privurl = (Set<String>)client.getPrivurl();
		}
		if(privurl == null) {
			privurl = new HashSet<String>();
			for(TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				List<TSRoleFunction> roleFunctionList = ResourceUtil.getSessionTSRoleFunction(role.getId());
				if(roleFunctionList == null || roleFunctionList.size() <= 0) {
					roleFunctionList = userService.findByProperty(TSRoleFunction.class , "TSRole.id" , role.getId());
					client.getRoleFunctionMap().put(role.getId() , roleFunctionList);
				} else {
					if(roleFunctionList.get(0).getId() == null) {
						roleFunctionList = userService.findByProperty(TSRoleFunction.class , "TSRole.id" , role.getId());
					}
				}
				for(TSRoleFunction roleFunction : roleFunctionList) {
					TSFunction function = roleFunction.getTSFunction();
					String urls = function.getPrivurl();// 获得该菜单拥有的url权限
					if(StringUtil.isNotEmpty(urls)) {
						String [] url = urls.split(",");
						for(int i = 0 ; i < url.length ; i++) {
							if(StringUtil.isNotEmpty(url[i])) {
								privurl.add(url[i]);// 将该url存入set
							}
						}
					}
					
					if(StringUtils.isNotEmpty(function.getFunctionUrl())) {
						privurl.add(function.getFunctionUrl());
					}
					
					if(StringUtils.isNotEmpty(roleFunction.getOperation())) {
						String [] operationurls = roleFunction.getOperation().split(",");
						for(int j = 0 ; j < operationurls.length ; j++) {
							if(StringUtil.isNotEmpty(operationurls[j])) {
								privurl.add(operationurls[j]);
							}
						}
					}
				}
			}
			client.setPrivurl(privurl);
		}
	}
	
	/**
	 * 获取权限的map
	 * 
	 * @param user
	 * @return
	 */
	public static Map<Integer , List<TSFunction>> getFunctionMap(TSUser user) {
		Map<Integer , List<TSFunction>> functionMap = new HashMap<Integer , List<TSFunction>>();
		Map<String , TSFunction> loginActionlist = getUserFunction(user);
		if(loginActionlist.size() > 0) {
			Collection<TSFunction> allFunctions = loginActionlist.values();
			functionMap.put(0 , new ArrayList<TSFunction>());
			for(TSFunction function : allFunctions) {
				// if (!functionMap.containsKey(function.getFunctionLevel() +
				// 0)) {
				// functionMap.put(function.getFunctionLevel() + 0,
				// new ArrayList<TSFunction>());
				// }
				
				if(function != null && function.getFunctionLevel() == 0) {
					functionMap.get(function.getFunctionLevel() + 0).add(function);
				}
			}
			// 菜单栏排序
			Collection<List<TSFunction>> c = functionMap.values();
			for(List<TSFunction> list : c) {
				Collections.sort(list , new NumberComparator());
			}
		}
		return functionMap;
	}
	
	/**
	 * 获取用户菜单列表
	 * 
	 * @param user
	 * @return
	 */
	public static Map<String , TSFunction> getUserFunction(TSUser user) {
		UserService userService = (UserService)getInterface("userService");
		Map<String , TSFunction> loginActionlist = new HashMap<String , TSFunction>();
		List<TSRoleUser> rUsers = userService.findByProperty(TSRoleUser.class , "TSUser.id" , user.getId());
		for(TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			List<TSRoleFunction> roleFunctionList = ResourceUtil.getSessionTSRoleFunction(role.getId());
			if(roleFunctionList == null || roleFunctionList.size() <= 0) {
				roleFunctionList = userService.findByProperty(TSRoleFunction.class , "TSRole.id" , role.getId());
				ResourceUtil.setSessionTSRoleFunction(role.getId() , roleFunctionList);
			} else {
				TSRoleFunction roleFunction = roleFunctionList.get(0);
				if(roleFunction.getId() == null) {
					roleFunctionList = userService.findByProperty(TSRoleFunction.class , "TSRole.id" , role.getId());
					ResourceUtil.setSessionTSRoleFunction(role.getId() , roleFunctionList);
				}
			}
			for(TSRoleFunction roleFunction : roleFunctionList) {
				TSFunction function = roleFunction.getTSFunction();
				loginActionlist.put(function.getId() , function);
			}
		}
		return loginActionlist;
	}
	
	/**
	 * 根据request获取完整的域名，如http://www.aaa.com:8080 如果端口为80则为：http://www.aaa.com
	 * 
	 * @return
	 */
	public static String getDomail() {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String port = "" + request.getServerPort();
		if("80".equals(port)) {
			port = "";
		} else {
			port = ":" + port;
		}
		String contextPath = request.getContextPath();
		if("/".equals(contextPath)) {
			contextPath = "";
		}
		
		String domain = request.getScheme()+"://" + ("localhost".equals(request.getServerName()) ? "127.0.0.1" : request.getServerName()) + port + contextPath;
		return domain;
	}

	/**
	 * 传入模板返回静态页面
	 *
	 * @param templatepath
	 *            模板路径
	 * @param data
	 *            注入模板数据
	 * @return 生成后的内容
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static String generateFile(String templatepath , Map<String , Object> data) throws TemplateException , IOException {
		if(data == null) {
			data = new HashMap<String , Object>();
		}

		String template=FileUtil.readText(new File(templatepath), "UTF-8");

		StringWriter result = new StringWriter();
		try {
			Template t = new Template("name", new StringReader(template), new Configuration());
			t.process(data, result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	/**
	 * 当前用户所拥有的角色
	 * 
	 * @return
	 */
	public static final List<TSRole> getRoleUser() {
		List<TSRole> roleList = new ArrayList<TSRole>();
		UserService userService = (UserService)getInterface("userService");
		String userId = PlatFormUtil.getSessionUser().getId();
		List<TSRoleUser> roleUserList = userService.findByProperty(TSRoleUser.class , "TSUser.id" , userId);
		for(TSRoleUser roleUser : roleUserList) {
			TSRole role = userService.get(TSRole.class , roleUser.getTSRole().getId());
			roleList.add(role);
		}
		return roleList;
	}

	@Test
	public void test() {
		try {
			System.out.println(PlatFormUtil.generateFile("D:\\project\\java\\lmcms\\lmcms-framework\\WebRoot\\wwwroot\\www\\template\\footer.html" , null));
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
