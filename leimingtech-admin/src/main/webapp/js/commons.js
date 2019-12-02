//查询方法 
function query_onClick(){
	var strCondtion = buildQueryCondition();
	document.getElementById("queryCondition").value=strCondtion;
	form.action="/quiee/reportJsp/showReport.jsp?raq=itemandproject.raq";
	form.target="reportFrame";
	form.submit();
	return false;
}
//重置的方法
function reset_back(){
	document.getElementById("itemname").value="";
}
//打印报表
function toPrint_OnClick() {
	try{
		window.frames['reportFrame'].report1_print();
	}catch(e){
	}
}
//导出报表
function toExcel_OnClick() {
	try{
		window.frames['reportFrame'].report1_saveAsExcel();
	}catch(e){
	}
}
//宏定义条件
function buildQueryCondition() {  //构建简单查询
	var queryCondition = "";  //定义组合后的查询条件的字符串变量
	var qca = new Array();  //定义查询条件的数组变量,每一个可能的查询条件会被压入这个数组
			
	pushCondition(qca, "itemname", "like '", "%'", "a.itemname");
	for(var i=0; i<qca.length; i++) {  //从第2个开始循环查询条件
		queryCondition += " and " + qca[i] + " ";  //组装第2个以后的查询条件
	}
	return queryCondition;
}
function changePage(urlStr){
	if(urlStr=='#')
		return ;
	
	if(!urlStr){
		urlStr=toNextUrl;
	}
	// var el = $(".page-header-fixed");
	// App.blockUI(el);
 	$.ajax({
		type:'post',
		url:urlStr,
		data:'data=0',
		dataType:'text',
		success:function(msg){
			//更改新链接的选中样式
			var a=$("#left_menu a[onclick='changePage('"+urlStr+"')']");//当前打开链接的A标签
			if(a.length>0){
				$("span.arrow").prev("i").removeClass("icon-folder-open");
				//当跳转连接找的到就修改选中样式
				var li=$("#left_menu li[class *='active']");//找到左菜单中所有选中的LI标签
				$(li).removeClass("active").removeClass("open");//删除一级栏目选中样式
				
				//给新的定位加样式
				var newLi=$(a).parents("li");//通过链接找到父级Li标签
				$(newLi).addClass("active");//添加二级栏目选中样式
				$("span.open").prev("i").addClass("icon-folder-open");
			}
			if($(".nav a[onclick$='"+urlStr+"')']").length>0){
				var activeUl=$(".nav li[class*='active'] ul");
				if (activeUl.length > 0) {
					$(".nav li[class*='active']").removeClass("active").removeClass("open").find("span").addClass("arrow").removeClass("selected");
				}
				else{
					$(".nav li[class*='active']").removeClass("active").removeClass("open").find("span").removeClass("selected");
				}
				$(".nav a[onclick$='"+urlStr+"')']").parents("li").addClass("active").filter(":last").find("span:eq(0)").removeClass("arrow").addClass("selected");
			}
			$('#dashboard').empty();
			$('#dashboard').append(msg);
			App.fixContentHeight();
		},
		error:function(){
			lmAlert("load page error, page url is " + urlStr);
		},
		complete:function (){
			// App.unblockUI(el);
		}
	});
 	
}

/**
 * 横向菜单切换
 * @param urlStr
 */
function horizantal(lefUrl, rightUrl){
 	$.ajax({
		type:'post',
		url:lefUrl,
		data:'data=0',
		dataType:'text',
		success:function(msg){
			$('#leftmenu').empty();
			$('#leftmenu').append("<li><div class=\"sidebar-toggler hidden-phone\"></div></li>"+msg);
			changePage(rightUrl);
		},
		error:function(){
			lmAlert("load page error, page url is " + lefUrl);
		}
	});
}

/**
 * 菜单树
 * @param urlStr
 */
