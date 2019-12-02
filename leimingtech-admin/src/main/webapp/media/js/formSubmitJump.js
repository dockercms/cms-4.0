      $(function(){
     		var titleValue = "${seek}";//父级页面传的值
    		if(titleValue!=""){
	    		$("#cms_title").attr('value',titleValue);
    		}else{//当前页面值
    			$("#cms_title").attr('value',"${title}");
    		}
    	});
    	function formSubmitJump(temporary,contentId){
    		var str=document.getElementsByName("attachcontents");
    		var objarray=str.length;
    		var chestr="";
    		for (i=0;i<objarray;i++){
    			if(str[i].checked == true){
    			  chestr+=str[i].value+",";
    			}
    		}
    		var urlStr = 'contentsController.do?correlations&checked='+chestr+'&temporary='+temporary+'&contentId='+contentId;
    		var formName = 'form_sample_2';
    		$.ajax({
    			type:'post',
    			url:urlStr,
    			data:$('#' + formName).serialize(),
    			success:function(msg){
    				var obj = JSON.parse(eval(msg));
    				if(obj.isSuccess){
    					var toUrl = obj.toUrl;
    					var checked = obj.checked;
    					var temporary = obj.temporary;
    					var contentId = obj.contentId;
    					if (!toUrl && typeof(toUrl)!="undefined" && toUrl!=0 && toUrl != '')
    					{
    					    toUrl = arguments[2] + '';
    					}
    					
    					jump(toUrl,checked,temporary,contentId);
    				}else{
    					lmAlert("提交处理失败!");
    				}
    			},
    			error:function(){
    				lmAlert("提交通讯失败!");
    			}
    		});
    	}
    	function jump(tourl,checked,temporary,contentId){
    		$.ajax({
    			type:'post',
    			url:tourl,
    			data:{checked:checked,temporary:temporary,contentId:contentId},
    			dataType:'text',
    			success:function(msg){
    				$('#contentModel').empty();
    				$('#contentModel').modal('hide');
    				$('#correlationDiv').empty();
    				$('#correlationDiv').append(msg);
    			},
    			error:function(){
    				lmAlert("load page error, page url is " + urlStr);
    			}
    		});
    	}
    	function checkall(obj){
    		if(obj.checked==true){
    			$("input[name='attachcontents']").each(function(){
    	            $(this).attr("checked", "checked"); 
    	        });
    		}else{
    			$("input[name='attachcontents']").each(function(){
    	            $(this).attr("checked", false); 
    	        });
    		}
    	}
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
			},
			error:function(){
				alert("load page error, page url is " + urlStr);
			}
		});
	};