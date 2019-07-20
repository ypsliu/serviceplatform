/**
 * 
 */
package com.ruixue.serviceplatform.commons.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * the base timed VO
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public abstract class BaseTimedVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5686934038142267519L;

	protected Date inputTime;

	protected Date updateTime;

	/**
	 * to create an empty BaseFullVo
	 */
	public BaseTimedVo() {
		super();
	}

	/**
	 * to create the BaseFullVo by all fields
	 * 
	 * @param inputTime
	 * @param updateTime
	 */
	public BaseTimedVo(Date inputTime, Date updateTime) {
		super();
		this.inputTime = inputTime;
		this.updateTime = updateTime;
	}

	/**
	 * @return the inputTime
	 */
	public final Date getInputTime() {
		return inputTime;
	}

	/**
	 * @param inputTime
	 *            the inputTime to set
	 */
	public final void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	/**
	 * @return the updateTime
	 */
	public final Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public final void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
