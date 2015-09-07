package tk.fjnugis.dangerserver.service;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import tk.fjnugis.dangerserver.model.Danger;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Xiangyang on 2014/11/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DangerServiceTest {
    public static Danger danger;
    public DangerService service;
    public static Logger logger = Logger.getLogger(DangerServiceTest.class);

    @BeforeClass
    public static void beforeClass() {
        danger = new Danger();
        danger.setName("危险信息");
        danger.setDescription("危险信息的描述，可能会很长很长。。。");
        danger.setRank(4.44444f);
        danger.setLatitude(26.2);
        danger.setLongitude(119.2);
        //danger.setAddress("福建省 闽侯县 福建师范大学旗山校区");
        danger.setAddressDescritpion("B1-1 613");
    }

    @Test
    public void testAdd() {
        service.add(danger);
    }

    @Test
    public void testGet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Danger danger1 = service.get(1);
        logger.debug("danger--------name:" + danger1.getName());
        for (Field field : danger1.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1, fieldName.length());
            Method method = danger1.getClass().getMethod(methodName, null);
            Object value = method.invoke(danger1);
            System.out.println("字段：" + fieldName + "：" + value);
        }
    }

    @Test
    public void testHas(){
        Assert.assertEquals(service.has(1),true);
    }

    @Test
    public void testGetAll() {
        List<Danger> list = service.getAll();
        int i = 1;
        for (Danger d : list) {
            logger.debug("危险信息" + (i++) + ":" + d.getName());
        }
    }

    @Test
    public void testGetList() {
        List<Danger> list = service.getList(0, 5, 119.0, 23.0);
        int i = 1;
        logger.debug("数量：" + list.size());
        for (Danger d : list) {
            logger.debug("危险信息" + (i++) + ":" + d.getName());
        }
    }

    public DangerService getService() {
        return service;
    }

    @Resource(name="DangerService")
    public void setService(DangerService service) {
        this.service = service;
    }
}
