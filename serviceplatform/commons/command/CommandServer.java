/**
 * 
 */
package com.ruixue.serviceplatform.commons.command;

/**
 * the command server
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public interface CommandServer {

	/**
	 * to start the command server
	 */
	void start();

	/**
	 * to stop the command server
	 */
	void stop();

	/**
	 * to add the command listener
	 * 
	 * @param listener
	 *            the listener
	 */
	void addCommandListener(CommandListener listener);

}
