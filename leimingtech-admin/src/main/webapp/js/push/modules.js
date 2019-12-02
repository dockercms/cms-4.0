/*
 *set datepicker default settings
 */
;(function($){
	$.datepicker.regional['zh-CN'] = {
		closeText: '关闭',
		// prevText: '&#x3c;',
		// nextText: '&#x3e;',
		prevText: '',
		nextText: '',
		currentText: '',
		monthNames: ['1','2','3','4','5','6',
		'7','8','9','10','11','12'],
		monthNamesShort: ['一月','二月','三月','四月','五月','六月',
		'七月','八月','九月','十月','十一月','十二月'],
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		weekHeader: '周',
		dateFormat: 'yy-mm-dd',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: ' .'};
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
})(jQuery);

/**
 * Tips
 */
 ;(function($){
 	$.fn.Tips = function(options){
	return this.each(function(){
			var inst = $(this);
			var defaults = {
				modal : !1
			};
			var settings = $.extend({},defaults,options);
			if(typeof(options) == "string"){
			inst.dialog(options);	
			}else{
			inst.dialog(settings);	
			}
		});
};
 })(jQuery);
 /*
	 * hidePopFrame
	 */
window.hidePopFrame= function(inst){
	$('.app-container').hide();
	if(arguments.length>0){
		$('.select-body').not(arguments[0]).hide();
	}else{
		$('.select-body').hide();
	}
	$('.filterpanel').hide();
	$('#proTemp').hide();
  $('#proTemp2').hide();
	$('.singledate').hide();
	$('#dateSelPanel').hide();
	$('.pop_body').hide();
};
/*
 * stopBubble
 */
function stopBubble(e) {
	// 如果提供了事件对象，则这是一个非IE浏览器
	if ( e && e.stopPropagation )
	// 因此它支持W3C的stopPropagation()方法
	e.stopPropagation();
	else
	// 否则，我们需要使用IE的方式来取消事件冒泡
	window.event.cancelBubble = true;
};
/**
 * it can be get the height of a DOM when the DOM is hide;
 */
function getDimensions(element) {
  element = $(element);  
  var display = $(element).css('display');  
  if (display != 'none' && display != null) // Safari bug
  return {width: element.offsetWidth, height: element.offsetHeight};  
  
  // All *Width and *Height properties give 0 on elements with display none,
  // so enable the element temporarily
  var originalVisibility = element.css('visibility');  
  var originalPosition = element.css('position'); 
  var originalDisplay = element.css('display'); 
  element.css('visibility','hidden');  
  element.css('position','absolute');  
  element.css('display','block');  
  var originalWidth = element.width();  
  var originalHeight = element.height();
  element.css('visibility',originalVisibility);  
  element.css('position',originalPosition);  
  element.css('display',originalDisplay); 
  return {width: originalWidth, height: originalHeight};  
}
/**
 * it can be used to replace the windows.confirm
 */
function confirm_msg(title,str,callback){
	$('<div>'+str+'</div>').dialog({title:title, buttons: [
		{
			text: "确认",
			click: function() { $(this).dialog("close"); callback(); }
		},		
		{
			text: "取消",
			click: function() { $(this).dialog("close");}
		}

	]});
};

function l_confirm(ob,str,callback,callback2){
	var l_confirm = $("<div class='l_confirm'></div>");
	l_confirm.append('<b class="lc_corner"></b>')
	.append("<span>"+str+"</span>")
	.append("<input type='button' value='确认' / class='iconOk purple-little-btn'>")
	.append("<input type='button' value='取消' / class='iconCancel purple-little-btn'>");
	l_confirm.find(".iconOk").click(function() {  l_confirm.remove(); callback(); });
	l_confirm.find(".iconCancel").click(function() {  l_confirm.remove();callback2();});
	console.log(ob.width());
	l_confirm.css({top:ob.offset().top+39,left:ob.offset().left+ob.width()-149});
	l_confirm.appendTo($("body"));
	l_confirm.show();

}
  function substringWithChinese(str,len){
     if(!str){
      return str;
     }
     var temp = "",index=0;
     for(var i=0;i<str.length;i++){
       if(str.charCodeAt(i)>255){
         index += 2;
       }else{
        index++;
       }
       temp += str.charAt(i);
       if(index >len){
        return temp+"......";
       }
     }
     return temp;
    }
