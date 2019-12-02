	//用于缩略图区别（如果出现多个图片库的情况）
	var pathid = "";
	// 相关内容-----点击搜索按钮
	function toEdit(seek,temporary,content_id){
		//var seek = $("#correlation").val();
	 	var urlStr = "contentsMobileController.do?correlationDialog&temporary="+temporary
	 		+"&content_id="+content_id
	 		+"&seek="+seek;
		initSWFUload = null;
		$.ajax({
			type:'post',
			url:urlStr,
			data:'data=0',
			dataType:'text',
			success:function(msg){
				$('#contentModel').empty();
				$('#contentModel').append(msg);
				$('#contentModel').modal('show');
				if(initSWFUload){
					initSWFUload();
				}
			},
			error:function(){
				lmAlert("load page error, page url is " + urlStr);
			}
		});
	}
	//相关内容--------手工添加 
	function toAdd(urlStr){
		initSWFUload = null;
		$.ajax({
			type:'post',
			url:urlStr,
			data:'data=0',
			dataType:'text',
			success:function(msg){
				$('#showModel').empty();
				$('#showModel').append(msg);
				$('#showModel').modal('show');
				if(initSWFUload){
					initSWFUload();
				}
			},
			error:function(){
				lmAlert("load page error, page url is " + urlStr);
			}
		});
	}
	function liveTip(){
		var $liveTip = $('<div id="livetip"></div>').hide().appendTo('body');
		$('.left').bind('mouseover mouseout mousemove',
		function(event) {
			var $link = $(event.target).closest('input');
			if (!$link.length) {
				return;
			}
			var link = $link[0];
			if(!link.value){
				return;
			}
			if (event.type == 'mouseover'|| event.type == 'mousemove') {
				$liveTip.css({
					top : event.pageY + 12,
					left : event.pageX + 12
				});
			};
			if (event.type == 'mouseover') {
				$liveTip.html('<div><img src=' + link.value + ' width=150 /></div>').show();
			};
			if (event.type == 'mouseout') {
				$liveTip.hide();
			};
		});
	}
	$('.date-picker').datepicker({
	    rtl : App.isRTL()
	});
	
	//统计字数
	function gbcount(message,total,used){
		var max;
		max = total.value;
		if (message.value.length > max) {
			message.value = message.value.substring(0,max);
			used.value = max;
			lmAlert("不能超过"+total.value+"个字!");
		}
		else {
		used.value = message.value.length;
		}
	}
	//ztree属性下拉树选择

	var code;
	function setCheck() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		py = $("#py").attr("checked")? "p":"",
		sy = $("#sy").attr("checked")? "s":"",
		pn = $("#pn").attr("checked")? "p":"",
		sn = $("#sn").attr("checked")? "s":"",
		type = { "Y":py + sy, "N":pn + sn};
		zTree.setting.check.chkboxType = type;
		showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
	}
	function showCode(str) {
		if (!code) code = $("#code");
		code.empty();
		code.append("<li>"+str+"</li>");
	}

	function beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
	
	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getCheckedNodes(true),
		v = "",vid="";
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			vid += nodes[i].id + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (vid.length > 0 ) vid = vid.substring(0, vid.length-1);
		var cityObj = $("#souContent");
		var mobileId = $("#mobileId");
		cityObj.attr("value", v);
		mobileId.attr("value",vid);
	}

	function showMenu() {
		var cityObj = $("#souContent");
		var cityOffset = $("#souContent").offset();
		$("#menuContent").css({left:"0px", top:cityObj.outerHeight()+ "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "souContent" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	function pushMobile(classify,contentId){
		var urlStr = 'contentsMobileController.do?checkContent&classify='+classify+'&chestr='+contentId;
		var formName = 'form_sample_2';
		$.ajax({
			type:'post',
			url:urlStr,
			data:$('#' + formName).serialize(),
			success:function(msg){
				var obj = JSON.parse(eval(msg));
				if(obj.isSuccess){
					classifyMan(obj);
					$('#editModel').empty();
					$('#editModel').modal('hide');
				}else{
					lmAlert("提交处理失败!");
				}
			},
			error:function(){
				lmAlert("提交通讯失败!");
			}
		});
	}
	function classifyMan(obj){
		var contents = obj.content;
		var article = obj.article;
		var pictureGroup = obj.pictureGroup;
		var link = obj.link;
		var video = obj.video;
		var vote = obj.vote;
		var survey = obj.survey;
		var surveyOptionSize = obj.surveyOptionSize;
		$("#title").attr("value",contents.title);
		$("#select2_sample5").attr("value",contents.sourceid);
		$("#digest").attr("value",contents.digest);
		$("#used").attr("value",0);
		$("#listThumbnail").attr("value",contents.thumb);
		$("#author").attr("value",contents.author);
		$("#editor").attr("value",contents.editor);
		//文章
		if(article!=null){
			UE.getEditor('ueditor').setContent(article.content);//文章编辑器内容
			//内容标记
			var selectCount = document.getElementById("contentMark").options;
			for(var i = 0 ; i<selectCount.length;i++){  
				if(selectCount[i].value==contents.contentMark){  
					selectCount[i].selected = true;
				}  
			}
		}
		//组图
		if(pictureGroup!=null){
			pictureChange(pictureGroup.url,pictureGroup.id);
			$("#pictureId").attr("value",pictureGroup.id);
		}
		//链接
		if(link!=null){
			$("#isposter").attr("value",contents.isposter);
			$("#linkurl").attr("value",link.linkurl);
		}
		//视频
		if(video!=null){
			$("#videobackpath").attr("value",video.videourl);
			$("#time").attr("value",video.time);
		}
		//投票
		if(vote!=null){
			$("#vh").css("display",'block');
			$("#voteId").attr("value",vote.id);
//			$("#votepattern").attr("value",vote.votepattern);
//			$("#votetype").attr("value",vote.votetype);
			if(vote.voteiplimit==""||vote.voteiplimit=="0"||vote.voteiplimit==null){
				document.getElementById('iplimit1').innerHTML = "无限制";
			}else{
				$("#voteiplimit").attr("value",vote.voteiplimit);
				document.getElementById('voteiplimit1').innerHTML = vote.voteiplimit;
			}
			if(""!=vote.id&&null!=vote.id){
//				if(vote.votepattern=="10"){document.getElementById('votepattern1').innerHTML = "普通模式"};
//				if(vote.votepattern=="20"){document.getElementById('votepattern1').innerHTML = "评选模式"};
//				if(vote.votepattern=="10"){document.getElementById('votepattern1').checked = true};
//				if(vote.votepattern=="20"){document.getElementById('votepattern2').checked = true};
//				if(vote.votetype=="10"){document.getElementById('votetype1').checked = true};
//				if(vote.votetype=="20"){document.getElementById('votetype2').checked = true};
			}else{
				document.getElementById('votepattern1').innerHTML = "无限制";
			}
			voteOptionView(vote.id);
		}
		//调查
		if(survey!=null){
			if(survey.surveypeolimit==""||survey.surveypeolimit=="0"||survey.surveypeolimit==null){
				document.getElementById('surveypeolimit1').innerHTML = "无限制";
			}else{
				$("#surveypeolimit").attr("value",survey.surveypeolimit);
				document.getElementById('surveypeolimit1').innerHTML = survey.surveypeolimit;
			}
			if(survey.surveyiplimit==""||survey.surveyiplimit=="0"||survey.surveyiplimit==null){
				document.getElementById('iplimit1').innerHTML = "无限制";
			}else{
				$("#surveyiplimit").attr("value",survey.surveyiplimit);
				document.getElementById('surveyiplimit1').innerHTML = survey.surveyiplimit;
			}
			if(obj.surveyOptionSize==0){
				$("#detail").css("diaply","none");
			}
			$("#surveyId").attr("value",survey.id);
			$("#vh").css("display",'block');
			document.getElementById('size').innerHTML = obj.surveyOptionSize;
		}
	}
	//组图用以保存、显示
	function pictureChange(pictureGroupUrl,pictureGroupId){
		
		$.ajax({
			type:'post',
			url:'pictureAloneMobileController.do?pictureList',
			data:{'pictureGroup':pictureGroupUrl,'pictureGroupId':pictureGroupId},
			dataType:'text',
			success:function(msg){
				$('#pictureDiv').empty();
				$('#pictureDiv').append(msg);
			},
			error:function(){
				lmAlert("load page error, page url is " + urlStr);
			}
		});
	}
	//投票结果显示
	function voteOptionView(voteId){
		$.ajax({
			type:'post',
			url:'voteMobileController.do?voteOptionView',
			data:{'voteId':voteId},
			dataType:'text',
			success:function(msg){
				$('#resultView').empty();
				$('#resultView').append(msg);
			},
			error:function(){
				lmAlert("load page error, page url is " + urlStr);
			}
		});
	}

	
/**
 * 移动文章置顶或者取消置顶
 * 
 * @param id
 */
function toTop(id) {
	var el = $(".page-header-fixed");
	App.blockUI(el);
	var urlStr = "contentsMobileOperateController.do?updateTop&contentId=" + id;
	$.ajax({
		type : 'post',
		url : urlStr,
		dataType : 'text',
		success : function(data) {
			var obj = JSON.parse(eval(data));
			if (obj.isSuccess) {
				changePage(obj.toUrl); 
			} else {
				lmAlert(obj.msg);
			}
		},
		error : function() {
			lmAlert("操作失败");
		},
		complete : function() {
			App.unblockUI(el);
		}
	});
}

/**
 * 移动文章设为头图或者取消头图
 * 
 * @param id
 */
function toTopPic(id) {
	var el = $(".page-header-fixed");
	App.blockUI(el);
	var urlStr = "contentsMobileOperateController.do?updateTopPic&contentId=" + id;
	$.ajax({
		type : 'post',
		url : urlStr,
		dataType : 'text',
		success : function(data) {
			var obj = JSON.parse(eval(data));
			if (obj.isSuccess) {
				changePage(obj.toUrl); 
			} else {
				lmAlert(obj.msg);
			}
		},
		error : function() {
			lmAlert("操作失败");
		},
		complete : function() {
			App.unblockUI(el);
		}
	});
}


