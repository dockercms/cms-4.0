package com.leimingtech.core.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.MobileChannelPrivEntity;
import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.entity.TSRoleUser;
import com.leimingtech.core.service.UserService;

/**
 * 移动工具类
 * 
 * @author liuzhen 2015年4月20日 11:14:32
 * 
 */
public class MobileUtil {
	/**
	 * 初始化移动栏目用户权限
	 */
	public static Set<String> initMobileCatalogPermission() {

		HttpSession session = ContextHolderUtils.getSession();
		UserService userService = (UserService) PlatFormUtil.getInterface("userService");
		String sessionid = session.getId();
		Client client = ClientManager.getInstance().getClient(sessionid);
		if (client == null) {
			sessionid = ContextHolderUtils.getRequest().getParameter(
					"sessionId");
			client = ClientManager.getInstance().getClient(sessionid);
		}

		Set<String> mobileCatalogIds = null;
		if (client != null && client.getMobileCatalogIds() != null) {
			mobileCatalogIds = (Set<String>) client.getMobileCatalogIds();
		}
		if (mobileCatalogIds == null) {
			mobileCatalogIds = new HashSet<String>();
			List<TSRoleUser> rUsers = userService.findByProperty(
					TSRoleUser.class, "TSUser.id", PlatFormUtil.getSessionUser().getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				List<MobileChannelPrivEntity> mobileChannelPrivList = userService
						.findByProperty(MobileChannelPrivEntity.class,
								"TSRole.id", role.getId());
				for (MobileChannelPrivEntity mobileChannelPriv : mobileChannelPrivList) {
					if (null != mobileChannelPriv) {
						mobileCatalogIds.add(String.valueOf(mobileChannelPriv
								.getMobileChannelEntity().getId()));
					}
				}
			}
			client.setMobileCatalogIds(mobileCatalogIds);
			ClientManager.getInstance().addClinet(sessionid, client);
		}

		return mobileCatalogIds;

	}
}
