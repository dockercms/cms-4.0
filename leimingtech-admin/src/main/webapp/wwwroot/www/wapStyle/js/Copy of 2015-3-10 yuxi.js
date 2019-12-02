var browser = {
	versions : function() {
		var u = navigator.userAgent, app = navigator.appVersion;
		return { // 移动终端浏览器版本信息
			trident : u.indexOf('Trident') > -1, // IE内核
			presto : u.indexOf('Presto') > -1, // opera内核
			webKit : u.indexOf('AppleWebKit') > -1, // 苹果、谷歌内核
			gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, // 火狐内核
			mobile : !!u.match(/AppleWebKit.*Mobile.*/), // 是否为移动终端
			ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), // ios终端
			android : u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, // android终端或uc浏览器
			iPhone : u.indexOf('iPhone') > -1, // 是否为iPhone或者QQHD浏览器
			iPad : u.indexOf('iPad') > -1, // 是否iPad
			webApp : u.indexOf('Safari') == -1
		// 是否web应该程序，没有头部与底部
		};
	}(),
	language : (navigator.browserLanguage || navigator.language).toLowerCase()
}
// document.writeln("语言版本: " + browser.language);
// document.writeln(" 是否为移动终端: " + browser.versions.mobile);
// document.writeln(" ios终端: " + browser.versions.ios);
// document.writeln(" android终端: " + browser.versions.android);
// document.writeln(" 是否为iPhone: " + browser.versions.iPhone);
// document.writeln(" 是否iPad: " + browser.versions.iPad);
// document.writeln(navigator.userAgent);

/**
 * 返回
 */
function goBack(){
	if (browser.versions.android) {
		window.yuxi.goBack();
	} else if (browser.versions.ios || browser.versions.iPhone
			|| browser.versions.iPad) {
		location.href = "yuxi:goBack";
	}else{
		history.back();
	}
}

/**
 * 爆料
 */
function baoliao() {
	if(!islogin()){
		if(confirm("你还没有登录哦！是否现在登录")){
			login();
		}
		return;
	}
	
	if (browser.versions.android) {
		window.yuxi.baoliao();
	} else if (browser.versions.ios || browser.versions.iPhone
			|| browser.versions.iPad) {
		location.href = "yuxi:baoliao";
	}
}

/**
 * 分享
 */
function share() {
	var thisurl = location.href;
	$("#url").val(thisurl);
	var title = $("#title").val();
	var host = window.location.host;
	var thisimage = $("#image").val();
	var Summary = $("#Summary").val();
	if (thisimage) {
		if (thisimage.indexOf("http://") == -1) {
			thisimage = "http://" + host +"/"+ thisimage;
			$("#image").val(thisimage);
		}
	}
	if (browser.versions.android) {
		try{
			window.yuxi.share(title, thisurl, thisimage,Summary);
		}catch(err){
			//android 1.0.4版本以前方法
			window.yuxi.share(title, thisurl, thisimage);
		}
	} else if (browser.versions.ios || browser.versions.iPhone
			|| browser.versions.iPad) {
		location.href = "yuxi:share";
	}
}

/**
 * 设置缓存大小
 * 
 * @param size
 */
function setcacheSize(size) {
	$("#cacheSize").html(size);
}

/**
 * 清除缓存
 */
function clearcache() {
	if (browser.versions.android) {
		window.yuxi.clearcache();
	} else if (browser.versions.ios || browser.versions.iPhone
			|| browser.versions.iPad) {
		location.href = "yuxi:clearcache";
	}
}

/**
 * 登录
 */
function login() {
	if (browser.versions.android) {
		window.yuxi.login();
	} else if (browser.versions.ios || browser.versions.iPhone
			|| browser.versions.iPad) {
		location.href = "yuxi:login";
	}
}

/**
 * 注册
 */
function register() {
	if (browser.versions.android) {
		window.yuxi.register();
	} else if (browser.versions.ios || browser.versions.iPhone
			|| browser.versions.iPad) {
		location.href = "yuxi:register";
	}
}

/**
 * 设置详细页文字大小
 */
