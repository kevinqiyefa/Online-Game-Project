package net.response.match;

import util.GamePacket;
import metadata.NetworkCode;
import net.response.GameResponse;

public class ResponseMatchStatus extends GameResponse {
	
	private int matchID;
	private Boolean isActive;
	private Boolean opponentIsReady;
	
	public ResponseMatchStatus() {
		response_id = NetworkCode.MATCH_STATUS;
	}

	
	@Override
	public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);
        packet.addInt32(matchID);
        packet.addBoolean(isActive);
        packet.addBoolean(opponentIsReady);
		return packet.getBytes();
	}
	
	
	public void setMatchID(int matchID){
		this.matchID = matchID;
	}
	
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @param isReady the isReady to set
	 */
	public void setIsReady(Boolean isReady) {
		this.opponentIsReady = isReady;
	}


}
