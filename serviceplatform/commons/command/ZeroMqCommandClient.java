/**
 * 
 */
package com.ruixue.serviceplatform.commons.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.zeromq.ZMQ;

/**
 * the Zero MQ implementation for CommandClient
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class ZeroMqCommandClient implements CommandClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZeroMqCommandClient.class);

	private String serverIp;

	private int serverPort;

	private final ZMQ.Context context = ZMQ.context(1);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.command.CommandClient#start()
	 */
	@Override
	public void start() {
		//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.command.CommandClient#stop()
	 */
	@Override
	public void stop() {
		// close the context
		this.context.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.command.CommandClient#sendCommand(com.ruixue.serviceplatform.commons.command.ControlCommand)
	 */
	@Override
	public void sendCommand(final ControlCommand command) {
		// check
		if (command == null) {
			LOGGER.warn("the sending command is null");
			return;
		}
		// socket
		final ZMQ.Socket socket = context.socket(ZMQ.REQ);
		try {
			// connect to server
			LOGGER.debug("connecting to command server: {}:{}", this.serverIp, this.serverPort);
			socket.connect("tcp://" + this.serverIp + ":" + this.serverPort);
			LOGGER.info("connected to command server: {}:{}, sending command: {}", this.serverIp, this.serverPort, command.getCommand());
			// send the command
			socket.send(command.getCommand());
			final String resp = socket.recvStr();
			if (!StringUtils.isEmpty(resp)) {
				LOGGER.info("command response: {}", resp);
			} else {
				LOGGER.warn("no response");
			}
		} catch (Exception e) {
			LOGGER.error("send the command " + command.getCommand() + " error: " + e.getMessage(), e);
		} finally {
			// close the socket
			socket.close();
		}
	}

	/**
	 * @param serverIp
	 *            the serverIp to set
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	/**
	 * @param serverPort
	 *            the serverPort to set
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

}
