package test;

import com.leimingtech.netease.entity.AddressEntity;
import com.leimingtech.netease.entity.BaseNetEaseApiEntity;
import com.leimingtech.netease.entity.ChannelEntity;
import com.leimingtech.netease.entity.ChannelItemEntity;
import com.leimingtech.netease.entity.ChannelListEntity;
import com.leimingtech.netease.service.CloudLiveService;
import com.leimingtech.netease.service.impl.NetEaseCloudLiveServiceImpl;
import org.junit.Test;

public class Run {

	/**
	 * @param args
	 */
	@Test
	public void test() {
		CloudLiveService cloudLiveService = new NetEaseCloudLiveServiceImpl();
		BaseNetEaseApiEntity<ChannelListEntity> channelList = cloudLiveService
				.getChannelList(15, 1, null, null);
//
//		 BaseNetEaseApiEntity<ChannelEntity> channel = cloudLiveService
//		 .createChannel("211", 0);
//
//		 BaseNetEaseApiEntity api = cloudLiveService.updateChannel("222",
//		 "70fa72cadfca46c5a035f25cc90befeb", 1);
//
//		 BaseNetEaseApiEntity<AddressEntity> address = cloudLiveService
//		 .getAddress("70fa72cadfca46c5a035f25cc90befeb");
//
//		 BaseNetEaseApiEntity<ChannelItemEntity> item = cloudLiveService
//		 .getChannel("70fa72cadfca46c5a035f25cc90befeb");
//
//		 BaseNetEaseApiEntity api = cloudLiveService
//		 .deleteChannel("70fa72cadfca46c5a035f25cc90befeb");

		System.out.println();
	}

}
