Cms = {};

/**
 * 投票列表
 * 
 * @param base
 * @param vote
 *            列表展示位置
 * @param contentid
 *            文章id
 */
Cms.voteList = function(vote, contentid) {
	vote = vote || "votecontent";
	$("#" + vote).load("/front/voteTagAct.do?getVote", {
		contentid : contentid
	});
};

/**
 * 提交评论
 * 
 * @param callback
 *            成功回调函数
 * @param form
 *            表单
 */
Cms.comment = function(callback,form) {
	form = form || "commentForm";
	$.ajax({
		type : 'post',
		url : "/front/commentaryTagAct.do?addCommentary&contentId="+contentId,
		data : $('#' + form).serialize(),
		dataType: "text",
		success : callback,
		error : function() {
			alert("提交通讯失败!");
		}
	});
};

/**
 * 获取总评论数
 * 
 * @param id
 *            用于显示数量的标签id
 */
Cms.commentSize = function(id) {
	id = id || "s_commentsize";
	$.ajax({
		type : 'post',
		url : "/front/commentaryTagAct.do?getCommentSize&t="+new Date().getTime(),
		data : {"contentId": contentId},
		success : function(size) {
			$("#" + id).text(size);
		}
	});
};

/**
 * 获取评论列表
 * 
 * @param id
 *            展示评论列表的标签id
 */
Cms.commentList = function(id, option) {
	id = id || "commentListContainer";
	$("#" + id).load("/front/commentaryTagAct.do?getCommentList&t="+new Date().getTime(), option);
};

/**
 * 提交调查
 * 
 * @param callback
 *            成功回调函数
 * @param form
 *            表单
 */
Cms.survey = function(callback,form) {
	form = form || "surveyForm";
	$.ajax({
		type : 'post',
		url : "/lmcms/front/surveyTagAct.do?saveSurvey",
		data : $('#' + form).serialize(),
		dataType: "text",
		success : callback,
		error : function() {
			alert("提交通讯失败!");
		}
	});
};

