<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% request.setAttribute("currentPage", "webapp");  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>fjnuGIS-在线工具</title>
    <%@ include file="/public/basic_link_script.jsp"%>
   <%--<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/flot/jquery.flot.min.js"></script>
    <script type="text/javascript" SRC="${pageContext.request.contextPath}/js/swfobject.js"></script>
    <script type="text/javascript"> 
	$(document).ready(function(){
		
		/* setup navigation, content boxes, etc... */
		Administry.setup();
		
		var d1 = ${data_arr_string};
		
		 $.plot($("#placeholder"), [
        {
            data: d1,
            bars: { show: true}
        },
        ]);
		
		});
	</script>--%> 
  </head>
  <body>
   <%@ include file="/public/page_header.jsp"%>
   <%-- Page title --%>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>FJNUGIS &rarr; <span>在线工具</span></h1>
		</div>
	</div>
	<%-- End of Page title --%>
	<%-- Page content --%>
	<div id="page">
		<%-- Wrapper --%>
		<div class="wrapper">
			<%-- Left column/section --%>
			<section class="column width6 first">
					<h3>频率统计图</h3>
						<span class="subtitle">计量地理学编程 109042011003 游向阳</span>
					<hr/>
						<%--<div id="placeholder" style="height:300px"></div> --%>
					<form action="${pageContext.request.contextPath}/servlet/GraphServlet">
					<p>
					<label class="required">请输入一组数据（以空格分隔）：</label>
					<br/>
					<textarea class="medium full" name="data"></textarea>
					</p>
					<p class="box">
						<input type="reset" value="清空" class="btn"/> 或 
						<input type="submit" value="绘图" class="btn btn-green big" id="submit">
					</p>
					</form>
					<P>
						例如：您可以输入以下数据<br>5.79 6.09 6.05 6.07 6.05 6.16 6.10 6.09 6.01 5.92 6.05 6.03 5.96 6.00 5.87 6.03 6.18 6.13 5.97 5.90 5.98 5.88 6.18 6.08 6.10 6.03 6.01 6.03 6.11 5.97 6.09 6.00 6.00 6.10 6.10 6.09 6.01 5.97 6.20 5.89 6.00 6.06 5.97 5.97 6.06 6.04 6.24 6.00 5.92 6.09 5.98 6.10 5.93 6.06 6.00 5.98 5.96 5.93 6.07 6.24 6.08 6.07 5.88 5.97 6.03 6.00 5.86 6.02 6.04 6.05 5.92 6.08 6.28 6.04 6.05 6.15 5.95 6.10 6.07 5.96 6.10 6.01 6.10 6.03 6.00 6.05 5.86 5.96 6.35 6.17 6.04 6.07 6.06 6.04 6.13 6.00 6.18 6.2 5.81 6.01 5.98 6.06 6.01 5.92 6.14 6.01 5.98 5.98 6.08 6.11 5.98 6.01 5.84 6.06 6.11 6.29 6.05 5.84 6.19 5.89 6.06 6.06
					</P>
			</section>
			<%-- Right column/section --%>
			<%@ include file="/public/register_right.jsp"%>
		</div>
	</div>
	<%@ include file="/public/page_footer.jsp"%>
  </body>
</html>