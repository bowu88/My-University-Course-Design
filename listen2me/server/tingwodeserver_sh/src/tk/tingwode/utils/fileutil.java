package tk.tingwode.utils;

import java.io.File;

public class FileUtil {
	public static void delete(String path){
		if(path==null){
			return;
		}
		File f=new File(path);
		if(f.exists()&&f.isFile()){
			f.delete();
		}
	}
}
