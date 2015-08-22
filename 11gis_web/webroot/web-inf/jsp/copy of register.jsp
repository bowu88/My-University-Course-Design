<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户注册</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/initForm.js"></script>
</head>
<body
	onload="pageInit('${empty(formbean.birthday)?'1990-01-01':formbean.birthday}')">
	<form
		action="${pageContext.request.contextPath}/servlet/RegisterServlet"
		id="userform" method="post" onsubmit="return dosubmit()">
		<p>
			登陆类型： ${formbean.errors.type}<br> <input type="radio"
				name="type" value="老师" ${formbean.type=='老师'? 'checked':'' }>老师
			<input type="radio" name="type" value="学生" ${formbean.type=='学生'?'checked':''}>学生
			<input type="radio" name="type" value="其他" ${formbean.type=='其他'?'checked':''}>其他
		</p>
		<p>
			邮箱<input type="text" name="email" value="${formbean.email}">${formbean.errors.email}
		</p>
		<p>
			密码<input type="password" name="password">${formbean.errors.password}
		</p>
		<p>
			确认密码<input type="password" name="password2">${formbean.errors.password2}
		</p>
		<p>
			姓名<input type="text" name="name" value="${formbean.name}">${formbean.errors.name}
		</p>
		<p>
			性别${formbean.errors.gender} <input type="radio" name="gender"
				value="男" ${formbean.gender=='男'? 'checked':'' }>男 <input
				type="radio" name="gender" value="女" ${formbean.gender=='女'? 'checked':'' }>女
		</p>
		<p>
			籍贯 <input type="text" name="hometown" value="${formbean.hometown}">${formbean.errors.hometown}
		</p>
		<p>
			家庭住址<br /> <input type="text" name="address"
				value="${formbean.address}">${formbean.errors.address}
		</p>
		<p>
			生日 <select id="selYear"></select>年 <select id="selMonth"></select>月 <select
				id="selDay"></select>日 ${formbean.errors.birthday}
		</p>
		<p>
			手机<input type="text" name="cellphone" value="${formbean.cellphone}">${formbean.errors.cellphone}
		</p>
		<p>
			家庭电话<input type="text" name="home_phone"
				value="${formbean.home_phone}">${formbean.errors.home_phone}
		</p>
		<p>
			研究方向${formbean.errors.direction}<br />
			<c:forEach var="dir" items="${directionItems }">
				<input type="checkbox" name="pre" value="${dir}" ${fn:contains(formbean.direction,dir)?'checked':''}/>${dir} 
			</c:forEach>
		</p>
		<p>
			备注:<br />
			<textarea rows="5" cols="30" name="description">${formbean.description}</textarea>
			<input type="hidden" name="token" value="${sessionScope.token}">
		</p>
		<p>
			验证码<input type="text" name="checkcode"> <img border="1"
				src="${pageContext.request.contextPath }/servlet/CheckCodeImage" />
			${formbean.errors.checkcode}
		</p>
		<p>
			<input type="submit" value="提交" id="submit">
		</p>
	</form>
</body>
</html>
