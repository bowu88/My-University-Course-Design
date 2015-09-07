package tk.fjnugis.danger.util;

import tk.fjnugis.danger.model.Danger;

import java.text.DecimalFormat;

/**
 * Created by Xiangyang on 2015/1/7.
 */
public class DangerInfoHelper {

    /**
     * 获取经过处理的标题
     * @param danger
     * @return
     */
    public static String getTitle(Danger danger){
        return getTitle(danger,12);
    }

    public static String getTitle(Danger danger,int lenth){
        if(danger==null)
            return null;
        if(lenth<0)
            lenth=0;
        String title=danger.getName();
        if(title==null||title.equals(""))
            title="无标题";
        if(title!=null&&title.length()>lenth)
            title=title.substring(0,lenth)+"…";
        return title;
    }

    /**
     * 获取经过处理的等级
     * @param danger
     * @return
     */
    public static Float getRank(Danger danger){
        if(danger==null)
            return 0f;
        Float rank=danger.getRank();
        if(rank<0) {
            rank = 0f;
        }else if(rank>5){
            rank=5f;
        }
        String rankStr=new DecimalFormat("#.#").format(rank);
        return Float.parseFloat(rankStr);
    }

    /**
     * 获取经过处理的类别
     * @param danger
     * @return
     */
    public static String getCategory(Danger danger){
        if(danger==null)
            return null;
        String category=danger.getCategory();
        String subCategory=danger.getSubCategory();
        if(category==null||category.equals("")){
            return "";
        }
        return danger.getCategory()+">"+danger.getSubCategory();
    }

    public static String getLatLng(Danger danger){
        StringBuilder sb = new StringBuilder();
        double lon=danger.getLongitude();
        double lat=danger.getLatitude();
        if (lon > 0) {
            sb.append("东经：").append(lon).append("度\n");
        } else {
            sb.append("西经：").append(-lon).append("度\n");
        }
        if (lat > 0) {
            sb.append("北纬：").append(lat).append("度\n");
        } else {
            sb.append("南纬：").append(-lat).append("度\n");
        }
        if(danger.getAccuracy()!=null)
            sb.append("定位精度：").append(danger.getAccuracy());
        return sb.toString();
    }

    /**
     * 获取处理后的地址信息
     * @param danger
     * @return
     */
    public static String getAddress(Danger danger) {
        String address;
        if(danger.getProvince()==null)
            address="";
        else
            address=danger.getProvince()+danger.getCity()+danger.getDistrict()+danger.getStreet();
        return address;
    }

    /**
     * 获取图片地址
     * @param danger
     * @return
     */
    public static String[] getPictures(Danger danger) {
        String pictures_str=danger.getPictures();
        if(pictures_str==null)
            return null;
        String[] arr = pictures_str.split(" ");
        String root=HttpHelper.ROOT+"/file/image?imagePath=";
        for(int i=0;i<arr.length;i++){
            String s=arr[i];
            arr[i]=HttpHelper.ROOT+"/file/image?imagePath="+s;
        }
        return arr;
    }

    public static String[] getTags(Danger danger) {
        String tagStr=danger.getTags();
        if(tagStr==null)
            return null;
        return tagStr.split(" ");
    }

    /**
     * 描述距离
     * @param distance  距离，单位：米
     * @return  距离的描述
     */
    public static String getDistance(double distance) {
        StringBuilder sb=new StringBuilder();
        int distanceInt;
        if(distance>1000) {
            distanceInt = (int) (distance / 1000);
            sb.append(distanceInt).append("公里");
        }else if (distance>=0){
            distanceInt=(int)distance;
            sb.append(distanceInt).append("米");
        }else {
            return null;
        }
        return sb.toString();
    }
}
