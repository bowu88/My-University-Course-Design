<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% request.setAttribute("currentPage", "index");  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>fjnuGIS-莆田市地震应急信息服务平台</title>
    <%@ include file="/public/basic_link_script.jsp"%>
    <!-- 
    <script language="javascript" src="http://api.tianditu.com/js/maps.js"></script> -->
    <script type="text/javascript" src="http://api.tianditu.com/api-new/api/js/maps.js"></script>
	<script language="javascript" src="js/map.js"></script> 
	<script type="text/javascript">
	
	$(document).ready(function(){
		
		Administry.setup();
		
		$("#tabs, #tabs2").tabs();
		
		$("#aclass").change(function(){
			var i=(10-$("#aclass").val()*10)/10;
			$("#apeople").val(i);
		});
		$("#apeople").change(function(){
			var i=(10-$("#apeople").val()*10)/10;
			$("#aclass").val(i);
		});
		
	});
	</script>
  </head>
  <body onLoad="onLoad('${pageContext.request.contextPath}')">
   <%@ include file="/public/page_header.jsp"%>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>FJNUGIS &rarr; <span>莆田市地震应急信息服务平台</span></h1>
		</div>
		
	</div>
	<div id="page">
		<!-- 地图控件占位符 -->
		<div id="mapDiv" style="width:100%; height:80%"></div>
		
		<div id="toolpaddiv">
			<table class="no-style full">
				<tbody>
					<tr>
						<td><a href="#" class="btn" onclick="resetCenter()">位置重置</a></td>
						<td class="ta-right"><a class="btn" onclick="onLoad('${pageContext.request.contextPath}')">重新载入</a></td>
						<td class="ta-right"><a class="btn btn-red" onclick="map.clearOverLays()">清空地图</a></td>
					</tr>
				</tbody>
			</table>
			
			<div id="tabs">
				<ul>
					<li><a class="corner-tl" href="#tabs-1">基本功能</a></li>
					<li><a class="" href="#tabs-2">空间量算</a></li>
					<li><a class="corner-tr" href="#tabs-3">路线规划</a></li>
				</ul>
				<div id="tabs-1">
					<h4>应急避难场所 服务范围分析</h4>
					<hr>
					<p>根据应急避难场所的类别、可容纳人数，通过缓冲区分析，生成应急避难场所的服务范围。</p>
					<table class="stylized full" style="width:80%;margin:auto;">
					<thead>
						<tr><th>项目</th><th>权重</th></tr>
					</thead>
						<tbody>
							<tr class="high">
								<td><b>类别</b></td>
								<td><select id="aclass">
									<option>0.1</option>
									<option>0.2</option>
									<option>0.3</option>
									<option>0.4</option>
									<option selected="selected">0.5</option>
									<option>0.6</option>
									<option>0.7</option>
									<option>0.8</option>
									<option>0.9</option>
								</select></td>
							</tr>
							<tr class="high">
								<td><b>可容纳人数</b></td>
								<td><select id="apeople">
									<option>0.1</option>
									<option>0.2</option>
									<option>0.3</option>
									<option>0.4</option>
									<option selected="selected">0.5</option>
									<option>0.6</option>
									<option>0.7</option>
									<option>0.8</option>
									<option>0.9</option>
								</select></td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td style="text-align:center" colspan="2"><a class="btn btn-green" onclick="queryRange()">服务范围查询</a></td>
							</tr>
						</tfoot>
					</table>
					
				</div>
				<div id="tabs-2">
					<h4>空间量算</h4>
					<hr>
					<p>提供测距工具和测面工具，用于量算空间距离或面积。</p>
					<a href="#" class="btn btn-green" onclick="openLineTool()">测距工具</a>
					<a class="btn btn-green" onclick="openPolygonTool()">测面工具</a>
				</div>
				<div id="tabs-3">
					<!-- 查询面板 --> 
					<div id="queryPanel" class="content-box corners">
						<header style="cursor: s-resize;">
							<h3>路线规划</h3>
						</header>
						<section id="queryPanelSection" style="display:block;">
						<input type="radio" name="planType" value="0" checked="checked"/>最少时间 
						<input type="radio" name="planType" value="1"/>最短距离  
						<input type="radio" name="planType" value="2"/>避开高速 
						<input type="radio" name="planType" value="3"/>步行 
						<hr/> 
						该工具在添加起点后，会自动计算出离起点最近的应急避难场所，并自动将其设为终点，以便查询路线。
						
						<input type="hidden" id="start" value=""/> 
						<a href="#" class="btn" onClick="openStartTool()"><span class="icon icon-add">&nbsp;</span>添加起点</a> 
						<input type="hidden" id="end" value=""/> 
						<a href="#" class="btn" onClick="openEndTool()"><span class="icon icon-add">&nbsp;</span>手动添加终点</a> 
						<br/> 
						<br/> 
						<a href="#" class="btn" onClick="searchDrivingRoute()">路线搜索</a> 
						</section>
 					</div>
 					<br>
					<!-- 结果面板 --> 
					<div id="resultDiv" style="overflow-y:auto;height:200px"></div> 
				</div>
			</div> 
		</div>
	</div>
	<%@ include file="/public/page_footer.jsp"%>
  </body>
</html>