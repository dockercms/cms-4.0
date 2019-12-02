today=new Date();
var hours = today.getHours();
var minutes = today.getMinutes();
var seconds = today.getSeconds();
//var timeValue = "" + ((hours >24) ? hours -24 :hours); timeValue += ((minutes < 10) ? ":0" : ":") + minutes+"";
//timeValue += (hours >= 12) ? " 下午 " : " 上午 ";
function initArray(){
this.length=initArray.arguments.length
for(var i=0;i<this.length;i++)
this[i+1]=initArray.arguments[i]  }
var d=new initArray("星期日","星期一","星期二","星期三","星期四","星期五","星期六"); //document.write("",today.getFullYear(),"年","",today.getMonth()+1,"月","",today.getDate(),"日 ",d[today.getDay()+1]," ",timeValue); 

document.write("今天是",today.getFullYear(),"年","",today.getMonth()+1,"月","",today.getDate(),"日 ",d[today.getDay()+1]); 

function OnEnter( field ) 
{ 
	if( field.value == field.defaultValue ) 
	{ 
		field.value = ""; 
	} 
}
function OnExit( field ) 
{ 
	if( field.value == "" ) 
	{ 
		field.value = field.defaultValue; 
	} 
}
function isValid(form){

  var imagebutton = document.getElementById("imagebutton");
  imagebutton.disabled="disabled";  
  return true;
}

function sethome(obj, url) {
	try {
		obj.style.behavior = 'url(#default#homepage)';
		obj.setHomePage(url);
	} catch (e) {
		if (window.netscape) {
			try {
				netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
			} catch (e) {
				alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
			}
		} else {
			alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【" + url + "】设置为首页。");
		}
	}
}

function addfavorite(title, url) {
	try {
		window.external.addFavorite(url, title);
	} catch (e) {
		try {
			window.sidebar.addPanel(title, url, "");
		} catch (e) {
			alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请进入新网站后使用Ctrl+D进行添加");
		}
	}
}