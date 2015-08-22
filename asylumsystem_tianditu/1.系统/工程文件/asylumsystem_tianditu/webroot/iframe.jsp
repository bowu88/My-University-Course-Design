<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("x", request.getParameter("x"));
request.setAttribute("y", request.getParameter("y"));
request.setAttribute("depth", request.getParameter("depth"));
request.setAttribute("magnitude", request.getParameter("magnitude"));
request.setAttribute("time", request.getParameter("time"));
%>
<iframe src="${pageContext.request.contextPath }/servlet/ViewLocationServlet?time=${time }&magnitude=${magnitude }&depth=${depth }&x=${x}&y=${y}" style="width:100%;height:100%;padding: 0px;margin: 0px;"></iframe>
