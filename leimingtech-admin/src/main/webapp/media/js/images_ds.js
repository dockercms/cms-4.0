//判断图片数量
function imgnumber(inputName){
	var imgurl=$("#"+inputName).val();
	var imgs=imgurl.split(",");
	if(imgs.length==5){
	$("#"+inputName).siblings(".btn.blue").hide();
	$("#"+inputName).siblings(".btn.green").hide();
	}else{
		$("#"+inputName).siblings(".btn.blue").show();
		$("#"+inputName).siblings(".btn.green").show();
	}
}

//图片删除
	function delimg(name,inputName){
	var oldurl=$("#"+inputName).val();
	var delurl=$("#"+name).children("img.img").attr("src");
	var imgs=oldurl.split(",");
	imgs.splice($.inArray(delurl,imgs),1);
	var newurl=imgs.join(",");
	$("#"+inputName).val(newurl);
	$("#"+name).remove();
	imgnumber(inputName);
	}
	//图片显示
	    function showallimg(inputName){
	     $("#"+inputName).siblings(".imagesurl").empty();
	             var imgurl=$("#"+inputName).val();
	             if(imgurl!=""){
	             var imgs=imgurl.split(",");
	             for(var i=0;i<imgs.length;i++){
	             var thisInput="<input type='button' class='del' onclick=delimg('"+i+"','"+inputName+"') >";
	             $("#"+inputName).siblings(".imagesurl").prepend("<div id='"+i+"' class='images'><img src='"+imgs[i]+ "'class='img'/>"+thisInput+"</div>")
	            }
	          }
	    }
		function selectPictureBack(inputName){
				var result = $("input[name='path']:checked");
				var paths="";
				if (result.length > 0) {
					for ( var i = 0; i < result.length; i++) {
						if(i==result.length-1){//末尾不加","
							paths = paths + $(result[i]).val()
						}else{
							paths = paths + $(result[i]).val() + ",";
						}
					}
				}
				if($("#"+inputName).val()){
					$("#"+inputName).val($("#"+inputName).val()+","+paths);
				}else{
					$("#"+inputName).val(paths);
				}
			 imgnumber(inputName);
			 showallimg(inputName);
			$('#editModel').modal('hide');
		}