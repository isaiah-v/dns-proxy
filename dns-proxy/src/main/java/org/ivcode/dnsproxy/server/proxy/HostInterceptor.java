package org.ivcode.dnsproxy.server.proxy;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.xbill.DNS.ARecord;
import org.xbill.DNS.Record;

public class HostInterceptor implements Interceptor {

	private static final long DEFAULT_TIME_TO_LIVE = 256; 
	
	private final InetAddress address;
	private final long ttl;
	
	public HostInterceptor(String host) throws UnknownHostException {
		this(InetAddress.getByName(host));
	}
	
	public HostInterceptor(InetAddress address) {
		this(address, DEFAULT_TIME_TO_LIVE);
	}
	
	public HostInterceptor(InetAddress address, long ttl) {
		this.address = address;
		this.ttl = ttl;
	}

	@Override
	public Record intercept(Record quesion) {
		return new ARecord(quesion.getName(), quesion.getDClass(), ttl, address);
	}

}
