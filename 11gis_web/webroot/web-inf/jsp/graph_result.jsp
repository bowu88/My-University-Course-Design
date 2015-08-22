<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>频率统计图</title>
    <%@ include file="/public/basic_link_script.jsp"%>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/graph/graph.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/graph/lines.css">
<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/graph/d3.v3.js"></script>
<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/graph/rickshaw.js"></script>

<style>
#chart {
	position: relative;
	left: 40px;
	display: block;
}
#y_axis {
	position: absolute;
	top: 0;
	bottom: 0;
	width: 40px;
}
#x_axis {
	position: relative;
	left: 40px;
	height: 40px;
}
</style>
  </head>
  <body>
  <%-- Header --%>
	<%-- Header --%>
<header id="top">
	<div class="wrapper">
		<%-- Title/Logo - can use text instead of image --%>
		<div id="title"><img src="${pageContext.request.contextPath}/img/logo.png" alt="Administry" /></div>
		
		<%-- End of Aside links --%>
	</div>
</header>
<%-- End of Header --%>
	
	<%-- Page title --%>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>结果</h1>
		</div>
	</div>
	<%-- End of Page title --%>
	<%-- Page content --%>
	<div id="page" style="min-height:320px">
		<%-- Wrapper --%>
		<div class="wrapper">
			<h2>频率分布图</h2>
<div id="chart_container" style="position:relative;left:40px;display:block;">
	<div id="y_axis"></div>
	<div id="chart"></div>
	<div id="x_axis"></div>
</div>
<script>

// instantiate our graph!

var graph = new Rickshaw.Graph( {
	element: document.getElementById("chart"),
	renderer: 'bar',
	height: 300,
	width: 800,
	series: [
		{
			data:${data_arr_y},
			color: 'steelblue'
		}
	]
} );

var format = function(n) {

	var map=${data_arr_x};
	return map[n];
}

var x_ticks = new Rickshaw.Graph.Axis.X( {
	graph: graph,
	orientation: 'bottom',
	element: document.getElementById('x_axis'),
	pixelsPerTick: 800/${count},
	tickFormat: format
} );

var y_ticks = new Rickshaw.Graph.Axis.Y( {
	graph: graph,
	orientation: 'left',
	tickFormat: Rickshaw.Fixtures.Number.formatKMBT,
	element: document.getElementById('y_axis'),
} );

graph.render();

</script>
<p>
	平均数：${data.avg }<br>
	中位数：${data.mid}<br>
	标准差：${data.s }<br>
	变异系数：${data.cv }
</p>
		</div>
	</div>
	<%@ include file="/public/login_pagefooter.jsp"%>
  </body>
</html>



