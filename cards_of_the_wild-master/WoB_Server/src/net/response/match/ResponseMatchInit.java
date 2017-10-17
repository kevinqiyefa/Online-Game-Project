package net.response.match;

import metadata.NetworkCode;
import net.response.GameResponse;
import util.GamePacket;


public class ResponseMatchInit extends GameResponse {

	private int matchID;
	private short status;
	
	public ResponseMatchInit() {
		response_id = NetworkCode.MATCH_INIT;
	}
	

	@Override
	public byte[] getBytes() {

        GamePacket packet = new GamePacket(response_id);
        packet.addShort16(status);
        packet.addInt32(matchID);
        
		return packet.getBytes();
	}

	public void setMatchID(int matchID){
		this.matchID = matchID;
	}
	
	public void setStatus(short status){
		this.status = status;
	}
}
