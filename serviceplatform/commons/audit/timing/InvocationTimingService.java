/**
 * 
 */
package com.ruixue.serviceplatform.commons.audit.timing;

import java.util.Collection;

/**
 * the invocation timing service
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public interface InvocationTimingService {

	/**
	 * the audit context key for invocation info
	 */
	String KEY_INVOCATION_INFO = "audit.key.invocationInfo";

	/**
	 * invocation finished
	 * 
	 * @param info
	 *            the invocation info
	 */
	void invoked(InvocationInfo info);

	/**
	 * to get the invocation summaries
	 * 
	 * @return the summaries
	 */
	Collection<InvocationSummary> getSummaries();

}
