package cn.edu.fjnu.gis11.web.UI;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.fjnu.gis11.utils.Items;
import cn.edu.fjnu.gis11.utils.TokenProccessor;

public class RegisterUIServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("currentPage", "register");
		String token=TokenProccessor.getInstance().makeToken();
		request.getSession().setAttribute("token", token);
		request.getSession().setAttribute("directionItems", Items.getDirection());
		request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
