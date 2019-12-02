$(function(){
	var flag=false;
	var toleft=false;
	var toright=false;
	$(".comp-navTop a").each(function (i){
		var $self=$(this);
		if(curUrl+"index.shtml"==$self.attr("href")){
			//$self.parent("li").addClass("active");
			flag=true;
			toleft=true;
			if($(".comp-navTop a:eq("+(i+1)+")").length!=0){
				toright=true;
			}
		}
	});
	if(!flag){
		//$(".comp-navTop li:eq(0)").addClass("active");
		if($(".comp-navTop a").length>1){
			toright=true;
		}
	}
	$(document).on('swipeleft', '#scroller .list_li', function() {
		if(toright){
			var url=$(".comp-navTop .active").next().find("a").attr("href");
			location.href=url;
		}
	});
	$(document).on('swiperight', '#scroller .list_li', function() {
		if(toleft){
			var url=$(".comp-navTop .active").prev().find("a").attr("href");
			location.href=url;
		}
	});
});

var myScroll,
pullDownEl, pullDownOffset,
pullUpEl, pullUpOffset,
generatedCount = 0,
pageindex=2;

$(window).resize(function (){
	setTimeout("changewindow()",100);
});

/**
 * 下拉刷新 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullDownAction () {
	     var contentCatId=  $("#contentCatId").val();
	     $(".newLi").remove();//清空栏目下的新闻数据
	     loadDataMy(contentCatId,1);
	     $("#pageNo").val(1);
}
//-------------------------------加载数据---------------------------------------------------------------
function loadDataMy(contentCatId,pageNo){
$("#pageNo").val(1);
if(pageNo==1){
	$(".newLi").remove();//清空栏目下的新闻数据
}
$.ajax({
		url :contextPath +'/front/frontDataController.do?loadData&contentCatId='+contentCatId+'&pageNo='+pageNo,//返回String
		type : "POST",
		dataType : 'JSON',//解析方式
		success : function(result) {
			if(result!="没有数据了"){
				json = JSON.parse(result);
				renderingDataMy(json);
			  $("#pageNo").val(pageNo);
			}
			 
		},
		error : function(e) {
		 
		},
		complete: function() {      
        	 
        		$("#pullUp").show(); 
        		$("#pullDown").show();
        		myScroll.refresh();
        }  
	});
}
//-------------------------------将数据渲染到html中---------------------------------------------------------------
function  renderingDataMy(json){
 
$.each(json, function(i,data) {
		if(json[i].thumb!="")
			{
			var str='<li data-inset="true" data-corners="false" data-shadow="false" data-iconshadow="true" data-wrapperels="div" data-icon="arrow-r" data-iconpos="right" data-theme="c" class="ui-btn ui-btn-icon-right ui-li-has-arrow ui-li list_li ui-li-has-thumb ui-btn-up-c newLi">'+
			'<div class="ui-btn-inner ui-li">'+
				'<div class="ui-btn-text">'+
					'<a href="'+json[i].wapUrl+'" " data-ajax="false" class="ui-link-inherit list_a">'+ 
					    '<img src="'+json[i].thumb+'" class="ui-li-thumb list_thumb">'+
						'<h3 style="" class="ui-li-heading list_title">'+json[i].title+'</h3>'+
						'<p class="list_summary"></p> '+
					'</a>'+
				'</div>'+
			'</div>'+
		'</li>';	 
 
         $("#thelist").append(str);
			}
		else{
			var str='<li data-inset="true" data-corners="false" data-shadow="false" data-iconshadow="true" data-wrapperels="div" data-icon="arrow-r" data-iconpos="right" data-theme="c" class="ui-btn ui-btn-icon-right ui-li-has-arrow ui-li list_li ui-li-has-thumb ui-btn-up-c newLi">'+
			'<div class="ui-btn-inner ui-li">'+
				'<div class="ui-btn-text">'+
					'<a href="'+json[i].wapUrl+'" " data-ajax="false" class="ui-link-inherit" style="padding-left:0px;line-height: 65px;">'+ 
						'<h3>'+json[i].title+'</h3>'+
						'<p class="list_summary"></p> '+
					'</a>'+
				'</div>'+
			'</div>'+
		'</li>';	 
 
          $("#thelist").append(str);
		}
 });	
}
/**
 * 滚动翻页 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullUpAction () {
	  var contentCatId=  $("#contentCatId").val();
	  var pageNo= parseInt($("#pageNo").val());
	  loadDataMy(contentCatId,pageNo+1);
}

/**
 * 初始化iScroll控件
 */
function loaded() {
	pullDownEl = document.getElementById('pullDown');
	pullDownOffset = pullDownEl.offsetHeight;
	pullUpEl = document.getElementById('pullUp');	
	pullUpOffset = pullUpEl.offsetHeight;
	
	myScroll = new iScroll('wrapper', {
		scrollbarClass: 'myScrollbar', /* 重要样式 */
		useTransition: false, /* 此属性不知用意，本人从true改为false */
		topOffset: pullDownOffset,
		onRefresh: function () {
			if (pullDownEl.className.match('loading')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
			} else if (pullUpEl.className.match('loading')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
			}
		},
		onScrollMove: function () {
			if (this.y > 5 && !pullDownEl.className.match('flip')) {
				pullDownEl.className = 'flip';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '松手开始更新...';
				this.minScrollY = 0;
			} else if (this.y < 5 && pullDownEl.className.match('flip')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
				this.minScrollY = -pullDownOffset;
			} else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
				this.maxScrollY = this.maxScrollY- pullUpOffset;
			}
		},
		onScrollEnd: function () {
			if (pullDownEl.className.match('flip')) {
				 
				pullDownEl.className = 'loading';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';				
				pullDownAction();	// Execute custom function (ajax call?)
			} else if (pullUpEl.className.match('flip')) {
				 
				pullUpEl.className = 'loading';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';				
				pullUpAction();	// Execute custom function (ajax call?)
			}
		}
	});
	
	setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);
}

