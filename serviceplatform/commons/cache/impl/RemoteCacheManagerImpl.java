/**
 * 
 */
package com.ruixue.serviceplatform.commons.cache.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.HeadersBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.ruixue.serviceplatform.commons.CommonsConstants;
import com.ruixue.serviceplatform.commons.cache.CacheInfo;
import com.ruixue.serviceplatform.commons.cache.CacheServer;
import com.ruixue.serviceplatform.commons.cache.RemoteCacheManager;
import com.ruixue.serviceplatform.commons.monitor.web.CacheEvictionResult;

/**
 * the implementation for RemoteCacheManager
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class RemoteCacheManagerImpl implements RemoteCacheManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteCacheManagerImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	private String monitorPassword;

	/**
	 * the thread pool
	 */
	private final ExecutorService threadPool = Executors.newFixedThreadPool(3, new ThreadFactory() {

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}

	});

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.RemoteCacheManager#getCacheInfos(com.ruixue.serviceplatform.commons.cache.CacheServer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<CacheInfo> getCacheInfos(CacheServer cacheServer, String cacheName) {
		// check
		if (cacheServer == null || cacheServer.isLocal() || StringUtils.isEmpty(cacheServer.getMonitorUrl())) {
			LOGGER.warn("the cacheServer is null or is local server or monitorUrl is null, get remote cache infos igonred");
			return null;
		}
		try {
			URI url = new URI(cacheServer.getCacheInfosUrl().replace(CACHE_CONTROL_COMMAND_KEY_CACHE_NAME_TEMPLATE, cacheName));
			HeadersBuilder<?> builder = RequestEntity.get(url);
			if (!StringUtils.isEmpty(this.monitorPassword)) {
				builder.header(CommonsConstants.HEADER_KEY_MONITOR_PASSWORD, this.monitorPassword);
			}
			RequestEntity<Void> req = builder.build();
			@SuppressWarnings("rawtypes")
			ResponseEntity<Collection> response = this.restTemplate.exchange(req, Collection.class);
			HttpStatus status = response.getStatusCode();
			if (status.is2xxSuccessful()) {
				return response.getBody();
			} else {
				LOGGER.error("get remote cache infos failed, url={}, status={}", url, status);
			}
		} catch (URISyntaxException e) {
			LOGGER.error("invalid monitorUrl: " + e.getMessage(), e);
		} catch (Throwable e) {
			LOGGER.error("get remote cache infos error: " + e.getMessage(), e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.RemoteCacheManager#evict(com.ruixue.serviceplatform.commons.cache.CacheServer, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void evict(final CacheServer cacheServer, final String cacheName, final String cacheKeyId) {
		// check
		if (cacheServer == null || cacheServer.isLocal() || StringUtils.isEmpty(cacheServer.getMonitorUrl())) {
			LOGGER.warn("the cacheServer is null or is local server or monitorUrl is null, evict remote cache infos igonred");
			return;
		}
		this.threadPool.submit(new Runnable() {

			@Override
			public void run() {
				try {
					URI url = new URI(cacheServer.getEvictionUrl().replace(CACHE_CONTROL_COMMAND_KEY_CACHE_NAME_TEMPLATE, cacheName)
							.replace(CACHE_CONTROL_COMMAND_KEY_CACHE_KEY_TEMPLATE, cacheKeyId));
					HeadersBuilder<?> builder = RequestEntity.delete(url);
					if (!StringUtils.isEmpty(RemoteCacheManagerImpl.this.monitorPassword)) {
						builder.header(CommonsConstants.HEADER_KEY_MONITOR_PASSWORD, RemoteCacheManagerImpl.this.monitorPassword);
					}
					RequestEntity<Void> req = builder.build();
					ResponseEntity<CacheEvictionResult> response = RemoteCacheManagerImpl.this.restTemplate.exchange(req, CacheEvictionResult.class);
					HttpStatus status = response.getStatusCode();
					if (status.is2xxSuccessful()) {
						CacheEvictionResult result = response.getBody();
						if (result.getStatusCode() == HttpStatus.OK.value()) {
							LOGGER.info("the remote cache evicted, url={}, result={}", url, result);
						} else {
							LOGGER.warn("evict the remote cache failed, url={}, result={}", url, result);
						}
					} else {
						LOGGER.error("evict remote cache failed, url={}, status={}", url, status);
					}
				} catch (URISyntaxException e) {
					LOGGER.error("invalid monitorUrl: " + e.getMessage(), e);
				} catch (Throwable e) {
					LOGGER.error("evict remote cache error: " + e.getMessage(), e);
				}
			}

		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.cache.RemoteCacheManager#clear(com.ruixue.serviceplatform.commons.cache.CacheServer, java.lang.String)
	 */
	@Override
	public void clear(final CacheServer cacheServer, final String cacheName) {
		// check
		if (cacheServer == null || cacheServer.isLocal() || StringUtils.isEmpty(cacheServer.getMonitorUrl())) {
			LOGGER.warn("the cacheServer is null or is local server or monitorUrl is null, clear remote cache infos igonred");
			return;
		}
		this.threadPool.submit(new Runnable() {

			@Override
			public void run() {
				try {
					URI url = new URI(cacheServer.getEvictionUrl().replace(CACHE_CONTROL_COMMAND_KEY_CACHE_NAME_TEMPLATE, cacheName)
							.replace(CACHE_CONTROL_COMMAND_KEY_CACHE_KEY_TEMPLATE, CACHE_CONTROL_COMMAND_KEY_ALL));
					HeadersBuilder<?> builder = RequestEntity.delete(url);
					if (!StringUtils.isEmpty(RemoteCacheManagerImpl.this.monitorPassword)) {
						builder.header(CommonsConstants.HEADER_KEY_MONITOR_PASSWORD, RemoteCacheManagerImpl.this.monitorPassword);
					}
					RequestEntity<Void> req = builder.build();
					ResponseEntity<CacheEvictionResult> response = RemoteCacheManagerImpl.this.restTemplate.exchange(req, CacheEvictionResult.class);
					HttpStatus status = response.getStatusCode();
					if (status.is2xxSuccessful()) {
						CacheEvictionResult result = response.getBody();
						if (result.getStatusCode() == HttpStatus.OK.value()) {
							LOGGER.info("the remote cache cleared, url={}, result={}", url, result);
						} else {
							LOGGER.warn("clear the remote cache failed, url={}, result={}", url, result);
						}
					} else {
						LOGGER.error("clear remote cache failed, url={}, status={}", url, status);
					}
				} catch (URISyntaxException e) {
					LOGGER.error("invalid monitorUrl: " + e.getMessage(), e);
				} catch (Throwable e) {
					LOGGER.error("clear remote cache error: " + e.getMessage(), e);
				}
			}

		});
	}

	/**
	 * @param monitorPassword
	 *            the monitorPassword to set
	 */
	public void setMonitorPassword(String monitorPassword) {
		if (!StringUtils.isEmpty(monitorPassword)) {
			this.monitorPassword = DigestUtils.md5Hex(monitorPassword);
		}
	}

	/**
	 * @param restTemplate
	 *            the restTemplate to set
	 */
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
