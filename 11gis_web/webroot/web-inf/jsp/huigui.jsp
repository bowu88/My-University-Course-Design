<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% request.setAttribute("currentPage", "webapp");  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>fjnuGIS-在线工具</title>
    <%@ include file="/public/basic_link_script.jsp"%>
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
					<h3>多元回归分析</h3>
						<span class="subtitle">计量地理学编程 109042011003 游向阳</span>
					<hr/>
					<form action="${pageContext.request.contextPath}/servlet/HuiguiUIServlet">
					<p>
					请输入X的个数：<input type="text" name="HuiguiK" value="${HuiguiK==null?2:HuiguiK}">
					<input type="submit" value="设置" class="btn" id="submit">
					</p>
					</form>
					
					<form action="${pageContext.request.contextPath}/servlet/HuiguiServlet">
					<p>
					<label class="required">请输入样本数据（以空格分隔）：</label>
					<br/>
					<h4>Y =</h4>
					<input type="text" name="HuiguiY" class="full">
					</p>
					<p>
					<%for(int i=0;i<(Integer)request.getSession().getAttribute("HuiguiK");i++){ %>
						<h4>X<%=i+1 %>=</h4>
						<input type="text" name="HuiguiX" class="full"><br>
					<%} %>
					</p>
					<p class="box">
						<input type="reset" value="清空" class="btn"/> 或 
						<input type="submit" value="计算" class="btn btn-green big" id="submit">
					</p>
					</form>
					<P>
						测试数据<br>
						Y = 6825.99 512 1902 146 2824 37 52 56 187 1065 107 173 771 192<br>
						X1 = 1298 119.8 344.28 235.56 163.79 76.72 17.81 30.66 15.92 345.08 6.7 28 75  12.47<br>
						X2 = 437.26 1283.48 1128.33 600.58 783.15 65.26 441.26 242.33 23.98 371.98 324.4 262.11 1508.16 1072.27<br>
					</P>
			</section>
			<%-- Right column/section --%>
			<%@ include file="/public/register_right.jsp"%>
		</div>
	</div>
	<%@ include file="/public/page_footer.jsp"%>
  </body>
</html>