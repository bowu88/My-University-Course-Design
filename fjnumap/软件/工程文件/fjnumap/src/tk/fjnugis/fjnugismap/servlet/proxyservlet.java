package tk.fjnugis.fjnugismap.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//代理
public class ProxyServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getParameter("url");
		if (url == null)
			return;
		StringBuffer sb = new StringBuffer();
		// Map<String,String> map= request.getParameterMap();
		// Set<Map.Entry<String, String>> set=map.entrySet();
		// for(Map.Entry<String, String> et:set){
		// String kv=et.getKey()+"="+et.getValue();
		// System.out.println(kv);
		// sb.append(kv).append("&");
		// }
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String paramName = (String) e.nextElement();
			if(paramName.equals("url")){
				continue;
			}
			String paramValue = new String(request.getParameter(paramName).getBytes("ISO8859-1"),"UTF-8");
			//System.out.println(paramValue);
			sb.append(paramName).append("=").append(URLEncoder.encode(paramValue,"UTF-8")).append("&");
		}
		String params=null;
		if(sb.length()>0){
			params = sb.deleteCharAt(sb.length() - 1).toString();
		}
//		response.setCharacterEncoding("UTF-8");
//		response.setHeader("content-type", "text/html;charset=UTF-8");
		// 添加支持跨域访问的响应头
//		response.setHeader("Access-Control-Allow-Credentials",
//				"Access-Control-Allow-Credentials: true");
		// PrintWriter writer=response.getWriter();

		// BufferedReader in = null;
		BufferedInputStream in = null;
		try {
			String urlName = url;
			if(params!=null){
				if (urlName.contains("?")) {
					urlName += params;
				} else {
					urlName += "?" + params;
				}
			}
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", request.getHeader("accept"));
//			System.out.println(request.getHeader("accept"));
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 建立实际的连接
			conn.connect();
//			if(urlName.contains("image/png")){
//				response.setContentType("image/png");
//				response.setHeader("content-type", "image/png");
//			}
			response.setContentType(conn.getContentType());
//			System.out.println(urlName);
			

			// 定义BufferedReader输入流来读取URL的响应
			// in = new BufferedReader(
			// new InputStreamReader(conn.getInputStream(),"utf-8"));
			// String line;
			// while ((line = in.readLine()) != null) {
			// //System.out.println(line);
			// writer.write(line);
			// writer.flush();
			// }
			in = new BufferedInputStream(conn.getInputStream());
			byte[] buf = new byte[1024];
			for (int len = 0; (len = in.read(buf)) > 0;) {
				response.getOutputStream().write(buf,0,len);
			}
		} catch (Exception ex) {
			System.out.println("发送GET请求出现异常！" + ex);
			ex.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
