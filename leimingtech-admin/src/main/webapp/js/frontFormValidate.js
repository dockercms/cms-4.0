/*
 * 前台表单验证
 */
 
function validateObj(fromid){
	
	var form = $("#" + fromid);
   	var error = $('.error', form);
   	var success = $('.success', form);
   	var create_error_view=$("<div class='alert alert-error hide'><button class='close' data-dismiss='alert'></button>有未填写项或错误项. 请检查后再提交.</div>");
	var create_success_view=$("<div class='alert alert-success hide'><button class='close' data-dismiss='alert'></button>表单验证成功!</div>");
	var validateform = form.validate({
        errorElement: 'span', // default input error message container
        errorClass: 'help-inline', // default input error message class
        focusInvalid: true, // do not focus the last invalid input
        ignore: "",
        rules: {
        	username: {
                minlength: 6,
                maxlength :16,
                required: true
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
            email :{
            	required : true,
            	email : true
            },
            phone : {
            	required : true,
            	isphone : true
            },
          /*  qq : {
            	required : true,
            	isqq: true
            },*/
            istelephone : {
            	rrequired : true,
            	istelephone : true
            }
        },
        messages:{
        	username : {
        		required: "请输入用户名",
        		minlength : "用户名最少为6位",
        		maxlength : "用户名最多为16位"
        	},
			password :{
				required: "请输入密码",
        		minlength : "最少为6位",
        		maxlength : "最长为16位"
            },
            confirm_password : {
            	required: "请输入确认密码",
         		minlength : "密码最小长度为6个字符",
         		maxlength : "密码最大长度为16个字符",
         		equalTo : "两次输入的密码不一致"
            },
            email : {
            	required: "请输入邮箱",
            	email : "请输入正确的邮箱"
            },
            phone : {
            	required : "请输入手机号"
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
            success.show();
            error.hide();
        }
    });
    
    return validateform;
};

/**
 * 写入自己的验证，非公用的验证， 如：title:{required:true}
 * @param {} obj   如：rules
 * @param {} prop  如：title
 * @param {} val   如：{required:true}
 */
function createJson(obj, prop, val) {
    // 如果 val 被忽略
    if(typeof val === "undefined") {
        // 删除属性
        delete obj[prop];
    } else {
        // 添加 或 修改
        obj[prop] = val;
    }
}

//扩展方法--手机号码验证
jQuery.validator.addMethod("isphone", function(value, element) {
    var length = value.length;
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1}))+\d{8})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写您的手机号码");

//扩展方法--qq号码验证
jQuery.validator.addMethod("isqq", function(value, element) {
    var length = value.length;
    var qq = /^\d{5,10}$/;
    return this.optional(element) || qq.test(value);
}, "请正确填写您的qq号码");

//扩展方法--固定电话验证
jQuery.validator.addMethod("istelephone", function(value, element) {
    var length = value.length;
    var telephone = /^\d{2,5}-\d{7,8}$/;
    return this.optional(element) || telephone.test(value);
}, "请正确填写固定电话");


