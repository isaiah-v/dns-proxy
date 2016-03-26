package org.ivcode.dnsproxy.firewall;

import java.net.DatagramPacket;

public interface Firewall {
	boolean isAllowed(DatagramPacket data);
}
