package tk.fjnugis.dangerserver.dao;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import tk.fjnugis.dangerserver.dao.DangerDao;
import tk.fjnugis.dangerserver.model.Danger;
import tk.fjnugis.dangerserver.service.DangerServiceTest;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * Created by Xiangyang on 2015/1/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager="txManager", defaultRollback=false)
@Transactional
public class DangerDaoTest {
    public static Danger danger;
    public DangerDao dao;
    public static Logger logger = Logger.getLogger(DangerServiceTest.class);

    @BeforeClass
    public static void beforeClass() {
        //sessionFactory=new AnnotationConfiguration().configure().buildSessionFactory();
        //dao.setSessionFactory(new AnnotationConfiguration().configure().buildSessionFactory());

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
        logger.debug("返回的id："+dao.add(danger));
    }

    @Test
    public void testGet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Danger danger1 = dao.get(1);
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
    public void testGetAll() {
        List<Danger> list = dao.getAll();
        int i = 1;
        for (Danger d : list) {
            logger.debug("危险信息" + (i++) + ":" + d.getName());
        }
    }

    @Test
    public void testGetList() {
        List<Danger> list = dao.getList(0, 5, 119.0, 23.0);
        int i = 1;
        logger.debug("数量：" + list.size());
        for (Danger d : list) {
            logger.debug("危险信息" + (i++) + ":" + d.getName());
        }
    }
    @Test
    public void testGetSimpleList(){
        List<Map<String,Double>> list=dao.getLngLatList();
        if(list.size()==0)
            fail("失败");
        logger.debug("大小----------------------"+list.size());
        for(Map map:list){
            logger.debug(map.toString());
        }
    }

    @Test
    public void testGetLngLatList(){
        List<Map<String,Double>> list=dao.getLngLatList("自然灾害",null,null);
//        List<Map<String,Double>> list2=dao.getLngLatList("","泥石流",null);
        List<Map<String,Double>> list2=dao.getLngLatList("自然灾害","泥石流",null);
//        List<Map<String,Double>> list3=dao.getLngLatList("",null,"流");
        List<Map<String,Double>> list3=dao.getLngLatList("自然灾害","泥石流","流");
        if(list.size()==0)
            fail("失败");
        logger.debug("自然灾害----------------------" + list.size());
        logger.debug("subcategory----------------------" + list2.size());
        logger.debug("tags----------------------" + list3.size());

    }

    @Test
    public void testGetListByTag(){
        List<Danger> list= dao.getListByTag(0,10,119.1,26.1,"duang");
        if(list.size()==0)
            fail("未取得数据");
        for(Danger danger1:list) {
            logger.debug(danger1.getLongitude()+" 纬度："+danger1.getLatitude()+"  "+danger1.getProvince()+danger1.getCity()+danger1.getDistrict()+danger1.getStreet());
        }
    }

    @Test
    public void testGetByBoundary(){
        List<Danger> list= dao.getByBoundary(112.1,26,122,20);
        if(list.size()==0)
            fail("未取得数据");
        for(Danger danger1:list) {
            logger.debug(danger1.getLongitude()+" 纬度："+danger1.getLatitude()+"  "+danger1.getProvince()+danger1.getCity()+danger1.getDistrict()+danger1.getStreet());
        }
    }

    public DangerDao getDao() {
        return dao;
    }

    @Resource
    public void setDao(DangerDao dao) {
        this.dao = dao;
    }
}
