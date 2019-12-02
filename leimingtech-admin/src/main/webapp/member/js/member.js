/*
 * 会员中心
 */

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $(".searchbtn").click();
        return false;
    };
});

//update-begin--Author:zhangguoming  Date:20140226 for：添加验证码
$('#randCodeImage').click(function() {
	reloadRandCodeImage();
});
/**
 * 刷新验证码
 */
function reloadRandCodeImage() {
	var date = new Date();
	var img = document.getElementById("randCodeImage");
	img.src = contextpath + '/randCodeImage?a=' + date.getTime();
}

function personal() {
	$.ajax({
		type : 'post',
		url : 'loginAct.do?personal',
		data : 't=' + new Date().getTime(),
		dataType : 'text',
		success : function(msg) {
			$('#rightcontent').empty();
			$('#rightcontent').append(msg);
		},
		error : function() {
			alert("load page error, page url is " + urlStr);
		}
	});
}

/*
 * 修改密码
 */
function passwordModify() {
	$.ajax({
		type : 'post',
		url : 'loginAct.do?passwordModify',
		data : 't=' + new Date().getTime(),
		dataType : 'text',
		success : function(msg) {
			$('#rightcontent').empty();
			$('#rightcontent').append(msg);
		},
		error : function() {
			alert("load page error, page url is " + urlStr);
		}
	});
}

/*
 * 我的留言
 */
function guestBook() {
	$.ajax({
		type : 'post',
		url : 'guestBookFrontController.do?guestBook',
		data : 't=' + new Date().getTime(),
		dataType : 'text',
		success : function(msg) {
			$('#rightcontent').empty();
			$('#rightcontent').append(msg);
		},
		error : function() {
			alert("load page error, page url is " + urlStr);
		}
	});
}

/*
 * 我的评论
 */
function commentary() {
	$.ajax({
		type : 'post',
		url : 'commentaryFrontController.do?commentary',
		data : 't=' + new Date().getTime(),
		dataType : 'text',
		success : function(msg) {
			$('#rightcontent').empty();
			$('#rightcontent').append(msg);
		},
		error : function() {
			alert("load page error, page url is " + urlStr);
		}
	});
}

/*
 * 我的收藏
 */
function collect() {
	$.ajax({
		type : 'post',
		url : 'collectFrontController.do?collect',
		data : 't=' + new Date().getTime(),
		dataType : 'text',
		success : function(msg) {
			$('#rightcontent').empty();
			$('#rightcontent').append(msg);
		},
		error : function() {
			alert("load page error, page url is " + urlStr);
		}
	});
}

/*
 * 评论展现列表
 */
function commentaryListTest() {
	$.ajax({
		type : 'post',
		url : 'commentaryFrontController.do?commentaryListTest',
		data : 't=' + new Date().getTime(),
		dataType : 'text',
		success : function(msg) {
			$('#rightcontent').empty();
			$('#rightcontent').append(msg);
		},
		error : function() {
			alert("load page error, page url is " + urlStr);
		}
	});
}

/**
 * 退出登录
 */
function exitLogin() {
	$.ajax({
		type : 'post',
		url : 'loginAct.do?exitLogin',
		data : 't=' + new Date().getTime(),
		dataType : 'text',
		success : function(msg) {
			var obj = JSON.parse(eval(msg));
			var toUrl = obj.toUrl;
			if (!toUrl && typeof (toUrl) != "undefined" && toUrl != 0
					&& toUrl != '') {
				toUrl = arguments[2] + '';
			}
			alert(obj.msg);
			location.href = toUrl;
		},
		error : function() {
			alert("服务器连接异常！");
		}
	});
}

/**
 * 会员搜索
 * 
 * @param action
 *            表单提交位置
 * @param formName
 */
function memberSearch(action, formName) {
	location.href = action + "&" + $('#' + formName).serialize();
}

function resetForm(form){
	$(':input','#'+form).not(':button,:submit,:reset,:hidden').val('').removeAttr('checked').removeAttr('checked');
}
