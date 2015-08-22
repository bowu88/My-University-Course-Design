package cn.edu.fjnu.gis11.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.fjnu.gis11.webapp.graph.Group;
import cn.edu.fjnu.gis11.webapp.graph.StatisticsData;
import cn.edu.fjnu.gis11.webapp.graph.StrToNumException;

public class GraphServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			StatisticsData data=new StatisticsData(request.getParameter("data"));
			Group[] groups=data.getGroups();
			request.setAttribute("data", data);
			
			StringBuffer y_sb=new StringBuffer();
			StringBuffer x_sb=new StringBuffer();
			y_sb.append("[");
			x_sb.append("{");
			for(int i=0;i<groups.length;i++){
				y_sb.append("{x:"+i+",y:"+groups[i].getF()+"},");
				x_sb.append(i+":"+groups[i].getLower()+",");
			}
			y_sb.deleteCharAt(y_sb.length()-1);
			y_sb.append("]");
			x_sb.append(groups.length+":"+groups[groups.length-1].getCaps());
			x_sb.append("}");
			String data_arr_y=y_sb.toString();
			request.setAttribute("data_arr_y", data_arr_y);
			String data_arr_x=x_sb.toString();
			request.setAttribute("data_arr_x", data_arr_x);
			request.setAttribute("count",groups.length);
			request.getRequestDispatcher("/WEB-INF/jsp/graph_result.jsp").forward(request, response);
			
		} catch (StrToNumException e) {
			e.printStackTrace();
			request.setAttribute("message_type","error");
			request.setAttribute("message", "对不起，输入有误，请输入数字，以空格分隔。<a href='"+request.getContextPath()+"/servlet/GraphUIServlet'>返回</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
