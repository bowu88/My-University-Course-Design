<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%-- Header --%>
<header id="top">
	<div class="wrapper">
		<%-- Title/Logo - can use text instead of image --%>
		<div id="title"><a href="https://www.fjnugis.tk" target="blank"><img src="${pageContext.request.contextPath}/img/logo.png" alt="Administry" /></a><span>莆田市地震应急信息服务平台</span></div>
		
		<%-- Main navigation --%>
		<nav id="menu">
			<ul class="sf-menu">
				<li class="${currentPage=='index'?'current':''}">
					<a HREF="${pageContext.request.contextPath}/index.jsp">地图</a>
				</li><li class="${currentPage=='table'?'current':''}">
					<a HREF="${pageContext.request.contextPath}/servlet/AsylumListServlet">避难场所信息</a>
				</li><li class="${currentPage=='earthquakeInfo'?'current':''}">
					<a HREF="${pageContext.request.contextPath}/servlet/EarthquakeListServlet">地震信息</a>
				</li><li class="${currentPage=='science'?'current':''}">
					<a href="${pageContext.request.contextPath}/science.jsp">地震科普</a>
				</li><li class="${currentPage=='aboutThis'?'current':''}">
					<a HREF="${pageContext.request.contextPath }/aboutThis.jsp">关于本站</a>
				</li>
			</ul>
		</nav>
		<%-- End of Main navigation --%>
		<%-- Aside links --%>
		<aside><b>简体中文</b></aside>
		<%-- End of Aside links --%>
	</div>
</header>
<%-- End of Header --%>