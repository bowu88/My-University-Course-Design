package cn.edu.fjnu.gis11.webapp.graph;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * ��װͳ�����ݵĶ���
 * @author ������
 *
 */
public class StatisticsData {
	private double[] data;//����
	private double max,min;//���ݵ����ֵ����Сֵ
	private int groupNum;//����
	private double distance;//���
	private Group[] groups;//����������
	
	private double avg;//ƽ����
	private double mid;//��λ��
	private double s;//��׼��
	private double cv;//����ϵ��

	public StatisticsData(String dataString) throws StrToNumException{
		//��������ַ��������Կո��и�õ��ַ�������
		String[] dataStringArr=dataString.split(" +");
		//���ַ�������ת��double���飬������data
		data=new double[dataStringArr.length];
		try{
			for(int i=0;i<data.length;i++)
				data[i]=Double.valueOf(dataStringArr[i]);
			
			//�����鰴 ���� ��������
			Arrays.sort(data);
			
			//��ȡ���ֵ��Сֵ
			this.max=data[data.length-1];
			this.min=data[0];
			
			//������
			this.groupNum=(int) (1+3.32*Math.log10(data.length));
			
			//�����
			this.distance=(max-min)/groupNum;
			
			this.groups=new Group[this.groupNum];
			for(int i=0;i<groups.length;i++){
				groups[i]=new Group();
			}
			
			//��������������޵�����
			setGroups();
			
			//��ƽ����
			setAvg();
			
			//����λ��
			setMid();
			
			//���׼��
			setS();
			
			//�����ϵ��
			setCv();
			
		}catch(NumberFormatException e){
			throw new StrToNumException("�ַ���תdouble����");
		}
		
		
	}

	private void setGroups() {
		
		
		//���õ�һ��������޺�����
		groups[0].setLower(min);
		groups[0].setCaps(min+1.0/2*distance);
		
		for(int i=1;i<groups.length;i++){
			//����ÿ�������
			groups[i].setCaps(groups[0].getCaps()+(i+1)*distance);
			//����ÿ�������
			groups[i].setLower(groups[i-1].getCaps());
		}
		
		//����ÿ���Ƶ��
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
			//����ÿ���Ƶ��
			groups[i].setF((groups[i].getM())/(double)(data.length));
			for(int j=0;j<i;j++){
				//����ÿ����ۼ�Ƶ��
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
	
	//���ø÷���ǰ�����ȶ�data�����������
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
