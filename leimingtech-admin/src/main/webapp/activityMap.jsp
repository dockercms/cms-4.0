<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script src="media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=PsfbU6EktQdlpkFZIvEkp5qS"></script>
<script type="text/javascript">
var activityaddress = parent.document.getElementById("activityaddress").value;
window.onload = function(){
	var map = new BMap.Map("l-map");
	map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
	map.addControl(new BMap.OverviewMapControl());   //添加默认缩略地图控件
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,15);
	if(activityaddress!=""){
		// 创建地址解析器实例
		var myGeo = new BMap.Geocoder();
		// 将地址解析结果显示在地图上,并调整地图视野
		myGeo.getPoint(activityaddress, function(point){
		  if (point) {
		    map.centerAndZoom(point, 16);
			var marker = new BMap.Marker(point);  // 创建标注
			map.enableScrollWheelZoom();
			map.addOverlay(marker);              // 将标注添加到地图中
			marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
		    map.addOverlay(marker);
		  }
		}, "北京市");
	}
	var gc = new BMap.Geocoder();
	map.addEventListener("click", function(e){
		var pt = e.point;
	    gc.getLocation(pt, function(rs){
	        var addComp = rs.addressComponents;
	        document.getElementById("activityaddress").value = addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
	   		sub();
	    });
	});
}

function sub(){
	if(document.getElementById("activityaddress").value!=""){
		window.parent.document.getElementById("activityaddress").value = document.getElementById("activityaddress").value;
	}
}
</script>

<title>百度地图</title>
<style type="text/css">
body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
#l-map{height:500px;width:560px;float:left;border-right:2px solid #bcbcbc;}
</style>
</head>
<body>
<div id="l-map"></div>
<div id="r-result">
	<input type="text" id="activityaddress" name="activityaddress"/>
</div>
</body>
</html>