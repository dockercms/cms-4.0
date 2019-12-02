package com.leimingtech.wechat.service.impl.wechatuser;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import com.leimingtech.core.entity.WechatConfigEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.util.TSTypteUtil;
import com.leimingtech.core.util.date.DateUtils;
import com.leimingtech.wechat.entity.wechatuser.WechatUserEntity;
import com.leimingtech.wechat.service.wechatuser.WechatUserServiceI;
import com.leimingtech.wechat.util.WechatInterfaceRequestUtil;

/**
 * @Title: interface
 * @Description: 微信号管理接口实现
 * @author
 * @date 2015-08-12 18:18:13
 * @version V1.0
 * 
 */
@Service("wechatUserService")
@Transactional
public class WechatUserServiceImpl implements WechatUserServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	private final Logger log = Logger.getLogger(WechatUserServiceImpl.class);
	
	/**
	 * 保存微信号管理
	 * 
	 * @param wechatUser
	 * @return
	 */
	public String save(WechatUserEntity wechatUser) {
		return (String)commonService.save(wechatUser);
	}

	/**
	 * 更新微信号管理
	 *
	 * @param wechatUser
	 */
	@Override
	public void saveOrUpdate(WechatUserEntity wechatUser) {
		commonService.saveOrUpdate(wechatUser);
	}

	/**
	 * 通过id获取微信号管理
	 *
	 * @param id
	 *            微信号管理id
	 * @return
	 */
	@Override
	public WechatUserEntity getEntity(String id) {
		return commonService.getEntity(WechatUserEntity.class , id);
	}
	
	/**
	 * 获取分页后的微信号管理数据集
	 * 
	 * @param wechatUser
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return wechatUserList微信号管理数据集 pageCount总页数
	 */
	@Override
	public Map<String , Object> getPageList(WechatUserEntity wechatUser , Map param , int pageSize , int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(WechatUserEntity.class , pageSize , pageNo , "" , "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq , wechatUser , param);
		cq.add();
		PageList pageList = commonService.getPageList(cq , true);
		List<WechatUserEntity> resultList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0) {
			pageCount = 1;
		}
		Map<String , Object> m = new HashMap<String , Object>();
		m.put("wechatUserList" , resultList);
		m.put("pageCount" , pageCount);
		return m;
	}
	
	/**
	 * 删除微信号管理
	 * 
	 * @param wechatUser
	 */
	@Override
	public void delete(WechatUserEntity wechatUser) {
		commonService.delete(wechatUser);
	}
	
	@Override
	public void autoGetWechatUserAccessToken() {
		String sql = "select id,name,appId,appSecret,access_token accessToken,lastGetAccessTokenTime,isUpdate,lastOverdueTime from wechat_user";
		List<Map<String , Object>> wechatUserlist = commonService.findForJdbc(sql);
		if(wechatUserlist != null && wechatUserlist.size() > 0) {
			String expiresIn = WechatConfigEntity.getConfig("expiresIn");
			long expiresIn_long = 0L;
			if(expiresIn != null) {
				expiresIn_long = Long.parseLong(expiresIn);
				String url = WechatConfigEntity.getConfig("getAccessTokenUrl");
				Date formatBeforeDate = new Date();
				Timestamp nowDate = Timestamp.valueOf(DateUtils.format(formatBeforeDate , "yyyy-MM-dd HH:mm:00"));
				for(Map<String , Object> map : wechatUserlist) {
					Object lastGetAccessTokenTime_obj = map.get("lastGetAccessTokenTime");
					Object lastOverdueTime_obj = map.get("lastOverdueTime");
					boolean isValidity = true;
					if(lastGetAccessTokenTime_obj == null) {
						isValidity = false;// 从未获取accessToken
					} else {
						Timestamp lastGetAccessTokenTime = Timestamp.valueOf(lastGetAccessTokenTime_obj.toString());// 最后一次获取access_token
						Timestamp lastOverdueTime = null;// 最后一次获取的access_token过期时间
						long dateDiff_getAccessToken = nowDate.getTime() - lastGetAccessTokenTime.getTime();// 从第二次获取acces_token,差值以上一次获取的access_token时间为准
						boolean isServiceRestart = false;// 应用程序所部署的服务器是否在获取access_token时停止过服务器
						Timestamp referenceTime = null;
						if(lastOverdueTime_obj != null) {// 从第二次以后获取acces_token,差值以上一次获取的access_token过期时间为准
							lastOverdueTime = Timestamp.valueOf(lastOverdueTime_obj.toString());
							dateDiff_getAccessToken = nowDate.getTime() - lastOverdueTime.getTime();
							referenceTime = new Timestamp(lastOverdueTime.getTime() + expiresIn_long * 1000L);
							isServiceRestart = nowDate.compareTo(referenceTime) > 0;// 应用程序所部署的服务器有停止服务情况,且重启服务器时access_token已过期
						} else {
							referenceTime = new Timestamp(lastGetAccessTokenTime.getTime() + expiresIn_long * 1000L);
							isServiceRestart = nowDate.compareTo(referenceTime) > 0;// 应用程序所部署的服务器有停止服务情况,且重启服务器时access_token已过期
						}
						if(isServiceRestart) {// 服务器有过停止阶段
							boolean isCleanHistory = nowDate.getTime() - referenceTime.getTime() > expiresIn_long * 1000L;// 服务器停止之前最后一次获取的时间距离当前时间已超过了access_token有限时间,则清空服务器停止之前获取的access_token记录,从新开始计时获取
							if(isCleanHistory) {
								String cleanHistorySql = "update wechat_user set access_token=null,lastGetAccessTokenTime=null,lastOverdueTime=null,isUpdate=null where id=?";
								Integer row = this.commonService.executeSql(cleanHistorySql , map.get("id"));
								Object name = map.get("name");
								if(row > 0) {
									log.info(DateUtils.defaultFormat(nowDate) + "微信号:" + name + " 清空access_token历史记录成功!");
								} else {
									log.info(DateUtils.defaultFormat(nowDate) + "微信号:" + name + " 清空access_token历史记录失败!");
								}
								return;
							}
						}
						float advancedMinute = getAdvancedMinute();
						if((dateDiff_getAccessToken >= (expiresIn_long - advancedMinute * 60L) * 1000L && dateDiff_getAccessToken <= expiresIn_long * 1000L)// 按设定好的指定分钟数分钟提前更新access_token
								|| isServiceRestart// 重新更新应用程序所部署的服务器停止阶段已过期的access_token
						) {
							isValidity = false;// 即将过期或已过期
						}
						boolean fag = lastOverdueTime_obj != null && // 已获取过access_token
						nowDate.compareTo(lastOverdueTime) > 0;// access_token已过期
						if(fag) {// 提前指定分钟数分钟更新是否获已获取access_token状态值
							Object isUpdate = map.get("isUpdate");
							if(isUpdate != null) {
								if(BooleanUtils.toBoolean(isUpdate.toString())) {
									String setGetAccessTokenTimeSql = "update wechat_user set isUpdate=? where id=? and isUpdate=?";
									Object wechatUserId = map.get("id");
									Integer row = this.commonService.executeSql(setGetAccessTokenTimeSql , false , wechatUserId , true);
									Object name = map.get("name");
									if(row > 0) {
										log.info(DateUtils.defaultFormat(nowDate) + "微信号:" + name + " 获取access_token标识值更新成功!");
									} else {
										log.info(DateUtils.defaultFormat(nowDate) + "微信号:" + name + " 获取access_token标识值更新失败!");
									}
								}
							}
						}
					}
					if(!isValidity) {
						Object isUpdate = map.get("isUpdate");
						if(isUpdate != null) {
							if(!BooleanUtils.toBoolean(isUpdate.toString())) {
								new UpdateAccessTokenThread(url , map , nowDate).start();
							}
						} else {
							new UpdateAccessTokenThread(url , map , nowDate).start();// 首次获取access_token
						}
					}
				}
			}
		}
	}
	
	/**
	 * 更新accessToken
	 * 
	 * @param url
	 *            微信获取access_token接口路径
	 * @param map
	 *            公众平台信息
	 * @return
	 */
	private boolean updatingWechatUserAccessToken(String url , Map<String , Object> map , Timestamp nowDate) {
		boolean result = false;
		Object wechatUserId = map.get("id");
		Object appid = map.get("appId");
		Object secret = map.get("appSecret");
		Object name = map.get("name");
		Object oldAccessToken = map.get("accessToken");
		if(appid != null && secret != null) {
			url = String.format(url , appid , secret);
			JSONObject json = WechatInterfaceRequestUtil.httpsRequestResultAsJson(url , RequestMethod.GET);
			Map<String , Object> testMap = new HashMap<String , Object>();
			// testMap.put("access_token" , System.nanoTime() +
			// System.nanoTime() + UUID.randomUUID().toString());
			// testMap.put("expires_in" ,
			// WechatConfigEntity.getConfig("expiresIn"));
			// JSONObject json = JSONObject.fromObject(testMap);
			Object errcode = null;
			Object errmsg = null;
			Set<?> jsonKeySet = json.keySet();
			if(jsonKeySet.contains("errcode")) {
				errcode = json.get("errcode");
			}
			if(jsonKeySet.contains("errmsg")) {
				errmsg = json.get("errmsg");
			}
			if(errcode == null && errmsg == null) {
				Object access_token = null;
				Object expires_in = null;
				if(jsonKeySet.contains("expires_in") && jsonKeySet.contains("access_token")) {
					expires_in = json.get("expires_in");
					access_token = json.get("access_token");
					Boolean isUpdate_b = null;
					Timestamp lastGetAccessTokenTime = nowDate;
					Float advancedMinuteFloat = getAdvancedMinute();
					int advancedMinute = (int)Math.floor(advancedMinuteFloat);
					Float frac = advancedMinuteFloat - advancedMinute;
					Timestamp firstOverdueTime = new Timestamp((nowDate.getTime() + (advancedMinute * 60L + (frac > 0 ? Long.parseLong((Math.floor(frac * 60) + "").split("\\.")[0]) : 0L)) * 1000L));
					Timestamp overdueTime = firstOverdueTime;// 假设从未获取过access_token,则标识倒数第二次获取accessToken实际到期时间为当前时间
					Object lastOverdueTime_old_obj = map.get("lastOverdueTime");
					Object lastGetAccessTokenTime_obj = map.get("lastGetAccessTokenTime");
					// 验证假设是否成立
					if(lastOverdueTime_old_obj != null) {// 不成立,需更正假设结果
						// 已经获取过access_token则重新计算倒数第二次获取accessToken实际到期时间
						Timestamp lastOverdueTime_old = Timestamp.valueOf(lastOverdueTime_old_obj.toString());
						int expiresIn = 7200;// access_token的有效期 单位：秒
						String expiresInStr = WechatConfigEntity.getConfig("expiresIn");
						if(!StringUtils.isEmpty(expiresInStr)) {
							expiresIn = Integer.parseInt(expiresInStr);
						}
						overdueTime = new Timestamp(lastOverdueTime_old.getTime() + expiresIn * 1000L);
						isUpdate_b = true;
					} else {
						if(lastGetAccessTokenTime_obj != null) {// 第二次获取access_token
							isUpdate_b = true;
						} else {
							overdueTime = null;// 首次获取access_token
						}
					}
					String setGetAccessTokenTimeSql = "update wechat_user set access_token=?,lastOverdueTime=?,lastGetAccessTokenTime=?,isUpdate=? where id=?";
					Integer row = this.commonService.executeSql(setGetAccessTokenTimeSql , access_token , overdueTime , lastGetAccessTokenTime , isUpdate_b , wechatUserId);
					String lastGetAccessTokenHistory = "上次更新时间：" + (lastGetAccessTokenTime_obj == null ? "从未获取过access_token" : DateUtils.defaultFormat(Timestamp.valueOf(lastGetAccessTokenTime_obj.toString())));
					if(row > 0) {
						long sumSecond = Long.parseLong(expires_in.toString());
						long hour = sumSecond / (60 * 60);
						long minute = sumSecond % (60 * 60) / 60;
						long second = sumSecond % (60 * 60) % 60;
						String validity = hour + "小时" + minute + "分" + second + "秒";
						log.info(DateUtils.defaultFormat(nowDate) + "微信号:" + name + " access_token更新成功(" + lastGetAccessTokenHistory + " 旧的access_token:" + oldAccessToken + " 新的access_token:" + access_token + " 有效期：" + validity + ")!");
						result = true;// 更新accessToken成功
					} else {
						log.info(DateUtils.defaultFormat(nowDate) + "微信号:" + name + " access_token更新失败!");
					}
				} else {
					log.info(DateUtils.defaultFormat(nowDate) + "微信号:" + name + " access_token更新失败!");
				}
			} else {
				String erroeMsg = TSTypteUtil.getStringTStypeName(commonService , "wechatOverallResultCode" , errcode.toString());
				log.info(DateUtils.defaultFormat(nowDate) + "微信号:" + name + " access_token更新失败(" + erroeMsg + ")!");
			}
		}
		return result;
	}
	
	private float getAdvancedMinute() {
		float advancedMinute = 1;// 提前更新access_token阀值(单位：分钟)
		String advancedMinuteStr = WechatConfigEntity.getConfig("advancedGetAccessTokenMinute");
		if(!StringUtils.isEmpty(advancedMinuteStr)) {
			advancedMinute = Float.parseFloat(advancedMinuteStr);
		}
		return advancedMinute;
	}
	
	public class UpdateAccessTokenThread extends Thread {
		
		volatile boolean isStop;
		private String url;
		private Map<String , Object> map;
		private Timestamp nowDate;
		
		public UpdateAccessTokenThread (String url , Map<String , Object> map , Timestamp nowDate ){
			super();
			this.isStop = false;
			this.url = url;
			this.map = map;
			this.nowDate = nowDate;
		}
		
		@Override
		public void run() {
			if(!this.isStop) {
				updatingWechatUserAccessToken(url , map , nowDate);
				this.isStop = true;
				this.interrupt();
			}
		}
	}
}