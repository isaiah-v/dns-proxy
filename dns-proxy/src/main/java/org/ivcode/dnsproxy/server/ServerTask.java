package org.ivcode.dnsproxy.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.xbill.DNS.Message;

class ServerTask implements Runnable {

	private final DatagramSocket socket;
	private final DatagramPacket packet;
	private final MessageProcessor messageProcessor;

	public ServerTask(DatagramSocket socket, DatagramPacket packet, MessageProcessor messageProcessor) {
		this.socket = socket;
		this.packet = packet;
		this.messageProcessor = messageProcessor;
	}

	@Override
	public void run() {
		try {
			processRequest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processRequest() throws IOException {
		// input message
		Message in = new Message(packet.getData());

		// output message
		Message out = messageProcessor.processDnsQueries(in);

		// output message was not defined
		if (out == null) {
			// TODO log
			return;
		}

		// set and send the data
		packet.setData(out.toWire());
		socket.send(packet);
	}

}
