package net.response.match;

// Other Imports
import metadata.NetworkCode;
import util.GamePacket;
import net.response.GameResponse;

public class ResponseSummonCard extends GameResponse {
   
	private short status;
	
    public ResponseSummonCard() {
        response_id = NetworkCode.SUMMON_CARD;
    }

    @Override
    public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);
       

        return packet.getBytes();
    }

    public void setStatus(short status){
    	this.status = status;
    }
}
