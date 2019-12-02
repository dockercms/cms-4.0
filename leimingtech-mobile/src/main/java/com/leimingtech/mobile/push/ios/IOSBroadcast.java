package com.leimingtech.mobile.push.ios;

import com.leimingtech.mobile.push.IOSNotification;

public class IOSBroadcast extends IOSNotification {
	
	public IOSBroadcast (){
		try {
			this.setPredefinedKeyValue("type" , "broadcast");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