function treeMenu(lefUrl, rightUrl){
	$.ajax({
		type:'post',
		url:lefUrl,
		data:'data=0',
		dataType:'text',
		success:function(msg){
			var activeUl=$(".nav li[class*='active'] ul");
			if (activeUl.length > 0) {
				$(".nav li[class*='active']").removeClass("active").find("span").addClass("arrow").removeClass("selected");
			}
			else{
				$(".nav li[class*='active']").removeClass("active").find("span").removeClass("selected");
			}
			$(".nav a[onclick*='treeMenu('"+lefUrl+"']").parents("li").addClass("active").filter(":last").find("span:eq(0)").removeClass("arrow").addClass("selected");
			$('#leftmenu').empty();
			$('#leftmenu').append(msg);
			$(".nav li[class*='active']").removeClass("active").find("span").removeClass("selected");
			$("a[onclick*='"+lefUrl+"']").parent().addClass("active").find("span").addClass("selected");
			if(rightUrl!=""&&rightUrl.length>0&&rightUrl!="null"){
				changePage(rightUrl);
			}
			App.fixContentHeight();
		},
		error:function(){
			lmAlert("load page error, page url is " + lefUrl);
		}
	});
}

/**
 * 数据字典修改类型
 * @param urlStr
 */
function changeSub(urlStr){
	if(!urlStr){
		urlStr=toSubNextUrl;
	}
	var el = $(".page-header-fixed");
	App.blockUI(el);
	$.ajax({
		type:'post',
		url:urlStr,
		data:'data=0',
		dataType:'text',
		success:function(msg){
			$('#editModel').empty();
			$('#editModel').append(msg);
		},
		error:function(){
			lmAlert("load page error, page url is " + urlStr);
		},
		complete:function (){
			App.unblockUI(el);
		}
	});
}
var toNextUrl2="";
var changeDivName="";

function lazyChangeDivBody(toUrl,divName){
	toNextUrl2=toUrl;
	changeDivName=divName;
	setTimeout(changeDivBody,500);
}

function changeDivBody(urlStr, divName){
	if (!urlStr || !divName) {
		urlStr = toNextUrl2;
		divName = changeDivName;
	}
	$.ajax({
		type:'post',
		url:urlStr,
		data:'data=0',
		dataType:'text',
		success:function(msg){
			$('#' + divName).empty();
			$('#' + divName).append(msg);
		},
		error:function(){
			lmAlert("load page error, page url is " + urlStr);
		}
	});
}
function changeDivBody2(urlStr, divName,catid){
	$.ajax({
		type:'post',
		url:urlStr,
		data:'data=0',
		dataType:'text',
		success:function(msg){
			$("#catId").val(catid);
			$('#' + divName).empty();
			$('#' + divName).append(msg);
		},
		error:function(){
			lmAlert("load page error, page url is " + urlStr);
		}
	});
}

function toEditModel(urlStr,model){
	if(!model){
		model=$('#editModel');
	}
	model.empty();
	$.ajax({
		type:'post',
		url:urlStr,
		data:'{data:0,alert:true}',
		dataType:'text',
		success:function(msg){
			model.empty();
			model.append(msg);
			model.modal('show');
		},
		error:function(){
			lmAlert("load page error, page url is " + urlStr);
		}
	});
}

function showModel(urlStr,model){
	if(!model){
		model=$("#editModel");
	}else{
		model=$('#'+model);
	}
	model.empty();
	$.ajax({
		type:'post',
		url:urlStr,
		data:'{data:0,alert:true}',
		dataType:'text',
		success:function(msg){
			model.empty();
			model.append(msg);
			model.modal('show');
			model.modal('layout');
		},
		error:function(){
			lmAlert("load page error, page url is " + urlStr);
		}
	});
}

/**
 * 数据字典
 * 
 */
function toTypeModel(urlStr){
	$.ajax({
		type:'post',
		url:urlStr,
		data:'data=0',
		dataType:'text',
		success:function(msg){
			$('#editModel').empty();
			$('#editModel').append(msg);
			$('#editModel').modal('show');
		},
		error:function(){
			alert("load page error, page url is " + urlStr);
		}
	});
}

