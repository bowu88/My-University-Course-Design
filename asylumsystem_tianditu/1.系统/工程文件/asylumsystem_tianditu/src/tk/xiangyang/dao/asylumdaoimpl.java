package tk.xiangyang.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tk.xiangyang.domain.Asylum;
import tk.xiangyang.utils.JdbcUtils;

public class AsylumDaoImpl {
	
	public void add(Asylum asylum){
		try{
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="INSERT INTO asylums(id,name,address,classid,area,people,img,description,x,y)VALUES(?,?,?,?,?,?,?,?,?,?)";
			qr.update(sql,asylum.getId(),asylum.getName(),asylum.getAddress(),asylum.getClassid(),asylum.getArea(),asylum.getPeople(),asylum.getImg(),asylum.getDescription(),asylum.getX(),asylum.getY());
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public List<Asylum> getAll(){
		try{
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="SELECT * FROM asylums";
			return qr.query(sql, new BeanListHandler<Asylum>(Asylum.class));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public Asylum get(String id){
		try{
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="SELECT * FROM asylums WHERE id=?";
			return qr.query(sql, new BeanHandler<Asylum>(Asylum.class),id);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public void update(Asylum asylum){
		try{
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="UPDATE asylums SET " +
					"name=?," +
					"address=?," +
					"classid=?," +
					"area=?," +
					"people=?," +
					"img=?," +
					"description=?," +
					"x=?," +
					"y=?" +
					" WHERE `id`= ?";
			qr.update(sql, asylum.getName(),asylum.getAddress(),asylum.getClassid(),asylum.getArea(),asylum.getPeople(),asylum.getImg(),asylum.getDescription(),asylum.getX(),asylum.getY(),asylum.getId());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
