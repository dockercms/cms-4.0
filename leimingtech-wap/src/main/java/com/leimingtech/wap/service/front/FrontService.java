package com.leimingtech.wap.service.front;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ContentsServiceI;

/**
 * wap 前台 服务
 * 
 * 
 * 
 */
@Service("frontService")
public class FrontService {
	@Autowired
	private ContentsServiceI contentsService;

	public String getFrontData( String catId, int pageSize,int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class, pageSize, pageNo, "", "");
		cq.eq("catid", catId);
		cq.eq("constants", "50"); //---------内容状态：正常-----------
		cq.add();
		PageList pageList = this.contentsService.getPageList(cq, true);
		List<ContentsEntity> contentsList = pageList.getResultList();
		int allpage = pageList.getCount()/pageSize+1;
		 
		
		if(pageNo<=allpage){
			JSONArray jArray = new JSONArray();
			for(ContentsEntity c :contentsList){
				JSONObject jObject = new JSONObject();
				jObject.accumulate("wapUrl", c.getWapurl());
				jObject.accumulate("title", c.getTitle());
				jObject.accumulate("thumb", c.getThumb());
				jArray.add(jObject);
			}
			return jArray.toString();
			
		}else{
			return "没有数据了";
		}
		
	}

}
