<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Page footer -->
	<footer id="bottom">
		<div class="wrapper">
			<nav>
				<a href="http://www.fjnugis.tk" target="bland">fjnuGIS首页</a> &middot;
				<a HREF="http://www.fjnu.edu.cn/" target="_blank">福建师范大学</a> &middot;
				<a HREF="http://geo.fjnu.edu.cn/home.asp" target="_blank">福师大地科院</a> &middot;
			</nav>
		<p>Copyright &copy; 2014 <b><a HREF="http://www.fjnugis.tk/" title="www.fjnugis.tk">www.fjnugis.tk</a></b> | Icons by <a HREF="http://www.fjnugis.tk/">fjnugis.tk</a></p>		</div>
	</footer>
	<!-- End of Page footer -->

<!--  footer -->
	<footer id="animated">
		<ul>
			<li >
					<a HREF="${pageContext.request.contextPath}/index.jsp">地图</a>
				</li><li>
					<a HREF="${pageContext.request.contextPath}/servlet/AsylumListServlet">避难场所信息</a>
				</li><li>
					<a HREF="${pageContext.request.contextPath}/servlet/EarthquakeListServlet">地震信息</a>
				</li><li>
					<a href="${pageContext.request.contextPath}/science.jsp">地震科普</a>
				</li><li>
					<a HREF="${pageContext.request.contextPath }/aboutThis.jsp">关于本站</a>
				</li>
		</ul>
	</footer>
	<!-- End of Animated footer -->
	<!-- Scroll to top link -->
	<a href="#" id="totop">^ 回到顶部</a>

	<!-- User interface javascript load -->
	<script type="text/javascript" SRC="${pageContext.request.contextPath}/js/administry.js"></script>
