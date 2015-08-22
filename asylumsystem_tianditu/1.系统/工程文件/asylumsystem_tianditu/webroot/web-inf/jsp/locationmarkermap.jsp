<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<title>震中位置</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<style type="text/css">
body {
	padding: 0px;
	margin: 0px;
}
</style>

<script language="javascript" src="http://api.tianditu.com/js/maps.js"></script>
<script language="javascript">
	var map;
	var zoom = 12;
	function onLoad(lon, lat, depth, magnitude, time) {
		//初始化地图对象 
		map = new TMap("mapDiv");
		//设置显示地图的中心点和级别 
		map.centerAndZoom(new TLngLat(lon, lat), zoom);
		//允许鼠标滚轮缩放地图 
		map.enableHandleMouseScroll();

		var config = {
			type : "TMAP_NAVIGATION_CONTROL_LARGE", //缩放平移的显示类型 
			anchor : "TMAP_ANCHOR_TOP_LEFT", //缩放平移控件显示的位置 
			offset : [ 0, 0 ], //缩放平移控件的偏移值 
			showZoomInfo : true
		//是否显示级别提示信息，true表示显示，false表示隐藏。 
		};
		//创建缩放平移控件对象 
		control = new TNavigationControl(config);
		//添加缩放平移控件 
		map.addControl(control);

		//创建比例尺控件对象
		var scale = new TScaleControl();
		//添加比例尺控件
		map.addControl(scale);

		var config = {
			anchor : "TMAP_ANCHOR_BOTTOM_RIGHT", //设置鹰眼位置
			size : new TSize(180, 120), //鹰眼显示的大小 
			isOpen : true
		//鹰眼是否打开，true表示打开，false表示关闭，默认为关闭 
		};
		//创建鹰眼控件对象 
		overviewMap = new TOverviewMapControl(config);
		//添加鹰眼控件 
		map.addControl(overviewMap);

		//创建地图类型控件对象 
		var control = new TMapTypeControl();
		//将地图类型控件添加到地图上 
		map.addControl(control);

		map.setMapType(TMAP_HYBRID_MAP);
		//创建图片对象 
		var icon = new TIcon("http://www.fjea.gov.cn/images/icon/point.gif",
				new TSize(120, 120), {
					anchor : new TPixel(60, 60)
				});
		//向地图上添加自定义标注 
		var marker = new TMarker(new TLngLat(lon, lat), {
			icon : icon
		});

		var x;
		var y;
		if (lon > 0) {
			x = lon + "°E";
		} else {
			x = -lon + "°W";
		}
		if (lat > 0) {
			y = lat + "°N";
		} else {
			y = -lat + "°S";
		}
		var htmlText = "<h4>地震信息</h4><hr><table class='no-style full' width='100%'><tbody>";
		htmlText += "<tr><td><b>坐标：</b></td><td class='ta-right'>" + x + "," + y + "</td></tr>";
		htmlText += "<tr><td><b>时间：</b></td><td class='ta-right'>" + time + "</td></tr>";
		htmlText += "<tr><td><b>震级(M)：</b></td><td class='ta-right'>" + magnitude + "级</td></tr>";
		htmlText += "<tr><td><b>震源深度：</b></td><td class='ta-right'>" + depth + "公里</td></tr>";
		htmlText += "</tbody></table>";
		marker.info = htmlText;
		TEvent.addListener(marker, "click", function(p) {
			this.openInfoWinHtml(this.info);
		});
		map.addOverLay(marker);
		marker.setInfoWinWidth(200);
		marker.setInfoWinHeight(20);
		marker.openInfoWinHtml(htmlText);
	}
</script>
</head>
<body
	onLoad="onLoad('${lon }','${lat}','${depth }','${magnitude }','${time }')">
	<div id="mapDiv" style="position:absolute;width:100%; height:100%"></div>
</body>
</html>
