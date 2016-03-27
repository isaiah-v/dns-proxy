package org.ivcode.dnsproxy.firewall;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;

public interface Firewall {
	boolean addAddress(InetAddress address);
	boolean removeAddress(InetAddress address);
	Set<InetAddress> getAddresses();
	boolean isAllowed(DatagramPacket data);
}
