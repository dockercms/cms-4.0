var setting = {
	view : {
		dblClickExpand : false,
		selectedMulti : false,
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeClick : beforeClick,
		onClick : onClick
	}
};

function beforeClick(treeId, treeNode) {
	var check = (treeNode && !treeNode.isParent);
	// if (!check) alert("只能选择城市...");
	return true;
}

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
			.getSelectedNodes(), v = "";
	nodes.sort(function compare(a, b) {
		return a.id - b.id;
	});
	for ( var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	var cityObj = $("#catId");
	cityObj.attr("value", v);
}

function showMenu() {
	var cityObj = $("#catId");
	var cityOffset = $("#catId").offset();
	var modelcityOffset = $("#editModel").offset();
	$("#menuContent").css(
			{
				left : cityOffset.left - modelcityOffset.left + "px",
				top : cityOffset.top + cityObj.outerHeight()
						- modelcityOffset.top + "px"
			}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
			event.target).parents("#menuContent").length > 0)) {
		hideMenu();
	}
}