package tk.tingwode.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Global {
	public static Gson gson= new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
}
