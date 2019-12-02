package com.leimingtech.core.service.impl.jedis;

import org.apache.commons.lang3.BooleanUtils;

import com.leimingtech.core.util.PropertiesUtil;

/**
 * jedis配置信息
 * 
 * @author liuzhen
 * 
 */
public class JedisConfig {

	/**jedis开关*/
	public final static boolean jedis_status;

	static {
		PropertiesUtil p = new PropertiesUtil("redis.properties");
		jedis_status = BooleanUtils.toBoolean(p.readProperty("redis.openflag"));
	}
}
