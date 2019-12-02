function formSubmitModel(urlStr, formName){
	$.ajax({
		type:'post',
		url:urlStr,
		data:$('#' + formName).serialize(),
		success:function(msg){
			var obj = JSON.parse(eval(msg));
			if(obj.isSuccess){
				var toUrl = obj.toUrl;
				if (!toUrl && typeof(toUrl)!="undefined" && toUrl!=0 && toUrl != '')
				{
				    toUrl = arguments[2] + '';
				}
				alert(obj.msg);
				location.href=toUrl;
			}else{
				alert("提交处理失败!");
			}
		},
		error:function(){
			alert("提交通讯失败!");
		}
	});
}

function formSubmitModel1(urlStr, formName){
	$.ajax({
		type:'post',
		url:urlStr,
		data:$('#' + formName).serialize(),
		success:function(msg){
			var obj = JSON.parse(eval(msg));
			if(obj.isSuccess){
				var toUrl = obj.toUrl;
				if (!toUrl && typeof(toUrl)!="undefined" && toUrl!=0 && toUrl != '')
				{
				    toUrl = arguments[2] + '';
				}
				alert(obj.msg);
				location.href=toUrl;
			}else{
				var toUrl = obj.toUrl;
				if (!toUrl && typeof(toUrl)!="undefined" && toUrl!=0 && toUrl != '')
				{
				    toUrl = arguments[2] + '';
				}
				alert(obj.msg);
				location.href=toUrl;
			}
		},
		error:function(){
			alert("提交通讯失败!");
		}
	});
}

function del(urlStr, id){
	
	if(!confirm("是否删除该记录？")){
		return;
	}
	$.ajax({
		type:'post',
		url:urlStr,
		data:'id=' + id,
		success:function(msg){
			var obj = JSON.parse(eval(msg));
			if(obj.isSuccess){
				var toUrl = obj.toUrl;
				if (!toUrl && typeof(toUrl)!="undefined" && toUrl!=0 && toUrl != '')
				{
				    toUrl = arguments[2] + '';
				}
				alert(obj.msg);
				changePage(toUrl);
			}else{
				alert("删除处理失败!");
			}
		},
		error:function(){
			alert("提交通讯失败!");
		}
	});
}

