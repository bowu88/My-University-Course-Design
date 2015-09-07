package tk.fjnugis.dangerserver.dao;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import tk.fjnugis.dangerserver.model.Tag;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Xiangyang on 2015/1/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class TagDaoTest {
    private TagDao tagDao;
    private Tag tag;
    private Logger logger = Logger.getLogger(TagDaoTest.class);

    @Before
    public void before() {
        tag = new Tag();
        tag.setName("标签1");
        tag.setCount(0);
    }

    @Test
    public void testAdd() {
        tagDao.add(tag);
    }

    @Test
    public void testGet() {
        Tag getTag = tagDao.get(tag.getName());
        Assert.assertEquals(getTag.getCount(), tag.getCount());
    }

    @Test
    public void testListByName(){
        List<Tag> list=tagDao.getListByName("标",4);
        StringBuilder sb=new StringBuilder("\n获取到的标签:\n");
        for(Tag tag:list){
            sb.append(tag.toString()+"\n");
        }
        logger.info(sb.toString());
    }

    @Resource
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }
}
