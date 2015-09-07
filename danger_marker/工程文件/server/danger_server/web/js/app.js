var map;
var dangers=[];
var city="厦门";
var markerIcon;
var categorys={"自然灾害":["泥石流","山体滑坡","山体落石","悬崖","天坑"],
                "户外设施":["外露电缆","管道破裂","高空坠物","没有井盖的深井"],
                "行车安全":["事故多发区","急转弯","道路施工","道路塌陷","道路异物","道路积水"],
                "意外伤害":["野兽出没","抢劫多发区"]};
var firstPoint=null;
var polygon;
var listener;
var tagLib=["地球太危险了","内有恶犬","树～倒～了～","致命弯道","电锯惊魂","熊出没","熊孩子出没","没有路灯","城管来了","道路不平","五连发夹弯",
            "大水沟","没有护栏","井盖去哪儿了","没有红绿灯","过水路面","易积水","扒手出没","骗子出没","车辆多","高压危险","有狗","有便便","千万别回头",
            "小心掉水里","小心掉沟里","小心火烛","永远不要相信这些标签","这些标签都是随机生成的","duang～","山穷水尽","柳暗花明又一村..的上一句","不知道加什么标签好",
            "风水不好","柯南来这里了","可以不加标签吗","一定要加标签吗","一针根治","什么鬼","慎入","小心头","watch out","WTF!","shit!"];

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
    //var mh=$('#map_head').height();
//	alert(mh);
//    if(mh>0){
//        a+=mh+20;
//    }
    $('#map').height(h - a);
}

$(document).ready(function() {
    changeMapSize();
    $(window).resize(function() {
        changeMapSize();
    });
});

//初始化地图
function initMap(){
    map = new BMap.Map("map");
    map.centerAndZoom(city, 11);  // 初始化地图,设置中心点坐标和地图级别
    map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
    map.addControl(new BMap.NavigationControl());
    map.addControl( new BMap.OverviewMapControl());
    map.addControl( new BMap.ScaleControl());
    map.setCurrentCity(city);          // 设置地图显示的城市 此项是必须设置的
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    markerIcon=new BMap.Icon("img/marker.png",new BMap.Size(23,30),{anchor:new BMap.Size(11,30),infoWindowAnchor:new BMap.Size(11,0)});
}

//从服务端获取数据
function getDangers(callback){
    $.ajax({
        url:"server/Danger-getAll",
        dataType:"json",
        success:function(data){
            dangers=data;
            if(callback)callback(data);
        }
    });
}

//添加Marker
function addMarkers(dangers){
    for(var i=0;i<dangers.length;i++){
        addMarker(dangers[i]);
    }
}

function addMarker(danger){
    //{"id":34,"category":"行车安全","subCategory":"事故多发区","rank":0.0,"pictures":"34_1_c2c23cc8e369362dcb0665b64beec2b9.jpg 34_2_f00fbf4f.jpg 34_3_c8415983d66126e45c2d3abfd343f7d0.jpg 34_4_app80977599.jpg","longitude":119.212569,"latitude":26.041075,"accuracy":87.7176,"province":"福建省","city":"福州市","district":"闽侯县","street":"广贤路","date":"Jan 19, 2015 11:31:08 PM","solved":false,"helped":0,"numSolved":0},{"id":36,"name":"事故多发","category":"行车安全","subCategory":"事故多发区","tags":"交通","rank":3.5,"description":"测试信息","pictures":"36_1_f00fbf4f.jpg","longitude":118.172691,"latitude":24.490381,"accuracy":66.3232,"province":"福建省","city":"厦门市","district":"思明区","street":"吕岭路","date":"Mar 14, 2015 4:52:52 PM","solved":false,"helped":0,"numSolved":0}]
    var marker=new BMap.Marker(new BMap.Point(danger.longitude, danger.latitude),{icon:markerIcon});
    var h="<table class='table table-striped'>" +
        "<tr><td>标题：</td><td>"+danger.name+"</td></tr>" +
        "<tr><td>类别：</td><td>"+danger.category+"-"+danger.subCategory+"</td></tr>" +
        "<tr><td>标签：</td><td>"+createTags(danger.tags)+"</td></tr>" +
        "<tr><td>危险等级：</td><td>"+createStars(danger.rank)+"</td></tr>" +
        "<tr><td>地址：</td><td>"+danger.province+danger.city+danger.district+danger.street+"</td></tr>" +
        "<tr><td>表述：</td><td>"+danger.description+"</td></tr>" +
        "</table>"
    var infoWindow = new BMap.InfoWindow(h);  // 创建信息窗口对象
    marker.addEventListener("click", function(){
        this.openInfoWindow(infoWindow); //开启信息窗口
    });
    map.addOverlay(marker);
}

