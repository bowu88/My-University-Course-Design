	var map;
	var zoom = 12; 
	var currentMarker;
	var asylums;
	var webpath;
	var isRangeShow;//标记服务范围是否有显示
	var rangs=[];
	var lineTool,polygonTool;//测距工具 测面工具
	
	var drivingRoute;	//驾车路线规划对象 
	var obj;			//搜索结果 
	var startLngLat;	//起点经纬度 
	var endLngLat;		//终点经纬度 
	var startTool;		//起点标注工具 
	var startMarker;	//起点标记
	var endTool;		//终点标注工具 
	var endMarker;		//终点标记
	var startIcon;	//起点图标 
	var endIcon;		//终点图标 
	var infoWin = null; 
	var line;		//路线
	
	//载入地图
	function onLoad(path) 
	{ 
		webpath=path;
		
		//初始化地图
		initMap();
		
		//初始化路线规划工具
		loadDrivingTool();
		
		//获取所有避难所
		getAllAsynums();
	}
	
	//初始化地图
	function initMap(){
		//初始化地图对象 
	   	map=new TMap("mapDiv"); 
	   	//设置显示地图的中心点和级别 
		resetCenter();
		//允许鼠标滚轮缩放地图 
		map.enableHandleMouseScroll(); 
		
		var config = { 
			type:"TMAP_NAVIGATION_CONTROL_LARGE",	//缩放平移的显示类型 
			anchor:"TMAP_ANCHOR_TOP_LEFT",			//缩放平移控件显示的位置 
			offset:[0,0],							//缩放平移控件的偏移值 
			showZoomInfo:true						//是否显示级别提示信息，true表示显示，false表示隐藏。 
		}; 
		//创建缩放平移控件对象 
		control=new TNavigationControl(config); 
		//添加缩放平移控件 
		map.addControl(control); 
		
		//创建比例尺控件对象
		var scale = new TScaleControl();
		//添加比例尺控件
		map.addControl(scale);
		
		var config = { 
			anchor: "TMAP_ANCHOR_BOTTOM_RIGHT",	//设置鹰眼位置
			size  : new TSize(180,120),			//鹰眼显示的大小 
			isOpen : true						//鹰眼是否打开，true表示打开，false表示关闭，默认为关闭 
		}; 
		//创建鹰眼控件对象 
		overviewMap = new TOverviewMapControl(config); 
		//添加鹰眼控件 
        map.addControl(overviewMap); 
		
		//创建地图类型控件对象 
		var control = new TMapTypeControl(); 
		//将地图类型控件添加到地图上 
		map.addControl(control); 
	}
	
	//从服务器获取所有避难所数据（json格式)
	function getAllAsynums(callback){
		//从服务器获取应急避难场所的数据
		$.get(webpath+"/servlet/Asylum2JSONServlet",function(data,status){
			if(status=="success"){
				dealJson(data);
			}
		});
	}
	
	//处理json
	function dealJson(json){
		obj = JSON.parse(json);
		//清除所有覆盖物
		map.clearOverLays();
		isRangeShow=false;
		asylums=obj.asylums;
		for(var i=0;i<obj.asylums.length;i++){
			//获取应急避难场所的坐标
			var lnglat=new TLngLat(asylums[i].x,asylums[i].y); 
			//获取名称
			var name=asylums[i].name;
			//创建图片对象 
	        var icon = new TIcon(webpath+"/img/blueMark.png",new TSize(32,32),{anchor:new TPixel(16,32)}); 
	        //向地图上添加自定义标注 
			var marker = new TMarker(lnglat,{icon:icon});
			marker.setTitle(asylums[i].name);
			marker.id=asylums[i].id;
			marker.index=i;
			//注册标注点击事件（点击标注后打开信息窗口）
			TEvent.addListener(marker,"click",function(p){
				if(currentMarker!=null){
					//关闭之前打开的信息窗口
					currentMarker.closeInfoWindow();
				}
				this.setInfoWinWidth(180);
				//获取避难场所对象
				var a=obj.asylums[this.index];
				//构建信息窗口的内容
				var html='';
				html=html+'<h4>'+a.name+'</h4><hr>';
				html=html+'<table class="no-style full" width="100%"><tbody>';
				html=html+'<tr><td><b>地址：</b></td><td class="ta-right">'+a.address+'</td></tr>';
				html=html+'<tr><td><b>类别：</b></td><td class="ta-right"><a target="blank" href="'+webpath+'/static/biaozhunjieshao.jsp">'+a.classid+'</a></td></tr>';
				html=html+'<tr><td><b>棚宿区面积：</b></td><td class="ta-right">'+a.area+'万平方米</td></tr>';
				html=html+'<tr><td><b>容纳人数：</b></td><td class="ta-right">'+a.people+'</td></tr>';
				html=html+'</tbody></table>';
				this.openInfoWinHtml(html);
				currentMarker=this;
			});
	        map.addOverLay(marker);
        }
	}
	//移除所有服务半径
	function removeRange(){
		for(var i=0;i<rangs.length;i++){
			map.removeOverLay(rangs[i]);
		}
		rangs=[];
	}
	//避难场所服务范围查询
	function queryRange(){
		var aclass=$("#aclass").val();
		var apeople=$("#apeople").val();
		if(asylums==null){
			return;
		}else if(isRangeShow){
			removeRange();
		}
		var config = { 
			strokeColor:"#43ACDA",	//圆边线颜色 
			fillColor:"#71DCF7",	//填充颜色。 
			strokeWeight:"3px",	//圆边线线的宽度，以像素为单位 
			strokeOpacity:0.3,	//圆边线线的透明度，取值范围0 - 1 
			fillOpacity:0.3,			//填充的透明度，取值范围0 - 1 
			strokeStyle:"solid"	//圆边线线的样式，solid或dashed 
		}; 
		for(var i=0;i<asylums.length;i++){
			var asy=asylums[i];
			//计算服务半径
			var r;
			
			//根据场所类型计算基本服务半径
			if(asy.classid=="Ⅲ类"){
				r=1000;
			}else if(asy.classid=="Ⅱ类"){
				r=1500;
			}else if(asy.classid=="Ⅰ类"){
				r=2000;
			}
			//根据容纳人数调整半径
			//if(asy.people>1000){
				r=r*(1+aclass*10)/10+Math.sqrt(asy.people)*5*10*(apeople*10)/10;
			//}
			// 定义显示区域 
			var circle = new TCircle(new TLngLat(asy.x,asy.y),r,config); 
			//向地图上添加圆 
			map.addOverLay(circle);
			rangs.push(circle);
		}
		isRangeShow=true;
	}
	
	//初始化地图的中心点和级别 
	function resetCenter(){
		map.centerAndZoom(new TLngLat(119.01449,25.458759),zoom);
	}
	
	//打开测距工具
	function openLineTool(){
		if(lineTool==null){
			var config = { 
				strokeColor:"blue",	//折线颜色 
				strokeWeight:"3px",	//折线的宽度，以像素为单位 
				strokeOpacity:0.5,	//折线的透明度，取值范围0 - 1 
				strokeStyle:"solid"	//折线的样式，solid或dashed 
			}; 
			//创建测距工具对象 
			lineTool = new TPolylineTool(map,config); 
			//注册测距工具绘制完成后的事件 
			TEvent.addListener(lineTool,"draw",function(bounds,line,obj){ 
				lineTool.close(); 
			});
		}
		lineTool.open();
	}
	
	//打开测面工具
	function openPolygonTool(){
		if(polygonTool==null){
			var config = { 
				strokeColor:"blue",	//折线颜色 
				fillColor:"#FFFFFF",	//填充颜色。当参数为空时，折线覆盖物将没有填充效果 
				strokeWeight:"3px",	//折线的宽度，以像素为单位 
				strokeOpacity:0.5,	//折线的透明度，取值范围0 - 1 
				fillOpacity:0.5			//填充的透明度，取值范围0 - 1 
			}; 
			//创建测面工具对象 
			polygonTool = new TPolygonTool(map,config); 
			//注册测面工具绘制完成后的事件 
			TEvent.addListener(polygonTool,"draw",function(bounds,line){     
				polygonTool.close(); 
			});
		}
		polygonTool.open();
	}
	
	//初始化路线规划工具
	function loadDrivingTool(){
		startIcon=webpath+"/img/start.png";
		endIcon=webpath+"/img/end.png";
		
		//鼠标标起点 
		var config = { 
			icon : new TIcon(startIcon,new TSize(37,62),{anchor:new TPixel(20,62)}) 
		}; 
   		startTool = new TMarkTool(map,config); 
   		TEvent.addListener(startTool,"mouseup",mouseUpStartMaker); 
   		 
   		//鼠标标终点 
   		var config = { 
			icon : new TIcon(endIcon,new TSize(37,62),{anchor:new TPixel(20,62)}) 
		}; 
   		endTool = new TMarkTool(map,config); 
   		TEvent.addListener(endTool,"mouseup",mouseUpEndMaker); 
		 
		var config = { 
			policy:0,	//驾车策略 
			onSearchComplete:searchResult	//检索完成后的回调函数 
		}; 
		 
		//创建公交搜索对象 
		drivingRoute = new TDrivingRoute(map,config); 
	}
	
	//公交搜索 
	function searchDrivingRoute() 
	{ 
		//清空显示列表 
		document.getElementById("resultDiv").innerHTML = ""; 
		if(line!=null){
			map.removeOverLay(line);
		 }
		 
		//起点经纬度 
		var startVal = document.getElementById("start").value.split(","); 
		startLngLat = new TLngLat(startVal[0],startVal[1]); 
		//终点经纬度 
		var endVal = document.getElementById("end").value.split(","); 
		endLngLat = new TLngLat(endVal[0],endVal[1]); 
		 
		//设置驾车策略 
		drivingRoute.setPolicy(getRadioValue()); 
		//驾车路线搜索 
		drivingRoute.search(startLngLat,endLngLat); 
	} 
	 
	//显示公交搜索结果 
	function searchResult(result) 
	{ 
		//添加起始点 
		//createStartMarker(); 
		 
		obj = result; 
		var resultList = document.createElement("div"); 
		//获取方案个数  
		var routes = result.getNumPlans(); 
		for(var i=0;i<routes;i++) 
		{ 
			//收起路线规划查询面板
			var queryPanelSection=document.getElementById("queryPanelSection");
			queryPanelSection.style.display="none";
			//获得单条驾车方案结果对象 
			var plan = result.getPlan(i); 
			 
			//显示单个方案面板 
			var div = document.createElement("div"); 
			div.style.cssText = "font-size:12px; cursor:pointer; border:1px solid #999999"; 
			 
			//显示方案内容 
			var time=plan.getDuration();
			var sec=time%60;
			var min=(time-sec)/60;
			var describeStr = "<strong>总时间："+min+"分钟"+sec+"秒，总距离："+Math.round(plan.getDistance())+"公里</strong>"; 
			describeStr += "<div><img src='"+startIcon+"'/>"+document.getElementById("start").value+"</div>"; 
			 
			//显示该方案每段的描述信息 
			var numRoutes = plan.getNumRoutes(); 
			for(var m=0;m<numRoutes;m++) 
			{ 
				var route = plan.getRoute(m); 
				describeStr += (m+1)+".<span>"+route.getDescription()+"</span><br/>" 
				 
				//显示该方案每段的详细描述信息 
				var numStepsStr = ""; 
				var numSteps = route.getNumSteps(); 
				for(var n=0;n<numSteps;n++) 
				{ 
					var step = route.getStep(n); 
					numStepsStr += "<p>"+(n+1)+")<a href='javascript://' onclick='showPosition(\""+step.getPosition().getLng()+"\",\""+step.getPosition().getLat()+"\",\""+step.getDescription()+"\");'>"+step.getDescription()+"</a></p>"; 
				} 
				describeStr += numStepsStr; 
			} 
			describeStr += "<div><img src='"+endIcon+"'/>"+document.getElementById("end").value+"</div>"; 
			div.innerHTML = describeStr; 
			resultList.appendChild(div); 
			 
			//显示驾车线路 
			createRoute(plan.getPath()); 
			//显示最佳级别 
			map.setViewport(plan.getPath()); 
		} 
		//显示公交搜索结果 
		document.getElementById("resultDiv").appendChild(resultList); 
	} 
	 
	//公交线路，pointsStr表示经纬度字符串，type表示线路类型1，步行；2，公交；3，地铁；4， 地铁站内换乘，lnglat表示显示公交或地铁图标的经纬度 
	function createRoute(lnglats,lineColor) 
	{ 
		//创建线对象 
	    line = new TPolyline(lnglats,{strokeColor:"#2C64A7", strokeWeight:5, strokeOpacity:0.9}); 
	    //向地图上添加线 
	    map.addOverLay(line); 
	} 
	 
	//定位关键点 
	function showPosition(lng,lat,des) 
	{ 
		if(infoWin) 
		{ 
			map.removeOverLay(infoWin); 
			infoWin = null; 
		} 
		var lnglat = new TLngLat(lng,lat); 
		infoWin=new TInfoWindow(lnglat,new TPixel([0,0])); 
		infoWin.setLabel(des); 
		map.addOverLay(infoWin); 
		//打开信息窗口时地图自动平移 
		infoWin.enableAutoPan(); 
	} 
	 
	function mouseUpStartMaker(point) 
	{ 
		//向地图上添加起点 
        var icon = new TIcon(startIcon,new TSize(37,62),{anchor:new TPixel(20,62)});
		startMarker = new TMarker(point,{icon:icon}); 
        //关闭标点工具 
        startTool.close(); 
        map.addOverLay(startMarker); 
        //设置公交搜索起点 
        var lng=point.getLng();
        var lat=point.getLat();
        document.getElementById("start").value = lng+","+lat;
        
        //将最近的应急避难场所设为终点
       var mindistance=-1;
       var minDictPoint;
       for(var i=0;i<asylums.length;i++){
        	//distance=new TLngLat(asylums[i].x,asylums[i].y).distanceFrom({other:point});
        	distance=Math.sqrt((asylums[i].x-lng)*(asylums[i].x-lng)+(asylums[i].y-lat)*(asylums[i].y-lat));
       		if(mindistance==-1||mindistance>distance){
       			mindistance=distance;
       			minDictPoint=new TLngLat(asylums[i].x,asylums[i].y);
       		}
        }
        mouseUpEndMaker(minDictPoint);
	} 
	 
	function mouseUpEndMaker(point) 
	{ 
		if(endMarker!=null) map.removeOverLay(endMarker);
		//向地图上添加起点 
        var icon = new TIcon(endIcon,new TSize(37,62),{anchor:new TPixel(20,62)});
		endMarker = new TMarker(point,{icon:icon}); 
        map.addOverLay(endMarker); 
        //关闭标点工具 
        endTool.close(); 
        //设置公交搜索终点 
        document.getElementById("end").value = point.getLng()+","+point.getLat(); 
	} 
	 
	//获得驾车路线策略 
	function getRadioValue() 
	{ 
		var planType = document.getElementsByName("planType"); 
		for(var i=0;i<planType.length;i++) 
		{ 
			if(planType[i].checked) 
			{ 
				return planType[i].value; 
			} 
		} 
	} 
	
	//开启标注起点工具
	function openStartTool(){
		
		if(startMarker!=null){ 
			map.removeOverLay(startMarker);
		}
		startTool.open();
	}
	
	//开启标注终点工具
	function openEndTool(){
		
		if(endMarker!=null){ 
			map.removeOverLay(endMarker);
		}
		endTool.open();
	}
	