function changewindow(){
	var $img=$("#swiper-container img:last");
	if($img.length==0){
		return;
	}
	$("#swiper-container").height("auto");
    $(".swiper-wrapper").height("auto");
    $(".swiper-slide").height("auto");
	if($img.complete){
		$img.on("load",function (){
			var containerH=$(this).height();
			var storage = window.localStorage;
			if(containerH!=0){
				 storage.removeItem("imgheight");// 清除textsize的值
				 storage.setItem("imgheight",containerH);
			}else{
				containerH = storage.getItem("imgheight");
			}
			$("#swiper-container img:not(:last)").height(containerH);
			$("#swiper-container").height(containerH);
		    $(".swiper-wrapper").height(containerH);
		    $(".swiper-slide").height(containerH);
		});
	}else{
		var containerH=$img.height();
		var storage = window.localStorage;
		if(containerH!=0){
			 storage.removeItem("imgheight");// 清除textsize的值
			 storage.setItem("imgheight",containerH);
		}else{
			containerH = storage.getItem("imgheight");
		}
		$("#swiper-container img:not(:last)").height(containerH);
	    $("#swiper-container").height(containerH);
	    $(".swiper-wrapper").height(containerH);
	    $(".swiper-slide").height(containerH);
	}
}

$(function (){
	var activeMenu=$(".comp-navTop .active");
	  var el=$(".comp-navTop ul");
	  $(".comp-navTop *").unbind();
	  var mc = new Hammer(el);
	  var curLeft=0;
	  
	  var activeWidth=activeMenu.width();
	  var activeleft=activeMenu.position().left+activeWidth/2;
	  
	  var lastLi=$(el).find("li").last();
	  var left=lastLi.position().left;
	  var maxLeft=left+lastLi.width()-$(window).width()+8+5+60;
	  
	  if($(window).width()/2<activeleft){
		  var thisX=activeleft-$(window).width()/2;
		  if(maxLeft>thisX){
			  curLeft=-thisX;
			  el.css ('-webkit-transform', 'translateX('+ (-thisX) +'px)');
			  el.css ('-moz-transform', 'translateX('+ (-thisX) +'px)');
			  el.css ('transform', 'translateX('+ (-thisX) +'px)');
		  }else if(maxLeft>0){
			  curLeft=-maxLeft;
			  el.css ('-webkit-transform', 'translateX('+(-maxLeft)+'px)');
			  el.css ('-moz-transform', 'translateX('+(-maxLeft)+'px)');
			  el.css ('transform', 'translateX('+(-maxLeft)+'px)');
		  }else{
			  curLeft=0;
			  el.css ('-webkit-transform', 'translateX(0px)');
			  el.css ('-moz-transform', 'translateX(0px)');
			  el.css ('transform', 'translateX(0px)');
		  }
	  }
	  
	  mc.on('drag',function(ev){
		  var aa=ev.gesture.deltaX+"  "+ev.gesture.deltaY;
		  el.css ('-webkit-transform', 'translateX('+ (curLeft+ev.gesture.deltaX) +'px)');
		  el.css ('-moz-transform', 'translateX('+ (curLeft+ev.gesture.deltaX) +'px)');
		  el.css ('transform', 'translateX('+ (curLeft+ev.gesture.deltaX) +'px)');
	  });
	  mc.on('dragend',function(ev){
		  var aa=ev.gesture.deltaX+"  "+ev.gesture.deltaY;
		  var elWidth=el.width();
		  var wWidth=$(window).width();
		  var lastLi=$(el).find("li").last();
		  var left=lastLi.position().left;
		  var maxLeft=left+lastLi.width()-wWidth+8+5+60;
		  var thisleft=curLeft;
		  curLeft=curLeft+ev.gesture.deltaX;
		  if(thisleft+ev.gesture.deltaX>0 || wWidth>elWidth){
			  curLeft=0;
			  el.css ('-webkit-transform', 'translateX(0px)');
			  el.css ('-moz-transform', 'translateX(0px)');
			  el.css ('transform', 'translateX(0px)');
		  }else if((maxLeft+thisleft+ev.gesture.deltaX)<0){
			  curLeft=-maxLeft;
			  el.css ('-webkit-transform', 'translateX('+(-maxLeft)+'px)');
			  el.css ('-moz-transform', 'translateX('+(-maxLeft)+'px)');
			  el.css ('transform', 'translateX('+(-maxLeft)+'px)');
		  }
	  });
	
	var $menu = $('nav#menutwo');

	$menu.mmenu({
		position:'right',
		classes	:'mm-light'
	});
	
    var mySwiper = new Swiper('.swiper-container',{
        pagination: '.my-pager',
        autoplay:3000,
        loop:true,
        grabCursor: true,
        paginationClickable: true
    })
    setTimeout("changewindow()",60);
    
    $("#wrapper").css("bottom","0");
	
    loadDataMy($("#contentCatId").val(),1);//
});

//初始化绑定iScroll控件 
//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', loaded, false); 