var browser = {
	versions : function() {
		var u = navigator.userAgent, app = navigator.appVersion;
		return { // �ƶ��ն�������汾��Ϣ
			trident : u.indexOf('Trident') > -1, // IE�ں�
			presto : u.indexOf('Presto') > -1, // opera�ں�
			webKit : u.indexOf('AppleWebKit') > -1, // ƻ�����ȸ��ں�
			gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, // ����ں�
			mobile : !!u.match(/AppleWebKit.*Mobile.*/), // �Ƿ�Ϊ�ƶ��ն�
			ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), // ios�ն�
			android : u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, // android�ն˻�uc�����
			iPhone : u.indexOf('iPhone') > -1, // �Ƿ�ΪiPhone����QQHD�����
			iPad : u.indexOf('iPad') > -1, // �Ƿ�iPad
			webApp : u.indexOf('Safari') == -1
		// �Ƿ�webӦ�ó���û��ͷ����ײ�
		};
	}(),
	language : (navigator.browserLanguage || navigator.language).toLowerCase()
}
// document.writeln("���԰汾: " + browser.language);
// document.writeln(" �Ƿ�Ϊ�ƶ��ն�: " + browser.versions.mobile);
// document.writeln(" ios�ն�: " + browser.versions.ios);
// document.writeln(" android�ն�: " + browser.versions.android);
// document.writeln(" �Ƿ�ΪiPhone: " + browser.versions.iPhone);
// document.writeln(" �Ƿ�iPad: " + browser.versions.iPad);
// document.writeln(navigator.userAgent);

/**
 * ����
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
 * ����
 */
function baoliao() {
	if(!islogin()){
		if(confirm("�㻹û�е�¼Ŷ���Ƿ����ڵ�¼")){
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
 * ����
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
			//android 1.0.4�汾��ǰ����
			window.yuxi.share(title, thisurl, thisimage);
		}
	} else if (browser.versions.ios || browser.versions.iPhone
			|| browser.versions.iPad) {
		location.href = "yuxi:share";
	}
}

/**
 * ���û����С
 * 
 * @param size
 */
function setcacheSize(size) {
	$("#cacheSize").html(size);
}

