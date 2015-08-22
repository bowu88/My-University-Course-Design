package tk.xiangyang.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tk.xiangyang.domain.Asylum;
import tk.xiangyang.domain.Earthquake;
import tk.xiangyang.utils.JdbcUtils;

public class EarthquakeDaoImpl {

	public void add(Earthquake earthquake){
		try{
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="INSERT INTO earthquake(id,location,time,magnitude,x,y,depth)VALUES(?,?,?,?,?,?,?)";
			qr.update(sql,earthquake.getId(),earthquake.getLocation(),earthquake.getTime(),earthquake.getMagnitude(),earthquake.getX(),earthquake.getY(),earthquake.getDepth());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public List<Earthquake> getAll(){
		try{
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="SELECT * FROM earthquake";
			return qr.query(sql, new BeanListHandler<Earthquake>(Earthquake.class));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
