<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%-- Header --%>
<header id="top">
	<div class="wrapper">
		<%-- Title/Logo - can use text instead of image --%>
		<div id="title"><a href="${pageContext.request.contextPath}/index.jsp"><img src="${pageContext.request.contextPath}/img/logo.png" alt="Administry" /></a><span>福建师范大学 地理信息系统</span></div>
		<%-- Top navigation --%>
		 <c:if test="${user==null}">
		 <div id="topnav">
			<a href="#"><img class="avatar" src="${pageContext.request.contextPath}/img/user_32.png" alt="" /></a>
			访客:<b>${pageContext.request.remoteAddr}</b>
			<span>|</span> <a href="${pageContext.request.contextPath}/servlet/LoginUIServlet">登陆</a>
			<span>|</span> <a href="${pageContext.request.contextPath}/servlet/RegisterUIServlet" target="_blank">注册</a><br />
			<small>游客模式</small>
		</div>
		 </c:if><c:if test="${user!=null}">
		<div id="topnav">
			<a href="#"><img class="avatar" src="${pageContext.request.contextPath}/img/user_32.png" alt="" /></a>
			欢迎您， <b>${user.name}</b>
			<%--<span>|</span> <a href="#">设置</a>  --%>
			<span>|</span> <a href="${pageContext.request.contextPath}/servlet/LogoutServlet">注销</a><br />
			<small>登陆类型：${user.type}</small>
		</div>
		</c:if>
		<%-- End of Top navigation --%>
		
		<%-- Main navigation --%>
		<nav id="menu">
			<ul class="sf-menu">
				<li class="${currentPage=='index'?'current':''}">
					<a HREF="${pageContext.request.contextPath}/index.jsp">首页</a>
				</li><li class="${currentPage=='news'?'current':''}">
					<a HREF="${pageContext.request.contextPath}/news.jsp">资讯</a>
				</li><li class="${currentPage=='webapp'?'current':''}">
					<a href="#">在线工具</a>
					<ul>
						<li><a href="#">计量地理学</a>
							<ul>
								<li><a HREF="${pageContext.request.contextPath}/servlet/GraphUIServlet">频率统计图</a></li>
								<li><a HREF="${pageContext.request.contextPath}/servlet/HuiguiUIServlet">多元回归分析</a></li>
							</ul>
						</li>
					</ul>
				</li><li class="${currentPage=='contacts'?'current':''}">
					<a HREF="${pageContext.request.contextPath}/servlet/ContactsServlet">通讯录</a>
				</li><li class="${currentPage=='about'?'current':''}">
					<a HREF="#">关于</a>
					<ul>
						<li>
							<a HREF="${pageContext.request.contextPath}/about_fjnugis.jsp">关于FJNUGIS</a>
						</li>
						<li>
							<a HREF="${pageContext.request.contextPath}/aboutus.jsp">关于我们</a>
						</li>
						<li>
							<a HREF="${pageContext.request.contextPath}/terms.jsp">服务条款</a>
						</li>
						<li>
							<a href="#">关注微博</a>
							<ul>
								<li><a HREF="http://weibo.com/youxiangyang" target="_blank">游向阳</a></li>
								<li><a HREF="http://weibo.com/u/2433512931" target="_blank">杨敬杰</a></li>
								<li><a HREF="http://weibo.com/u/2530494533" target="_blank">刘桂林</a></li>
								<li><a HREF="http://weibo.com/u/3235921814" target="_blank">周梦辉</a></li>
								<li><a HREF="http://weibo.com/u/2486205674" target="_blank">吴宇翔</a></li>
							</ul>
						</li>
					</ul>
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