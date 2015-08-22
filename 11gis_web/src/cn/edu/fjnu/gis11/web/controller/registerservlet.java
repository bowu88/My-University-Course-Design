package cn.edu.fjnu.gis11.web.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import cn.edu.fjnu.gis11.domain.User;
import cn.edu.fjnu.gis11.exception.UserExistException;
import cn.edu.fjnu.gis11.service.BusinessServiceImpl;
import cn.edu.fjnu.gis11.utils.DateConverter;
import cn.edu.fjnu.gis11.utils.WebUtils;
import cn.edu.fjnu.gis11.web.formbean.RegisterFormBean;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��֤���Ƿ��ظ��ύ
		if(isToken(request)){
			request.setAttribute("message_type","warning");
			request.setAttribute("message", "�Բ����벻Ҫ�ظ��ύ��<a href='"+request.getContextPath()+"/servlet/RegisterUIServlet'>����</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		RegisterFormBean formbean=WebUtils.request2Bean(request, RegisterFormBean.class);
		
		//�������е�direction��������
		String dirs[]=request.getParameterValues("pre");
		if(dirs!=null){
			StringBuffer sb=new StringBuffer();
			for(String dir:dirs){
				sb.append(dir+"��");
			}
			sb.deleteCharAt(sb.length()-1);
			formbean.setDirection(sb.toString());
		}
		
		if(!formbean.validata((String)request.getSession().getAttribute("checkcode"))){
			request.setAttribute("formbean", formbean);
			request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
			return;
		}
		//�ѱ���������䵽JavaBean��
		User user=new User();
		try {
			
			//ע���ַ��������ڵ�ת����
			Converter convertor=new DateConverter();
			ConvertUtils.register(convertor, Date.class);
			BeanUtils.copyProperties(user, formbean);
			user.setId(WebUtils.makeId());
			BusinessServiceImpl service=new BusinessServiceImpl();
			service.registerUser(user);

			request.getSession().removeAttribute("token");
			request.setAttribute("message_type","success");
			request.setAttribute("message", "ע��ɹ�! ����5����Զ���ת����½ҳ�档<meta http-equiv='Refresh' content='5;url="+request.getContextPath()+"/servlet/LoginUIServlet'/><a href='"+request.getContextPath()+"/servlet/LoginUIServlet'>���ϵ�½!</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}catch(UserExistException e){
			formbean.getErrors().put("email", "�Բ��������ѱ�ʹ��");
			request.setAttribute("formbean", formbean);
			request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();//�ں�̨��¼�쳣
			request.setAttribute("message_type","error");
			request.setAttribute("message", "�Բ���ע��ʧ�ܡ�<a href='"+request.getContextPath()+"/servlet/RegisterUIServlet'>����</a>"+e);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	
	private boolean isToken(HttpServletRequest request) {
		String server_token=(String) request.getSession().getAttribute("token");
		if(server_token==null){
			return true;
		}
		String client_token=request.getParameter("token");
		if(client_token==null){
			return true;
		}
		if(!client_token.equals(server_token)){
			return true;
		}
		return false;
	}

}
