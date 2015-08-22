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
		//验证表单是否重复提交
		if(isToken(request)){
			request.setAttribute("message_type","warning");
			request.setAttribute("message", "对不起，请不要重复提交。<a href='"+request.getContextPath()+"/servlet/RegisterUIServlet'>返回</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		RegisterFormBean formbean=WebUtils.request2Bean(request, RegisterFormBean.class);
		
		//将参数中的direction连接起来
		String dirs[]=request.getParameterValues("pre");
		if(dirs!=null){
			StringBuffer sb=new StringBuffer();
			for(String dir:dirs){
				sb.append(dir+"、");
			}
			sb.deleteCharAt(sb.length()-1);
			formbean.setDirection(sb.toString());
		}
		
		if(!formbean.validata((String)request.getSession().getAttribute("checkcode"))){
			request.setAttribute("formbean", formbean);
			request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
			return;
		}
		//把表单是数据填充到JavaBean里
		User user=new User();
		try {
			
			//注册字符串到日期的转换器
			Converter convertor=new DateConverter();
			ConvertUtils.register(convertor, Date.class);
			BeanUtils.copyProperties(user, formbean);
			user.setId(WebUtils.makeId());
			BusinessServiceImpl service=new BusinessServiceImpl();
			service.registerUser(user);

			request.getSession().removeAttribute("token");
			request.setAttribute("message_type","success");
			request.setAttribute("message", "注册成功! 将在5秒后自动跳转到登陆页面。<meta http-equiv='Refresh' content='5;url="+request.getContextPath()+"/servlet/LoginUIServlet'/><a href='"+request.getContextPath()+"/servlet/LoginUIServlet'>马上登陆!</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}catch(UserExistException e){
			formbean.getErrors().put("email", "对不起，邮箱已被使用");
			request.setAttribute("formbean", formbean);
			request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();//在后台记录异常
			request.setAttribute("message_type","error");
			request.setAttribute("message", "对不起，注册失败。<a href='"+request.getContextPath()+"/servlet/RegisterUIServlet'>返回</a>"+e);
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
