package com.leimingtech.core.util.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.PropertyFilter;

public class JsonConfig extends net.sf.json.JsonConfig {
	private static JsonConfig instance;

	private JsonConfig() {
		
//		this.registerJsonValueProcessor(Object.class, new JsonValueProcessor() {
//
//			@Override
//			public Object processObjectValue(String key, Object value,
//					net.sf.json.JsonConfig config) {
//				if(null == value || "null".equals(value)){
//					return "{}";
//				}
//				System.out.println("Object processObjectValue \t key:"+key+" \t value:"+value+" \t config:"+config);
//				return value;
//			}
//
//			@Override
//			public Object processArrayValue(Object value,
//					net.sf.json.JsonConfig config) {
//				System.out.println("Object processObjectValue \t value:"+value+" \t config:"+config);
//				return value;
//			}
//
//		});
		
		this.registerJsonValueProcessor(String.class, new JsonValueProcessor() {

			@Override
			public Object processObjectValue(String key, Object value,
					net.sf.json.JsonConfig config) {
				if(null == value || "null".equals(value)){
					return "";
				}
//				System.out.println("String processObjectValue \t key:"+key+" \t value:"+value+" \t config:"+config);
				return value;
			}

			@Override
			public Object processArrayValue(Object value,
					net.sf.json.JsonConfig config) {
//				System.out.println("String processObjectValue \t value:"+value+" \t config:"+config);
				return value;
			}

		});
		
		this.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {

			@Override
			public Object processObjectValue(String key, Object value,
					net.sf.json.JsonConfig config) {
				if(null == value || "null".equals(value)){
					return "";
				}
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				
//				System.out.println("Date processObjectValue \t key:"+key+" \t value:"+value+" \t config:"+config);
				return dateFormat.format((Date) value); 
			}

			@Override
			public Object processArrayValue(Object value,
					net.sf.json.JsonConfig config) {
//				System.out.println("Date processObjectValue \t value:"+value+" \t config:"+config);
				return value;
			}

		});
		
//		this.setJsonPropertyFilter(new PropertyFilter() {
//			@Override
//			public boolean apply(Object source, String name, Object value) {
//				return value == null || "null".equals(value);
//			}
//			
//		});
		
		
	}

	public static JsonConfig getInstance() {
		if (instance == null) {
			instance = new JsonConfig();
		}
		return instance;
	}
}
