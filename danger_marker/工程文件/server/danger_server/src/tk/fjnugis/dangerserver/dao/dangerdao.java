package tk.fjnugis.dangerserver.dao;

import tk.fjnugis.dangerserver.model.Danger;

import java.util.List;
import java.util.Map;

/**
 * Created by Xiangyang on 2015/1/8.
 */
public interface DangerDao {
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
     * 根据条件获取只包含坐标的信息
     * @param category
     * @param subCategory
     * @param tag
     * @return
     */
    List<Map<String,Double>> getLngLatList(String category,String subCategory,String tag);
    /**
     * 获取只包含坐标信息的集合
     * @return
     */
    List<Map<String,Double>> getLngLatList();

    /**
     * 更新安全隐患信息
     * @param danger
     */
    void update(Danger danger);

    /**
     * 根据标签获取数据
     * @param tag 标签
     * @return
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
