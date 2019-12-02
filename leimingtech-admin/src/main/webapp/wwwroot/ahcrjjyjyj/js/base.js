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
$(document).ready(function () {
	$(".sversion").hover(function () {
		$(".sversion-block").show();
	})
	$(".sversion-block").mouseleave(function () {
		$(".sversion-block").hide();
	})

	$(".nav li a").mouseenter(function () {
		$(this).parent().addClass("select");
		$(this).parent().find(".sub-menu").show();
	}).mouseleave(function () {
		$(this).parent().removeClass("select");
		$(this).parent().find(".sub-menu").hide();
	})

	$(".sub-menu").mouseleave(function () {
		$(this).hide();
	}).mouseenter(function () {
		$(this).show();
	})
	$(".kx .title li").mouseenter(function () {
		$(".kx li hr").remove();
		$(".kx li").removeClass("tabon");
		$(this).addClass("tabon").append("<hr class='slider-dot'>");
		$(".kx-news").removeClass("show");
		$("#"+$(this).data("link")).addClass("show");
	})
	$(".xxgk-title>ul>li").mouseenter(function () {
		$(".xxgk-title>ul>li").removeClass("itemon");
		$(this).addClass("itemon");
		$(".itemon.w60 a").css("background","url('/images/bstdbh2.png') !important");
	})
	$(".bsdt-ul li").mouseenter(function () {
		$(".bsdt-ul li").removeClass("bsdt-itemon");
		$(".bsdt-ul li").removeClass("bsdt-itemon-fix");
		if ($(this).hasClass("w60")) {
			$(this).addClass("bsdt-itemon-fix");
		}
		$(this).addClass("bsdt-itemon");
	})
	$(".zylj>ul>li").mouseenter(function () {
		$(".zylj>ul>li").removeClass("active");
		$(this).addClass("active");
		$(".links").hide();
		$("#"+$(this).data("name")).show();
	})
	$(".hovertitle a").mouseenter(function () {
		$(this).parent().find("a").removeClass("fthover");
		$(this).addClass("fthover");
		$(this).parent().parent().find(".hovercontent").hide();
		$(this).parent().parent().find("."+$(this).data("control")).show();
	})
	$(".service-platform img").mouseenter(function () {
		$(this).attr("src","http://pic.ahciq.gov.cn/2015/06/10/2015610151942.gif");
	}).mouseleave(function () {
		$(this).attr("src","http://pic.ahciq.gov.cn/2015/06/10/2015610151942_1.gif");
	})
	if ( $(".content-block").height() < 580 ) {
		$(".content-block").css("height","580px");
	}
        
	$(".bsdt-table tr").each(function () {
		$($(this).find("a")[0]).addClass("ellipsis");
	})
	if ( $(".content-block").height() < ($(".cjwtpage").height() + $(".cjsfw").height() + 80) ) {
        $(".content-block").css("height", $(".cjwtpage").height() + $(".cjsfw").height() + 80+"px");
    } 
})

