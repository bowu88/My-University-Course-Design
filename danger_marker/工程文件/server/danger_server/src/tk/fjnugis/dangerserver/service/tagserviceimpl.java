package tk.fjnugis.dangerserver.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.fjnugis.dangerserver.dao.TagDao;
import tk.fjnugis.dangerserver.model.Tag;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Xiangyang on 2015/1/11.
 */
@Component("tagService")
public class TagServiceImpl implements TagService {
    private TagDao tagDao;
    private Logger logger=Logger.getLogger(TagServiceImpl.class);

    @Transactional
    @Override
    public boolean has(String name) {
        Tag tag = tagDao.get(name);
        if (tag != null)
            return true;
        else
            return false;
    }

    @Transactional
    @Override
    public void create(String name) {
        Tag tag=new Tag();
        tag.setName(name);
        tag.setCount(1);
        tag.setUpdateTime(new Date());
        tagDao.add(tag);
    }

    @Transactional
    @Override
    public void increase(String name) {
        Tag tag=tagDao.get(name);
        tag.setCount(tag.getCount()+1);
        tagDao.update(tag);
    }

    @Transactional
    @Override
    public void decrease(String name) {
        Tag tag=tagDao.get(name);
        if(tag.getCount()>1) {
            tag.setCount(tag.getCount() - 1);
            tagDao.update(tag);
        }else{
            tagDao.delete(name);
        }
    }

    @Transactional
    @Override
    public List<Tag> getList(int begin, int count, String orderBy, String ascOrDesc) {
        logger.debug("调用 TagService.getList\n开始索引："+begin+"\t个数:"+count+"\t排序条件："+orderBy+"\t升序降序："+ascOrDesc);
        return tagDao.getList(begin,count,orderBy,ascOrDesc);
    }

    @Transactional //未使用
    @Override
    public List<Tag> getTagsLike(String name, int count) {
        logger.debug("调用 TagServic.getTagsLike.\t获取"+count+"个标签名包含"+name+"的标签");
        return tagDao.getListByName(name,count);
    }

    @Transactional
    @Override
    public boolean update() {
        try{
            tagDao.updateAll();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //----------- IoC --------------
    @Resource
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }
}
