$(document).ready(function() {
	$("#leftcontent dt").click(function() {
		$("#leftcontent dl").removeClass("current");
		$(this).parent().addClass("current");
	});
});