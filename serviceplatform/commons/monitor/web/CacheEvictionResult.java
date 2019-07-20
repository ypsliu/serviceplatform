/**
 * 
 */
package com.ruixue.serviceplatform.commons.monitor.web;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * the cache eviction result
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CacheEvictionResult implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3657909669729309077L;

	private int statusCode;

	private String errorMessage;

	private String cacheName;

	private String key;

	/**
	 * to create an empty CacheEvictionResult
	 */
	public CacheEvictionResult() {
		super();
	}

	/**
	 * to create the CacheEvictionResult by all fields
	 * 
	 * @param statusCode
	 * @param errorMessage
	 * @param cacheName
	 * @param key
	 */
	public CacheEvictionResult(int statusCode, String errorMessage, String cacheName, String key) {
		super();
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
		this.cacheName = cacheName;
		this.key = key;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the cacheName
	 */
	public String getCacheName() {
		return cacheName;
	}

	/**
	 * @param cacheName
	 *            the cacheName to set
	 */
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CacheEvictionResult [statusCode=" + statusCode + ", errorMessage=" + errorMessage + ", cacheName=" + cacheName + ", key=" + key + "]";
	}

}
