/**
 * 表单验证
 */
jQuery(document).ready(function() {   
   var form = $("#form_sample_1");
   var error = $('.error', form);
   var success = $('.success', form);
   
   var key=keyword.replace(/\s+/g,"")
	var keyWord2 = encodeURI(key);
	keyWord2 = encodeURI(keyWord2);
   form.validate({
        errorElement: 'span', //default input error message container
        errorClass: 'help-inline', // default input error message class
        focusInvalid: true, // do not focus the last invalid input
        ignore: "",
        rules: {
        	username: {
                minlength: 6,
                required: true,
                remote : "loginAct.do?checkName"
            },
            password :{
            	minlength : 6,
            	required : true
            },
            email :{
            	required : true,
            	email : true,
            	remote : {
            		type : "post",
            		url : "MemberAct.do?checkNameAndEmail",
            		data : {
            			username : function(){
            				return $("#username").val();
            			},
            			email : function(){
            				return $("#email").val();
            			}
            		}
            	}
            }
        },
        messages:{
        	username : {
        		required: "请输入用户名",
        		minlength : "用户名最少为6位",
        		remote : "用户名不存在"
        	},
			password :{
				required: "请输入密码",
        		minlength : "最少为6位"
            },
            email : {
            	required: "请输入邮箱",
            	email : "请输入正确的邮箱",
            	remote : "用户名或邮箱不正确"
            }
        },
        invalidHandler: function (event, validator) {              
            success.hide();
            error.show();
        },
        highlight: function (element) { 
        	$("#login_password").remove();
            $(element)
                .closest('.help-inline').removeClass('ok'); 
            $(element)
                .closest('.control-group').removeClass('success').addClass('error'); 
        },
        unhighlight: function (element) { 
            $(element)
                .closest('.control-group').removeClass('error'); 
        },
        success: function (label) {
            label.addClass('valid').addClass('help-inline ok') 
            .closest('.control-group').removeClass('error').addClass('success'); 
        },
        submitHandler: function (form) {
            success1.show();
            error1.hide();
        }
    });
});

/**
 * 回车事件
 */
$(document).keydown(function(event) {
	
	if (event.keyCode == 13) {
		$(".input-submit-style-3").click();
	}
});

/**
 * 登录提交事件
 * @param {} urlStr
 * @param {} formName
 */
function formSubmitModel1(urlStr, formName) {
	var username=$("#username").val();
	   var password=$("#password").val();
	   if(/\s/.test(username)){
	       alert('用户名不正确');
	       return false;
	   }
	if($('#' + formName).valid()){
		$.ajax({
			type : 'post',
			url : urlStr,
			data : $('#' + formName).serialize(),
			success : function(msg) {
				var obj = JSON.parse(eval(msg));
				var toUrl = obj.toUrl;
				
				if (!toUrl && typeof (toUrl) != "undefined" && toUrl != 0
						&& toUrl != '') {
					toUrl = arguments[2] + '';
				}
				if (!obj.isSuccess) {
					var errortype = obj.errortype;
					$("#login_" + errortype).css("display", "block");
					$("#login_" + errortype).addClass("help-inline ok");
					$("#login_" + errortype).html(obj.msg);
					$("#login_" + errortype).attr("htmlfor", errortype);
					$("#login_" + errortype).closest('.control-group').removeClass('success').addClass('error');
					
				}else{
					location.href = toUrl;
				}
			},
			error : function() {
				alert("提交通讯失败!");
			}
		});
	}
}

