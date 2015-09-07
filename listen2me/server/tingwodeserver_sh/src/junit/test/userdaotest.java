package junit.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

import org.junit.Test;

import tk.tingwode.dao.UserDao;
import tk.tingwode.domain.User;
import tk.tingwode.domain.Voice;

public class UserDaoTest {
	
	@Test
	public void add(){
		User user=new User();
		user.setUsername("测试用户");
		user.setEmail("test@test.com");
		user.setPassword("123");
		user.setGender("男");
		user.setPhoto_url("sss");
		
		UserDao dao=new UserDao();
		dao.add(user);
	}
	
	@Test
	public void find(){
		UserDao dao=new UserDao();
		User user=dao.find("test2@test.com","123");
		System.out.print(user.getUsername());
	}
	
	@Test
	public void find2(){
		UserDao dao=new UserDao();
		User user=dao.find("2c91b905466aca4801466aca4ae20000");
		for(Voice voice:user.getVoices()){
			System.out.println(voice.getTitle());
		}
		System.out.print(user.getUsername());
	}
	
	@Test
	public void post() throws Exception{
        URL postUrl = new URL("http://192.168.1.109:8080/TingwodeServer_SH/web/User-login");
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
      
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection
                .getOutputStream());
        // The URL-encoded contend
        // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
        String content = "user.email=" + URLEncoder.encode("test@test.com", "UTF-8");
        content +="&password="+URLEncoder.encode("123", "UTF-8");;
        out.writeBytes(content);
        out.flush();
        out.close(); 
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null){
            System.out.println(line);
        }
      
        reader.close();
        connection.disconnect();
	}
	
	@Test
	public void testUploadFile() throws IOException{
		String filepath="C:\\Users\\Xiangyang\\Pictures\\愤怒的小鸟.jpg";
		File file=new File(filepath);
		if(!file.exists()){
			System.out.println("文件不存在");
			return;
		}
		
		URL url = new URL("http://localhost:8080/TingwodeServer_SH/web/User-add");  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setReadTimeout(10 * 10000000);  
        conn.setConnectTimeout(10 * 10000000);  
        conn.setDoInput(true); // 允许输入流  
        conn.setDoOutput(true); // 允许输出流  
        conn.setUseCaches(false); // 不允许使用缓存  
        conn.setRequestMethod("POST"); // 请求方式  
        conn.setRequestProperty("Charset", "utf-8"); // 设置编码  
        conn.setRequestProperty("connection", "keep-alive");  
        String BOUNDARY = UUID.randomUUID().toString();//边界标识 随机生成
        String LINE_END = "\r\n";
		conn.setRequestProperty("Content-Type", "multipart/form-data" + ";boundary=" + BOUNDARY); 
        
        OutputStream outputSteam = conn.getOutputStream();  
        DataOutputStream dos = new DataOutputStream(outputSteam);  
        StringBuffer sb = new StringBuffer();  
        sb.append("--");  
        sb.append(BOUNDARY);  
        sb.append(LINE_END);  
        /** 
         * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 
         * filename是文件的名字，包含后缀名的 比如:abc.png 
         */  

        sb.append("Content-Disposition: form-data; name=\"photo\"; filename=\""  
                + file.getName() + "\"" + LINE_END);  
        sb.append("Content-Type: application/octet-stream; charset=utf-8" + LINE_END);  
        sb.append(LINE_END);  
        dos.write(sb.toString().getBytes());  
        InputStream is = new FileInputStream(file);  
        byte[] bytes = new byte[1024];  
        int len = 0;  
        while ((len = is.read(bytes)) != -1) {  
            dos.write(bytes, 0, len);  
        }  
        is.close();  
        dos.write(LINE_END.getBytes());  
        
        //-------------添加普通字段----
        StringBuffer s = new StringBuffer();  
        s.append("--");  
        s.append(BOUNDARY);  
        s.append(LINE_END); 
        
        s.append("Content-Disposition: form-data; name=\"user.username\""+LINE_END);
        s.append(LINE_END);
        s.append("手动添加普通字段");
        s.append(LINE_END);
        dos.write(s.toString().getBytes());
        //-----------------
        byte[] end_data = ("--" + BOUNDARY + "--" + LINE_END)  
                .getBytes();  
        dos.write(end_data);
        dos.flush();  
        /** 
         * 获取响应码 200=成功 当响应成功，获取响应的流 
         */  
        int res = conn.getResponseCode();  
        if (res == 200) { 
        	InputStreamReader reader=new InputStreamReader(conn.getInputStream());  
        	char[] cbuf=new char[256];
            for(int i=0;(i=reader.read(cbuf))!=-1;){
            	System.out.print(new String(cbuf,0,i));
            }
        }  
	}
	
}
