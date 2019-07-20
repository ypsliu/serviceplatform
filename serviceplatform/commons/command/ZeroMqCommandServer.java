/**
 * 
 */
package com.ruixue.serviceplatform.commons.command;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.zeromq.ZMQ;

/**
 * the socket implementation for CommandServer
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class ZeroMqCommandServer implements CommandServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZeroMqCommandServer.class);

	private int port;

	private final Set<CommandListener> listeners = new HashSet<CommandListener>();

	private volatile AtomicBoolean runFlag = new AtomicBoolean(true);

	private final ZMQ.Context context = ZMQ.context(1);

	private final ZMQ.Socket socket = context.socket(ZMQ.REP);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.command.CommandServer#start()
	 */
	@Override
	public void start() {
		LOGGER.debug("starting ZeroMqCommandServer on {} ...", this.port);

		this.runFlag.set(true);

		this.socket.bind("tcp://127.0.0.1:" + this.port);

		final Thread worker = new Thread() {

			public void run() {
				while (runFlag.get()) {
					try {
						final String commandStr = socket.recvStr();
						if (!StringUtils.isEmpty(commandStr)) {
							final ControlCommand command = ControlCommand.parseCommand(commandStr);
							if (command != null) {
								LOGGER.info("executing command: {}", commandStr);
								// send the answer
								socket.send("executing command: " + commandStr);
								// call back the command listeners
								for (final CommandListener listener : listeners) {
									listener.onCommand(command);
								}
							} else {
								socket.send("unknown command: " + commandStr);
								LOGGER.warn("unknown command: {}", commandStr);
							}
						}
					} catch (Exception e) {
						LOGGER.error("process the command error: " + e.getMessage(), e);
					}
				}
			}

		};
		worker.setDaemon(true);
		worker.start();
		LOGGER.info("the ZeroMqCommandServer started on {}", this.port);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.command.CommandServer#stop()
	 */
	@Override
	public void stop() {
		LOGGER.debug("stopping ZeroMqCommandServer...");
		// stop the worker
		this.runFlag.set(false);
		// stop the ZeroMQ
		this.socket.close();
		this.context.close();
		LOGGER.info("the ZeroMqCommandServer stopped");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruixue.serviceplatform.commons.command.CommandServer#addCommandListener(com.ruixue.serviceplatform.commons.command.CommandListener)
	 */
	@Override
	public void addCommandListener(final CommandListener listener) {
		if (listener != null) {
			this.listeners.add(listener);
			LOGGER.info("new command listener added");
		} else {
			LOGGER.warn("command listener is null, ignore it");
		}
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

}
