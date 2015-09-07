<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<div id="sidebar">
	<div id="sidebar-wrapper">
		<h1 id="sidebar-title">
			<a href="#">Tingwode</a>
		</h1>
		<a href="http://www.tingwode.tk"><img id="logo"
			src="resources/images/logo.png" alt="听我的" /> </a>
		<c:if test="${user==null}">
			<div id="profile-links">
				<div id="user_area">未登录</div> 当前城市： <b id="currentCity">未知</b><br><a href="#" onclick="initMap()">重新加载地图</a><br /> <br />
				<a href="#regist" rel="modal" title="注册">注册</a> | <a href="#login"
					rel="modal" title="登录">登录</a>
			</div>
		</c:if>
		<c:if test="${user!=null }">
			<div id="profile-links">
				<div id="user_area">欢迎您, <a id="userName" href="#" title="个人中心">${user.username }</a></div>
				当前城市： <b id="currentCity">未知</b><br><a href="#" onclick="initMap()">重新加载地图</a><br /> <br />
				<a href="#regist" rel="modal" title="注册">注册</a> | <a href="#" onclick="logout()"
					title="退出">注销</a>
			</div>
		</c:if>
		<ul id="main-nav">
			<li><a id="index" href="index.jsp" class="nav-top-item no-submenu">
					首页 </a>
			</li>

			<li><a href="#" class="nav-top-item"> 声音 </a>
				<ul>
					<li><a href="#">热门声音</a></li>
					<li><a href="#">推荐声音</a></li>
					<li><a href="#">全部声音</a></li>
				</ul>
			</li>
			<li><a href="#" class="nav-top-item"> 专辑 </a>
				<ul>
					<li><a href="#">热门专辑</a></li>
					<li><a href="#">推荐专辑</a></li>
					<li><a href="#">全部专辑</a></li>
				</ul>
			</li>
			<li><a id="publish" href="#" class="nav-top-item">
					发布 </a>
				<ul>
					<li><a id="createAlbum" href="createAlbum.jsp">创建新专辑</a></li>
					<li><a id="publishVoice" href="publishVoice.jsp">发布新声音</a></li>
				</ul>
			</li>
			<li><a href="#" class="nav-top-item">好友</a>
				<ul>
					<li><a href="#">好友动态</a></li>
					<li><a href="#">好友声音</a></li>
					<li><a href="#">好友专辑</a></li>
				</ul>
			</li>
			<li><a id="usercenter" href="#" class="nav-top-item">个人中心</a>
				<ul>
					<li><a href="#">我的好友</a></li>
					<li><a id="myvoice" href="Page-myVoice">我的声音</a></li>
					<li><a id="myalbum" href="myAlbum.jsp">我的专辑</a></li>
					<li><a href="#">收藏的声音</a></li>
					<li><a href="#">收藏的专辑</a></li>
				</ul>
			</li>
			<li><a href="#" class="nav-top-item">设置</a>
				<ul>
					<li><a href="#">修改头像</a></li>
					<li><a href="#">修改资料</a></li>
				</ul>
			</li>
		</ul>
		<!-- End #main-nav -->
		<div id="login" style="display: none">
			<h3>登录</h3>
			<form id="loginform">
				<p>
					<label>邮箱：</label> <input class="text-input large-input"
						type="text" id="userEmail" name="user.email" onblur="setInputValue(this)"/>
				</p>
				<p>
					<label>密码：</label> <input class="text-input large-input"
						type="password" id="userPassword" name="user.password" onblur="setInputValue(this)"/>
				</p>
				<input id="login_btn" class="button" type="button" value="登录" />
				<script type="text/javascript">
				$(document).ready(function(){
					//登录按钮
					$("input#login_btn").click(function(){
						$(this).after("<span id='logining'><img src='resources/images/loading.gif'/>登录中...</span>");
						$(this).attr("disabled",true);
						$.post("web/User-login", {
							"user.email" : $("#userEmail").val(),
							"user.password" : $("#userPassword").val()
						}, function(data, status) {
							if (status == "success") {
								var j=JSON.parse(data);
								var message=j.message;
								if(message){
									alert(message);
								}else{
									//$("div#facebox").remove();
									//$("#user_area").html(j.username);
									alert("登录成功！欢迎您 "+j.username);
									location.reload(true);
								}
							}else{
								alert("服务器未响应");
							}
							$("input#login_btn").removeAttr("disabled");
							$("span#logining").remove();
						});
					});
				});
				</script>
			</form>
		</div>
		<div id="regist" style="display: none">
			<h3>注册</h3>
			<form id="registform" name="registform">
				<p>
					<label>用户名：</label> <input class="text-input large-input"
						type="text" id="user_username" name="ru_username" onblur="setInputValue(this)">
				</p>
				<p>
					<label>邮箱：</label> <input class="text-input large-input"
						type="text" id="user_email" onblur="setInputValue(this)"/>
				</p>
				<p>
					<label>密码：</label> <input class="text-input large-input"
						type="password" id="user_password" onblur="setInputValue(this)"/>
				</p>
				<p>
					<label>确认密码:</label> <input class="text-input large-input"
						type="password" id="user_password2" onblur="setInputValue(this)"/>
				</p>
				<p>
					<label>性别:</label> <input type="radio" name="gender" value="男" checked="checked" onchange="setInputValueById('registerGender',this)"/>男
					<input type="radio" name="gender" value="女" onchange="setInputValueById('registerGender',this)"/>女
					<input id="registerGender" type="hidden" name="user.gender" value="男">
				</p>
<!-- 				<p> -->
<!-- 					<input id="user_photo" name="photo" type="file" onchange="setInputFile(this)"> -->
<!-- 				</p> -->
				<input id="regist_btn" class="button" type="button" value="注册"/>
				<script type="text/javascript">
					//初始化导航
					var currentTopId="#"+'${currentTop}';
					$(currentTopId).addClass("current");
					var curenntSubId="#"+'${curenntSub}';
					if(curenntSubId!="#")
						$(curenntSubId).addClass("current");
					//注册按钮
					$("input#regist_btn").click(function(){
						$(this).after("<span id='registing'><img src='resources/images/loading.gif'/>注册中...</span>");
						$(this).attr("disabled",true);
						$.post("web/User-add",{
								"user.username" : $("#user_username").val(),
								"user.email" : $("#user_email").val(),
								"user.password" : $("#user_password").val(),
								"user.gender" : $("#registerGender").val(),
						},function(result,status){
							if (status == "success") {
								var j=JSON.parse(result);
								var message=j.message;
								alert(message);
								if(message=="注册成功"){
									location.reload(true);
								}
							}else{
								alert("服务器未响应");
							}
							$("input#regist_btn").removeAttr("disabled");
							$("span#registing").remove();
						});
					});
				</script>
			</form>
		</div>
	</div>
</div>
