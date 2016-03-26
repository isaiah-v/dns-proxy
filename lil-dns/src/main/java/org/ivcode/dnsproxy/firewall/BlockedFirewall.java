package org.ivcode.dnsproxy.firewall;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;

public class BlockedFirewall implements Firewall {

	private final Set<InetAddress> blocked;
	
	public BlockedFirewall(Set<InetAddress> blocked) {
		this.blocked = blocked;
	}

	@Override
	public boolean isAllowed(DatagramPacket data) {
		return !blocked.contains(data.getAddress());
	}
}
