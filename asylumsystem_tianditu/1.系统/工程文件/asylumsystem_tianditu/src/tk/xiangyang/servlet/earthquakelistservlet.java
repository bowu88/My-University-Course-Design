package tk.xiangyang.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tk.xiangyang.domain.Earthquake;
import tk.xiangyang.service.BusinessService;

public class EarthquakeListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BusinessService service=new BusinessService();
		List<Earthquake> list= service.getAllEarthquake();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/jsp/earthquake_list.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
