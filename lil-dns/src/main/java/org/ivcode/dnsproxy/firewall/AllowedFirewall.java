package org.ivcode.dnsproxy.firewall;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;

public class AllowedFirewall implements Firewall {

	private final Set<InetAddress> allowed;
	
	public AllowedFirewall(Set<InetAddress> allowed) {
		this.allowed = allowed;
	}

	@Override
	public boolean isAllowed(DatagramPacket data) {
		return allowed.contains(data.getAddress());
	}
}
