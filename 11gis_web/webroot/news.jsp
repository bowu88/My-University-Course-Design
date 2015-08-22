<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("currentPage", "news");  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>fjnuGIS-资讯</title>
    <%@ include file="/public/basic_link_script.jsp"%>
    <script type="text/javascript" SRC="js/swfobject.js"></script>
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
			<h1>FJNUGIS &rarr; <span>资讯</span></h1>
		</div>
	</div>
	<%-- End of Page title --%>
	<%-- Page content --%>
	<div id="page">
		<%-- Wrapper --%>
		<div class="wrapper">
			<%-- Left column/section --%>
			<section class="column width6 first">
					<h3>资讯</h3>
						<span class="subtitle">福建师范大学 地理信息系统 交流平台</span>
					<hr/>					
					<p>&nbsp;</p>			
					</section>
			<%-- Right column/section --%>
			<%@ include file="/public/register_right.jsp"%>
		</div>
	</div>
	<%@ include file="/public/page_footer.jsp"%>
  </body>
</html>
