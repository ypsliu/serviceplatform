/**
 * 
 */
package com.ruixue.serviceplatform.commons.audit.timing;

/**
 * the invocation summary
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class InvocationSummary {

	private String action;

	private long count;

	private long max;

	private long min;

	private long avg;

	private long totalTime;

	/**
	 * to create an empty InvocationSummary(
	 */
	public InvocationSummary() {
		super();
	}

	/**
	 * to create the InvocationSummary by all fields
	 * 
	 * @param action
	 * @param count
	 * @param max
	 * @param min
	 * @param avg
	 * @param totalTime
	 */
	public InvocationSummary(String action, long count, long max, long min, long avg, long totalTime) {
		super();
		this.action = action;
		this.count = count;
		this.max = max;
		this.min = min;
		this.avg = avg;
		this.totalTime = totalTime;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @return the max
	 */
	public long getMax() {
		return max;
	}

	/**
	 * @return the min
	 */
	public long getMin() {
		return min;
	}

	/**
	 * @return the avg
	 */
	public long getAvg() {
		return avg;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(long max) {
		this.max = max;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(long min) {
		this.min = min;
	}

	/**
	 * @param avg
	 *            the avg to set
	 */
	public void setAvg(long avg) {
		this.avg = avg;
	}

	/**
	 * @return the totalTime
	 */
	public long getTotalTime() {
		return totalTime;
	}

	/**
	 * @param totalTime
	 *            the totalTime to set
	 */
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InvocationSummary [action=" + action + ", count=" + count + ", max=" + max + ", min=" + min + ", avg="
				+ avg + ", totalTime=" + totalTime + "]";
	}

}
