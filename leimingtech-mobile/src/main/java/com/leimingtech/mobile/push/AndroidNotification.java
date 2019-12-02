package com.leimingtech.mobile.push;

import java.util.Arrays;
import java.util.HashSet;
import org.json.JSONObject;

public abstract class AndroidNotification extends UmengNotification {
	
	protected static final HashSet<String> PAYLOAD_KEYS = new HashSet<String>(Arrays.asList(new String []{"display_type"}));
	
	protected static final HashSet<String> BODY_KEYS = new HashSet<String>(Arrays.asList(new String []{"ticker" , "title" , "text" , "builder_id" , "icon" , "largeIcon" , "img" , "play_vibrate" , "play_lights" , "play_sound" , "sound" , "after_open" , "url" , "activity" , "custom"}));
	
	@Override
	public boolean setPredefinedKeyValue(String key , Object value) throws Exception {
		if(ROOT_KEYS.contains(key)) {
			rootJson.put(key , value);
		} else if(PAYLOAD_KEYS.contains(key)) {
			JSONObject payloadJson = null;
			if(rootJson.has("payload")) {
				payloadJson = rootJson.getJSONObject("payload");
			} else {
				payloadJson = new JSONObject();
				rootJson.put("payload" , payloadJson);
			}
			payloadJson.put(key , value);
		} else if(BODY_KEYS.contains(key)) {
			JSONObject bodyJson = null;
			JSONObject payloadJson = null;
			if(rootJson.has("payload")) {
				payloadJson = rootJson.getJSONObject("payload");
			} else {
				payloadJson = new JSONObject();
				rootJson.put("payload" , payloadJson);
			}
			if(payloadJson.has("body")) {
				bodyJson = payloadJson.getJSONObject("body");
			} else {
				bodyJson = new JSONObject();
				payloadJson.put("body" , bodyJson);
			}
			bodyJson.put(key , value);
		} else if(POLICY_KEYS.contains(key)) {
			JSONObject policyJson = null;
			if(rootJson.has("policy")) {
				policyJson = rootJson.getJSONObject("policy");
			} else {
				policyJson = new JSONObject();
				rootJson.put("policy" , policyJson);
			}
			policyJson.put(key , value);
		} else {
			if(key == "payload" || key == "body" || key == "policy" || key == "extra") {
				throw new Exception("You don't need to set value for " + key + " , just set values for the sub keys in it.");
			} else {
				throw new Exception("Unknown key: " + key);
			}
		}
		return true;
	}
	
	@Override
	public boolean setExtraField(String key , Object value) throws Exception {
		JSONObject payloadJson = null;
		JSONObject extraJson = null;
		if(rootJson.has("payload")) {
			payloadJson = rootJson.getJSONObject("payload");
		} else {
			payloadJson = new JSONObject();
			rootJson.put("payload" , payloadJson);
		}
		
		if(payloadJson.has("extra")) {
			extraJson = payloadJson.getJSONObject("extra");
		} else {
			extraJson = new JSONObject();
			payloadJson.put("extra" , extraJson);
		}
		extraJson.put(key , value);
		return true;
	}

	@Override
	public boolean send() throws Exception {
		String logInfo=this.getClass().getSimpleName()+"发送推送请求...";
		this.log.info(logInfo);
		return super.send();
	}
}
