package tk.xiangyang.utils;

import java.util.UUID;

public class WebUtils {
	public static String getId(){
		return UUID.randomUUID().toString();
	}
}
