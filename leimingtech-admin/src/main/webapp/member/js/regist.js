/**
 * 回车事件
 */
$(document).keydown(function(event) {
	if (event.keyCode == 13) {
		$(".input-submit-style-3").click();
	}
});

/**
 * 输新验证码
 */
$('#randCodeImage').click(function(){
    reloadRandCodeImage();
});

/**
 * 刷新验证码
 */
function reloadRandCodeImage() {
    var date = new Date();
    var img = document.getElementById("randCodeImage");
    img.src=ctx+'/randCodeImage?a=' + date.getTime();
}

/**
 * 验证表单
 */
jQuery(document).ready(function() {   
	//FormValidation.init();
	var form = $("#register_form");
	var error = $('.alert-error', form);
    var success = $('.alert-success', form);
	   
    form.validate({
         errorElement: 'span', //default input error message container
         errorClass: 'help-inline', // default input error message class
         focusInvalid: true, // do not focus the last invalid input
         ignore: "",
         rules: {
         	username: {
                 minlength: 6,
                 maxlength: 16,
                 required: true,
                 remote : "registerAct.do?checkUsername"
             },
             email:{
             	required:true,
             	email : true
             
             },
             password :{
             	minlength : 6,
             	maxlength :16,
             	required : true
             },
             confirm_password : {
            	 required : true,
            	 minlength : 6,
            	 maxlength : 16,
            	 equalTo : "#password"
             },
             valicode : {
            	 required : true,
            	 remote : "registerAct.do?checkValicode"
             }
         },
         messages:{
         	username : {
         		required: "请输入用户名",
         		minlength : "用户名最小长度为6个字符",
         		maxlength : "用户名最大长度为16个字符",
         		remote : "用户名已存在"
         	},
         	email : {
         		required : "请输入邮箱",
         		email : "邮箱格式不正确"
         	},
			password :{
				required: "请输入密码",
         		minlength : "密码最小长度为6个字符",
         		maxlength : "密码最大长度为16个字符"
            },
            confirm_password : {
            	required: "请输入确认密码",
         		minlength : "密码最小长度为6个字符",
         		maxlength : "密码最大长度为16个字符",
         		equalTo : "两次输入的密码不一致"
            },
            valicode : {
           		required : "请输入验证码",
           		remote : "验证码输入错误"
            }
         },
         invalidHandler: function (event, validator) {              
             success.hide();
             error.show();
         },
         highlight: function (element) { 
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
 * 表单提交
 * @param {} urlStr
 * @param {} formName
 */
function formSubmitModel(urlStr, formName) {

	if($('#' + formName).valid()){
		$.ajax({
			type : 'post',
			url : urlStr,
			data : $('#' + formName).serialize(),
			success : function(msg) {
				var obj = JSON.parse(eval(msg));
				var toUrl = obj.url;
				var code = obj.code;
				if (code == "1") {
					alert(obj.info);
				}else{
					alert(obj.info);
					location.href = toUrl;
				}
			},
			error : function() {
				alert("提交通讯失败!");
			}
		});
	}
	
}

function subform(urlStr, formName) {
	if($('#' + formName)){
		$.ajax({
			type : 'post',
			url : urlStr,
			data : $('#' + formName).serialize(),
			success : function(msg) {
				var obj = JSON.parse(eval(msg));
				var toUrl = obj.url;
				var code = obj.code;
				if (code == "1") {
					alert(obj.info);
				}else{
					alert(obj.info);
					location.href = toUrl;
				}
			},
			error : function() {
				alert("提交通讯失败!");
			}
		});
	}
	
}