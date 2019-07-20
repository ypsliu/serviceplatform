/**
 * 
 */
package com.ruixue.serviceplatform.commons.datetime;

import java.sql.Timestamp;
import java.util.Date;

/**
 * the DateTimeProvider
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public interface DateTimeProvider {

	/**
	 * to get the time stamp
	 * 
	 * @return the time stamp
	 */
	Timestamp nowTimestamp();

	/**
	 * to get the date time
	 * 
	 * @return the date time
	 */
	Date nowDatetime();

	/**
	 * to get the time millis
	 * 
	 * @return the time millis
	 */
	long nowTimeMillis();

}
