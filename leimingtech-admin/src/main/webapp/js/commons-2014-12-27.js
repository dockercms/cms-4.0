/*
 * 重新定义的通用方法 2014-12-27 11:55:54
 */


var lmAlertModel;
/**
 * 提示框
 */
function lmAlert(message, title) {
	
	if (!message)
		return;

	if (!title) {
		title = "提示";
	}
	if (lmAlertModel) {
		lmAlertModel.find(".alertBody").html(message);
		lmAlertModel.find(".alertTitle").html(title);
		lmAlertModel.modal('show');
		return;
	}
	var $alert = $("<div class='modal hide fade lmalert' tabindex='-1' role='dialog' data-width='300' aria-hidden='true'>");
	var $header = $("<div class='modal-header'>");
	var $closeBtn = $("<button type='button' class='close' data-dismiss='modal' aria-hidden='true'></button>");
	var $title = $("<h3 class='alertTitle'>" + title + "</h3>");
	$header.append($closeBtn).append($title);
	$alert.append($header);

	var $body = $("<div class='modal-body'>").append(
			"<p class='alertBody'>" + message + "</p>");
	$alert.append($body);

	var $footer = $("<div class='modal-footer'>").append(
			"<button data-dismiss='modal' class='btn green'>确定</button>");
	$alert.append($footer);

	$("body").append($alert);
	$alert.modal('show');
	lmAlertModel = $alert;
}

var lmConfirmModel;

/**
 * 确认提示框
 */
function lmConfirm(message,title,fun){
	if (!message)
		return;

	if (!title) {
		title = "提示";
	}
	if (lmConfirmModel) {
		lmConfirmModel.find(".confirmBody").html(message);
		lmConfirmModel.find(".confirmTitle").html(title);
		lmConfirmModel.find(".confirmIsOk").unbind().click(fun);
		lmConfirmModel.modal('show');
		return;
	}
	var $confirm = $("<div class='modal hide fade lmConfirm' tabindex='-1' role='dialog' data-width='300' aria-hidden='true'>");
	var $header = $("<div class='modal-header'>");
	var $closeBtn = $("<button type='button' class='close' data-dismiss='modal' aria-hidden='true'></button>");
	var $title = $("<h3 class='confirmTitle'>" + title + "</h3>");
	$header.append($closeBtn).append($title);
	$confirm.append($header);

	var $body = $("<div class='modal-body'>").append(
			"<p class='confirmBody'>" + message + "</p>");
	$confirm.append($body);

	var $okBtn =$("<button data-dismiss='modal' class='btn green confirmIsOk'>确定</button>");
	$okBtn.click(fun);
	
	var $footer = $("<div class='modal-footer'>").append(
			"<button data-dismiss='modal' class='btn'>取消</button>&nbsp;&nbsp;").append($okBtn);
	$confirm.append($footer);

	$("body").append($confirm);
	$confirm.modal('show');
	lmConfirmModel = $confirm;
}

/**
 * 打开一个新窗口
 * @param url
 */
function openUrl(url){
	var width  = (screen.availWidth-10)+"px";
	var height = (screen.availHeight-60)+"px";
	var leftm  = 0;
	var topm   = 0;
	// var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
	var args = "left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
	var w = window.open(url,"",args);
	if(!w){
		Dialog.alert( "发现弹出窗口被阻止，请更改浏览器设置，以便正常使用本功能!" ) ;
		return ;
	}
}


var sessionStorage = window.sessionStorage;
//changePageURL
function getSessionStorageBykey(key){
	
	if(!sessionStorage){
		return null;
	}
	
	if(sessionStorage.getItem(key)){
		return sessionStorage.getItem(key);
	}
	
	return null;
}

function setSessionStorage(key,value){
	if(!sessionStorage){
		return;
	}
	sessionStorage.setItem(key,value);
}

function getCurrentChangePageURL(){
	return getSessionStorageBykey("changePageURL");
}

function setCurrentChangePageURL(url){
	setSessionStorage("changePageURL",url);
}
