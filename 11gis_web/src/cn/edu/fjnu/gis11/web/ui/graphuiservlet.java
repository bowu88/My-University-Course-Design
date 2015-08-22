package cn.edu.fjnu.gis11.web.UI;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GraphUIServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("message_type","error");
		request.setAttribute("message", "对不起，输入有误。<a href='"+request.getContextPath()+"/servlet/HuiguiUIServlet'>返回</a>");
		request.getRequestDispatcher("/WEB-INF/jsp/graph.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
