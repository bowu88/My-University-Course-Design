package cn.edu.fjnu.gis11.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.fjnu.gis11.domain.User;
import cn.edu.fjnu.gis11.service.BusinessServiceImpl;

public class ContactsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�ж��Ƿ��¼
		if(request.getSession().getAttribute("user")==null){
			request.setAttribute("message_type","warning");
			request.setAttribute("message", "�Բ��𣬵�½��������ͨѶ¼��<a href='"+request.getContextPath()+"/servlet/LoginUIServlet'>������½</a> ���� <a href='"+request.getContextPath()+"/servlet/RegisterUIServlet'>����ע��</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		BusinessServiceImpl service=new BusinessServiceImpl();
		List<User> list=service.getAllUser();
		request.setAttribute("list", list);
		request.setAttribute("currentPage", "contacts");
		request.getRequestDispatcher("/WEB-INF/jsp/contacts.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
