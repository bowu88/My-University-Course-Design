//地图基本配置
var center = [ 119.203, 26.034 ];
var zoom = 16;
var projection = 'EPSG:3857';
var extent=[13268735,3001831,13270771,3004448];//地图范围
var view = new ol.View({
	center : ol.proj.transform(center, 'EPSG:4326', 'EPSG:3857'),
	zoom : zoom,
	extent:extent
});
// geoserver地址
var geoserver_url = 'http://localhost:8090/geoserver/FjnuMap/wms';
var geoserver_gwc_url = 'http://localhost:8090/geoserver/gwc/service/wms';
var proxy_url = 'servlet/ProxyServlet?url=' + escape(geoserver_url);
var gwc_proxy_url = 'servlet/ProxyServlet?url=' + escape(geoserver_gwc_url);

// 基本变量
var map, draw, typeSelect, modify, layer_feature_arr = [];// 定制地图相关
// 初始化
// 窗口大小改变时调用
function changeMapSize() {
	var h = window.innerHeight || document.documentElement.clientHeight
			|| document.body.clientHeight;
	if (h > screen.availHeight || h == screen.availHeight) {
		//alert(h - screen.availHeight);
		$('#map').height(h);
		return;
	}
	var a = 90;
	var mh=$('#map_head').height();
//	alert(mh);
	if(mh>0){
		a+=mh+20;
	}
	$('#map').height(h - a);
}

$(document).ready(function() {
	changeMapSize();
	$(window).resize(function() {
		changeMapSize();
	});
});

// 基础地图服务
var layer_hyb = new ol.layer.Tile({
	source : new ol.source.MapQuest({
		layer : 'hyb'
	})
});
var layer_osm = new ol.layer.Tile({
	source : new ol.source.MapQuest({
		layer : 'osm'
	})
});
var layer_lat = new ol.layer.Tile({
	source : new ol.source.MapQuest({
		layer : 'sat'
	})
});
// 版权说明
var arrtibution = new ol.Attribution({
	html : '&copy; 11GIS <a href="http://www.fjnugis.tk">FjnuGIS</a>'
});

// WMS源
var wmsSource_fjnumap = new ol.source.TileWMS({
	url : geoserver_url,
	params : {
		'LAYERS' : 'FjnuMap',
	},
	serverType : 'geoserver',
	attributions : [ arrtibution ]
});

// 矢量数据
var layer_fjnumap = new ol.layer.Tile({
	source : wmsSource_fjnumap
});
var layer_fjnumap_rs=getWmsLayerByName('FjnuMap_rs');

var layer_domitory = getWmsLayer(proxy_url, 'FjnuMap:生活区',
		'119.2010953599968,26.022886678782694,119.20860935384876,26.041632582877973');
var layer_tiyuchangsuo = getWmsLayerByName('FjnuMap:体育场所');
var layer_bangonglou = getWmsLayerByName('FjnuMap:办公楼');
var layer_tushuguan = getWmsLayerByName('FjnuMap:图书馆');
var layer_jiaoxuequ = getWmsLayerByName('FjnuMap:教学区');
var layer_wenhuajie = getWmsLayerByName('FjnuMap:文化街');
var layer_xiaoyiyuan = getWmsLayerByName('FjnuMap:校医院');
var layer_shitang = getWmsLayerByName('FjnuMap:食堂');

var layer_jingdian = getWmsLayerByName('FjnuMap:景点');

// 遥感影像
// var layer_06 = getWmsLayer(proxy_url,
// 'FjnuMap:06_rpj','119.19329052388488,26.016508058517253,119.21570498491135,26.049400577866184');
// var layer_11 = getWmsLayer(proxy_url,
// 'FjnuMap:11_rpj','119.19022244676663,26.02001555599414,119.21759637195842,26.045128642895225');
var layer_11 = getWmsGwcLayer('FjnuMap:11_rpj');
var layer_06 = getWmsGwcLayer('FjnuMap:06_rpj');
var layer_11_proxy = getWmsLayer(proxy_url,'FjnuMap:11_rpj','119.19022244676663,26.02001555599414,119.21759637195842,26.045128642895225');
//var layer_06_proxy = getWmsGwcLayer('FjnuMap:06_rpj', gwc_proxy_url);

