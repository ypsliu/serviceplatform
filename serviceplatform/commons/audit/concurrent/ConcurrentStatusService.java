/**
 * 
 */
package com.ruixue.serviceplatform.commons.audit.concurrent;

import java.util.Collection;

/**
 * the concurrent status service
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public interface ConcurrentStatusService {

	/**
	 * the audit context key for concurrent status
	 */
	String KEY_CONCURRENT_STATUS = "audit.key.concurrentStatus";

	/**
	 * the total path
	 */
	String TOTAL_PATH = "TOTAL";

	/**
	 * to begin the invocation
	 * 
	 * @param path
	 *            the invoking path
	 */
	void beginInvocation(String path);

	/**
	 * to end the invocation
	 * 
	 * @param path
	 *            the invoked path
	 */
	void endInvocation(String path);

	/**
	 * to get the total count
	 * 
	 * @return the count
	 */
	long getTotalCount();

	/**
	 * to get the count for path
	 * 
	 * @param path
	 *            the path
	 * @return the count, -1 when the path is not existed
	 */
	ConcurrentStatus getCount(String path);

	/**
	 * to get the concurrent status
	 * 
	 * @return the status
	 */
	Collection<ConcurrentStatus> getStatus();

	/**
	 * to reset the counters
	 */
	void reset();

}