function toSearch(urlStr, formName){
	$.ajax({
		type:'post',
		url:urlStr,
		data:$('#' + formName).serialize(),
		dataType:'text',
		success:function(msg){
			$('#dashboard').empty();
			$('#dashboard').append(msg);
		},
		error:function(){
			alert("load page error, page url is " + urlStr);
		}
	});
}
/**
 * 数据字典分页查询
 * @param urlStr
 * @param formName
 */
function toSearchSub(urlStr, formName,model){
	if(!model){
		model=$('#editModel');
	}
	$.ajax({
		type:'post',
		url:urlStr,
		data:$('#' + formName).serialize(),
		dataType:'text',
		success:function(msg){
			model.empty();
			model.append(msg);
			model.modal('layout');
		},
		error:function(){
			lmAlert("load page error, page url is " + urlStr);
		}
	});
}
function formSubmit(urlStr, formName){
	if(!$('#' + formName).valid()){
		return false;
  	}
	//if(ueditor){
		//ueditor.sync();
	//}
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
				lmAlert(obj.msg);
				lazyChangePage(toUrl);
			}else{
				lmAlert("提交处理失败!");
			}
		},
		error:function(){
			lmAlert("提交通讯失败!");
		}
	});
}
/**
 * 引用CKeditor时，需调用此方法。
 * 此方法是解决在引用Editor编译器时，保存不成功问题。
 */
function CKupdate() {
	for (instance in CKEDITOR.instances) {
		var ins = CKEDITOR.instances[instance];
		ins.setData(ins.getData().replace(new RegExp("\r\n", "g"), ""));
		ins.updateElement();
	}
}
/**
 * 保存
 * CKeditor，Ajax提交方法。
 * @param urlStr
 * @param formName
 */
function formSubmitModelCKeditor(urlStr, formName){
	if(!$('#' + formName).valid()){
		return false;
  	}
	if(ueditor){
		ueditor.sync();
	}
	var el = $(".page-header-fixed");
	App.blockUI(el);
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
				$('#editModel').empty();
				$('#editModel').modal('hide');
				lmAlert(obj.msg);
				changePage(toUrl);
			}else{
				lmAlert("提交处理失败!");
			}
		},
		error:function(){
			lmAlert("提交通讯失败!");
		},
		complete:function (){
			App.unblockUI(el);
		}
	});
}
/**
 * 修改
 * Ckeditor,Ajax提交方法。
 * @param urlStr
 * @param formName
 */
function toEditModelCKeditor(urlStr){
	$.ajax({
		type:'post',
		url:"wechatButtonController.do?add",
		data:'data=0',
		dataType:'text',
		success:function(msg){
			$('#editModel').empty();
			$('#editModel').append(msg);
			$('#editModel').modal('show');
		},
		error:function(){
			lmAlert("load page error, page url is " + urlStr);
		}
	});
}

function formSubmitModel(urlStr, formName){
	if(!$('#' + formName).valid()){
		return false;
  	}
	var el = $(".page-header-fixed");
	App.blockUI(el);
	//if(ueditor){
	//	ueditor.sync();
	//}
	$.ajax({
		type:'post',
		url:urlStr,
		data:$('#' + formName).serialize(),
		dataType:'text',
		success:function(msg){
			var obj = JSON.parse(eval(msg));
			if(obj.isSuccess){
				var toUrl = obj.toUrl;
				if (!toUrl && typeof(toUrl)!="undefined" && toUrl!=0 && toUrl != '')
				{
				    toUrl = arguments[2] + '';
				}
				$('#editModel').modal('hide');
				$('#editModel').empty();
				lmAlert(obj.msg);
				lazyChangePage(toUrl);
			}else{
				if(obj.msg){
					lmAlert(obj.msg);
				}else{
					lmAlert("提交处理失败!");
				}
			}
		},
		error:function(){
			lmAlert("提交通讯失败!");
		},
		complete:function (){
			App.unblockUI(el);
		}
	});
}

