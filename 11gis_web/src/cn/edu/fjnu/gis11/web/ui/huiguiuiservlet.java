package cn.edu.fjnu.gis11.web.UI;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HuiguiUIServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String HuiguiK=request.getParameter("HuiguiK");
		if(HuiguiK!=null&&HuiguiK.trim().length()!=0){
			try{
				int k=Integer.parseInt(HuiguiK.trim());
				request.getSession().setAttribute("HuiguiK", k);
			}catch(Exception e){
				request.setAttribute("message_type","error");
				request.setAttribute("message", "对不起，输入有误。<a href='"+request.getContextPath()+"/servlet/HuiguiUIServlet'>返回</a>");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}
		}else{
			request.getSession().setAttribute("HuiguiK", 2);
		}
		request.getRequestDispatcher("/WEB-INF/jsp/huigui.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
