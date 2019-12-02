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

	$(window).resize(function(){
		fnRese();
		fnImg();
	});

	$(window).scroll(function(){
		fnImg();
		if($(document).scrollTop() > 200){
			$('.scrollBox').show();
		}else{
			$('.scrollBox').hide();
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
	$('.fore').hover(function(){
		$(this).toggleClass('active');
	});

	// banner焦点图
	(function(){
		var bannerCon = $('.banner'),
			bImgArr = [ url+'exp/images/banner_hot1.jpg', url+'exp/images/banner01.jpg', url+'exp/images/banner02.jpg', url+'exp/images/banner03.jpg', url+'exp/images/banner04.jpg'],
			bTextArr = ['第一个图片的文字', '第二个图片的文字', '第三个图片的文字', '第四个图片的文字', '第五个图片的文字'],
			bannerImgDiv = $('.bannerImg'),
			bannerImg = $('.bannerImg img'),
			bannerImgW = bannerImg.width(),
			bannerImgH = bannerImg.height(),
			bannerList = $('.banList').find('li'),
			oPrev = $('.b_prev'),
			oNext = $('.b_next'),
			iNow = 0,
			timer = null;
		bannerImg.css({ 'position' : 'absolute', 'top' : '50%', 'left' : '50%', 'margin-top' : -(bannerImgH/2), 'margin-left' : -(bannerImgW/2) });

		// bannerCon.on({
		// 	'mouseover' : function(){
		// 		bannerList.stop().animate( {'height' : '40px'}, 300, 'easeOut');
		// 	},
		// 	'mouseout' : function(){
		// 		bannerList.stop().animate( {'height' : '3px'}, 300, 'easeOut' );
		// 	}
		// });

		bannerCon.on({
			'mouseover' : function(){
				//$(this).find('.bannerBtnEm').stop().show();
			 	clearInterval(timer);
			},
			'mouseout' : function(){
				//$(this).find('.bannerBtnEm').stop().hide();
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
				if(iNow == bTextArr.length){
					iNow = 0;
				}
				fnFand();
				//alert(iNow)
			},3000);
		};
		autoPlay();

		// 上一张
		oPrev.on('click', function(){
			iNow --;
			if(iNow < 0){
				iNow = bTextArr.length-1;
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
			if(iNow == bTextArr.length){
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
					bannerImgDiv.find('li').eq(i).css({'z-index' : 99});
					bannerImgDiv.find('li').eq(i).stop().animate( {'opacity' : '0'}, 1000, 'easeOut' );
				}
				//alert(iNow)
				bannerList.eq(iNow).addClass('on');
				bannerImgDiv.find('li').eq(iNow).stop().animate( {'opacity' : '1'}, 1000, 'easeOut' );
				bannerImgDiv.find('li').eq(iNow).css({'z-index' : 100})
			})
		};



		// 初始化banner图片
		function fnTabImg(){
			for(i in bImgArr){
				bannerImgDiv.find('li').each(function(index){
					if(bImgArr[index]){
						$(this).html("<img src='" + bImgArr[index] + "'>");
					}else{
						$(this).html('<em>图片正在疯狂加载中...</em>')
					}
					bannerImgDiv.find('li').eq(0).css({'z-index' : 100})
				})
			}
			for(i in bTextArr){
				bannerList.children('div').each(function(index){
					if(bTextArr[index]){
						$(this).html(bTextArr[index])
					}else{
						$(this).html('加载失败...')
					}	
				})
			}
		}
		fnTabImg();
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