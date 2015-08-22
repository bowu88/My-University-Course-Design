<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("currentPage", "about");  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>fjnuGIS-关于本站</title>
    <%@ include file="/public/basic_link_script.jsp"%>
    <script type="text/javascript" SRC="${pageContext.request.contextPath }/js/swfobject.js"></script>
    <script type="text/javascript">
	$(document).ready(function(){
		
		Administry.setup();
		$("#tabs, #tabs2").tabs();
		
		});
	</script>
  </head>
  <body>
   <%@ include file="/public/page_header.jsp"%>
   <%-- Page title --%>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>关于 &rarr; <span>关于fjnuGIS</span></h1>
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
						<span class="subtitle">福建师范大学 地理信息系统 交流平台</span>
					<hr/>
					<div id="tabs">
						<ul>
							<li><a class="corner-tl" href="#tabs-1">开发背景</a></li>
							<li><a class="" href="#tabs-2">技术细节</a></li>
							<li><a class="corner-tr" href="#tabs-3">开发日志</a></li>
						</ul>
						<div id="tabs-1">
							<p>&nbsp;&nbsp;&nbsp; 在学习张明峰老师&ldquo;计算机网络技术&rdquo;课程的过程中，有个作业是制作班级的通讯录，于是我们团队想到了制作一个师大GIS的通讯录，并由此发展成一个师大GIS的交流平台。在这个平台上，用户的联系方式将被发布为通讯录，以便于GISer之间的联系交流。</p>
							<p>&nbsp;&nbsp;&nbsp; 借着完成本课程期末作业的契机，我们将这个想法实现了。虽然功能还不完善，但网站基本有了雏形。</p>
							<p>&nbsp;&nbsp;&nbsp; 2013年12月8日，我们注册了fjnugis.tk这个免费的域名，并将网站发布到了新浪的SAE（Sina Application Engine）云平台上，正式上线运营。</p>
						</div>
						<div id="tabs-2">
							<p>&nbsp;&nbsp;&nbsp; 服务器端采用java语言开发，前端使用html、javascript，采用MVC（模型-视图-控制器）设计模式，使擅长服务端开发人员可以专心采用JavaEE开发，而前端开发人员和美工即使没学过JavaEE也能应用自己擅长的html、css、javascript等前端技术进行开发。</p>
							<p>&nbsp;&nbsp;&nbsp; 数据库使用的是轻量级的MySQL，这使得本网站可以很容易的发布到新浪SAE、百度BAE等平台上。</p>
						</div>
						<div id="tabs-3">
							<p>&nbsp;&nbsp;&nbsp; 2013年12月8日，发布到SAE上后发现SAE对java的支持不太好，在线运行和本地运行的结果不太一样，使用Apache的beanuitl包中的日期转换器无法成功将字符串转为日期。</p>
							<p>&nbsp;&nbsp;&nbsp; 2013年12月9日，将网站迁移至国外的OpenShift提供的免费空间，发现在国内会被墙，导致网站无法访问。</p>
							<p>&nbsp;&nbsp;&nbsp; 2013年12月10日，将网站迁移至百度BAE云平台，发现BAE对Java的支持不如SAE。于是将网站又迁回了SAE，并对网站的代码进行修改，放弃使用SAE支持不好的Apache提供的日期转换器，采用我们自己编写个简单的的转换器，由此解决SAE支持不好的问题。</p>
							<p>&nbsp;&nbsp;&nbsp; 2013年12月12日，解决了OpenShift被墙的问题，发现网站发布到OpenShift平台上访问速度比SAE快。又将网站迁移到OpenShift平台上。</p>
							<p>&nbsp;&nbsp;&nbsp; 2013年12月12日，更新代码，加入控制css、js、图片缓存的过滤器，使浏览器打开这些没怎么变动的资源时读取缓存。并且对一些图片进行了无损压缩。网页打开速度得到明显提高。</p>
							<p>&nbsp;&nbsp;&nbsp; 2013年12月13日，解决OpenShift平台添加数据到数据库出现中文乱码的问题。</p>
							<p>&nbsp;&nbsp;&nbsp; 2014年1月11日，添加了“在线工具”，发布了计量地理学两个的web程序——频率曲线统计图、多元线性回归分析。</p>
						</div>
					</div> 
								
					<p>&nbsp;</p>			
					</section>
			<%-- Right column/section --%>
			<%@ include file="/public/register_right.jsp"%>
		</div>
	</div>
	<%@ include file="/public/page_footer.jsp"%>
  </body>
</html>
