<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	request.setAttribute("currentTop", "usercenter");
	request.setAttribute("curenntSub", "myvoice");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>听我的 - 个人中心 - 我的声音</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<%@ include file="/WEB-INF/public/css_script.jsp"%>

</head>

<body>
	<div id="body-wrapper">
		<%@ include file="/WEB-INF/public/sidebar.jsp"%>
		<div id="main-content">
			<div class="content-box">
				<div class="content-box-header">
					<h3>我的声音</h3>
				</div>
				<div class="content-box-content">
					<div class="tab-content default-tab">
						<table>
							<thead>
								<tr>
									<th>标题</th>
									<th>标签</th>
									<th>描述</th>
									<th>图片</th>
									<th>位置</th>
									<th>发布日期</th>
									<th>操作</th>
								</tr>
							</thead>
							<c:forEach var="v" items="${voices}">
								<tr>
									<td>${v.title}</td>
									<td>${v.tag}</td>
									<td>${v.description}</td>
									<td>
										<c:url var="imgUrl" value="http://localhost:8080/TingwodeServer_SH/file/image">
											<c:param name="imagePath" value="${v.picture_url}"/>
										</c:url>
										<c:if test="${v.picture_url!=null}">
										<a rel="modal" href="${imgUrl}">查看</a>
										</c:if>
										<c:if test="${v.picture_url==null}">无图片</c:if>
									</td>
									<td>${v.longitude}，${v.latitude}</td>
									<td>${v.date}</td>
									<td>
									<a href="#" title="编辑"><img
											src="resources/images/icons/pencil.png" alt="Edit">
									</a> <a id="${v.id}" href="#" title="删除" onclick="deleteVoice(this)"><img
											src="resources/images/icons/cross.png" alt="Delete">
									</a></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
			<%@ include file="/WEB-INF/public/footer.jsp"%>
		</div>
	</div>
</body>
</html>
