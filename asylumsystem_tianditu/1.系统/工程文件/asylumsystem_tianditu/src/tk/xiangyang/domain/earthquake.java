package tk.xiangyang.domain;

//地震实体类  用于封装地震信息
public class Earthquake {
	private String id;
	private String location;	//位置描述
	private String time;		//时间
	private double magnitude;	//等级
	private double x;	//经度
	private double y;	//纬度
	private double depth;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public double getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getDepth() {
		return depth;
	}
	public void setDepth(double depth) {
		this.depth = depth;
	}
	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("*****************\r\n");
		sb.append("id:"+this.id).append("\r\n");
		sb.append("time:"+this.time).append("\r\n");
		sb.append("location:"+this.location).append("\r\n");
		sb.append("magnitue:"+this.magnitude).append("\r\n");
		sb.append("x:"+this.x).append("\r\n");
		sb.append("y:"+this.y).append("\r\n");
		sb.append("depth:"+this.depth).append("\r\n");
		
		return sb.toString();
	}
}