/**
 * it can be used to replace the windows.alert
 */
window.alert = $.alert = function(msg,time){

	if(arguments.length<=0 || msg == undefined){
		msg = "";
	}
	var randomnum = parseInt(Math.random()*1000000000);
	if(time>0){
		$('<div id="temp_alert_box'+randomnum+'">'+msg+'</div>').Tips({title:"提示",width:300});
		setTimeout(function(){
			$('#temp_alert_box'+randomnum).Tips("close");
			$('#temp_alert_box'+randomnum).parent(".ui-dialog").remove();
			$('#temp_alert_box'+randomnum).remove();
		},time);
	}else{
		$('<div id="temp_alert_box">'+msg+'</div>').dialog({title:"提示",width:397,modal:true, buttons: [
		{
			text: "确认",
			click: function() { $(this).dialog("close");}
		}]});		
	}
};
/*
 * Pop Tips
 */

/*
 * $(document).delegate('.poptips','mouseover',function(){ var _ = $(this); var
 * panel = $('#'+_.attr('action-frame')); if(panel.length>0){
 * _.after(panel.clone()); _.next('#'+_.attr('action-frame')).fadeIn("fast"); }
 * });
 * 
 * $(document).delegate('.poptips','mouseleave',function(){ var _ = $(this); var
 * panel = $('#'+_.attr('action-frame')); _.next(panel).fadeOut("fast",function( ){
 * _.next(panel).remove(); }); })
 */

;(function($){
    $.fn.loadStart = function(){
    	$(this).each(function(i,e){
    		$("<div class='loading'></div>").appendTo($(e)).fadeIn("fast");
    	});
    };
    $.fn.loadEnd = function(){
    	$(this).each(function(i,e){
    		$(e).find(".loading").fadeOut("fast",function(){
    			$("this").remove();
    		});
    	});
    };
})(jQuery);

