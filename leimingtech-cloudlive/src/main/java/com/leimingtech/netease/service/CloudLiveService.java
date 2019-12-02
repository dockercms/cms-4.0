package com.leimingtech.netease.service;

import com.leimingtech.netease.entity.AddressEntity;
import com.leimingtech.netease.entity.BaseNetEaseApiEntity;
import com.leimingtech.netease.entity.ChannelEntity;
import com.leimingtech.netease.entity.ChannelItemEntity;
import com.leimingtech.netease.entity.ChannelListEntity;

/**
 * 
 * @author liuzhen
 * 
 */
public interface CloudLiveService {
	/**
	 * 创建一个直播频道
	 * 
	 * @param name
	 *            频道名称
	 * @param type
	 *            频道类型（0:rtmp；1:hls；2:http）
	 */
	BaseNetEaseApiEntity<ChannelEntity> createChannel(String name, int type);

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
	BaseNetEaseApiEntity updateChannel(String name, String cid, int type);

	/**
	 * 删除一个直播频道
	 * 
	 * @param cid
	 *            频道ID，32位字符串
	 * @return
	 */
	BaseNetEaseApiEntity deleteChannel(String cid);

	/**
	 * 获取一个直播频道的信息 <br/>
	 * 获取频道状态
	 * 
	 * @param cid
	 *            频道ID，32位字符串
	 */
	BaseNetEaseApiEntity<ChannelItemEntity> getChannel(String cid);

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
	BaseNetEaseApiEntity<ChannelListEntity> getChannelList(Integer records,
			Integer pnum, String ofield, Integer sort);

	/**
	 * 重新获取推流地址<br/>
	 * 用户创建频道时获取的推流地址失效时，重新获取推流地址。
	 * 
	 * @param cid
	 *            频道ID
	 * @return
	 */
	BaseNetEaseApiEntity<AddressEntity> getAddress(String cid);
}
