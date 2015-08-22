package cn.edu.fjnu.gis11.service;

import java.util.List;

import cn.edu.fjnu.gis11.dao.UserDaoJdbcImpl;
import cn.edu.fjnu.gis11.domain.User;
import cn.edu.fjnu.gis11.exception.UserExistException;

public class BusinessServiceImpl {
	private UserDaoJdbcImpl dao=new UserDaoJdbcImpl();
	
	//�ṩע�����
	public void registerUser(User user) throws UserExistException{
		if(dao.find(user.getEmail())!=null){
			throw new UserExistException("�������ѱ�ע�ᣡ");
		}
		dao.add(user);
	}

	//�ṩ��½����
	public User loginUser(String email, String password){
		return dao.find(email, password);
	}
	
	public List<User> getAllUser(){
		return dao.getAll();
	}
}