var locationOverlay;//显示位置
// 初始化地图
function initMap() {
//	var view = new ol.View({
//		center : ol.proj.transform(center, 'EPSG:4326', 'EPSG:3857'),
//		zoom : zoom
//	// center : center,
//	// zoom : zoom,
//	// projection:'EPSG:4326'
//	});
	var map = new ol.Map({
		target : 'map',
		layers : [ layer_domitory ],
		controls : //ol.control.defaults().extend(
				[ new ol.control.Zoom({zoomInTipLabel:'放大',zoomOutTipLabel:'缩小'}),
				  new ol.control.ZoomToExtent({tipLabel:'重置位置',extent:extent}),
				  new ol.control.Attribution({tipLabel:'版权信息'}),
				  new ol.control.ScaleLine(), new ol.control.FullScreen({
					tipLabel : '全屏浏览'
				}) ],
	//),
		view : view
	});
	// 图层组
	var layerGroup_v = new ol.layer.Group({
		layers : [ layer_osm, layer_fjnumap ]
	});
	map.setLayerGroup(layerGroup_v);
	var layerGroup_r = new ol.layer.Group({
		layers : [ layer_11 ]
	});
	layerGroup_rs = new ol.layer.Group({
		layers : [ layer_11, layer_hyb, layer_fjnumap_rs ]
	});

	$("#simple_layout").click(function() {
		map.setLayerGroup(layerGroup_v);
	});
	$("#rs_layout").click(function() {
		map.setLayerGroup(layerGroup_r);
	});
	$("#mix_layout").click(function() {
		map.setLayerGroup(layerGroup_rs);
	});
	// 信息窗口
	var info = $('#info');
	info.tooltip({
		animation : false,
		trigger : 'manual',
		html : true
	});
	map.on('singleclick', function(evt) {
//		var pixel = evt.pixel;
		var pixel = map.getEventPixel(evt.originalEvent);
//		alert(pixel[0]+","+pixel[1]);
		var viewResolution = /** @type {number}/ */
		(view.getResolution());
		var url = wmsSource_fjnumap.getGetFeatureInfoUrl(evt.coordinate,
				viewResolution, 'EPSG:3857', {
					'INFO_FORMAT' : 'application/json'
				});
		$('#info').css({
			left : pixel[0] + 'px',
			top : (pixel[1] -10) + 'px'
		});
		if (url) {
			$.ajax({
				type : "get",
				url : 'servlet/ProxyServlet?url=' + escape(url),
				// url : url,
				// success : function(json) {
				// alert(json.features[0].id);
				// //var obj = eval("(" + json + ")");
				// //alert(json);
				// },
				success : showFeatureInfo,
				error : function() {
					showInfoWindow("查询失败，服务器忙");
				},
				beforeSend : function() {
					showInfoWindow('查询中');
				}
			});
		}
	});
	//定位服务
	var geolocation = new ol.Geolocation({
		projection : view.getProjection()
	});
//	var track = new ol.dom.Input(document.getElementById('track'));
//	track.bindTo('checked', geolocation, 'tracking');
	$('#track').click(function(){
		if(geolocation.getTracking()){
			geolocation.setTracking(false);
			map.removeOverlay(locationOverlay);
			$('#track-span').removeClass('glyphicon glyphicon-ok');
		}else{
			geolocation.setTracking(true);
			map.addOverlay(locationOverlay);
			$('#track-span').addClass('glyphicon glyphicon-ok');
		}
		$('#track').toggleClass('active');
	});
	// handle geolocation error.
	geolocation.on('error', function(error) {
		alert(error.message);
	});

	var accuracyFeature = new ol.Feature();
	accuracyFeature.bindTo('geometry', geolocation, 'accuracyGeometry');

	var positionFeature = new ol.Feature();
//	positionFeature.setStyle(new ol.style.Style({
//		fill:new ol.style.Fill({
//			color:'rgba(00, 153, 255, 0.8)',
//			image : new ol.style.Circle({
//				stroke : new ol.style.Stroke({
//					color : '#ffffff',
//					width : 1
//				}),
//				radius : 7,
//				fill : new ol.style.Fill({
//					color : '#0099FF'
//				})
//			})
//		})
//	}));
	positionFeature.bindTo('geometry', geolocation, 'position').transform(
			function() {
			}, function(coordinates) {
				return coordinates ? new ol.geom.Point(coordinates) : null;
			});

	locationOverlay = new ol.FeatureOverlay({
		map : map,
		features : [ accuracyFeature, positionFeature ]
	});
}
// 显示图层信息
function showFeatureInfo(json) {
	if (json.features.length == 0) {
		showInfoWindow('未查询到结果');
		var t = setTimeout("$('#info').tooltip('hide')", 5000);
		return;
	}
	var html = '<table><tbody>';
	var the_feature = json.features[0];
	var geometry_type = the_feature.geometry.type;
	var p = the_feature.properties;
	if (geometry_type == 'MultiPolygon') {
		// MultiPolygon数据
		html += '<tr><td>名称：</td><td>' + p.NAME + '</td></tr>';
		html += '<tr><td>类型：</td><td>' + p.CLASS + '</td></tr>';
		html += '<tr><td>高度：</td><td>' + p.CENGSHU + '层</td></tr>';
		html += '<tr><td>备注：</td><td>' + p.DESC_ + '</td></tr>';
	} else {
		// Point数据
		html += '<tr><td>名称：</td><td>' + p.NAME + '</td></tr>';
		html += '<tr><td>规格：</td><td>' + p.SIZE + '</td></tr>';
		html += '<tr><td>时间：</td><td>' + p.AGE.substring(0,10) + '</td></tr>';
		html += '<tr><td>材质：</td><td>' + p.MATERIAL + '</td></tr>';
	}
	html += '</tbody></table>';
	showInfoWindow(html);
}
//显示信息窗口
function showInfoWindow(infoHtml) {
	$('#info').tooltip('hide').attr('data-original-title', infoHtml).tooltip(
			'fixTitle').tooltip('show');
}

