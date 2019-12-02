package com.leimingtech.core.common;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TagCreator;
import com.leimingtech.core.util.FileUtils;
import com.leimingtech.core.util.SystemPath;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

/**
 * 模板生成静态
 *
 * @author liuzhen 2014年4月18日 17:35:14
 */
public class CreateStaticHtml {

    /**
     * 添加参数支持
     *
     * @param templateFileName
     * @param type
     * @throws TemplateException
     * @throws IOException
     */
    public static void invoke(String templateFilepath, int type, Map<String, Object> data) throws TemplateException, IOException {
        if (MapUtils.isEmpty(data))
            data = new HashMap<String, Object>();

        data.put("newTag", new TagCreator());
        generateFile(templateFilepath, data, type);
    }

    /**
     * 生成创建静态文件
     *
     * @param templateFileName
     * @param type
     * @param data
     * @throws TemplateException
     * @throws IOException
     */
    public static void generateFile(String templateFilepath, Map<String, Object> data, Integer type) throws TemplateException, IOException {
        String separator = System.getProperty("file.separator");
        templateFilepath = templateFilepath.replaceAll("/", separator + separator);
        // 从Session中获取站点
        HttpSession session = ContextHolderUtils.getSession();
        SiteEntity site = ClientManager.getInstance().getClient(session.getId()).getSite();
        String sitePath = site.getSitePath();// 站点路径
        String staticSuffix = site.getStaticSuffix();// 静态页面后缀

        // 获取站点全路径
        String projectPath = SystemPath.getSysPath();
        String siteFullPath = projectPath + CmsConstants.SITE_STORAGE_PATH + separator + sitePath + separator;

        Configuration cfg = new Configuration();
        cfg.setLocale(Locale.CHINA);
        cfg.setDefaultEncoding("UTF-8");

        // 模板位置
        File file = new File(projectPath + "upload" + separator + "template" + separator + templateFilepath);
        String filename = FileUtils.getFilePrefix(file.getName()) + "_0";

        // 根据类型指定生成位置
        String fileNamePath = "";
        switch (type) {
            case CmsConstants.SECTION:
                fileNamePath = siteFullPath + "section" + separator + filename + staticSuffix;
                break;
            case CmsConstants.INDEX:
                fileNamePath = siteFullPath + filename + staticSuffix;
                break;
            case CmsConstants.TEMPLATE:
                fileNamePath = siteFullPath + "cache" + separator + "template" + separator + filename + staticSuffix;
                break;
            case CmsConstants.PAGE:
                fileNamePath = siteFullPath + "cache" + separator + "page" + separator + filename + staticSuffix;
                break;
        }

        StringTemplateLoader stringLoader = new StringTemplateLoader();

        String ftl = IOUtils.toString(new FileInputStream(file));
        String includePath = "section/";
        String regStr = replaceHtmlTag(ftl, "#include", "<!--#include virtual=\"" + includePath + "", staticSuffix + "\"-->");

        stringLoader.putTemplate("myTemplate", regStr);
        cfg.setTemplateLoader(stringLoader);
        Template template = cfg.getTemplate("myTemplate", "utf-8");

        String fileDir = StringUtils.substringBeforeLast(fileNamePath, separator);
        org.apache.commons.io.FileUtils.forceMkdir(new File(fileDir + separator));
        Writer out = new OutputStreamWriter(new FileOutputStream(fileNamePath), "utf-8");

        template.process(data, out);
        out.flush();
        out.close();
    }

    /*
     * 得到项目代码的绝对路径，到src
     */
    public String getProjectPath() {
        String path = System.getProperty("user.dir").replace("\\", "/") + "/";
        return path;
    }

    @Test
    public void test() {
        String systemPath = System.getProperty("user.dir").replace("\\", "/") + "/";
        File file = new File(systemPath + "WebRoot/WEB-INF/view/cms/tag/index.ftl");
        System.out.println(file.getName());
        try {
            String ftl = IOUtils.toString(new FileInputStream(file));
            // <!--#include virtual="/include/header_0.html"-->
            String regStr = replaceHtmlTag(ftl, "#include", "<!--#include virtual=\"", ".shtml\"-->");
            System.out.println(regStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 基本功能：替换指定的标签
     * <p/>
     *
     * @param str
     * @param beforeTag 要替换的标签
     * @param startTag  新标签开始标记
     * @param endTag    新标签结束标记
     * @return String
     * @如：替换img标签的src属性值为[img]属性值[/img]
     */
    public static String replaceHtmlTag(String str, String beforeTag, String startTag, String endTag) {
        String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
        String regxpForTagAttrib = "\"([^\"]+)\"";
        String regxpForTaginfo = "([^\"]+)/([^\"]+)(?:\\.[a-z])";
        Pattern patternForTag = Pattern.compile(regxpForTag);
        Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
        Pattern patternForinfo = Pattern.compile(regxpForTaginfo);
        Matcher matcherForTag = patternForTag.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        while (result) {
            StringBuffer sbreplace = new StringBuffer();
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
            if (matcherForAttrib.find()) {
                // System.out.println("frlUrl-->"+matcherForAttrib.group(matcherForAttrib.groupCount()));
                Matcher matcherForinfo = patternForinfo.matcher(matcherForAttrib.group(1));

                if (matcherForinfo.find()) {
                    // System.out.println("ftlInfo-->"+matcherForinfo.group(matcherForinfo.groupCount()));
                    matcherForinfo.appendReplacement(sbreplace, startTag + matcherForinfo.group(matcherForinfo.groupCount()) + "_0"
                            + endTag);
                } else {
                    matcherForAttrib.appendReplacement(sbreplace, startTag + matcherForAttrib.group(1) + endTag);
                }
            }
            matcherForTag.appendReplacement(sb, sbreplace.toString());
            result = matcherForTag.find();
        }
        matcherForTag.appendTail(sb);
        return sb.toString();
    }
}
