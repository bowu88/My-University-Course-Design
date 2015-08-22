package tk.xiangyang.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tk.xiangyang.domain.Asylum;
import tk.xiangyang.service.BusinessService;

public class Asylum2JSONServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BusinessService service=new BusinessService();
		List<Asylum> list=service.getAllAsylums();
		
		StringBuilder json=new StringBuilder();
		json.append("{\"asylums\":[");
		for(Asylum a:list){
			json.append("{\"id\":\"").append(a.getId()).append("\",");
			json.append("\"name\":\"").append(a.getName()).append("\",");
			json.append("\"address\":\"").append(a.getAddress()).append("\",");
			json.append("\"classid\":\"").append(a.getClassid()).append("\",");
			json.append("\"area\":\"").append(a.getArea()).append("\",");
			json.append("\"people\":\"").append(a.getPeople()).append("\",");
			json.append("\"x\":\"").append(a.getX()).append("\",");
			json.append("\"y\":\"").append(a.getY()).append("\"},");
		}
		json.deleteCharAt(json.length()-1);
		json.append("]}");
		
		request.setAttribute("json", json.toString());
		request.getRequestDispatcher("/WEB-INF/jsp/json.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
