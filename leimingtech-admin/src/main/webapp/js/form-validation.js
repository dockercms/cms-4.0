(function($){
    $.fn.myValidate = function(options,isUseDefaultRules){
    	var create_error_view=$("<div class='alert alert-error hide'><button class='close' data-dismiss='alert'></button>有未填写项或错误项. 请检查后再提交.</div>");
    	var create_success_view=$("<div class='alert alert-success hide'><button class='close' data-dismiss='alert'></button>表单验证成功!</div>"); 
    	var selfForm=$(this);
    	var rules= {
				name: {
					minlength: 2,
					required: true
				},
				email: {
					required: true,
					email: true
				},
				category: {
					required: true
				},
				options1: {
					required: true
				},
				options2: {
					required: true
				},
				occupation: {
					minlength: 5,
				},
				membership: {
					required: true
				},
				service: {
					required: true,
					minlength: 2
				}
		};
    	if(isUseDefaultRules==false){
    		rules={};
    	}
    	if(!options){
    		options={
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules:rules,
	            messages: { // custom messages for radio buttons and checkboxes
	                membership: {
	                    required: "Please select a Membership type"
	                },
	                service: {
	                    required: "Please select  at least 2 types of Service",
	                    minlength: jQuery.format("Please select  at least {0} types of Service")
	                }
	            },

	            errorPlacement: function (error, element) { // render error placement for each input type
	                if (element.attr("name") == "education") { // for chosen elements, need to insert the error after the chosen container
	                    error.insertAfter("#form_2_education_chzn");
	                } else if (element.attr("name") == "membership") { // for uniform radio buttons, insert the after the given container
	                    error.addClass("no-left-padding").insertAfter("#form_2_membership_error");
	                } else if (element.attr("name") == "service") { // for uniform checkboxes, insert the after the given container
	                    error.addClass("no-left-padding").insertAfter("#form_2_service_error");
	                } else {
	                    error.insertAfter(element); // for other inputs, just perform default behavoir
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	            	 var successView=selfForm.find(".alert-success");
	              	if(successView.length!=0){
	              		successView.removeClass("show").addClass("hide");
	              	}
	              	
	              	var errorView=selfForm.find(".alert-error");
	              	if(errorView.length==0){
	              		errorView=create_error_view;
	              		selfForm.prepend(errorView);
	              	}
	              	errorView.removeClass("hide").addClass("show");
	                 App.scrollTo(errorView, -200);
	            	
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.help-inline').removeClass('ok'); // display OK icon
	                $(element)
	                    .closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
	            },

	            unhighlight: function (element) { // revert the change dony by hightlight
	                $(element)
	                    .closest('.control-group').removeClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                if (label.attr("for") == "service" || label.attr("for") == "membership") { // for checkboxes and radip buttons, no need to show OK icon
	                    label
	                        .closest('.control-group').removeClass('error').addClass('success');
	                    label.remove(); // remove error label here
	                } else { // display success icon for other inputs
	                    label
	                        .addClass('valid').addClass('help-inline ok') // mark the current input as valid and display OK icon
	                    .closest('.control-group').removeClass('error').addClass('success'); // set success class to the control group
	                }
	            },

	            submitHandler: function (form) {
	                var successView=selfForm.find(".alert-success");
	            	if(successView.length==0){
	            		successView=create_success_view;
	            		selfForm.prepend(successView);
	            	}
	            	successView.removeClass("hide").addClass("show");
	            	
	            	var errorView=selfForm.find(".alert-error");
	            	if(errorView.length!=0){
	            		errorView.removeClass("show").addClass("hide");
	            	}
	            }

	        }
    	}
    	selfForm.validate(options);
    }
})(jQuery);