package cn.edu.fjnu.gis11.webapp.graph;

public class StrToNumException extends Exception {

	/**
	 * 字符串转成double失败所抛出的异常
	 */
	private static final long serialVersionUID = 2160084897232212560L;
	public StrToNumException(String e){
		super(e);
	}
	
}
