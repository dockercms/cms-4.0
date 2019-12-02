package com.leimingtech.wechat.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMethod;

import com.leimingtech.cms.net.content.DomPage;
import com.leimingtech.cms.net.content.HtmlBot;

/**
 * 微信接口调用工具
 * 
 * @author 谢进伟
 * 
 * @date 2015-8-13 上午11:15:14
 */
public class WechatInterfaceRequestUtil {
	
	private final static Logger log = Logger.getLogger(WechatInterfaceRequestUtil.class);
	
	/**
	 * 发起https请求,以JSONObject形式响应
	 * 
	 * @param url
	 *            请求路径
	 * @param requestMethod
	 *            请求方法
	 * @return
	 */
	public static JSONObject httpsRequestResultAsJson(String url , RequestMethod requestMethod) {
		try {
			StringBuffer bf = execRequest(url , requestMethod);
			JSONObject json = JSONObject.fromObject(bf.toString());
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 发起https请求,以map形式响应
	 * 
	 * @param url
	 *            请求路径
	 * @param requestMethod
	 *            请求方法
	 * @return
	 */
	public static Map<String , Object> httpsRequestResultAsMap(String url , RequestMethod requestMethod) {
		try {
			StringBuffer bf = execRequest(url , requestMethod);
			DomPage dompage = HtmlBot.getDomPageByHtml(bf.toString());
			Document doc = dompage.getDoc();
			Elements allElements = doc.getAllElements();
			Map<String , Object> resultMap = new HashMap<String , Object>();
			for(Element element : allElements) {
				String tagName = element.tagName();
				List<Node> childNodes = element.childNodes();
				if(childNodes.size() > 0) {
					converToMap(resultMap , childNodes);
				} else {
					String value = element.text();
					resultMap.put(tagName , value);
				}
			}
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将节点转换成键值对
	 * 
	 * @param resultMap
	 *            键值对存储容器
	 * @param nodes
	 *            节点结合
	 */
	private static void converToMap(Map<String , Object> resultMap , List<Node> nodes) {
		for(Node node : nodes) {
			List<Node> childNodes = node.childNodes();
			if(childNodes.size() > 0) {
				converToMap(resultMap , childNodes);
			} else {
				TextNode tn = (TextNode)node;
				resultMap.put(tn.nodeName() , tn.text());
			}
		}
	}
	
	/**
	 * get HttpsURLConnection
	 * 
	 * @param strUrl
	 *            url地址
	 * @return HttpsURLConnection
	 * @throws IOException
	 */
	private static HttpsURLConnection getHttpsURLConnection(String strUrl) throws IOException {
		URL url = new URL(strUrl);
		HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
		return httpsURLConnection;
	}
	
	/**
	 * 发起https请求
	 * 
	 * @param url
	 *            请求路径
	 * @param requestMethod
	 *            请求方法
	 * @return
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private static StringBuffer execRequest(String url , RequestMethod requestMethod) throws IOException , ProtocolException {
		HttpsURLConnection httpsURLConnection = getHttpsURLConnection(url);
		httpsURLConnection.setRequestMethod(requestMethod.toString());
		httpsURLConnection.connect();
		InputStream inputStream = httpsURLConnection.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(inputStream);
		int len = 0;
		byte [] b = new byte [1024];
		StringBuffer bf = new StringBuffer();
		while ((len = bis.read(b , 0 , len > 0 ? (len < 1024 ? len : 1024) : 1024)) > -1) {
			bf.append(new String(b));
		}
		log.info(bf.toString());
		return bf;
	}
	
}
