package com.leimingtech.core.comparator;

import java.util.Comparator;

import com.leimingtech.core.entity.Client;

public class ClientSort implements Comparator<Client> {

	
	public int compare(Client prev, Client now) {
		return (int) (now.getLogindatetime().getTime()-prev.getLogindatetime().getTime());
	}

}