$.fn.renderList = function(opt){
  var _this = $(this);
  var defOpt = {
      'url' : '/app/appList',
      'callback' : 'callback',
      'params': {
        'offset':['offset',0],
        'perPage' : ['appPerPage',10]        
      }, 
      'list' : 'app_list',
      'render': function(d){},
      'error':function(msg){alert(msg);},
      'empty':function(){}
    };
    
  	var opt = $.extend(true,{},defOpt,opt||{});
    if(opt.url.indexOf("?") < 0){
    	opt.url = opt.url+"?"+opt.callback+"=?";
    }else{
    	opt.url = opt.url+"&"+opt.callback+"?";
    }

    function getList(){
	    var params = {};
	    $.each(opt.params,function(i,e){
	    	params[e[0]] = e[1];
	    }); 	
	    _this.loadStart();  
	    $.getJSON(opt.url,params,function(d){
	    	_this.loadEnd();  
		  	if(d.success != 1){
		  		var callback = opt.error;
		  		callback(d.error);
		  		return false;
		  	}else if(d.total_num < 1){
		  		var callback = opt.empty;
		  		callback();
		  		return false;
		  	}
		  	
		  	var callback = opt.render;
		  	callback(opt.list?d[opt.list]:d);
		  	if(offset >= d.total_num){
		  		_this.find(".pagenav").empty();
		  	}else{
		  		var totalNum = d.total_num,
		  		offset = opt.params.offset[1],
		  		perPage = opt.params.perPage[1],
		  		nowPage = parseInt(offset/perPage)+1,
		  		totalPage = Math.ceil(totalNum/perPage),
		  		nav = $('<div class="pagination fr"></div>');
		  		
		  		if(totalPage == 1){
		  			nav = $("");
		  		}
		  		if(nowPage == 1){
		  			nav.append('<span class="current prev">上一页</span><span class="current">1</span>');
		  		}else{
		  			nav.append('<a class="prev">上一页</a><a href="#">1</a>');
		  		}
		  		if(nowPage - 3 > 1){
		  			nav.append('<span class="current">...</span>');
		  		}
		  		for(i = nowPage-2;i<=nowPage+2;i++){
		  			if(i < 2){
		  				continue;
		  			}else if(i >= totalPage){
		  				break;
		  			}
			  		if(nowPage == i){
			  			nav.append('<span class="current">'+i+'</span>');
			  		}else{
			  			nav.append('<a href="#">'+i+'</a>');
			  		}		  		
			  	}
		  		if(nowPage + 3 < totalPage){
		  			nav.append('<span class="current">...</span>');
		  		}		  		
		  		if(nowPage == totalPage){
		  			nav.append('<span class="current">'+totalPage+'</span><span class="current next">下一页</span>');
		  		}else{
		  			nav.append('<a href="#">'+totalPage+'</a><a class="next">下一页</a>');
		  		}
		  		
		  		if(totalPage == 1){
		  			nav = $("");
		  		}
			  	_this.find(".pagenav").empty().append(nav);


			  	nav.find("a").click(function(){
			  		if($(this).hasClass("prev")){
			  			opt.params.offset[1] = opt.params.offset[1]-opt.params.perPage[1];
			  		}else if($(this).hasClass("next")){
			  			opt.params.offset[1] = opt.params.offset[1]+opt.params.perPage[1];
			  		}else{
			  			opt.params.offset[1] = (parseInt($(this).text())-1)*opt.params.perPage[1];
			  		}
			  		getList();
			  	});
		  	}
	  	});
    }


    getList();


}; 
	$.fn.renderLineChart = function(opt){
		var _this = $(this);
		var style = {
		fillColor: {
		  linearGradient: [0, 0, 0, 300],
		  stops: [
		  [0, 'rgb(160, 192, 193)'],
		  [1, 'rgba(255,255,255,0)']
		  ]
		},
		marker: {
		  symbol: 'circle',
		  fillColor: '#FFFFFF',
		  lineWidth: 2,
		  lineColor: null 
		}
		};
		function render(series,xAis){
			var chart = new Highcharts.Chart(
		        {
		            chart: {
		                renderTo: _this.attr("id"),
										type:"spline",	
										height:360	           
							  },
		            title:"",
		            xAxis: {
		                categories: xAis,
		                labels:{
		                    align:"right",
		                    step: parseInt(xAis.length/7)
		                }
		            },
					credits: {
						"enabled":false
					},
					yAxis: {
						title:"",
						min:0,
						labels: {
								formatter: function() {
										return this.value;
								}
		    			}
					},
		      series: series,
			    plotOptions: {
	            series: {
	                cursor: 'pointer'
	            }
	        },
					tooltip: {
						enabled: true,
						formatter: function() {
								return ''+this.x + ' '+ this.series.name + ' : '+ this.y;
						}
					},
					legend: {
						margin: 25,
						enabled: true
					}				
		        });
		}

		if(opt.data){
			var sum = 0;
			for(i in opt.data.series){
				for(j in opt.data.series[i].data){
					if(opt.data.series[i].data[j] && !isNaN(opt.data.series[i].data[j].y)){
						sum += opt.data.series[i].data[j].y;
					}else{
						sum += (opt.data.series[i].data[j]?opt.data.series[i].data[j]:0);
					}
				}
			opt.data.series[i] = $.extend(opt.data.series[i],style);
			}
			if(sum>0){
				render(opt.data.series,opt.data.xAis);
			}else{
				_this.html('<div class="nodata">暂无数据！</div>');
			}
		}

	};

