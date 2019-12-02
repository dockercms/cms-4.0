// JavaScript Document
function progressBar() {
	this.start = function() {
		$(".progressBars").modal("show");
		$(".progressdiv").html("<div class='progressBar'><div class='background'></div><div class='progress'></div><div class='hint'>100%</div></div>");
		var progressBars = $(".progressBar");
		var proportion = 0;
		this.progressBartimer = setInterval(function() {
			proportion = proportion + 0.01;
			begin(progressBars, proportion);
			if (1 - proportion < Math.pow(10, -14)) {
				proportion=0;
			}
		}, 10);
	};
	this.stop = function() {
		clearInterval(this.progressBartimer);
		begin($(".progressBar"),1);
		var stopTimer=setInterval(function(){
			$(".progressBars").modal("hide");
			clearInterval(stopTimer);
		},500);
	};
	var begin = function(progressBar, proportion) {
		var background = progressBar.find(".background");
		var progress = progressBar.find(".progress");
		var hint = progressBar.find(".hint");
		var maxWidth = progressBar.width() * proportion;
		progress.width(0);
		progress.show();
		hint.show();
		hint.html(Math.floor(proportion * 100) + "%");
//		if(proportion==1){
//			progress.stop().animate({
//				"width" : "+="+maxWidth + "px"
//			});
//		}else{
			progress.stop().css({
				"width" : maxWidth + "px"
			});
//		}
	};
};
var SystemProgressBar = new progressBar();