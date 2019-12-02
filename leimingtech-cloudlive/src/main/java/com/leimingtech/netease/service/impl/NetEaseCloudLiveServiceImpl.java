package com.leimingtech.netease.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.leimingtech.netease.entity.AddressEntity;
import com.leimingtech.netease.entity.BaseNetEaseApiEntity;
import com.leimingtech.netease.entity.ChannelEntity;
import com.leimingtech.netease.entity.ChannelItemEntity;
import com.leimingtech.netease.entity.ChannelListEntity;
import com.leimingtech.netease.service.CloudLiveService;
import com.leimingtech.netease.utils.CheckSumBuilder;
import com.leimingtech.netease.utils.CloudLiveProperties;
import com.leimingtech.netease.utils.NetEaseJSONUtil;

/**
 * 网易云直播接口
 * 
 * @author liuzhen
 * 
 */
@Service("netEaseCloudLiveService")
public class NetEaseCloudLiveServiceImpl implements CloudLiveService {

	/**
	 * code状态表
	 * 
	 * code 详细描述<br/>
	 * 
	 * 200 操作成功<br/>
	 * 402 验证码错误<br/>
	 * 403 请求信息不完整<br/>
	 * 404 用户已存在<br/>
	 * 405 激活码不存在<br/>
	 * 406 用户未激活<br/>
	 * 407 用户不存在<br/>
	 * 408 密码错误<br/>
	 * 409 认证失败<br/>
	 * 410 用户已激活<br/>
	 * 411 邮箱不存在<br/>
	 * 501 内部错误<br/>
	 * 602 频道查询失败<br/>
	 * 603 频道删除失败<br/>
	 * 604 频道添加失败<br/>
	 * 605 频道更新失败<br/>
	 * 607 AppKey不存在<br/>
	 * 609 频道ID为空<br/>
	 * 610 频道名称为空<br/>
	 * 611 频道名称已经存在<br/>
	 * 612 频道类型错误<br/>
	 * 613 CheckSum为空<br/>
	 * 614 AppKey为空<br/>
	 * 615 CurTime为空<br/>
	 * 617 cid或uid错误<br/>
	 * 618 uid不存在或该uid下无频道<br/>
	 * 
	 */

	/**
	 * 创建一个直播频道
	 * 
	 * @param name
	 *            频道名称
	 * @param type
	 *            频道类型（0:rtmp；1:hls；2:http）
	 */
	@Override
	public BaseNetEaseApiEntity<ChannelEntity> createChannel(String name,
			int type) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url = "https://vcloud.163.com/app/channel/create";
		HttpPost httpPost = new HttpPost(url);

		String appKey = CloudLiveProperties.APPKEY;
		String appSecret = CloudLiveProperties.APPSECRET;
		String nonce = "1";
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
		String checkSum = CheckSumBuilder
				.getCheckSum(appSecret, nonce, curTime);// 参考 计算CheckSum的java代码

		// 设置请求的header
		httpPost.addHeader("AppKey", appKey);
		httpPost.addHeader("Nonce", nonce);
		httpPost.addHeader("CurTime", curTime);
		httpPost.addHeader("CheckSum", checkSum);
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

		BaseNetEaseApiEntity<ChannelEntity> channel = new BaseNetEaseApiEntity<ChannelEntity>();
		try {
			// 设置请求的参数
			StringEntity params = new StringEntity("{\"name\":\"" + name
					+ "\", \"type\":" + type + "}");
			httpPost.setEntity(params);

			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);

			// 请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == 200) {
				// 读取服务器返回过来的json字符串数据
				String strResult = EntityUtils.toString(response.getEntity(),
						"utf-8");

				channel = new NetEaseJSONUtil<ChannelEntity>().ConvertToEntity(
						ChannelEntity.class, strResult);

