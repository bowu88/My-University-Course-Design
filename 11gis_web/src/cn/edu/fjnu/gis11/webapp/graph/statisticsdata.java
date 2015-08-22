package cn.edu.fjnu.gis11.webapp.graph;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 封装统计数据的对象
 * @author 游向阳
 *
 */
public class StatisticsData {
	private double[] data;//数据
	private double max,min;//数据的最大值、最小值
	private int groupNum;//组数
	private double distance;//组距
	private Group[] groups;//组对象的数组
	
	private double avg;//平均数
	private double mid;//中位数
	private double s;//标准差
	private double cv;//变异系数

	public StatisticsData(String dataString) throws StrToNumException{
		//将输入的字符串数据以空格切割，得到字符串数组
		String[] dataStringArr=dataString.split(" +");
		//将字符串数组转成double数组，并传给data
		data=new double[dataStringArr.length];
		try{
			for(int i=0;i<data.length;i++)
				data[i]=Double.valueOf(dataStringArr[i]);
			
			//对数组按 升序 进行排序
			Arrays.sort(data);
			
			//获取最大值最小值
			this.max=data[data.length-1];
			this.min=data[0];
			
			//求组数
			this.groupNum=(int) (1+3.32*Math.log10(data.length));
			
			//求组距
			this.distance=(max-min)/groupNum;
			
			this.groups=new Group[this.groupNum];
			for(int i=0;i<groups.length;i++){
				groups[i]=new Group();
			}
			
			//设置组的上限下限等数据
			setGroups();
			
			//求平均数
			setAvg();
			
			//求中位数
			setMid();
			
			//求标准差
			setS();
			
			//求变异系数
			setCv();
			
		}catch(NumberFormatException e){
			throw new StrToNumException("字符串转double错误");
		}
		
		
	}

	private void setGroups() {
		
		
		//设置第一组组的下限和上限
		groups[0].setLower(min);
		groups[0].setCaps(min+1.0/2*distance);
		
		for(int i=1;i<groups.length;i++){
			//设置每组的上限
			groups[i].setCaps(groups[0].getCaps()+(i+1)*distance);
			//设置每组的下限
			groups[i].setLower(groups[i-1].getCaps());
		}
		
		//计算每组的频数
		for(int i=0;i<data.length;i++){
			for(int j=0;j<groups.length;j++){
				if(data[i]==groups[0].getLower()){
					groups[0].setM(groups[j].getM()+1);
					break;
				}
				if(data[i]>groups[j].getLower()&&data[i]<=groups[j].getCaps()){
					groups[j].setM(groups[j].getM()+1);
					break;
				}
			}
		}
		
		for(int i=0;i<groups.length;i++){
			//计算每组的频率
			groups[i].setF((groups[i].getM())/(double)(data.length));
			for(int j=0;j<i;j++){
				//计算每组的累计频率
				groups[i].setAccF(groups[i].getAccF()+groups[j].getF());
			}
		}
		
	}
	
	private void setAvg() {
		double sum=0;
		for(double i:data){
			sum+=i;
		}
		avg= sum/data.length;
	}
	
	//调用该方法前必须先对data数组进行排序
	private void setMid() {
		int n=data.length ;
		if(n%2==0){
			this.mid=(data[n/2]+data[n/2+1])/2;
		}else{
			this.mid=data[(n+1)/2];
		}
	}
	
	private void setS() {
		int n=data.length;
		double d=0;
		for(int i=0;i<n;i++){
			d+=(data[i]-avg)*(data[i]-avg);
		}
		this.s = Math.sqrt(d/n);
	}
	
	private void setCv() {
		this.cv = s/avg;
	}
	
	
	public Group[] getGroups() {
		return groups;
	}

	public double getAvg() {
		return formate(avg);
	}

	public double getMid() {
		return formate(mid);
	}

	public double getS() {
		return formate(s);
	}

	public double getCv() {
		return formate(cv);
	}
	
	public double formate(double f){
		BigDecimal bg = new BigDecimal(f);
        return bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
