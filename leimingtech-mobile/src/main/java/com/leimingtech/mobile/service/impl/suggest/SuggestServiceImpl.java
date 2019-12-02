package com.leimingtech.mobile.service.impl.suggest;

import com.leimingtech.core.entity.BeanApi;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.entity.suggest.SuggestEntity;
import com.leimingtech.mobile.service.suggest.SuggestServiceI;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.util.Date;

@Service("suggestService")
@Transactional
public class SuggestServiceImpl extends CommonServiceImpl implements SuggestServiceI {

	/**移动频道管理接口*/
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	
	/**
	 * 意见反馈接口
	 * @param userId
	 * @param content
	 * @param email
	 * 
	 * @return string
	 */
	@Override
	public JSONObject getSuggest(String all, String content,String email,String userId){
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try{
			if(StringUtil.isNotEmpty(all)){
				beanApi.setResultCode("1");
				beanApi.setResultMsg("反馈成功");
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			}else{
				if(StringUtil.isNotEmpty(userId)){
					MemberEntity member = get(MemberEntity.class,  userId  );
					if(member!=null){
						SuggestEntity suggest = new SuggestEntity();
						suggest.setUserId( member.getId()+"" );
						suggest.setEmail(email);
						content = URLDecoder.decode(content,"utf-8");
						suggest.setSuggestContent(content);
						suggest.setCreatedtime(new Date());//创建时间
						save(suggest);
						beanApi.setResultMsg("感谢您的反馈~");
						beanApi.setResultCode("1");
						json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
					}else{
						beanApi.setResultMsg("找不到用户，请重新登录后反馈");
						beanApi.setResultCode("0");
						beanApi.setItems(null);
						json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
					}
				}else{
					beanApi.setResultMsg("请登录之后进行反馈");
					beanApi.setResultCode("0");
					beanApi.setItems(null);
					json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
				}
			}
			
		}catch(Exception e1){
			e1.printStackTrace();
			beanApi.setResultMsg("服务器繁忙，请稍后重试");
			beanApi.setResultCode("0");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}
	
}