function setContentTextSize(){
	var storage = window.localStorage;
	var textsize=storage.getItem("textsize");
	if(!textsize){
		storage.setItem("textsize","min");
		textsize="min";
	}
	var $img="<img src='../images/right.png'/>";
	switch (textsize) {
	case "max":
		$(".content").css({"fontSize":"20pt"});
		$(".content p").css({"fontSize":"20pt"});
		$(".content div").css({"fontSize":"20pt"});
		 $("p[size='max']").find("span").html($img);
		 $("#points").attr("value","3");
		 $(".text_size_info").html("大<img src='../images/jiantou.png'>");
		break;
	case "middle":
		$(".content").css({"fontSize":"15pt"});
		$(".content p").css({"fontSize":"15pt"});
		$(".content div").css({"fontSize":"15pt"});
		$("p[size='middle']").find("span").html($img);
		$("#points").attr("value","2");
		$(".text_size_info").html("中<img src='../images/jiantou.png'>");
		break;
	case "min":
		$(".content").css({"fontSize":"12pt"});
		$(".content p").css({"fontSize":"12pt"});
		$(".content div").css({"fontSize":"12pt"});
		$("p[size='min']").find("span").html($img);
		$("#points").attr("value","1");
		$(".text_size_info").html("小<img src='../images/jiantou.png'>");
		break;
	}
}


// 显示加载器
function showLoader() {  
    // 显示加载器.for jQuery Mobile 1.2.0
    $.mobile.loading('show', {  
        text: '加载中...', // 加载器中显示的文字
        textVisible: true, // 是否显示文字
        theme: 'b',        // 加载器主题样式a-e
        textonly: false,   // 是否只显示文字
        html: ""           // 要显示的html内容，如图片等
    });  
}  
  
// 隐藏加载器.for jQuery Mobile 1.2.0
function hideLoader()  
{  
    // 隐藏加载器
    $.mobile.loading('hide');  
}  

// 显示加载器
function showToast(message) {
    // 显示加载器.for jQuery Mobile 1.2.0
    $.mobile.loading('show', {  
        text: message, // 加载器中显示的文字
        textVisible: true, // 是否显示文字
        theme: 'b',        // 加载器主题样式a-e
        textonly: true,
        html: ""           // 要显示的html内容，如图片等
    }); 
    
    setTimeout("hideLoader()",1500);
}  

// var details = {author:"isaac","description":"fresheggs","rating":100};
// storage.setItem("details",JSON.stringify(details));
// details = JSON.parse(storage.getItem("details"));

// var storage = window.localStorage;
// function showStorage(){
// for(var i=0;i<storage.length;i++){
// //key(i)获得相应的键，再用getItem()方法获得对应的值
// document.write(storage.key(i)+ " : " + storage.getItem(storage.key(i)) +
// "<br>");
// }
// }

function getpointsValue(){
	 // 字体大小
	 var thisval=$("#points").val();
	 var textsize="min";
	 switch (thisval) {
		case "3":
			 textsize="max";
			break;
		case "2":
			textsize="middle";
			break;
		case "1":
			textsize="min";
			break;
		}
	 
	 var storage = window.localStorage;
	 storage.removeItem("textsize");// 清除textsize的值
	 storage.setItem("textsize",textsize);
	 setContentTextSize();// 设置字体大小
	 
}

/**
 * 设置推送开关
 */
function setSwitch(){
	var storage = window.localStorage;
	 var isswitch= storage.getItem("switch");
	 if(isswitch == "false"){
		 $("#switch").val("off").slider("refresh");
	 }else{
		 $("#switch").val("on").slider("refresh");
	 }
}

function switchConfirm(){
	var isopen=false;
	if(confirm("您将不能收到玉溪日报的优质推送啦，确定关闭推送吗？")){
		 storage.setItem("switch",false);
	 }else{
		 $("#switch").val("on").slider("refresh");
		 return false;
	 }
	
	if (browser.versions.android) {
		window.yuxi.pushswitch(isopen);
	} else if (browser.versions.ios || browser.versions.iPhone
			|| browser.versions.iPad) {
		location.href = "yuxi:pushswitch:"+isopen;
	}
}

