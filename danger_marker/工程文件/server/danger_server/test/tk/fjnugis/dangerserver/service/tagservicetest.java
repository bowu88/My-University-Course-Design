package tk.fjnugis.dangerserver.service;

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

/**
 * Created by Xiangyang on 2015/1/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class TagServiceTest {
    private Tag tag;
    private TagService tagService;
    @Before
    public void before(){
        tag = new Tag();
        tag.setName("标签1");
        tag.setCount(0);
    }

    @Test
    public void testHas(){
        String name=tag.getName();
        boolean has= tagService.has(name);
        Assert.assertEquals(has,true);
    }

    @Test
    public void testCreate(){
        String name="测试create";
        Assert.assertEquals(tagService.has(name),false);
        tagService.create(name);
        Assert.assertEquals(tagService.has(name),true);
    }

    @Test
    public void testIncrease(){
        String name="测试create";
        tagService.increase(name);
    }

    @Resource
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}
