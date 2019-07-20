/**
 * 
 */
package com.ruixue.serviceplatform.commons.command;

/**
 * the control command
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public enum ControlCommand {

	/**
	 * to shut down the server
	 */
	SHUT_DOWN("shutdown");

	private String command;

	private ControlCommand(final String command) {
		this.command = command;
	}

	/**
	 * to get the command
	 * 
	 * @return the command
	 */
	public String getCommand() {
		return this.command;
	}

	/**
	 * to parse the command
	 * 
	 * @param command
	 *            the string command
	 * @return the command
	 */
	public static ControlCommand parseCommand(final String command) {
		ControlCommand[] values = ControlCommand.values();
		for (ControlCommand value : values) {
			if (value.getCommand().equalsIgnoreCase(command)) {
				return value;
			}
		}
		return null;
	}

}
