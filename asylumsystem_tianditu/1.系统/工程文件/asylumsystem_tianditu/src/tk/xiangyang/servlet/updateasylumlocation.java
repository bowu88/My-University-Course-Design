package tk.xiangyang.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tk.xiangyang.domain.Asylum;
import tk.xiangyang.service.BusinessService;

public class UpdateAsylumLocation extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id=request.getParameter("id");
		String x=request.getParameter("x");
		String y=request.getParameter("y");
		BusinessService service=new BusinessService();
		try{
			Asylum asylum=service.getAsylum(id);
			asylum.setX(Double.valueOf(x));
			asylum.setY(Double.valueOf(y));
			service.updateAsylum(asylum);
			response.getWriter().write("succeed");
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("failed");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
