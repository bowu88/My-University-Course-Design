package cn.edu.fjnu.gis11.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.fjnu.gis11.domain.User;
import cn.edu.fjnu.gis11.service.BusinessServiceImpl;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email=null;
		String password=null;
		String c_email=(String)request.getAttribute("c_email");
		String c_password=(String)request.getAttribute("c_password");
		if(c_email!=null&&c_password!=null){
			email=c_email;
			password=c_password;
		}else{
			email=request.getParameter("email");
			password=request.getParameter("password");
		}
		BusinessServiceImpl service=new BusinessServiceImpl();
		User user=service.loginUser(email, password);
		if(user==null){
			request.setAttribute("message_type","error");
			request.setAttribute("message", "登陆失败,用户名或密码错误");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		request.getSession().setAttribute("user", user);
		if(request.getParameter("remember")!=null){
			Cookie usercookie=new Cookie("c_user", user.getEmail()+"=="+user.getPassword());
			usercookie.setMaxAge(90*24*60*60);
			response.addCookie(usercookie);
		}
		request.setAttribute("message_type","success");
		request.setAttribute("message", "登陆成功，欢迎您，"+user.getName()+" "+"页面将在5秒后自动跳转<meta http-equiv='Refresh' content='5;url="+request.getContextPath()+"/index.jsp'/>");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
