package cn.edu.fjnu.gis11.webapp.graph;

import java.math.BigDecimal;

/**
 * 封装组的对象
 * @author 游向阳
 *
 */
public class Group {
	private double Caps,Lower;//组的上限和下限
	private int m;//频数
	private double f,accF;//频率和累计频率
	
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
	
	//保留两位小数
	public static double formate(double f){
		BigDecimal bg = new BigDecimal(f);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