// ---------------------------------- 时光机------------------------------------
function initTimeMachine() {
	var view = new ol.View({
		center : ol.proj.transform(center, 'EPSG:4326', 'EPSG:3857'),
		zoom : zoom
	// center : center,
	// zoom : zoom,
	// projection : 'EPSG:4326'
	});

	var map = new ol.Map({
		target : 'map',
		layers : [ layer_11, layer_06 ],
		controls : [ new ol.control.Zoom({zoomInTipLabel:'放大',zoomOutTipLabel:'缩小'}),
					  new ol.control.ZoomToExtent({tipLabel:'重置位置',extent:extent}),
					  new ol.control.Attribution({tipLabel:'版权信息'}),
					  new ol.control.ScaleLine(), new ol.control.FullScreen({
						tipLabel : '全屏浏览'
					}) ],
		view : view
	});

	var radius = 75;
	$(document).keydown(function(evt) {
		if (evt.which === 38) {
			radius = Math.min(radius + 5, 150);
			map.render();
		} else if (evt.which === 40) {
			radius = Math.max(radius - 5, 25);
			map.render();
		}
	});

	// 鼠标移动时获取鼠标位置
	var mousePosition = null;
	$(map.getViewport()).on('mousemove', function(evt) {
		mousePosition = map.getEventPixel(evt.originalEvent);
		map.render();
	}).on('mouseout', function() {
		mousePosition = null;
		map.render();
	});

	// 渲染图像前对图像进行裁剪
	layer_06.on('precompose', function(event) {
		var ctx = event.context;
		var pixelRatio = event.frameState.pixelRatio;
		ctx.save();
		ctx.beginPath();
		if (mousePosition) {
			// 只显示图像周围圆圈范围内的部分
			ctx.arc(mousePosition[0] * pixelRatio, mousePosition[1]
					* pixelRatio, radius * pixelRatio, 0, 2 * Math.PI);
			ctx.lineWidth = 5 * pixelRatio;
			ctx.strokeStyle = 'rgba(0,0,0,0.5)';
			ctx.stroke();
		}
		ctx.clip();
	});

	// 图像渲染完重新初始化
	layer_06.on('postcompose', function(event) {
		var ctx = event.context;
		ctx.restore();
	});
}

