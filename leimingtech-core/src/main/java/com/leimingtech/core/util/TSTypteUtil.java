package com.leimingtech.core.util;

import java.util.ArrayList;
import java.util.List;

import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.service.CommonService;

/**
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-1-4 下午4:31:44
 */
public class TSTypteUtil {
	
	/**
	 * 根据数据字典中的编码得到数据字典项
	 * 
	 * @param CommonService
	 *            系统服务Service
	 * @param groupCode
	 *            数据字典分组编码
	 * @return 数据字典项
	 */
	public static List<TSType> getTSTypesByGroupCode(CommonService CommonService , String groupCode) {
		List<TSType> stTypes = new ArrayList<TSType>();
		List<TSTypegroup> list = CommonService.findByProperty(TSTypegroup.class , "typegroupcode" , groupCode);
		TSTypegroup typeGroup = null;
		if(list != null && list.size() > 0) {
			typeGroup = list.get(0);
			if(typeGroup != null) {
				stTypes = CommonService.findByProperty(TSType.class , "TSTypegroup.id" , typeGroup.getId());
			}
		}
		return stTypes;
	}
	
	/**
	 * 根据数据字典分组码及数据字典项代码得到数据字典项名称
	 * 
	 * @param CommonService
	 *            系统服务Service
	 * @param groupCode
	 *            数据字典分组编码
	 * @param typeCode
	 *            数据字典项代码
	 * @return 数据字典代码所对应的名称
	 */
	public static String getTStypeName(CommonService CommonService , String groupCode , int typeCode) {
		List<TSType> tsTypes = getTSTypesByGroupCode(CommonService , groupCode);
		if(tsTypes.size() > 0) {
			for(TSType tstype : tsTypes) {
				if(tstype.getTypecode().equals(typeCode + "")) {
					return tstype.getTypename();
				}
			}
			return "<font style=\"color:red\">未找到数据字典项</font>";
		} else {
			return "<font style=\"color:red\">未找到相关数据字典配置</font>";
		}
	}
	
	/**
	 * 根据数据字典分组码及数据字典项代码得到数据字典项名称---typeCode为String类型
	 * 
	 * @param CommonService
	 *            系统服务Service
	 * @param groupCode
	 *            数据字典分组编码
	 * @param typeCode
	 *            数据字典项代码
	 * @return 数据字典代码所对应的名称
	 */
	public static String getStringTStypeName(CommonService CommonService , String groupCode , String typeCode) {
		List<TSType> tsTypes = getTSTypesByGroupCode(CommonService , groupCode);
		if(tsTypes.size() > 0) {
			for(TSType tstype : tsTypes) {
				if(tstype.getTypecode().equals(typeCode + "")) {
					return tstype.getTypename();
				}
			}
			return "";
		} else {
			return "";
		}
	}
}
