<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>地图</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords"
	content="听我的,声音地图,福建师范大学,地理信息系统,地理科学学院,GIS,11GIS">
<meta http-equiv="description" content="福建师范大学，地理科学学院，11级地理信息系统，音为你团队力作">

<!-- 引用百度地图js库 -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=XGOi8tnSGk5gdU0nPVGH1Pjw"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
<!-- 绘制弧线工具 -->
<script type="text/javascript" src="http://api.map.baidu.com/library/CurveLine/1.5/src/CurveLine.min.js"></script>

<script type="text/javascript" src="resources/scripts/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
	var map;//地图
	var voices = [];//声音数组

	// 初始化地图
	function initMap() {
		map = new BMap.Map("allmap");
		map.centerAndZoom("福州", 13);
		map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
		map.addControl(new BMap.ScaleControl());      // 添加默认比例尺控件
		map.addControl(new BMap.OverviewMapControl());//添加默认缩略地图控件
		
		var myCity = new BMap.LocalCity();
		myCity.get(function(result) {
			var cityName = result.name;
			map.setCenter(cityName);
		});
	}
	//定位
	function getLocation() {
		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r) {
			if (this.getStatus() == BMAP_STATUS_SUCCESS) {
				var mk = new BMap.Marker(r.point);
				map.addOverlay(mk);
				map.panTo(r.point);
				alert('您的位置：' + r.point.lng + ',' + r.point.lat);
			} else {
				alert('定位失败，状态码：' + this.getStatus());
			}
		}, {
			enableHighAccuracy : true
		});
	}
	//ajax test
	function test() {
		$.post("http://localhost:8080/TingwodeServer_SH/web/Voice-getAll", {
			name : "Donald Duck",
			city : "Duckburg"
		}, function(data, status) {
			if (status == "success") {
				voices = JSON.parse(data);
				addVoices();
			}
		});
	}
	//添加声音点
	function addVoices(){
		
		var markers=[];
		var points=[];
		for (var i=0; i < voices.length; i++) {
		alert(voices[0].longitude+"   "+voices[i].latitude);
			var ponint=new BMap.Point(voices[i].longitude,voices[i].latitude);
			points.push(ponint);
		   var marker=new BMap.Marker(ponint);
		   markers.push(marker);
		}
		var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});
		
		var curve = new BMapLib.CurveLine(points, {strokeColor:"blue", strokeWeight:3, strokeOpacity:0.5}); //创建弧线对象
		map.addOverlay(curve); //添加到地图中
	}
</script>
<script>
	$(document).ready(function() {
		initMap();
		getLocation();
		test();
		
	});
</script>
</head>
<body>
	<div style="width: 100%;height: 100%;overflow: hidden;margin:0;"
		id="allmap"></div>
</body>
</html>