/**
 * �������
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
 * ��¼
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
 * ע��
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
 * ������ϸҳ���ִ�С
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
		 $(".text_size_info").html("��<img src='../images/jiantou.png'>");
		break;
	case "middle":
		$(".content").css({"fontSize":"15pt"});
		$(".content p").css({"fontSize":"15pt"});
		$(".content div").css({"fontSize":"15pt"});
		$("p[size='middle']").find("span").html($img);
		$("#points").attr("value","2");
		$(".text_size_info").html("��<img src='../images/jiantou.png'>");
		break;
	case "min":
		$(".content").css({"fontSize":"12pt"});
		$(".content p").css({"fontSize":"12pt"});
		$(".content div").css({"fontSize":"12pt"});
		$("p[size='min']").find("span").html($img);
		$("#points").attr("value","1");
		$(".text_size_info").html("С<img src='../images/jiantou.png'>");
		break;
	}
}


// ��ʾ������
function showLoader() {  
    // ��ʾ������.for jQuery Mobile 1.2.0
    $.mobile.loading('show', {  
        text: '������...', // ����������ʾ������
        textVisible: true, // �Ƿ���ʾ����
        theme: 'b',        // ������������ʽa-e
        textonly: false,   // �Ƿ�ֻ��ʾ����
        html: ""           // Ҫ��ʾ��html���ݣ���ͼƬ��
    });  
}  
  
// ���ؼ�����.for jQuery Mobile 1.2.0
function hideLoader()  
{  
    // ���ؼ�����
    $.mobile.loading('hide');  
}  

// ��ʾ������
function showToast(message) {
    // ��ʾ������.for jQuery Mobile 1.2.0
    $.mobile.loading('show', {  
        text: message, // ����������ʾ������
        textVisible: true, // �Ƿ���ʾ����
        theme: 'b',        // ������������ʽa-e
        textonly: true,
        html: ""           // Ҫ��ʾ��html���ݣ���ͼƬ��
    }); 
    
    setTimeout("hideLoader()",1500);
}  

// var details = {author:"isaac","description":"fresheggs","rating":100};
// storage.setItem("details",JSON.stringify(details));
// details = JSON.parse(storage.getItem("details"));

// var storage = window.localStorage;
// function showStorage(){
// for(var i=0;i<storage.length;i++){
// //key(i)�����Ӧ�ļ�������getItem()������ö�Ӧ��ֵ
// document.write(storage.key(i)+ " : " + storage.getItem(storage.key(i)) +
// "<br>");
// }
// }

function getpointsValue(){
	 // �����С
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
	 storage.removeItem("textsize");// ���textsize��ֵ
	 storage.setItem("textsize",textsize);
	 setContentTextSize();// ���������С
	 
}

/**
 * �������Ϳ���
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
	if(confirm("���������յ���Ϫ�ձ���������������ȷ���ر�������")){
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
	$(".vote_view").show();//��ʾͶƱ
		setContentTextSize();// ���������С
	 $(".textsize").on("click",function (){
		 var $self=$(this);
		 $(".textsize span").html("");
		 var $img="<img src='../images/right.png'/>";
		 $self.find("span").html($img);
		 var textsize=$self.attr("size");
		 
		 var storage = window.localStorage;
		 storage.removeItem("textsize");// ���textsize��ֵ
		 storage.setItem("textsize",textsize);
		 setContentTextSize();
	 });
	 setSwitch();//�������Ϳ���
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
	 
	 $("#sendmsg").on("click",function (){ // ��������
		 var content = document.getElementById("CmntContent");
		 if(content.value.trim() == ""){
			 showToast("�������ݲ���Ϊ��!");
			 content.focus();
			 return false;
		 }else{
			 var subform = document.getElementById("commentform");
		     subform.action = "/UCM/Services/MobileComment.jsp?CmntUserName=" + getUsername();
		     subform.submit();
	     }
	 });
	 
	 // �ղ�
	 $("#favoritebtn").on("click", function(){
		 setTimeout("subfavorite()", 500);
	 });
	 
	 $("#showmessage").on("click",function (){
//		 if(!islogin()){
//			 showToast("�㻹û�е�¼Ŷ��");
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
		 $(".loginview > p").html("��ӭ�㣡"+getUsername());
	 }
	 
	 //������
	 $("#checkNewApp").on("click",function (){
		if (browser.versions.android) {
			window.yuxi.checknewapp();
		} else if (browser.versions.ios || browser.versions.iPhone
				|| browser.versions.iPad) {
			location.href = "yuxi:checknewapp";
		}
	 });
	 
	 //�������
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

// �ղ�
function subfavorite(){
	if(getUsername()){
		var subform = document.getElementById("favoriteform");
		subform.action = "/UCM/Member/AddToFavorite.jsp?MemberName=" + getUsername();
		subform.submit();
	}else{
		showToast("�㻹û�е�¼Ŷ��");
	}
}

/**
 * �ж��Ƿ��¼
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
 * ����һ���û�
 */
function saveuser(uid,username,ucsynlogin){
	var storage = window.localStorage;
	storage.removeItem("yuxiuser");
	storage.removeItem("uid");
	storage.setItem("yuxiuser",username);
	storage.setItem("uid",uid);
	$(".loginview > p").html("��ӭ�㣡"+getUsername());
	if(ucsynlogin){
		$("body").append(ucsynlogin);
	}
}

/**
 * �ղ��б�
 */
function favoriteList(){
	if(getUsername()){
		window.location.href = "/UCM/Member/MobileFavoriteListPage.jsp?membername=" + getUsername();
	}else{
		showToast("�㻹û�е�¼Ŷ��");
	}
}

// $(document).on("pagebeforecreate",function(event){
// showToast("pagebeforecreate");
// });
// $(document).on("pagecreate",function(event){
// });
// $(document).on("mobileinit",function(event){
// // showToast("���� pageinit �¼���");
// });

/**
 * ��ȡ�б����ݺ��ʼ������
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
						var $span="<span class='ui-li-count info_type' >ͷ��</span>";
						$a.append($span);
						break;
  					}
  					if(attrArray[j]=="dujia"){
						var $span="<span class='ui-li-count info_type' >����</span>";
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
