package tk.fjnugis.danger.util;

import android.graphics.Bitmap;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xiangyang on 2014/12/1.
 */
public class HttpHelper {
    //默认的服务端根路径
    public static String ROOT="http://192.168.191.1:8080/danger";
    //服务端根路径在配置文件中存储的KEY
    public static final String ROOT_PREFERENCE_KEY="tk.fjnugis.danger.util.httphelper.root";
    //public static String ROOT="http://192.168.1.102:8080/danger";
    public static Gson GSON=new Gson();
    public static ImageLoader.ImageCache imageCache=new MyImageCache();

    /**
     * 构建post请求所需的参数的Map集合
     * @param keyHead Map的Key的会以"keyHead."开头
     * @param object 构建Map的POJO类
     * @return 装有参数的Map
     */
    public static Map<String,String> makeParamsMap(String keyHead,Object object) {
        if(object==null)
            return null;
        if(keyHead==null)
            keyHead="";
        keyHead+=".";
        Map<String,String> params=new HashMap<String,String>();
        //使用反射获取所有字段
        for(Field field:object.getClass().getDeclaredFields()){
            try {
                String fildName=field.getName();
                String methodName="get"+Character.toUpperCase(fildName.charAt(0))+fildName.substring(1);
                Method method= object.getClass().getMethod(methodName);
                Object value= method.invoke(object);
                String stringValue;
                if(value==null||value.toString().equals("")) {
                    continue;
                }
                //结果为时期型，则进行转换
                if(method.getReturnType().equals(Class.forName("java.util.Date"))){
                    Date date=(Date)value;
                    stringValue=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                }else{
                    stringValue=value.toString();
                }
                params.put(keyHead + fildName,stringValue);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return params;
    }

    /**
     * url编码
     * @param str   要编码的字符串
     * @return      编码后的字符串
     */
    public static String urlEncode(String str){
        try {
            return URLEncoder.encode(str,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("url编码错误");
        }
    }
}
