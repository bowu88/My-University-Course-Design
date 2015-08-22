<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("currentPage", "science");  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>fjnuGIS-莆田市地震应急信息服务平台</title>
    <%@ include file="/public/basic_link_script.jsp"%>
    <script type="text/javascript" SRC="js/swfobject.js"></script>
    <script type="text/javascript">
	$(document).ready(function(){
		
		/* setup navigation, content boxes, etc... */
		Administry.setup();
	});
	</script>
  </head>
  <body>
   <%@ include file="/public/page_header.jsp"%>
   <%-- Page title --%>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>FJNUGIS &rarr; <span>地震科普</span></h1>
		</div>
	</div>
	<%-- End of Page title --%>
	<%-- Page content --%>
	<div id="page">
		<%-- Wrapper --%>
		<div class="wrapper">
			<%-- Left column/section --%>
			<section class="column width6 first">
					<h3>地震科普</h3>
						<span class="subtitle">地震常识，应急知识</span>
					<hr/>
					<h4><a href="${pageContext.request.contextPath}/static/biaozhunjieshao.jsp">《地震应急避难场所场址及配套设施》标准介绍</a></h4><p>
					<h4><a href="${pageContext.request.contextPath}/static/ruhetaosheng.jsp">地震如何逃生</a></h4><p>
					<h4><a href="${pageContext.request.contextPath}/static/chengzhen.jsp">福建省主要城镇抗震设防烈度、设计基本地震加速度和设计地震分组</a></h4>
					<p>&nbsp;</p>			
					</section>
			<%-- Right column/section --%>
			<%@ include file="/public/register_right.jsp"%>
		</div>
	</div>
	<%@ include file="/public/page_footer.jsp"%>
  </body>
</html>
