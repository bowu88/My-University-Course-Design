<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("currentPage", "science");  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>fjnuGIS-莆田市地震应急信息服务平台</title>
    <%@ include file="/public/basic_link_script.jsp"%>
    <style type="text/css">
    p.indent{
    	text-indent: 2em;
    }
    </style>
    <script type="text/javascript" SRC="js/swfobject.js"></script>
    <script type="text/javascript">
	$(document).ready(function(){
		
		/* setup navigation, content boxes, etc... */
		Administry.setup();
	});
	</script>
  </head>
  <body>
   <%@ include file="/public/page_header.jsp"%>
   <%-- Page title --%>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>FJNUGIS &rarr; <span>地震科普</span></h1>
		</div>
	</div>
	<%-- End of Page title --%>
	<%-- Page content --%>
	<div id="page">
		<%-- Wrapper --%>
		<div class="wrapper">
			<%-- Left column/section --%>
			<section class="column width6 first">
					<h3>福建省主要城镇抗震设防烈度、设计基本地震加速度和设计地震分组</h3>
						<span class="subtitle">2008 年版修订|根据国家标准GB18306-2001《中国地震动参数区划图》第1号修改单(国标委服务函[2008]57号)</span>
					<hr/>
					<p class="indent">
					本附录仅提供福建省抗震设防区各县级及县级以上城镇的中心地区建筑工程抗震设计时所采用的抗震设防烈度、设计基本地震加速度值和所属的设计地震分组。
					</p>
					<p>注：本附录一般把“设计地震第一、二、三组”简称为“第一组、第二组、第三组”。</p>
					<h4>1   抗震设防烈度为 8 度，设计基本地震加速度值为 0.20g：</h4>
					<ul>
						<li><strong>第一组：</strong>金门</li>
					</ul>
					<h4>2   抗震设防烈度为 7 度，设计基本地震加速度值为 0.15g：</h4>
					<ul>
						<li><strong>第一组：</strong>厦门（7 个市辖区），漳州（2 个市辖区），晋江，石狮，龙海，长泰，漳浦，东山，诏安</li>
						<li><strong>第二组：</strong>泉州（4 个市辖区）</li>
					</ul>
					<h4>3   抗震设防烈度为 7 度，设计基本地震加速度值为 0.10g：</h4>
					<ul>
						<li><strong>第一组：</strong>福州（除马尾外的 4 个市辖区），安溪，南靖，华安，平和，云霄</li>
						<li><strong>第二组：</strong>莆田（2 个市辖区），长乐，福清，莆田县，平谭，惠安，南安，马尾</li>
					</ul>
					<h4>4  抗震设防烈度为 6 度，设计基本地震加速度值为 0.05g：</h4>
					<ul>
						<li><strong>第一组：</strong>三明（2 个市辖区），政和，屏南，霞浦，福鼎，福安，柘荣，寿宁，周宁，松溪，宁德，古田，罗源，沙县，尤溪，闽清，闽侯南平，大田，漳平，龙岩，永定，泰宁，宁化，长汀，武平，建宁，将乐，明溪，清流，连城，上杭，永安，建瓯</li>
						<li><strong>第二组：</strong>连江，永泰，德化，永春，仙游</li>
					</ul>
					
					</section>
			<%-- Right column/section --%>
			<%@ include file="/public/register_right.jsp"%>
		</div>
	</div>
	<%@ include file="/public/page_footer.jsp"%>
  </body>
</html>