var toNextUrl="";

function lazyChangePage(toUrl){
	toNextUrl=toUrl;
	setTimeout(changePage,600);
}
/**
 * 保存数据字典
 * @param urlStr
 * @param id
 */

function formSubmitModelSub(urlStr, formName){
	if(!$('#' + formName).valid()){
		return false;
  	}
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
				$('#toEditModelSub').empty();
				$('#toEditModelSub').modal('hide');
				lazyChangeSub(toUrl);
			}else{
				lmAlert("提交处理失败!");
			}
		},
		error:function(){
			lmAlert("提交通讯失败!");
		}
	});
}

var toSubNextUrl="";
function lazyChangeSub(url){
	toSubNextUrl=url;
	setTimeout(changeSub,500);
}
/**
 * 删除数据字典
 * @param urlStr
 * @param id
 * @param isstart
 */
function delSub(urlStr, id){
	
	lmConfirm("是否删除该记录？","",function (){
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
					lazyChangeSub(toUrl);
				}else{
					lmAlert("删除处理失败!");
				}
			},
			error:function(){
				lmAlert("提交通讯失败!");
			}
		});
	});
}

function del(urlStr, id){
	lmConfirm("是否删除该记录？","",function (){
		$.ajax({
			type:'post',
			url:urlStr,
			data:{'id':id,'ajax':true},
			success:function(msg){
				var obj = JSON.parse(eval(msg));
				if(obj.isSuccess){
					lazyChangePage(obj.toUrl);
				}else{
					lmAlert(obj.msg);
				}
			},
			error:function(){
				lmAlert("提交通讯失败!");
			}
		});
	});
}

function delTwo(urlStr, id, pid){
	
	lmConfirm("是否删除该记录？","",function (){
		$.ajax({
			type:'post',
			url:urlStr,
			data:{'id':id,'grogShopId':pid},
			success:function(msg){
				var obj = JSON.parse(eval(msg));
				if(obj.isSuccess){
					var toUrl = obj.toUrl;
					if (!toUrl && typeof(toUrl)!="undefined" && toUrl!=0 && toUrl != '')
					{
					    toUrl = arguments[2] + '';
					}
					lmAlert(obj.msg);
					changePage(toUrl);
				}else{
					lmAlert(obj.msg);
				}
			},
			error:function(){
				lmAlert("提交通讯失败!");
			}
		});
	});
}



function resetForm(formName){
	$(':input','#'+formName).not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
}

//重置按钮，清空选项同时刷新列表
function resetFormRefresh(toUrl,formName){
//	var roleCode = document.getElementById("roleCode").value;
//	var roleName = document.getElementById("roleName").value;
//	if(roleCode!=""||roleName!=""){
	$(':input','#'+formName).not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
	changePage(toUrl);
//	}
}

//地图经纬度
/*function map(longitude,latitude,flag){
	var urlStr = "";
	if(flag=="11"){
		urlStr = 'companyController.do?baiduMap&longitude='+longitude+'&latitude='+latitude;
	}else if(flag=="10"){
		urlStr = 'branchController.do?baiduMap&longitude='+longitude+'&latitude='+latitude;
	}
	$.ajax({
		type:'post',
		url:urlStr,
		data:'data=0',
		dataType:'text',
		success:function(msg){
			$('#editMapModel').empty();
			$('#editMapModel').append(msg);
			$('#editMapModel').modal('show');
		},
		error:function(){
			alert("地图位置出错");
		}
	});
}*/

//循环打印对象的值
function printObject(obj) { 
    // 用来保存所有的属性名称和值
    var props = "";
    // 开始遍历
    for(var p in obj){ 
        // 方法
        if(typeof(obj[p])=="function"){ 
            //obj[p]();
        }else{ 
            // p 为属性名称，obj[p]为对应属性的值
            props += p + "=" + obj[p] + ";  ";
        } 
    } 
    // 最后显示所有的属性
    lmAlert(props);
}

