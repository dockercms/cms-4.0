package com.leimingtech.netease.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import com.leimingtech.netease.entity.BaseNetEaseApiEntity;

/**
 * 网易云直播接口中json转化工具
 * 
 * @author liuzhen
 * 
 */
public class NetEaseJSONUtil<T> {

	public BaseNetEaseApiEntity<T> ConvertToEntity(Class<T> clazz,
			String jsonStr) {

		BaseNetEaseApiEntity<T> temp = new BaseNetEaseApiEntity<T>();

		JSONObject j = new JSONObject(jsonStr);
		int code = j.optInt("code", -1);
		temp.setCode(code);
		if (code == 200) {
			String ret = j.optString("ret", "");
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(
					DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			try {
				T bean = objectMapper.readValue(ret, clazz);
				temp.setRet(bean);
				return temp;
			} catch (JsonParseException e) {
				e.printStackTrace();
				temp.setMsg(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				temp.setMsg(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				temp.setMsg(e.getMessage());
			}
			temp.setCode(-1);
			temp.setMsg("实体解析异常，错误信息：" + temp.getMsg());
			return temp;
		} else {
			String msg = j.optString("msg", "");
			temp.setMsg(msg);
		}

		return temp;
	}
}
