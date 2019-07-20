/**
 * 
 */
package com.ruixue.serviceplatform.commons.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * the base search condition
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public abstract class BaseSearchCondition implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2435983834793425355L;

	/**
	 * default page size
	 */
	public static final int DEFAULT_PAGE_SIZE = 20;

	protected String fromTime;

	protected String toTime;

	@NotNull
	protected Integer pageNo;

	@NotNull
	protected Integer pageSize;

	/**
	 * to create an empty BaseSearchCondition
	 */
	public BaseSearchCondition() {
		super();
	}

	/**
	 * to create the BaseSearchCondition by all fields
	 * 
	 * @param fromTime
	 * @param toTime
	 * @param pageNo
	 * @param pageSize
	 */
	public BaseSearchCondition(String fromTime, String toTime, Integer pageNo, Integer pageSize) {
		super();
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	/**
	 * @return the fromTime
	 */
	public final String getFromTime() {
		return fromTime;
	}

	/**
	 * @param fromTime
	 *            the fromTime to set
	 */
	public final void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	/**
	 * @return the toTime
	 */
	public final String getToTime() {
		return toTime;
	}

	/**
	 * @param toTime
	 *            the toTime to set
	 */
	public final void setToTime(String toTime) {
		this.toTime = toTime;
	}

	/**
	 * @return the pageNo
	 */
	public final Integer getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public final void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public final Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public final void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
