package junit.test;

import java.util.Date;

import org.junit.Test;

import cn.edu.fjnu.gis11.dao.UserDaoJdbcImpl;
import cn.edu.fjnu.gis11.domain.User;

public class DaoTest {
	
	@Test
	public void addTest(){
		User user=new User();
		user.setId("TestId");
		user.setType("学生");
		user.setEmail("test@gmail.com");
		user.setName("测试");
		user.setPassword("111");
		user.setGender("男");
		user.setHometown("福州");
		user.setAddress("福建师范大学");
		user.setBirthday(new Date());
		user.setCellphone("10086");
		user.setHome_phone("10000");
		user.setDirection("开发");
		user.setDescription("测试用户");
		user.setPhoto("/photo/test.jpg");
		
		UserDaoJdbcImpl dao=new UserDaoJdbcImpl();
		dao.add(user);
	}
	
	@Test
	public void findTest(){
		UserDaoJdbcImpl dao=new UserDaoJdbcImpl();
		User user=dao.find("test@gmail.com");
		if(user!=null)
			System.out.println(user.getName());
	}
	@Test
	public void find2(){
		UserDaoJdbcImpl dao=new UserDaoJdbcImpl();
		User user=dao.find("test@gmail.com","11");
		if(user!=null)
			System.out.println(user.getName());
	}
}
