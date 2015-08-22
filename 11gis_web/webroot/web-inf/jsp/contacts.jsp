<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>fjnuGIS-通讯录</title>
<%@ include file="/public/basic_link_script.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	
	/* setup navigation, content boxes, etc... */
	Administry.setup();
	
	/* datatable */
	$('#example').dataTable();
	
	/* expandable rows */
	Administry.expandableRows();
});

</script>
</head>
<body>
	<%@ include file="/public/page_header.jsp"%>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>FJNUGIS &rarr; <span>通讯录</span></h1>
		</div>
	</div>

	<!-- Page content -->
	<div id="page">
		<!-- Wrapper -->
		<div class="wrapper">
			<!-- Left column/section -->
			<section class="column width6 first">
			
			<h3>通讯录</h3>
			<table class="display stylized" id="example">
				<thead>
					<tr>
						<th>姓名</th>
						<th>性别</th>
						<th>研究方向</th>
						<th>籍贯</th>
						<th>生日</th>
						<th>邮箱</th>
						<th>手机</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="u" items="${list}">
					<tr class="gradeX">
						<td>${u.name}</td>
						<td>${u.gender}</td>
						<td>${u.direction }</td>
						<td>${u.hometown }</td>
						<td>${u.birthday }</td>
						<td>${u.email }</td>
						<td>${u.cellphone }</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<p>&nbsp;</p>
			</section>
			<!-- Right column/section -->
			<%@ include file="/public/register_right.jsp"%>
		</div>
	</div>
	<%@ include file="/public/page_footer.jsp"%>
	
</body>
</html>
