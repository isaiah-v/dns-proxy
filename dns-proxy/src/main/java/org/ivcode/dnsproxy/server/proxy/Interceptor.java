package org.ivcode.dnsproxy.server.proxy;

import org.xbill.DNS.Record;

public interface Interceptor {
	Record intercept(Record quesion);
}
