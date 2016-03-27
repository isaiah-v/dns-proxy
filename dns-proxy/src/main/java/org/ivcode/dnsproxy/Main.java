package org.ivcode.dnsproxy;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.ivcode.dnsproxy.firewall.AcceptFirewall;
import org.ivcode.dnsproxy.firewall.Firewall;
import org.ivcode.dnsproxy.server.Server;
import org.ivcode.dnsproxy.server.proxy.HostInterceptor;
import org.ivcode.dnsproxy.server.proxy.Interceptor;
import org.ivcode.dnsproxy.server.proxy.ProxyMessageProcessor;
import org.xbill.DNS.SimpleResolver;

public class Main {
	public static void main(String... args) throws UnknownHostException, InterruptedException {
		Firewall acceptFirewall = new AcceptFirewall();
		acceptFirewall.addAddress(InetAddress.getByName("71.237.252.85"));
		
		Interceptor fake = new HostInterceptor(InetAddress.getByName("0.0.0.0"));
		Interceptor local = new HostInterceptor(InetAddress.getByName("71.237.252.85"));
		
		ProxyMessageProcessor pmp = new ProxyMessageProcessor(new SimpleResolver("8.8.4.4"));
//		pmp.addInterceptor("android.clients.google.com", fake);
//		pmp.addInterceptor("www.googleapis.com", fake);
//		pmp.addInterceptor("ssl.google-analytics.com", fake);
//		pmp.addInterceptor("lh3.googleusercontent.com", fake);
//		pmp.addInterceptor("play.googleapis.com", fake);
//		pmp.addInterceptor("plus.google.com", fake);
//		pmp.addInterceptor("play.google.com", fake);
//		pmp.addInterceptor("google.com", fake);
//		pmp.addInterceptor("clients4.google.com", fake);
//		pmp.addInterceptor("accounts.google.com", fake);
//		pmp.addInterceptor("accounts.youtube.com", fake);
//		pmp.addInterceptor("mcoc-701.sparxcdn.net", local);
		pmp.addInterceptor("mcoc-701.zap.net", local);
		
		Server server = new Server(53, pmp, acceptFirewall);
		server.start();
		server.join();
	}
}
