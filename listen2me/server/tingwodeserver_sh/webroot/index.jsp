<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
	request.setAttribute("currentTop", "index");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>听我的 - 首页</title>
    
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="听我的,声音地图,GIS">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="/WEB-INF/public/css_script.jsp"  %>
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		initMap();
		addVoiceMarkers();
	});
	</script>
  </head>
  
  <body>
  <div id="body-wrapper">
  	<%@ include file="/WEB-INF/public/sidebar.jsp" %>
  	<div id="main-content">
    	<div class="content-box">
				<div class="content-box-header">
					<h3>所有声音</h3>
				</div>
				<div class="content-box-content " style=" padding:0;">
					<div class="tab-content default-tab" style="display: block;">
						<div style="width: 100%;height: 70%;overflow: hidden;margin:0;"
							id="allmap"></div>
					</div>
				</div>
			</div>
    		<%@ include file="/WEB-INF/public/footer.jsp" %>
    	</div>
    </div>
  </body>
</html>