$(function (){
	$(".vote_view").show();//显示投票
		setContentTextSize();// 设置字体大小
	 $(".textsize").on("click",function (){
		 var $self=$(this);
		 $(".textsize span").html("");
		 var $img="<img src='../images/right.png'/>";
		 $self.find("span").html($img);
		 var textsize=$self.attr("size");
		 
		 var storage = window.localStorage;
		 storage.removeItem("textsize");// 清除textsize的值
		 storage.setItem("textsize",textsize);
		 setContentTextSize();
	 });
	 setSwitch();//设置推送开关
	 $("#switch").on("change",function (){
		 var $self=$(this);
		 var storage = window.localStorage;
		 storage.removeItem("switch");
		 var isopen=false;
		 if("off"==$self.val()){
			 setTimeout("switchConfirm()",500);
			 return;
//			 storage.setItem("switch",false);
		 }else if("on"==$self.val()){
			 storage.setItem("switch",true);
			 isopen=true;
		 }
		 if (browser.versions.android) {
				window.yuxi.pushswitch(isopen);
			} else if (browser.versions.ios || browser.versions.iPhone
					|| browser.versions.iPad) {
				location.href = "yuxi:pushswitch:"+isopen;
			}
	 })
	 
	 $("#sendmsg").on("click",function (){ // 文章评论
		 var content = document.getElementById("CmntContent");
		 if(content.value.trim() == ""){
			 showToast("评论内容不能为空!");
			 content.focus();
			 return false;
		 }else{
			 var subform = document.getElementById("commentform");
		     subform.action = "/UCM/Services/MobileComment.jsp?CmntUserName=" + getUsername();
		     subform.submit();
	     }
	 });
	 
	 // 收藏
	 $("#favoritebtn").on("click", function(){
		 setTimeout("subfavorite()", 500);
	 });
	 
	 $("#showmessage").on("click",function (){
//		 if(!islogin()){
//			 showToast("你还没有登录哦！");
//			 return false;
//		 }
	 });
	 
	 $("#logout").on("click",function (){
		 logout();
		 if (browser.versions.android) {
			window.yuxi.logout();
		} else if (browser.versions.ios || browser.versions.iPhone
				|| browser.versions.iPad) {
			location.href = "yuxi:logout";
		}
	 });
	 
	 if(islogin()){
		 $(".loginview > p").html("欢迎你！"+getUsername());
	 }
	 
	 //检查更新
	 $("#checkNewApp").on("click",function (){
		if (browser.versions.android) {
			window.yuxi.checknewapp();
		} else if (browser.versions.ios || browser.versions.iPhone
				|| browser.versions.iPad) {
			location.href = "yuxi:checknewapp";
		}
	 });
	 
	 //意见反馈
	 $(".feedback").on("click",function (){
		 if (browser.versions.android) {
			window.yuxi.feedback();
		} else if (browser.versions.ios || browser.versions.iPhone
				|| browser.versions.iPad) {
			location.href = "yuxi:feedback";
		}
	});
	 
	 $(".favorite").on("click",favoriteList);
});

// 收藏
function subfavorite(){
	if(getUsername()){
		var subform = document.getElementById("favoriteform");
		subform.action = "/UCM/Member/AddToFavorite.jsp?MemberName=" + getUsername();
		subform.submit();
	}else{
		showToast("你还没有登录哦！");
	}
}

/**
 * 判断是否登录
 */
function islogin(){
	 var storage = window.localStorage;
	 var user=storage.getItem("yuxiuser");
	 if(user){
		 return true;
	 }
	 return false;
}

function getUid(){
	var storage = window.localStorage;
	return storage.getItem("uid");
}


function getUsername(){
	var storage = window.localStorage;
	return storage.getItem("yuxiuser");
}

function logout(){
	var storage = window.localStorage;
	storage.removeItem("yuxiuser");
	storage.removeItem("uid");
}


function tonexturl(){
	location.href="http://bbs.yuxi.cn";
}

/**
 * 保存一个用户
 */
function saveuser(uid,username,ucsynlogin){
	var storage = window.localStorage;
	storage.removeItem("yuxiuser");
	storage.removeItem("uid");
	storage.setItem("yuxiuser",username);
	storage.setItem("uid",uid);
	$(".loginview > p").html("欢迎你！"+getUsername());
	if(ucsynlogin){
		$("body").append(ucsynlogin);
	}
}

