package org.ivcode.dnsproxy.firewall;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

public class FirewallBuilder {
	
	private final Set<InetAddress> allowed = new HashSet<>();
	private final Set<InetAddress> blocked = new HashSet<>();
	
	public FirewallBuilder acceptAddress(String host) throws UnknownHostException {
		return acceptAddress(InetAddress.getByName(host));
	}
	
	public FirewallBuilder acceptAddress(InetAddress address) {
		allowed.add(address);
		return this;
	}
	
	public FirewallBuilder blockAddress(String host) throws UnknownHostException {
		return blockAddress(InetAddress.getByName(host));
	}
	
	public FirewallBuilder blockAddress(InetAddress address) {
		blocked.add(address);
		return this;
	}
	
	public Firewall build() {
		if(blocked.isEmpty() && allowed.isEmpty()) {
			return new AlwaysPassFirewall();
		} else if (allowed.isEmpty()) {
			return new BlockedFirewall(blocked);
		} else {
			Set<InetAddress> myAllowed = new HashSet<>(allowed);
			myAllowed.removeAll(blocked);
			
			return new AllowedFirewall(myAllowed);
		}
	}
}
