package cn.edu.fjnu.gis11.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public class DateConverter implements Converter {

	public Object convert(Class type, Object value) {
		if(value==null){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse((String) value);
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
	}

}
