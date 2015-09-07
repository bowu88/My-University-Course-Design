var map=null;// 地图
var markerClusterer=null;//点聚集

var voices = [];// 声音数组
var myvoices;//用户声音
var myalbums;//用户专辑
var albumVoiceMarkers=[];//专辑声音(我的专辑功能）
var voicesid=[];//声音id数组 (发布专辑功能)
var mylocation;
var mylocationMarker = null;

//将值添加到指定id的input里
function setInputValue(obj){
	$("#"+obj.id).val(obj.value);
}

function setInputValueById(id,obj){
	alert(obj.value);
	$("#"+id).val(obj.value);
}

function setInputFile(obj){
	$("#"+obj.id).select(obj.value);
}

//用户登录
function login(){
	$.post("web/User-login", {
		"user.email" : $("#userEmail").val(),
		"user.password" : $("#userPassword").val()
	}, function(data, status) {
		if (status == "success") {
			var j=JSON.parse(data);
			var message=j.message;
			if(message){
				alert(message);
				return;
			}
			//$("div#facebox").remove();
			//$("#user_area").html(j.username);
			alert("登录成功！欢迎您 "+j.username);
			location.reload(true);
		}else{
			alert("服务器未响应");
		}
	});
}
//注销
function logout(){
	 $.get("web/User-logout", function(result){
	    //$("#user_area").html("未登录");
	    location.reload(true);
	    alert("注销成功！");
	  });
}
//注册
function regist() {
//		$.ajaxFileUpload({
//			url : 'web/User-add',
//			secureuri : false,
//			fileElementId : [ 'photo' ],
//			dataType : 'json',
//			success : function(data, status) {
//				alert(data.message);
//			},
//			error : function(data, status, e) {
//				alert(e);
//			},
//			data : {
//				"user.username" : $("#user_username").val(),
//				"user.email" : $("#user_email").val(),
//				"user.password" : $("#user_password").val(),
//				"user.gender" : $("#registerGender").val(),
//			}
//		});
	$.post("web/User-add",{
			"user.username" : $("#user_username").val(),
			"user.email" : $("#user_email").val(),
			"user.password" : $("#user_password").val(),
			"user.gender" : $("#registerGender").val(),
	},function(result,status){
		if (status == "success") {
			var j=JSON.parse(result);
			var message=j.message;
			alert(message);
			if(message=="注册成功"){
				location.reload(true);
			}
		}else{
			alert("服务器未响应");
		}
	});
}


// 初始化地图并定位
function initMap() {
	map = new BMap.Map("allmap");
	map.centerAndZoom("福州", 13);
	map.addControl(new BMap.NavigationControl()); //添加默认缩放平移控件
	map.addControl(new BMap.ScaleControl()); // 添加默认比例尺控件
	map.addControl(new BMap.OverviewMapControl());//添加默认缩略地图控件
	map.enableScrollWheelZoom();//允许鼠标滚轮放大缩小地图
	getCurrentCity();
}

//定位当前城市
function getCurrentCity(){
	var myCity = new BMap.LocalCity();
	myCity.get(function(result) {
		var cityName = result.name;
		map.setCenter(cityName);
		document.getElementById("currentCity").innerHTML=cityName;
		getLocation();
	});
}

//定位
function getLocation() {
	$("#myloc").html("<img src='resources/images/loading.gif'/>定位中...");
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r) {
		if (this.getStatus() == BMAP_STATUS_SUCCESS) {
			mylocation=r.point;
			refrushLoc();
		} else {
			$("myloc").html("定位失败 :(");
		}
	}, {
		enableHighAccuracy : true
	});
}
//显示更新位置
function refrushLoc(){
	map.removeOverlay(mylocationMarker);
	mylocationMarker=new BMap.Marker(mylocation);
	map.addOverlay(mylocationMarker);
	map.panTo(mylocation);
	document.getElementById("myloc").innerHTML="经度："+mylocation.lng+"，纬度："+mylocation.lat;
}
//添加单击获取位置的监听器
function addListenerOfGetLoc(){
	document.getElementById("myloc").innerHTML="单击地图上的点来设置位置";
	map.addEventListener("click", function(e){
		mylocation=e.point;
		refrushLoc();
		map.removeEventListener("click",null);
	});
}

//删除声音
function deleteVoice(obj){
	if(confirm("确定删除该声音？")){
		//删除
		$.get("web/Voice-delete?voice.id="+obj.id, function(result,status){
			if(status=="success"){
				var date=JSON.parse(result);
				alert(date.message);
				location.reload(true);
			}else{
				alert("服务器未响应");
			}
		});
	}
}

