<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Header -->
	<header id="top">
		<div class="wrapper-login">
			<!-- Title/Logo - can use text instead of image -->
			<div id="title"><a href="${pageContext.request.contextPath}/index.jsp"><img SRC="${pageContext.request.contextPath}/img/logo.png" alt="Administry" /></a></div>
			<!-- Main navigation -->
			<nav id="menu">
				<ul class="sf-menu">
					<li class="${currentPage=='login'?'current':''}"><a href="${pageContext.request.contextPath}/servlet/LoginUIServlet">登陆</a></li>
					<li class="${currentPage=='register'?'current':''}"><a href="${pageContext.request.contextPath}/servlet/RegisterUIServlet">注册</a></li>
				</ul>
			</nav>
			<!-- End of Main navigation -->
			<!-- Aside links -->
			<aside><b>简体中文</b>
			<!-- End of Aside links -->
		</div>
	</header>
	<!-- End of Header -->
	
	<!-- Page title -->
	<div id="pagetitle">
		<div class="wrapper-login"></div>
	</div>
	<!-- End of Page title -->