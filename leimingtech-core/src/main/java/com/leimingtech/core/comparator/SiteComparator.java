package com.leimingtech.core.comparator;

import java.util.Comparator;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.SiteEntity;

/**
 * 站点数据通过创建时间倒序排序
 * 
 * @author liuzhen
 * 
 */
public class SiteComparator implements Comparator<SiteEntity> {

	private SortDirection sort = SortDirection.desc;

	public SiteComparator() {

	}

	public SiteComparator(SortDirection sort) {
		this.sort = sort;
	}

	/**
	 * 如果o1小于o2,返回一个正数;如果o1大于o2，返回一个负数;如果他们相等，则返回0;实现日期倒序
	 */
	@Override
	public int compare(SiteEntity o1, SiteEntity o2) {

		if (o1.getCreatedtime().after(o2.getCreatedtime())) {
			if (sort == SortDirection.desc) {
				return 1;
			} else {
				return -1;
			}

		} else if (o1.getCreatedtime().before(o2.getCreatedtime())) {
			if (sort == SortDirection.desc) {
				return -1;
			} else {
				return 1;
			}
		}
		return 0;
	}

}
