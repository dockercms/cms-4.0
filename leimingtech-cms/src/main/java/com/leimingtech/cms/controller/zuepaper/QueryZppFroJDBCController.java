package com.leimingtech.cms.controller.zuepaper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.entity.ZppEntity;
import com.leimingtech.core.common.ContentClassify;
import com.leimingtech.core.common.ContentStatus;
import com.leimingtech.core.entity.ArticleEntity;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.service.ContentCatServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.SystemService;

/**
 * 数字报
 * @author zhangxq
 *
 */
@Controller
@RequestMapping("/queryZppFroJDBCController")
public class QueryZppFroJDBCController {
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private ContentCatServiceI contentCatService;
	@Autowired
	private ContentsServiceI contentsService;
	
	@RequestMapping(params = "queryZppFroJDBC")
	public ModelAndView queryZppForJdbc(HttpServletRequest request){
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		int pageCount = 0;
		Map<String, Object> m = new HashMap<String, Object>();;
		
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 15 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3307/lm_maven_cms?useUnicode=true&characterEncoding=UTF-8";
			conn = DriverManager.getConnection(url,"root","root");
			stm = conn.createStatement();
			String sql = "select papername,title,author,pubdate,editon,channel,newsid from zuepaper limit " +((pageNo-1)*pageSize)+","+pageSize;
			rs = stm.executeQuery(sql);
			List<ZppEntity> modelsList = new ArrayList<ZppEntity>();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			while(rs.next()){
				ZppEntity ze = new ZppEntity();
				
				ze.setPapername(rs.getString(1));//报纸名称
				ze.setTitle(rs.getString(2)); 	 //标题
				ze.setAuthor(rs.getString(3));	 //作者
				ze.setPubdate(dateFormat.format(rs.getDate(4))); 	 //发布时间
				ze.setEditon(rs.getString(5));  //版本号
				ze.setChannel(rs.getString(6));  //所在板块
				ze.setNewsid(rs.getString(7));      //新闻id
				
				modelsList.add(ze);
			}
			
			String sql1 = "select count(*) from zuepaper";
			
			rs = stm.executeQuery(sql1);
			
			while(rs.next()){
				pageCount = rs.getInt(1)/pageSize;
			}
			
			m.put("modelsList", modelsList);
			m.put("pageNo",pageNo);
			m.put("pageSize", pageSize);
			m.put("pageCount", pageCount);
			m.put("actionUrl", "queryZppFroJDBCController.do?queryZppFroJDBC");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				stm.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("cms/zuepaper/zuepaperList",m);
	}
	
	@RequestMapping(params = "mobiles")
	public ModelAndView mobiles(HttpServletRequest request){
		String ids = request.getParameter("ids");
		String toType = request.getParameter("toType");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("ids", ids);
		m.put("toType", toType);
		return new ModelAndView("cms/zuepaper/mobileZuepaper", m);
	}
	
	@RequestMapping(params = "toContents")
	@ResponseBody
	public String toContents(HttpServletRequest request){	
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		JSONObject j = new JSONObject();
		String ids = request.getParameter("ids");
		String contentCatId = request.getParameter("funVal");
		String[] zuepaperids = ids.split(",");
		String message = "";
		ContentCatEntity catalog = contentCatService.getEntity(String.valueOf(contentCatId));
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3309/ucm?useUnicode=true&characterEncoding=UTF-8";
			conn = DriverManager.getConnection(url,"root","root");
			stm = conn.createStatement();
			for (String zuepaperid : zuepaperids) {
				message = "复制内容到 [ " + catalog.getName() + " ] 成功";
				String sql = "select newsid,subtitle,title,author,pubdate,allcontent from zuepaper where newsid=" + zuepaperid;
				rs = stm.executeQuery(sql);
				while(rs.next()){
					ContentsEntity cte = new ContentsEntity();
					
					cte.setSubtitle(rs.getString(2));
					cte.setTitle(rs.getString(3));
					cte.setAuthor(rs.getString(4));
//					cte.setPublished(rs.getDate(5));
					cte.setCatid(catalog.getId());
					cte.setModelid(catalog.getJsonid());
					cte.setPathids(catalog.getParentids()+""+catalog.getId());
					cte.setClassify(ContentClassify.CONTENT_ARTICLE);
					cte.setSiteid("1");
					cte.setConstants(ContentStatus.CONTENT_COMMITTED);
					cte.setCatName(catalog.getName());
					contentsService.saveOrUpdate(cte);
					
					ArticleEntity ae = new ArticleEntity();
					ae.setContent(rs.getString(6));
					ae.setContent(cte.getId());
					System.out.println(cte.getId());
					contentsService.saveOrUpdate(ae);
					System.out.println(ae.getId());
				}
			}
			j.accumulate("isSuccess", true);
			j.accumulate("toUrl", "queryZppFroJDBCController.do?queryZppFroJDBC");
			j.accumulate("msg", message);
		}catch (Exception e) {
			e.printStackTrace();
			j.accumulate("isSuccess", false);
		}finally{
			try {
				rs.close();
				stm.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		String str = j.toString();
		return str;
	}
}
