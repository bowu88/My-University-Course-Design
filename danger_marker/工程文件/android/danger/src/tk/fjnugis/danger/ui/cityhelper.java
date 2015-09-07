package tk.fjnugis.danger.ui;

/**
 * Created by Xiangyang on 2015/4/14.
 */
public class CityHelper {
    /**
     * 将字节数转为M
     * @param size
     * @return
     */
    public static String formatDataSize(int size) {
        String ret = "";
        if (size < (1024 * 1024)) {
            ret = String.format("%dK", size / 1024);
        } else {
            ret = String.format("%.1fM", size / (1024 * 1024.0));
        }
        return ret;
    }
}
