<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% request.setAttribute("currentPage", "index");  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>fjnuGIS-首页</title>
    <%@ include file="/public/basic_link_script.jsp"%>
    <script type="text/javascript" SRC="${pageContext.request.contextPath}/js/swfobject.js"></script>
    <script type="text/javascript">
	$(document).ready(function(){
		
		/* setup navigation, content boxes, etc... */
		Administry.setup();
		Administry.videoSupport('video-flash', 'video/video.f4v', 320, 240);
		});
	</script>
  </head>
  <body>
   <%@ include file="/public/page_header.jsp"%>
   <%-- Page title --%>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>福建师范大学 地理信息系统 &rarr; <span>首页</span></h1>
		</div>
	</div>
	<%-- End of Page title --%>
	<%-- Page content --%>
	<div id="page">
		<%-- Wrapper --%>
		<div class="wrapper">
			<%-- Left column/section --%>
			<section class="column width6 first">
					<h3>GIS,MAPPING YOUR WORLD</h3>
					<span class="subtitle">福建师范大学 第六届GIS节</span>
					<hr/>					
					<div id="video-flash">
					<p>
						<embed src="http://player.youku.com/player.php/sid/XNjA5MzE4MjAw/v.swf" allowFullScreen="true" quality="high" width="738px" height="428px" align="middle" type="application/x-shockwave-flash" flashvars="winType=index" style="visibility:visible;"></embed>
					</p>
					</div>
					<p>&nbsp;</p>			
					</section>
			<%-- Right column/section --%>
			<%@ include file="/public/register_right.jsp"%>
		</div>
	</div>
	<%@ include file="/public/page_footer.jsp"%>
  </body>
</html>
