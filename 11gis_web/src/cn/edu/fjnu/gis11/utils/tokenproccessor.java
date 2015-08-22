package cn.edu.fjnu.gis11.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import sun.misc.BASE64Encoder;

public class TokenProccessor {
	private static final TokenProccessor instance=new TokenProccessor();
	
	public static TokenProccessor getInstance(){
		return instance;
	}
	public String makeToken(){
		String token=System.currentTimeMillis()+new Random().nextInt(999999999)+"";
		
		//数据指纹	128位长	16个字节	md5
		try {
			MessageDigest md=MessageDigest.getInstance("md5");
			byte[] md5=md.digest(token.getBytes());
			
			//base64编码--任意二进制编码明文字符	3字节变4字节
			BASE64Encoder encoder=new BASE64Encoder();
			
			return encoder.encode(md5);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
	}
}