//ajax 加载所有声音
function addVoiceMarkers() {
	$.post("web/Voice-getAll", {
		name : "Donald Duck",
		city : "Duckburg"
	}, function(data, status) {
		if (status == "success") {
			var markers=[];
			voices = JSON.parse(data);
			for(var i=0;i<voices.length;i++){
//				map.addOverlay(voice2Marker(voices[i]));
				markers.push(voice2Marker(voices[i]));
			}
			markerClusterer = new BMapLib.MarkerClusterer(map, {
				markers:markers,
				gridSize:40,			//聚合计算时网格的像素大小
				isAverangeCenter:true 	//聚集点在内点的平均值
			});
		}
	});
}
//将声音转为marker
function voice2Marker(voice){
	var marker = new BMap.Marker(new BMap.Point(voice.longitude,voice.latitude)); 
	marker.voice=voice;
	var sContent="<table><tbody>"
		+"<tr><td>标题：</td><td>"+voice.title+"</td></tr>"
		+"<tr><td>上传者：</td><td>"+voice.userjo.username+"</td></tr>"
		+"<tr><td>标签：</td><td>"+voice.tag+"</td></tr>"
		+"<tr><td>描述：</td><td>"+voice.description+"</td></tr>"
		+"<tr><td>日期：</td><td>"+voice.date+"</td></tr>";
	if(voice.picture_url){
		var url=voice.picture_url;
		sContent+="<tr><td>图片：</td><td><a rel='modal' title='查看图片' href='file/image?imagePath="+url+"' rel='modal'>"+url.substr(url.lastIndexOf("_")+1,url.length)+"</a></td></tr>";
	}
	var u=voice.audio_url;
//	sContent+="<tr><td>声音：</td><td><a rel='modal' title='播放声音' href='file/audio?audioPath="+u+"' rel='modal'>"+u.substr(u.lastIndexOf("_")+1,u.length)+"</a></td></tr>";
	sContent+="<tr><td colspan='2'><audio controls='controls'  autoplay='autoplay'>"
		+"<source src='file/audio?audioPath="+u+"' type='audio/"+u.substr(u.lastIndexOf(".")+1,u.length)+"'>"
		+"<embed src='file/audio?audioPath="+u+"'></embed>"
		+"</audio></td></tr>";
	sContent+="</tbody></table>";
	marker.infoWindow=new BMap.InfoWindow(sContent);
	marker.infoWindow.disableCloseOnClick();
	marker.infoWindow.disableAutoPan();
	marker.addEventListener("click", function(){
		this.openInfoWindow(this.infoWindow);
//		document.getElementById('infoWindowImg').onload = function (){
//		       infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
//		};
		$('a[rel*=modal]').facebox();
	});
	return marker;
}

//获取用户声音
function getMyVoices(){
	$.get("web/Voice-getMyVoice",function(result){
		data=JSON.parse(result);
		var message=data.message;
		if(message){
			alert(message);
		}else{
			myvoices= data;
			addMyVoices2page();
		}
	});
}
//将用户的声音添加到网页中
function addMyVoices2page(){
	if(myvoices){
		var vhtml="<table>"
		+"<thead><tr>"
		+"<th><input type='checkbox' class='check-all'>全选</th>"
		+"<th>标题</th>"
		+"<th>标签</th>"
		+"</tr></thead><tbody>";
		for(var i=0;i<myvoices.length;i++){
			vhtml+="<tr>"
			+"<td><input type='checkbox' class='checkVoice' id='"+myvoices[i].id+"'></td>"
			+"<td>"+myvoices[i].title+"</td>"
			+"<td>"+myvoices[i].tag+"</td>"
			+"</tr>";
		}
		vhtml+="</tbody></table>";
//		vhtml+="<input id='addvoices_btn' type='button' class='button' value='添加到专辑'>";
		$("#myvoices").html(vhtml);
		$('tbody tr:even').addClass("alt-row"); // Add class "alt-row" to even table rows
		$('.check-all').click(
				function(){
					$(this).parent().parent().parent().parent().find("input[type='checkbox']").attr('checked', $(this).is(':checked'));   
				}
			);
		$("input[type='checkbox']").click(function(){
			var count=0;
			$("input.checkVoice").each(function(){
				if($(this).attr('checked'))
					count++;
			});
			$('#voices_count').html(count);
		});
	}
}

//获取用户专辑
function getMyAlbums(){
	$.get("web/Album-getMyAlbum",function(result){
		data=JSON.parse(result);
		var message=data.message;
		if(message){
			alert(message);
		}else{
			myalbums= data;
			addMyAlbums2page();
		}
	});
}
//将专辑列表显示
function addMyAlbums2page(){
	if(myalbums){
		var vhtml="<table>"
		+"<thead><tr>"
		+"<th>名称</th>"
		+"<th>描述</th>"
		+"<th>声音数</th>"
		+"<th>创建日期</th>"
		+"</tr></thead><tbody>";
		for(var i=0;i<myalbums.length;i++){
			vhtml+="<tr>"
			+"<td><a class='album' href='#' id='"+myalbums[i].id+"'>"+myalbums[i].name+"</a></td>"
			+"<td>"+myalbums[i].description+"</td>"
			+"<td>"+myalbums[i].voices_count+"</td>"
			+"<td>"+myalbums[i].date+"</td>"
			+"</tr>";
		}
		vhtml+="</tbody></table>";
		$("#myalbums").html(vhtml);
		$('tbody tr:even').addClass("alt-row"); // Add class "alt-row" to even table rows
		$("a.album").click(function(){
			//获取点击的专辑的id
			showAlbum($(this).attr("id"));
		});
	}
}
//根据id在地图上显示专辑
function showAlbum(album_id){
	$.get("web/Album-getAlbumVoice?album.id="+album_id,function(result){
		data=JSON.parse(result);
		var message=data.message;
		if(message){
			alert(message);
		}else{
			var albumvoices= data;
			albumVoiceMarkers=[];
			for(var i=0;i<albumvoices.length;i++){
				albumVoiceMarkers.push(voice2Marker(albumvoices[i]));
			}
			//清除所有覆盖物
			map.clearOverlays();
			//将声音添加到地图上，并画出弧线
			var points=[];
			for(var i=0;i<albumVoiceMarkers.length;i++){
				map.addOverlay(albumVoiceMarkers[i]);
				points.push(new BMap.Point(albumVoiceMarkers[i].voice.longitude,albumVoiceMarkers[i].voice.latitude));
			}
			var curve = new BMapLib.CurveLine(points, {strokeColor:"blue", strokeWeight:3, strokeOpacity:0.5}); //新建弧线覆盖物对象
			map.setViewport(points);//设置地图视野
			map.addOverlay(curve); //添加到地图
		}
	});
}
