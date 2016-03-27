package org.ivcode.dnsproxy.firewall;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractFirewall implements Firewall {
	
	protected final HashSet<InetAddress> addresses = new HashSet<>();
	
	@Override
	public boolean addAddress(InetAddress address) {
		return addresses.add(address);
	}

	@Override
	public boolean removeAddress(InetAddress address) {
		return addresses.remove(address);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<InetAddress> getAddresses() {
		return (Set<InetAddress>) addresses.clone();
	}
}
