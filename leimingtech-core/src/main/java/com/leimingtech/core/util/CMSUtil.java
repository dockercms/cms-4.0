package com.leimingtech.core.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.ContentCatPrivEntity;
import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.entity.TSRoleUser;
import com.leimingtech.core.service.UserService;

/**
 * CMS工具类
 * 
 * @author liuzhen 2015年4月20日 11:12:24
 * 
 */
public class CMSUtil {
	/**
	 * 初始化PC栏目用户权限
	 */
	public static Set<String> initPCCatalogPermission() {

		HttpSession session = ContextHolderUtils.getSession();
		UserService userService = (UserService) PlatFormUtil
				.getInterface("userService");
		String sessionid = session.getId();
		Client client = ClientManager.getInstance().getClient(sessionid);
		if (client == null) {
			sessionid = ContextHolderUtils.getRequest().getParameter(
					"sessionId");
			client = ClientManager.getInstance().getClient(sessionid);
		}

		Set<String> pcCatalogIds = null;
		if (client != null && client.getPcCatalogIds() != null) {
			pcCatalogIds = (Set<String>) client.getPcCatalogIds();
		}
		if (pcCatalogIds == null) {
			pcCatalogIds = new HashSet<String>();
			List<TSRoleUser> rUsers = userService.findByProperty(
					TSRoleUser.class, "TSUser.id", PlatFormUtil
							.getSessionUser().getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				List<ContentCatPrivEntity> contentCatPrivList = userService
						.findByProperty(ContentCatPrivEntity.class,
								"TSRole.id", role.getId());
				for (ContentCatPrivEntity contentCatPriv : contentCatPrivList) {
					if (null != contentCatPriv.getContentCatEntity()) {
						pcCatalogIds.add(String.valueOf(contentCatPriv
								.getContentCatEntity().getId()));
					}
				}
			}
			client.setPcCatalogIds(pcCatalogIds);
			ClientManager.getInstance().addClinet(sessionid, client);
		}
		return pcCatalogIds;

	}
}
