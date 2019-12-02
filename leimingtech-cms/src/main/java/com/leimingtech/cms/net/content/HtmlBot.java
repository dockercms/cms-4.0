/*
 * Copyright (C) 2014 hu
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package com.leimingtech.cms.net.content;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

/**
 * DomPage 创建器
 * 
 * @author 谢进伟
 * @DateTime 2015-7-30 上午1:30:32
 */
public class HtmlBot {
	
	private static Logger log = Logger.getLogger(HtmlBot.class);
	
	public static DomPage getDomPageByURL(String url) {
		Connection connect = HttpConnection.connect(url);
		connect.userAgent("Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
		DomPage domPage = null;
		int connectCount = 0;
		Document doc = null;
		ArrayList<String> errorCodeList = new ArrayList<String>();
		errorCodeList.add("400");// 请求无效
		errorCodeList.add("401");// 未授权：登录失败
		errorCodeList.add("403");// 禁止访问
		errorCodeList.add("404");// 无法找到文件
		errorCodeList.add("405");// 资源被禁止
		errorCodeList.add("406");// 无法接受
		errorCodeList.add("407");// 要求代理身份验证
		errorCodeList.add("410");// 永远不可用
		errorCodeList.add("412");// 先决条件失败
		errorCodeList.add("414");// 请求 – URI 太长
		errorCodeList.add("500");// 内部服务器错误
		errorCodeList.add("501");// 未实现
		errorCodeList.add("502");// 网关错误
		while (doc == null && connectCount < 3) {
			try {
				doc = connect.get();
				domPage = new DomPage(doc);
				String baseUri = domPage.getDoc().baseUri();
				if(!baseUri.contains(url)) {// 已重定向
					for(String code : errorCodeList) {
						if(baseUri.contains(code)) {
							return null;
						}
					}
				}
			} catch (SocketTimeoutException e) {
				connectCount++;
				if(connectCount >= 3) {// 如果3次均无法连接到指定网站,就不在继续连接
					log.error(e.getMessage());
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				return null;
			}
		}
		return domPage;
	}
	
	public static DomPage getDomPageByHtml(String html) {
		return getDomPageByHtml(html , null);
	}
	
	public static DomPage getDomPageByHtml(String html , String url) {
		Document doc = Jsoup.parse(html);
		if(url != null) {
			doc.setBaseUri(url);
		}
		DomPage domPage = new DomPage(doc);
		return domPage;
	}
}
