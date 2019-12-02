jQuery(document).ready(function() {   
   var form = $("#form_sample_1");
   var error = $('.error', form);
   var success = $('.success', form);
   form.validate({
        errorElement: 'span', //default input error message container
        errorClass: 'help-inline', // default input error message class
        focusInvalid: true, // do not focus the last invalid input
        ignore: "",
        rules: {
        	username: {
                required: true,
                remote : "loginAct.do?checkName"
            }
        },
        messages:{
        	username : {
        		required: "请输入用户名",
        		remote : "用户名不存在"
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