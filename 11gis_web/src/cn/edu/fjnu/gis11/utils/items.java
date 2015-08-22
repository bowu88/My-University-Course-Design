package cn.edu.fjnu.gis11.utils;

import java.util.ArrayList;
import java.util.List;

public class Items {
	private static List<String> direction=new ArrayList();
	
	static{
		direction.add("Ò£¸Ð");
		direction.add("GIS");
		direction.add("GPS");
		direction.add("¿ª·¢");
	}
	public static List<String> getDirection() {
		return direction;
	}
	public static void addDirection(String myDirection){
		direction.add(myDirection);
	}
}
