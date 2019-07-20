package com.ruixue.serviceplatform.commons.exception;

@SuppressWarnings("serial")
public class OptimisticLockException extends RuntimeException {

	public OptimisticLockException() {
		this("网络连接超时，请重试");
	}

	public OptimisticLockException(final Throwable e) {
		super(e);
	}

	public OptimisticLockException(final String msg) {
		super(msg);
	}

	
	public OptimisticLockException(final String msg,final Throwable e) {
		super(msg,e);
	}
}
