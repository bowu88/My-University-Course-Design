package tk.fjnugis.dangerserver.service;

import tk.fjnugis.dangerserver.model.Tag;

import java.util.List;

/**
 * Created by Xiangyang on 2015/1/11.
 */
public interface TagService {
    /**
     * 检查标签是否已存在
     * @param name 标签名
     * @return 是非添加成功
     */
    public boolean has(String name);

    /**
     * 创建标签
     * @param name
     */
    public void create(String name);

    /**
     * 标签使用次数增加一次
     * @param name 标签名
     */
    public void increase(String name);

    /**
     * 标签使用次数减少一次
     * @param name 标签名
     */
    public void decrease(String name);

    /**
     * 获取标签集合
     * @param begin 开始索引
     * @param count 标签最大数量
     * @param orderBy 排序条件
     * @param ascOrDesc 升序还是降序
     * @return 标签集合
     */
    public List<Tag> getList(int begin,int count,String orderBy,String ascOrDesc);

    /**
     * 根据标签名 模糊查询
     * @param name 标签名包含的词语
     * @param count 获取个数
     * @return 获取到底标签集合
     */
    public List<Tag> getTagsLike(String name,int count);

    /**
     * 更新标签库数据
     * @return 是否更新成功
     */
    public boolean update();
}
