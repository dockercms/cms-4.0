package com.leimingtech.platform.service.impl.server;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.leimingtech.platform.entity.message.resp.Article;
import com.leimingtech.platform.entity.message.resp.NewsMessage;

/** 
 * 类说明 
 */
public class BaiduMapService {
	/**
	 * 根据检索条件、中心点和检索半径搜索周边
	 * 
	 * @param location 检索条件
	 * @param location 周边检索中心点，不支持多个点
	 * @param radius 周边检索半径，单位为米
	 * @return List<Article>
	 */
	public static NewsMessage searchMap(String query, String location, String radius){
		NewsMessage newsMessage = new NewsMessage();
		//百度地图检索地址
		String requestUrl = "http://api.map.baidu.com/place/v2/search?&query={QUERY}&page_size=10&page_num=0&scope=2&location={LOCATION}&radius={RADIUS}&output=xml&ak=B494b1881a63e3966063d40b42d16512";
		// 对检索条件、中心点、检索半径进行URL编码
		requestUrl = requestUrl.replace("{QUERY}", urlEncodeUTF8(query));
		requestUrl = requestUrl.replace("{LOCATION}", urlEncodeUTF8(location));
		requestUrl = requestUrl.replace("{RADIUS}", urlEncodeUTF8(radius));
		
		// 查询并获取返回结果
		InputStream inputStream = httpRequest(requestUrl);
		List<Article> articleList = parseArticle(inputStream);
		if(articleList.size()>0){
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
		}
		return newsMessage;
		
	}
	/**
	 * UTF-8编码
	 * 
	 * @param source
	 * @return
	 */
	private static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 发送http请求取得返回的输入流
	 * 
	 * @param requestUrl 请求地址
	 * @return InputStream
	 */
	private static InputStream httpRequest(String requestUrl) {
		InputStream inputStream = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			// 获得返回的输入流
			inputStream = httpUrlConn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
	/**
	 * 解析图文消息参数
	 * 
	 * @param inputStream 百度地图检索API返回的输入流
	 * @return List<Article>
	 */
	@SuppressWarnings("unchecked")
	private static List<Article> parseArticle(InputStream inputStream) {
		List<Article> articleList = new ArrayList<Article>();
		
		try {
			// 使用dom4j解析xml字符串
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// total表示检索到的信息数
			int total = Integer.parseInt(root.element("total").getText());
			if(total>10){
				total = 10;
			}
			// 当检索到的信息数大于0时
			if (!"0".equals(total)) {
				Element results = root.element("results");
				List<Element> resultList = results.elements("result");
				for(int i=0;i<total;i++){
					Article article = new Article();
					String name = resultList.get(i).element("name").getText();
					String distance = resultList.get(i).element("detail_info").element("distance").getText();
					String detail_url = resultList.get(i).element("detail_info").element("detail_url").getText();
					String picUrl = "";
					if(i==0){
						picUrl = "http://1.javawx.sinaapp.com/img/1.jpg";
					}else{
						picUrl = "http://1.javawx.sinaapp.com/img/ss.png";
					}
					article.setTitle(name+"\n"+"距离约"+distance+"米");
					article.setDescription(resultList.get(i).element("uid").getText());
					article.setPicUrl(picUrl);
					try {
						java.net.URLEncoder.encode(detail_url, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					article.setUrl(detail_url);
					articleList.add(article);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return articleList;
	}
	
	// 测试方法
	public static void main(String[] args) {
		NewsMessage newsMessage = searchMap("ATM", "39.953537,116.378190","2000");
		for(int i=0 ;i<newsMessage.getArticles().size();i++){
			System.out.println(newsMessage.getArticles().get(i).getDescription());
		}
		
	}

}
