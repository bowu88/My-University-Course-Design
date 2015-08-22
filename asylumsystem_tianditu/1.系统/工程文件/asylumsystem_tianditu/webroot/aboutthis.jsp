<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("currentPage", "aboutThis");  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>fjnuGIS-莆田市地震应急信息服务平台</title>
    <%@ include file="/public/basic_link_script.jsp"%>
      <style type="text/css">
    p{
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
			<h1>FJNUGIS &rarr; <span>关于本站</span></h1>
		</div>
	</div>
	<%-- End of Page title --%>
	<%-- Page content --%>
	<div id="page">
		<%-- Wrapper --%>
		<div class="wrapper">
			<%-- Left column/section --%>
			<section class="column width6 first">
				<h3>关于本站</h3>
					<span class="subtitle">by 福师大地科院11级地理信息系统 游向阳</span>
				<hr/>
				<p>2013年这一年对于<a href="http://zh.wikipedia.org/wiki/%E4%BB%99%E6%B8%B8%E5%8E%BF">仙游</a>这个小县城来说是个不平静的一年。仙游在这一整年发生了20次地震<em>(数据来源:<a href="http://www.csi.ac.cn/">中国地震信息网</a>)</em>。其中震感最强烈的一次是9月4日发生在莆田市仙游县与福州市永泰县交界处的4.8级地震。福建省多地有震感，与仙游相邻的泉州市区及永春、德化震感明显，厦门不少市民也被从睡梦中震醒。</p>
				<q>2013年9月4日仙游地震发生时，福建省多地有震感，与仙游相邻的泉州市区及永春、德化震感明显，厦门不少市民也被从睡梦中震醒。地震发生后，省地震局立即启动Ⅲ级应急响应预案。截至4日15时，震区未发现人员伤亡。9月4日6时23分，在莆田市仙游县、永泰县交界处（北纬25.6度、东经118.8度）发生4.8级地震，震源深度10千米。地震发生后，省地震局立即启动Ⅲ级应急响应预案。截至4日15时，仙游县石苍乡有2处老旧房屋倒塌、3所学校校舍墙体出现裂缝，游洋镇1处老旧房屋倒塌，部分路面、墙体出现裂缝、瓦片脱落，未发现人员伤亡。<cite>——百度百科-<a href="http://baike.baidu.com/view/10896005.htm">9.4莆田仙游7连震</a></cite></q>
				<p>仙游是我的家乡，本站的建设旨在为这个小县城提供地震应急信息，如果您的所在地正是莆田市，您可以通过本站了解到莆田市所有的应急避难场所信息，并且通过路线规划工具您还可以查询到您住所到最近的应急避难场所的最快捷的路线。</p>
				<p>&nbsp;</p>
			</section>
			<%-- Right column/section --%>
			<%@ include file="/public/register_right.jsp"%>
		</div>
	</div>
	<%@ include file="/public/page_footer.jsp"%>
  </body>
</html>
