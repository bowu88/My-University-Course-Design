package junit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import tk.xiangyang.dao.EarthquakeDaoImpl;
import tk.xiangyang.domain.Earthquake;

public class GetData {

	@Test
	public void openWeb() throws Exception {
		// String sr=sendPost("http://www.csi.ac.cn/csi/fuzzyQuery.action",
		// "queryValue="+URLEncoder.encode("仙游")+"&button=搜 索");
		// System.out.println(sr);
		EarthquakeDaoImpl dao=new EarthquakeDaoImpl();

		String url = "http://www.csi.ac.cn/csi/fuzzyQuery.action";
		String param = "queryValue=" + URLEncoder.encode("仙游")
				+ "&curPage=1&perPage=100";
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			int count = 0;
			while ((line = in.readLine()) != null) {
				if (line.contains("仙游") && line.contains("http")) {
					String key = "href=\"";
					String xyUrl = line.substring(
							line.indexOf(key) + key.length(),
							line.indexOf(">", line.indexOf("href=\"")));
					xyUrl = xyUrl.substring(0, xyUrl.length() - 1);

					// System.out.println(xyUrl);
					count++;
					Earthquake e=getInfoFromUrl(xyUrl);
					try{
						dao.add(e);
					}catch(Exception ex){
						System.out.println("失败");
					}
				}
			}
			System.out.println(count);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		
	}
	
	//解析字符串中的地震信息
//	public void readInfo(String info){
//		String location=DaoJunit.getString(info,"分","");
//		String time=info.substring(0,info.indexOf("endMark"));
//		double depth=DaoJunit.getDouble(info,"发生"+2,"级");
//		double magnitude=DaoJunit.getDouble(info,"震源深度","公里");
//		double x=DaoJunit.getDouble(info,"东经","度 ）");
//		double y=DaoJunit.getDouble(info,"北纬","度");
//		System.out.println(location+"\t"+time+"\t"+depth+"\t"+ magnitude+"\t"+x+"\t"+y);
//	}

	//从页面中抓取地震信息
	public static Earthquake getInfoFromUrl(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            String xianyou=null;
            String year=null;
            Earthquake e=new Earthquake();
            while ((line = in.readLine()) != null) {
            	if(line.contains("仙游")&&line.contains("级")){
                	e.setLocation(DaoJunit.getString(line, "分", "发生"));
                	e.setMagnitude(DaoJunit.getDouble(line, "发生", "级"));
                }else if(line.contains("subtime")&&line.contains("20")){
//            		if(xianyou!=null){
//            			year=line.substring(line.indexOf("20"),line.lastIndexOf("'"));
//            			String finalResult= year+"endMark"+xianyou.substring(xianyou.indexOf(">")+1);
//            			year=null;
//            			xianyou=null;
//            			return finalResult;
//            		}
                	String time=line.substring(line.indexOf("20"),line.lastIndexOf("'"));
                	e.setId(time);
                	e.setTime(time);
                }else if(line.contains("subStringLocationLatitude")&&!line.contains("function")){
                	String y=line.substring(line.indexOf("\"")+1,line.lastIndexOf("\""));
                	e.setY(Double.valueOf(y));
                }else if(line.contains("subStringLocationLongitude")&&!line.contains("function")){
                	String x=line.substring(line.indexOf("\"")+1,line.lastIndexOf("\""));
                	e.setX(Double.valueOf(x));
                }else if(line.contains("subShendu")&&!line.contains("function")){
                	String x=line.substring(line.indexOf("\"")+1,line.lastIndexOf("\""));
                	e.setDepth(Double.valueOf(x));
                	return e;
                }
                
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
