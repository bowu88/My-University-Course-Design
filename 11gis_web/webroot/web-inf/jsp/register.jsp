<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=	UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户注册</title>
<%@ include file="/public/form_head_link_script.jsp"%>
<script type="text/javascript">
	$(document).ready(function(){
		
		/* setup navigation, content boxes, etc... */
		Administry.setup();
	
		/* progress bar animations - setting initial values */
		Administry.progress("#capacity", 72, 100);
		
		/* tabs */
		$("#tabs").tabs();
		
	});
	</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/initForm.js"></script>
</head>
<body onload="pageInit('${formbean.birthday}')">
	<%@ include file="/public/login_header.jsp"%>
	<!-- Page title -->
	<div id="pagetitle">
		<div class="wrapper">
			<h1>用户注册</h1>
		</div>
	</div>
	<!-- End of Page title -->
	<!-- Page content -->
	<div id="page">
		<!-- Wrapper -->
		<div class="wrapper">
			<!-- Left column/section -->
			<section class="column width6 first">
			<form
				action="${pageContext.request.contextPath}/servlet/RegisterServlet"
				id="sampleform" method="post" onsubmit="return dosubmit()">
				<fieldset>
					<div class="box box-info">登陆信息将用于账号的登陆，请您务必记牢。</div>
					<legend>登陆信息</legend>
					<p>
						<label class="required">登陆类型：</label><label class="error">${formbean.errors.type}</label><br>
						<input type="radio" name="type" value="老师" ${formbean.type=='老师'? 'checked':'' }>老师
						<input type="radio" name="type" value="学生" ${formbean.type=='学生'?'checked':''}>学生
						<input type="radio" name="type" value="学生" ${formbean.type=='从业者'?'checked':''}>从业者
						<input type="radio" name="type" value="其他" ${formbean.type=='其他'?'checked':''}>其他
					</p>
					<p>
						<label class="required">邮箱:</label><label class="error">${formbean.errors.email}</label>
						<br />
						<input type="text" name="email" class="half" value="${formbean.email}">
					</p>
					<p>
						<label class="required">密码:</label><label class="error">${formbean.errors.password}</label>
						<br />
						<input type="password" class="half" name="password">
					</p>
					<p>
						<label class="required">确认密码:</label><label class="error">${formbean.errors.password2}</label>
						<br />
						<input type="password" class="half" name="password2">
					</p>
				</fieldset>
				<fieldset>
					<div class="box box-info">个人信息将用于构建本站的通讯录，以方便师大GISer之间的联系交流，望您认真填写。</div>
					<legend>个人信息</legend>

					<p>
						<label class="required">姓名:</label><label class="error">${formbean.errors.name}</label>
						<br /> 
						<input type="text" name="name" class="half" value="${formbean.name}">
					</p>
					<p>
						<label class="required">性别:</label><label class="error">${formbean.errors.gender}</label>
						<br />
						<input type="radio" name="gender" value="男" ${formbean.gender=='男'? 'checked':'' }>男
						<input type="radio" name="gender" value="女" ${formbean.gender=='女'? 'checked':'' }>女
					</p>
					<p>
						<label class="required">籍贯:</label><label class="error">${formbean.errors.hometown}</label>
						<br /> <input type="text" name="hometown" class="half" value="${formbean.hometown}">
					</p>
					<p>
						<label class="choice">家庭住址:</label><label class="error">${formbean.errors.address}
						<br />
						<input type="text" name="address" class="full" value="${formbean.address}">
					</p>
					<p>
						<label class="required">生日:</label><label class="error">${formbean.errors.birthday}</label>
						<br> 
						<select id="selYear"></select>年
						<select id="selMonth"></select>月
						<select id="selDay"></select>日
					</p>
					<p>
						<label class="required">手机:</label><label class="error">${formbean.errors.cellphone}</label>
						<br>
						<input type="text" name="cellphone" class="half" value="${formbean.cellphone}">
					</p>
					<p>
						<label class="choice">家庭电话:</label><label class="error">${formbean.errors.home_phone}</label>
						<br>
						<input type="text" name="home_phone" class="half" value="${formbean.home_phone}">
					</p>
					<p>
						<label class="required">研究方向</label><label class="error">${formbean.errors.direction}</label>
						<br />
						<c:forEach var="dir" items="${directionItems }">
							<input type="checkbox" name="pre" value="${dir}" ${fn:contains(formbean.direction,dir)?'checked':''}/>${dir} 
						</c:forEach>
					</p>
					<p>
						<label class="choice">备注:</label>
						<br />
						<textarea class="medium half" name="description">${formbean.description}</textarea>
						<input type="hidden" name="token" value="${sessionScope.token}">
					</p>
					<p>
						<label class="required">验证码:</label><input type="text" name="checkcode">
						<img border="1" src="${pageContext.request.contextPath }/servlet/CheckCodeImage" />
						<label class="error">${formbean.errors.checkcode}</label>
					</p>
					<p>
						<input type="checkbox" id="terms" class="" value="1" name="terms" />
						<label class="choice" for="terms">我已经阅读并接受 <a HREF="${pageContext.request.contextPath}/terms.jsp">服务条款</a>
						</label><label class="error">${formbean.errors.terms}</label>
					</p>
					<p class="box">
						<input type="reset" value="重置" class="btn"/> 或 
						<input type="submit" value="提交" class="btn btn-green big" id="submit">
					</p>
				</fieldset>
			</form>
			</section>
			<!-- End of Right column/section -->

		</div>
		<!-- End of Wrapper -->
		<!-- End of Page content -->
		<%@ include file="/public/register_right.jsp"%>
	</div>
	<!-- End of Page content -->
	<%@ include file="/public/page_footer.jsp"%>
</body>
</html>
