package com.leimingtech.cms.service.impl.reply;

import com.leimingtech.cms.entity.reply.ReplyEntity;
import com.leimingtech.cms.service.reply.ReplyFrontServiceI;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: interface
 * @Description: 跟帖接口实现
 * @author
 * @date 2017-03-27 19:11:05
 * @version V1.0
 * 
 */
@Service("replyService")
@Transactional
public class ReplyFrontServiceImpl implements ReplyFrontServiceI {


    /** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存跟帖
	 * 
	 * @param reply
	 * @return
	 */
	public java.lang.String save(ReplyEntity reply) {
		return (java.lang.String) commonService.save(reply);
	}

	/**
	 * 更新跟帖
	 * 
	 * @param reply
	 */
	@Override
	public void saveOrUpdate(ReplyEntity reply) {
		commonService.saveOrUpdate(reply);
	}

	/**
	 * 通过id获取跟帖
	 * 
	 * @param id
	 *            跟帖id
	 * @return
	 */
	@Override
	public ReplyEntity getEntity(java.lang.String id) {
		return commonService.getEntity(ReplyEntity.class, id);
	}

	/**
	 * 获取分页后的跟帖数据集
	 * 
	 * @param reply
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return replyList跟帖数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(ReplyEntity reply, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(ReplyEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, reply, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<ReplyEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("replyList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除跟帖
	 * 
	 * @param reply
	 */
	@Override
	public void delete(ReplyEntity reply) {
		commonService.delete(reply);
	}

    @Override
    public Integer addReplySupport(String replyId) {
        String sql = "update cms_reply set supportcount = supportcount+1 where id = '"
                + replyId + "'";
        commonService.executeSql(sql);
        ReplyEntity reply = commonService.get(ReplyEntity.class, replyId);
        return reply.getSupportcount();
    }
    @Override
    public Integer deleteReplySupport(String replyId) {
        String sql = "update cms_reply set opposecount = opposecount+1 where id = '"
                + replyId + "'";
        commonService.executeSql(sql);
        ReplyEntity reply = commonService.get(ReplyEntity.class, replyId);
        return reply.getOpposecount();
    }

}