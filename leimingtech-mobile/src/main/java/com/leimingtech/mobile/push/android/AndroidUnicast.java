package com.leimingtech.mobile.push.android;

import com.leimingtech.mobile.push.AndroidNotification;

public class AndroidUnicast extends AndroidNotification {
	
	public AndroidUnicast (){
		try {
			this.setPredefinedKeyValue("type" , "unicast");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}