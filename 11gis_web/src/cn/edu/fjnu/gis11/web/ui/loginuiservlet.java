package cn.edu.fjnu.gis11.web.UI;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginUIServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cookie cookies[]=request.getCookies();
		for(int i=0;i<cookies.length&&cookies!=null;i++){
			if(cookies[i].getName().equals("c_user")){
				String[] infos=cookies[i].getValue().split("==");
				request.setAttribute("c_email", infos[0]);
				request.setAttribute("c_password",infos[1]);
				request.getRequestDispatcher("/servlet/LoginServlet").forward(request, response);
				return;
			}
		}
		
		request.setAttribute("currentPage","login");
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