/**
 * 收藏列表
 */
function favoriteList(){
	if(getUsername()){
		window.location.href = "/UCM/Member/MobileFavoriteListPage.jsp?membername=" + getUsername();
	}else{
		showToast("你还没有登录哦！");
	}
}

// $(document).on("pagebeforecreate",function(event){
// showToast("pagebeforecreate");
// });
// $(document).on("pagecreate",function(event){
// });
// $(document).on("mobileinit",function(event){
// // showToast("触发 pageinit 事件！");
// });

/**
 * 获取列表数据后初始化布局
 */
function initView(jsonobj,$view){
	if(jsonobj.length>0){
		for(var i=0;i<jsonobj.length;i++){
			var obj=jsonobj[i];
			
			var img_list=obj.img_list;
			if(img_list!=''){
				var list=img_list.split("::::");
				var length=list.length;
				if(length>2){
					var $li=$("<li  data-inset='true' data-corners='false' data-shadow='false' data-iconshadow='true' data-wrapperels='div' data-icon='arrow-r' data-iconpos='right' data-theme='c' class='ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-btn-up-c list_li'>");
					var $div=$("<div class='ui-btn-inner ui-li'>");
					var $div2=$("<div class='ui-btn-text'>");
					var $a=$("<a href='"+obj.link+"' data-ajax='false' class='ui-link-inherit list_imgs_a'>");
					var $h3="<h3 style='"+obj.TitleStyle+"' class='ui-li-heading list_title'>"+obj.Title+"</h3>";
					$a.append($h3);
					var $P=$("<p class='ui-li-desc list_imgs'>");
					for(var j=0;j<3;j++){
						var img_content=list[j].split(":::");
						var imgurl=img_content[2];
						if(imgurl.indexOf("http://")==-1){
							imgurl=obj.level+imgurl;
						}
						var $img=$("<img src='"+imgurl+"' width='32%'>");
						if(j==0){
							$img.on( 'load', function() {
								$(this).nextAll().css("height",$(this).height()).show();
								myScroll.refresh();
							} );
						}else{
							$img.hide();
						}
						if(j!=2){
							$img.css("marginRight","2%");
						}
						$P.append($img);
					}
					$a.append($P);
					$div2.append($a);
		  			$div.append($div2);
		  			$li.append($div);
					$view.append($li);
					continue;
				}
				
			}
			
			var $li=$("<li data-inset='true' data-corners='false' data-shadow='false' data-iconshadow='true' data-wrapperels='div' data-icon='arrow-r' data-iconpos='right' data-theme='c' class='ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-btn-up-c list_li'>");
			var $div=$("<div class='ui-btn-inner ui-li'>");
			var $div2=$("<div class='ui-btn-text'>");
  			var $a=$("<a href='"+obj.link+"' data-ajax=false class='ui-link-inherit list_a'></a>");
  			var $h3=$("<h3 style='"+obj.TitleStyle+"' class='ui-li-heading list_title' >"+obj.Title+"</h3>");
  			if(obj.smallLogo!=''){
  				$a.append("<img src='"+obj.level+obj.smallLogo+"' class='ui-li-thumb list_thumb'>");
  				$li.addClass("ui-li-has-thumb");
  			}else{
  				$a.addClass("list_noThumb");
  				$h3.css("marginTop","0px");
  			}
  			$a.append($h3);
  			var $p="<p class='list_summary'>"+obj.Summary+"</p>";
  			$a.append($p);
  			if(obj.Attribute!=''){
  				var attrArray=obj.Attribute.split(",");
  				for(var j=0;j<attrArray.length;j++){
  					if(attrArray[j]=="toutiao"){
						var $span="<span class='ui-li-count info_type' >头条</span>";
						$a.append($span);
						break;
  					}
  					if(attrArray[j]=="dujia"){
						var $span="<span class='ui-li-count info_type' >独家</span>";
						$a.append($span);
						break;
  					}
  				}
  			}
  			$div2.append($a);
  			$div.append($div2);
  			$li.append($div);
  			$view.append($li);
		}
	}
}
