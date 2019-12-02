package com.leimingtech.mobile.push.android;

import com.leimingtech.mobile.push.AndroidNotification;

public class AndroidBroadcast extends AndroidNotification {
	
	public AndroidBroadcast (){
		try {
			this.setPredefinedKeyValue("type" , "broadcast");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
