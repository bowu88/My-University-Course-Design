<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>消息</title>
    <%@ include file="/public/basic_link_script.jsp"%>
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
			<h1>消息</h1>
		</div>
	</div>
	<%-- End of Page title --%>
	<%-- Page content --%>
	<div id="page" style="min-height:320px">
		<%-- Wrapper --%>
		<div class="wrapper">
			<%-- Left column/section --%>
			<section class="column width6 first">
					<p>&nbsp;</p>
					<div class="box box-${message_type}">${message }</div>
			</section>
			<%-- Right column/section --%>
		</div>
	</div>
	<%@ include file="/public/login_pagefooter.jsp"%>
  </body>
</html>
