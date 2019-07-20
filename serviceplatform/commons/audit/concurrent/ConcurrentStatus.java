/**
 * 
 */
package com.ruixue.serviceplatform.commons.audit.concurrent;

/**
 * the concurrent status
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class ConcurrentStatus {

	private String path;

	private long count;

	/**
	 * to create an empty ConcurrentStatus
	 */
	public ConcurrentStatus() {
		super();
	}

	/**
	 * to create the ConcurrentStatus
	 * 
	 * @param path
	 * @param count
	 */
	public ConcurrentStatus(String path, long count) {
		super();
		this.path = path;
		this.count = count;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConcurrentStatus [path=" + path + ", count=" + count + "]";
	}

}
