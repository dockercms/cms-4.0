$("#tree_2").on("nodeselect.tree.data-api", "[data-role=leaf]", function(e) {
	var id = e.node.value;
	changeDivBody('sectionClassController.do?Table&id=' + id, 'divBody');
});

$("#tree_2").on("openbranch.tree", "[data-role=branch]", function(e) {
	var id = e.node.value;
	changeDivBody('sectionClassController.do?Table&id=' + id, 'divBody');
});

function totempleteDiv(urlStr) {
	$.ajax({
		type : 'post',
		url : urlStr,
		data : 't=' + new Date().getTime(),
		dataType : 'text',
		success : function(msg) {
			$('#editModel').empty();
			$('#editModel').append(msg);
			$('#editModel').modal('show');
		},
		error : function() {
			lmAlert("load page error, page url is " + urlStr);
		}
	});
}

// 选择模板后隐藏弹出框
function selectMoreTemplate(path) {
	// 选择模板后返回的路径
	$("#tempaltepath").val(path);
	$("#templeteDiv").modal('hide');
}

function formSubmitModel2Div(urlStr, formName, div, returnUrl) {
	$.ajax({
		type : 'post',
		url : urlStr + '&t=' + new Date().getTime(),
		data : $('#' + formName).serialize(),
		success : function(msg) {
			var obj = JSON.parse(eval(msg));
			if (obj.isSuccess) {
				$('#editModel').modal('hide');
				$('#editModel').empty();
				lazyChangePage(toUrl);
			} else {
				lmAlert(obj.msg);
			}
		},
		error : function() {
			lmAlert("提交通讯失败!");
		}
	});
}

/**
 * 删除区块分类 删除区块将直接删除区块所有关联数据
 * 
 * @param id
 *            区块id
 */
function delSectionClass(id) {

	if (!confirm("确定要删除此区块分类吗？ 删除将不可恢复！删除同时也将会删除与之关联的所有区块数据！慎重！")) {
		return;
	}
	$.ajax({
		type : 'post',
		url : 'sectionClassController.do?del&t=' + new Date().getTime(),
		data : 'id=' + id,
		success : function(msg) {
			var obj = JSON.parse(eval(msg));
			if (obj.isSuccess) {
				var toUrl = obj.toUrl;
				if (!toUrl && typeof (toUrl) != "undefined" && toUrl != 0
						&& toUrl != '') {
					toUrl = arguments[2] + '';
				}
				lazyChangePage(toUrl);
			} else {
				lmAlert(obj.msg);
			}
		},
		error : function() {
			lmAlert("提交通讯失败!");
		}
	});
}

function formSubmitSection(urlStr, formName, returnUrl, selectId) {
	$.ajax({
		type : 'post',
		url : urlStr + '&t=' + new Date().getTime(),
		data : $('#' + formName).serialize(),
		success : function(msg) {
			var obj = JSON.parse(eval(msg));
			if (obj.isSuccess) {
				$('#showeditModel').modal('hide');
//				changeDivBody(returnUrl, "dashboard");
				lazyChangeDivBody(returnUrl, "dashboard");
			} else {
				lmAlert(obj.msg);
			}
		},
		error : function() {
			lmAlert("提交通讯失败!");
		}
	})
}

function delSection(urlStr, id, toUrl, selectId) {

	if (!confirm("确定删除区块吗？ 删除同时也将删除区块下的关联数据！且删除后不可恢复！")) {
		return;
	}
	$.ajax({
		type : 'post',
		url : urlStr + '&t=' + new Date().getTime(),
		data : 'id=' + id,
		success : function(msg) {
			var obj = JSON.parse(eval(msg));
			if (obj.isSuccess) {
				changeDivBody(toUrl, 'dashboard');
			} else {
				lmAlert("删除区块失败!");
			}
		},
		error : function() {
			lmAlert("提交通讯失败!");
		}
	})
}

function delSectionData(urlStr, id, toUrl) {

	if (!confirm("是否删除该记录？")) {
		return;
	}
	$.ajax({
		type : 'post',
		url : urlStr + '&t=' + new Date().getTime(),
		data : 'id=' + id,
		success : function(msg) {
			var obj = JSON.parse(eval(msg));
			if (obj.isSuccess) {
				changeDivBody(toUrl, 'sectionDatebody');
			} else {
				lmAlert("删除处理失败!");
			}
		},
		error : function() {
			lmAlert("提交通讯失败!");
		}
	});
}

/**
 * 生成静态区块
 * 
 * @param id
 *            区块分类id
 */
function createSectionStatic(id) {
	$.ajax({
		type : 'post',
		url : 'sectionController.do?createSectionStatic&t='
				+ new Date().getTime(),
		data : 'id=' + id,
		success : function(msg) {
			var obj = JSON.parse(eval(msg));
			if (obj.isSuccess) {
				lmAlert(obj.msg);
			} else {
				lmAlert(obj.msg);
			}
		},
		error : function() {
			lmAlert("提交通讯失败!");
		}
	});
}

function changecontent(urlStr) {
	$.ajax({
		type : 'post',
		url : urlStr,
		data : 't=' + new Date().getTime(),
		dataType : 'text',
		success : function(msg) {
			$('#dashboard').empty();
			$('#dashboard').append(msg);
		},
		error : function() {
			lmAlert("load page error, page url is " + urlStr);
		}
	});
}

function searchSectionData(urlStr, div) {
	$.ajax({
		type : 'post',
		url : urlStr,
		data : 't=' + new Date().getTime(),
		dataType : 'text',
		success : function(msg) {
			$('#' + div).empty();
			$('#' + div).append(msg);

		},
		error : function() {
			lmAlert("load page error, page url is " + urlStr);
		}
	});
}

function showEditModel(urlStr) {
	$.ajax({
		type : 'post',
		url : urlStr,
		data : 't=' + new Date().getTime(),
		dataType : 'text',
		success : function(msg) {
			$('#showeditModel').empty();
			$('#showeditModel').append(msg);
			$('#showeditModel').modal('show');
		},
		error : function() {
			lmAlert("load page error, page url is " + urlStr);
		}
	});
}
