/**
 * 标签管理
 * @type {string}
 */
var addtag=function() {
	var tag = {
		id: '',
		text: '',
	}
	return tag;
}
$(function(){
	var contentTags=$("#tags").val();
	var tagArry=new Array();
	if(contentTags!='') {
		var tagList = contentTags.split(",");
		for (var i = 0; i < tagList.length; i++) {
			var tag=addtag();
			tag.id = tagList[i];
			tag.text = tagList[i];
			tagArry.push(tag);
		}
	}
	$('#select2Tags').select2({
		tags: true,
		width:'600px',
		data:tagArry,
		maximumSelectionLength: 5,
	});
	$(".select2-container").css("margin-left","-20px");

	$("#select2Tags").val(tagList).trigger('change')
})
function changeTages(){
	var res = $("#select2Tags").select2("data");
	var tag='';
	if(res!=null){
		for(var i=0;i<res.length;i++){
			if(res.length==1){
				tag+=res[i].text;
			}else{
				if(i==res.length-1){
					tag+=res[i].text;
				}else{
					tag+=res[i].text+',';
				}
			}
		}
		$("#tags").val(tag);
		$("#correlation").val(tag);
	}
}
//用于缩略图区别（如果出现多个图片库的情况）
	var pathid = "";
	// 相关内容-----点击搜索按钮
	function toEdit(seek,temporary,content_ids){
		var content_id=content_ids;
		
	 	var urlStr = "contentsController.do?correlationDialog&temporary="+temporary+"&seek="+seek;
		$.ajax({
			type:'post',
			url:urlStr,
			data:{content_id:'content_id'},
			
			success:function(msg){
				$('#contentModel').empty();
				$('#contentModel').append(msg);
				$('#contentModel').modal('show');
			},
			error:function(){
				lmAlert("load page error, page url is " + urlStr);
			}
		});
	}
	//相关内容--------手工添加 
	function toAdd(urlStr){
		$.ajax({
			type:'post',
			url:urlStr,
			data:'data=0',
			dataType:'text',
			success:function(msg){
				$('#showModel').empty();
				$('#showModel').append(msg);
				$('#showModel').modal('show');
			},
			error:function(){
				lmAlert("load page error, page url is " + urlStr);
			}
		});
	}
	function liveTip(){
		var $liveTip = $('<div id="livetip"></div>').hide().appendTo('body');
		$('.left').bind('mouseover mouseout mousemove',
		function(event) {
			var $link = $(event.target).closest('input');
			if (!$link.length) {
				return;
			}
			var link = $link[0];
			if(!link.value){
				return;
			}
			if (event.type == 'mouseover'|| event.type == 'mousemove') {
				$liveTip.css({
					top : event.pageY + 12,
					left : event.pageX + 12
				});
			};
			var srcValue=link.value+".thumb.jpg";
			if (event.type == 'mouseover') {
				$liveTip.html('<div><img src=' + srcValue + ' /></div>').show();
			};
			if (event.type == 'mouseout') {
				$liveTip.hide();
			};
		});
	}
	function liveTag(){
		var $liveTip = $('<div id="livetip"></div>').hide().appendTo('body');
		$('.leftTag').bind('mouseover mouseout mousemove',
		function(event) {
			var $link = $(event.target).closest('input');
			if (!$link.length) {
				return;
			}
			var link = $link[0];
			if(!link.value){
				return;
			}
			if (event.type == 'mouseover'|| event.type == 'mousemove') {
				$liveTip.css({
					top : event.pageY + 12,
					left : event.pageX + 12
				});
			};
			var srcValue=link.value;
			if (event.type == 'mouseover') {
				$liveTip.html('<div><img style="max-width: 200px; max-height: 200px;" src=' + srcValue + ' /></div>').show();
			};
			if (event.type == 'mouseout') {
				$liveTip.hide();
			};
		});
	}
	$('.date-picker').datepicker({
	    rtl : App.isRTL()
	});
	//显示、隐藏副题
	function changeVal(){
		setTimeout(cVal,100);
	}
	function cVal(){
		if(document.getElementById('h1').value=='true'){
			document.getElementById('subdiv').style.display = 'block';
		}else{
			document.getElementById('subdiv').style.display = 'none';
		}
	}
	//统计字数
	function gbcount(message,total,used){
		var max;
		max = total.value;
		if (message.value.length > max) {
			message.value = message.value.substring(0,max);
			used.value = max;
			lmAlert("不能超过"+total.value+"个字!");
		}
		else {
		used.value = message.value.length;
		}
	}
	//扩展字段规则
	//控制从键盘只能输入整型数据(int型),但此函数无法阻止用户粘贴和拖放
	function filterInt(evt){   
	    evt = evt || window.event;   
	    var b = evt.keyCode || evt.which;   
	  
	    return(b>=48&&b<=57)||b==8;   //0到9的键值为48到57,BackSpace的键值为8
	}      
	
	//防止用户粘贴和拖放非整型数据(当文本框失焦时检测数据,如果数据为非整型就将其清掉)
	function checkInt(event){
		var patrn=/[^0123456789]/;
		if(patrn.exec(this.value)){
		lmAlert("输入不合法，请重新输入！");
		this.value="";
		this.focus();
		}
	}
	function JHshNumberText(a)
	{ 
	var fa="";
	if(a.value.substring(0,1)=="-")
	      fa="-";
	
	var str=(a.value.replace(/[^0-9.]/g,'')).replace(/[.][0-9]*[.]/, '.');
	
	if (str.substring(0,1)==".")
	   str="0"+str;
	   a.value=fa+str;
	
	} 
	//颜色选择器
	var ColorHex=new Array('00','33','66','99','CC','FF');
	var SpColorHex=new Array('FF0000','00FF00','0000FF','FFFF00','00FFFF','FF00FF');
	var current=null;
	function initcolor(evt) 
		{ 
		var colorTable='' 
		for (i=0;i<2;i++){ 
			for (j=0;j<6;j++){ 
				colorTable=colorTable+'<tr height=15>' 
				colorTable=colorTable+'<td width=15 style="border:1px #000000 solid;background-color:#000000">' 
				if(i==0){ 
					colorTable=colorTable+'<td width=15 style="border:1px #000000 solid;cursor:pointer;background-color:#'+ColorHex[j]+ColorHex[j]+ColorHex[j]+'">'
				}else{ 
					colorTable=colorTable+'<td width=15 style="border:1px #000000 solid;cursor:pointer;background-color:#'+SpColorHex[j]+'">'
				}
				colorTable=colorTable+'<td width=15 style="border:1px #000000 solid;background-color:#000000">' 
				for (k=0;k<3;k++){ 
					for (l=0;l<6;l++){ 
						colorTable=colorTable+'<td width=15 style="border:1px #000000 solid;cursor:pointer;background-color:#'+ColorHex[k+i*3]+ColorHex[l]+ColorHex[j]+'">' 
					} 
				}
				
			} 
		} 
		colorTable='<table border="0" cellspacing="0" cellpadding="0" style="border:1px #000000 solid;border-bottom:none;border-collapse: collapse;width:337px;max-width:337px;" bordercolor="000000">' 
		+'<tr height=20><td colspan=21 bgcolor=#ffffff style="font:12px tahoma;padding-left:2px;">' 
		+'<span style="float:left;color:#999999;"></span>' 
		+'<span style="float:right;padding-right:3px;cursor:pointer;" id="colorclose">×关闭</span>' 
		+'</td></table>' 
		+'<table border="1" cellspacing="0" cellpadding="0" style="border:1px #000000 solid;border-collapse: collapse;width:337px;max-width:337px;" bordercolor="000000" style="cursor:pointer;">' 
		+colorTable+'</table>'; 
		document.getElementById("colorpane").innerHTML=colorTable; 
		var current_x = document.getElementById("input1").offsetLeft; 
		var current_y = document.getElementById("input1").offsetTop; 
		var colorwidth=parseInt($("#colorpane").css("width"));
		document.getElementById("colorpane").style.left = -colorwidth+30 + "px"; 
		document.getElementById("colorpane").style.top ="0px"; 
		
		} 
	
		setTimeout(initcolor,500);
	   $("#colorclose").live("click",function (evt){
			$("#colorpane").hide();
			var e=(evt)?evt:window.event; //判断浏览器的类型，在基于ie内核的浏览器中的使用cancelBubble  
			if (window.event) {  
			e.cancelBubble=true;  
			} else {  
			//e.preventDefault(); //在基于firefox内核的浏览器中支持做法stopPropagation  
			e.stopPropagation();  
			}  
	   });
	   $("#colorpane table:eq(1) td").live("click",function (evt){
		   var obj=$(this).css("backgroundColor");
		   document.getElementById("color").value = obj;
			document.getElementById("title").style.color = obj;
			document.getElementById("input1").style.backgroundColor = obj;
			$("#colorpane").hide();
			var e=(evt)?evt:window.event; //判断浏览器的类型，在基于ie内核的浏览器中的使用cancelBubble  
			if (window.event) {  
			e.cancelBubble=true;  
			} else {  
			//e.preventDefault(); //在基于firefox内核的浏览器中支持做法stopPropagation  
			e.stopPropagation();  
			}  
	   });
	   $(function (){
		   $("#input1").click(function (){
			   $("#colorpane").show();
		   });
		   
	   });
	
	//ztree属性下拉树选择

	var code,code1;
	function setCheck() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		py = $("#py").attr("checked")? "p":"",
		sy = $("#sy").attr("checked")? "s":"",
		pn = $("#pn").attr("checked")? "p":"",
		sn = $("#sn").attr("checked")? "s":"",
		type = { "Y":py + sy, "N":pn + sn};
		zTree.setting.check.chkboxType = type;
		showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
	}
	function showCode(str) {
		if (!code) code = $("#code");
		code.empty();
		code.append("<li>"+str+"</li>");
	}

	function beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
	
	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getCheckedNodes(true),
		v = "";
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		var cityObj = $("#attribute");
		cityObj.attr("value", v);
	}

	function showMenu() {
		var cityObj = $("#attribute");
		var cityOffset = $("#attribute").offset();
		$("#menuContent").css({left:"0px", top:cityObj.outerHeight()+ "px"}).slideDown("fast");
//{left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}
		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "attribute" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	//
	function setCheck1() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo1"),
		py = $("#py").attr("checked")? "p":"",
		sy = $("#sy").attr("checked")? "s":"",
		pn = $("#pn").attr("checked")? "p":"",
		sn = $("#sn").attr("checked")? "s":"",
		type = { "Y":py + sy, "N":pn + sn};
		zTree.setting.check.chkboxType = type;
		showCode1('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
	}
	function showCode1(str) {
		if (!code) code = $("#code1");
		code.empty();
		code.append("<li>"+str+"</li>");
	}

	function beforeClick1(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo1");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
	
	function onCheck1(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo1"),
		nodes = zTree.getCheckedNodes(true),
		v = "",vid="";
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			vid += nodes[i].id + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (vid.length > 0 ) vid = vid.substring(0, vid.length-1);
		var cityObj = $("#souContent");
		var mobileId = $("#mobileId");
		cityObj.attr("value", v);
		mobileId.attr("value",vid);
	}

	function showMenu1() {
		var cityObj = $("#souContent");
		var cityOffset = $("#souContent").offset();
		$("#menuContent1").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown1);
	}
	function hideMenu1() {
		$("#menuContent1").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown1);
	}
	function onBodyDown1(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "souContent" || event.target.id == "menuContent1" || $(event.target).parents("#menuContent1").length>0)) {
			hideMenu1();
		}
	}
	
	function uploaderinit(){
		//扩展字段上传图片
	var uploaderoptionimg2 = WebUploader.create({ auto: true, swf: 'plug-in/ueditor/third-party/webuploader/Uploader.swf',

	    // 文件接收服务端。
	    server: 'controller.jsp?action=uploadimage',

	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: '#optionimg',

	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
	    resize: false,
	    
	    // 只允许选择图片文件。
	    accept: {
	        title: 'Images',
	        extensions: 'gif,jpg,jpeg,bmp,png',
	        mimeTypes: 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
	    }
	});

	//当有文件添加进来的时候
	uploaderoptionimg2.on( 'uploadSuccess', function( file,Object ) {
		$("#picAcc").val(Object.url);
	});
	}
	//表单还有一个div时点击提交
	function formSubmitJump(urlStr, formName){
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
					lazyChangePage(toUrl);
					lmAlert(obj.msg);
				}else{
					lmAlert("提交处理失败!");
				}
			},
			error:function(){
				lmAlert("提交通讯失败!");
			}
		});
	}
	//嵌套div中数据删除
	function delJump(urlStr, id){
		
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
						lmAlert(obj.msg);
						jump(toUrl);
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
