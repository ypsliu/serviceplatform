/**
 * 
 */
package com.ruixue.serviceplatform.commons.audit.timing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * the implementation for InvocationTimingService
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
@Service
public class InvocationTimingServiceImpl implements InvocationTimingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvocationTimingServiceImpl.class);

	/**
	 * the invocation summaries
	 */
	private final ConcurrentMap<String, Summary> summaries = new ConcurrentHashMap<String, InvocationTimingServiceImpl.Summary>();

	/**
	 * the process queue
	 */
	private final BlockingQueue<InvocationInfo> queue = new LinkedBlockingQueue<InvocationInfo>();

	/**
	 * to initialize the InvocationTimingServiceImpl
	 */
	public InvocationTimingServiceImpl() {
		Thread processThread = new Thread() {

			public void run() {
				while (true) {
					try {
						processInvocationInfo(queue.take());
					} catch (InterruptedException e) {
						// do nothing
					}
				}
			}

		};
		processThread.setDaemon(true);
		processThread.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ruixue.serviceplatform.commons.audit.timing.InvocationTimingService#invoked(com.ruixue.serviceplatform.commons
	 * .audit.timing.InvocationInfo)
	 */
	@Override
	public void invoked(final InvocationInfo info) {
		// just add the info to queue, do not block the main process
		this.queue.add(info);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.audit.timing.InvocationTimingService#getSummaries()
	 */
	@Override
	public Collection<InvocationSummary> getSummaries() {
		List<InvocationSummary> result = new ArrayList<InvocationSummary>();
		Collection<Summary> list = this.summaries.values();
		for (Summary s : list) {
			result.add(s.toInvocationSummary());
		}
		return result;
	}

	/**
	 * to process the invocation info
	 * 
	 * @param info
	 *            the invocation info
	 */
	private void processInvocationInfo(final InvocationInfo info) {
		if (info == null) {
			return;
		}
		LOGGER.debug("processing invocation info: {}", info);
		final String action = "[" + info.getMethod() + "]" + info.getPath();
		Summary summary = this.summaries.get(action);
		if (summary == null) {
			summary = new Summary(action);
			this.summaries.put(action, summary);
		}
		summary.addInvocation(info.getTime());
	}

	/**
	 * the invocation summary
	 * 
	 * @author shangchunming@rkylin.com.cn
	 *
	 */
	private class Summary {

		private final String action;

		private long count;

		private long max = Long.MIN_VALUE;

		private long min = Long.MAX_VALUE;

		private long avg;

		private long sum;

		/**
		 * to create the InvocationSummary by action
		 * 
		 * @param action
		 *            the action
		 */
		public Summary(String action) {
			super();
			this.action = action;
		}

		/**
		 * to add the invocation time to summary
		 * 
		 * @param time
		 *            the time
		 */
		public void addInvocation(long time) {
			this.count++;
			this.sum += time;
			if (time > this.max) {
				this.max = time;
			}
			if (time < this.min) {
				this.min = time;
			}
			this.avg = this.sum / this.count;
		}

		/**
		 * to convert to InvocationSummary
		 * 
		 * @return
		 */
		public InvocationSummary toInvocationSummary() {
			return new InvocationSummary(this.action, this.count, this.max, this.min, this.avg, this.sum);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Summary [action=" + action + ", count=" + count + ", max=" + max + ", min=" + min + ", avg=" + avg
					+ ", sum=" + sum + "]";
		}

	}

}
