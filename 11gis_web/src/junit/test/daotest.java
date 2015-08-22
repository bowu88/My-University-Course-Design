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
		user.setType("ѧ��");
		user.setEmail("test@gmail.com");
		user.setName("����");
		user.setPassword("111");
		user.setGender("��");
		user.setHometown("����");
		user.setAddress("����ʦ����ѧ");
		user.setBirthday(new Date());
		user.setCellphone("10086");
		user.setHome_phone("10000");
		user.setDirection("����");
		user.setDescription("�����û�");
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
