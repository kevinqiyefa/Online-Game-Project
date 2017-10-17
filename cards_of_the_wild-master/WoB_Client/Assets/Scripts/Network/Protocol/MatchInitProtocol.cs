using UnityEngine;
using System.Collections;
using System;
using System.IO;

public class MatchInitProtocol {
			
	public static NetworkRequest Prepare(int playerID1, int playerID2) {
		NetworkRequest request = new NetworkRequest(NetworkCode.MATCH_INIT);
		request.AddInt32(playerID1);
		request.AddInt32(playerID2);
		
		return request;
	}
	
	public static NetworkResponse Parse(MemoryStream dataStream) {
		ResponseMatchInit response = new ResponseMatchInit();
		response.status = DataReader.ReadShort(dataStream);
		
		if (response.status == 0) {
			response.matchID = DataReader.ReadInt(dataStream);
			}
		return response;
	}
}
	
	public class ResponseMatchInit : NetworkResponse {
		
		public short status { get; set; }
		public int matchID { get; set;}
		
		public ResponseMatchInit() {
			protocol_id = NetworkCode.MATCH_INIT;
		}
	}
