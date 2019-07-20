/**
 * 
 */
package com.ruixue.serviceplatform.commons.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruixue.serviceplatform.commons.service.FileStorageProvider;
import com.ruixue.serviceplatform.commons.utils.StreamUtils;

/**
 * the local file system implementation for FileStorageProvider
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public class FileStorageLocalProvider implements FileStorageProvider {

	/**
	 * logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageLocalProvider.class);

	private String fileStoragePath;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.service.FileStorageProvider#loadBytes(java.lang .String, java.lang.String)
	 */
	public byte[] loadBytes(String category, String key) {
		File file = this.getTargetFile(category, key);
		if (file.exists() && file.isFile() && file.canRead()) {
			try {
				return FileUtils.readFileToByteArray(file);
			} catch (IOException e) {
				LOGGER.error("load bytes error: " + e.getMessage(), e);
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
		return new ByteArrayInputStream(this.loadBytes(category, key));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.service.FileStorageProvider#loadStream(java.lang .String, java.lang.String, java.io.OutputStream)
	 */
	public void loadStream(String category, String key, OutputStream outputStream) {
		File file = this.getTargetFile(category, key);
		if (file.exists() && file.isFile() && file.canRead()) {
			try {
				StreamUtils.transfer(file, outputStream);
			} catch (Exception e) {
				LOGGER.error("load stream error: " + e.getMessage(), e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.service.FileStorageProvider#save(java.lang.String, java.lang.String, byte[])
	 */
	public boolean save(String category, String key, byte[] content) {
		return this.save(category, key, new ByteArrayInputStream(content));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.service.FileStorageProvider#save(java.lang.String, java.lang.String, java.io.InputStream)
	 */
	public boolean save(String category, String key, InputStream content) {
		File file = this.getTargetFile(category, key);
		file.getParentFile().mkdirs();
		try {
			FileUtils.copyInputStreamToFile(content, file);
			LOGGER.debug("the file saved to local: " + file.getAbsolutePath());
			return true;
		} catch (IOException e) {
			LOGGER.error("save the file to local error: " + e.getMessage(), e);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.service.FileStorageProvider#remove(java.lang.String , java.lang.String)
	 */
	public boolean remove(String category, String key) {
		File file = this.getTargetFile(category, key);
		if (file.exists() && file.isFile() && file.canWrite()) {
			file.delete();
			LOGGER.debug("the file removed from local: " + file.getAbsolutePath());
			return true;
		}
		return false;
	}

	/**
	 * to get the target file
	 * 
	 * @param category
	 *            the category
	 * @param key
	 *            the key
	 * @return the target file
	 */
	private File getTargetFile(String category, String key) {
		return new File(this.getFileStoragePath() + category + File.separator + key);
	}

	/**
	 * @return the fileStoragePath
	 */
	public String getFileStoragePath() {
		return fileStoragePath;
	}

	/**
	 * @param fileStoragePath
	 *            the fileStoragePath to set
	 */
	public void setFileStoragePath(String fileStoragePath) {
		this.fileStoragePath = fileStoragePath;
	}

}
