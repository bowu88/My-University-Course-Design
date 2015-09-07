package tk.tingwode.twd4and.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import tk.tingwode.twd4and.domain.UserJO;
import tk.tingwode.twd4and.utils.Global;
import tk.tingwode.twd4and.utils.HttpTool;
import android.graphics.Bitmap;

import com.google.gson.Gson;

public class BusinessService {

	/**
	 * 用户登录
	 * @param email 注册邮箱
	 * @param password 登录密码
	 * @return
	 * @throws Exception
	 */
	public UserJO login(String email, String password) throws Exception {
		String url = Global.serverUrl+"/web/User-login";
		URL postUrl = new URL(url);
        HttpURLConnection connection = HttpTool.getPostConn(url);
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection
                .getOutputStream());
        String content = "user.email=" + URLEncoder.encode(email, "UTF-8") +"&user.password="+URLEncoder.encode(password, "UTF-8");;
        out.writeBytes(content);
        out.flush();
        out.close(); 
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		try{
			if (connection.getResponseCode() == 200) {
				Gson gson = Global.gson;
				UserJO user = gson.fromJson(reader,UserJO.class);
				return user;
			} else {
				throw new Exception("获取用户信息失败:"+connection.getResponseCode());
			}
		}finally{
			reader.close();
        	connection.disconnect();
		}
	}
	
	/**
	 * 用户注册
	 * @param photo 用户头像
	 * @param params 用户信息
	 * @throws Exception 
	 */
	public void regist(Bitmap photo,UserJO user) throws Exception{
		
		String url= Global.serverUrl+"/web/User-add";
        /*HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();  
        conn.setReadTimeout(10 * 10000000);  
        conn.setConnectTimeout(10 * 10000000);  
        conn.setDoInput(true); // 允许输入流  
        conn.setDoOutput(true); // 允许输出流  
        conn.setUseCaches(false); // 不允许使用缓存  
        conn.setRequestMethod("POST"); // 请求方式  
        conn.setRequestProperty("Charset", "utf-8"); // 设置编码  
        conn.setRequestProperty("connection", "keep-alive");  */
        String BOUNDARY = UUID.randomUUID().toString();//边界标识 随机生成
        String LINE_END = "\r\n";
        HttpURLConnection conn =HttpTool.getMultipartPostConn(url, BOUNDARY);
//		conn.setRequestProperty("Content-Type", "multipart/form-data" + ";boundary=" + BOUNDARY); 
        
        OutputStream outputSteam = conn.getOutputStream();  
        DataOutputStream dos = new DataOutputStream(outputSteam);  
        //照片
        StringBuffer sb = new StringBuffer();  
        sb.append("--");  
        sb.append(BOUNDARY);  
        sb.append(LINE_END);  
        sb.append("Content-Disposition: form-data; name=\"photo\"; filename=\""  
                + "faceImage.jpg" + "\"" + LINE_END);  
        sb.append("Content-Type: application/octet-stream; charset=utf-8" + LINE_END);  
        sb.append(LINE_END);  
        dos.write(sb.toString().getBytes());  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos); 
        dos.write(baos.toByteArray());
        dos.write(LINE_END.getBytes());  
        //-------------添加普通字段----
        Map<String, String> params=new HashMap<String, String>();
        params.put("user.email", user.email);
        params.put("user.password", user.password);
        params.put("user.username", user.username);
        params.put("user.gender", user.gender);
		dos.write(HttpTool.addPostParamsInMultipart(BOUNDARY, params).getBytes());
        //-----------------
        byte[] end_data = ("--" + BOUNDARY + "--" + LINE_END)  
                .getBytes();  
        dos.write(end_data);
        dos.flush(); 
        InputStreamReader reader=null;
        int res = conn.getResponseCode();  
        try{
	        if (res == 200) { 
	        	reader=new InputStreamReader(conn.getInputStream());  
	        	StringBuilder sbuilder=new StringBuilder();
	        	char[] cbuf=new char[256];
	            for(int i=0;(i=reader.read(cbuf))!=-1;){
	            	sbuilder.append(cbuf,0,i);
	            }
	            JSONObject jo=new JSONObject(sbuilder.toString());
	            jo.getString("message");
	        }
        }finally{
        	dos.close();
        	if(reader!=null)
        		reader.close();
        }
	}
}
