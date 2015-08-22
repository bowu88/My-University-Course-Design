<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% request.setAttribute("currentPage", "earthquakeInfo");  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>fjnuGIS-莆田市地震应急信息服务平台</title>
    <%@ include file="/public/basic_link_script.jsp"%>
    <script language="javascript" src="http://api.tianditu.com/js/maps.js"></script> 
    <script type="text/javascript">
    
    $(document).ready(function(){
	
	/* datatable */
	$('#earthquakeTable').dataTable({
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
			<h2>莆田市2级以上地震信息</h2>
			<span class="subtitle">2012年1月1日 至 2014年9月7日 </span>
			<hr>
			<table class="display stylized" id="earthquakeTable">
			<thead>
				<th>时间</th>
				<th>位置描述</th>
				<th>震级(M)</th>
				<th>经度</th>
				<th>纬度</th>
				<th>震源深度</th>
				<th>操作</th>
			</thead>
			<tbody>
			<c:forEach items="${list}" var="e">
				<tr>
				<td>${e.time}</td>
				<td>${e.location}</td>
				<td>${e.magnitude}</td>
				<td>${e.x}</td>
				<td>${e.y}</td>
				<td>${e.depth}</td>
				<td><a href="${pageContext.request.contextPath }/iframe.jsp?time=${e.time }&magnitude=${e.magnitude }&depth=${e.depth }&x=${e.x}&y=${e.y}" class="nyroModal">查看地图</a></td>
				</tr>
			</c:forEach>
			</tbody>
			<tfoot>
				<th>时间</th>
				<th>位置描述</th>
				<th>震级(M)</th>
				<th>经度</th>
				<th>纬度</th>
				<th>震源深度</th>
				<th>操作</th>
				</tr>
			</tfoot>
			</table>
			<hr/>
			<span class="subtitle">数据来源:中国地震信息网</span>
			<p>&nbsp;</p>
		</section>
		<%@ include file="/public/register_right.jsp"%>
		
		</div>
	</div>
	<%@ include file="/public/page_footer.jsp"%>
  </body>
</html>