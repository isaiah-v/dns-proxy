package org.ivcode.dnsproxy.firewall;

import java.net.DatagramPacket;

public class AlwaysPassFirewall implements Firewall {

	@Override
	public boolean isAllowed(DatagramPacket data) {
		return true;
	}
}
