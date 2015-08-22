package cn.edu.fjnu.gis11.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.fjnu.gis11.webapp.huigui.SPT;

public class HuiguiServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Ystr=request.getParameter("HuiguiY");
		String[] Yarr=Ystr.trim().split(" +");
		double[] y=new double[Yarr.length];
		for(int i=0;i<Yarr.length;i++){
			y[i]=Double.parseDouble(Yarr[i]);
		}
		
		String[] HuiguiXs=request.getParameterValues("HuiguiX");
		double[][] x=new double[HuiguiXs.length][Yarr.length];
		for(int i=0;HuiguiXs==null||i<HuiguiXs.length;i++){
			String[] Xarr=HuiguiXs[i].trim().split(" +");
			for(int j=0;j<Yarr.length;j++){
				x[i][j]=Double.parseDouble(Xarr[j]);
			}
		}
		
		int i;
		double[] a = new double[HuiguiXs.length+1];
		double[] v = new double[HuiguiXs.length];
		double[] dt = new double[4];


		SPT.sqt2(x, y, HuiguiXs.length, Yarr.length, a, dt, v);
		for (i = 0; i < a.length; i++)
			System.out.println("a(" + i + ")=" + a[i]);
		System.out.println("q=" + dt[0] + "  s=" + dt[1] + "  r=" + dt[2]);
		for (i = 0; i < v.length; i++)
			System.out.println("v(" + i + ")=" + v[i]);
		System.out.println("u=" + dt[3]);
		System.out.println("f="+(dt[3]/2)/(dt[0]/(Yarr.length-2-1)));
		
		String fangcheng="";
		for(i=0;i<a.length-1;i++){
			fangcheng+=" + "+a[i]+" X"+(i+1);
		}
		fangcheng="Y = "+a[a.length-1]+" + "+fangcheng;
		
		request.setAttribute("HuiguiFangcheng",fangcheng);
		request.setAttribute("F", (dt[3]/2)/(dt[0]/(Yarr.length-2-1)));
		request.getRequestDispatcher("/WEB-INF/jsp/Huigui_result.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
