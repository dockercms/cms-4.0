function add(){
	if($('#form').valid()){
		$.ajax({
			type:'post',
			url:"contributeController.do?saveContribute",
			data:$('#form').serialize(),
			success:function(msg){
				var obj = JSON.parse(eval(msg));
				if(obj.isSuccess){
					alert(obj.msg);
					location.href="contributeController.do?contentsList";
				}else{
					alert(obj.msg);
				}
			},
			error:function(){
				alert("提交通讯失败!");
			}
		});
	};
};

function showtree(){
	var cityObj = $("#catid");
	var cityOffset = $("#catid").offset();
	$("#treediv").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", hideMenu);
	
}

function hideMenu() {
	$("#treediv").fadeOut("fast");
	$("body").unbind("mousedown", hideMenu);
}

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
var zNodes = new Array();
var catid = '${contents.catid?if_exists}';
var url = "contributeController.do?contentCatTree";
if(catid){
	url = url + "&catid=" + catid;
}
$.ajax({
	type:'post',
	async:false,
	url:url,
	data:'data=0',
	success:function(data){
		 zNodes = data;
	}
});
var json1 = JSON.parse(zNodes);
$(document).ready(function(){
	// 栏目树
	$.fn.dropMenu.init($("#treeDemo"), setting,json1);
	$('#treeDemo').click(function(){
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var checked=zTree.getCheckedNodes(true);//已经选中的对象 
		$('#funVal').attr("value",checked[0].id);
		$("#catid").attr("value", checked[0].name);
	});		
	
	// 表单验证
	var formvalidate = validateObj("form");
	var rules = formvalidate.settings.rules;
	var title = {required:true};
	createJson(rules, "title", title);
	var content = {required:true};
	createJson(rules, "content", content);
	var catid = {required:true};
	createJson(rules, "catid", catid);
});

// 验证码
$('#randCodeImage').click(function(){
    reloadRandCodeImage();
});

// 刷新验证码
function reloadRandCodeImage() {
    var date = new Date();
    var img = document.getElementById("randCodeImage");
    img.src='/lmcms/randCodeImage?a=' + date.getTime();
}