<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setAttribute("currentTop", "usercenter");
	request.setAttribute("curenntSub", "myalbum");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>听我的 - 个人中心 - 我的专辑</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="/WEB-INF/public/css_script.jsp"%>
	<script type="text/javascript">
	$(document).ready(function(){
		initMap();
		getMyAlbums();
	});
	</script>
  </head>
  <body>
     <div id="body-wrapper">
		<%@ include file="/WEB-INF/public/sidebar.jsp"%>
		<div id="main-content">
		<div class="content-box column-left">
			<div class="content-box-header">
				<h3>我的专辑</h3>
			</div>
			<div class="content-box-content ">
				<div class="tab-content default-tab" style="display: block;">
					<div class="tab-content default-tab" style="display: block;">
						<div id="myalbums">暂无专辑</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 地图窗口 -->
		<div class="content-box column-right">
			<div class="content-box-header">
				<h3>地图</h3>
			</div>
			<div class="content-box-content " style=" padding:0;">
				<div class="tab-content default-tab" style="display: block;">
					<div style="width: 100%;height: 70%;overflow: hidden;margin:0;" id="allmap"></div>
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<%@ include file="/WEB-INF/public/footer.jsp"%>
		</div>
	</div>
  </body>
</html>
