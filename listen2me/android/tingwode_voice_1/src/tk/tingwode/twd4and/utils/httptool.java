package tk.tingwode.twd4and.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpTool {
	
	public static String addParams(Map<String,String> params){
		if(params.isEmpty())
			return "";
		StringBuffer sb=new StringBuffer("?");
		for(Map.Entry<String, String> en:params.entrySet()){
			sb.append(en.getKey()).append("=").append(en.getValue()).append("&");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	/**
	 * 构建post的参数
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String addPostParams(Map<String,String> params) throws UnsupportedEncodingException{
		if(params.isEmpty())
			return "";
		StringBuffer sb=new StringBuffer();
		for(Map.Entry<String, String> en:params.entrySet()){
			sb.append(en.getKey()).append("=").append(URLEncoder.encode(en.getValue(),"utf-8")).append("&");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	/**
	 * 由url构建post连接
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static HttpURLConnection getPostConn(String url) throws MalformedURLException, IOException{
		URL postUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
      
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	     
		return connection;
	}
	
	/**
	 * 获取文件上传的POST连接
	 * @param url 服务端url
	 * @param boundary 分隔字符串
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static HttpURLConnection getMultipartPostConn(String url,String boundary) throws MalformedURLException, IOException{
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();  
        conn.setReadTimeout(10 * 10000000);  
        conn.setConnectTimeout(10 * 10000000);  
        conn.setDoInput(true); // 允许输入流  
        conn.setDoOutput(true); // 允许输出流  
        conn.setUseCaches(false); // 不允许使用缓存  
        conn.setRequestMethod("POST"); // 请求方式  
        conn.setRequestProperty("Charset", "utf-8"); // 设置编码  
        conn.setRequestProperty("connection", "keep-alive"); 
        conn.setRequestProperty("Content-Type", "multipart/form-data" + ";boundary=" + boundary); 
        return conn;
	}
	
	/**
	 * 上传文件添加普通字段
	 * @param boundary 分割字符串
	 * @return
	 */
	public static String addPostParamsInMultipart(String boundary,Map<String,String> params){
		StringBuffer sb=new StringBuffer();
		for(Map.Entry<String, String> en:params.entrySet()){
			sb.append("--").append(boundary).append("\r\n");
			sb.append("Content-Disposition: form-data; name=\"").append(en.getKey()).append("\"").append("\r\n");
			sb.append("\r\n");
			sb.append(en.getValue()).append("\r\n");
		}
		return sb.toString();
	}
}
