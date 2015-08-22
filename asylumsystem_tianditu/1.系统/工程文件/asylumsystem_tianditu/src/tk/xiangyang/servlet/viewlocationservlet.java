package tk.xiangyang.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewLocationServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String x=request.getParameter("x");
		String y=request.getParameter("y");
		String depth=request.getParameter("depth");
		try{
			Double.valueOf(x);
			Double.valueOf(y);
		}catch(Exception e){
			response.getWriter().write("params is wrong!");
			return;
		}
		request.setAttribute("depth",depth );
		request.setAttribute("magnitude", request.getParameter("magnitude"));
		request.setAttribute("time", request.getParameter("time"));
		request.setAttribute("lon", x);
		request.setAttribute("lat", y);
		request.getRequestDispatcher("/WEB-INF/jsp/locationMarkerMap.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
