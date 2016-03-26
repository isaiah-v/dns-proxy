package org.ivcode.dnsproxy.server;

import java.io.IOException;

import org.xbill.DNS.Message;

public interface MessageProcessor {
	Message processDnsQueries(Message message) throws IOException;
}
