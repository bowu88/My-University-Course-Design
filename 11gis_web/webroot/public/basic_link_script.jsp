<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- Favicons --%> 
<link rel="shortcut icon" type="image/png" HREF="${pageContext.request.contextPath}/img/favicons/favicon.png"/>
<link rel="icon" type="image/png" HREF="${pageContext.request.contextPath}/img/favicons/favicon.png"/>
<link rel="apple-touch-icon" HREF="${pageContext.request.contextPath}/img/favicons/apple.png" />
<%-- Main Stylesheet --%> 
<link rel="stylesheet" HREF="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<%-- Colour Schemes
Default colour scheme is blue. Uncomment prefered stylesheet to use it.
<link rel="stylesheet" HREF="${pageContext.request.contextPath}/css/brown.css" type="text/css" media="screen" />  
<link rel="stylesheet" HREF="${pageContext.request.contextPath}/css/gray.css" type="text/css" media="screen" />  
<link rel="stylesheet" HREF="${pageContext.request.contextPath}/css/green.css" type="text/css" media="screen" />
<link rel="stylesheet" HREF="${pageContext.request.contextPath}/css/pink.css" type="text/css" media="screen" />  
<link rel="stylesheet" HREF="${pageContext.request.contextPath}/css/red.css" type="text/css" media="screen" />
--%>
<%--swfobject - needed only if you require <video> tag support for older browsers --%>
<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/swfobject.js"></script>
<%-- jQuery with plugins --%>
<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/jquery-1.4.2.min.js"></script>
<%-- Could be loaded remotely from Google CDN : <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script> --%>
<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/jquery.ui.core.min.js"></script>
<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/jquery.ui.widget.min.js"></script>
<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/jquery.ui.tabs.min.js"></script>
<%-- jQuery tooltips --%>
<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/jquery.tipTip.min.js"></script>
<%-- Superfish navigation --%>
<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/jquery.superfish.min.js"></script>
<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/jquery.supersubs.min.js"></script>
<%-- jQuery popup box --%>
<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/jquery.nyroModal.pack.js"></script>
<%-- jQuery form validation --%>
<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/jquery.validate_pack.js"></script>
<%-- Internet Explorer Fixes --%> 
<%--[if IE]>
<link rel="stylesheet" type="text/css" media="all" HREF="${pageContext.request.contextPath}/css/ie.css"/>
<script SRC="${pageContext.request.contextPath}/js/html5.js"></script>
<![endif]--%>
<%--Upgrade MSIE5.5-7 to be compatible with MSIE8: http://ie7-js.googlecode.com/svn/version/2.1(beta3)/IE8.js --%>
<%--[if lt IE 8]>
<script SRC="${pageContext.request.contextPath}/js/IE8.js"></script>
<![endif]--%>