/**
 * 资源上传
 * @param title 上传文件资源名称
 * @param isSingle 是否只允许单文件上传
 * @param isPureUploadPicture 是否只允许上传纯图片
 * @param saveFolder  上传文件所在服务器端存储的文件夹相对路径
 * @param responseDomId 文件上传后,接收文件上传成功后所形成的网络路径dom元素Id
 */
function showUploadFilePage(title, isSingle, isPureUploadPicture, saveFolder, responseDomId) {
	var uploadPageDiv = '<div id="uploadPageDiv" class="modal fade" tabindex="-1" data-backdrop="static" data-focus-on="input:first" data-width="620" style="min-height:400px;"></div>';
	$("#uploadPageDiv").remove();
	$("body").append(uploadPageDiv);
	$.ajax({
		type : 'post',
		url : "wechatFileUploadController.do?showUploadFilePage&timer="
				+ new Date().getTime() + "&title=" + title + "&isSingle="
				+ isSingle + "&isPureUploadPicture=" + isPureUploadPicture
				+ "&saveFolder=" + saveFolder + "&responseDomId="
				+ responseDomId,
		data : 'data=0',
		dataType : 'text',
		success : function(msg) {
			$('#uploadPageDiv').empty();
			$('#uploadPageDiv').append(msg);
			$('#uploadPageDiv').modal('show');
		},
		error : function() {
			lmAlert("load page error, page url is " + urlStr);
		}
	});
}
/**
 * 图片原图查看
 * @param originalImgPath 原始图片路径
 * @param title 图片查看器标题
 * @param width 图片查看器宽度
 * @param height 图片查看器高度
 */
function viewOriginalImage(originalImgPath,title,width,height){
	if(!title){
		title="查看原图";
	}
	if(!width){
		width=800;
	}
	if(!height){
		height=400;
	}
	var testImg=$('<img src="'+originalImgPath+'" id="testViewOriginalImage" style="display:none;">').appendTo($("body").eq(0));
	var testViewOriginalImage=$("#testViewOriginalImage");
	var testViewOriginalImageWidth=testViewOriginalImage.width();
	var testViewOriginalImageHeight=testViewOriginalImage.height();
	var maxWidth=Math.max(testViewOriginalImageWidth,width);
	var maxHeight=Math.max(testViewOriginalImageHeight,height);
	testImg.remove();
	var isBeyondScreen=false;
	if(maxWidth==testViewOriginalImageWidth){
		var fillWindowWidth=$(window).width()-50;
		width=Math.min(maxWidth+30,fillWindowWidth);
		if(width==fillWindowWidth){
			isBeyondScreen=true;
		}
	}
	if(maxHeight==testViewOriginalImageHeight){
		height=maxHeight+(isBeyondScreen?53:88);
	}
	var viewOriginalImageDivStr=
	'<div class="modal fade viewOriginalImageDiv" tabindex="-1" data-backdrop="static" data-focus-on="input:first" data-width="'+width+'" style="height:'+height+'px;">'+
		'<div class="modal-header">'+
			'<button type="button" class="close" data-dismiss="modal"'+
				'aria-hidden="true"></button>'+
			'<h4 class="modal-title">'+title+'</h4>'+
		'</div>'+
		'<div class="modal-body">'+
			'<center>'+
				'<img src="" id="viewOriginalImage" '+(isBeyondScreen?'style="width:100%;"':"")+' >'+
			'</center>'+
		'</div>'+
	'</div>';
	var viewOriginalImageDiv=$(".viewOriginalImageDiv");
	viewOriginalImageDiv.remove();
	viewOriginalImageDiv=$(viewOriginalImageDivStr).appendTo($("body").eq(0));
	viewOriginalImageDiv.find("#viewOriginalImage").attr("src" , originalImgPath).css({"max-width":"none","max-height":"none"}).end().modal('show');
}