				return channel;
			}

			channel.setCode(-1);
			channel.setMsg("网易云直播接口出现异常，无法正常返回信息");
			return channel;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			channel.setMsg(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			channel.setMsg(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			channel.setMsg(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			channel.setMsg(e.getMessage());
		}
		channel.setCode(-1);
		channel.setMsg("网易云直播接口出现异常," + channel.getMsg());
		return channel;
	}

	/**
	 * 修改直播频道信息
	 * 
	 * @param name
	 *            频道名
	 * @param cid
	 *            频道ID，32位字符串
	 * @param type
	 *            频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
	 * @return
	 */
	@Override
	public BaseNetEaseApiEntity updateChannel(String name, String cid, int type) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url = "https://vcloud.163.com/app/channel/update";
		HttpPost httpPost = new HttpPost(url);

		String appKey = CloudLiveProperties.APPKEY;
		String appSecret = CloudLiveProperties.APPSECRET;
		String nonce = "1";
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
		String checkSum = CheckSumBuilder
				.getCheckSum(appSecret, nonce, curTime);// 参考 计算CheckSum的java代码

		// 设置请求的header
		httpPost.addHeader("AppKey", appKey);
		httpPost.addHeader("Nonce", nonce);
		httpPost.addHeader("CurTime", curTime);
		httpPost.addHeader("CheckSum", checkSum);
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

		BaseNetEaseApiEntity result = new BaseNetEaseApiEntity();
		try {
			// 设置请求的参数
			StringEntity params = new StringEntity("{\"name\":\"" + name
					+ "\",\"cid\":\"" + cid + "\",\"type\":" + type + "}");
			httpPost.setEntity(params);

			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);

			// 请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == 200) {
				// 读取服务器返回过来的json字符串数据
				String strResult = EntityUtils.toString(response.getEntity(),
						"utf-8");

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper
						.configure(
								DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
								false);
				result = objectMapper.readValue(strResult,
						BaseNetEaseApiEntity.class);

				return result;
			}

			result.setCode(-1);
			result.setMsg("网易云直播接口出现异常，无法正常返回信息");
			return result;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result.setMsg(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result.setMsg(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			result.setMsg(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			result.setMsg(e.getMessage());
		}
		result.setCode(-1);
		result.setMsg("网易云直播接口出现异常," + result.getMsg());
		return result;
	}

	/**
	 * 删除一个直播频道
	 * 
	 * @param cid
	 *            频道ID，32位字符串
	 * @return
	 */
	@Override
	public BaseNetEaseApiEntity deleteChannel(String cid) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url = "https://vcloud.163.com/app/channel/delete";
		HttpPost httpPost = new HttpPost(url);

		String appKey = CloudLiveProperties.APPKEY;
		String appSecret = CloudLiveProperties.APPSECRET;
		String nonce = "1";
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
		String checkSum = CheckSumBuilder
				.getCheckSum(appSecret, nonce, curTime);// 参考 计算CheckSum的java代码

		// 设置请求的header
		httpPost.addHeader("AppKey", appKey);
		httpPost.addHeader("Nonce", nonce);
		httpPost.addHeader("CurTime", curTime);
		httpPost.addHeader("CheckSum", checkSum);
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

		BaseNetEaseApiEntity result = new BaseNetEaseApiEntity();
		try {
			// 设置请求的参数
			StringEntity params = new StringEntity("{\"cid\":\"" + cid + "\"}");
			httpPost.setEntity(params);

			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);

			// 请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == 200) {
				// 读取服务器返回过来的json字符串数据
				String strResult = EntityUtils.toString(response.getEntity(),
						"utf-8");

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper
						.configure(
								DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
								false);
				result = objectMapper.readValue(strResult,
						BaseNetEaseApiEntity.class);

				return result;
			}

			result.setCode(-1);
			result.setMsg("网易云直播接口出现异常，无法正常返回信息");
			return result;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result.setMsg(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result.setMsg(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			result.setMsg(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			result.setMsg(e.getMessage());
		}
		result.setCode(-1);
		result.setMsg("网易云直播接口出现异常," + result.getMsg());
		return result;
	}

	/**
	 * 获取一个直播频道的信息 <br/>
	 * 获取频道状态
	 * 
	 * @param cid
	 *            频道ID，32位字符串
	 */
	@Override
	public BaseNetEaseApiEntity<ChannelItemEntity> getChannel(String cid) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url = "https://vcloud.163.com/app/channelstats";
		HttpPost httpPost = new HttpPost(url);

		String appKey = CloudLiveProperties.APPKEY;
		String appSecret = CloudLiveProperties.APPSECRET;
		String nonce = "1";
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
		String checkSum = CheckSumBuilder
				.getCheckSum(appSecret, nonce, curTime);// 参考 计算CheckSum的java代码

		// 设置请求的header
		httpPost.addHeader("AppKey", appKey);
		httpPost.addHeader("Nonce", nonce);
		httpPost.addHeader("CurTime", curTime);
		httpPost.addHeader("CheckSum", checkSum);
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

		BaseNetEaseApiEntity<ChannelItemEntity> item = new BaseNetEaseApiEntity<ChannelItemEntity>();
		try {
			// 设置请求的参数
			StringEntity params = new StringEntity("{\"cid\":\"" + cid + "\"}");
			httpPost.setEntity(params);

			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);

			// 请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == 200) {
				// 读取服务器返回过来的json字符串数据
				String strResult = EntityUtils.toString(response.getEntity(),
						"utf-8");

				item = new NetEaseJSONUtil<ChannelItemEntity>()
						.ConvertToEntity(ChannelItemEntity.class, strResult);

				return item;
			}

			item.setCode(-1);
			item.setMsg("网易云直播接口出现异常，无法正常返回信息");
			return item;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			item.setMsg(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			item.setMsg(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			item.setMsg(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			item.setMsg(e.getMessage());
		}
		item.setCode(-1);
		item.setMsg("网易云直播接口出现异常," + item.getMsg());
		return item;
	}

	/**
	 * 获取用户直播频道列表<br/>
	 * 获取频道列表
	 * 
	 * @param records
	 *            单页记录数，默认值为10<br/>
	 *            非必填项
	 * @param pnum
	 *            要取第几页，默认值为1<br/>
	 *            非必填项
	 * @param ofield
	 *            排序的域，支持的排序域为：ctime（默认）<br/>
	 *            非必填项
	 * @param sort
	 *            升序还是降序，1升序，0降序，默认为desc<br/>
	 *            非必填项
	 * @return
	 */
	@Override
	public BaseNetEaseApiEntity<ChannelListEntity> getChannelList(
			Integer records, Integer pnum, String ofield, Integer sort) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url = "https://vcloud.163.com/app/channellist";
		HttpPost httpPost = new HttpPost(url);

		String appKey = CloudLiveProperties.APPKEY;
		String appSecret = CloudLiveProperties.APPSECRET;
		String nonce = "1";// 随机数（随机数，最大长度128个字符）
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
		String checkSum = CheckSumBuilder
				.getCheckSum(appSecret, nonce, curTime);// 参考 计算CheckSum的java代码

		// 设置请求的header
		httpPost.addHeader("AppKey", appKey);
		httpPost.addHeader("Nonce", nonce);
		httpPost.addHeader("CurTime", curTime);
		httpPost.addHeader("CheckSum", checkSum);
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

		BaseNetEaseApiEntity<ChannelListEntity> list = new BaseNetEaseApiEntity<ChannelListEntity>();
		try {
			JSONObject j = new JSONObject();
			if (records != null) {
				j.accumulate("records", records);
			}

			if (pnum != null) {
				j.accumulate("pnum", pnum);
			}

			if (StringUtils.isNotBlank(ofield)) {
				j.accumulate("ofield", ofield);
			}

			if (sort != null) {
				j.accumulate("sort", sort);
			}

			// 设置请求的参数
			StringEntity params = new StringEntity(j.toString());
			httpPost.setEntity(params);

			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);

			// 请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == 200) {
				// 读取服务器返回过来的json字符串数据
				String strResult = EntityUtils.toString(response.getEntity(),
						"utf-8");

				list = new NetEaseJSONUtil<ChannelListEntity>()
						.ConvertToEntity(ChannelListEntity.class, strResult);
				return list;
			}

			list.setCode(-1);
			list.setMsg("网易云直播接口出现异常，无法正常返回信息");
			return list;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			list.setMsg(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			list.setMsg(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			list.setMsg(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			list.setMsg(e.getMessage());
		}
		list.setCode(-1);
		list.setMsg("网易云直播接口出现异常," + list.getMsg());
		return list;
	}

	/**
	 * 重新获取推流地址<br/>
	 * 用户创建频道时获取的推流地址失效时，重新获取推流地址。
	 * 
	 * @param cid
	 *            频道ID
	 * @return
	 */
	@Override
	public BaseNetEaseApiEntity<AddressEntity> getAddress(String cid) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url = "https://vcloud.163.com/app/address";
		HttpPost httpPost = new HttpPost(url);

		String appKey = CloudLiveProperties.APPKEY;
		String appSecret = CloudLiveProperties.APPSECRET;
		String nonce = "1";
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
		String checkSum = CheckSumBuilder
				.getCheckSum(appSecret, nonce, curTime);// 参考 计算CheckSum的java代码

		// 设置请求的header
		httpPost.addHeader("AppKey", appKey);
		httpPost.addHeader("Nonce", nonce);
		httpPost.addHeader("CurTime", curTime);
		httpPost.addHeader("CheckSum", checkSum);
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

		BaseNetEaseApiEntity<AddressEntity> address = new BaseNetEaseApiEntity<AddressEntity>();
		try {
			// 设置请求的参数
			StringEntity params = new StringEntity("{\"cid\":\"" + cid + "\"}");
			httpPost.setEntity(params);

			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);

			// 请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == 200) {
				// 读取服务器返回过来的json字符串数据
				String strResult = EntityUtils.toString(response.getEntity(),
						"utf-8");

				address = new NetEaseJSONUtil<AddressEntity>().ConvertToEntity(
						AddressEntity.class, strResult);

				return address;
			}

			address.setCode(-1);
			address.setMsg("网易云直播接口出现异常，无法正常返回信息");
			return address;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			address.setMsg(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			address.setMsg(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			address.setMsg(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			address.setMsg(e.getMessage());
		}
		address.setCode(-1);
		address.setMsg("网易云直播接口出现异常," + address.getMsg());
		return address;
	}
}
