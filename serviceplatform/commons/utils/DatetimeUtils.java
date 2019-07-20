/**
 * 
 */
package com.ruixue.serviceplatform.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the Utils for Datetime
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public final class DatetimeUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatetimeUtils.class);

	/**
	 * convert string to date by format
	 * 
	 * @param val
	 * @param format
	 * @return date
	 */
	public static Date stringToDate(String val, String format) {
		if (!StringUtils.isEmpty(val) && !StringUtils.isEmpty(format)) {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			try {
				return formatter.parse(val);
			} catch (ParseException e) {
				LOGGER.warn("can not use '{}' to convert '{}' to date.", new Object[] { format, val });
			}
		} else {
			LOGGER.warn("can not use '{}' to convert '{}' to date.", new Object[] { format, val });
		}
		return null;
	}

	/**
	 * convert date to string by format
	 * 
	 * @param date
	 * @param format
	 * @return string
	 */
	public static String dateToString(Date date, String format) {
		if ((date != null) && !StringUtils.isEmpty(format)) {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.format(date);
		}
		return null;
	}

}
