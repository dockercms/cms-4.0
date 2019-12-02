	
	//ztree属性下拉树选择
	//加载栏目
	var setting = {
		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "all"
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	var zNodes1 = new Array();
	$.ajax({
		type:'post',
		async:false,
		url:'contentsMobileController.do?loadmobile',
		data:'data=0',
		success:function(data){
			 zNodes1 = data;
		}
	});
	var json1 = JSON.parse(zNodes1);
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting,json1);
		$('#treeDemo').click(function(){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var checked=zTree.getCheckedNodes(true);//已经选中的对象 
			$('#pathids').attr("value",checked[0].pathids);
			$('#souContent').attr("value",checked[0].name);
		});		
	});

	function showMenu() {
		var cityObj = $("#souContent");
		var cityOffset = $("#souContent").offset();
		$("#menuContent").css({left:"310px", top:"88px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	};
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	};
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "souContent" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	};
	
	
	//搜索
	function showDataSearch(urlStr, formName){
		$.ajax({
			type:'post',
			url:urlStr,
			data:$('#' + formName).serialize(),
			dataType:'text',
			success:function(msg){
				$('#toEditModelSub').empty();
				$('#toEditModelSub').append(msg);
				$('#toEditModelSub').modal('show');
			},
			error:function(){
				alert("load page error, page url is " + urlStr);
			}
		});
	};
	//JsonObject格式时间    
	Date.prototype.format = function (format) {   
        var o = {   
            "M+": this.getMonth() + 1,   
            "d+": this.getDate(),   
            "h+": this.getHours(),   
            "m+": this.getMinutes(),   
            "s+": this.getSeconds(),   
            "q+": Math.floor((this.getMonth() + 3) / 3),   
            "S": this.getMilliseconds()   
        }   
        if (/(y+)/.test(format)) {   
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));   
        }   
        for (var k in o) {   
            if (new RegExp("(" + k + ")").test(format)) {   
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));   
            }   
        }   
        return format;   
    }; 
	function getFormatDateByLong(l, pattern) {   
        return getFormatDate(new Date(l), pattern);   
    };
    function getFormatDate(date, pattern) {   
        if (date == undefined) {   
            date = new Date();   
        }   
        if (pattern == undefined) {   
            pattern = "yyyy-MM-dd hh:mm:ss";   
        }   
        return date.format(pattern);   
    };
	function formSubmitJump(){
		var str="";
        $("input[name='box']:checkbox").each(function(){ 
            if($(this).attr("checked")){
                str += $(this).val()+",";
            }
        });
		var urlStr = 'videoalburmController.do?saveSelectArticle&chestr='+str+"&id="+id;
		var formName = 'form_sample_2';
		$.ajax({
			type:'post',
			url:urlStr,
			data:$('#' + formName).serialize(),
			success:function(msg){
				var obj = JSON.parse(eval(msg));
				if(obj.isSuccess){
					$('#toEditModelSub').modal('hide');
					var toUrl = obj.toUrl;
					lazyChageSub(toUrl);
				}else{
					alert("提交处理失败!");
				}
				
			},
			error:function(){
				alert("提交通讯失败!");
			}
		});
	};
	
	
	
	