package com.leimingtech.mobile.push.ios;

import com.leimingtech.mobile.push.IOSNotification;

public class IOSGroupcast extends IOSNotification {
	
	public IOSGroupcast (){
		try {
			this.setPredefinedKeyValue("type" , "groupcast");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
