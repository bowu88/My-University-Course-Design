package tk.fjnugis.dangerserver.service;

import tk.fjnugis.dangerserver.model.Danger;

import java.util.List;
import java.util.Map;

/**
 * Created by Xiangyang on 2014/11/20.
 */
public interface DangerService {

    /**
    添加危险信息
     */
    Integer add(Danger danger);

    /**
     * 根据id获取安全隐患信息
     * @param id 安全隐患信息的id
     * @return 安全隐患信息
     */
    Danger get(int id);

    /**
     * 检测id是非存在
     * @param id id
     * @return true为存在,false为不存在
     */
    boolean has(int id);
    /**
     * 获取所有安全隐患信息
     * @return 所有安全隐患信息的list
     */
    List<Danger> getAll();

    /**
     * 获取指定条数的信息
     *
     * @param startIndex 开始的索引，从0开始
     * @param count     信息条数
     * @param longitude  当前位置的经度
     *@param latitude   当前位置的纬度
     * @return
     */
    List<Danger> getList(int startIndex, int count, Double longitude, Double latitude);

    /**
     * 获取只包含坐标的集合
     * @return
     */
    List<Map<String,Double>> getLngLatList();

    /**
     * 获取只包含坐标的集合
     * @param category      一级类别
     * @param subCategory   二级类别
     * @param tag           标签
     * @return
     */
    List<Map<String,Double>> getLngLatList(String category, String subCategory, String tag);

    /**
     * 添加图片字段
     * @param id 安全隐患信息的id
     * @param pictures 图片地址
     */
    void addPicture(Integer id, String pictures);

    /**
     * 根据标签名获取安全隐患数据
     * @param tag   标签
     * @return  安全隐患信息集合
     */
    List<Danger> getListByTag(int startIndex, int count, Double longitude, Double latitude,String tag);

    /**
     * 获取矩形范围内的安全信息
     * @param lt_longitude 左上角经度
     * @param lt_latitude   左上角纬度
     * @param rb_longitude  右下角经度
     * @param rb_latitude   右下角纬度
     * @return
     */
    List<Danger> getByBoundary(double lt_longitude, double lt_latitude, double rb_longitude, double rb_latitude);
}
