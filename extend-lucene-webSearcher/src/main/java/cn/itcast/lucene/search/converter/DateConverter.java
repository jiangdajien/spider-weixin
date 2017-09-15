package cn.itcast.lucene.search.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * S:输入 source
 * T:输出 target
 * 
 * @author maoxiangyi
 *
 */
public class DateConverter implements Converter<String, Date> {

	public Date convert(String source) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = format.parse(source);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
