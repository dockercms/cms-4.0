package weibo4j.util;

import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.weibo.service.sinaweibo.SinaWeiboServiceI;
public class WeiboConfig {

	public static String getValue(String key){
		
		SinaWeiboServiceI sinaWeiboService=(SinaWeiboServiceI)PlatFormUtil.getInterface("sinaWeiboService");
		String site=PlatFormUtil.getSessionSite().getId();
		String info=sinaWeiboService.getWeiboinfo(key, site);
		return info;
	}
}
