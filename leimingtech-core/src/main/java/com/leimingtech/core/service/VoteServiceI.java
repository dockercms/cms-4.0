package com.leimingtech.core.service;

import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.VoteEntity;

public interface VoteServiceI extends CommonService{

	/**
	 * 保存投票
	 * @param contents
	 * @param vote
	 */
	void saveVote(ContentsEntity contents,VoteEntity vote);
	/**
	 * 修改投票
	 * @param contents
	 * @param vote
	 * @param contentcat
	 * @param voteOptionList
	 * @param temporary
	 * @param divValue
	 */
	void updateVote(ContentsEntity contents, VoteEntity vote,
			ContentCatEntity contentcat, String temporary, String divValue);

	/**
	 * 根据内容id获取投票
	 * @param contentId 内容id
	 * @return
	 */
	VoteEntity getVoteByContentId(String contentId);
}
