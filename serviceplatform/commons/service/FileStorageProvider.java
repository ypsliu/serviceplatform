/**
 * 
 */
package com.ruixue.serviceplatform.commons.service;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * the file storage provider
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public interface FileStorageProvider {

	/**
	 * to load the bytes
	 * 
	 * @param category
	 *            the category
	 * @param key
	 *            the key
	 * @return the bytes
	 */
	byte[] loadBytes(String category, String key);

	/**
	 * to load the stream
	 * 
	 * @param category
	 *            the category
	 * @param key
	 *            the key
	 * @param outputStream
	 *            the output stream
	 */
	void loadStream(String category, String key, OutputStream outputStream);

	/**
	 * to load the stream
	 * 
	 * @param category
	 *            the category
	 * @param key
	 *            the key
	 * @return the stream
	 */
	InputStream loadStream(String category, String key);

	/**
	 * to save the content
	 * 
	 * @param category
	 *            the category
	 * @param key
	 *            the key
	 * @param content
	 *            the content
	 * @return true:successed
	 */
	boolean save(String category, String key, byte[] content);

	/**
	 * to save the content
	 * 
	 * @param category
	 *            the category
	 * @param key
	 *            the key
	 * @param content
	 *            the content
	 * @return true:successed
	 */
	boolean save(String category, String key, InputStream content);

	/**
	 * to remove
	 * 
	 * @param category
	 *            the category
	 * @param key
	 *            the key
	 * @return true:successed
	 */
	boolean remove(String category, String key);

}
