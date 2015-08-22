<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登陆</title>
    <%@ include file="/public/login_head_link_script.jsp"%>
  </head>
  <body>
  	<%@ include file="/public/login_header.jsp"  %>
	
	<!-- Page content -->
	<div id="page">
		<!-- Wrapper -->
		<div class="wrapper-login">
				<!-- Login form -->
				<section class="full">					
					
					<h3>登陆</h3>
					
					<div class="box box-info">输入登陆信息</div>

					<form id="loginform" method="post" action="${pageContext.request.contextPath}/servlet/LoginServlet">

						<p>
							<label class="required" for="email">登陆邮箱:</label><br/>
							<input type="text" id="email" class="full" value="" name="email"/>
						</p>
						
						<p>
							<label class="required" for="password">密码:</label><br/>
							<input type="password" id="password" class="full" value="" name="password"/>
						</p>
						
						<p>
							<input type="checkbox" id="remember" class="" value="1" name="remember"/>
							<label class="choice" for="remember">自动登录</label>
						</p>
						
						<p>
							<input type="submit" class="btn btn-green big" value="登陆"/>
						</p>
						<div class="clear">&nbsp;</div>

					</form>		
					
				</section>
				<!-- End of login form -->
				
		</div>
		<!-- End of Wrapper -->
	</div>
	<!-- End of Page content -->
	
	<%@ include file="/public/login_pagefooter.jsp" %>

<!-- User interface javascript load -->
<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/administry.js"></script>
<%-- 
	 <form action="${pageContext.request.contextPath}/servlet/LoginServlet" method="post">
	   <p>登陆邮箱<input type="text" name="email"/></p>
	   <p>登陆密码<input type="password" name="password"/></p>
	   <input type="submit" value="登陆">
	 </form>--%>
  </body>
</html>
