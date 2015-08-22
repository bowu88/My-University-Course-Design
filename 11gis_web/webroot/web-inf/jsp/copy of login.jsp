<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登陆</title>
  </head>
  <body>
	 <form action="${pageContext.request.contextPath}/servlet/LoginServlet" method="post">
	   <p>登陆邮箱<input type="text" name="email"/></p>
	   <p>登陆密码<input type="password" name="password"/></p>
	   <input type="submit" value="登陆">
	 </form>
  </body>
</html>
