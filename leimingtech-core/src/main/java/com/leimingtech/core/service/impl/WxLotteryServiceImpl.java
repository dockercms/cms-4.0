package com.leimingtech.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.LotteryEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.WxLotteryServiceI;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 刮刮卡、大转盘、砸金蛋接口实现
 * 
 * @author liuzhen 2015-3-11 09:49:58
 * 
 */
@Service("wxLotteryService")
@Transactional
public class WxLotteryServiceImpl extends CommonServiceImpl implements WxLotteryServiceI {

	static final String DAZHUANPAN_PATH = "dazhuanpan/";
	static final String DAZHUANPAN_TEMPLATE_PATH="pc/game/dazhuanpan.html";
	
	static final String ZAJINDAN_PATH = "zajindan/";
	static final String ZAJINDAN_TEMPLATE_PATH="pc/game/zajindan.html";

	@Override
	public void staticDaZhuanPan(LotteryEntity wxLottery) {

		if (wxLottery == null || wxLottery.getId() == null) {
			return;
		}
		SiteEntity site = getEntity(SiteEntity.class, wxLottery.getSiteId());
		String sitePath = CmsConstants.getSitePath(site);
		
		String outPath=sitePath + DAZHUANPAN_PATH + wxLottery.getId() + site.getStaticSuffix();
		if (wxLottery.getStatus() == 0) {
			File oldFile = new File(outPath);
			if (oldFile.exists()) {
				oldFile.delete();
			}
			return;
		}
		String siteTemplatePath=CmsConstants.getSiteTemplatePath(site);
		
		try {
			generateFile(siteTemplatePath+DAZHUANPAN_TEMPLATE_PATH,null,outPath);
			wxLottery.setUrl("/"+DAZHUANPAN_PATH + wxLottery.getId() + site.getStaticSuffix());
			saveOrUpdate(wxLottery);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void staticZaJinDan(LotteryEntity wxLottery) {

		if (wxLottery == null || wxLottery.getId() == null) {
			return;
		}
		SiteEntity site = getEntity(SiteEntity.class, wxLottery.getSiteId());
		String sitePath = CmsConstants.getSitePath(site);
		
		String outPath=sitePath + ZAJINDAN_PATH + wxLottery.getId() + site.getStaticSuffix();
		if (wxLottery.getStatus() == 0) {
			File oldFile = new File(outPath);
			if (oldFile.exists()) {
				oldFile.delete();
			}
			return;
		}
		String siteTemplatePath=CmsConstants.getSiteTemplatePath(site);
		
		Map<String,Object> data=new HashMap<String,Object>();
		data.put("contextpath", Globals.CONTEXTPATH);
		data.put("site", site);
		data.put("lottery", wxLottery);
		
		try {
			generateFile(siteTemplatePath+ZAJINDAN_TEMPLATE_PATH,data,outPath);
			wxLottery.setUrl("/"+ZAJINDAN_PATH + wxLottery.getId() + site.getStaticSuffix());
			saveOrUpdate(wxLottery);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 传入模板返回静态页面
	 * 
	 * @param templatepath
	 *            模板路径
	 * @param data
	 *            注入模板数据
	 * @return 生成后的内容
	 * @throws FileNotFoundException 
	 * @throws TemplateException
	 * @throws IOException
	 */
	private void generateFile(String templatepath, Map<String, Object> data,String fileNamePath) throws FileNotFoundException, IOException, TemplateException{
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		Writer out = null;
		Configuration cfg = new Configuration();
		cfg.setLocale(Locale.CHINA);
		cfg.setDefaultEncoding("UTF-8");
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate("myTemplate", IOUtils.toString(new FileInputStream(templatepath)));
		cfg.setTemplateLoader(stringLoader);
		Template template = cfg.getTemplate("myTemplate", "utf-8");

		String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
		FileUtils.forceMkdir(new File(fileDir + "/"));
		out = new OutputStreamWriter(new FileOutputStream(fileNamePath), "utf-8");
		
		template.process(data, out);
		out.flush();
		out.close();
	}

}