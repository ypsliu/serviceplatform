/**
 * 
 */
package com.ruixue.serviceplatform.commons.cache;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * the cache info
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class CacheInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2072777378997108722L;

	private final String name;

	private final String keyId;

	private final String keyValue;

	private final String createTime;

	private final AtomicLong hits = new AtomicLong(1);

	/**
	 * to create the CacheInfo by key
	 * 
	 * @param name
	 * @param keyId
	 * @param keyValue
	 * @param createTime
	 */
	CacheInfo(String name, String keyId, String keyValue, String createTime) {
		super();
		this.name = name;
		this.keyId = keyId;
		this.keyValue = keyValue;
		this.createTime = createTime;
	}

	/**
	 * to increase the hits
	 */
	void increaseHits() {
		this.hits.incrementAndGet();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the keyId
	 */
	public String getKeyId() {
		return keyId;
	}

	/**
	 * @return the keyValue
	 */
	public String getKeyValue() {
		return keyValue;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @return the hits
	 */
	public long getHits() {
		return hits.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CacheInfo [name=" + name + ", keyId=" + keyId + ", keyValue=" + keyValue + ", createTime=" + createTime + ", hits=" + hits + "]";
	}

}
