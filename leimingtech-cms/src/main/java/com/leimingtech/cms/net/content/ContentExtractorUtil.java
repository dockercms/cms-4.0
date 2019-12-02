package com.leimingtech.cms.net.content;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import com.leimingtech.cms.net.img.CatchNetworkImageUtil;
import com.leimingtech.core.entity.ContentExtractoruleEntity;

/**
 * 抓取网页主体内容
 * 
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2013-7-30 上午1:37:32
 */
public class ContentExtractorUtil {
	
	protected DomPage domPage;
	private Document doc;
	private ArrayList<TextNode> tNodeList = new ArrayList<TextNode>();
	private HashMap<TextNode , String> xpathMap = new HashMap<TextNode , String>();
	private HashMap<String , ArrayList<CountInfo>> countMap = new HashMap<String , ArrayList<CountInfo>>();
	private HashMap<String , ComputeInfo> computeMap = new HashMap<String , ComputeInfo>();
	private ArrayList<Double> etprList = new ArrayList<Double>();
	private ArrayList<Double> gaussEtprList = new ArrayList<Double>();
	private double threshold;
	private ArrayList<String> imagesList = new ArrayList<String>();
	private String html;
	private String content;
	private boolean isNowPage = true;
	private static Logger log = Logger.getLogger(ContentExtractorUtil.class);
	
	public ContentExtractorUtil (DomPage domPage ){
		this.domPage = domPage;
		this.doc = domPage.getDoc();
		clean();
		buildHisto();
	}
	
	public ContentExtractorUtil (){
		this.setContent("");
		this.setHtml("");
	}
	
	public static class CountInfo {
		
		TextNode tNode;
		public int textCount;
		public int puncCount;
		
		public CountInfo (TextNode tNode ){
			this.tNode = tNode;
			String text = tNode.text();
			this.textCount = TextUtils.countText(text);
			this.puncCount = TextUtils.countPunc(text);
		}
	}
	
	public static class ComputeInfo {
		
		double tpr;
		double ppr;
		double cs;
		double ps;
		double etpr;
		
		public ComputeInfo (double tpr , double ppr , double cs , double ps ){
			this.tpr = tpr;
			this.ppr = ppr;
			this.cs = cs;
			this.ps = ps;
			this.etpr = tpr * ppr * cs * ps;
		}
	}
	
	/**
	 * 移除多余的dom元素
	 */
	private void clean() {
		doc.select("script").remove();
		doc.select("style").remove();
		doc.select("iframe").remove();
	}
	
	private double computeDeviation(ArrayList<Double> list) {
		if(list.size() == 0) {
			return 0;
		}
		double ave = 0;
		for(Double d : list) {
			ave += d;
		}
		ave = ave / list.size();
		double sum = 0;
		for(Double d : list) {
			sum += (d - ave) * (d - ave);
		}
		sum = sum / list.size();
		return Math.sqrt(sum);
	}
	
	private double computeThreshold() {
		double d = computeDeviation(gaussEtprList);
		return d * 1;
	}
	
	private ComputeInfo getComputeInfo(ArrayList<CountInfo> countInfoList) {
		double textSum = 0;
		double puncSum = 0;
		ArrayList<Double> textCountList = new ArrayList<Double>();
		ArrayList<Double> puncCountList = new ArrayList<Double>();
		for(CountInfo countInfo : countInfoList) {
			textSum += countInfo.textCount;
			puncSum += countInfo.puncCount;
			textCountList.add(countInfo.textCount + 0.0);
			puncCountList.add(countInfo.puncCount + 0.0);
		}
		double tpr = textSum / countInfoList.size();
		double ppr = puncSum / countInfoList.size();
		double cs = computeDeviation(textCountList);
		double ps = computeDeviation(puncCountList);
		return new ComputeInfo(tpr , ppr , cs , ps);
	}
	
