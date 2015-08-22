package cn.edu.fjnu.gis11.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.edu.fjnu.gis11.domain.User;
import cn.edu.fjnu.gis11.utils.JdbcUtils;

public class UserDaoJdbcImpl {
	public void add(User user){
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.getConnection();
			String sql="INSERT INTO users (id,type,email,name,password,gender," +
					"hometown,address,birthday,cellphone,home_phone,direction,description,photo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			st=conn.prepareStatement(sql);
			st.setString(1, user.getId());
			st.setString(2, user.getType());
			st.setString(3, user.getEmail());
			st.setString(4, user.getName());
			st.setString(5, user.getPassword());
			st.setString(6, user.getGender());
			st.setString(7, user.getHometown());
			st.setString(8, user.getAddress());
			st.setDate(9, new Date( user.getBirthday().getTime()));
			st.setString(10, user.getCellphone());
			st.setString(11, user.getHome_phone());
			st.setString(12, user.getDirection());
			st.setString(13, user.getDescription());
			st.setString(14, user.getPhoto());
			st.executeUpdate();
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtils.release(conn, st, rs);
		}
	}
	
	public User find(String email){
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.getConnection();
			String sql="SELECT * FROM users WHERE email=?;";
			st=conn.prepareStatement(sql);
			st.setString(1,email);
			rs=st.executeQuery();
			if(rs.next()){
				User user=new User();
				user.setId(rs.getString("id"));
				user.setType(rs.getString("type"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setGender(rs.getString("gender"));
				user.setHometown(rs.getString("hometown"));
				user.setAddress(rs.getString("address"));
				user.setBirthday(rs.getDate("birthday"));
				user.setCellphone(rs.getString("cellphone"));
				user.setHome_phone(rs.getString("home_phone"));
				user.setDirection(rs.getString("direction"));
				user.setDescription(rs.getString("description"));
				user.setPhoto(rs.getString("photo"));
				return user;
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtils.release(conn, st, rs);
		}
		return null;
	}
	
	public User find(String email,String password){
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.getConnection();
			String sql="SELECT * FROM users WHERE email=? and password=?;";
			st=conn.prepareStatement(sql);
			st.setString(1, email);
			st.setString(2, password);
			rs=st.executeQuery();
			if(rs.next()){
				User user=new User();
				user.setId(rs.getString("id"));
				user.setType(rs.getString("type"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setGender(rs.getString("gender"));
				user.setHometown(rs.getString("hometown"));
				user.setAddress(rs.getString("address"));
				user.setBirthday(rs.getDate("birthday"));
				user.setCellphone(rs.getString("cellphone"));
				user.setHome_phone(rs.getString("home_phone"));
				user.setDirection(rs.getString("direction"));
				user.setDescription(rs.getString("description"));
				user.setPhoto(rs.getString("photo"));
				return user;
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtils.release(conn, st, rs);
		}
		return null;
	}
	
	public List<User> getAll(){
		List<User> list=new ArrayList();
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.getConnection();
			String sql="SELECT * FROM users";
			st=conn.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()){
				User user=new User();
				user.setId(rs.getString("id"));
				user.setType(rs.getString("type"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setGender(rs.getString("gender"));
				user.setHometown(rs.getString("hometown"));
				user.setAddress(rs.getString("address"));
				user.setBirthday(rs.getDate("birthday"));
				user.setCellphone(rs.getString("cellphone"));
				user.setHome_phone(rs.getString("home_phone"));
				user.setDirection(rs.getString("direction"));
				user.setDescription(rs.getString("description"));
				user.setPhoto(rs.getString("photo"));
				list.add(user);
			}
			return list.size()>0?list:null;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtils.release(conn, st, rs);
		}
	}
}