// -----------------------------------常用工具-------------------------------------
// 获取wms图像
function getWmsLayer(url, layer, bbox) {
	var wmsSource = new ol.source.TileWMS({
		url : url == null ? geoserver_url : url,
		params : {
			'LAYERS' : layer,
			'BBOX' : bbox
		},
		serverType : 'geoserver',
		attributions : [ arrtibution ]
	});
	var layer = new ol.layer.Tile({
		source : wmsSource
	});
	return layer;
}
function getWmsLayerByName(name, url) {
	if (!url) {
		url = proxy_url;
	}
	var wmsSource = new ol.source.TileWMS({
		url : url,
		params : {
			// 'LAYERS' : 'FjnuMap:'+name,
			'LAYERS' : name
		},
		serverType : 'geoserver',
		attributions : [ arrtibution ]
	});
	var layer = new ol.layer.Tile({
		source : wmsSource
	});
	return layer;
}
function getWmsGwcLayer(name, url) {
	if (!url) {
		url = geoserver_gwc_url;
	}
	var wmsSource = new ol.source.TileWMS({
		url : url,
		params : {
			// 'LAYERS' : 'FjnuMap:'+name,
			'LAYERS' : name,
			'VERSION' : '1.1.1',
			'SRS' : projection
		},
		serverType : 'geoserver',
		attributions : [ arrtibution ]
	});
	var layer = new ol.layer.Tile({
		source : wmsSource
	});
	return layer;
}
// ------------------------------------订制地图----------------------------------
var featureOverlay = new ol.FeatureOverlay({
	style : new ol.style.Style({
		fill : new ol.style.Fill({
			color : 'rgba(255, 255, 255, 0.3)'// 填充颜色
		}),
		stroke : new ol.style.Stroke({
			color : '#0099FF',// 边的颜色
			width : 3
		}),
		image : new ol.style.Circle({
			stroke : new ol.style.Stroke({
				color : '#ffffff',
				width : 1
			}),
			radius : 7,
			fill : new ol.style.Fill({
				color : '#0099FF'
			})
		})
	})
});
function addOverlayFeatureToMap() {
	var arr = featureOverlay.getFeatures().getArray();
	var layer_feature = new ol.layer.Vector({
		source : new ol.source.Vector({
			features : arr
		})
	});
	layer_feature.setStyle(featureOverlay.getStyle());
	map.addLayer(layer_feature);
	layer_feature_arr.push(layer_feature);
	featureOverlay.getFeatures().clear();
}
function addInteractionToMap() {
	draw = new ol.interaction.Draw({
		features : featureOverlay.getFeatures(),
		type : /** @type {ol.geom.GeometryType} */
		(typeSelect.value),
		style : featureOverlay.getStyle()
	});
	map.addInteraction(draw);
}

