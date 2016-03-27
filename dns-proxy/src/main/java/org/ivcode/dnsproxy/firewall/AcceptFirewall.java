package org.ivcode.dnsproxy.firewall;

import java.net.DatagramPacket;

public class AcceptFirewall extends AbstractFirewall {

	@Override
	public boolean isAllowed(DatagramPacket data) {
		return addresses.contains(data.getAddress());
	}
}
