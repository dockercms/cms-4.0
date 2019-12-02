var UIToastr = function () {

    return {
        success:function (msg,title,onclick){
        	toastr.options = {
				"closeButton" : true,
				"debug" : false,
				"positionClass" : "toast-bottom-right",//Top Right、Bottom Right、Bottom Left、Top Left、Top Center、Bottom Center、Top Full Width、Bottom Full Width
				"onclick" : onclick,
				"showDuration" : "1000",
				"hideDuration" : "1000",
				"timeOut" : "5000",
				"extendedTimeOut" : "1000",
				"showEasing" : "swing",
				"hideEasing" : "linear",
				"showMethod" : "fadeIn",
				"hideMethod" : "fadeOut"
			}
        	var $toast = toastr['success'](msg, title);
        },
        info:function (msg,title,onclick){
        	toastr.options = {
				"closeButton" : true,
				"debug" : false,
				"positionClass" : "toast-bottom-right",//Top Right、Bottom Right、Bottom Left、Top Left、Top Center、Bottom Center、Top Full Width、Bottom Full Width
				"onclick" : onclick,
				"showDuration" : "1000",
				"hideDuration" : "1000",
				"timeOut" : "5000",
				"extendedTimeOut" : "1000",
				"showEasing" : "swing",
				"hideEasing" : "linear",
				"showMethod" : "fadeIn",
				"hideMethod" : "fadeOut"
			}
        	var $toast = toastr['info'](msg, title);
        },
        warning:function (msg,title,onclick){
        	toastr.options = {
				"closeButton" : true,
				"debug" : false,
				"positionClass" : "toast-bottom-right",//Top Right、Bottom Right、Bottom Left、Top Left、Top Center、Bottom Center、Top Full Width、Bottom Full Width
				"onclick" : onclick,
				"showDuration" : "1000",
				"hideDuration" : "1000",
				"timeOut" : "5000",
				"extendedTimeOut" : "1000",
				"showEasing" : "swing",
				"hideEasing" : "linear",
				"showMethod" : "fadeIn",
				"hideMethod" : "fadeOut"
			}
        	var $toast = toastr['warning'](msg, title);
        },
        error:function (msg,title,onclick){
        	toastr.options = {
				"closeButton" : true,
				"debug" : false,
				"positionClass" : "toast-bottom-right",//Top Right、Bottom Right、Bottom Left、Top Left、Top Center、Bottom Center、Top Full Width、Bottom Full Width
				"onclick" : onclick,
				"showDuration" : "1000",
				"hideDuration" : "1000",
				"timeOut" : "5000",
				"extendedTimeOut" : "1000",
				"showEasing" : "swing",
				"hideEasing" : "linear",
				"showMethod" : "fadeIn",
				"hideMethod" : "fadeOut"
			}
        	var $toast = toastr['error'](msg, title);
        }

    };
}();