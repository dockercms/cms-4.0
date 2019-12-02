package com.leimingtech.platform.controller.doc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.TagCreator;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.util.SystemPath;
import com.leimingtech.platform.entity.doc.DocEnEntity;
import com.leimingtech.platform.entity.doc.DocEnProEntity;
import com.leimingtech.platform.entity.doc.DocEnRefEntity;
import com.leimingtech.platform.entity.doc.DocEntity;
import com.leimingtech.platform.entity.doc.DocParamEntity;
import com.leimingtech.platform.entity.doc.DocReturnValueEntity;
import com.leimingtech.platform.entity.doc.DocTypeEntity;
import com.leimingtech.platform.service.doc.DocEnServiceI;
import com.leimingtech.platform.service.doc.DocParamServiceI;
import com.leimingtech.platform.service.doc.DocServiceI;
import com.leimingtech.platform.service.doc.DocTypeServiceI;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 文档数据调用
 * 
 * @author liuzhen 2014年6月11日 16:10:24
 * 
 */
@Controller
@RequestMapping("/docs/docAct")
public class DocAction extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DocAction.class);

	@Autowired
	private DocServiceI docService;
	@Autowired
	private DocTypeServiceI docTypeService;
	@Autowired
	private DocParamServiceI docParamService;
	@Autowired
	private DocEnServiceI docEnService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 文档首页
	 * 
	 * @param reqeust
	 * @return
	 */
	@RequestMapping(params = "index")
	public ModelAndView index(HttpServletRequest reqeust) {
		String platform = reqeust.getParameter("platform");
		CriteriaQuery cq = new CriteriaQuery(DocTypeEntity.class);
		cq.eq("status", 1);
		cq.addOrder("sort", SortDirection.desc);
		cq.add();

		List<DocTypeEntity> docTypeList = docTypeService.getListByCriteriaQuery(cq, false);

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("docTypeList", docTypeList);
		m.put("platform", platform);
		return new ModelAndView("platform/doc/index", m);
	}

	/**
	 * 预览文档类型下的文档
	 * 
	 * @param reqeust
	 * @return
	 */
	@RequestMapping(params = "type")
	public ModelAndView type(HttpServletRequest request, String id) {


		DocTypeEntity doctype = docTypeService.getEntity(DocTypeEntity.class, id);
		

		List<DocEntity> docList = docService.getListRedDoc(id);//根据id查看文档

		Map<String, Object> m = new HashMap<String, Object>();
		m.putAll(index(request).getModel());
		m.put("doctype", doctype);
		m.put("docList", docList);
		return new ModelAndView("platform/doc/doc_type", m);
	}

	/**
	 * 预览标签文档
	 * 
	 * @param reqeust
	 * @return
	 */
	@RequestMapping(params = "doctag")
	public ModelAndView doctag(HttpServletRequest request, String id) {
		DocEntity doc = docService.getDoc(id);
		List<DocEntity> docList = docService.getListRedDocTag(id);

		List<DocParamEntity> docParamList = docParamService.findByProperty(DocParamEntity.class, "docid", id);
		List<DocReturnValueEntity> docReturnValueList = docParamService.findByProperty(DocReturnValueEntity.class, "docid", id);

		Map<String, Object> m = new HashMap<String, Object>();
		m.putAll(index(request).getModel());
		m.put("docList", docList);
		m.put("docInfo", doc);
		m.put("docParamList", docParamList);
		m.put("docReturnValueList", docReturnValueList);
		return new ModelAndView("platform/doc/doctag", m);
	}

	/**
	 * 预览api文档列表
	 * 
	 * @param reqeust
	 * @return
	 */
	@RequestMapping(params = "docapiList")
	public ModelAndView docapiList(HttpServletRequest request, String id) {
	
		DocEntity doc = docService.getDoc(id);
		List<DocEntity> docList = docService.getListRedDocApiList(id);

		List<DocEnRefEntity> docEnRefList = docService.findByProperty(id);

		Map<String, Object> m = new HashMap<String, Object>();

		m.putAll(index(request).getModel());
		m.put("docInfo", doc);
		m.put("docList", docList);
		m.put("docEnRefList", docEnRefList);
		return new ModelAndView("platform/doc/doc_apiList", m);
	}

	/**
	 * 预览api文档
	 * 
	 * @param reqeust
	 * @return
	 */
	@RequestMapping(params = "docapi")
	public ModelAndView docapi(HttpServletRequest request, String id) {
		DocEntity doc = docService.getDoc(id);
		List<DocEntity> docList = docService.getListRedDocApi(id);
		List<DocParamEntity> docParamList = docParamService.findByProperty(DocParamEntity.class, "docid", id);
		List<DocReturnValueEntity> docReturnValueList = docParamService.findByProperty(DocReturnValueEntity.class, "docid", id);

		Map<String, Object> m = new HashMap<String, Object>();
		m.putAll(index(request).getModel());
		m.put("docList", docList);
		m.put("docInfo", doc);
		m.put("docParamList", docParamList);
		m.put("docReturnValueList", docReturnValueList);
		return new ModelAndView("platform/doc/docapi", m);
	}

	/**
	 * api试一试
	 * 
	 * @param reqeust
	 * @return
	 */
	@RequestMapping(params = "apitry")
	public ModelAndView apitry(HttpServletRequest request, String id) {
		DocEntity doc = docService.getDoc(id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("apiurl", doc.getApiAddress());
		return new ModelAndView("platform/doc/apitry", m);
	}

	/**
	 * 标签试一试
	 * 
	 * @param reqeust
	 * @return
	 */
	@RequestMapping(params = "tagtry")
	public ModelAndView tagtry(HttpServletRequest request, String id, String codepress) {
		DocEntity doc = docService.getDoc(id);
		Map<String, Object> m = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(codepress)) {
			m.put("tagdemo", codepress);
		} else {
			m.put("tagdemo", doc.getTagDemo());
		}
		String paramUrl = request.getParameter("paramUrl");

		try {
			Configuration cfg = new Configuration();
			cfg.setLocale(Locale.CHINA);
			cfg.setDefaultEncoding("UTF-8");

			StringTemplateLoader stringLoader = new StringTemplateLoader();
			if (StringUtils.isNotEmpty(codepress)) {
				stringLoader.putTemplate("myTemplate", codepress);
			} else {
				stringLoader.putTemplate("myTemplate", doc.getTagDemo());
			}
			cfg.setTemplateLoader(stringLoader);
			Template template = cfg.getTemplate("myTemplate", "utf-8");

			String separator = System.getProperty("file.separator");

			String projectPath = SystemPath.getSysPath();// 工程根路径

			String fileNamePath = projectPath + "platform" + separator + "doc" + separator + "loadtext.html";

			File file = new File(fileNamePath);
			if (file.exists()) {
				file.delete();
			}

			String fileDir = StringUtils.substringBeforeLast(fileNamePath, separator);
			org.apache.commons.io.FileUtils.forceMkdir(new File(fileDir + separator));
			Writer out = new OutputStreamWriter(new FileOutputStream(fileNamePath), "utf-8");

			Map<String, Object> data = new HashMap<String, Object>();
			data.putAll(request.getParameterMap());
			data.put("newTag", new TagCreator());

			if (StringUtils.isNotEmpty(paramUrl)) {
				String[] paramArray = paramUrl.trim().split("&");
				if (paramArray.length > 0) {
					for (int i = 0; i < paramArray.length; i++) {
						if (StringUtils.isNotEmpty(paramArray[i])) {
							data.put(paramArray[i].split("=")[0], paramArray[i].split("=")[1]);
						}
					}
				}
			}

			template.process(data, out);
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		m.put("id", doc.getId());
		m.put("searchMap", request.getParameterMap());
		return new ModelAndView("platform/doc/tagtry", m);
	}

	/**
	 * 实体显示
	 * 
	 * @param reqeust
	 * @return
	 */
	@RequestMapping(params = "getEntityInfo")
	public ModelAndView getEntityInfo(HttpServletRequest request, String entityName) {
		String platform = request.getParameter("platform");
		Map<String, Object> m = new HashMap<String, Object>();
		m.putAll(index(request).getModel());

		List<DocEnEntity> docEnList = docEnService.findByProperty(DocEnEntity.class, "code", entityName);
		if (docEnList != null && docEnList.size() > 0) {
			DocEnEntity donEn = docEnList.get(0);
			m.put("docEn", donEn);

			List<DocEnProEntity> docEnProList = docEnService.findByProperty(DocEnProEntity.class, "entityid", donEn.getId());
			m.put("docEnProList", docEnProList);

			List<DocReturnValueEntity> returnValueList = docEnService.findByProperty(DocReturnValueEntity.class, "code", donEn.getCode());
			List<DocEntity> docList = new ArrayList<DocEntity>();
			if (returnValueList != null && returnValueList.size() > 0) {
				for (int i = 0; i < returnValueList.size(); i++) {
					DocEntity doc = docEnService.getEntity(DocEntity.class, returnValueList.get(i).getDocid());
					if (StringUtils.isEmpty(platform)||doc.getPlatform().equals(platform)) {
						DocTypeEntity docType = docEnService.getEntity(DocTypeEntity.class, doc.getTypeid());
						doc.setType(docType.getName());
						docList.add(doc);
					}
				}
			}

			m.put("docList", docList);
		} else {
			m.put("docEn", new DocEnEntity());
			m.put("docEnProList", new ArrayList<DocEnProEntity>());
			m.put("docList", new ArrayList<DocEntity>());
		}

		return new ModelAndView("platform/doc/showEntityInfo", m);
	}

}
