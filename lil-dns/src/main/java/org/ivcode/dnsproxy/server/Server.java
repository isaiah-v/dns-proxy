package org.ivcode.dnsproxy.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;
import org.ivcode.dnsproxy.firewall.Firewall;

public class Server {
	
	private static final Logger LOGGER = Logger.getLogger(Server.class);
	
	private final ExecutorService executor;
	
	private final MessageProcessor messageProcessor;
	private final Firewall firewall;
	private final int port;
	
	private volatile Thread thread;
	private volatile DatagramSocket socket = null;
	
	public Server(int port, MessageProcessor messageProcessor, Firewall firewall, ExecutorService executor) {
		this.port = port;
		this.firewall = firewall;
		this.messageProcessor = messageProcessor;
		this.executor = executor;
	}
	
	public synchronized void start() throws InterruptedException {
		// defines two shared variables, "thread" and "socket" 
		try {
			if(thread!=null) {
				return;
			}
			
			socket = new DatagramSocket(port);
			
			thread = new Thread(()->run());
			thread.setDaemon(true);
			thread.start();
		} catch (SocketException se) {
			LOGGER.error(se.getMessage(), se);
		}
	}
	
	public synchronized void stop() throws InterruptedException {
		// undefines two shared variables, "thread" and "socket"
		if(this.thread==null) {
			return;
		}
		
		this.socket.close();
		this.socket = null;
		
		join();
		
		this.thread = null;
	}
	
	public void join() throws InterruptedException {
		join(this.thread);
	}
	
	private void join(Thread thread) throws InterruptedException {
		if(thread==null) {
			return;
		}
		thread.join();
	}
	
	private void run() {
		while(!socket.isClosed()) {
			try {
				byte[] buf = new byte[576];
				DatagramPacket packet = new DatagramPacket(buf, 576);
				
				packet.setData(buf);
				socket.receive(packet);
				
				if(!firewall.isAllowed(packet)) {
					LOGGER.debug("BLOCKED : " + packet.getAddress());
					continue;
				}
				
				executor.execute(new ServerTask(socket, packet, messageProcessor));
			} catch(Throwable th) {
				LOGGER.error(th.getMessage(), th);
			}
		}
	}
}
