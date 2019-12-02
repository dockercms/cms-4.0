package com.leimingtech.core.entity;

import java.util.Comparator;


/**
* @ClassName: NumberComparator 
* @Description: (字符串比较器) 
* @author lmcms 
* @date 2013-1-31 下午06:18:35 
*
 */
public class NumberComparator implements Comparator<Object> {
	private boolean ignoreCase = true;

	public NumberComparator() {
	}

	public NumberComparator(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public int compare(Object obj1, Object obj2) {
		String o1 = "";
		String o2 = "";
		if (ignoreCase) {
			TSFunction c1 = (TSFunction) obj1;
			TSFunction c2 = (TSFunction) obj2;
			o1 = c1.getFunctionOrder();
			o2 = c2.getFunctionOrder();
		}
		return o1.compareToIgnoreCase(o2);
	}

	private int getNumber(String str) {
		int num = Integer.MAX_VALUE;
		int bits = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
				bits++;
			} else {
				break;
			}
		}
		if (bits > 0) {
			num = Integer.parseInt(str.substring(0, bits));
		}
		return num;
	}
}
