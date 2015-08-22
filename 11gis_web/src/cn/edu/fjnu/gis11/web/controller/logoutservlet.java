package cn.edu.fjnu.gis11.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("user");
		Cookie cookies[]=request.getCookies();
		for(int i=0;cookies!=null&&i<cookies.length;i++){
			if(cookies[i].getName().equals("c_user")){
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}
		}
		request.setAttribute("message_type","info");
		request.setAttribute("message", "注销成功! 页面将在5秒后自动跳转<meta http-equiv='Refresh' content='5;url="+request.getContextPath()+"/index.jsp'/>");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
