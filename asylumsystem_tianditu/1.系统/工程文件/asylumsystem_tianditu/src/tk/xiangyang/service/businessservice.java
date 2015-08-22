package tk.xiangyang.service;

import java.util.List;

import tk.xiangyang.dao.AsylumDaoImpl;
import tk.xiangyang.dao.EarthquakeDaoImpl;
import tk.xiangyang.domain.Asylum;
import tk.xiangyang.domain.Earthquake;

public class BusinessService {
	AsylumDaoImpl adao=new AsylumDaoImpl();
	EarthquakeDaoImpl edao=new EarthquakeDaoImpl();
	
	//��ȡ���б��ѳ���
	public List<Asylum> getAllAsylums(){
		List<Asylum> list=adao.getAll();
		return list.size()>0?list:null;
	}
	
	//��ȡָ�����ѳ���
	public Asylum getAsylum(String id){
		return adao.get(id);
	}
	
	//���±��ѳ�����Ϣ
	public void updateAsylum(Asylum asylum){
		adao.update(asylum);
	}
	
	//��ȡ���еĵ�����Ϣ
	public List<Earthquake> getAllEarthquake(){
		return edao.getAll();
	}
	
}
