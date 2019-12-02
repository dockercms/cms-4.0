package com.leimingtech.cms.core.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * freemarker工具类
 * 
 * @author liuzhen
 * 
 */
public class FreemarkerUtils {

	private static Configuration cfg = new Configuration();

	static {
		cfg.setLocale(Locale.CHINA);
		cfg.setDefaultEncoding("UTF-8");
	}

	public static String renderString(String templateString,
			Map<String, ?> model) throws IOException, TemplateException {
		StringWriter result = new StringWriter();
		Template t = new Template("name", new StringReader(templateString), cfg);
		t.process(model, result);
		return result.toString();
	}
}
