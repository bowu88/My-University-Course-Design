package tk.fjnugis.dangerserver.util;

/**
 * Created by Xiangyang on 2015/1/16.
 */
public class ModelHelper {
    /**
     * 组装数组为字符串
     */
    private static ModelHelper instance=new ModelHelper();

    public String joinString(String[] arr,String str){
        StringBuilder sb=new StringBuilder();
        for(String a:arr){
            sb.append(a).append(str);
        }
        sb.delete(sb.length()-str.length(),sb.length());
        return sb.toString();
    }

    public static ModelHelper getInstance() {
        return instance;
    }
}
