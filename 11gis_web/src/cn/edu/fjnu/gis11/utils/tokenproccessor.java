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
		
		//����ָ��	128λ��	16���ֽ�	md5
		try {
			MessageDigest md=MessageDigest.getInstance("md5");
			byte[] md5=md.digest(token.getBytes());
			
			//base64����--��������Ʊ��������ַ�	3�ֽڱ�4�ֽ�
			BASE64Encoder encoder=new BASE64Encoder();
			
			return encoder.encode(md5);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
	}
}
