package tk.xiangyang.service;

import java.util.List;

import tk.xiangyang.dao.AsylumDaoImpl;
import tk.xiangyang.dao.EarthquakeDaoImpl;
import tk.xiangyang.domain.Asylum;
import tk.xiangyang.domain.Earthquake;

public class BusinessService {
	AsylumDaoImpl adao=new AsylumDaoImpl();
	EarthquakeDaoImpl edao=new EarthquakeDaoImpl();
	
	//获取所有避难场所
	public List<Asylum> getAllAsylums(){
		List<Asylum> list=adao.getAll();
		return list.size()>0?list:null;
	}
	
	//获取指定避难场所
	public Asylum getAsylum(String id){
		return adao.get(id);
	}
	
	//更新避难场所信息
	public void updateAsylum(Asylum asylum){
		adao.update(asylum);
	}
	
	//获取所有的地震信息
	public List<Earthquake> getAllEarthquake(){
		return edao.getAll();
	}
	
}
