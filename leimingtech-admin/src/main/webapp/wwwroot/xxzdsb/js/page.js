$(function(){
	// 全部商品分类
	(function(){
		$('.navAllsort').hide();
		$('.navgationLeft').mouseover(function(){
			$('.navAllsort').show();
		}).mouseout(function(){
			$('.navAllsort').hide();
		})
	})();

	// 分享
	(function(){
		$('.product-share').click(function(event){
			$('.shareBox').toggle();
			event.stopPropagation();
		});
		$('body').click(function(){
			$('.shareBox').hide();
		})
	})();

	// 选项卡
	(function(){
		fnTab($('.infor-tab'), 'li', $('.infor-tab-con'), 'click');

		function fnTab(parentTab, list, parentCon, sEvent){
			var aElem = parentTab.children(list);
			parentCon.hide().eq(0).show();

			aElem.each(function(index){
				$(this).on(sEvent, function(){
					aElem.removeClass('active');
					$(this).addClass('active');
					parentCon.hide().eq( index ).show();
				});
			});
		}
	})();

	// 商品详情焦点图
	(function(){
		var index=0;
		var length=$(".productFocus-list li").length;
		var i=1;

		//关键函数：通过控制i ，来显示图片
		function showImg(i){
			$(".productImg a").html($(".productFocus-list li").eq(i).html());
			$(".productFocus-list li").eq(i).addClass("on").siblings().removeClass("on");
		}

		function slideNext(){
			if(index >= 0 && index < length-1) {
				++index;
				showImg(index);
			}else{
				if(confirm("已经是最后一张,点击确定重新浏览！")){
					showImg(0);
					index=0;
					if(length > 5){
						aniPx=(length-5)*58+'px'; //所有图片数 - 可见图片数 * 每张的距离 = 最后一张滚动到第一张的距离
					}
					$(".productFocus-list").animate({ "left": "+="+aniPx },200);
					i=1;
				}
				return false;
			}
			if(i<0 || i>length-5){
				return false;
			}						  
			$(".productFocus-list").animate({ "left": "-=58px" },200)
			i++;
		}

		function slideFront(){
			if(index >= 1 ) {
				--index;
				showImg(index);
			}
			if(i<2 || i>length+5){
				return false;
			}
			$(".productFocus-list").animate({ "left": "+=58px" },200)
			i--;
		}

		$(".productFocus-next").click(function(){
			slideNext();
		});
		
		$(".productFocus-prev").click(function(){
			slideFront();
		});
		
		$(".productFocus-list li").click(function(){
			index  =  $(".productFocus-list li").index(this);
			showImg(index);
		});	

		$('.productImg').on('click', function(event){
			$('.productBigImg').html($(this).children('a').html());
			$('.productBigImg').show().animate({'opacity' : 1}, 200);
			event.stopPropagation();
		});
		$('.productBigImg').on('click', function(){
			$(this).hide().animate({'opacity' : 0}, 200);
		});
		$('body').click(function(){
			$('.productBigImg').hide();
		});
	})();


	// 评论星星
	(function(){
		var starLi = $('#star-bg li');
		for(var i = 0; i < starLi.length; i++){
			starLi[i].index = i;
			starLi[i].onmouseover = function(){
				for(var i = 0; i < starLi.length; i++){
					if(i <= this.index)
					{
						starLi[i].className = "hover";
					}else
					{
						starLi[i].className = "";
					}
				}
			}
			starLi[i].onmousedown=function (){
				// alert('提交成功:'+(this.index+1)+'分');
				$('.star-num').html((this.index+1))
			};
		}
	})();

	// 商品数量加减
	(function(){
        $(".product_remove").click (function (){
            var me = $ (this), 
            	minNum = me.parent().children('.minNum'),
            	maxNum = me.parent().children('.maxNum'),
            	attrNum = me.parent().children('.attribluteInput');
            var val = parseFloat(attrNum.val());
            if(val <= minNum.val()){
            	alert('最小购买数为' + minNum.val());
            	val = parseFloat(minNum.val())+1;
            }
            attrNum.val(val - 1);
        });
         
        $(".product_add").click (function (){
            var me = $ (this), 
            	minNum = me.parent().children('.minNum'),
            	maxNum = me.parent().children('.maxNum'),
            	attrNum = me.parent().children('.attribluteInput');

            var val = parseFloat(attrNum.val());
            
            if(parseInt(maxNum.val()) > 0){
            	if(val >= maxNum.val()){
            	alert('最大购买数为' + maxNum.val());
            	val = parseFloat(maxNum.val())-1;
            	}
            }
            
            attrNum.val(val + 1);
        });
	})();

	// 商品筛选更多点击按钮
	// (function(){
	// 	var a = true;
	// 	$('.screeningMore-btn').on('click', function(){
	// 		if(a){
	// 			$(this).addClass('screeningMore-on');
	// 			$('.screening-letter').show();
	// 			$('.screening-list').css({
	// 				'height' : '150px',
	// 				'overflow' : 'auto'
	// 			});
	// 			a = false;
	// 		}else{
	// 			$(this).removeClass('screeningMore-on');
	// 			$('.screening-letter').hide();
	// 			$('.screening-list').css({
	// 				'height' : '60px',
	// 				'overflow' : 'hidden'
	// 			});
	// 			$('.screening-list').scrollTop(0);
	// 			a = true;
	// 		}	
	// 	})
	// })();

	// 商品筛选更多点击按钮
	(function(){
		$('.screeningMore-btn').toggle(function(){
			$(this).addClass('screeningMore-on');
			$(this).parent().find('.screening-letter').show();
			$(this).parent().find('.screening-list').css({
				'height' : '150px',
				'overflow' : 'auto'
			});
			
		}, function(){
			$(this).removeClass('screeningMore-on');
			$(this).parent().find('.screening-letter').hide();
			$(this).parent().find('.screening-list').css({
				'height' : '60px',
				'overflow' : 'hidden'
			});
			$(this).parent().find('.screening-list').scrollTop(0);
		})
	})();

	// 您的信息修改
	(function(){
		//$('.information-change').on('click', function(){
		//	$(this).hide();
		//	$('.information-old').hide();
		//	$('.information-new').show();
		//});

		//$('input[name="infor-new-btn"]').on('click', function(){
		//	$('.information-change').show();
		//	$('.information-new').hide();
		//	$('.information-old').show();
		//});
	})();

	//村站选择选项卡
	(function(){
		$('.addList').each(function(index){
			$(this).on('click', function(){
				$(this).addClass('active').siblings().removeClass('active');
				$('.addTabCon').hide().eq(index).show();
			})
		})
	})();

	// 红包
	$('#bonus-left').on('click', function(){
		$(this).addClass('bonus-left-on');
		$('#bonus-right').show();
		$('#bonus-right-change').hide();
	});

	$('#bonus-btn-use').on('click', function(){
		$('#bonus-right').hide();
		$('#bonus-right-change').show();
	});

	$('#bonus-change-btn').on('click', function(){
		$('#bonus-right').show();
		$('#bonus-right-change').hide();
	});

	$('#bonus-btn-put').on('click', function(){
		$('#bonus-left').removeClass('bonus-left-on');
		$('#bonus-right').hide();
	})

	// 余额
	$('#remaining-left').on('click', function(){
		$('#remaining-right').show();
		$(this).addClass('bonus-left-on');
	});

//	$('#remaining-use').on('click', function(){
//		$('#remaining-right').hide();
//		$('#remaining-change-box').show();
//	});

	$('#remaining-change-btn').on('click', function(){
		$('#remaining-right').show();
		$('#remaining-change-box').hide();
	});


	// 今日行情
	(function(){
		var njtodyPrev = $('.njtoday-tab a').eq(0),
			njtodyNext = $('.njtoday-tab a').eq(1),
			njtodyList = $('.njtoday-con'),
			now = 0;

		function fnHave(){
			if(now <= 0){
				njtodyPrev.removeClass('njtoday-have');
			}else if(now >= njtodyList.length-1){
				njtodyNext.removeClass('njtoday-have');
			}else{
				$('.njtoday-tab a').addClass('njtoday-have');
			}
		}
		fnHave();

		function fnPrev(){
			now --;
			if(now <= 0){
				now = 0;
			}
			njtodyList.eq(now).show().siblings('.njtoday-con').hide();
		}

		function fnNext(){
			now ++;
			if(now >= njtodyList.length-1){
				now = njtodyList.length-1;
			}
			njtodyList.eq(now).show().siblings('.njtoday-con').hide();
		}

		njtodyPrev.on('click', function(){
			fnPrev();
			fnHave();
		});
		njtodyNext.on('click', function(){
			fnNext();
			fnHave();
		});
	})();

	// 农技中心焦点图
	//$('.njBanner-min li').mouseover(function(){
	//	$('.njBanner-min li').children('em').show();
	//	$(this).children('em').hide();

	//	var ImgSrc = $(this).children('img').attr('src');

	//	$('.njBanner-max img').attr('src', ImgSrc);
	//});

	// 新闻列表左导航
	(function(){
		$(window).scroll(function() {
				// alert('aaa')
			if($('.nj-infoBox').children().is('.njLeft')){
				if($(window).scrollTop() >= $('.njLeft').offset().top){
					$('.nj-left-nav').css({
						'position' : 'fixed',
						'top' : '-10px'
					})
				}else{
					$('.nj-left-nav').attr('style', '');
				}
			}	
		});
	})();
});