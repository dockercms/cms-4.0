package com.leimingtech.mobile.push.ios;

import com.leimingtech.mobile.push.IOSNotification;

public class IOSUnicast extends IOSNotification {
	
	public IOSUnicast (){
		try {
			this.setPredefinedKeyValue("type" , "unicast");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
