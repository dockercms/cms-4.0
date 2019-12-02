package com.leimingtech.platform.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.leimingtech.core.entity.TSFunction;



public class MenuTag extends TagSupport
{

    public MenuTag()
    {
    }

    public int doStartTag()
        throws JspTagException
    {
        return 6;
    }

    public int doEndTag()
        throws JspTagException
    {
        try
        {
            JspWriter out = pageContext.getOut();
            out.print(end().toString());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return 6;
    }

    public StringBuffer end()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<div id=\"nav\" class=\"easyui-accordion\" fit=\"true\" border=\"false\">");
        sb.append(getEasyuiMultistageTree(menuFun));
        sb.append("</div>");
        return sb;
    }
    /**
	 * 拼装EASYUI 多级 菜单  下级菜单为树形
	 * 
	 * @param pFunctions
	 * @param functions
	 * @return
	 */
	public static String getEasyuiMultistageTree(Map<Integer, List<TSFunction>> map) {
		StringBuffer menuString = new StringBuffer();
		List<TSFunction> list = map.get(0);
		for (TSFunction function : list) {
			menuString.append("<ul class=\"page-sidebar-menu\"><li class=\"\"><a href=\"javascript:;\"><i class=\"icon-file-text\" style=\"width: 1em;margin-right: 4px;\"></i><span class=\"title\">" + function.getFunctionName() + "</span><span class=\"arrow \"></span></a>");
			int submenusize = function.getTSFunctions().size();
			if (submenusize == 0) {
				menuString.append("</ul>");
			}
			if (submenusize > 0) {
				menuString.append("<ul class=\"sub-menu\">");
			}
			menuString.append(getChildOfTree(function,1,map));
			if (submenusize > 0) {
				menuString.append("</ul></ul>");
			}
		}
		return menuString.toString();
	}
	/**
	 * 获取树形菜单
	 * @param parent
	 * @param level
	 * @param map
	 * @return
	 */
	private static String getChildOfTree(TSFunction parent,int level,Map<Integer, List<TSFunction>> map){
		StringBuffer menuString = new StringBuffer();
		List<TSFunction> list = map.get(level);
		for (TSFunction function : list) {
			if (function.getTSFunction().getId().equals(parent.getId())){
				if(function.getTSFunctions().size()==0||!map.containsKey(level+1)){
					menuString.append(getLeafOfTree(function));
				}else if(map.containsKey(level+1)){
					menuString.append("<li><a href=\"javascript:;\"><i class=\"icon-user\"></i>"+function.getFunctionName()+"<span class=\"arrow\"></span></a>");
					menuString.append("<ul class=\"sub-menu\">");
					menuString.append(getChildOfTree(function,level+1,map));
					menuString.append("</ul></li>");
				}
			}
		}
		return menuString.toString();
	}
	/**
	 * 获取叶子节点  ------树形菜单的叶子节点
	 * @param function
	 * @return
	 */
	private static String getLeafOfTree(TSFunction function){
		StringBuffer menuString = new StringBuffer();
		String icon = "folder";
		if (StringUtils.isNotEmpty(function.getIconclass())) {
			icon = function.getIconclass();
		}
		menuString.append("<li ><a href=\"" + function.getFunctionUrl() + "&clickFunctionId=\"" + function.getId() + "\"\">" + function.getFunctionName() + "</a></li>");
		return menuString.toString();
	}
	
    public void setMenuFun(Map menuFun)
    {
        this.menuFun = menuFun;
    }

    protected Map menuFun;
}
