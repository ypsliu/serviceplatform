package com.ruixue.serviceplatform.commons.page;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 * the page
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
@JsonDeserialize(as = DefaultPage.class)
public interface Page<E> extends Serializable {

	/**
	 * has more records for next page?
	 * 
	 * @return
	 */
	boolean hasMore();

	/**
	 * to get total records count
	 * 
	 * @return the count
	 */
	long getTotal();

	/**
	 * to get the current page no
	 * 
	 * @return the no
	 */
	int getPageNo();

	/**
	 * to get the page size
	 * 
	 * @return the size
	 */
	int getPageSize();

	/**
	 * to get the current page records
	 * 
	 * @return the records
	 */
	List<E> getRecords();

	/**
	 * to set the current page records
	 * 
	 * @param records
	 *            the records
	 */
	void setRecords(List<E> records);

}
