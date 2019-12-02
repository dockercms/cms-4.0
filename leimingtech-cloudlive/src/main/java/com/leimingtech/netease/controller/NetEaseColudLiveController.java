package com.leimingtech.netease.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.netease.entity.AddressEntity;
import com.leimingtech.netease.entity.BaseNetEaseApiEntity;
import com.leimingtech.netease.entity.ChannelEntity;
import com.leimingtech.netease.entity.ChannelItemEntity;
import com.leimingtech.netease.entity.ChannelListEntity;
import com.leimingtech.netease.service.CloudLiveService;

/**
 * 网易云直播
 * 
 * @author liuzhen
 * 
 */
@Controller
@RequestMapping("/netEaseColudLiveController")
public class NetEaseColudLiveController extends BaseController {

	@Autowired
	private CloudLiveService netEaseCloudLiveService;

	/**
	 * 打开创建或者编辑直播频道界面
	 * 
	 * @param cid
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView form(String cid) {
		Map<String, Object> m = new HashMap<String, Object>();

		ChannelItemEntity item = new ChannelItemEntity();

		if (StringUtils.isNotBlank(cid)) {
			BaseNetEaseApiEntity<ChannelItemEntity> api = netEaseCloudLiveService
					.getChannel(cid);// 获取一个直播频道的信息

			if (api.getCode() == 200 || api.getRet() != null) {
				item = api.getRet();
			} else {
				m.put("errorMessage", "获取网易云直播频道信息失败，接口状态码：" + api.getCode()
						+ ",错误描述：" + api.getMsg());
			}
		}

		if (item == null) {
			item = new ChannelItemEntity();
		}

		m.put("channel", item);
		return new ModelAndView("cloudlive/netease/channel", m);
	}

	/**
	 * 保存或修改直播频道
	 * 
	 * @param name
	 *            频道名称
	 * @param type
	 *            频道类型（0:rtmp；1:hls；2:http）
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(String cid, String name, int type) {

		if (StringUtils.isBlank(name)) {
			return error("频道名称不能为空").toString();
		}

		if (StringUtils.isNotBlank(cid)) {
			BaseNetEaseApiEntity api = netEaseCloudLiveService.updateChannel(
					name, cid, type);

			if (api.getCode() == 200) {
				return success("频道" + name + "修改成功！").accumulate("toUrl",
						"netEaseColudLiveController/getChannelList.do")
						.toString();
			} else {
				return error("频道" + name + "修改失败！错误描述：" + api.getMsg())
						.toString();
			}
		} else {
			BaseNetEaseApiEntity api = netEaseCloudLiveService.createChannel(
					name, type);
			if (api.getCode() == 200) {
				return success("频道" + name + "保存成功！").accumulate("toUrl",
						"netEaseColudLiveController/getChannelList.do")
						.toString();
			} else {
				return error("频道" + name + "保存失败！错误描述：" + api.getMsg())
						.toString();
			}
		}
	}

	/**
	 * 删除一个直播频道
	 * 
	 * @param cid
	 *            频道ID，32位字符串
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id) {
		BaseNetEaseApiEntity api = netEaseCloudLiveService.deleteChannel(id);
		if (api.getCode() == 200) {
			return success("频道删除成功！").accumulate("toUrl",
					"netEaseColudLiveController/getChannelList.do").toString();
		} else {
			return error("频道删除失败！错误描述：" + api.getMsg()).toString();
		}
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
	@RequestMapping("/getChannelList")
	public ModelAndView getChannelList(Integer pageNo) {
		BaseNetEaseApiEntity<ChannelListEntity> list = netEaseCloudLiveService
				.getChannelList(15, pageNo, null, null);
		Map<String, Object> m = new HashMap<String, Object>();
		List<ChannelItemEntity> channelItem = new ArrayList<>();

		pageNo = 1;
		int pageSize = 0;
		int pageCount = 1;
		if (list != null) {
			if (list.getCode() == 200) {
				ChannelListEntity channelList = list.getRet();
				channelItem = channelList.getList();
				pageNo = channelList.getPnum();
				pageSize = channelList.getTotalRecords();
				pageCount = channelList.getTotalPnum();
			} else {
				m.put("errorMessage", "获取网易云直播频道列表失败，接口状态码：" + list.getCode()
						+ ",错误描述：" + list.getMsg());
			}
		}

		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);

		m.put("channelItem", channelItem);
		m.put("actionUrl", "etEaseColudLiveController/getChannelList.do");

		return new ModelAndView("cloudlive/netease/channelList", m);
	}

	/**
	 * 重新获取推流地址<br/>
	 * 用户创建频道时获取的推流地址失效时，重新获取推流地址。
	 * 
	 * @param cid
	 *            频道ID
	 * @return
	 */
	@RequestMapping("/getAddress")
	public ModelAndView getAddress(String cid) {
		Map<String, Object> m = new HashMap<String, Object>();

		BaseNetEaseApiEntity<AddressEntity> api = netEaseCloudLiveService
				.getAddress(cid);

		if (api.getCode() == 200) {
			m.put("address", api.getRet());
		} else {
			m.put("errorMessage", "获取网易云直播频道推流地址失败，接口状态码：" + api.getCode()
					+ ",错误描述：" + api.getMsg());
		}

		return new ModelAndView("cloudlive/netease/address", m);
	}
}
