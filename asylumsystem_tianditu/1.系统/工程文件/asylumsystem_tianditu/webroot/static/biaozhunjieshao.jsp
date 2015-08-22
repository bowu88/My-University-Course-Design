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
					<h3>《地震应急避难场所场址及配套设施》标准介绍</h3>
						<span class="subtitle">GB 21734-2008 简介</span>
					<hr/>
					<p class="indent">
					GB 21734-2008 《地震应急避难场所场址套设施》为强制性国家标准，规定了地震应急避难场
所的分类、场址选择及设施配置的要求。目的是为了应对地震突发事件，防御与减轻地震灾害，科学
合理建设地震应急避难场所，为居民提供应急避险空间，快速有许地疏散安置居民，本标准为首次发
布。其所作的各项规定，是建立在“统一规划、平震结合、因地制宜、综合利用、就近疏散、安全与
通达”的地震应急避难场所建设原则的基础上。适用于经城乡规划选定为地震应急避难场所的设计、
建设或改造。
					</p>
					<h4>一、地震应急避难场所分类</h4>
					<ul>
						<li><strong>Ⅰ类</strong>地震应急避难场所：具备综合设施配置，可安置受助人员30d 以上；</li>
						<li><strong>Ⅱ类</strong>地震应急避难场所：具备一般设施配置，可安置受助人员10d-30d；</li>
						<li><strong>Ⅲ类</strong>地震应急避难场所：具备基本设施配置，可安置受助人员10d 以内；</li>
					</ul>
					<h4>二、场址要求</h4>
					<ul>
						<li><strong>场址选择</strong><p>
公园（不包括动物园和文物古迹保护区域）；绿地；广场；体育场；室内公共的场、馆、所。</p></li>
						<li>
						<strong>安全性要求</strong><p>
应避开自然灾害易发地段、应选择地势合适的地形、应选择远离对人身安全产生影响的设
施范围外、应选择在高层建筑物、高耸构造物垮塌范围距离外、选择室内场所做场址时，
应达到当地抗震设防要求，在震后照GB 18208.2-2001 进行建筑物鉴定，鉴定合格后启用。</p>
						</li>
						<li>
						<strong>可通达性要求</strong><p>
应急避难场所应有方向不同的两条以上与外界相通的疏散道路。</p>
						</li>
						<li>
						<strong>面积要求</strong><p>
场址有效面积宜大于2000m2，人均居住面积应大于1.5 m2。</p>
						</li>
					</ul>
					<h4>三、设施配置</h4>
					<ul>
						<li><strong>一般设施配置</strong><p>
应急消防设施、应急物资储备设施、应急指挥管理设施。</p>
						</li>
						<li>
						<strong>综合设施配置</strong><p>
应急停车场、应急停机坪、应急洗浴设施、应急通风设施、功能介绍设施。</p>
						</li>
					</ul>
					<h4>四、其他要求</h4>
					<p class="indent">包括标志设置、抗震性能、蓬宿区内分区、无障碍、功能介绍、场地有效面积等方面提出要求</p>
					</section>
			<%-- Right column/section --%>
			<%@ include file="/public/register_right.jsp"%>
		</div>
	</div>
	<%@ include file="/public/page_footer.jsp"%>
  </body>
</html>
