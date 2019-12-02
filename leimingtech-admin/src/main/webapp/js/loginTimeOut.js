$(function(){
	var loginTimeOut='<div id="loginTimeOut"  class="modal fade loginTimeOut" tabindex="-1" data-backdrop="static" data-focus-on="input:first" data-width="320">'
		+'<div class="modal-header">'
		+'<span class="modal-title" >'
		+'<a href="javascript:void(0);" class="glyphicons no-js lock" style="margin-bottom: 10px;"><i></i></a>'
		+'<span style="font-size: 18px;">超时登录<span style="font-size: 12px; color: red;" class="loginHint"></span></span>'
		+'</span>'
		+'</div>'
		+'<div class="modal-body" style="height: 140px;">'
		+'<div class="content">'
		+'<form novalidate="novalidate" name="form1" class="form-vertical login-form" method="post" action="loginAction.do?login">'
		+'<div class="alert alert-error hide">'
		+'<button class="close" data-dismiss="alert"></button>'
		+'<span>输入您的用户名和密码</span>'
		+'</div>'
		+'<div class="control-group">'
		+'<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->'
		+'<label class="control-label visible-ie8 visible-ie9">用户名</label>'
		+'<div class="controls">'
		+'<div class="input-icon left userName">'
		+'<i class="icon-user"></i>'
		+'<input class="m-wrap placeholder-no-fix loginTimeOutUsrName" autocomplete="off" placeholder="用户名" id="userName" name="userName" value="" type="text" style="width: 249px;">'
		+'</div>'
		+'</div>'
		+'</div>'
		+'<div class="control-group">'
		+'<label class="control-label visible-ie8 visible-ie9">密码</label>'
		+'<div class="controls">'
		+'<div class="input-icon left password">'
		+'<i class="icon-lock"></i>'
		+'<input class="m-wrap placeholder-no-fix loginTimeOutPassword" autocomplete="off" placeholder="密码" id="password" name="password" value="" type="password"  style="width: 249px;">'
		+'</div>'
		+'</div>'
		+'</div>'
		+'<div class="control-group">'
		+'<label class="control-label visible-ie8 visible-ie9">验证码</label>'
		+'<div class="controls">'
		+'<div class="input-icon left">'
		+'<i class="icon-lock"></i>'
		+'<input class="m-wrap placeholder-no-fix" style="width:160px;" onfocus="change()" type="text" placeholder="验证码" id="randCode" name="randCode"/>'
		+'<div style="float: right;">'
		+'<img id="randCodeImage" title="点击换一张" src="randCodeImage" onclick="change()"/>'
		+'</div>'
		+'</div>'
		+'</div>'
		+'</div>'
		+'<span id="point"></span>'
		+'<span id="loginOn" class="btn blue pull-right" onclick="check()">'
		+'登录 <i class="m-icon-swapright m-icon-white"></i>'
		+'</span>'
		+'</form>'
		+'</div>'
		+'</div>'
		+'</div>'
		+'<div class="modal fade nonsupportCookies" tabindex="-1" data-backdrop="static" data-focus-on="input:first" data-width="250">'
		+'<div class="modal-body">'
		+'<div class="content">'
		+'</div>'
		+'<span style="color: red;" class="errorHint"></span>'
		+'</div>'
		+'<center>'
		+'<span id="supportCookiesBtn" class="btn green"style="display: none;">'
		+'我已开启 <i class="m-icon-swapright m-icon-white"></i>'
		+'</span> '
		+'<span id="nonsupportCookiesBtn" class="btn red">'
		+'退出登陆 <i class="m-icon-swapright m-icon-white"></i>'
		+'</span> '
		+'</center>'
		+'<br>'
		+'</div><iframe style="display: none;" class="simulateLogin"></iframe>';
	if($(".loginTimeOut").length==0){
		$("body").eq(0).append(loginTimeOut);
	}
//	var $=jQuery;//jQuery.noConflict();
	var hint1="您的浏览器不支持Cookie,请开启浏览器的Cookie功能或更换支持Cookie的浏览器!";
	var hint2="您的浏览器关闭了Cookie功能,请开启浏览器的Cookie功能或更换支持Cookie的浏览器!";
	var hint3="检测到Cookie仍不可用!";
	$("#nonsupportCookiesBtn").click(function(){
		window.location.href="loginAction.do?login";
	});
	$("#supportCookiesBtn").click(function(){
		if(!$.cookies.test()){
			$(".errorHint").html(hint3);
			return ;
		}
		$(".errorHint").html("");
		$(".nonsupportCookies").modal("hide");
	});
	
	if(!$.cookies.test()){
		$(".nonsupportCookies").modal("show").find(".content").html(hint1);
		$(".nonsupportCookies").find(".green").hide();
	}else{
		var cookiesLoginTimer=$.cookies.get("loginTimer");
		if(isNaN(cookiesLoginTimer)||cookiesLoginTimer==null){
			$.cookies.set("loginTimer",1);
		}
		
		$("*").click(function(){
			if($(this).parents(".loginTimeOut").length>0){
				return false;
			}
			$.cookies.set("loginTimer",1);
		});
	}
	$(".loginTimeOutUsrName,.loginTimeOutPassword").keyup(function(event){
		if(event.which==13){
			check();
		}
	});
});
var loginTimeOut=30*60;//自定义客户端系统未操作超时时间
var loginTimeOuttimer;
listenTimeOut();
function listenTimeOut(){
	loginTimeOuttimer=setInterval(function(){
		if(!$.cookies.test()){
			$(".nonsupportCookies").modal("show").find(".content").html(hint2);
			$(".nonsupportCookies").find(".green").show();
		}
		var loginTimer=$.cookies.get("loginTimer");
		loginTimer=parseInt(loginTimer)+1;
		if(loginTimer>=loginTimeOut){
			$("#loginTimeOut").modal("show");
			clearInterval(loginTimeOuttimer);
		}else{
			$.cookies.set("loginTimer",loginTimer);
		}
	},1000);
}
document.onkeydown=function(event){
	var e = event || window.event || arguments.callee.caller.arguments[0];

	if(e && e.keyCode==13){ // enter 键
		check();
	}
};
function check(){
	var oldLoginUserName=$("#nowLoginUserName").val();
	var urlStr = 'loginAction.do?checkuser';
	$('#randCode').attr("disabled",true);
	$('#userName').attr("disabled",true);
	$('#password').attr("disabled",true);
	var userName = $('#userName').val();
	var password = $('#password').val();
	var randCode = $('#randCode').val();
	if(oldLoginUserName!=userName){
		$(".loginHint").html("正在切换用户...");
	}else{
		$(".loginHint").html("正在重新连接...");
	}
	$.ajax({
		type:'post',
		url:urlStr,
		data:{'userName':userName,'password':password,'randCode':randCode},
		dataType:'text',
		success:function(msg){
			$(".loginHint").html("");
			$('#userName').attr("disabled",false);
			$('#password').attr("disabled",false);
			$('#randCode').attr("disabled",false);
			var obj = JSON.parse(eval(msg));
			if(obj.isSuccess){
				if(!$.cookies.test()){
					$(".nonsupportCookies").modal("show").find(".content").html(hint1);
					$(".nonsupportCookies").find(".green").hide();
				}else{
					$.cookies.set("loginTimer",1);
					if(oldLoginUserName!=userName){
						location.href="loginAction.do?login";
					}else{
						
						$('#userName').val("");
						$('#password').val("");
						$('#randCode').val("");
						$("#loginTimeOut").modal("hide");
						$(".simulateLogin").attr("src","loginAction.do?login");
						window.location.reload();//刷新当前页面
						listenTimeOut();
					}
				}
			}else{
				document.getElementById("point").innerHTML = 
					"<img src='media/image/action_stop.gif'><span style='margin-left:10px;color:#990033'>"+obj.msg+"</span>";
				 	//若验证码输入错误则更新
					document.getElementById("randCodeImage").src="randCodeImage?"+Math.random();
			}
		},
		error:function(){
			alert("load page error, page url is " + lefUrl);
		},
		complete:function (){
			$("#loginOn").removeAttr("disabled","disabled","disabled");
		}
	});
}
function change(){
		document.getElementById("randCodeImage").src="randCodeImage?"+Math.random();
}