function initDrawTool() {
	featureOverlay.setMap(map);
	modify = new ol.interaction.Modify({
		features : featureOverlay.getFeatures(),
		deleteCondition : function(event) {
			return ol.events.condition.shiftKeyOnly(event)
					&& ol.events.condition.singleClick(event);
		}
	});
	map.addInteraction(modify);
	typeSelect = document.getElementById('draw_type');
	/**
	 * 改变形状
	 * 
	 * @param {Event}
	 *            e Change event.
	 */
	typeSelect.onchange = function(e) {
		map.removeInteraction(draw);
		addInteractionToMap();
	};

	addInteractionToMap();
}
function turnOffDrawTool() {
	map.removeInteraction(draw);
	map.removeInteraction(modify);
	typeSelect.onchange = null;
}
function removeFeaturesInOverlay() {
	featureOverlay.getFeatures().clear();
	while (layer_feature_arr.length > 0) {
		map.removeLayer(layer_feature_arr.pop());
	}
}
function bindInputVisiblity(id, layer, name, _class) {
	var html = '<tr class="' + _class + '"><td><label><input id="' + id
			+ '_visible" type="checkbox">' + name
			+ '</label></td><td><input id="' + id
			+ '_opacity" type="range" min="0" max="1" step="0.01"></td></tr>';
	$('#layer_ctrl').append(html);

	var visible = new ol.dom.Input(document.getElementById(id + '_visible'));
	visible.bindTo('checked', layer, 'visible');

	var opacity = new ol.dom.Input(document.getElementById(id + '_opacity'));
	opacity.bindTo('value', layer, 'opacity').transform(parseFloat, String);
}
function initMakeMap() {
	var view = new ol.View({
		center : ol.proj.transform(center, 'EPSG:4326', 'EPSG:3857'),
		zoom : zoom
	// center : center,
	// zoom : zoom,
	// projection:'EPSG:4326'
	});
	map = new ol.Map({
		target : 'map',
		layers : [ layer_11_proxy, layer_osm, layer_hyb, layer_domitory,
				layer_tiyuchangsuo, layer_bangonglou, layer_tushuguan,
				layer_jiaoxuequ, layer_wenhuajie, layer_xiaoyiyuan,
				layer_shitang, layer_jingdian ],
		// layers : [ layer_osm ],
		controls :ol.control.defaults().extend(
				[new ol.control.Zoom({zoomInTipLabel:'放大',zoomOutTipLabel:'缩小'}), 
				 new ol.control.Attribution({tipLabel:'版权信息'}),
				  new ol.control.ScaleLine(),
				 new ol.control.Rotate({tipLabel:'重置方向'})
				]),
		view : view
	});

	bindInputVisiblity('layer_jingdian', layer_jingdian, '景点', 'warning');
	bindInputVisiblity('layer_shitang', layer_shitang, '食堂', 'info');
	bindInputVisiblity('layer_xiaoyiyuan', layer_xiaoyiyuan, '校医院', 'info');
	bindInputVisiblity('layer_wenhuajie', layer_wenhuajie, '文化街', 'info');
	bindInputVisiblity('layer_jiaoxuequ', layer_jiaoxuequ, '教学区', 'info');
	bindInputVisiblity('layer_tushuguan', layer_tushuguan, '图书馆', 'info');
	bindInputVisiblity('layer_bangonglou', layer_bangonglou, '办公楼', 'info');
	bindInputVisiblity('layer_tiyuchangsuo', layer_tiyuchangsuo, '体育场所', 'info');
	bindInputVisiblity('layer_domitory', layer_domitory, '生活区', 'info');
	bindInputVisiblity('layer_hyb', layer_hyb, '道路', 'success');
	bindInputVisiblity('layer_osm', layer_osm, 'OSM图层', 'success');
	bindInputVisiblity('layer_11_proxy', layer_11_proxy, '11年遥感影像', 'success');
	var rotation = new ol.dom.Input(document.getElementById('rotation'));
	rotation.bindTo('value', view, 'rotation').transform(parseFloat, String);

	var resolution = new ol.dom.Input(document.getElementById('resolution'));
	resolution.bindTo('value', view, 'resolution')
			.transform(parseFloat, String);

	$('#draw_tool:checkbox').click(function() {
		if (this.checked) {
			initDrawTool();
		} else {
			turnOffDrawTool();
		}
	});
	$('#clearMap').click(function() {
		removeFeaturesInOverlay();
	});
	/*
	 * $('#saveMap').click(function() { var url; addOverlayFeatureToMap();
	 * map.once('postcompose', function(event) { var canvas =
	 * event.context.canvas; url = canvas.toDataURL('image/png'); });
	 * document.getElementById("saveMap").href =url; map.renderSync(); });
	 */
	var exportPNGElement = document.getElementById('saveMap');

	if ('download' in exportPNGElement) {
		exportPNGElement.addEventListener('click', function(e) {
			addOverlayFeatureToMap();
			map.once('postcompose', function(event) {
				var canvas = event.context.canvas;
				exportPNGElement.href = canvas.toDataURL('image/png');
			});
			map.renderSync();
		}, false);
	} else {
		alert("浏览器不支持导出地图");
	}
	// 初始化画笔设置工具
	initDrawSettingTool();
	// 撤消操作
	$('#back').click(function() {
		if (featureOverlay.getFeatures().getArray().length > 0) {
			featureOverlay.getFeatures().pop();
		} else if (layer_feature_arr.length > 0) {
			map.removeLayer(layer_feature_arr.pop());
		} else {
			alert("没有可清除掉图形！");
		}
	});
}
// 设置featureOverlay的样式
function setFeatureStyle() {
	var style = new ol.style.Style({
		fill : new ol.style.Fill({
			color : 'rgba(255, 255, 255, 0.3)'// 填充颜色
		}),
		stroke : new ol.style.Stroke({
			color : $('#color_s').val(),// 边的颜色
			width : $('#s_w').val()
		}),
		image : new ol.style.Circle({
			stroke : new ol.style.Stroke({
				color : '#ffffff',
				width : 1
			}),
			radius : $('#p_r').val(),
			fill : new ol.style.Fill({
				color : $('#color_p').val()
			})
		})
	});
	addOverlayFeatureToMap();
	featureOverlay.setStyle(style);
	map.removeInteraction(draw);
	addInteractionToMap();
}
// 初始化颜色选择工具
function initDrawSettingTool() {
	$('#color_p').cxColor();
	$('#color_s').cxColor();
	$('.fm-setting:input').bind('change', function() {
		setFeatureStyle();
	});
	// $('#color_s').bind('change', function() {
	// setFeatureStyle();
	// });
}