	private void addTextNode(TextNode tNode) {
		String text = tNode.text().trim();
		if(text.isEmpty()) {
			return;
		}
		String xpath = JsoupHelper.getXpath(tNode);
		tNodeList.add(tNode);
		xpathMap.put(tNode , xpath);
		
		CountInfo countInfo = new CountInfo(tNode);
		ArrayList<CountInfo> countInfoList = countMap.get(xpath);
		if(countInfoList == null) {
			countInfoList = new ArrayList<CountInfo>();
			countMap.put(xpath , countInfoList);
		}
		countInfoList.add(countInfo);
	}
	
	private void buildHisto() {
		doc.traverse(new NodeVisitor() {
			
			@Override
			public void head(Node node , int i) {
				if(node instanceof TextNode) {
					TextNode tNode = (TextNode)node;
					addTextNode(tNode);
				} else if(node.nodeName().toUpperCase().equals("IMG")) {
					CatchNetworkImageUtil cm = new CatchNetworkImageUtil();
					List<String> imgUrl = cm.getImageUrl(node.toString());
					List<String> imgSrcs = cm.getImageSrc(imgUrl);
					imagesList.addAll(imgSrcs);
				}
			}
			
			@Override
			public void tail(Node node , int i) {
				
			}
		});
		for(Map.Entry<String , ArrayList<CountInfo>> entry : countMap.entrySet()) {
			ComputeInfo computeInfo = getComputeInfo(entry.getValue());
			computeMap.put(entry.getKey() , computeInfo);
		}
		for(TextNode tNode : tNodeList) {
			String xpath = xpathMap.get(tNode);
			double etpr = computeMap.get(xpath).etpr;
			etprList.add(etpr);
		}
		gaussEtprList = GaussSmooth.gaussSmooth(etprList , 1);
		threshold = computeThreshold();
	}
	
