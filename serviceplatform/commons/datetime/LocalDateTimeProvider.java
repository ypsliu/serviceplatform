/**
 * 
 */
package com.ruixue.serviceplatform.commons.datetime;

import java.sql.Timestamp;
import java.util.Date;

/**
 * the local DateTimeProvider
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public class LocalDateTimeProvider implements DateTimeProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.datetime.DateTimeProvider#nowTimestamp()
	 */
	public Timestamp nowTimestamp() {
		return new Timestamp(this.nowTimeMillis());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.datetime.DateTimeProvider#nowDatetime()
	 */
	public Date nowDatetime() {
		return new Date();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.datetime.DateTimeProvider#nowTimeMillis()
	 */
	public long nowTimeMillis() {
		return System.currentTimeMillis();
	}

}
