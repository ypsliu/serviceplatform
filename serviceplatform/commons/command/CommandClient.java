/**
 * 
 */
package com.ruixue.serviceplatform.commons.command;

/**
 * the command client
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public interface CommandClient {

	/**
	 * to start the command client
	 */
	void start();

	/**
	 * to stop the command client
	 */
	void stop();

	/**
	 * to send the control command
	 * 
	 * @param command
	 *            the command
	 */
	void sendCommand(ControlCommand command);

}
