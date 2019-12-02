package com.leimingtech.core.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.NumberComparator;
import com.leimingtech.core.entity.TSFunction;


/**
 * metro样式的菜单
 * 
 * @author :linjm linjianmao@gmail.com
 * @version :2014-4-23上午11:43:37
 **/
public class MetroMenu {

	/**
	 * 头部横向菜单
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static String getHorezantalMenu(List<TSFunction> functionList) throws Exception {
		StringBuilder sb = new StringBuilder();
		TSFunction function = null;
		// 只获取一级菜单 默认取前7个
		int index = 0;
		for (int i = 0; i < functionList.size(); i++) {
			function = functionList.get(i);
			if (function != null) {
				if (function.getTSFunctions() == null || function.getTSFunctions().size() == 0) {
					// 没有子节点的菜单
					sb.append("<li ><a href=\"javaScript:void(0);\" onclick=\"treeMenu('" + function.getFunctionUrl() + "','"
							+ function.getPrivurl() + "')\">" + function.getFunctionName() + "</a><span></span></li>\n");
					index++;
				}else if(function.getTSFunctions().size() > 0){// 有子节点
					getHorezantalNode(sb, function);
					index++;
				}
//				if (index == 8)
//					break;
			}
		}
		return sb.toString();
	}

	/**
	 * 头部横向子菜单
	 * 
	 * @param sb
	 * @param function
	 */
	private static void getHorezantalNode(StringBuilder sb, TSFunction function) {
		// ----------------一级节点开始----------------
		sb.append("<li >\n");
		sb.append("<a data-toggle=\"dropdown\" class=\"dropdown-toggle\" href=\"javascript:;\">\n");
		sb.append(function.getFunctionName());
		sb.append("<span class=\"arrow\"></span>\n");
		sb.append("</a>\n");
		// ----------------二级节点开始----------------
		sb.append("<ul style='min-width:110px;' class=\"dropdown-menu\">\n");
		// ==============子节点开始===============
		// Hibernate 查询方法
		List<TSFunction> childFunList = function.getTSFunctions();
		if (childFunList.size() > 0) {
			TSFunction childfuntion = null;
			HttpSession session = ContextHolderUtils.getSession();

			Client client = ClientManager.getInstance().getClient(session.getId());
			if (client == null) {
				client = ClientManager.getInstance().getClient(ContextHolderUtils.getRequest().getParameter("sessionId"));
			}

			Set<String> privurl = (Set<String>) client.getPrivurl();
			for (int j = 0; j < childFunList.size(); j++) {
				childfuntion = childFunList.get(j);// horizantal(lefUrl,
													// rightUrl)
				if (privurl.contains(childfuntion.getFunctionUrl())) {
					if("#".equals(childfuntion.getFunctionUrl())){
						Map<String, TSFunction> functionMap = PlatFormUtil.getUserFunction(PlatFormUtil.getSessionUser());
						if (functionMap.get(childfuntion.getId()) == null) {
							continue;
						}
					}
					if (childfuntion.getTSFunctions() == null || childfuntion.getTSFunctions().size() == 0) {
						// 没有子节点的菜单
						sb.append("<li><a href=\"javaScript:void(0);\" onclick=\"horizantal('loginAction.do?LeftMenu&topid=" + function.getId()
								+ "','" + childfuntion.getFunctionUrl() + "')\">" + childfuntion.getFunctionName() + "</a></li>\n");
					}else if(childfuntion.getTSFunctions().size() > 0){// 有子节点
						getHorezantalChildNode(sb, childfuntion);
					}
					
				}
			}
		}
		sb.append("</ul>\n");
		// ----------------二级节点结束----------------
		sb.append("<b class=\"caret-out\"></b>\n");
		sb.append("</li>\n");
	}
	