function GetDateStr(_day){
	var dd = new Date();
	if(typeof(_day) == 'number'){
		dd.setDate(dd.getDate()+_day);// 获取_day天后的日期
	}else if(typeof(_day) == 'object'){
		dd = _day;
	}
	var y = dd.getFullYear();
	var m = dd.getMonth()+1;// 获取当前月份的日期
	m = m<10?"0"+m:m;
	var d = dd.getDate();
	d = d<10?"0"+d:d;
	return y+"-"+m+"-"+d;
} 

function lang (e){
	var z = {
		'send_num':'发送量',
		'open_num':'打开量',
		'version':'版本',
		'channel':'渠道',
		'tag':'标签',
		'language':'语言',
		'device_model' : '机型',
		'province' : '地域',
		'active':'活跃度',
		'gender':'性别'
	};
	return z[e]?z[e]:e;
}
$.fn.dateRangePicker = function(callback){
	var _this = $(this);
	_this.find("#dateSelect .start").text(GetDateStr(-7));
	_this.find("#dateSelect .end").text(GetDateStr(0));
	_this.css('position','relative');
	_this.append("<div id=proTemp><div id=datepickerStart></div><div id=datepickerEnd></div></div>");
	_this.find("#proTemp").append("<button val='7'>最近7天</button><button val='30'>最近30天</button><button val='60'>最近60天</button><button  val='submit'>确定</button>");
	// _this.find("#datepickerStart").datepicker();
	// _this.find("#datepickerEnd").datepicker();

	_this.find(".dateselect").click(function(){
		if(_this.find("#proTemp").css("display") != "none"){
			_this.find("#proTemp").hide();
			return ;
		}
		_this.find("#proTemp").show();
		_this.find("#datepickerStart").datepicker({ 
			defaultDate: _this.find("#dateSelect .start").text() 
		});
		_this.find("#datepickerEnd").datepicker({ 
			defaultDate: _this.find("#dateSelect .end").text() 
		});
	});
	_this.find("button").click(function(){
		var action = $(this).attr("val");
		var _do = {
			'7' : function(){
				_this.find("#dateSelect .start").text(GetDateStr(-7));
				_this.find("#dateSelect .end").text(GetDateStr(0));
			},
			'30' : function(){
				_this.find("#dateSelect .start").text(GetDateStr(-30));
				_this.find("#dateSelect .end").text(GetDateStr(0));
			},
			'60' : function(){
				_this.find("#dateSelect .start").text(GetDateStr(-60));
				_this.find("#dateSelect .end").text(GetDateStr(0));
			},
			'submit' : function(){

				_this.find("#dateSelect .start").text(GetDateStr(_this.find("#datepickerStart").datepicker("getDate")));
				_this.find("#dateSelect .end").text(GetDateStr(_this.find("#datepickerEnd").datepicker("getDate")));
			}
		};

		_do = _do[action];
		_do();
		$("#proTemp").hide();
		_this.find("#datepickerStart").datepicker( "destroy" );
		_this.find("#datepickerEnd").datepicker( "destroy" );
		if(callback){
			callback();	
		}
	});
};

