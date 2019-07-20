/**
 * 
 */
package com.ruixue.serviceplatform.commons.cache;

/**
 * the cache server
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class CacheServer {

	private String name;

	private boolean local;

	private String monitorUrl;

	private String cacheInfosUrl;

	private String evictionUrl;

	/**
	 * to create an empty CacheServer
	 */
	public CacheServer() {
		super();
	}

	/**
	 * the create the CacheServer by all fields
	 * 
	 * @param name
	 * @param local
	 * @param monitorUrl
	 * @param cacheInfosUrl
	 * @param evictionUrl
	 */
	public CacheServer(String name, boolean local, String monitorUrl, String cacheInfosUrl, String evictionUrl) {
		super();
		this.name = name;
		this.local = local;
		this.monitorUrl = monitorUrl;
		this.cacheInfosUrl = cacheInfosUrl;
		this.evictionUrl = evictionUrl;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the local
	 */
	public boolean isLocal() {
		return local;
	}

	/**
	 * @param local
	 *            the local to set
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}

	/**
	 * @return the monitorUrl
	 */
	public String getMonitorUrl() {
		return monitorUrl;
	}

	/**
	 * @param monitorUrl
	 *            the monitorUrl to set
	 */
	public void setMonitorUrl(String monitorUrl) {
		this.monitorUrl = monitorUrl;
	}

	/**
	 * @return the cacheInfosUrl
	 */
	public String getCacheInfosUrl() {
		return cacheInfosUrl;
	}

	/**
	 * @param cacheInfosUrl
	 *            the cacheInfosUrl to set
	 */
	public void setCacheInfosUrl(String cacheInfosUrl) {
		this.cacheInfosUrl = cacheInfosUrl;
	}

	/**
	 * @return the evictionUrl
	 */
	public String getEvictionUrl() {
		return evictionUrl;
	}

	/**
	 * @param evictionUrl
	 *            the evictionUrl to set
	 */
	public void setEvictionUrl(String evictionUrl) {
		this.evictionUrl = evictionUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CacheServer [name=" + name + ", local=" + local + ", monitorUrl=" + monitorUrl + ", cacheInfosUrl=" + cacheInfosUrl + ", evictionUrl="
				+ evictionUrl + "]";
	}

}
