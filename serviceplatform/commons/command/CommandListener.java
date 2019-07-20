/**
 * 
 */
package com.ruixue.serviceplatform.commons.command;

/**
 * the command listener
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public interface CommandListener {

	/**
	 * will be invoked when the command received
	 * 
	 * @param command
	 *            the received command
	 */
	void onCommand(ControlCommand command);

}
