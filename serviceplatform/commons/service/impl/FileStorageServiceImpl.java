/**
 * 
 */
package com.ruixue.serviceplatform.commons.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruixue.serviceplatform.commons.CommonsConstants;
import com.ruixue.serviceplatform.commons.service.FileStorageProvider;
import com.ruixue.serviceplatform.commons.service.FileStorageService;

/**
 * @author shangchunming@rkylin.com.cn
 * 
 */
public class FileStorageServiceImpl implements FileStorageService {

	/**
	 * logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageServiceImpl.class);

	private String loadFromProviderName;

	private Map<String, FileStorageProvider> fileStorageProviders;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.service.FileStorageProvider#loadBytes(java.lang .String, java.lang.String)
	 */
	public byte[] loadBytes(String category, String key) {
		if (this.fileStorageProviders != null && !this.fileStorageProviders.isEmpty()) {
			FileStorageProvider provider = this.fileStorageProviders.get(this.getLoadFromProviderName());
			if (provider != null) {
				return provider.loadBytes(category, key);
			} else {
				LOGGER.error("the load from provider is NOT existed, load file failed");
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.service.FileStorageProvider#loadStream(java.lang .String, java.lang.String)
	 */
	public InputStream loadStream(String category, String key) {
		if (this.fileStorageProviders != null && !this.fileStorageProviders.isEmpty()) {
			FileStorageProvider provider = this.fileStorageProviders.get(this.getLoadFromProviderName());
			if (provider != null) {
				return provider.loadStream(category, key);
			} else {
				LOGGER.error("the load from provider is NOT existed, load file failed");
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.service.FileStorageProvider#loadStream(java.lang .String, java.lang.String, java.io.OutputStream)
	 */
	public void loadStream(String category, String key, OutputStream outputStream) {
		if (this.fileStorageProviders != null && !this.fileStorageProviders.isEmpty()) {
			FileStorageProvider provider = this.fileStorageProviders.get(this.getLoadFromProviderName());
			if (provider != null) {
				provider.loadStream(category, key, outputStream);
			} else {
				LOGGER.error("the load from provider is NOT existed, load file failed");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.service.FileStorageProvider#save(java.lang.String, java.lang.String, byte[])
	 */
	public boolean save(String category, String key, byte[] content) {
		if (this.fileStorageProviders != null && !this.fileStorageProviders.isEmpty()) {
			for (String providerName : this.fileStorageProviders.keySet()) {
				if (CommonsConstants.LOCAL_FILE_STORAGE_PROVDER_NAME.equalsIgnoreCase(providerName)) {
					// it is the local provider
					if (this.saveFileInLocal()) {
						this.fileStorageProviders.get(providerName).save(category, key, content);
					}
				} else {
					// others
					if (this.isAvailableProvider(providerName)) {
						this.fileStorageProviders.get(providerName).save(category, key, content);
					}
				}
			}
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.service.FileStorageProvider#save(java.lang.String, java.lang.String, java.io.InputStream)
	 */
	public boolean save(String category, String key, InputStream content) {
		if (this.fileStorageProviders != null && !this.fileStorageProviders.isEmpty()) {
			for (String providerName : this.fileStorageProviders.keySet()) {
				if (CommonsConstants.LOCAL_FILE_STORAGE_PROVDER_NAME.equalsIgnoreCase(providerName)) {
					// it is the local provider
					if (this.saveFileInLocal()) {
						this.fileStorageProviders.get(providerName).save(category, key, content);
					}
				} else {
					// others
					if (this.isAvailableProvider(providerName)) {
						this.fileStorageProviders.get(providerName).save(category, key, content);
					}
				}
			}
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.service.FileStorageProvider#remove(java.lang.String , java.lang.String)
	 */
	public boolean remove(String category, String key) {
		if (this.fileStorageProviders != null && !this.fileStorageProviders.isEmpty()) {
			Collection<FileStorageProvider> ps = this.fileStorageProviders.values();
			for (FileStorageProvider p : ps) {
				p.remove(category, key);
			}
			return true;
		}
		return false;
	}

	/**
	 * needs save the file in local file system?
	 * 
	 * @return
	 */
	private boolean saveFileInLocal() {
		return true;
	}

	/**
	 * is the provider available
	 * 
	 * @param providerName
	 *            the provider name
	 * @return true:available
	 */
	private boolean isAvailableProvider(String providerName) {
		return true;
	}

	/**
	 * @return the fileStorageProviders
	 */
	public Map<String, FileStorageProvider> getFileStorageProviders() {
		return fileStorageProviders;
	}

	/**
	 * @param fileStorageProviders
	 *            the fileStorageProviders to set
	 */
	public void setFileStorageProviders(Map<String, FileStorageProvider> fileStorageProviders) {
		this.fileStorageProviders = fileStorageProviders;
	}

	/**
	 * @return the loadFromProviderName
	 */
	public String getLoadFromProviderName() {
		return loadFromProviderName;
	}

	/**
	 * @param loadFromProviderName
	 *            the loadFromProviderName to set
	 */
	public void setLoadFromProviderName(String loadFromProviderName) {
		this.loadFromProviderName = loadFromProviderName;
	}

}