	/**
	 * 头部横向子菜单(二级以下)
	 * 
	 * @param sb
	 * @param function
	 */
	private static void getHorezantalChildNode(StringBuilder sb, TSFunction function) {
		// ----------------子级节点开始----------------
		sb.append("<li class=\"dropdown-submenu\">\n");
		sb.append("<a tabindex=\"-1\" href=\"javascript:;\">\n");
		sb.append(function.getFunctionName());
		sb.append("<span class=\"arrow\"></span>\n");
		sb.append("</a>\n");
		// ----------------子二级节点开始----------------
		sb.append("<ul style='min-width:110px;' class=\"dropdown-menu\">\n");
		// ==============子节点开始===============
		// Hibernate 查询方法
		List<TSFunction> childFunList = function.getTSFunctions();
		if (childFunList.size() > 0) {
			Collections.sort(childFunList, new NumberComparator());
			TSFunction childfuntion = null;
			HttpSession session = ContextHolderUtils.getSession();

			Client client = ClientManager.getInstance().getClient(session.getId());
			if (client == null) {
				client = ClientManager.getInstance().getClient(ContextHolderUtils.getRequest().getParameter("sessionId"));
			}

			Set<String> privurl = (Set<String>) client.getPrivurl();
			for (int j = 0; j < childFunList.size(); j++) {
				childfuntion = childFunList.get(j);// horizantal(lefUrl, rightUrl)
				if (privurl.contains(childfuntion.getFunctionUrl())) {
					if("#".equals(childfuntion.getFunctionUrl())){
						Map<String, TSFunction> functionMap = PlatFormUtil.getUserFunction(PlatFormUtil.getSessionUser());
						if (functionMap.get(childfuntion.getId()) == null) {
							continue;
						}
					}
					if (childfuntion.getTSFunctions() == null || childfuntion.getTSFunctions().size() == 0) {
						// 没有子节点的菜单
						sb.append("<li><a tabindex=\"-1\" href=\"javaScript:void(0);\" onclick=\"horizantal('loginAction.do?LeftMenu&topid=" + function.getId()
								+ "','" + childfuntion.getFunctionUrl() + "')\">" + childfuntion.getFunctionName() + "</a></li>\n");
					}else if(childfuntion.getTSFunctions().size() > 0){// 有子节点
						getHorezantalChildNode(sb, childfuntion);
					}
					
				}
			}
		}
		sb.append("</ul>\n");
		sb.append("<b class=\"caret-out\"></b>\n");
		sb.append("</li>\n");
	}

	/**
	 * 左边菜单拼装 方法
	 * 
	 * @return menuString
	 */
	public static String getMenu(List<TSFunction> functionList) {
		StringBuilder sb = new StringBuilder();
		TSFunction function = null;
		for (int i = 0; i < functionList.size(); i++) {
			function = functionList.get(i);
			if (function != null) {
				// 有子节点
				if (function.getTSFunctions().size() > 0) {
					getMenuNode(sb, function);
				} else if (function.getTSFunctions().size() == 0 && function.getFunctionLevel() == 0) {
					// 没有子节点的菜单
					// ----------------一级节点开始----------------
					sb.append("<li class=\"\">");
					sb.append("<a href=\"javascript:;\">");
					// 一级菜单名称
					sb.append("<i class=" + function.getIconclass() + "></i>");
					sb.append("<span class=\"title\">" + function.getFunctionName() + "</span>");
					sb.append("<span class=\"arrow\"></span>");
					sb.append("</a>");
					sb.append("</li>");
					// ----------------一级节点结束----------------
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 左边有子节点大菜单 拼装菜单节点 （目前只支持二级菜单）
	 * 
	 * @param sb
	 * @param function
	 */
	private static void getMenuNode(StringBuilder sb, TSFunction function) {
		// ----------------一级节点开始----------------
		sb.append("<li class=\"\">");
		sb.append("<a href=\"javascript:;\">");
		// 一级菜单名称
		sb.append("<i class=" + function.getIconclass() + "></i>");// 可根据图标ID查到图标对应的CSS
		sb.append("<span class=\"title\">" + function.getFunctionName() + "</span>");
		sb.append("<span ></span>");
		sb.append("<span class=\"arrow\"></span>");
		sb.append("</a>");

		// ==============子节点开始===============
		// Hibernate 查询方法
		List<TSFunction> childFunList = function.getTSFunctions();
		// jdbc查询方法
		// List<TSFunction> childFunList =
		// systemService.findHql("from TSFunction where parentfunctionid='"+function.getId()+"' order by functionorder ");
		if (childFunList.size() > 0) {
			sb.append("<ul class=\"sub-menu\">");
			TSFunction childfuntion = null;
			for (int j = 0; j < childFunList.size(); j++) {
				childfuntion = childFunList.get(j);
				// 子节点li
				sb.append("<li >");
				sb.append("<a href=\"javaScript:void(0);\" onclick=\"changePage('" + childfuntion.getFunctionUrl() + "')\"><i class="
						+ childfuntion.getIconclass() + "></i>" + childfuntion.getFunctionName() + "</a>");
				sb.append("</li>");
			}
			sb.append("</ul>");
		}
		// ===================子节点结束===================
		sb.append("</li>");
		// ----------------一级节点结束----------------
	}

}
