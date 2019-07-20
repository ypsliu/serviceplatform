/**
 * 
 */
package com.ruixue.serviceplatform.commons.audit.timing;

/**
 * the invocation info
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class InvocationInfo {

	private String path;

	private String method;

	private long beginTimestamp;

	private long endTimestamp;

	private long time;

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the beginTimestamp
	 */
	public long getBeginTimestamp() {
		return beginTimestamp;
	}

	/**
	 * @param beginTimestamp
	 *            the beginTimestamp to set
	 */
	public void setBeginTimestamp(long beginTimestamp) {
		this.beginTimestamp = beginTimestamp;
	}

	/**
	 * @return the endTimestamp
	 */
	public long getEndTimestamp() {
		return endTimestamp;
	}

	/**
	 * @param endTimestamp
	 *            the endTimestamp to set
	 */
	public void setEndTimestamp(long endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InvocationInfo [path=" + path + ", method=" + method + ", beginTimestamp=" + beginTimestamp + ", endTimestamp=" + endTimestamp + ", time="
				+ time + "]";
	}

}
