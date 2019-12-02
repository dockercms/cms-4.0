package com.leimingtech.mobile.push.android;

import com.leimingtech.mobile.push.AndroidNotification;

public class AndroidGroupcast extends AndroidNotification {
	
	public AndroidGroupcast (){
		try {
			this.setPredefinedKeyValue("type" , "groupcast");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
