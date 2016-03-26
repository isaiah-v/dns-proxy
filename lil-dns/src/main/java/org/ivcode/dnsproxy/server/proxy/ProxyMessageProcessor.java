package org.ivcode.dnsproxy.server.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ivcode.dnsproxy.server.MessageProcessor;
import org.xbill.DNS.Message;
import org.xbill.DNS.Opcode;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.Section;

public class ProxyMessageProcessor implements MessageProcessor {
	
	private final Logger LOGGER = Logger.getLogger(ProxyMessageProcessor.class);

	private final Map<String, Interceptor> interceptors = new HashMap<>();
	private final Resolver proxyClient;
	
	public ProxyMessageProcessor(Resolver proxyClient) {
		this.proxyClient = proxyClient;
	}

	@Override
	public Message processDnsQueries(Message in) throws IOException {
		Message out = null;
		if(isInterceptable(in)) {		
			out = new Message(in.getHeader().getID());
			
			List<Record> unknown = new ArrayList<>();
			
			for(Record q : in.getSectionArray(Section.QUESTION)) {
				Interceptor i = interceptors.get(getHostName(q));
				
				if(i==null) {
					LOGGER.info("PROXY : " + getHostName(q));
					unknown.add(q);
					break;
				}
				
				Record a = i.intercept(q);
				if(a!=null) {
					LOGGER.info("INTERCEPT : " + getHostName(q));
					out.addRecord(q, Section.QUESTION);
					out.addRecord(a, Section.ANSWER);
				} else {
					unknown.add(q);
				}
			}
			
			if(!unknown.isEmpty()) {
				// TODO
				out = proxyClient.send(in);
			}
			
		}
		
		if(out==null) {
			out = proxyClient.send(in);
		}
		
		return out;
	}
	
	public Interceptor addInterceptor(String host, Interceptor interceptor) {
		return interceptors.put(host, interceptor);
	}
	
	public Interceptor removeInterceptor(String host) {
		return interceptors.remove(host);
	}
	
	private boolean isInterceptable(Message message) {
		return message.getHeader().getOpcode()==Opcode.QUERY;
	}
	
	private String getHostName(Record question) {
		return question.getName().toString(true);
	} 
}
