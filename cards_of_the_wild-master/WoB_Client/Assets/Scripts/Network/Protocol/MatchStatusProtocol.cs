using UnityEngine;
using System.Collections;
using System;
using System.IO;

public class MatchStatusProtocol {
	
public static NetworkRequest Prepare(int playerID, string playerName) {
	NetworkRequest request = new NetworkRequest(NetworkCode.MATCH_STATUS);
	request.AddInt32(playerID);
	request.AddString (playerName);
	return request;
}

public static NetworkResponse Parse(MemoryStream dataStream) {
	ResponseMatchStatus response = new ResponseMatchStatus();
	response.matchID = DataReader.ReadInt(dataStream);
	response.isActive = DataReader.ReadBool(dataStream);
	response.opponentIsReady = DataReader.ReadBool (dataStream);
	return response;
	}
}

public class ResponseMatchStatus : NetworkResponse {

	public int matchID{get; set;}
	public bool isActive{ get; set; }
	public bool opponentIsReady { get; set;}
	
	public ResponseMatchStatus() {
		protocol_id = NetworkCode.MATCH_STATUS;
	}
}