	private void extractorContent() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < tNodeList.size() ; i++) {
			TextNode tNode = tNodeList.get(i);
			double gEtpr = gaussEtprList.get(i);
			if(gEtpr > threshold) {
				sb.append(tNode.text().trim() + "\n");
			}
		}
		this.setContent(sb.toString());
	}
	
	/**
	 * 从指定url对应的网页中，抽取正文(保留正文直接html标签)
	 * 
	 * @param url
	 *            指定网页的url
	 * @return 网页的正文
	 * @throws Exception
	 */
	private void extractorHtml() {
		String htmlStr = "";
		StringBuffer sb = new StringBuffer();
		String baseUri = null;
		for(int i = 0 ; i < tNodeList.size() ; i++) {
			TextNode tNode = tNodeList.get(i);
			double gEtpr = gaussEtprList.get(i);
			if(gEtpr > threshold) {
				Node parentNode = tNode.parentNode();
				Node grandpaNode = parentNode.parentNode();
				String outerHtml = grandpaNode.outerHtml();
				if(baseUri == null) {
					baseUri = tNode.baseUri();
				}
				if(!sb.toString().contains(outerHtml) && !StringUtils.isEmpty(outerHtml)) {
					sb.append(outerHtml);// 叠加grand节点区块主体内容
				}
				if(htmlStr.length() <= outerHtml.length()) {
					htmlStr = outerHtml;// 找最大的grand节点区块
				}
			}
		}
		if(baseUri != null && baseUri.contains("blog")) {// 经测试博客类网站更适合取最大主体区块内容作为主体内容
			this.setHtml(htmlStr);
			if(this.getHtml().equals("")) {
				this.setHtml(sb.toString());
			}
		} else {
			this.setHtml(sb.toString());
		}
	}
	
	/**
	 * 从指定url对应的网页中，抽取正文
	 * 
	 * @param url
	 *            指定网页的url
	 * @return 网页的正文
	 * @throws Exception
	 */
	public static ContentExtractorUtil getContentExtractorByURL(String url) throws Exception {
		DomPage domPage = HtmlBot.getDomPageByURL(url);
		if(domPage == null) {
			return new ContentExtractorUtil();
		}
		ContentExtractorUtil contentExtractor = new ContentExtractorUtil(domPage);
		contentExtractor.extractorHtml();
		contentExtractor.extractorContent();
		return contentExtractor;
	}
	
	/**
	 * 不适用配置的规则自动翻页(尝试性翻页)
	 * 
	 * @param url
	 *            页面路径
	 * @param pageNo
	 *            当前页
	 * @param mainCe
	 *            当前页面抓取内容（包括翻页内）
	 * @return 最后一页抓取内容
	 * @return
	 */
	public static void autoPaging(String url , ContentExtractorUtil mainCe) {
		autoPaging(url , null , mainCe);
	}
	
	/**
	 * 从html源码对应的网页中，抽取正文
	 * 
	 * @param html
	 *            html源码
	 * @return 网页的正文
	 * @throws Exception
	 */
	public static ContentExtractorUtil getContentExtractorByHtml(String html) throws Exception {
		DomPage domPage = HtmlBot.getDomPageByHtml(html);
		if(domPage == null) {
			return new ContentExtractorUtil();
		}
		ContentExtractorUtil contentExtractor = new ContentExtractorUtil(domPage);
		
		contentExtractor.extractorHtml();
		contentExtractor.extractorContent();
		return contentExtractor;
	}
	
	/**
	 * 过滤可能破坏插件样式的DOM元素
	 * 
	 * @param subjectHtml
	 *            抓取的主体内容
	 * @return
	 */
	public static String filterDom(String subjectHtml) {
		if(!StringUtils.isEmpty(subjectHtml)) {
			long nanoTime = System.nanoTime();
			subjectHtml = subjectHtml.replaceAll("\n" , "<" + nanoTime + ">").replaceAll("<!\\[CDATA\\[.*\\]\\]>" , "").replaceAll("(<link.*?/?>)|(<script.*?>.*?</script>)|(<style.*?>.*?</style>)|(<div.*?>)|(</div>)|(<iframe.*?>)|(</iframe>)" , "").replaceAll("<" + nanoTime + ">" , "\n");
		}
		return subjectHtml;
	}
	
	/**
	 * 判定指定的url是否配置过抓取规则
	 * 
	 * @param url
	 *            网站网址
	 * @return 匹配的规则
	 */
	public static ContentExtractoruleEntity isHaveRule(String url) {
		ContentExtractoruleEntity resultCere = null;
		int matchCount = 0;
		Map<Integer , ContentExtractoruleEntity> ceres = new TreeMap<Integer , ContentExtractoruleEntity>();
		Set<Integer> keySet = ceres.keySet();
		List<ContentExtractoruleEntity> allContentExtractorule = ContentExtractoruleEntity.allContentExtractorule;
		for(ContentExtractoruleEntity cere : allContentExtractorule) {
			String keywords = cere.getUrikeywordsfragment();
			matchCount = 0;
			String [] split2fragment = keywords.split(",");
			for(String keyword : split2fragment) {
				if(StringUtils.contains(url , keyword)) {
					matchCount++;
				} else {
					matchCount = 0;
					break;
				}
			}
			if(matchCount > 0) {
				if(keySet.contains(matchCount)) {
					if(ceres.get(matchCount).getUrikeywordsfragment().split(",").length > split2fragment.length) {
						ceres.put(matchCount , cere);// 始终匹配最精确的规则（取小范围）
					}
				} else {
					ceres.put(matchCount , cere);
				}
			}
		}
		if(ceres.size() > 0) {
			Integer maxKey = Collections.max(keySet);
			resultCere = ceres.get(maxKey);
		}
		return resultCere;
	}
	
	/**
	 * 按照配置规则严格抓取
	 * 
	 * @param url
	 *            网址
	 * @param cere
	 *            规则
	 * @return
	 */
	public static String contentExtratorule(String url , ContentExtractoruleEntity cere) {
		StringBuffer subjectHtml = new StringBuffer("");
		DomPage domPage = null;
		Element doc = null;
		domPage = HtmlBot.getDomPageByURL(url);
		if(domPage == null) {
			return "";
		} else {
			doc = domPage.getDoc();
		}
		if(doc != null) {
			String html = doc.html();
			if(!StringUtils.isEmpty(html)) {
				String scheme = cere.getScheme();
				log.info(cere.getWebsitename() + " 抓取方式：按" + scheme + "抓取,域名片段：" + Arrays.deepToString(cere.getUrikeywordsfragment().split(",")));
				if(scheme.equals("主体标签Id")) {// 根据主体内容容器Id抓取
					String newsBodyId = cere.getNewsbodyid();
					Element newsBody = doc.getElementById(newsBodyId);
					if(newsBody != null) {
						subjectHtml.append(newsBody.html());
					}
				} else if(scheme.equals("主体标签Class")) {// 根据主体内容容器class抓取
					String newsBodyClass = cere.getNewsbodyclass();
					Elements newsBodys = doc.getElementsByClass(newsBodyClass);
					ContentExtractorUtil.setSubjectHtmlByElements(subjectHtml , newsBodys);
				} else if(scheme.equals("自定义主体标签属性")) {
					String attrName = cere.getAttrname();
					String attrValue = cere.getAttrvalue();
					Elements newsBodys = doc.getElementsByAttributeValue(attrName , attrValue);
					ContentExtractorUtil.setSubjectHtmlByElements(subjectHtml , newsBodys);
				} else if(scheme.equals("网页注释抓取")) {// 根据主体内容的网页注释抓取
					String beginTag = cere.getBegintag();
					String endTag = cere.getEndtag();
					if(subjectHtml.length() == 0) {
						int beginTagIndex = html.indexOf(beginTag);
						int endTagIndex = html.lastIndexOf(endTag);
						if(beginTagIndex != -1 && endTagIndex != -1) {
							String subjectHtmlStr = html.substring(beginTagIndex , endTagIndex + endTag.length());
							subjectHtml.append(subjectHtmlStr);
						}
					}
				}
			}
		}
		return subjectHtml.toString();
	}
	
	/**
	 * 将指定元素中的内容放入指定字符容器中
	 * 
	 * @param subjectHtml
	 *            字符容器
	 * @param newsBodys
	 *            DOM元素集合
	 */
	private static void setSubjectHtmlByElements(StringBuffer subjectHtml , Elements newsBodys) {
		if(newsBodys != null) {
			for(Element newsBody : newsBodys) {
				subjectHtml.append(newsBody.html() + "<br/>");
			}
		}
	}
	
	/**
	 * 按指定规则自动翻页(尝试性翻页)
	 * 
	 * @param url
	 *            页面路径
	 * @param pageNo
	 *            当前页
	 * @param mainCe
	 *            当前页面抓取内容（包括翻页内）
	 * @return 最后一页抓取内容
	 * @return
	 */
	public static ContentExtractorUtil autoPaging(String url , ContentExtractoruleEntity cere , ContentExtractorUtil mainCe) {
		try {
			int lastIndexOf_suffix = url.lastIndexOf(".");
			if(lastIndexOf_suffix > -1) {
				// 获取当前网页的后缀名
				String suffix = url.substring(lastIndexOf_suffix);
				if(!StringUtils.isEmpty(suffix)) {
					int lastIndexOf_fileName = url.lastIndexOf("/");
					if(lastIndexOf_fileName > -1) {
						// 获取当前网页在远程服务器的网络文件夹地址
						String pageFolder = url.substring(0 , lastIndexOf_fileName);
						// 获取当前页面html文件名称
						String pageName = url.substring(lastIndexOf_fileName , lastIndexOf_suffix);
						boolean isChangePageName = false;
						long nanoTime = System.nanoTime();
						String pageNo = "1";
						String pageUrlPrefix = "";
						// 获取当前网页的兄弟页面
						String pageBrother = pageName;
						// 获取当前页码
						int lastIndexOf = pageName.lastIndexOf("_");
						boolean fag = false;// 当前url是否为翻页的url
						if(pageName.contains("_")) {
							if(lastIndexOf > -1) {
								String pageNoTemp = pageName.substring(lastIndexOf + 1);
								if(Integer.parseInt(pageNoTemp) > 0 && Integer.parseInt(pageNoTemp) < 100) {
									if(url.matches("^.*_" + pageNoTemp + suffix + "$")) {
										fag = true;
									}
								}
								if(!fag) {
									pageName = pageName.replace("_" , "-temp-" + nanoTime + "-temp-");
									isChangePageName = true;
								}
							}
						}
						if(isChangePageName) {
							lastIndexOf = pageName.lastIndexOf("_");
						}
						if(lastIndexOf > -1) {
							// 获取当前网页的兄弟页面
							pageBrother = pageName.substring(0 , lastIndexOf + 1);
							pageNo = pageName.substring(lastIndexOf + 1);
							// 获取当前网页网络前缀
							pageUrlPrefix = pageFolder + pageBrother;
						} else {
							// 获取当前网页网络前缀
							pageUrlPrefix = pageFolder + pageBrother + "_";
						}
						if(isChangePageName) {
							pageUrlPrefix = pageUrlPrefix.replace("-temp-" + nanoTime + "-temp-" , "_");
						}
						// 猜测下一页网络路径（猜测的网络路径可能实际并不存在
						String nextPageUrl = pageUrlPrefix + (Integer.parseInt(pageNo) + 1) + suffix;
						ContentExtractorUtil ce;
						if(cere != null) {
							// 尝试获取猜测的下一页主体内容
							String html = ContentExtractorUtil.contentExtratorule(nextPageUrl , cere);
							ce = ContentExtractorUtil.getContentExtractorByHtml(html);
							if(ce.getHtml().equals("")) {
								ce.setHtml(html);
								ce.setContent(html);
							}
						} else {
							ce = ContentExtractorUtil.getContentExtractorByURL(nextPageUrl);
						}
						if(ce != null && !"".equals(ce.getHtml())) {
							String mainHtml = mainCe.getHtml();
							String mainContent = mainCe.getContent();
							ArrayList<String> mainImages = mainCe.getImagesList();
							StringBuffer mcePageBreak = new StringBuffer("<p style='background-color:#F8F1B1;border:1px dashed #CCCCCC; text-align:center; font-weight:bold; line-height:27px; color:#0044FF;'>");
							mcePageBreak.append("第" + (Integer.parseInt(pageNo) + 1) + "页");
							mcePageBreak.append("</p>");
							if(mainCe.isNowPage()) {
								mainCe.setNowPage(false);
								StringBuffer mcePageBreakFirst = new StringBuffer("<p style='background-color:#F8F1B1;border:1px dashed #CCCCCC; text-align:center; font-weight:bold; line-height:27px; color:#0044FF;'>");
								mcePageBreakFirst.append("第" + (Integer.parseInt(pageNo)) + "页");
								mcePageBreakFirst.append("</p>");
								mainCe.setHtml(mcePageBreakFirst + mainHtml + mcePageBreak + ce.getHtml());// 追加新页面主题内容html代码
							} else {
								mainCe.setHtml(mainHtml + mcePageBreak + ce.getHtml());// 追加新页面主题内容html代码
							}
							mainCe.setContent(mainContent + mcePageBreak + ce.getContent());// 最佳新页面主体内容
							mainImages.addAll(ce.getImagesList());// 最佳新页面可下载图片资源
							if(Integer.parseInt(pageNo) < 30) {// 最大支持自动翻30页
								// 递归形式自动翻页
								autoPaging(nextPageUrl , cere , mainCe);
							}
						}
					}
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 将图片路径全部修正为网络路径
	 * 
	 * @param webUrl
	 *            当前web页面的网址
	 * @param html
	 *            主体内容
	 * @return
	 */
	public static String amendmentImgSrc(String webUrl , String html) {
		try {
			String imgTag_reg = "<img.*src=(.*?)[^>]*?>";
			String imgSrc_reg = "<img.*src=\"?http:\"?(.*?)(\"|>|\\s+)";
			html = html.replace(">" , ">\n");
			String [] split = html.split("\n");
			List<String> errorImgSrcs = new ArrayList<String>();
			for(String line : split) {
				Matcher matcher = Pattern.compile(imgTag_reg).matcher(line);
				while (matcher.find()) {
					String imgTag = matcher.group();
					if(!StringUtils.isEmpty(imgTag)) {
						// 特殊网站为了防止盗图,一般会将图片放在另外一个属性中,故应尝试修正图片真实路径
						String dataOriginal = StringUtils.substringBetween(imgTag , "data-original=\"" , "\"");
						if(!StringUtils.isEmpty(dataOriginal)) {
							String src = StringUtils.substringBetween(imgTag , "src=\"" , "\"");
							imgTag = imgTag.replaceAll(src , dataOriginal);
						}
						if(!imgTag.matches(imgSrc_reg)) {
							errorImgSrcs.add(imgTag);
						}
					}
				}
			}
			if(errorImgSrcs.size() > 0) {
				webUrl = webUrl.substring(0 , webUrl.lastIndexOf("/") + 1);
				String baseUrl = webUrl.substring(7);
				String [] folders = baseUrl.split("/");
				for(String imgTag : errorImgSrcs) {
					if(html.contains(imgTag)) {
						String src = StringUtils.substringBetween(imgTag , "src=\"" , "\"");
						String imgName = src.replaceAll("\\.\\./" , "");
						String realSrc = "";
						if(src.contains("../")) {
							List<String> tempFolder = new ArrayList<String>();
							int upSeriesCount = src.split("/").length - 1;
							for(int i = 0 ; i < upSeriesCount ; i++) {
								tempFolder.add(folders[folders.length - 1 - i]);
							}
							Collections.reverse(tempFolder);
							String abstraceUrl = StringUtils.join(tempFolder.toArray() , "/");
							realSrc = webUrl.substring(0 , webUrl.indexOf(abstraceUrl)) + abstraceUrl + "/" + imgName;
						} else {
							realSrc = "http://" + folders[0] + "/" + imgName;
						}
						String newImgTag = imgTag.replaceAll(src , realSrc);
						html = html.replaceAll(imgTag , newImgTag);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}
	
	/**
	 * 解码中文或时间输入参数
	 * 
	 * @param param
	 *            需要解码的参数
	 * @return
	 */
	public static String decode(String param) {
		try {
			if(param == null) {
				return null;
			} else {
				param = URLDecoder.decode(param , "UTF-8");
				param = new String(param.getBytes("ISO8839-1") , "UTF-8");
				return param;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取可下载图片资源链接
	 * 
	 * @return
	 */
	public ArrayList<String> getImagesList() {
		return imagesList;
	}
	
	/**
	 * 获取主体内容html代码
	 * 
	 * @return
	 */
	public String getHtml() {
		return html;
	}
	
	/**
	 * 获取主题内容
	 * 
	 * @return
	 */
	public String getContent() {
		return content;
	}
	
	public void setHtml(String html) {
		this.html = html;
	}
	
	public boolean isNowPage() {
		return isNowPage;
	}
	
	public void setNowPage(boolean isNowPage) {
		this.isNowPage = isNowPage;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public static void main(String [] args) throws Exception {
		String url = "http://msn.people.com.cn/n/2015/0710/c242548-27281963.html";
		// ContentExtractorUtil ce = getContentExtractorByURL(url);
		// autoPaging(url , ce);
		// filterDom(ce.getHtml());
		// System.out.println(ce.getHtml());
		
		ContentExtractoruleEntity cere = new ContentExtractoruleEntity();
		cere.setWebsitename("网易-新闻类");
		cere.setUrikeywordsfragment("http://msn.people.com.cn/");
		cere.setNewsbodyid("p_content");
		// cere.setScheme("自定义主体标签属性");
		cere.setScheme("主体标签Id");
		
		String subjectHtml = contentExtratorule(url , cere);
		ContentExtractorUtil contentExtractor = ContentExtractorUtil.getContentExtractorByHtml(subjectHtml);// 尝试翻页
		ContentExtractorUtil.autoPaging(url , cere , contentExtractor);
		subjectHtml = contentExtractor.getHtml();
		subjectHtml = ContentExtractorUtil.filterDom(subjectHtml);
		System.out.println(subjectHtml);
	}
}