$.fn.toolTip = function(opt){
	$(this).each(function(i,_this){
		var defOpt = {
			'posX' : 'center',
			'posY' : 'bottom'
		};
		o = $.extend(true,{},defOpt,opt||{});
		_this = $(_this);
		_this.css('position','relative');
		o.content = o.content?
		_this.find(o.content).addClass("tip")
		:$("<div class='tip'>"+_this.attr("title")+"</div>").appendTo(_this);
		o.content.css({
			'position':'absolute',
			'backgroundColor':'#000',
			'opacity':0.9,
			'zIndex': 600,
			'padding':10,
			'color':'#EFEFEF'
		});
		o.content.append("<div class='triangle'></div>");
		
		function givePos(){
			if(o.posX == 'center' && o.posY == 'middle'){
				o.posY = 'bottom';
			}
			if(o.posX == 'center'){
				o.content.css({
					'left': (_this.width()-o.content.width())/2
				});
			}
			if(o.posY == 'middle'){
				o.content.css({
					'top': (_this.height()-o.content.height())/2
				});
			}
			function _inverse (r){
				var e = {
					'top' : 'bottom',
					'bottom' : 'top',
					'right' :'left',
					'left' : 'right'
				};
				return e[r]?e[r]:r;
			}
			o.content.css(_inverse(o.posX), _this.width()+10);
			o.content.css(_inverse(o.posY), _this.height()+10);
			o.content.find(".triangle").attr("class","triangle triangle-"+o.posX+"-"+o.posY);
		}
		
		givePos(o.content);
		_this.mouseover(function(){
			var randomClass = "tipClone"+Math.round(Math.random()*100000);
			_this.attr("rc",randomClass);
			var tip = $(this).find(".tip").show();
			var clone = tip.clone().appendTo('body');
			$(".tipClone").remove();
			var tl = tip.parent().offset().left+tip.parent().width()/2-160;
			clone.css({
				'top' : tip.offset().top,
				'left' : tl,
				'position':'absolute'
			}).addClass(randomClass).addClass("tipClone");
			tip.hide();
			clone.css("box-shadow","0px 0px 5px #000").fadeIn(150);
		});
		_this.mouseleave(function(){
			var tc = $("."+$(this).attr("rc"));
			tc.fadeOut(150,function(){
				tc.remove();
			});
		});
	});
};

$(document).on("mouseenter", ".poptips", function(){
    var self = $(this), tooltip = $("#tooltipArea"), offset = self.offset();
    
    $('p', tooltip).text(self.attr("data-tooltip"));
    tooltip.css({left: offset.left + self.width() + "px", top:offset.top + (self.height() - 30)/2 + "px"}).show();
});

$(document).on("mouseout", ".poptips", function(){
    $("#tooltipArea").hide();    
});

$(document).on("mouseenter", ".poptipsTop", function(){
    var self = $(this), tooltip = $("#tooltipArea"), offset = self.offset();

    $('p', tooltip).text(self.attr("data-tooltip"));
    tooltip.css({left: offset.left -80+ "px", top:offset.top - 30 + "px"}).show();
});

$(document).on("mouseout", ".poptipsTop", function(){
    $("#tooltipArea").hide();
});

// --- upload btn start

$(document).on('change', '.upload_btn input[type="file"]', function(){
  var el = $(this), valfunc = el.parents('.upload_btn').attr("valfunc");

  	el.parent().next('span').text(el.val());
    if(!window[valfunc] || window[valfunc](el.val())){
  		
  		el.parent().siblings('button.upload').show();
  	} else {
  		el.parent().siblings('button.upload').hide();
  	}

  	$('input[type="hidden"]', el.parents('.upload_btn')).val('');
});

// upload
$(document).on('click', '.upload_btn ~ button.upload', function(){
  var el = $(this), action = el.attr('action'),
  	uploadarea = el.siblings(".upload_btn"),
  	file = $('input[type="file"]', uploadarea), reupload = el.siblings(".reupload"),
  	formData = new FormData();
  formData.append(file.attr('name'), file.get(0).files[0]);
  
  $.ajax({
	url: action,
	type: "POST",
	data: formData,
	processData: false,  // tell jQuery not to process the data
	contentType: false,   // tell jQuery not to set contentType
	success: function(data){
		if(data.success == "1"){
			$('input[type="hidden"]', uploadarea).val(data.file_path);
			file.attr("disabled", "disabled").val('').parent().hide();
			el.hide();
			reupload.show();
		} else {
			alert(data.error);
		}
	}
  });


  return false;
  // --- upload btn end
});

// reupload
$(document).on('click', '.upload_btn ~ button.reupload', function(){

	var el = $(this), uploadarea = el.siblings('.upload_btn'), 
	file = $('input[type="file"]', uploadarea), 
	uploadbtn = el.siblings('button.upload');
	$('input[type="hidden"]', uploadarea).val('');

	file.removeAttr("disabled");
	uploadarea.show();
	uploadarea.next('span').text('');
	el.hide();

	return false;
});

