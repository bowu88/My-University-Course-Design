package junit;

import java.util.List;

import org.junit.Test;

import tk.xiangyang.dao.AsylumDaoImpl;
import tk.xiangyang.dao.EarthquakeDaoImpl;
import tk.xiangyang.domain.Asylum;
import tk.xiangyang.domain.Earthquake;
import tk.xiangyang.utils.WebUtils;

public class DaoJunit {
	@Test
	public void AsylumDaoTest(){
		AsylumDaoImpl dao=new AsylumDaoImpl();
		Asylum asylum=new Asylum();
		asylum.setId(WebUtils.getId());
		asylum.setName("���������㳡");
		asylum.setAddress("�������㳡");
		asylum.setClassid("����");
		asylum.setArea(2.3);
		asylum.setPeople(12000);
		asylum.setX(119.01449);
		asylum.setY(25.458759);
		dao.add(asylum);
	}
	
	@Test
	public void AsylumGetAll(){
		List<Asylum> list=new AsylumDaoImpl().getAll();
		for(Asylum a:list){
			System.out.println(a.getName());
		}
	}
	
	@Test
	public void EarthquakeAdd(){
//		Earthquake earthquake=new Earthquake();
//		earthquake.setId("1");
//		earthquake.setLocation("仙游");
//		earthquake.setDepth(2.2);
//		earthquake.setMagnitude(2);
//		earthquake.setTime("昨天的昨天");
//		earthquake.setX(188);
//		earthquake.setY(222);
//		EarthquakeDaoImpl dao=new EarthquakeDaoImpl();
//		dao.add(earthquake);
		String[] infos ={
				"中国地震台网正式测定： 2014年 7月19日1时50分 ，在福建省莆田市仙游县 （ 北纬25.6度 ，东经118.7度 ） 发生 2.6级地震，震源深度 11 公里。",
				"中国地震台网正式测定： 2014年 3月14日19时53分 ，在福建省莆田市仙游县 （ 北纬25.6度 ，东经118.8度 ） 发生 3.3级地震，震源深度 11 公里。",
				"中国地震台网正式测定： 2013年 10月30日1时50分 ，在福建省莆田市仙游县、福州市永泰县交界 （ 北纬25.6度 ，东经118.8度 ） 发生 4.3级地震，震源深度 11 公里。",
				"中国地震台网正式测定： 2013年 9月4日6时23分 ，在福建省莆田市仙游县、福州市永泰县交界 （ 北纬25.6度 ，东经118.8度 ） 发生 4.8级地震，震源深度 10 公里。",
				"中国地震台网正式测定： 2013年 8月23日5时2分 ，在福建省莆田市仙游县 （ 北纬25.6度 ，东经118.8度 ） 发生 4.0级地震，震源深度 8 公里。",
				"中国地震台网正式测定： 2013年 8月19日17时36分 ，在福建省莆田市仙游县 （ 北纬25.6度 ，东经118.8度 ） 发生 3.3级地震，震源深度 11 公里。",
				"中国地震台网自动测定： 2013年 8月9日13时38分 ，在福建省莆田市仙游县 （ 北纬25.6度 ，东经118.7度 ） 发生 3.2级地震，震源深度 9 公里。",
				"中国地震台网正式测定： 2013年 8月9日13时37分 ，在福建省莆田市仙游县 （ 北纬25.6度 ，东经118.8度 ） 发生 2.2级地震，震源深度 7 公里。",
				"中国地震台网正式测定： 2013年 8月3日2时43分 ，在福建省莆田市仙游县 （ 北纬25.6度 ，东经118.8度 ） 发生 3.5级地震，震源深度 4 公里。",
		};
		for(String info:infos){
			String location=getString(info,"在","（");
			String time=getString(info,"测定：","，在");
			double depth=getDouble(info,"发生","级");
			double magnitude=getDouble(info,"震源深度","公里");
			double x=getDouble(info,"东经","度 ）");
			double y=getDouble(info,"北纬","度");
			System.out.println(location+"\t"+time+"\t"+depth+"\t"+ magnitude+"\t"+x+"\t"+y);
		}
//		Earthquake earthquake=new Earthquake();
//		earthquake.setId(WebUtils.getId());
//		earthquake.setLocation("仙游");
//		earthquake.setDepth(2.2);
//		earthquake.setMagnitude(2);
//		earthquake.setTime("昨天的昨天");
//		earthquake.setX(188);
//		earthquake.setY(222);
//		EarthquakeDaoImpl dao=new EarthquakeDaoImpl();
//		dao.add(earthquake);
		
	}
	
	@Test
	public void getAllEarthquake(){
		EarthquakeDaoImpl dao=new EarthquakeDaoImpl();
		List<Earthquake> list=dao.getAll();
		for(Earthquake e:list){
			System.out.println(e);
		}
	}
	
	public static String getString(String info,String before,String after){
		int begin=info.indexOf(before)+before.length();
		int end=info.indexOf(after);
		if(end>=info.length()||end<begin){
			return "*************end越界 end-begin="+(end-begin)+"**************";
		}
		if(begin>end){
			return "***************“"+info+"”中找不到信息！！！！！！！**************";
		}
		return info.substring(begin,end ).trim();
	}
	
	public static double getDouble(String info,String before,String after){
		String str=getString(info, before, after);
		double result=0;
		try{
			result=Double.valueOf(str);
		}catch (Exception e){
			System.out.println("*************“"+str+ "”不能被转换为double**************");
		}
		return result;
	}
}
