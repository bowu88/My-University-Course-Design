package tk.tingwode.twd4and.utils;

import android.os.Environment;
/**
 * 设置头像相关类
 * @author Xiangyang
 *
 */
public class Tools {
	/**检查是否存在SDCar*/
	public static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
}
