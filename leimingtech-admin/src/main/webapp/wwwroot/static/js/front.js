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
	$("#" + vote).load(contextpath+"/front/voteTagAct.do?getVote", {
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
		url : contextpath+"/front/commentaryTagAct.do?addCommentary&contentId="+contentId,
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
		url : contextpath+"/front/commentaryTagAct.do?getCommentSize&t="+new Date().getTime(),
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
 *            展示评论列表的容器id
 */
Cms.commentList = function(id, option) {
	id = id || "commentListContainer";
	$("#" + id).load(contextpath+"/front/commentaryTagAct.do?getCommentList&t="+new Date().getTime(), option);
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
		url : contextpath+"/front/surveyTagAct.do?saveSurvey",
		data : $('#' + form).serialize(),
		dataType: "text",
		success : callback,
		error : function() {
			alert("提交通讯失败!");
		}
	});
};

/**
 * 加载视频播放器
 * @param div
 * @param videourl
 */
Cms.loadPlayer = function(div,videourl){
	$("#" + div).load(contextpath+"/front/videoTagData.do?getVideo", {
		videourl:videourl
	});
};

/**
 * 获取评论表单及列表
 * 
 * @param id
 *            展示评论表单及列表的容器id
 */
Cms.commentForm = function(id) {
	id = id || "commentContainer";
	$("#" + id).load(contextpath+"/front/commentaryTagAct.do?getCommentForm&contentId="+contentId+"&t="+new Date().getTime());
};

