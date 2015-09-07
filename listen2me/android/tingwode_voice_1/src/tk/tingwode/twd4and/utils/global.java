package tk.tingwode.twd4and.utils;

import tk.tingwode.twd4and.domain.UserJO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Global {
//	public static final String serverUrl="http://10.0.2.2:8080/TingwodeServer_SH";
//	public static final String serverUrl="http://192.168.1.106:8080/TingwodeServer_SH";
	public static final String serverUrl="http://192.168.173.1:8080/TingwodeServer_SH";
	public static UserJO userjo;
	
	public static Gson gson= new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
}
