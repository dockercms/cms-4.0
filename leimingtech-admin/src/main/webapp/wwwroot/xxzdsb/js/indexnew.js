$(function(){

	var url = "http://static.ync365.com/";

	// 图片延迟加载

	function loadImage(url, callback) {

		var img = new Image(); 

		img.src = url;



		if(img.complete) { 

			callback.call(img);

			return true; 

		}

		img.onload = function () { 

			callback.call(img)

		};

	}



	function fnImg(){

		var aImg=$('img');

		var scrollBottom= $(document).scrollTop() +document.documentElement.clientHeight;



		$.each(aImg,function(index,val){

			//console.log(index);

			$obj  = $(val);

			pos = $obj.position();

			if(scrollBottom>=pos.top){



				if($obj.attr('_src')){

					$obj.attr('src',$obj.attr('_src'));

					// loadImage($obj.attr('_src'),function(img){

						 // $obj.attr('src',$obj.attr('_src'));

					// });					

				}

			}

		});		

	}

	fnImg();



	// 选项卡

	(function(){

		fnTab($('.activityTab'), 'li', $('.activityList'), 'mouseover');

		fnTab($('.hotshopTab '), '.smt', $('.productList'), 'mouseover');



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



	// 根据分辨率判断css

	function fnRese(){

		if($(window).width() <= 1198){

			$('#css').attr('href', 'ynccss/style1000.css');

		}else{

			$('#css').attr('href', 'ynccss/style.css');

		}

	}

	fnRese();

	function fnHotImg(){
		var imgW = $('.headerHot').find('img').width();
		$('.headerHot').css({
			'width' : $(window).width(),
			'overflow' : 'hidden',
			'position' : 'relative'
		})
		$('.headerHot').find('img').css({
			'position' : 'absolute',
			'left' : '50%',
			'margin-left' : -imgW/2
		})
	}

	fnHotImg();

	$(window).resize(function(){

		fnRese();

		fnImg();
		fnHotImg();
	});



	$(window).scroll(function(){

		fnImg();

		if($(document).scrollTop() > 200){

			$('.scrollBox').show();

		}else{

			$('.scrollBox').hide();

		}

		if($(document).scrollTop() > 3500){
			$('.index_cj_con').hide();
			//$(document).scrollTop(0);
		}

	});



	// 搜索框

	// (function(){

	// 	var searchText = $('.searchText');

	// 	searchText.focus(function(){

	// 		if($(this).val() == "请输入你要找的商品名"){

	// 			$(this).val('');

	// 		}

	// 	});

	// 	searchText.blur(function(){

	// 		if($(this).val() == ''){

	// 			$(this).val('请输入你要找的商品名');

	// 		}

	// 	});

	// })();



	// 左边栏选项卡

	/*$('.fore').hover(function(){

		$(this).toggleClass('active');

	});*/

	$('.fore').hover(function(){

		$(this).addClass('active');

	}, function(){

		$(this).removeClass('active');

	});



// banner焦点图new

	(function(){

		var bannerCon = $('#indexBanner'),

			bannerImgDiv = $('.bannerImg'),

			bannerDiv = $('.bannerImg > .bannerImg-only'),

			// bannerImg = $('.bannerImg > .bannerImg-only').find('img'),

			bannerImgW = bannerDiv.width(),

			bannerImgH = bannerDiv.height(),

			oPrev = $('.b_prev'),

			oNext = $('.b_next'),

			iNow = 0,

			timer = null;

		bannerDiv.each(function(i){
			bannerDiv.eq(i).css("z-index", 10-i);
		})



		var bannerLi = '<li></li>';

		for(var j = 0; j < bannerDiv.length; j++){

			$('.banList > ul').append(bannerLi)

		}



		var bannerList = $('.banList').find('li');

		bannerList.eq(0).addClass('on');

		bannerList.each(function(i){
			$(this).html(i+1);
		})


		bannerDiv.css({ 'position' : 'absolute', 'top' : '50%', 'left' : '50%', 'margin-top' : -(bannerImgH/2), 'margin-left' : -(bannerImgW/2) });


		bannerCon.on({

			'mouseover' : function(){

			 	clearInterval(timer);

			},

			'mouseout' : function(){

				autoPlay();

			}

		});



		bannerList.on('hover', function(){

			iNow = $(this).index();

			fnFand();

		});



		// 自动切换

		function autoPlay(){

			timer = setInterval(function(){

				iNow++;

				if(iNow == bannerDiv.length){

					iNow = 0;

				}

				fnFand();

			},3000);

		};

		autoPlay();



		// 上一张

		oPrev.on('click', function(){

			iNow --;

			if(iNow < 0){

				iNow = bannerDiv.length-1;

			}

			fnFand();

		})

		oPrev.mouseover(function(){

			$(this).find('.bannerBtnEm').stop().show();

		}).mouseout(function(){

			$(this).find('.bannerBtnEm').stop().hide();

		});

		// 下一张

		oNext.on('click', function(){

			iNow ++;

			if(iNow == bannerDiv.length){

				iNow = 0;

			}

			fnFand();

		})

		oNext.mouseover(function(){

			$(this).find('.bannerBtnEm').stop().show();

		}).mouseout(function(){

			$(this).find('.bannerBtnEm').stop().hide();

		});



		// 图片切换

		function fnFand(){

			bannerList.each(function(index){



				for(var i = 0; i < bannerList.length; i++){

					bannerList.eq(i).removeClass('on');

					bannerDiv.eq(i).css({'z-index' : 99});

					bannerDiv.eq(i).stop().animate( {'opacity' : '0'}, 1000, 'easeOut' );

				}



				bannerList.eq(iNow).addClass('on');

				bannerDiv.eq(iNow).stop().animate( {'opacity' : '1'}, 1000, 'easeOut' );

				bannerDiv.eq(iNow).css({'z-index' : 100})

			})

		};



	})();



	



	// 选项卡底边线效果

	(function(){

		$('.hotshopTab').children('.smt').on('mouseover', function(index){

			var hotshopBorder = $('.hotshop_border'),

				hotshopBorderW = hotshopBorder.width();	

			hotshopBorder.stop().animate({'left' : hotshopBorderW*($(this).index()-1)}, 300, 'easeIn');

		})





	})();



	// 广告图片文字提示

	(function(){

		$('.hotBrand_ad').on({

			'mouseover' : function(){

				$(this).find('.hotBrandAd_text').stop().animate({'bottom' : '5'}, 100, '').show();

			},

			'mouseout' : function(){

				$(this).find('.hotBrandAd_text').stop().animate({'bottom' : '-52'}, 100, '').show();

			}

		});

	})();



	// 微信提示

	// (function(){

	// 	$('.scrollBox_w').hover(function(){

	// 		$(this).toggleClass('active');

	// 	})

	// })();



	// 返回顶部

	(function(){

		var $back_to_top = $('.scrollTop');

		$back_to_top.on('click', function(event){

			event.preventDefault();

			$('body,html').animate({scrollTop: 0}, 300);

		});

	})();



	if(window.navigator.userAgent.indexOf('MSIE 6.0')!=-1){

		window.onload=function(){

			var oscrollBox=$('.scrollBox')[0];

			window.onscroll=function(){

				//alert('aaa')

				var scrollT=document.documentElement.scrollTop || document.body.scrollTop;

				var t=document.documentElement.clientHeight-oscrollBox.offsetHeight;

				oscrollBox.style.top=t+scrollT+'px';	

			}	

		}

	}

})