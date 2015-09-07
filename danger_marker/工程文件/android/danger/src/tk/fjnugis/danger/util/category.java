package tk.fjnugis.danger.util;

import java.util.*;

/**
 * Created by Xiangyang on 2014/11/29.
 */
public class Category {
    private static Map<String,List<String>> map=new HashMap<String,List<String>>();
    private static Category instance=new Category();
    static{
        addCategory("自然灾害","泥石流","山体滑坡","山体落石","悬崖","天坑");
        addCategory("户外设施","外露电缆","管道破裂","高空坠物","没有井盖的深井");
        addCategory("行车安全","事故多发区","急转弯","道路施工","道路塌陷","道路异物","道路积水");
        addCategory("意外伤害","野兽出没","抢劫多发区");
    }
    private Category(){}

    /**
     * 获取实例
     * @return Category的实例
     */
    public static Category getInstance(){return instance;}
    /**
     * 添加类别
     * @param category 一级类别
     * @param subCategorys 二级类别
     */
    public static void addCategory(String category,String ... subCategorys ){
        /*
        List<String> list=new ArrayList<String>();
        for(String subCategory:subCategorys){
            list.add(subCategory);
        }
        */
        map.put(category,Arrays.asList(subCategorys));
    }

    /**
     * 获取所有一级类别
     * @return 一级类别的Set集合
     */
    public Set<String> getCategorys(){
        return map.keySet();
    }

    /**
     * 获取二级类别
     * @param category 一级类名称
     * @return 该一级类下面的所有子类
     */
    public List<String> getSubCategory(String category){
        return map.get(category);
    }


}
