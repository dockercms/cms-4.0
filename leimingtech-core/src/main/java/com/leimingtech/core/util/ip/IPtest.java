package com.leimingtech.core.util.ip;

import java.util.Map;

import com.leimingtech.core.util.MapJsonUtil;
import org.junit.Test;


public class IPtest {

	@Test
      public void test() {
		
    	  //指定纯真数据库的文件名，所在文件夹  
    	  //IPSeeker ip=new IPSeeker("QQWry.Dat","f:/qqwry");  
    	  //测试IP 58.20.43.13  
    	  //System.out.println(ip.getIPLocation("58.20.43.13").getCountry()+":"+ip.getIPLocation("58.20.43.13").getArea());
    	  
    	  String aa = "{'code':0,'data':{'country':'\u4e2d\u56fd','country_id':'CN','area':'\u534e\u4e2d','area_id':'400000','region':'\u6e56\u5357\u7701','region_id':'430000','city':'\u957f\u6c99\u5e02','city_id':'430100','county':'','county_id':'-1','isp':'\u8054\u901a','isp_id':'100026','ip':'58.20.43.13'}}";
    	  Map result = MapJsonUtil.parseJSON2Map(aa);
    	  String data = result.get("data") + "";
    	  result = MapJsonUtil.parseJSON2Map(data);
    	  System.out.println(result.get("country"));
	}
}  
