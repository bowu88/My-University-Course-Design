<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setAttribute("currentTop", "publish");
	request.setAttribute("curenntSub", "createAlbum");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>听我的 - 发布 - 创建新专辑</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="/WEB-INF/public/css_script.jsp"%>
	<script type="text/javascript">
	$(document).ready(function(){
		getMyVoices();
		//‘创建’按钮点击事件
		$("input#createAlbum_btn").click(function(){
			var checkedVoiceId=[];
			//获取被选中的声音的id
			$("input.checkVoice[type='checkbox']").each(function(){
				if($(this).attr('checked'))
					checkedVoiceId.push($(this).attr('id'));
			});
			var checkedVoiceIdStr=checkedVoiceId.join(" ");//使用空格连接id
			$.post("web/Album-create",{
				"album.name":$("#album_name").val(),
				"album.description":$("#album_description").val(),
				"voiceIdStr":checkedVoiceIdStr
			},function(data,status){
				if(status=="success"){
					alert(JSON.parse(data).message);
				}else{
					alert("服务器未响应");
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
		<div class="content-box column-left">
			<div class="content-box-header">
				<h3>创建新专辑</h3>
			</div>
			<div class="content-box-content ">
				<div class="tab-content default-tab" style="display: block;">
					<div class="tab-content default-tab" style="display: block;">
						<form id="albumform">
							<p>
								<label>专辑名：</label>
								<input id="album_name" class="text-input large-input" type="text">
							</p>
							<p>
								<label>描述：</label>
								<textarea id="album_description"></textarea>
							</p>
							<p>
								<label>声音：</label>
								已选择 <strong><span id="voices_count">0</span></strong> 首（请从右侧列表选择）
							</p>
							<p>
								<input id="createAlbum_btn" type="button" class="button" value="创建">
								<span id="creating" style="display:none;"><img src="resources/images/loading.gif"/>创建中..</span>
							</p>
							
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="content-box column-right">
				<div class="content-box-header">
					<h3>我的声音</h3>
				</div>
				<div class="content-box-content ">
					<div class="tab-content default-tab" style="display: block;">
						<div id="myvoices">
							<p>暂无声音</p>
						</div>
					</div>
				</div>
			</div>
		<div class="clear"></div>
		<%@ include file="/WEB-INF/public/footer.jsp"%>
		</div>
	</div>
  </body>
</html>
