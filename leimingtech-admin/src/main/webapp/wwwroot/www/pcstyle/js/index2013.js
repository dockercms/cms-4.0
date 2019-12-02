function autoplay_imgs(img_num,t_span,bigboxs,nextbtn,perbtn){
	var timer=3000,
		index=0,
		imgsvar=$(img_num),
		trispan=$(t_span),
		//boxNum=imgsvar.length,
		boxNum=5,
		autoPlay,
		doPlay;
	doPlay=function(){
		index++;
		//if(index>=boxNum) index = 0;
		if(index>=5) index = 0;
		imgsvar.eq(index).show().siblings().hide();
		trispan.eq(index).addClass("cur").siblings().removeClass("cur");
	};
	autoPlay=setInterval(doPlay,timer);
	
	$(bigboxs).hover(function(){
		clearInterval(autoPlay);
		$(nextbtn).show();
		$(perbtn).show();
	},function(){
		autoPlay=setInterval(doPlay,timer);
		$(nextbtn).hide();
		$(perbtn).hide();
	});
	
	$(nextbtn).click(function(){
		doPlay();	
	});
	
	$(perbtn).click(function(){
		index==0?index=boxNum-1:index--;
		imgsvar.eq(index).show().siblings().hide();
		trispan.eq(index).addClass("cur").siblings().removeClass("cur");
	});

	trispan.mouseover(function(){
		index=trispan.index(this);
		imgsvar.eq(index).show().siblings().hide();
		trispan.eq(index).addClass("cur").siblings().removeClass("cur");
	});
}
autoplay_imgs("#focusImage li","#triggers span","#focusImage","#next_down","#pre_down");
mousevertabs(".smalltabs li",".smallcons>div","cur");
mousevertabs(".bigtabs li",".bigcons>div","cur");
mousevertabs(".phototabs li",".photocons>div","cur");
mousevertabs(".firendtit span",".firendcons>div","cur");

function slibe_imgone(){
	//if($("#scorimgs a").length<5)  return false;
	var end_box=$("#scorimgs"),
		vli = $("#scorimgs a"),
		vlen = vli.length,
		gg_var=5;
	//end_box.append('<span class="prev prev_disabled" title="上一页">上一页</span><span class="next" title="下一页">下一页</span>');

	vli.unwrap();
	var p=Math.ceil(vlen/gg_var),
		s_width;
	for(var i=1;i<=p;i++){
		var start=gg_var*(i-1),
			end=start+gg_var;
		if(end>vlen) end=vlen;
		var lastli=$("#scorimgs a").slice(start, end);
		lastli.wrapAll("<li></li>");
	}
	
	var page = 1,
		$v_show = $("#scorimgs ul"),
		v_width = $("#scorimgs li:first").width();
		$("span.next").click(function(){
			if( page == p-1){
				$("span.next").addClass("next_disabled");
			 }else if(page == p){
				return false;
			 }
			 if( !$v_show.is(":animated") ){
					$("span.prev").removeClass("prev_disabled");
					$v_show.animate({ left : '-='+v_width }, "slow");
					page++;
			 }
			  
	   });


		//往前 按钮
		$("span.prev").click(function(){
			if( page ==1){
				return false;
			 }else if(page == 2){
				$("span.prev").addClass("prev_disabled");
			 }
			 if( !$v_show.is(":animated") ){
					$("span.next").removeClass("next_disabled");
					$v_show.animate({ left : '+='+v_width }, "slow");
					page--;
			}
		});
		

}
slibe_imgone();



function l_play_imgs(idbox){
	var timer=3000,
		index=0,
		imgsvar=$(idbox),
		v_width=imgsvar.width(),
		boxNum=imgsvar.find('li').length,
		cons=imgsvar.find('ul'),
		autoPlay,
		doPlay;
	doPlay=function(){
		index++;
		if(index>=boxNum) index = 0;
		cons.stop().animate({left:'-'+v_width*index},200);
	};
	autoPlay=setInterval(doPlay,timer);
	
	imgsvar.hover(function(){
		clearInterval(autoPlay);
	},function(){
		autoPlay=setInterval(doPlay,timer);
	});

	imgsvar.find(".js_next").click(function(){
		doPlay();
	});
	
	imgsvar.find(".js_prev").click(function(){
		index==0?index=boxNum-1:index--;
		cons.stop().animate({left:'-'+v_width*index},200);
	});
}
l_play_imgs("#l_play_0");
l_play_imgs("#l_play_1");
l_play_imgs("#l_play_2");
l_play_imgs("#l_play_3");
l_play_imgs("#l_play_4");


function tabsplay_imgs(img_num,t_span,bigboxs,nextbtn,perbtn){
	var timer=3000,
		index=0,
		imgsvar=$(img_num),
		trispan=$(t_span).find("i"),
		boxNum=imgsvar.length,
		autoPlay,
		doPlay;
	doPlay=function(){
		index++;
		if(index>=boxNum) index = 0;
		imgsvar.eq(index).show().siblings().hide();
		trispan.eq(index).addClass("cur").siblings().removeClass("cur");
	};
	autoPlay=setInterval(doPlay,timer);
	
	$(bigboxs).parent().hover(function(){
		clearInterval(autoPlay);
	},function(){
		autoPlay=setInterval(doPlay,timer);
	});
	$(t_span).find(".tab_next").click(function(){
		doPlay();
	});
	
	$(t_span).find(".tab_prev").click(function(){
		index==0?index=boxNum-1:index--;
		imgsvar.eq(index).show().siblings().hide();
		trispan.eq(index).addClass("cur").siblings().removeClass("cur");
	});

	trispan.mouseover(function(){
		index=trispan.index(this);
		imgsvar.eq(index).show().siblings().hide();
		trispan.eq(index).addClass("cur").siblings().removeClass("cur");
	});
}
tabsplay_imgs("#tab_con0>div","#tab_tit0","#tab_con0");
tabsplay_imgs("#tab_con1>div","#tab_tit1","#tab_con1");

$(".photocons a").hover(
	function(){
			$(this).addClass('cur');
	},
	function(){
			$(this).removeClass('cur');
	}
);

$(window).bind('resize',function(){
	var jb_w=($(window).width()-980)/2+990;
	$("#fixedwrap").css({left:jb_w+'px'});
	if($(window).width()<980){
		$("#fixedwrap").hide();
	}else{
		$("#fixedwrap").show();
	}
});
