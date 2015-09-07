<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
	request.setAttribute("currentTop", "publish");
	request.setAttribute("curenntSub", "publishVoice");
%>
<title>听我的 - 发布 - 发布声音</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<%@ include file="/WEB-INF/public/css_script.jsp"%>
<script type="text/javascript">
$(document).ready(function() {

			initMap();
			
			//上传声音
			$("input#submit_btn").click(function(){
				if(mylocation==null){
					alert("未能获取当前位置");
					return;				
				}
				document.getElementById("submit_btn").disabled=true;
				$("#publishing").show();
				$.ajaxFileUpload({
					url : 'web/Voice-publish', //用于文件上传的服务器端请求地址
					secureuri : false, //是否需要安全协议，一般设置为false
					fileElementId : ['audio','picture'], //文件上传域的ID
					dataType : 'json', //返回值类型 一般设置为json
					success : function(data, status){
								alert(data.message);
								$("input#submit_btn").attr("disabled", false);
								$("#publishing").hide();
							},
					error : function(data,status, e){
						alert(e);
						$("input#submit_btn").attr("disabled", false);
						$("#publishing").hide();
					},
					data:{
						"voice.title":voiceform.voice_title.value,
						"voice.tag":voiceform.voice_tag.value,
						"voice.description":voiceform.voice_description.value,
						"voice.latitude":mylocation.lat,
						"voice.longitude":mylocation.lng,
					}
				});
			}); 

});
</script>
</head>

<body>
	<div id="body-wrapper">
		<%@ include file="/WEB-INF/public/sidebar.jsp"%>
		<div id="main-content">
			<!-- 地图窗口 -->
			<div class="content-box column-left">
				<div class="content-box-header">
					<h3>地图</h3>
				</div>
				<div class="content-box-content " style=" padding:0;">
					<div class="tab-content default-tab" style="display: block;">
						<div style="width: 100%;height: 70%;overflow: hidden;margin:0;"
							id="allmap"></div>
					</div>
				</div>
			</div>
			
			<!-- 添加声音窗口 -->
			<div class="content-box column-right">
				<div class="content-box-header">
					<h3>新建声音</h3>
				</div>
				<div class="content-box-content ">
					<div class="tab-content default-tab" style="display: block;">
						<form id="voiceform">
							<p>
								<label>标题：</label> <input
									class="text-input medium-input datepicker" type="text"
									id="voice_title" name="voice.title">
							</p>
							<p>
								<label>描述：</label> <input
									class="text-input large-input datepicker" type="text"
									id="voice_description" name="voice.description">
							</p>
							<p>
								<label>标签：</label> <input
									class="text-input large-input datepicker" type="text"
									id="voice_tag" name="voice.tag"> <br> <small>多个标签请用空格分隔</small>
							</p>
							<p>
 								<label>位置：</label>
								<div id="myloc"></div>
								<input type="button" class="button"
								onclick="addListenerOfGetLoc()" value="手动设置" /> <input
								type="button" class="button" onclick="getLocation()"
								value="自动定位" />
							</p>
							<p>
								<label>声音：</label> <input type="file" id="audio" name="upload" accept="audio/mp3, audio/wav">
								<br>
								<small>格式支持： mp3 、mav</small>
							</p>
							<p>
								<label>图片：</label> <input type="file" id="picture" name="upload" accept="image/gif, image/jpeg">
								<br>
								<small>格式支持： jpg、png、gif</small>
							</p>
							<p>
								<input id="submit_btn" type="button" class="button" value="发布">
								<span id="publishing" style="display:none;"><img src="resources/images/loading.gif"/>发布中..</span>
							</p>
						</form>
					</div>
				</div>
			</div>

			<div class="clear"></div>


			<%@ include file="/WEB-INF/public/footer.jsp"%>
		</div>
	</div>
</body>
</html>
