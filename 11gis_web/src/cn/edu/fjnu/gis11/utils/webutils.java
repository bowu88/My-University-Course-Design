package cn.edu.fjnu.gis11.utils;

import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

public class WebUtils {
	public static <T> T request2Bean(HttpServletRequest request,Class<T> clazz){
		try {
			T bean=clazz.newInstance();
			for(Enumeration<String> e=request.getParameterNames();e.hasMoreElements();){
				String name=e.nextElement();
				String value=request.getParameter(name);
				if(!value.trim().equals(""))
					BeanUtils.setProperty(bean, name, value);
			}
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static String makeId(){
		//UUIDËã·¨	128Î» 36×Ö·û
		return UUID.randomUUID().toString();
	}
}
