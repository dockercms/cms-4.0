/**
 * 广告展现次数增加
 * @param id 广告id
 */
function advDisplayAdd(advId){
	$.get(contextpath + "/advertisingController.do?advDisplayAdd&advId="+advId);
}

/**
 * 广告点击次数
 * @param id 广告id
 */
function advClickAdd(advId){
	$.get(contextpath + "/advertisingController.do?advClickAdd&advId="+advId);
}

$(function (){
	$(".friendLink_click").on("click",function (){
//		alert($(this).attr("data"));
	});
});