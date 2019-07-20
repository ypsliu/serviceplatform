/**
 * 
 */
package com.ruixue.serviceplatform.commons.audit.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;

/**
 * the implementation for ConcurrentStatusService
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
@Service
public class ConcurrentStatusServiceImpl implements ConcurrentStatusService {

	private final BlockingQueue<InvocationConcurrentInfo> queue = new LinkedBlockingQueue<InvocationConcurrentInfo>();

	/**
	 * the counter
	 */
	private final Map<String, Long> counters = new HashMap<String, Long>();

	/**
	 * the reset lock
	 */
	private final ReentrantLock resetLock = new ReentrantLock();

	{
		// initialize the counters
		this.counters.put(TOTAL_PATH, 0L);
		// the process thread
		final Thread worker = new Thread() {

			public void run() {
				while (true) {
					try {
						processConcurrentInfo(queue.take());
					} catch (InterruptedException e) {
						// do nothing
					}
				}
			}

		};
		worker.setDaemon(true);
		worker.start();
	}

	/**
	 * to process the invocation concurrent info
	 * 
	 * @param info
	 *            the info
	 */
	private void processConcurrentInfo(InvocationConcurrentInfo info) {
		this.resetLock.lock();
		try {
			// total
			this.counters.put(TOTAL_PATH, this.counters.get(TOTAL_PATH) + info.delta);
			// counter
			Long count = this.counters.get(info.path);
			if (count == null) {
				// initialize the count
				count = 0L;
			}
			count += info.delta;
			if (count == 0) {
				// remove the counter when the count is 0
				this.counters.remove(info.path);
			} else {
				// update the count
				this.counters.put(info.path, count);
			}
			// finally, set the info to null
			info = null;
		} finally {
			this.resetLock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.audit.concurrent.ConcurrentStatusService#beginInvocation(java.lang.String)
	 */
	@Override
	public void beginInvocation(final String path) {
		final InvocationConcurrentInfo info = new InvocationConcurrentInfo();
		info.path = path;
		info.delta = 1; // to increase
		this.queue.add(info);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.audit.concurrent.ConcurrentStatusService#endInvocation(java.lang.String)
	 */
	@Override
	public void endInvocation(final String path) {
		final InvocationConcurrentInfo info = new InvocationConcurrentInfo();
		info.path = path;
		info.delta = -1; // to decrease
		this.queue.add(info);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.audit.concurrent.ConcurrentStatusService#getTotalCount()
	 */
	@Override
	public long getTotalCount() {
		return this.counters.get(TOTAL_PATH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.audit.concurrent.ConcurrentStatusService#getCount(java.lang.String)
	 */
	@Override
	public ConcurrentStatus getCount(final String path) {
		final Long count = this.counters.get(path);
		if (count != null) {
			return new ConcurrentStatus(path, count);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.audit.concurrent.ConcurrentStatusService#getStatus()
	 */
	@Override
	public Collection<ConcurrentStatus> getStatus() {
		final Collection<ConcurrentStatus> status = new ArrayList<ConcurrentStatus>();
		for (final String path : this.counters.keySet()) {
			status.add(new ConcurrentStatus(path, this.counters.get(path)));
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.audit.concurrent.ConcurrentStatusService#reset()
	 */
	@Override
	public void reset() {
		this.resetLock.lock();
		try {
			// clear the counters
			this.counters.clear();
			// reset the total counter
			this.counters.put(TOTAL_PATH, 0L);
		} finally {
			this.resetLock.unlock();
		}
	}

	/**
	 * the invocation concurrent info
	 * 
	 * @author shangchunming@rkylin.com.cn
	 *
	 */
	private class InvocationConcurrentInfo {

		public String path;

		public int delta;

	}

}
