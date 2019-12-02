package com.leimingtech.mobile.push;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.leimingtech.mobile.push.android.AndroidBroadcast;
import com.leimingtech.mobile.push.android.AndroidCustomizedcast;
import com.leimingtech.mobile.push.android.AndroidGroupcast;
import com.leimingtech.mobile.push.android.AndroidUnicast;
import com.leimingtech.mobile.push.ios.IOSBroadcast;
import com.leimingtech.mobile.push.ios.IOSCustomizedcast;
import com.leimingtech.mobile.push.ios.IOSGroupcast;
import com.leimingtech.mobile.push.ios.IOSUnicast;

public abstract class UmengNotification {
	
	protected Logger log=Logger.getLogger(UmengNotification.class);
	
	protected final JSONObject rootJson = new JSONObject();
	
	protected HttpClient client = new DefaultHttpClient();
	
	protected static final String host = "http://msg.umeng.com";
	
	protected static final String uploadPath = "/upload";
	
	protected static final String postPath = "/api/send";
	
	protected String appMasterSecret;
	
	protected final String USER_AGENT = "Mozilla/5.0";
	
	protected static final HashSet<String> ROOT_KEYS = new HashSet<String>(Arrays.asList(new String []{"appkey" , "timestamp" , "type" , "device_tokens" , "alias" , "alias_type" , "file_id" , "filter" , "production_mode" , "feedback" , "description" , "thirdparty_id"}));
	
	protected static final HashSet<String> POLICY_KEYS = new HashSet<String>(Arrays.asList(new String []{"start_time" , "expire_time" , "max_send_num"}));
	
	public abstract boolean setPredefinedKeyValue(String key , Object value) throws Exception;
	
	public abstract boolean setExtraField(String key , Object value) throws Exception;
	
	public void setAppMasterSecret(String secret) {
		appMasterSecret = secret;
	}
	
	/**
	 * 根据传入的广播类型及手机系统创建相应的同志对系那个
	 * 
	 * @param type
	 *            广播类型<br/>
	 * 				取值(不区分大小写)：broadcast、groupcast、unicast、customizedcast;传入其他参数将返回null
	 * @param phoneSystems
	 *            手机系统 <br/>
	 * 				取值(不区分大小写)：android、IOS;传入其他参数将返回null
	 * @return
	 */
	public static UmengNotification createNotification(String type , String phoneSystems) {
		UmengNotification notification = null;
		if(!StringUtils.isEmpty(type) && !StringUtils.isEmpty(phoneSystems)) {
			if(phoneSystems.toUpperCase().equals("android".toUpperCase())) {
				if(type.equals("broadcast")) {
					notification = new AndroidBroadcast();
				} else if(type.equals("groupcast")) {
					notification = new AndroidGroupcast();
				} else if(type.equals("unicast")) {
					notification = new AndroidUnicast();
				} else if(type.equals("customizedcast")) {
					notification = new AndroidCustomizedcast();
				}
			} else if(phoneSystems.toUpperCase().equals("ios".toUpperCase())) {
				if(type.equals("broadcast")) {
					notification = new IOSBroadcast();
				} else if(type.equals("groupcast")) {
					notification = new IOSGroupcast();
				} else if(type.equals("unicast")) {
					notification = new IOSUnicast();
				} else if(type.equals("customizedcast")) {
					notification = new IOSCustomizedcast();
				}
			}
		}
		return notification;
	}
	
	public boolean send() throws Exception {
		String url = host + postPath;
		String postBody = rootJson.toString();
		log.info(postBody);
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("UTF-8"));//加密是注意指定编码，编码必须和请求是编码一致
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent" , USER_AGENT);
		StringEntity se = new StringEntity(postBody , "UTF-8");
		post.setEntity(se);
		// 发送post请求和响应
		HttpResponse response = client.execute(post);
		int status = response.getStatusLine().getStatusCode();
		log.info("Response Code : " + status);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		log.info(result.toString());
		if(status == 200) {
			log.info("Notification sent successfully.");
		} else {
			log.info("Failed to send the notification!");
		}
		return true;
	}
	
}
