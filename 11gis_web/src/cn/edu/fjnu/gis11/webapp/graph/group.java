package cn.edu.fjnu.gis11.webapp.graph;

import java.math.BigDecimal;

/**
 * ��װ��Ķ���
 * @author ������
 *
 */
public class Group {
	private double Caps,Lower;//������޺�����
	private int m;//Ƶ��
	private double f,accF;//Ƶ�ʺ��ۼ�Ƶ��
	
	public Group(){
		this.Caps=0;
		this.Lower=0;
		this.m=0;
		this.f=0;
		this.accF=0;
	}
	
	public double getCaps() {
		return Caps;
	}
	public void setCaps(double caps) {
		Caps = formate(caps);
	}
	public double getLower() {
		return Lower;
	}
	public void setLower(double lower) {
		Lower = formate(lower);
	}
	public int getM() {
		return m;
	}
	public void setM(int m) {
		this.m = m;
	}
	public double getF() {
		return f;
	}
	public void setF(double f) {
		this.f = f;
	}
	public double getAccF() {
		return accF;
	}
	public void setAccF(double accF) {
		this.accF = accF;
	}
	
	//������λС��
	public static double formate(double f){
		BigDecimal bg = new BigDecimal(f);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
