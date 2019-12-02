<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script src="media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=PsfbU6EktQdlpkFZIvEkp5qS"></script>
<script type="text/javascript">
var longitude = parent.document.getElementById("longitude").value;
var latitude = parent.document.getElementById("latitude").value;
window.onload = function(){
	var map = new BMap.Map("l-map");
	map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
	map.addControl(new BMap.OverviewMapControl());   //添加默认缩略地图控件
	if(longitude==""&&latitude==""){
		map.centerAndZoom("北京",15);                   // 初始化地图,设置城市和地图级别。
		map.enableScrollWheelZoom();                            //启用滚轮放大缩小
		
		map.addEventListener("click", function(e){
			document.getElementById("longitude").value = e.point.lng;
			document.getElementById("latitude").value = e.point.lat;
		});
	}else{
		var point = new BMap.Point(longitude, latitude);
		map.centerAndZoom(point,15);
		var marker = new BMap.Marker(point);  // 创建标注
		map.enableScrollWheelZoom();
		map.addOverlay(marker);              // 将标注添加到地图中
		marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
		map.addEventListener("click", function(e){
			document.getElementById("longitude").value = e.point.lng;
			document.getElementById("latitude").value = e.point.lat;
		});
	}
}
function sub(){
	if(document.getElementById("longitude").value!=""&&document.getElementById("latitude").value!=""){
		window.parent.document.getElementById("longitude").value = document.getElementById("longitude").value;
		window.parent.document.getElementById("latitude").value = document.getElementById("latitude").value;
	}
}
</script>

<title>百度地图</title>
<style type="text/css">
body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
#l-map{height:100%;width:100%;float:left;border-right:2px solid #bcbcbc;}
</style>
</head>
<body>
<div id="l-map" onclick="sub()"></div>
<div id="r-result">
<input type="hidden" id="longitude" name="longitude"/>
<input type="hidden" id="latitude" name="latitude"/></div>
</body>
</html>