//生成星星
function createStars(rank){
    var intRank=Math.floor(rank);
    var star="<span class='glyphicon glyphicon-star'></span>";
    var emptyStar="<span class='glyphicon glyphicon-star-empty'></span>";
    var result="";
    for(var i=0;i<intRank;i++){
        result+=star;
    }
    for(var i=0;i<5-intRank;i++){
        result+=emptyStar;
    }
    result+=rank;
    return result;
}
//生成标签
function createTags(tags){
    if(tags) {
        var arr = tags.split(" ");
        var result = "";
        for (var i = 0; i < arr.length; i++) {
            result += "<span class='label label-warning'>" + arr[i] + "</span>"
        }
        return result;
    }
}

//地图点击监听事件
function onMapClick(e){
    if(firstPoint==null){
        firstPoint= e.point;
        polygon=new BMap.Polygon(createRecPoints(firstPoint, e.point));
        map.addOverlay(polygon);
        listener=function(e){
            polygon.setPath(createRecPoints(firstPoint, e.point));
        }
        map.addEventListener("mousemove",listener);
    }else{
        var count=$("#count").val();
        if(!count>0)
            count=5;
        map.removeOverlay(polygon);
        map.removeEventListener("mousemove",listener);
        arr=createRandomPoint(firstPoint, e.point,count);
        createDangers(arr);
        firstPoint=null;
    }
}

//生成长方形的四个顶点
function createRecPoints(p1,p2){
    return [p1,new BMap.Point(p1.lng,p2.lat),p2,new BMap.Point(p2.lng,p1.lat)];
}
/**
 * 生成随机点
 * @param p1 p2 对角点
 * @param count 个数
 */
function createRandomPoint(p1,p2,count){
    var arr=[];
    for(var i= 0;i<count;i++){
        var p= new BMap.Point(createRandomNum(p1.lng,p2.lng),createRandomNum(p1.lat,p2.lat));
        arr.push(p);
    }
    return arr;
}
function createRandomNum(num1,num2){
    return num1+(num2-num1)*Math.random();
}

var geoc=new BMap.Geocoder();
/**
 * 生成随机的danger
 * @param points
 */
function createDangers(points){
    for(var i=0;i<points.length;i++){
        //地理编码
        geoc.getLocation(points[i],function(rs){
            var addComp=rs.addressComponents;
            var danger=createOneDanger(rs.point);
            danger.province=addComp.province;
            danger.district=addComp.district;
            danger.city=addComp.city;
            danger.street=addComp.street;
            uploadDanger(danger);
            addMarker(danger);
        });
    }
}
/**实体类*/
var Danger= function(){
    var danger=new Object();
    danger.name="";
    danger.category="";    //一级类别
    danger.subCategory=""; //二级类别
   danger.tags="";
    danger.rank=4;         //等级
   danger.description="批量生产的数据";  //描述
   danger.pictures="";
   danger.remark="随机生成";      //备注
    //位置
    danger.longitude=0.0;   //经度
    danger.latitude=0.0;    //纬度
    danger.accuracy=0.0;;    //精确度
   danger.province="";    //省
   danger.city="";        //市
   danger.district="";    //县
   danger.street="";      //街道
   danger.addressDescritpion="";//位置详细描述

    danger.data=new Date();          //上传时间
    danger.solved=false;;     //是否已解决
    danger.helped=0;     //多少人认为有帮助
    danger.numSolved=0;  //多少人认为问题已解决
    return danger;
}
var keys=["自然灾害", "户外设施", "行车安全",, "意外伤害"];
function createOneDanger(point){
    var d=new Danger();
    //生成类别
    var i=Math.floor(createRandomNum(0,4));
    d.category=keys[i];
    var j=Math.floor(createRandomNum(0,categorys[d.category].length));
    d.subCategory=categorys[d.category][j];
    //alert(d.category+" "+ d.subCategory);
    if(point){
        d.longitude=point.lng;
        d.latitude=point.lat;
    }
    //生成等级
    d.rank=createRandomNum(0,5).toFixed(1);
    //生成标题
    d.name=d.street+ d.subCategory;
    d.helped=Math.floor(createRandomNum(0,1024));
    d.solved=Math.floor(createRandomNum(0,255));
    //生成标签
    d.tags= d.category+" "+ d.subCategory+" "+tagLib[Math.floor(createRandomNum(0,tagLib.length))];
    return d;
}

function uploadDanger(danger){
        var data={};
        var s="danger."
        for(var key in danger){
            data[s+key]=danger[key];
        }
        $.ajax({
            url:"server/Danger-add",
            method:"post",
            data:data,
            success:function(data){
                console.log("上传数据：id="+data);
            }
        });
}