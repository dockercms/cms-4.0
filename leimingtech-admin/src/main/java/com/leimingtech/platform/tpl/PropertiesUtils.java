package com.leimingtech.platform.tpl;

import java.util.Properties;


public class PropertiesUtils {
	// 属性 
    public static Properties props = new PropertiesLoader("/conf/leimingtech.properties").getProperties(); 
}
