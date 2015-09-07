package tk.fjnugis.dangerserver.dao;

import tk.fjnugis.dangerserver.model.Tag;

import java.util.List;

/**
 * Created by Xiangyang on 2015/1/10.
 */
public interface TagDao {
    /**
     * 增加记录
     * @param tag 要增加的标签
     */
    public void add(Tag tag);

    /**
     * 删除记录
     * @param name 要删除的标签的名称
     */
    public void delete(String name);

    /**
     * 更新记录
     * @param tag 更新的标签对象
     */
    public void update(Tag tag);

    /**
     * 获取标签
     * @param name 标签名
     * @return 标签对象
     */
    public Tag get(String name);
    /**
     * 获取List
     * @param startIndex 开始的索引
     * @param count 获取的数量
     * @return 获取到的标签集合
     */
    public List<Tag> getList(int startIndex,int count,String orderBy,String ascOrDesc);

    /**
     * 获取名称包含指定词语的标签
     * @param name 名称包含的词语
     * @param count 个数
     * @return 获取到的标签集合
     */
    public List<Tag> getListByName(String name, int count);

    /**
     * 更新所有标签的信息
     */
    void updateAll();
}
