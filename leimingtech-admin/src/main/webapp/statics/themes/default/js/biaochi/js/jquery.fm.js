//权重 标尺
$(function(){
	$.fn.fmbiaoche = function(options){
		var defaults = {
			axis: 'y'//标尺的显示方式 x,y
		}
		var options = $.extend(defaults, options);
		this.each(function(){
			 var fz=this;
			$(this).click(function(e){//点击显示标尺 				   
				var xx=(e.originalEvent.x||e.originalEvent.layerX-Math.max(document.documentElement.scrollLeft,document.body.scrollLeft)||0)+10;
				var yy=(e.originalEvent.y||e.originalEvent.layerY-Math.max(document.documentElement.scrollTop,document.body.scrollTop)||0)-10;
			
				var bx=15;
				var bx_value=$(this).prev().attr('value')==''?0:$(this).prev().attr('value')*2;
				$(".biaoche").remove();
				if(options.axis=="y"){
					s="top";
					xx=((document.documentElement.clientWidth-xx)<30?document.documentElement.clientWidth-30:xx)+Math.max(document.documentElement.scrollLeft,document.body.scrollLeft);
					yy=((document.documentElement.clientHeight-yy)<220?document.documentElement.clientHeight-220:yy)+Math.max(document.documentElement.scrollTop,document.body.scrollTop);
				}
				else if(options.axis=="x"){ 
					s="left";
					xx=((document.documentElement.clientWidth-xx)<220?document.documentElement.clientWidth-220:xx)+Math.max(document.documentElement.scrollLeft,document.body.scrollLeft);
					yy=((document.documentElement.clientHeight-yy)<30?document.documentElement.clientHeight-30:yy)+Math.max(document.documentElement.scrollTop,document.body.scrollTop);
				}
				var title_content="<div class='biaoche' id='biaoche"+options.axis+"' style='top:"+yy+"px; left:"+xx+"px;'><div id='biaoche"+options.axis+"_left' style='"+s+":"+bx_value+"px;'></div><div id='biaoche"+options.axis+"_close' ></div></div>";
				$("body").append(title_content);
				$("#biaoche"+options.axis+"_left").draggable({//移动游标 采用JQuery中的UI插件
					containment: '#biaoche'+options.axis,
					axis: options.axis,
					grid:[2,2],
					start: function() {
					},
					drag: function() {
							divT=$("#biaoche"+options.axis+"_left").css(s);
							divT=(parseInt(divT)/2);
							$(fz).prev().attr('value',divT);
					}
				});	 
				$("#biaoche"+options.axis+"").click(function(e){//点击你所选中的刻度 
					if(options.axis=="y"){
						y=e.originalEvent.y||e.originalEvent.layerY+parseInt($("#biaoche"+options.axis+"").css(s))-Math.max(document.documentElement.scrollTop,document.body.scrollTop)||0;
						py=parseInt($("#biaoche"+options.axis+"").css(s))-Math.max(document.documentElement.scrollTop,document.body.scrollTop);
					}
					else if(options.axis=="x"){
						if(e.originalTarget.id=="biaoche"+options.axis+"_left"){
							return;
						}
						y=e.originalEvent.x||e.originalEvent.layerX+parseInt($("#biaoche"+options.axis+"").css(s))-Math.max(document.documentElement.scrollLeft,document.body.scrollLeft)||0;
						py=parseInt($("#biaoche"+options.axis+"").css(s))-Math.max(document.documentElement.scrollLeft,document.body.scrollLeft);
					}
					t=Math.ceil((y-py-5)/6);
					if(t>100) t=100;
					else if(t<0) t=0;
					$("#biaoche"+options.axis+"_left").css(s,t*6+"px");
					$(fz).prev().attr('value',t);
				});
				$("#biaoche"+options.axis+"_close").click(function(){//关闭标尺
					$("#biaoche"+options.axis+"").remove();
				});
			return false;
			});
		});
	};
});