<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% request.setAttribute("currentPage", "table");  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>fjnuGIS-莆田市地震应急信息服务平台</title>
    <%@ include file="/public/basic_link_script.jsp"%>
    <script type="text/javascript">
    $(document).ready(function(){
	
	/* datatable */
	$('#asylumsTable').dataTable({
		 "oLanguage" : {
                "sLengthMenu": "每页显示 _MENU_ 条记录",
                "sZeroRecords": "抱歉， 没有找到",
                "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
                "sInfoEmpty": "没有数据",
                "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
                "sZeroRecords": "没有检索到数据",
                 "sSearch": "搜索:",
                "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"
                }
                     
            }
	});
	
});
    
    </script>
  </head>
  
  <body>
     <%@ include file="/public/page_header.jsp"%>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>FJNUGIS &rarr; <span>莆田市地震应急信息服务平台</span></h1>
		</div>
	</div>
	<div id="page">
		<div class="wrapper">
		<section class="column width6 first">
			<h2>莆田市应急避难场所信息列表</h2>
			<span class="subtitle">截至2014年已建成的应急避难场所</span>
			<hr>
			<table class="display stylized" id="asylumsTable">
			<thead>
				<th>名称</th>
				<th>地址</th>
				<th>类别</th>
				<th>面积</th>
				<th>可容纳人数</th>
				
			</thead>
			<tbody>
			<c:forEach items="${asylums}" var="a">
				<tr>
				<td>${a.name }</td>
				<td>${a.address }</td>
				<td>${a.classid }</td>
				<td>${a.area }</td>
				<td>${a.people }</td>
				</tr>
			</c:forEach>
			</tbody>
			<tfoot>
				<tr>
				<th>名称</th>
				<th>地址</th>
				<th>类别</th>
				<th>面积</th>
				<th>可容纳人数</th>
				</tr>
			</tfoot>
			</table>
			<hr>
			<p><span class="subtitle">数据来源：莆田市地震局</span></p>
			<p>&nbsp;</p>
		</section>
		<%@ include file="/public/register_right.jsp"%>
		
		</div>
	</div>
	<%@ include file="/public/page_footer.jsp"%>
  </body>
</html>
