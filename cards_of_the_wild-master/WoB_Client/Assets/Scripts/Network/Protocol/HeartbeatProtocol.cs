using System;
using System.IO;

public class HeartbeatProtocol {
	
	public static NetworkRequest Prepare() {
		NetworkRequest request = new NetworkRequest(NetworkCode.HEARTBEAT);
		
		return request;
	}
	
	public static NetworkResponse Parse(MemoryStream dataStream) {
		return null;
	}
}
